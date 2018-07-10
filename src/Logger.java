import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.CategoryChart;

import java.sql.Timestamp;

public class Logger {
	private List<Relatorio> listaRelatorios = new ArrayList<Relatorio>();

	public void addRelatorio(Relatorio relatorio) {
		listaRelatorios.add(relatorio);
	}
	public List<String> log(){
		String fileName = "";
		try {
			List<String> lines = new ArrayList<String>();
			String currentTime = new Timestamp(System.currentTimeMillis()).toString();
			fileName = "Results-"+ currentTime ;
			Path file = Paths.get(fileName+".txt" );
			for(Relatorio relatorio : listaRelatorios ) {
				StringBuffer line =new StringBuffer("Número da operação: " + relatorio.getNumeroDaOperacao() + " Média de tempo (ms):" 
						+ relatorio.getMedia() +" Desvio Padrão: "+ relatorio.getDesvioPadrao() + "\n");
				int nroExecucao = 0;
				for(Long execucao : relatorio.getExecucoes()) {
					nroExecucao ++;
					line.append("Tempo da execução (ms) " + nroExecucao + "º : " +execucao + "\n" );
				}
				lines.add(line.toString());
			}
			Files.write(file,lines);
			CategoryChart chart = Chartter.createChart(this , "Tempo de Processamento de uma RPC");
			BitmapEncoder.saveBitmap(chart, fileName+".png", BitmapFormat.PNG);
			
			CategoryChart chart2 = Chartter.createChart(this.getLoggerSemOutliers(),"Tempo de Processamento de uma RPC sem Outliers");
			BitmapEncoder.saveBitmap(chart2, fileName+"-Sem_Outliers.png", BitmapFormat.PNG);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Erro tentando logar os resultados da aplicação");
			e.printStackTrace();
		}
		return Arrays.asList(new String[] {fileName+".txt" , fileName+".png" , fileName+"-Sem_Outliers.png" });
	}
	public List<Relatorio> getListaRelatorios() {
		return listaRelatorios;
	}
	public List<Integer> GetOperacoes() {
		int qtdRelatorios = this.getListaRelatorios().size();
		List<Integer> listaDeOperacoes =  new ArrayList<Integer>();
		for(int relatorio = 0 ; relatorio < qtdRelatorios ; relatorio ++ ) {
			listaDeOperacoes.add(relatorio);
		}
		return listaDeOperacoes;
	}
	public List<Long> GetTempoMedio(){
		List<Long> listaTempoMedio = new ArrayList<Long>(); 
		for(Relatorio relatorio : this.getListaRelatorios()) {
			listaTempoMedio.add(relatorio.getMedia());
		}
		return listaTempoMedio;
	}
	public List<Double> GetDesvioPadrao(){
		List<Double> listaDesvioPadrao = new ArrayList<Double>(); 
		for(Relatorio relatorio : this.getListaRelatorios()) {
			listaDesvioPadrao.add(relatorio.getDesvioPadrao());
		}
		return listaDesvioPadrao;
	}
	public Logger getLoggerSemOutliers() {
		Logger loggerSemOutLiers = new Logger();
		int nroOper = 0;
		for(Relatorio relatorio : this.getListaRelatorios()) {
			nroOper ++;
			Relatorio relatorioSemOutliers = new Relatorio(nroOper);
			List<Long> ExecucoesSemOutliers = RemoveOutliers(relatorio.getExecucoes());
			ExecucoesSemOutliers.forEach(execucao -> relatorioSemOutliers.addExecucao(execucao));
			relatorioSemOutliers.setMedia(MathHelper.media(relatorioSemOutliers.getExecucoes()));
			relatorioSemOutliers.setDesvioPadrao(MathHelper.desvioPadrao(relatorioSemOutliers.getExecucoes()));
			loggerSemOutLiers.addRelatorio(relatorioSemOutliers);
			
		}
		return loggerSemOutLiers;
	}
	public static List<Long> RemoveOutliers(List<Long> listaExecucoes) {
		Collections.sort(listaExecucoes);
		List<Long> output = new ArrayList<Long>();
		List<Long> data1 = new ArrayList<Long>();
		List<Long> data2 = new ArrayList<Long>();
		if (listaExecucoes.size() % 2 == 0) {
			data1 = listaExecucoes.subList(0, listaExecucoes.size() / 2);
			data2 = listaExecucoes.subList(listaExecucoes.size() / 2, listaExecucoes.size());
		} else {
			data1 = listaExecucoes.subList(0, listaExecucoes.size() / 2);
			data2 = listaExecucoes.subList(listaExecucoes.size() / 2 + 1, listaExecucoes.size());
		}
		double q1 = getMedian(data1);
		double q3 = getMedian(data2);
		double iqr = q3 - q1;
		double lowerFence = q1 - 1.5 * iqr;
		double upperFence = q3 + 1.5 * iqr;
		for (int i = 0; i < listaExecucoes.size(); i++) {
			if (listaExecucoes.get(i) >= lowerFence && listaExecucoes.get(i) <= upperFence)
				output.add(listaExecucoes.get(i));
		}
		if(output.isEmpty()) {
			return listaExecucoes;
		}else {
			return output;
		}
	}
	private static double getMedian(List<Long> data) {
        if (data.size() % 2 == 0)
            return (data.get(data.size() / 2) + data.get(data.size() / 2 - 1)) / 2;
        else
            return data.get(data.size() / 2);
    }
}
