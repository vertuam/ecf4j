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
package org.ecf4j.scanner.exceptions;

import org.ecf4j.utils.i18n.MessagesI18n;

/**
 * Classe de Exceções do Scanner
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 * @extends Exception
 */
public class ScannerInativoException extends Exception {

	
	private static final long serialVersionUID = -5085389702093109478L;

	public ScannerInativoException() {
		super(MessagesI18n.BUNDLE.getString("erroScannerInativoException"));
	}

	public ScannerInativoException(String message, Throwable cause) {
		super(message, cause);
	}

	public ScannerInativoException(String message) {
		super(message);
	}

	public ScannerInativoException(Throwable cause) {
		super(cause);
	}

}
