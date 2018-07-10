import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.style.Styler.LegendPosition;

public class Chartter {
	public Chartter(){
		
	}
	public static CategoryChart createChart(Logger log, String title) {
		CategoryChart chart  = new CategoryChartBuilder().width(800).height(1000)
				.title(title).yAxisTitle("Tempo Médio da Operação")
				.xAxisTitle("Operação").build();
		chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
		chart.getStyler().setHasAnnotations(true);
		List<Integer> listaOperacoes = log.GetOperacoes();
		List<Long>  listaTempoMedio = log.GetTempoMedio();
		List<Double> listaDesvioPadrao = log.GetDesvioPadrao();
		chart.addSeries(" RPC ",listaOperacoes , listaTempoMedio,listaDesvioPadrao);
		return chart;
	}
	
	
}
