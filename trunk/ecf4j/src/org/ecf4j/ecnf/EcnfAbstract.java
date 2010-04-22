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

import java.math.BigDecimal;

import org.ecf4j.utils.comm.CommEquipto;
import org.ecf4j.utils.comm.exceptions.CommException;
import org.ecf4j.utils.files.FileUtils;
import org.ecf4j.utils.strings.StringUtils;

import com.thoughtworks.xstream.XStream;

/**
 * Classe abstrata do ECNF
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 * @extends CommEquipto
 * @see CommEquipto
 */
public abstract class EcnfAbstract extends CommEquipto {
		
	protected boolean commEnabled = false;
	protected int alinhamento = StringUtils.ALINHAMENTO_ESQUERDA;
	protected boolean sublinhado = false;
	protected boolean italico = false;
	protected boolean negrito = false;
	protected CupomConfig cupomConfig = null;
	
	/*
	public EcnfAbstract(String porta, Integer velocidade, Integer bitsDados,
			Integer paridade, Integer bitsParada){
		try {
			inicializacao(porta, velocidade, bitsDados, paridade, bitsParada);
		} catch (CommException e) {
			e.printStackTrace();
		}	
	}
	*/

	/**
	 * Método inicializa a comunicação com o Ecnf
	 */
	protected void inicializacao(String porta, Integer velocidade, Integer bitsDados,
			Integer paridade, Integer bitsParada) throws CommException{
		comm.abrirPorta(porta, velocidade, bitsDados, paridade, bitsParada);
		commEnabled = true;
		ativarModoNormal();
		desativarItalico();
		desativarNegrito();
		desativarSublinhado();
		carregarCupomConfig();
	}
	
	/**
	 * Método finaliza comunicação com o Ecnf
	 */
	protected void finalizacao(){
		comm.fechar();
	}
	
