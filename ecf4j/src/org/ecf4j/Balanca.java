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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.ecf4j.balanca.BalancaAbstract;
import org.ecf4j.balanca.BalancaListener;
import org.ecf4j.balanca.exceptions.BalancaInativaException;
import org.ecf4j.balanca.toledo.Toledo8217;
import org.ecf4j.utils.Ecf4jFactory;
import org.ecf4j.utils.bigdecimals.BigDecimalUtils;
import org.ecf4j.utils.comm.exceptions.CommException;

/**
 * Classe controladora de Balança serial
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 */
public class Balanca {
	
	private List<BalancaListener> balancaListeners = new ArrayList<BalancaListener>();
	private Thread thread = null;
	public BalancaAbstract balanca = null;
	private BigDecimal pesoAnterior = BigDecimalUtils.newQtde();
	private boolean terminated = false;
	
	/**
	 * Método construtor da classe
	 */
	public Balanca(){		
		balanca = null;
		criarTherad();
	}
	
	/**
	 * Método cria a Thread que faz a leitura da porta serial da balança
	 */
	public void criarTherad(){
		thread = new Thread(){

			@Override
			public void run() {
				BigDecimal p = BigDecimalUtils.newQtde();
				while (!terminated){
					try {
						p = balanca.lerBalanca();
						if(BigDecimalUtils.isDifferent(p, pesoAnterior)){
							pesoAnterior = p;
							notifyBalancaListeners(p);
						}
					} catch (Exception e) {}
				}
			}			
		};
		thread.setPriority(Thread.MIN_PRIORITY);
	}
	
	/**
	 * Método faz a leitura do peso enviado pela balança na norta serial
	 * @return peso
	 * @throws BalancaInativaException Exceção por balança inativa
	 * @throws CommException Exceção de comunicação
	 * @throws InterruptedException Exceção por interrupsão
	 */
	public BigDecimal lerBalanca() throws BalancaInativaException, CommException, InterruptedException{
		if (balanca != null){
			return balanca.lerBalanca();
		}else{
			throw new BalancaInativaException();
		}
	}
	
	/**
	 * Metodo de inicialização da balança
	 * @param codigo <i><String/i>
	 * @param porta <i>String</i>
	 * @param velocidade <i>Integer</i>
	 * @param bitsDados <i>Integer</i>
	 * @param paridade <i>Integer</i>
	 * @param bitsParada <i>Integer</i>
	 * @throws CommException Exceção de comunicação
	 */
	public void inicializar(String codigo, String porta, Integer velocidade, Integer bitsDados,
			Integer paridade, Integer bitsParada) throws CommException{
		balanca = Ecf4jFactory.createBalanca(codigo);
		balanca.inicializar(porta, velocidade, bitsDados, paridade, bitsParada);
		
		if(!balancaListeners.isEmpty()){
			thread.start();
		}
	}
	
	/**
	 * Método que finaliza comunicação com a balança
	 * @throws CommException Exceção de comunicação
	 */
	public void finalizar() throws CommException{
		terminated = true;
		balanca.finalizar();
	}
	
	/**
	 * Método adiciona uma BalancaListener a uma lista
	 * @param b <i>BalancaListener</i>
	 */
	public void addBalancaListener(BalancaListener b){
		balancaListeners.add(b);
	}
	
	/**
	 * Método remove uma BalancaListener de uma lista
	 * @param b <i>BalancaListener</i>
	 */
	public void removeBalancaListener(BalancaListener b){
		balancaListeners.remove(b);
	}
	
	/**
	 * Método passa o peso da balança para a BalancaListener
	 * @param peso <i>BigDecimal</i>
	 */
	public synchronized void notifyBalancaListeners(BigDecimal peso){
		if (!terminated){
			for(BalancaListener b : balancaListeners){
				b.onChangeWeight(peso);
			}	
		}
	}
}
