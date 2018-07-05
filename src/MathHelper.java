import java.util.List;

public class MathHelper{
	public MathHelper(){
		
	}
	public static long media(List<Long> tempos) {
		long total = 0;
		for(long tempo : tempos) {
			total += tempo;
		}
		return total/tempos.size();
	}
	public static long desvioPadrao(List<Long> tempos) {
		long media = MathHelper.media(tempos);
		long SomaValorMenosMediaAoQuadrado = 0;
		for(long tempo : tempos) {
			SomaValorMenosMediaAoQuadrado += (tempo - media)*(tempo - media);
		}
		return SomaValorMenosMediaAoQuadrado/tempos.size();	
	}
}
