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
package org.ecf4j.ecf;

/**
 * Classe controladora das coordenadas para impressão de cheque
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 */
public class CoordenadaCheque {
	private String coordenada;
	private int valor;
	
	/**
	 * Método construtor da classe CoordenadaCheque
	 */
	public CoordenadaCheque(){
		coordenada = "";
		valor = 0;
	}
	
	/**
	 * Método construtor da classe CoordenarCheque <i>(com parâmetros)</i>
	 * @param coordenada <i>String</i>
	 * @param valor <i>int</i>
	 */
	public CoordenadaCheque(String coordenada, int valor){
		this.coordenada = coordenada;
		this.valor = valor;
	}

	/**
	 * Método busca coordenada pra cheque
	 * @return Coordenada
	 */
	public String getCoordenada() {
		return coordenada;
	}

	/**
	 * Método seta coordenada para cheque
	 * @param coordenada <i>String</i>
	 */
	public void setCoordenada(String coordenada) {
		this.coordenada = coordenada;
	}

	/**
	 * Método busca o valor da coordenada co cheque
	 * @return Valor de coordenada
	 */
	public int getValor() {
		return valor;
	}
	
	/**
	 * Método seta um valor para a coordenada
	 * @param valor <i>int</i>
	 */
	public void setValor(int valor) {
		this.valor = valor;
	}

}
