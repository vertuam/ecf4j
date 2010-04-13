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
package org.ecf4j;

import java.math.BigDecimal;

import org.ecf4j.ecnf.EcnfAbstract;
import org.ecf4j.ecnf.EcnfException;
import org.ecf4j.utils.Ecf4jFactory;
import org.ecf4j.utils.comm.exceptions.CommException;

/**
 * Classe controladora do ECNF
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 */
public class Ecnf {
	
	private EcnfAbstract ecnf = null;

	/**
	 * Método inicializa comunicação com ECNF
	 * @param codigo <i>String</i>
	 * @param porta <i>String</i>
	 * @throws EcnfException Exceção do ECNF
	 * @throws CommException Exceção do comunicação
	 */
	public void inicilizar(String codigo, String porta) throws EcnfException, CommException{
		try{
			ecnf = Ecf4jFactory.createEcnf(codigo);
			System.out.println(ecnf.modelo());
			ecnf.inicializar(porta);
		}catch(NullPointerException e){
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método finaliza comunicação com ECNF
	 */
	public void finalizar(){
		ecnf.finalizar();
		ecnf = null;
	}
	
	/**
	 * Método carrega configurações do cupom
	 * @throws EcnfException Exceção do ECNF
	 */
	public void carregarCupomConfig() throws EcnfException{
		try {
			ecnf.carregarCupomConfig();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}		
	}
	
	/**
	 * Método carrega as configurações do cupom a partir de um XML
	 * @param xml <i>String</i>
	 * @throws EcnfException Exceção do ECNF
	 */
	public void carregarCupomConfig(String xml) throws EcnfException{
		try {
			ecnf.carregarCupomConfig(xml);
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método retorna o alinhamento utilizado
	 * @return alinhamento
	 * @throws EcnfException Exceção do ECNF
	 */
	public int getAlinhamento() throws EcnfException {
		try {
			return ecnf.getAlinhamento();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}

	/**
	 * Método seta um novo alinhamento a ser utilizado pela ECNF
	 * @param alinhamento <i>int</i>
	 * @throws EcnfException Exceção do ECNF
	 */
	public void setAlinhamento(int alinhamento) throws EcnfException {
		try {
			ecnf.setAlinhamento(alinhamento);
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}

	/**
	 * Método verifica se texto a ser impresso será sublinhado
	 * @return boolean
	 * <li> True - Caso esteja sublinhado
	 * <li> False - Caso não esteja sublinhado
	 * @throws EcnfException Exceção do ECNF
	 */
	public boolean isSublinhado() throws EcnfException {
		try {
			return ecnf.isSublinhado();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}

	/**
	 * Método seta se o texto a ser impresso será sublinhado ou não
	 * @param sublinhado <i>boolean</i>
	 * @throws EcnfException Exceção do ECNF
	 */
	public void setSublinhado(boolean sublinhado) throws EcnfException {
		try {
			ecnf.setSublinhado(sublinhado);
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}

	/**
	 * Método verifica se texto a ser impresso será itálico
	 * @return boolean
	 * <li> True - Caso esteja itálico
	 * <li> False - Caso não esteja itálico
	 * @throws EcnfException Exceção do ECNF
	 */
	public boolean isItalico() throws EcnfException {
		try {
			return ecnf.isItalico();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}

	/**
	 *  Método seta se o texto a ser impresso será itálico ou não
	 * @param italico <i>boolean</i>
	 * @throws EcnfException Exceção do ECNF
	 */
	public void setItalico(boolean italico) throws EcnfException {
		try {
			ecnf.setItalico(italico);
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}

	/**
	 * Método verifica se texto a ser impresso será negrito
	 * @return boolean
	 * <li> True - Caso esteja negrito
	 * <li> False - Caso não esteja negrito
	 * @throws EcnfException Exceção do ECNF
	 */
	public boolean isNegrito() throws EcnfException {
		try {
			return ecnf.isNegrito();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}

	/**
	 *  Método seta se o texto a ser impresso será negrito ou não
	 * @param negrito <i>boolean</i>
	 * @throws EcnfException Exceção do ECNF
	 */
	public void setNegrito(boolean negrito) throws EcnfException {
		try {
			ecnf.setNegrito(negrito);
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}

	/**
	 * Método força a ativação do modo itálico
	 * @throws EcnfException Exceção do ECNF
	 */
	public void ativarItalico() throws EcnfException{
		try {
			ecnf.ativarItalico();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método força a desativação do modo itálico
	 * @throws EcnfException Exceção do ECNF
	 */
	public void desativarItalico() throws EcnfException{
		try {
			ecnf.desativarItalico();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método força a ativação do modo sublinhado
	 * @throws EcnfException Exceção do ECNF
	 */
	public void ativarSublinhado() throws EcnfException{
		try {
			ecnf.ativarSublinhado();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}	
	}
	
	/**
	 * Método força a desativação do modo sublinhado
	 * @throws EcnfException Exceção do ECNF
	 */
	public void desativarSublinhado() throws EcnfException{
		try {
			ecnf.desativarSublinhado();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}	
	}
	
	/**
	 * Método força a ativação do modo negrito
	 * @throws EcnfException Exceção do ECNF
	 */
	public void ativarNegrito() throws EcnfException{
		try {
			ecnf.ativarNegrito();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método força a desativação do modo negrito
	 * @throws EcnfException Exceção do ECNF
	 */
	public void desativarNegrito() throws EcnfException{
		try {
			ecnf.desativarNegrito();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método imprime uma linha no modo expandido
	 * @param linha <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcnfException Exceção do ECNF
	 */
	public void imprimirLinhaExpandida(String linha) throws CommException, EcnfException{
		try {
			ecnf.imprimirLinhaExpandida(linha);
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método imprime uma linha no modo condensado
	 * @param linha <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcnfException Exceção do ECNF
	 */
	public void imprimirLinhaCondensada(String linha) throws CommException, EcnfException{
		try {
			ecnf.imprimirLinhaCondensada(linha);
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método imprime uma linha
	 * @param linha <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcnfException Exceção do ECNF
	 */
	public void imprimirLinha(String linha) throws CommException, EcnfException{
		try {
			ecnf.imprimirLinha(linha);
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método imprime cabeçalho do cupom
	 * @throws CommException Exceção de comunicação
	 * @throws EcnfException Exceção do ECNF
	 */
	public void imprimirCabecalho() throws CommException, EcnfException {
		try {
			ecnf.imprimirCabecalho();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}

	/**
	 * Método imprime rodapé do cupom
	 * @throws CommException Exceção de comunicação
	 * @throws EcnfException Exceção do ECNF
	 */
	public void imprimirRodape() throws CommException, EcnfException{
		try {
			ecnf.imprimirRodape();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}	
	}

	/**
	 * Método pula linhas nas impressão do ECNF
	 * @param numLinhas <i>int</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcnfException Exceção do ECNF
	 */
	public void avancarLinhas() throws CommException{
		ecnf.avancarLinhas();
	}
	
	public String alinhaItem(String str1, String str2, int dif){
		return ecnf.alinhaItem(str1, str2, dif);
	}
	
	public void imprimirItem(int numItem, String codProduto, String descProduto, 
			String un, BigDecimal qtdeItem, BigDecimal valorItem) throws EcnfException{
		try {
			ecnf.imprimirItem(numItem, codProduto, descProduto, un, qtdeItem, valorItem);
		} catch (CommException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	public void imprimirLinha(String str1, String str2)throws EcnfException, CommException{
		try {
			ecnf.imprimirLinha(str1, str2);
		} catch (CommException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}

	public void imprimirLinhaExpandida(String str1, String str2)throws EcnfException, CommException{
		try {
			ecnf.imprimirLinhaExpandida(str1, str2);
		} catch (CommException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	public void imprimirSeparador() throws CommException{
		ecnf.imprimirSeparador();
	}
	
	public void pularLinha(int numLinhas) throws CommException, EcnfException{
		try {
			ecnf.pularLinha(numLinhas);
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método verifica se a porta serial/paralela está habilitada
	 * @return boolean 
	 * <li> True - Porta habilitada
	 * <li> False - Porta desabilitada
	 * @throws EcnfException
	 */
	public boolean isCommEnabled() throws EcnfException {
		try {
			return ecnf.isCommEnabled();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca o fabricante do ECNF
	 * @return fabricante
	 * @throws EcnfException Exceção do ECNF
	 */
	public String fabricante() throws EcnfException{
		try {
			return ecnf.fabricante();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca o modelo do ECNF
	 * @return modelo
	 * @throws EcnfException Exceção do ECNF
	 */
	public String modelo() throws EcnfException{
		try {
			return ecnf.modelo();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método imprime codigos de barras modelo EAN13
	 * @param ean <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcnfException Exceção do ECNF
	 */
	public void imprimirEan13(String ean) throws CommException, EcnfException{
		try {
			ecnf.imprimirEan13(ean);
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método imprime codigos de barras modelo EAN8
	 * @param ean <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcnfException Exceção do ECNF
	 */
	public void imprimirEan8(String ean) throws CommException, EcnfException{
		try {
			ecnf.imprimirEan8(ean);
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método força a ativação do modo normal no tamanho da fonte de impressão
	 * @throws CommException Exceção de comunicação
	 * @throws EcnfException Exceção do ECNF
	 */
	public void ativarModoNormal() throws CommException, EcnfException{
		try {
			ecnf.ativarModoNormal();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método força a ativação do modo condensado no tamanho da fonte de impressão
	 * @throws CommException Exceção de comunicação
	 * @throws EcnfException Exceção do ECNF
	 */
	public void ativarModoCondensado() throws CommException, EcnfException{
		try {
			ecnf.ativarModoCondensado();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método força a ativação do modo expandido no tamanho da fonte de impressão
	 * @throws CommException Exceção de comunicação
	 * @throws EcnfException Exceção do ECNF
	 */
	public void ativarModoExpandido() throws CommException, EcnfException{
		try {
			ecnf.ativarModoExpandido();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método corta o papel integralmente no ECNF
	 * @throws CommException Exceção de comunicação
	 * @throws EcnfException Exceção do ECNF
	 */
	public void cortarPapel() throws CommException, EcnfException{
		try {
			ecnf.cortarPapel();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método corta o papel parcialmente no ECNF
	 * @throws CommException Exceção de comunicação
	 * @throws EcnfException Exceção do ECNF
	 */
	public void cortarParcialmentePapel() throws CommException, EcnfException{
		try {
			ecnf.cortarParcialmentePapel();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método abre a gaveta conectada no ECNF
	 * @throws CommException Exceção de comunicação
	 * @throws EcnfException Exceção do ECNF
	 */
	public void abrirGaveta() throws CommException, EcnfException{
		try {
			ecnf.abrirGaveta();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}	
	}
	
	/**
	 * Método verifica se a gaveta está aberta
	 * @return boolean
	 * <li> True - Caso a gaveta esteja aberta
	 * <li> False - Caso a gaveta estaja fechada
	 * @throws CommException
	 * @throws EcnfException
	 */
	public boolean isGavetaAberta() throws CommException, EcnfException{
		try {
			return ecnf.isGavetaAberta();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método pula uma linha na impressão do ECNF
	 * @throws CommException Exceção de comunicação
	 * @throws EcnfException Exceção do ECNF
	 */
	public void pularLinha() throws CommException, EcnfException{
		try {
			ecnf.pularLinha();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método verifica a quantidade de caracteres por linha no modelo de ECF  instanciado
	 * @return Número de colunas
	 */
	public Integer getNumColunas(){
		return ecnf.getNumColunas();
	}

}
