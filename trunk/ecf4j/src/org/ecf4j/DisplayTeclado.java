/**
 * 
 */
package org.ecf4j;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.ecf4j.displayteclado.DisplayTecladoAbstract;
import org.ecf4j.utils.comm.exceptions.CommException;

/**
 * @author pablo
 *
 */
public class DisplayTeclado {
	
	private DisplayTecladoAbstract display = new DisplayTecladoAbstract();
	
	public void inicializar(String codigo, String porta, Integer velocidade, Integer bitsDados,
			Integer paridade, Integer bitsParada) throws CommException{
		display.abrirComm(porta, velocidade, bitsDados, paridade, bitsParada);
	}
	
	public void finalizar(){
		display.fecharComm();
	}
	
	/*
	public void showFirstLine(String texto){
		display.showFisrtLine(texto);
	}
	
	public void showSecondLine(String texto){
		display.showSecondLine(texto);
	}
	
	public void clearFisrtLine(){
		display.clearFirstLine();
	}
	
	public void clearSecondLine(){
		display.clearSecondLine();
	}
	*/
	
	public void outPort(String s) throws FileNotFoundException, IOException{
		display.outPort(s);
	}

}
