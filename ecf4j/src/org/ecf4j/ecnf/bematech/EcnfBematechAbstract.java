/**
 * Ecf4J - framework Java para interaï¿½ï¿½o com equipamentos de Automaï¿½ï¿½o Comercial 
 * 
 * Direitos Autorais Reservados (c) 2009-2010 ecf4j.org
 *
 * Autores: Agner Gerï¿½nimo Munhoz, 
 *          Pablo Henrique Fassina, 
 *          Rafael Pasqualini de Freitas,
 *          Wellington Carvalho
 *
 * Vocï¿½ pode obter a ï¿½ltima versï¿½o desse arquivo na pagina do Ecf4J.org
 * disponï¿½vel em: <http://www.ecf4j.org> 21/09/2009.
 *
 * Este arquivo ï¿½ parte da framework Ecf4J
 *
 * Ecf4J ï¿½ um framework livre; vocï¿½ pode redistribui-lo e/ou 
 * modifica-lo dentro dos termos da Licenï¿½a Pï¿½blica Geral Menor GNU como 
 * publicada pela Fundaï¿½ï¿½o do Software Livre (FSF); na versï¿½o 2.1 da 
 * Licenï¿½a.
 *
 * Este framework ï¿½ distribuido na esperanï¿½a que possa ser  util, 
 * mas SEM NENHUMA GARANTIA; sem uma garantia implicita de ADEQUAï¿½ï¿½O a qualquer
 * MERCADO ou APLICAï¿½ï¿½O EM PARTICULAR. Veja a
 * Licenï¿½a Pï¿½blica Geral GNU para maiores detalhes.
 *
 * Vocï¿½ deve ter recebido uma cï¿½pia da Licenï¿½a Pï¿½blica Geral Menor GNU
 * junto com este framework, se nï¿½o, escreva para a Fundaï¿½ï¿½o do Software
 * Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.ecf4j.ecnf.bematech;

import java.math.BigDecimal;

import org.ecf4j.ecnf.EcnfAbstract;
import org.ecf4j.ecnf.EcnfException;
import org.ecf4j.utils.bigdecimals.BigDecimalUtils;
import org.ecf4j.utils.bytes.ByteUtils;
import org.ecf4j.utils.comm.exceptions.CommException;
import org.ecf4j.utils.integers.IntegerUtils;
import org.ecf4j.utils.strings.StringUtils;

/**
 * Classe Abstrata de Bematech ECNF
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 * @extends EcnfAbstract
 * @see EcnfAbstract
 */
public abstract class EcnfBematechAbstract extends EcnfAbstract {

	@Override
	public void inicializar(String porta) throws CommException {
		inicializacao(porta, 9600, 8, 0, 1);	
	}
	
	@Override
	public void finalizar() {
		finalizacao();
		
	}

	@Override
	protected void executaComando(byte[] cmd) throws CommException {
		comm.write(cmd);	
	}

	@Override
	protected byte[] getRetorno(int len) throws CommException {
		byte[] retorno = comm.readDireto();
		return retorno;
	}

	@Override
	protected byte[] preparaComando(byte[] cmd, byte[] prm) {
		return ByteUtils.encondeByteArray(cmd, prm);
	}

	@Override
	protected void verificaRetorno() throws CommException {
		getRetorno(0);
		
	}

	@Override
	public String fabricante() {
		return "Bematech";
	}
	
	
	
	
	
	//--------------------------------------------------------------------------
	//IMPRESSÃO
	/* Implementado nas classes específicas
	@Override
	protected void imprimirLinhaAbstract(String linha) throws CommException {
		String ss[] = linha.split("\n");
		int len = 48;
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
	*/
	
	/* Implementado nas classes específicas
	@Override
	protected void imprimirLinhaExpandidaAbstract(String linha) throws CommException {
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
	*/
	
	public String alinhaItem(String str1, String str2, int dif){
		str1 = StringUtils.formatStrLimite(str1, str1.length() + dif, ' ', StringUtils.ALINHAMENTO_ESQUERDA);		
		return str1 + str2;
	}
	
