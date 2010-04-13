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
package org.ecf4j.scanner;

import org.ecf4j.utils.comm.CommEquipto;
import org.ecf4j.utils.comm.exceptions.CommException;

/**
 * Classe abstrata de Scanners
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 * @extends CommEquipto
 * @see CommEquipto
 */
public abstract class ScannerAbstract extends CommEquipto {

	/**
	 * Método abre porta de comunicação Serial/paralela
	 * @param porta <i>String</i>
	 * @param velocidade <i>Integer</i>
	 * @param bitsDados <i>Integer</i>
	 * @param paridade <i>Integer</i>
	 * @param bitsParada <i>Integer</i>
	 * @throws CommException Exceção de comunicação
	 */
	public void abrirComm(String porta, Integer velocidade, Integer bitsDados,
			Integer paridade, Integer bitsParada) throws CommException {
		
		comm.abrirPorta(porta, velocidade, bitsDados, paridade, bitsParada);
	}
	
	/**
	 * Método fecha comunicação Serial/Paralela
	 */
	public void fecharComm(){
		
		comm.fechar();
	}
	
	/**
	 * Método captura o conteúdo da porta do Scanner
	 * @return Código capturado pelo Scanner
	 * @throws CommException Exceção de comunicação
	 */
	public abstract String lerScanner() throws CommException;
	/**
	 * Método captura o conteúdo da porta do Scanner
	 * @return Código capturado pelo Scanner
	 * @throws CommException Exceção de comunicação
	 */
	public abstract byte[] lerBytes() throws CommException;
	/**
	 * Método inicializa comunicação com o Scanner
	 * @param porta <i>String</i>
	 * @param velocidade <i>Integer</i>
	 * @param bitsDados <i>Integer</i>
	 * @param paridade <i>Integer</i>
	 * @param bitsParada <i>Integer</i>
	 * @throws CommException Exceção de comunicação
	 */
	public abstract void inicializar(String porta, Integer velocidade, Integer bitsDados,
			Integer paridade, Integer bitsParada) throws CommException;
	/**
	 * Método finaliza comunicação com o Scanner
	 * @throws CommException Exceção de comunicação
	 */
	public abstract void finalizar() throws CommException;
	/**
	 * Método busca delimitador entre os codigos de barras capturados pelo Scanner
	 * @return Delimitador
	 */
	public abstract byte getDelimitador() ;

}
