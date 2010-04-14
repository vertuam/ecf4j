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
package org.ecf4j.balanca.toledo;

import java.math.BigDecimal;

import org.ecf4j.utils.bigdecimals.BigDecimalUtils;
import org.ecf4j.utils.bytes.ByteUtils;
import org.ecf4j.utils.comm.exceptions.CommException;

/**
 * Classe Balança Toledo8217
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 * @extends BalancaAbstract
 * @see BalancaAbstract
 */
public class Toledo8217 extends ToledoAbstract {

	@Override
	public void inicializar(String porta, Integer velocidade, Integer bitsDados,
			Integer paridade, Integer bitsParada) throws CommException {
		abrirComm(porta, 2400, 8, 0, 1);
	}

	@Override
	public BigDecimal lerBalanca() throws CommException, InterruptedException {
		comm.write(ByteUtils.newByteArray(5));
		Thread.sleep(200);
		
		byte[] bytes = comm.readDireto();//(7);
      
		//Thread.sleep(500);
		
		String peso = new String(bytes);
		String s = peso.substring(3, 6);
		
		peso = peso.substring(1, 3);
		peso = peso + "." + s;
		
		return BigDecimalUtils.newQtde(peso);
	}
	
	
	
	

}
