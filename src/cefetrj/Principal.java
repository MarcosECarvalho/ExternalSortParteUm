package cefetrj;

import java.io.RandomAccessFile;
import java.util.*;


public class Principal{
	public static final long qntLinhas = 29;
	public static void main(String[] arg) throws Exception{

		RandomAccessFile cepDat =  new RandomAccessFile("ceptest.dat", "r");
		RandomAccessFile colunas =  new RandomAccessFile("colunaaux.dat", "rw");
		Scanner scan = new Scanner(System.in);
		
		// Para proxima etapa (merge) -> adicionar um metodo de verificar espaco em disco e estimar quantidade e tamanho de colunas
		
		System.out.println("Digite o número de colunas");
		int qntColunas = scan.nextInt();
		
		long [] chunks = defineChunk(cepDat, qntColunas);
		
		
		List<byte[]> chunkAux = new ArrayList<byte[]>();
		
		for (int i = 0; i < chunks.length; i++) {
			if(i == 0) {
			chunkAux = ordenaChunks(cepDat, 0, chunks[i]);
			}else {
			chunkAux = ordenaChunks(cepDat,chunks[i-1], chunks[i]);
			}
			escreveChunk(chunkAux, colunas);
		}

		cepDat.close();
		colunas.close();
		scan.close();
	}
	


  public static long [] defineChunk(RandomAccessFile e, int qntColunas) throws Exception{
	  
	long [] chunks = new long [qntColunas];
	long qntLinhas = (e.length()/300);
	
	long linhaspColunas = (qntLinhas/qntColunas);
	long finalChunk = linhaspColunas;
	
	for (int i = 0; i < chunks.length; i++) {
		chunks[i] = finalChunk;
		finalChunk += linhaspColunas;
	}
	
	if(qntLinhas%qntColunas > 0) {	
		chunks[chunks.length-1] += qntLinhas%qntColunas;
	}
	
	return chunks;
}
  
  public static List<byte[]> ordenaChunks(RandomAccessFile e, long inicio, long ultimo) throws Exception{ 
	  List<byte[]> chunkAux = new ArrayList<byte[]>();
		
		for (long i = inicio; i < ultimo; i++) { //trocar 5 por tamChunkAtual
			e.seek(i*300);
			byte [] aux = new byte[300];			
			e.readFully(aux);
			chunkAux.add(aux);
		}
		Collections.sort(chunkAux, new Compara());
	  return chunkAux;
  }
  
  
  public static void escreveChunk(List<byte[]> chunkAux, RandomAccessFile e) throws Exception {
	  for(int i = 0; i<chunkAux.size(); i++){
		//for para escrever a coluna 
			
			e.write(chunkAux.get(i));
			
			
		}
  }
  

}