/**
 * Ecf4J - framework Java para interação com equipamentos de Automação Comercial 
 * 
 * Direitos Autorais Reservados (c) 2009-2010 ecf4j.org
 *
 * Autores: Agner Gerônimo Munhoz, 
 *          Pablo Henrique Fassina, 
 *          Rafael Pasqualini de Freitas,
 *          Wellington Carvalho
 *
 * Você pode obter a última versão desse arquivo na pagina do Ecf4J.org
 * disponível em: <http://www.ecf4j.org> 21/09/2009.
 *
 * Este arquivo é parte da framework Ecf4J
 *
 * Ecf4J é um framework livre; você pode redistribui-lo e/ou 
 * modifica-lo dentro dos termos da Licença Pública Geral Menor GNU como 
 * publicada pela Fundação do Software Livre (FSF); na versão 2.1 da 
 * Licença.
 *
 * Este framework é distribuido na esperança que possa ser  util, 
 * mas SEM NENHUMA GARANTIA; sem uma garantia implicita de ADEQUAÇÂO a qualquer
 * MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a
 * Licença Pública Geral GNU para maiores detalhes.
 *
 * Você deve ter recebido uma cópia da Licença Pública Geral Menor GNU
 * junto com este framework, se não, escreva para a Fundação do Software
 * Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.ecf4j;

import java.util.ArrayList;
import java.util.List;

import org.ecf4j.scanner.ScannerAbstract;
import org.ecf4j.scanner.ScannerListener;
import org.ecf4j.scanner.exceptions.ScannerInativoException;
import org.ecf4j.utils.Ecf4jFactory;
import org.ecf4j.utils.bytes.ByteUtils;
import org.ecf4j.utils.comm.exceptions.CommException;

/**
 * Classe controladora de Scanner serial
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 */
public class Scanner {
	
	//propriedades
	private List<ScannerListener> scannerListeners = new ArrayList<ScannerListener>();
	private Thread thread = null;
	private ScannerAbstract scanner = null;
	private boolean scannerVazio = true;
	private byte[] buffer = null;
	private boolean terminated = false;
	
	//metodos
	/**
	 * Método construtor da classe
	 */
	public Scanner(){
		scanner = null;
		criarThread();
	}
	
	/**
	 * Método cria a thread que faz a leitura da porta do scanner
	 */
	private void criarThread(){
		thread = new Thread(){
			
			@Override
			public void run() {
				byte[] b = null;
				
				while(!terminated){
					
					try {
						b = scanner.lerBytes();
						if (b.length > 0){
							scannerVazio = false;
							for(int i = 0; i < b.length; i++){
								if(b[i] != scanner.getDelimitador()){
									buffer = ByteUtils.encondeByteArray(buffer, ByteUtils.newByteArray(b[i]));
								}
								else{
									if ((buffer != null)&&(buffer.length > 0)){
										notifyScannerListeners(ByteUtils.byteArrayToEan(buffer));//(new String(buffer).trim());
										buffer = null;
									}
									/*
									if(b[i+1] != 0){
										buffer = ByteUtils.encondeByteArray(buffer, ByteUtils.newByteArray(b[i+1]));
									}
									*/
								}
							}	
						} else{
							scannerVazio = true;	
						}
					} catch (Exception e) {}
				}
			}
		};
		thread.setPriority(Thread.MIN_PRIORITY);
	}
	
	/**
	 * Método captura o conteúdo da porta do Scanner
	 * @return Código capturado pelo Scanner
	 * @throws ScannerInativoException 
	 * @throws CommException 
	 */
	public String lerScanner() throws ScannerInativoException, CommException{
		if (scanner != null){
			return scanner.lerScanner();
		}else{
			throw new ScannerInativoException();
		}
	}
	
	/**
	 * Método inicializa comunicação com o Scanner
	 * @param codigo <i>String</i>
	 * @param porta <i>String</i>
	 * @param velocidade <i>Integer</i>
	 * @param bitsDados <i>Integer</i>
	 * @param paridade <i>Integer</i>
	 * @param bitsParada <i>Integer</i>
	 * @throws CommException Exceção de comunicação
	 */
	public void inicializar (String codigo, String porta, Integer velocidade, Integer bitsDados,
			Integer paridade, Integer bitsParada) throws CommException{
		scanner = Ecf4jFactory.createScanner(codigo);
		scanner.inicializar(porta, velocidade, bitsDados, paridade, bitsParada);
		
		if(!scannerListeners.isEmpty()){
			thread.start();
		}
		
	}
	
	/**
	 * Método finaliza comunicação com Scanner
	 * @throws CommException Exceção de comunicação
	 */
	public void finalizar() throws CommException{
		terminated = true;
		scanner.finalizar();
	}
	
	/**
	 * Método adiciona um ScannerListener a lista
	 * @param s ScannerListener
	 */
	public void addScannerListener(ScannerListener s){
		scannerListeners.add(s);
	}
	
	/**
	 * Método remove um ScannerListener da lista
	 * @param s ScannerListener
	 */
	public void removeScannerListener(ScannerListener s){
		scannerListeners.remove(s);
	}
	
	/**
	 * Método passa o código capturado pelo scanner para o ScannerListener
	 * @param codigo <i>String</i>
	 */
	private synchronized void notifyScannerListeners(String codigo){
		if (!terminated){
			for(ScannerListener s : scannerListeners){
				s.onRead(codigo);
			}	
		}
	}
	
	/**
	 * Método limpa o buffer do scanner
	 * @throws CommException Exceção de comunicação
	 */
	public void limparScanner() throws CommException{
		byte[] b = scanner.lerBytes();
		buffer = null;		
	}
	
	/**
	 * Método verifica se o buffer do scanner está vazio
	 * @return boolena
	 * <li> True - Caso o buffer esteja vazio
	 * <li> False - Caso o buffer não estaja vazio
	 */
	public boolean isScannerVazio(){
		return scannerVazio;
	}
}
