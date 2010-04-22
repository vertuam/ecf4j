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

import java.util.ArrayList;
import java.util.List;

/**
 * Classe controladora do Layout de impressão do cheque
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 */
public class LayoutCheque {
	
	private String fabricante = "";
	private String modelo = "";
	private String banco = "";
	private List<CoordenadaCheque> coordenadas = new ArrayList<CoordenadaCheque>();

	/**
	 * Método seta lista de coordenadas
	 * @param coordenadas <i>List</i>
	 */
	public void setCoordenadas(List<CoordenadaCheque> coordenadas) {
		this.coordenadas = coordenadas;
	}

	/**
	 * Método busca lista de coordenadas 
	 * @return Lista de coordenadas
	 */
	public List<CoordenadaCheque> getCoordenadas() {
		return coordenadas;
	}
	
	/**
	 * Método busca fabricante do Ecf
	 * @return Fabricante do Ecf
	 */
	public String getFabricante() {
		return fabricante;
	}

	/**
	 * Método seta um fabricante para o Ecf
	 * @param fabricante <i>String</i>
	 */
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	/**
	 * Método busca o modelo do Ecf
	 * @return Modelo do Ecf
	 */
	public String getModelo() {
		return modelo;
	}

	/**
	 * Método seta um modelo para o Ecf
	 * @param modelo <i>String</i>
	 */
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	/**
	 * Método busca o código do banco
	 * @return Código do banco
	 */
	public String getBanco() {
		return banco;
	}

	/**
	 * Método seta uma código para o banco
	 * @param banco <i>String</i>
	 */
	public void setBanco(String banco) {
		this.banco = banco;
	}

	/**
	 * Método busca coordenada an lista de coordenadas
	 * @param coordenada <i>String</i>
	 * @return Coordenada
	 */
	public int getCoordenadaCheque(String coordenada){
		for(CoordenadaCheque c : coordenadas){
			if(c.getCoordenada().equalsIgnoreCase(coordenada)){
				return c.getValor();
			}
		}
		return 0;
	}


}
