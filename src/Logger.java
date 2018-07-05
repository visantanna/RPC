import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class Logger {
	private List<Relatorio> listaRelatorios;
	
	public void addRelatorio(Relatorio relatorio) {
		listaRelatorios.add(relatorio);
	}
	public void log(){
		try {
			List<String> lines = new ArrayList<String>();
			String currentTime = new Timestamp(System.currentTimeMillis()).toString();
			Path file = Paths.get("Results-"+ currentTime + ".txt" );
			for(Relatorio relatorio : listaRelatorios ) {
				String line =  relatorio.getNumeroDaOperacao() + " " + relatorio.getMedia() + relatorio.getDesvioPadrao();
				lines.add(line);
			}
			Files.write(file,lines);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Erro tentando logar os resultados da aplicação");
			e.printStackTrace();
		}
	}
}