	/*Implementado nas classes específicas 
	public void imprimirItem(int numItem, String codProduto, String descProduto, 
			String un, BigDecimal qtdeItem, BigDecimal valorItem) throws CommException{
		String item = IntegerUtils.intToStr(numItem, 3) + " ";
		String qtde = BigDecimalUtils.qtdeToString(qtdeItem) + " ";
		String valor = BigDecimalUtils.moedaToString(valorItem);
		if (!un.equals("")) {
			un = StringUtils.limiteStr(un, 2) + "x";
		}
		String produto = codProduto+" "+descProduto + " ";
		int len = 4 + qtde.length()+valor.length() + un.length() + produto.length();	
		
		
		if (len <= 48){
			imprimirLinha(alinhaItem(item+produto, un+qtde+valor, 48 - len));	
		} else {
			imprimirLinhaCondensada(alinhaItem(item+produto, un+qtde+valor, 60 - len));	
		}		
	}
	*/
	
	/* Implementado nas classes específicas
	public void imprimirLinha(String str1, String str2) throws CommException{
		
	}
	*/
	
	/* Implementado nas classes específicas
	public void imprimirLinhaExpandida(String str1, String str2) throws CommException{
		int len = 24 - str1.length();
		str2 = StringUtils.formatStr(str2, len, StringUtils.ALINHAMENTO_DIREITA);
		imprimirLinhaExpandida(str1+str2);
	}
	*/
	
	/* Implementado nas classes específicas
	@Override
	public void imprimirSeparador() throws CommException {
		//imprimirLinhaAbstract("                                                ");	
		imprimirLinhaAbstract(StringUtils.formatStr("-", 48, '-', StringUtils.ALINHAMENTO_ESQUERDA));	
	}
	*/

	//--------------------------------------------------------------------------
	//CONFIGURAÇÃO
	@Override
	protected void ativarItalicoAbstract() throws CommException {
		executaComando(preparaComando(ByteUtils.newByteArray(27,52)));
	}
	
	/* Implementado nas classes específicas
	@Override
	protected void desativarItalicoAbstract() throws CommException{
		executaComando(preparaComando(ByteUtils.newByteArray(27,53)));
	}
	*/
	
	@Override
	protected void ativarSublinhadoAbstract() throws CommException{
		executaComando(preparaComando(ByteUtils.newByteArray(27,45,1)));		
	}
	
	@Override
	protected void desativarSublinhadoAbstract() throws CommException{
		executaComando(preparaComando(ByteUtils.newByteArray(27,45,0)));		
	}
	
	@Override
	protected void ativarNegritoAbstract() throws CommException{
		executaComando(preparaComando(ByteUtils.newByteArray(27,69)));		
	}

	/* Implementado nas classes específicas
	@Override
	protected void desativarNegritoAbstract() throws CommException{
		executaComando(preparaComando(ByteUtils.newByteArray(27,70)));		
	}
	*/
	
	/* Implementado nas classes específicas
	@Override
	public void ativarModoNormal() throws CommException{
		executaComando(preparaComando(ByteUtils.newByteArray(27,77)));		
	}
	*/
	
	@Override
	public void ativarModoExpandido() throws CommException{
		executaComando(preparaComando(ByteUtils.newByteArray(27,14)));		
	}
	
	@Override
	public void ativarModoCondensado() throws CommException{
		executaComando(preparaComando(ByteUtils.newByteArray(27,15)));
	}
	
	//----------------------------------------------------------------
	/* Implementado nas classes específicas
	@Override
	public void abrirGaveta() throws CommException {
		executaComando(preparaComando(ByteUtils.newByteArray(27,118, 100)));		
	}
	*/

	@Override
	public boolean isGavetaAberta() throws CommException, EcnfException {
		//executaComando(preparaComando(ByteUtils.newByteArray(27,98,50)));
		//verificaRetorno();
		return false;
	}

	@Override
	public void pularLinha() throws CommException {
		executaComando(preparaComando(("\n").getBytes()));;
	}
	//----------------------------------------------------------------		
	
}
