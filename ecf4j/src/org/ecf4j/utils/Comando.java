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
package org.ecf4j.utils;

/**
 * @author Pablo Fassina e Agner Munhoz
 *
 */
public class Comando {
	private String value = "";
	public Comando() {
		value = "";
	}
	public Comando(String arg){
		value = trataString(arg);
	}
	public Comando(byte[] arg) {
		value = new String(arg);
	}
	public Comando(int... charArray) {
		value = "";
		for (int i : charArray) {
			value = value+(char) i;				
		}
	}
	public void add(String arg) {
		value = value.concat(trataString(arg));
	}
	public void add(byte[] arg) {
		String str = new String(arg);
		value = value.concat(str);	
	}
	public void add(char arg) {
		value = value+arg;
	}
	public void add(int chr) {
		value = value+(char) chr;
	}
	public void add(int... charArray) {
		for (int i : charArray) {
			value = value+(char) i;				
		}
	}	
	public void clear() {
		value = "";
	}
	private String trataString(String arg){
		arg = arg.replaceAll("Á", "A"); arg = arg.replaceAll("á", "a");
		arg = arg.replaceAll("À", "A"); arg = arg.replaceAll("à", "a");
		arg = arg.replaceAll("Â", "A"); arg = arg.replaceAll("â", "a");
		arg = arg.replaceAll("Ã", "A"); arg = arg.replaceAll("ã", "a");
		arg = arg.replaceAll("Ä", "A"); arg = arg.replaceAll("ä", "a");
		arg = arg.replaceAll("É", "A"); arg = arg.replaceAll("é", "a");
		arg = arg.replaceAll("È", "A"); arg = arg.replaceAll("è", "a");
		arg = arg.replaceAll("Ê", "A"); arg = arg.replaceAll("ê", "a");
		arg = arg.replaceAll("Ë", "A"); arg = arg.replaceAll("ë", "a");
		arg = arg.replaceAll("Í", "I"); arg = arg.replaceAll("í", "i");
		arg = arg.replaceAll("Ì", "I"); arg = arg.replaceAll("ì", "i");
		arg = arg.replaceAll("Î", "I"); arg = arg.replaceAll("î", "i");
		arg = arg.replaceAll("Ï", "I"); arg = arg.replaceAll("ï", "i");
		arg = arg.replaceAll("Ó", "O"); arg = arg.replaceAll("ó", "o");
		arg = arg.replaceAll("Ò", "O"); arg = arg.replaceAll("ò", "o");
		arg = arg.replaceAll("Ô", "O"); arg = arg.replaceAll("ô", "o");
		arg = arg.replaceAll("Õ", "O"); arg = arg.replaceAll("õ", "o");
		arg = arg.replaceAll("Ö", "O"); arg = arg.replaceAll("ö", "o");
		arg = arg.replaceAll("Ú", "U"); arg = arg.replaceAll("ú", "u");
		arg = arg.replaceAll("Ù", "U"); arg = arg.replaceAll("ù", "u");
		arg = arg.replaceAll("Û", "U"); arg = arg.replaceAll("û", "u");
		arg = arg.replaceAll("Ü", "U"); arg = arg.replaceAll("ü", "u");
		return arg;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return value;
	}
	
}
