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
package org.ecf4j.ecnf;

/**
 * Classe configura linha para impressão no ECNF
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 */
public class LinhaFormatada {
	private String texto;
	private String modo;
	private String alinhamento;
	private boolean italico;
	private boolean sublinhado;
	private boolean negrito;
	
	public LinhaFormatada(){
		texto = "";
		modo = "NORMAL";
		alinhamento = "ESQUERDA";
		italico = false;
		sublinhado = false;
		negrito = false;				
	}
	public LinhaFormatada(String texto, String modo, String alinhamento, 
			boolean italico, boolean sublinhado, boolean negrito){
		this.texto = texto;
		this.modo = modo;
		this.alinhamento = alinhamento;
		this.italico = italico;
		this.sublinhado = sublinhado;
		this.negrito = negrito;
	}
	
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getModo() {
		return modo;
	}
	public void setModo(String modo) {
		this.modo = modo;
	}
	public String getAlinhamento() {
		return alinhamento;
	}
	public void setAlinhamento(String alinhamento) {
		this.alinhamento = alinhamento;
	}
	public boolean isItalico() {
		return italico;
	}
	public void setItalico(boolean italico) {
		this.italico = italico;
	}
	public boolean isSublinhado() {
		return sublinhado;
	}
	public void setSublinhado(boolean sublinhado) {
		this.sublinhado = sublinhado;
	}
	public boolean isNegrito() {
		return negrito;
	}
	public void setNegrito(boolean negrito) {
		this.negrito = negrito;
	}
}
