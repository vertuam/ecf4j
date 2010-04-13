/**
 * 
 */
package org.ecf4j.displayteclado;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.ecf4j.utils.comm.CommEquipto;
import org.ecf4j.utils.comm.exceptions.CommException;
import org.ecf4j.utils.files.FileUtils;

/**
 * @author pablo
 *
 */
public class DisplayTecladoAbstract extends CommEquipto {
	
	public void abrirComm(String porta, Integer velocidade, Integer bitsDados,
			Integer paridade, Integer bitsParada) throws CommException {
		
		comm.abrirPorta(porta, velocidade, bitsDados, paridade, bitsParada);
	}
	
	public void fecharComm(){
		comm.fechar();
	}
	
	public void outPort(String s) throws FileNotFoundException, IOException{
		String so = System.getProperty("os.name");
		
		if(so.trim().toUpperCase().equals("LINUX")){
			
			File fw = new File("/dev/port");
			
			RandomAccessFile port = new RandomAccessFile(fw, "r");
			port.seek(96);
			
			port.writeByte("T".getBytes()[0]);
			
			port.close();
			/*
			StringBuffer port = new StringBuffer(FileUtils.carregar("/dev/port"));
			
			if (port != null){
				port.setCharAt(96, c);
			}
			
			FileUtils.salvar("/dev/port", port.toString(), false);
			*/
			
		}else{
			//Implementação para windows TODO
		}
	}
	
	/*
	public abstract void showFisrtLine(String texto);
	public abstract void showSecondLine(String texto);
	public abstract void clearFirstLine();
	public abstract void clearSecondLine();
	*/
	
}
