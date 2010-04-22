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

import org.ecf4j.utils.bigdecimals.BigDecimalUtils;

/**
 * Classe controladora das Formas de Pagamento
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 */
public class FormaPagamento {
	
	private String codigo;
	private String descricao;
	private boolean permiteVincular;
	private BigDecimal total = BigDecimalUtils.newMoeda();
	
	/**
	 * Metodo construtor da classe FormaPagamento
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @param permiteVincular <i>boolean</i>
	 * @param valor <i>BigDecimal</i>
	 */
	public FormaPagamento(String codigo, String descricao, boolean permiteVincular, BigDecimal valor){
		this.codigo = codigo;
		this.descricao = descricao;
		this.permiteVincular = permiteVincular;
	}

	/**
	 * Método busca código da forma de pagamento
	 * @return Código de Forma de Pagamento
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Método seta uma valor para o código da forma de pagamento
	 * @param codigo <i>String</i>
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Método busca descrição de forma de pagamento
	 * @return Decrição de Formade Pagamento
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Método seta uma descrição para a forma de pagamento
	 * @param descricao <i>String</i>
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Método retorna um boolean indicando se a forma de pagamento permite vincular cupom 
	 * @return boolean
	 * <li> True - Caso permita vincular cupom
	 * <li> False - Caso não permita vincular cupom
	 */
	public boolean getpermiteVincular() {
		return permiteVincular;
	}

	/**
	 * Métod seta se é permitido vincular cupom  para determinada forma de pagamento
	 * @param permiteVincular <i>boolean</i>
	 */
	public void setPermiteVincular(boolean permiteVincular) {
		this.permiteVincular = permiteVincular;
	}

	/**
	 * Método busca valor total para determinada forma de pagamento
	 * @return Valor vendido para determinada forma de pagamento
	 */
	public BigDecimal getTotal() {
		return total;
	}

	/**
	 * Método seta valor total vendido para determinada forma de pagamento
	 * @param total <i>BigDecimal</i>
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	

}
