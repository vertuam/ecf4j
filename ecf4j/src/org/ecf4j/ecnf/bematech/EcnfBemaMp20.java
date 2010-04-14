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
package org.ecf4j.ecnf.bematech;

import java.math.BigDecimal;

import org.ecf4j.ecnf.EcnfException;
import org.ecf4j.utils.bigdecimals.BigDecimalUtils;
import org.ecf4j.utils.bytes.ByteUtils;
import org.ecf4j.utils.comm.exceptions.CommException;
import org.ecf4j.utils.integers.IntegerUtils;
import org.ecf4j.utils.strings.StringUtils;

/**
 * Classe ECNF específica Bematech MP20
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 * @extends EcnfBematechAbstract
 * @see EcnfBematechAbstract 
 */
public class EcnfBemaMp20 extends EcnfBematechAbstract {

	
	//------------------------------------------------------------------
	//IMPRESS�O
	@Override
	protected void imprimirLinhaCondensadaAbstract(String linha) throws CommException {
		ativarModoCondensado();
		String ss[] = linha.split("\n");
		int len = 60;
		String p = "";
		int i = 0;
		
		for(String s: ss){
			p = "";
			i = 0;	
			p = StringUtils.strToEcf(alinhamento, s, i++, len);
			
			while(!p.trim().equals("")){
				executaComando(preparaComando((p+"\n").getBytes()));;
				p = StringUtils.strToEcf(alinhamento, s, i++, len);
			}
		}
		ativarModoNormal();	
	}
	
	@Override
	public void imprimirEan13(String ean) throws CommException, EcnfException {
		throw new EcnfException(EcnfException.ERRO_COMANDO_INEXISTENTE);
		
	}

	@Override
	public void imprimirEan8(String ean) throws CommException, EcnfException {
		throw new EcnfException(EcnfException.ERRO_COMANDO_INEXISTENTE);
		
	}
	
	
	
	//------------------------------------------------------------------
	@Override
	public void cortarPapel() throws CommException {
		
	}

	@Override
	public void cortarParcialmentePapel() throws CommException, EcnfException {
		throw new EcnfException(EcnfException.ERRO_COMANDO_INEXISTENTE);
		
	}

	@Override
	public String modelo() throws EcnfException {
		return "MP-20";
	}

	@Override
	public String getEstado() throws CommException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void imprimirLinha(String str1, String str2) throws CommException {
		int len = getNumColunas() - str1.length();
		str2 = StringUtils.formatStr(str2, len, StringUtils.ALINHAMENTO_DIREITA);
		imprimirLinha(str1+str2);
	}

	@Override
	public void imprimirSeparador() throws CommException {
		imprimirLinhaAbstract(StringUtils.formatStr("-", getNumColunas(), '-', StringUtils.ALINHAMENTO_ESQUERDA));
	}

	@Override
	protected void imprimirLinhaAbstract(String linha) throws CommException {
		String ss[] = linha.split("\n");
		int len = getNumColunas();
		String p = "";
		int i = 0;
		
		for(String s: ss){
			p = "";
			i = 0;	
			p = StringUtils.strToEcf(alinhamento, s, i++, len);
			
			while(!p.trim().equals("")){
				executaComando(preparaComando((p+"\n").getBytes()));;
				p = StringUtils.strToEcf(alinhamento, s, i++, len);
			}
		}
	}

	@Override
	protected void imprimirLinhaExpandidaAbstract(String linha)
			throws CommException {
		String ss[] = linha.split("\n");
		int len = 24;
		String p = "";
		int i = 0;
		
		for(String s: ss){
			p = "";
			i = 0;	
			p = StringUtils.strToEcf(alinhamento, s, i++, len);
			
			while(!p.trim().equals("")){
				ativarModoExpandido();
				executaComando(preparaComando((p+"\n").getBytes()));;
				p = StringUtils.strToEcf(alinhamento, s, i++, len);
			}
		}
		ativarModoNormal();
	}

	@Override
	public void imprimirItem(int numItem, String codProduto,
			String descProduto, String un, BigDecimal qtdeItem,
			BigDecimal valorItem) throws CommException {
		String item = IntegerUtils.intToStr(numItem, 3) + " ";
		String qtde = BigDecimalUtils.qtdeToString(qtdeItem) + " ";
		String valor = BigDecimalUtils.moedaToString(valorItem);
		if (!un.equals("")) {
			un = StringUtils.limiteStr(un, 2) + "x";
		}
		String produto = codProduto+" "+descProduto + " ";
		int len = 4 + qtde.length()+valor.length() + un.length() + produto.length();	
		
		if (len <= getNumColunas()){
			imprimirLinha(alinhaItem(item+produto, un+qtde+valor, getNumColunas() - len));	
		} else {
			imprimirLinhaCondensada(alinhaItem(item+produto, un+qtde+valor, 60 - len));	
		}	
	}

	@Override
	public void imprimirLinhaExpandida(String str1, String str2)
			throws CommException {
		int len = 24 - str1.length();
		str2 = StringUtils.formatStr(str2, len, StringUtils.ALINHAMENTO_DIREITA);
		imprimirLinhaExpandida(str1+str2);
	}

	@Override
	public void abrirGaveta() throws CommException {
		executaComando(preparaComando(ByteUtils.newByteArray(27,118, 100)));
	}

	@Override
	protected void desativarNegritoAbstract() throws CommException {
		executaComando(preparaComando(ByteUtils.newByteArray(27,70)));
	}

	@Override
	protected void desativarItalicoAbstract() throws CommException {
		executaComando(preparaComando(ByteUtils.newByteArray(27,53)));
	}

	@Override
	public void ativarModoNormal() throws CommException {
		executaComando(preparaComando(ByteUtils.newByteArray(27,77)));
	}

	@Override
	public Integer getNumColunas() {
		return 48;
	}

	
}
