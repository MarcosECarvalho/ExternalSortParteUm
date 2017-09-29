package cefetrj;

import java.util.Comparator;

public class Compara implements Comparator<byte[]>{
	
	//Chaves para determinar a posicao inicial e final de onde esta cada conteudo
	private int offsetCEPI = 290;
	private int offsetCEPF = 298;
	
	private int offsetLOGI = 0;
	private int offsetLOGF = 72;
					
	public int compare(byte[] a, byte[] b) {
		String aCEP = null;
		String bCEP = null;
		
		for(int i = offsetCEPI; i<offsetCEPF; i++) {
			aCEP += a[i];
			bCEP += b[i];
		}
		
		
		
		if(aCEP.equals(bCEP)) {
			String aLOG = null;
			String bLOG = null;
			for(int i = offsetLOGI; i<offsetLOGF; i++) {
				aLOG += a[i];
				bLOG += b[i];
			}
			
			return aLOG.compareTo(bLOG);
			
		}else {
			return aCEP.compareTo(bCEP);
		}
		 
	}
 
}
