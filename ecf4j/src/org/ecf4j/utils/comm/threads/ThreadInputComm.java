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
 * Esse arquivo usa a biblioteca RXTX Copyright 1997-2007 by Trent Jarvi.
 * disponível em: <http://users.frii.com/jarvi/rxtx/index.html> 21/09/2009.
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
package org.ecf4j.utils.comm.threads;

import java.io.IOException;
import java.io.InputStream;

import org.ecf4j.utils.comm.exceptions.CommException;
import org.ecf4j.utils.i18n.MessagesI18n;

/**
 * 
 * @author Pablo Fassina e Agner Munhoz
 */
public class ThreadInputComm extends Thread {
	
	private InputStream in = null;
	private int timeOut = 200;
	private boolean terminated = false;
	private String buffer = "";
		

	public synchronized String getBuffer() {
		String result = buffer;
		this.buffer = "";
		return result;
	}

	private void setBuffer(String buffer) {
		this.buffer += buffer;
	}
	
	public synchronized void clearBuffer(){
		this.buffer = "";
	}

	public boolean isTerminated() {
		return terminated;
	}

	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public InputStream getIn() {
		return in;
	}

	public void setIn(InputStream in) {
		this.in = in;
	}

	@Override
	public void run() {
		byte[] buffer = new byte[1024];
		int len = 0;
		 
		if (in != null){
			while(!terminated){
				
				try {
					len = this.in.read(buffer);
					setBuffer(new String(buffer,0,len));
				} catch (IOException e) {
					e.printStackTrace();
				}
				

			}
		}
		super.run();
	}
	
	
	
	

}
