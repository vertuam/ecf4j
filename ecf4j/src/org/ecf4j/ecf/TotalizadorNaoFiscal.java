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

import java.math.BigDecimal;


/**
 * Classe controladora dos Totalizadores Não Fiscais
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 */
public class TotalizadorNaoFiscal {
	
	private String codigo;
	private String descricao;
	private BigDecimal valor;
	private String contador;
	
	/**
	 * Método construtor ca classe TotalizadorNaoFiscal
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @param contador <i>String</i>
	 */
	public TotalizadorNaoFiscal(String codigo, String descricao, BigDecimal valor, String contador){
		this.codigo = codigo;
		this.descricao = descricao;
		this.valor = valor;
		this.contador = contador;
	}

	/**
	 * Método busca código de totalizador não fiscal
	 * @return Código
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Método seta código para totalizador não fiscal
	 * @param codigo <i>String</i>
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Método valor do totalizador não fiscal
	 * @return Valor do Totalizador
	 */
	public BigDecimal getValor() {
		return valor;
	}

	/**
	 * Método seta um valor para o totalizador não fiscal
	 * @param valor <i>BigDecimal</i>
	 */
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	/**
	 * Método busca descrição de totalizador não fiscal
	 * @return Descrição de totalizador
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Método seta uma descrição para totalizador não fiscal
	 * @param descricao <i>String</i>
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Método retorna o contador do totalizador não fiscal
	 * @return Contador
	 */
	public String getContador() {
		return contador;
	}

	/**
	 * Método seta um contador para o totalizador não fiscal
	 * @param contador <i>String</i>
	 */
	public void setContador(String contador) {
		this.contador = contador;
	}
	
	
	
}
