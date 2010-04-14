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
	
	protected void finalizacao(){
		comm.fechar();
	}
	
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

	public int getAlinhamento() {
		return alinhamento;
	}

	public void setAlinhamento(int alinhamento) {
		this.alinhamento = alinhamento;
	}

	public boolean isSublinhado() {
		return sublinhado;
	}

	public void setSublinhado(boolean sublinhado) {
		if (sublinhado){
			ativarSublinhado();
		}else{
			desativarSublinhado();
		}
	}

	public boolean isItalico() {
		return italico;
	}

	public void setItalico(boolean italico) {
		if (italico){
			ativarItalico();
		}else{
			desativarItalico();
		}
	}

	public boolean isNegrito() {
		return negrito;
	}

	public void setNegrito(boolean negrito) {
		if (negrito){
			ativarNegrito();
		}else{
			desativarNegrito();
		}
	}

	public void ativarItalico(){
		try {
			ativarItalicoAbstract();
			this.italico = true;
		} catch (CommException e) {
			e.printStackTrace();
		}
	}
	
	public void desativarItalico(){
		try {
			desativarItalicoAbstract();
			this.italico = false;		
		} catch (CommException e) {
			e.printStackTrace();
		}
	}
	
	public void ativarSublinhado(){
		try {
			ativarSublinhadoAbstract();
			this.sublinhado = true;
		} catch (CommException e) {
			e.printStackTrace();
		}	
	}
	
	public void desativarSublinhado(){
		try {
			desativarSublinhadoAbstract();
			this.sublinhado = false;
		} catch (CommException e) {
			e.printStackTrace();
		}		
	}
	
	public void ativarNegrito(){
		try {
			ativarNegritoAbstract();
			this.negrito = true;
		} catch (CommException e) {
			e.printStackTrace();
		}
	}
	
	public void desativarNegrito(){
		try {
			desativarNegritoAbstract();
			this.negrito = false;
		} catch (CommException e) {
			e.printStackTrace();
		}
	}
	public void imprimirLinhaExpandida(String linha) throws CommException{
		if(linha.trim().equals("")){
			pularLinha();
		}else{
			imprimirLinhaExpandidaAbstract(linha);
		}
	}
	public void imprimirLinhaCondensada(String linha) throws CommException{
		if(linha.trim().equals("")){
			pularLinha();
		}else{
			imprimirLinhaCondensadaAbstract(linha);
		}
	}
	public void imprimirLinha(String linha) throws CommException{
		if(linha.trim().equals("")){
			pularLinha();
		}else{
			imprimirLinhaAbstract(linha);
		}
	}
	
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
	
	public void avancarLinhas() throws CommException{
		pularLinha(cupomConfig.getLinhasAvanco());
	}
	
	public void pularLinha(int numLinhas) throws CommException{
		for(int i = 0; i < numLinhas; i ++){
			pularLinha();
		}
	}
	
	public boolean isCommEnabled() {
		return commEnabled;
	}
	
	protected byte[] preparaComando(byte[] cmd){
		return preparaComando(cmd, "");
	}
	protected byte[] preparaComando(byte[] cmd, String prm){
		return preparaComando(cmd, prm.getBytes());
	}

	protected abstract byte[] preparaComando(byte[] cmd, byte[] prm);
	protected abstract void verificaRetorno() throws CommException;
	protected abstract byte[] getRetorno(int len) throws CommException;
	protected abstract void executaComando(byte[] cmd) throws CommException;
	
	public abstract String fabricante();
	public abstract String modelo() throws EcnfException;
	public abstract void inicializar(String porta) throws CommException;
	public abstract void finalizar();
	
	//Impressão------------------------------------------------------------
	protected abstract void imprimirLinhaAbstract(String linha) throws CommException;
	protected abstract void imprimirLinhaExpandidaAbstract(String linha) throws CommException;
	protected abstract void imprimirLinhaCondensadaAbstract(String linha) throws CommException;
	public abstract void imprimirEan13(String ean) throws CommException, EcnfException;
	public abstract void imprimirEan8(String ean) throws CommException, EcnfException;
	public abstract String alinhaItem(String str1, String str2, int dif);
	public abstract void imprimirItem(int numItem, String codProduto, String descProduto, 
			String un, BigDecimal qtdeItem, BigDecimal valorItem) throws CommException;
	public abstract void imprimirLinha(String str1, String str2) throws CommException;
	public abstract void imprimirLinhaExpandida(String str1, String str2) throws CommException;
	public abstract void imprimirSeparador() throws CommException;

	//Configuração----------------------------------------------------------
	protected abstract void ativarItalicoAbstract() throws CommException;
	protected abstract void desativarItalicoAbstract() throws CommException;
	protected abstract void ativarSublinhadoAbstract() throws CommException;
	protected abstract void desativarSublinhadoAbstract() throws CommException;
	protected abstract void ativarNegritoAbstract() throws CommException;
	protected abstract void desativarNegritoAbstract() throws CommException;
	public abstract void ativarModoNormal() throws CommException;
	public abstract void ativarModoCondensado() throws CommException;
	public abstract void ativarModoExpandido() throws CommException;
	
	//-----------------------------------------------------------------------
	public abstract void cortarPapel() throws CommException;
	public abstract void cortarParcialmentePapel() throws CommException, EcnfException;
	public abstract void abrirGaveta() throws CommException;
	public abstract boolean isGavetaAberta() throws CommException, EcnfException;
	public abstract void pularLinha() throws CommException;
	public abstract String getEstado()  throws CommException;
	public abstract Integer getNumColunas();
	

}
