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
 * Classe controladora de aliquotas
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 */
public class Aliquota {
	
	private String codigo;
	private BigDecimal aliquota = BigDecimalUtils.newMoeda();
	private BigDecimal total = BigDecimalUtils.newMoeda();
	private String incidencia = "P";
	private String sitTributaria = "T";

	/**
	 * Método construtor da classe Aliquota
	 * @param codigo <i>String</i>
	 * @param aliquota <i>BigDecimal</i>
	 * @param total <i>BigDecimal</i>
	 * @param incidencia <i>String</i>
	 * @param sitTributaria <i>String</i>
	 */
	public Aliquota (String codigo, BigDecimal aliquota, BigDecimal total, String incidencia, String sitTributaria){
		this.aliquota = aliquota;
		this.codigo = codigo;
		this.incidencia = incidencia;
		this.total = total;
	}

	/**
	 * Método retorna o código da aliquota
	 * @return Código
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Método seta uma código para uma aliquota
	 * @param codigo <i>String</i>
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Método busca o valor percentual de uma ali<i></i>quota
	 * @return Valor de aliquota
	 */
	public BigDecimal getAliquota() {
		return aliquota;
	}

	/**
	 * Método seta um valor percentual para uam aliquota
	 * @param aliquota <i>BigDecimal</i>
	 */
	public void setAliquota(BigDecimal aliquota) {
		this.aliquota = aliquota;
	}

	/**
	 * Método busca o valor vendido para determianda aliquota
	 * @return Valor vendido
	 */
	public BigDecimal getTotal() {
		return total;
	}

	/**
	 * Métod seta uma valor vendido para o objeto aliquota
	 * @param total <i>BigDecimal</i>
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	/**
	 * Método busca a incidência da aliquota
	 * @return Incidência da aliquota
	 */
	public String getIncidencia() {
		return incidencia;
	}

	/**
	 * Método seta a incidência de aliquota
	 * @param incidencia <i>String</i>
	 */
	public void setIncidencia(String incidencia) {
		this.incidencia = incidencia;
	}
	
	/**
	 * Método busca a situação tributária de aliquota
	 * @return Situação tributária
	 */
	public String getSitTributaria() {
		return sitTributaria;
	}

	/**
	 * Método seta uma situação tributária para aliquota
	 * @param sitTributaria <i>String</i>
	 */
	public void setSitTributaria(String sitTributaria) {
		this.sitTributaria = sitTributaria;
	}
	

}