	/**
	 * Método carrega as configurações do cupom. Essas confurações saõ carregadas a partir de um xml encontrado no mesmo diretório do pacote ecf4j.jar com o nome de EcnfCupomConfig.xml 
	 */
	public void carregarCupomConfig(){
		cupomConfig = null;
		try {
			String arq = FileUtils.carregar("EcnfCupomConfig.xml");
			if (arq != null){
				XStream xStream = new XStream();
				cupomConfig = (CupomConfig)xStream.fromXML(arq);	
			}
		} catch (Exception e) {
			cupomConfig = null;
			e.printStackTrace();
		}
		if (cupomConfig == null){
			cupomConfig = new CupomConfig();
			cupomConfig.initDefaultValues();
			XStream xStream = new XStream();
			String arq = xStream.toXML(cupomConfig);
			try {
				FileUtils.salvar("EcnfCupomConfig.xml", arq, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Método carrega configurações do cupom a partir de um xml passado por parâmetro
	 * @param xml <i>String</i>
	 */
	public void carregarCupomConfig(String xml){
		cupomConfig = null;
		try {
			if (xml != null){
				XStream xStream = new XStream();
				cupomConfig = (CupomConfig)xStream.fromXML(xml);	
			}
		} catch (Exception e) {
			cupomConfig = null;
			e.printStackTrace();
		}
		if (cupomConfig == null){
			carregarCupomConfig();
		}
	}

	/**
	 * Método busca tipo de alinhamento do cupom não fiscal
	 * @return Tipo de de alinhamento 
	 */
	public int getAlinhamento() {
		return alinhamento;
	}

	/**
	 * Métoda seta tipo de alinhamento
	 * @param alinhamento <i>int</i>
	 */
	public void setAlinhamento(int alinhamento) {
		this.alinhamento = alinhamento;
	}

	/**
	 * Método verifica se impressão está setada para ser sublinhada ou não
	 * @return boolean
	 * <li> True - impressão sublinhada
	 * <li> False - impressão normal
	 */ 
	public boolean isSublinhado() {
		return sublinhado;
	}

	/**
	 * Método seta impressão sublinhada ou não
	 * @param sublinhado <i>boolean</i>
	 */
	public void setSublinhado(boolean sublinhado) {
		if (sublinhado){
			ativarSublinhado();
		}else{
			desativarSublinhado();
		}
	}

	/**
	 * Método verifica se impressão está setada para ser em modo itálico ou não
	 * @return boolean
	 * <li> True - Modo itálico "on"
	 * <li> False - Modo itálico "off"
	 */
	public boolean isItalico() {
		return italico;
	}

	/**
	 * Método seta impressão em modo itálico ou não
	 * @param italico <i>boolean</i>
	 */
	public void setItalico(boolean italico) {
		if (italico){
			ativarItalico();
		}else{
			desativarItalico();
		}
	}

	/**
	 * Método verifica se impressão está setada para ser em modo negrito ou não
	 * @return boolean
	 * <li> True - Modo negrito "on"
	 * <li> False - Modo negrito "off"
	 */
	public boolean isNegrito() {
		return negrito;
	}

	/**
	 * Método seta impressão em modo negrito ou não
	 * @param negrito <i>boolean</i>
	 */
	public void setNegrito(boolean negrito) {
		if (negrito){
			ativarNegrito();
		}else{
			desativarNegrito();
		}
	}

	/**
	 * Método força a ativação do modo itálico
	 */
	public void ativarItalico(){
		try {
			ativarItalicoAbstract();
			this.italico = true;
		} catch (CommException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método força a desativação do modo itálico
	 */
	public void desativarItalico(){
		try {
			desativarItalicoAbstract();
			this.italico = false;		
		} catch (CommException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método força a ativação do modo sublinhado
	 */
	public void ativarSublinhado(){
		try {
			ativarSublinhadoAbstract();
			this.sublinhado = true;
		} catch (CommException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Método força a desativação do modo sublinhado
	 */
	public void desativarSublinhado(){
		try {
			desativarSublinhadoAbstract();
			this.sublinhado = false;
		} catch (CommException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Método força a ativação do modo negrito
	 */
	public void ativarNegrito(){
		try {
			ativarNegritoAbstract();
			this.negrito = true;
		} catch (CommException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método força a desativação do modo negrito
	 */
	public void desativarNegrito(){
		try {
			desativarNegritoAbstract();
			this.negrito = false;
		} catch (CommException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método imprime linha em modo expandido
	 * @param linha <i>String</i>
	 * @throws CommException Exceção de comunicação
	 */
	public void imprimirLinhaExpandida(String linha) throws CommException{
		if(linha.trim().equals("")){
			pularLinha();
		}else{
			imprimirLinhaExpandidaAbstract(linha);
		}
	}
	
	/**
	 * Método imprime linha em modo condensado
	 * @param linha <i>String</i>
	 * @throws CommException Exceção de comunicação
	 */
	public void imprimirLinhaCondensada(String linha) throws CommException{
		if(linha.trim().equals("")){
			pularLinha();
		}else{
			imprimirLinhaCondensadaAbstract(linha);
		}
	}
	
	/**
	 * Método imprime linha me modo normal
	 * @param linha <i>String</i>
	 * @throws CommException Exceção de comunicação
	 */
	public void imprimirLinha(String linha) throws CommException{
		if(linha.trim().equals("")){
			pularLinha();
		}else{
			imprimirLinhaAbstract(linha);
		}
	}
	
	/**
	 * Método imprime cabeçãlho de comprovante
	 * @throws CommException Exceção de comunicação
	 */
	public void imprimirCabecalho() throws CommException {
		if (cupomConfig != null){
			boolean sublinhado = this.sublinhado;
			boolean negrito = this.negrito;
			boolean italico = this.italico;
			int alinhamento = this.alinhamento;
			try{
				for(LinhaFormatada l : cupomConfig.getCabecalho()){
					//Alinhamento
					if (l.getAlinhamento().equalsIgnoreCase("CENTRO")){
						setAlinhamento(StringUtils.ALINHAMENTO_CENTRALIZADO);
					}else{
						if (l.getAlinhamento().equalsIgnoreCase("DIREITA")){
							setAlinhamento(StringUtils.ALINHAMENTO_DIREITA);
						}else{
							setAlinhamento(StringUtils.ALINHAMENTO_ESQUERDA);
						}
					}
					setItalico(l.isItalico());
					setSublinhado(l.isSublinhado());
					setNegrito(l.isNegrito());
					//Modo de Imprissão
					if (l.getModo().equalsIgnoreCase("EXPANDIDO")){
						imprimirLinhaExpandida(l.getTexto());
					}else{
						if (l.getModo().equalsIgnoreCase("CONDENSADO")){
							imprimirLinhaCondensada(l.getTexto());
						}else{
							imprimirLinha(l.getTexto());
						}
					}
				}	
			}finally{
				setAlinhamento(alinhamento);
				setSublinhado(sublinhado);
				setNegrito(negrito);
				setItalico(italico);
			}
			
		}
	}

	/**
	 * Método imprime radapé de comprovante
	 * @throws CommException Exceção de comunicação
	 */
	public void imprimirRodape() throws CommException{
		boolean sublinhado = this.sublinhado;
		boolean negrito = this.negrito;
		boolean italico = this.italico;
		int alinhamento = this.alinhamento;
		try{
			for(LinhaFormatada l : cupomConfig.getRodape()){
				//Alinhamento
				if (l.getAlinhamento().equalsIgnoreCase("CENTRO")){
					setAlinhamento(StringUtils.ALINHAMENTO_CENTRALIZADO);
				}else{
					if (l.getAlinhamento().equalsIgnoreCase("DIREITA")){
						setAlinhamento(StringUtils.ALINHAMENTO_DIREITA);
					}else{
						setAlinhamento(StringUtils.ALINHAMENTO_ESQUERDA);
					}
				}
				setItalico(l.isItalico());
				setSublinhado(l.isSublinhado());
				setNegrito(l.isNegrito());
				//Modo de Imprissão
				if (l.getModo().equalsIgnoreCase("EXPANDIDO")){
					imprimirLinhaExpandida(l.getTexto());
				}else{
					if (l.getModo().equalsIgnoreCase("CONDENSADO")){
						imprimirLinhaCondensada(l.getTexto());
					}else{
						imprimirLinha(l.getTexto());
					}
				}
			}	
		}finally{
			setAlinhamento(alinhamento);
			setSublinhado(sublinhado);
			setNegrito(negrito);
			setItalico(italico);
		}
		
	}
	
	/**
	 * Método avança o número de linhas especificada na configuração do cupom
	 * @throws CommException Exceção de comunicação
	 */
	public void avancarLinhas() throws CommException{
		pularLinha(cupomConfig.getLinhasAvanco());
	}
	
	/**
	 * Método pula o número de linhas que for passado por parâmetro
	 * @param numLinhas <i>int</i>
	 * @throws CommException Exceção de comunicação
	 */
	public void pularLinha(int numLinhas) throws CommException{
		for(int i = 0; i < numLinhas; i ++){
			pularLinha();
		}
	}
	
	/**
	 * Método verifica se a porta informada na inicialização esta habilitada
	 * @return booleaan
	 * <li> True - Porta habilitada
	 * <li> False - Porta desabilitada
	 */
	public boolean isCommEnabled() {
		return commEnabled;
	}
	
	/**
	 * Método formata comando para que o mesmo possa ser enviado ao equipamento
	 * @param cmd <i>byte[]</i>
	 * @return Comando pronto para ser enviado para o equipamento
	 */
	protected byte[] preparaComando(byte[] cmd){
		return preparaComando(cmd, "");
	}
	/**
	 * Método formata comando para que o mesmo possa ser enviado ao equipamento
	 * @param cmd <i>byte[]</i>
	 * @param prm <i>String</i>
	 * @return Comando formatado
	 */
	protected byte[] preparaComando(byte[] cmd, String prm){
		return preparaComando(cmd, prm.getBytes());
	}

	/**
	 * Método abstrato formata comandos para serem enviados para o equipamento
	 * @param cmd <i>byte[]</i>
	 * @param prm <i>byte[]</i>
	 * @return Comando formatado
	 */
	protected abstract byte[] preparaComando(byte[] cmd, byte[] prm);
	/**
	 * Método abstrato verifica retorno da impressora não fiscal
	 * @throws CommException Exceção de comunicação
	 */
	protected abstract void verificaRetorno() throws CommException;
	/**
	 * Método abstrato busca retorno da impressora não fiscal
	 * @param len <i>int</i>
	 * @return Resposta a algum comando enviado ao equipamento
	 * @throws CommException Exceção de comunicação
	 */
	protected abstract byte[] getRetorno(int len) throws CommException;
	/**
	 * Método abstrato envia comando ao equipamento
	 * @param cmd <i>cmd</i>
	 * @throws CommException Exceção de comunicação
	 */
	protected abstract void executaComando(byte[] cmd) throws CommException;
	
	/**
	 * Método abstrato retorna o fabricante da impressora não fiscal
	 * @return Fabricante do equipamento
	 */
	public abstract String fabricante();
	/**
	 * Método abstrato busca o modelo da impressora não fiscal
	 * @return Modelo do equipamento
	 * @throws EcnfException Exceção do Ecnf
	 */
	public abstract String modelo() throws EcnfException;
	/**
	 * Método abstrato de inicialização do Ecnf
	 * @param porta <i>String</i>
	 * @throws CommException Exceção de comunicação
	 */
	public abstract void inicializar(String porta) throws CommException;
	/**
	 * Método abstrato de finalização de comunicação com o Ecnf
	 */
	public abstract void finalizar();
	
	
	//Impressão------------------------------------------------------------
	/**
	 * Método abstrato para impressão de linha no Ecnf
	 */
	protected abstract void imprimirLinhaAbstract(String linha) throws CommException;
	
	/**
	 * Método abstrato para impressão de linha expandida no Ecnf
	 * @param linha <i>String</i>
	 * @throws CommException Exceção de comunicação
	 */
	protected abstract void imprimirLinhaExpandidaAbstract(String linha) throws CommException;
	
	/**
	 * Método abstrato para impressão de linha condensada no Ecnf
	 * @param linha <i>String</i>
	 * @throws CommException Exceção de comunicaçã
	 */
	protected abstract void imprimirLinhaCondensadaAbstract(String linha) throws CommException;
	
	/**
	 * Método abstrato imprime um código de barras do tipo EAN13
	 * @param ean <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcnfException Exceção do Ecnf
	 */
	public abstract void imprimirEan13(String ean) throws CommException, EcnfException;
	
	/**
	 * Método abstrato imprime um código de barras do tipo EAN8
	 * @param ean <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcnfException Exceção do Ecnf
	 */
	public abstract void imprimirEan8(String ean) throws CommException, EcnfException;
	
	/**
	 * Método abstrato alinha item a ser impresso em um cupom
	 * @param str1 <i>String</i>
	 * @param str2 <i>String</i>
	 * @param dif <i>dif</i>
	 * @return linha formatada para impressão
	 */
	public abstract String alinhaItem(String str1, String str2, int dif);
	
	/**
	 * Método abstratato para impressão de item
	 * @param numItem <i>int</i>
	 * @param codProduto <i>String</i>
	 * @param descProduto <i>String</i>
	 * @param un <i>Strinf</i>
	 * @param qtdeItem <i>BigDecimal</i>
	 * @param valorItem <i>BigDecimal</i>
	 * @throws CommException Exceção de comunicação
	 */
	public abstract void imprimirItem(int numItem, String codProduto, String descProduto, 
			String un, BigDecimal qtdeItem, BigDecimal valorItem) throws CommException;
	
	/**
	 * Método abstrato imprime uma linha
	 * @param str1 <i>String</i>
	 * @param str2 <i>String</i>
	 * @throws CommException Exceção de comunicação
	 */
	public abstract void imprimirLinha(String str1, String str2) throws CommException;
	
	/**
	 * Método abstrato imprime uma linha em modo expandido
	 * @param str1 <i>String</i>
	 * @param str2 <i>String</i>
	 * @throws CommException Exceção de comunicação
	 */
	public abstract void imprimirLinhaExpandida(String str1, String str2) throws CommException;
	
	/**
	 * Método abstrato imprime um separador entre linhas
	 * @throws CommException Exceção de comunicação
	 */
	public abstract void imprimirSeparador() throws CommException;

	
	//Configuração----------------------------------------------------------
	/**
	 *Método abstrato força a ativação do modo itálico 
	 */
	protected abstract void ativarItalicoAbstract() throws CommException;
	
	/**
	 * Método abstrato força a desativação do modo itálico
	 * @throws CommException Exceção de comunicação
	 */
	protected abstract void desativarItalicoAbstract() throws CommException;
	
	/**
	 * Método abstrato que força a ativação do modo sublinhado
	 * @throws CommException Exceção de comunicação
	 */
	protected abstract void ativarSublinhadoAbstract() throws CommException;
	
	/**
	 * Método abstrato que força a desativação do modo sublinhado
	 * @throws CommException Exceção de comunicação
	 */
	protected abstract void desativarSublinhadoAbstract() throws CommException;
	
	/**
	 * Método abstrato que força a ativação do modo sublinhado
	 * @throws CommException Exceção de comunicação
	 */
	protected abstract void ativarNegritoAbstract() throws CommException;
	
	/**
	 * Método abstrato que força a desativação do modo sublinhado
	 * @throws CommException Exceção do comunicação
	 */
	protected abstract void desativarNegritoAbstract() throws CommException;
	
	/**
	 * Método abstrato que força a ativação do modo Normal
	 * @throws CommException Exceção de comunicação
	 */
	public abstract void ativarModoNormal() throws CommException;
	
	/**
	 * Método abstrato que força a ativação do modo condensado
	 * @throws CommException Exceção de comunicação
	 */
	public abstract void ativarModoCondensado() throws CommException;
	
	/**
	 * Método abstrato que força a ativação do modo expandido
	 * @throws CommException Exceção de comunicação
	 */
	public abstract void ativarModoExpandido() throws CommException;
	
	//-----------------------------------------------------------------------
	/**
	 * Método abstrato 	que corta o papel do Ecnf
	 * @throws CommException Exceção de comunicação
	 */
	public abstract void cortarPapel() throws CommException;
	
	/**
	 * Método abstrato corta parcialmente o papel do Ecnf
	 * @throws CommException Exceção de comunicação
	 * @throws EcnfException Exceção do Ecnf
	 */
	public abstract void cortarParcialmentePapel() throws CommException, EcnfException;
	
	/**
	 * Método absrato abre a gaveta conectada ao Ecnf
	 * @throws CommException - Exceção de comunicação
	 */
	public abstract void abrirGaveta() throws CommException;
	
	/**
	 * Método abstrato verifica se a gaveta está aberta
	 * @return boolean
	 * <li> True - Gaveta aberta
	 * <li> False - Gaveta fechada
	 * @throws CommException Exceção de comunicação
	 * @throws EcnfException Exceção do Ecnf
	 */
	public abstract boolean isGavetaAberta() throws CommException, EcnfException;
	
	/**
	 * Método abstrato pula uma linha na impressão
	 * @throws CommException - Exceção de comunicação
	 */
	public abstract void pularLinha() throws CommException;
	
	/**
	 * método abstrato busca o estado do Ecnf
	 * @return Estado do ecnf
	 * @throws CommException Exceção de comunicação
	 */
	public abstract String getEstado()  throws CommException;
	
	/**
	 * Método abstrato retorna o número de colunas do Ecnf
	 * @return Número de colunas
	 */
	public abstract Integer getNumColunas();
	

}
