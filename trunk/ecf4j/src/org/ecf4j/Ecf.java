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
import java.util.Date;
import java.util.List;

import org.ecf4j.ecf.Aliquota;
import org.ecf4j.ecf.EcfAbstract;
import org.ecf4j.ecf.EcfEstado;
import org.ecf4j.ecf.FormaPagamento;
import org.ecf4j.ecf.LayoutCheque;
import org.ecf4j.ecf.TotalizadorNaoFiscal;
import org.ecf4j.ecf.exceptions.EcfException;
import org.ecf4j.utils.Ecf4jFactory;
import org.ecf4j.utils.comm.exceptions.CommException;


/** 
 * Classe ECF controladora
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 */
public class Ecf {
	
	private EcfAbstract ecf = null;
	
	public Ecf(){
		
	}
	
	/**
	 * Método de inicialização do ECF
	 * @param codigo Código do ECF
	 * @param porta Porta utilizada pelo ECF
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção da comunicação
	 */
	public void inicializar(String codigo, String porta) throws EcfException, CommException{
		try {
			ecf = Ecf4jFactory.createEcf(codigo);
			ecf.inicializar(porta);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	public void inicializar(EcfAbstract ecf, String porta) throws EcfException{
		this.ecf = ecf;
	}
		
	/**
	 * Método de finalização do ECF
	 */
	public void finalizar(){
		ecf.finalizar();
		ecf = null;
	}
	
	/**
	 * Método verifica se a porta esta habilitada
	 * @return boolean 
	 * <li> True - Se a porta esteja habilitada
	 * <li> False - Caso a porta esteja desabilitada
	 * @throws EcfException Exceção do ECF
	 */
	public boolean isCommEnabled() throws EcfException {
		try {
			return ecf.isCommEnabled();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método retorna o fabricante do ECF
	 * @return String - Fabricante do ECF
	 * @throws EcfException Exceção do ECF
	 */
	public String fabricante() throws EcfException{
		try {
			return ecf.fabricante();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método retorna o modelo do ECF
	 * @return String - Modelo do ECF
	 * @throws EcfException Exceção do ECF
	 */
	public String modelo() throws EcfException{
		try {
			return ecf.modelo();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método verifica se o modelo já foi homologado
	 * @return boolean
	 * <li> True - Caso esteja homologado
	 * <li> False - Caso não esteja homologado
	 * @throws EcfException Exceção do ECF
	 */
	public boolean isHomologado() throws EcfException{
		try {
			return ecf.isHomologado();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
		
	}
	
	/**
	 * Método verifica se o sinal da gaveta esta invertido
	 * @return boolean
	 * <li> True - Caso esteja com Sinal invertido
	 * <li> False - Caso não esteja com sinal invertido
	 * @throws EcfException Exceção do ECF
	 */
	public boolean isGavetaSinalInvertido() throws EcfException {
		try {
			return ecf.isGavetaSinalInvertido();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método seta o tipo do sinal da gaveta no ECF
	 * @param gavetaSinalInvertido boolean - indicando se o sinal esta invertido
	 * @throws EcfException Exceção do ECF
	 */
	public void setGavetaSinalInvertido(boolean gavetaSinalInvertido) throws EcfException {
		try {
			ecf.setGavetaSinalInvertido(gavetaSinalInvertido);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	//Informações do ECF----------------------------------------------------------------
	
	/**
	 * Método verifica se há pouco papel no ECF
	 * @return boolean
	 * <li> True - Se houver Pouco Papel
	 * <li> False - Caso não esteja com Pouco Papel
	 * @throws EcfException Exceção do ECF 
	 */
	public boolean isPoucoPapel() throws EcfException{
		try {
			return ecf.isPoucoPapel();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}	
	}
	
	/**
	 * Método verifica se não há papel no ECF
	 * @return boolean
	 * <li> True - Se estiver Sem Papel 
	 * <li> False - Se não estiver Sem Papel
	 * @throws EcfException Exceção do ECF
	 */
	public boolean isSemPapel() throws EcfException{
		try {
			return ecf.isSemPapel();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método verifica se é permitido cancelar o último cupom fiscal emitido
	 * @return boolean
	 * <li> True - Caso seja possível cancelar 
	 * <li> False - Caso não seja possível cancelar
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção da comunicação
	 */
	public boolean isPermiteCancelamentoCupomFiscal() throws EcfException, CommException{
		try {
			return ecf.isPermiteCancelamentoCupomFiscal();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método verifica se não há espaço na memória fiscal
	 * @return boolean
	 * <li> True - Se não houver mais espaço
	 * <li> False - Se ainda houver espaço na memória fiscal
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public boolean isMemoriaFiscalSemEspaco() throws EcfException, CommException{
		try {
			return ecf.isMemoriaFiscalSemEspaco();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método verifica se é permitido cancelar o último cupom não fiscal emitido 
	 * @return boolean
	 * <li> True - Caso seja possível cancelar
	 * <li> False - Caso não seja possível cancelar
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public boolean isPermiteCancelamentoNaoFiscal() throws EcfException, CommException{
		try {
			return ecf.isPermiteCancelamentoNaoFiscal();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método verifica se é permitido estornar comprovante
	 * @return boolean
	 * <li> True - Caso seja possível estornar
	 * <li> False - Caso não seja possível estornar
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public boolean isPermiteEstornoComprovante() throws EcfException, CommException{
		try {
			return ecf.isPermiteEstornoComprovante();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método verifica se o ECF está com o Horário de verão ativo
	 * @return boolean
	 * <li> True - Se estiver ativo
	 * <li> False - Se estiver inativo
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public boolean isHorarioVerao() throws EcfException, CommException{
		try {
			return ecf.isHorarioVerao();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método ativa/desativa horário de verão no ECF
	 * @param isHorarioVerao parametro indicando se Horário de verão está ativo
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public void setHorarioVerao(boolean isHorarioVerao) throws EcfException, CommException{
		try {
			ecf.setHorarioVerao(isHorarioVerao);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método verifica se o ECF está truncando em seus calculos
	 * @return boolean
	 * <li> True - Se estiver truncando
	 * <li> False - Se não estiver truncando
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public boolean isTruncando() throws EcfException, CommException{
		try {
			return ecf.isTruncando();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca no ECF seu número serial
	 * @return String - Número serial do ECF
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public String getNumeroSerial() throws EcfException, CommException{
		try {
			return ecf.getNumeroSerial();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca no ECF o número do último cupom emitido
	 * @return int - Número do último cupom emitido
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public int getNumeroCupom() throws EcfException, CommException{
		try {
			return ecf.getNumeroCupom();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca no ECF o número do último cupom emitido
	 * @return int - Número do último cupom emitido
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public int getCOO() throws CommException, EcfException{
		try {
			return ecf.getCOO();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca no ECF o contador de reinicios de operação 
	 * @return int - Contador de reinicios de operação
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public int getCRO() throws CommException, EcfException{
		try {
			return ecf.getCRO();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca no ECF o contador de reduções Z
	 * @return int - Contador de Reduções Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public int getCRZ() throws CommException, EcfException{
		try {
			return ecf.getCRZ();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca no ECF o seu número
	 * @return int - Número do ECF
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public int getNumeroEcf() throws CommException, EcfException{
		try {
			return ecf.getNumeroEcf();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca do ECF o número da loja
	 * @return int - Número da Loja
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public int getNumeroLoja()  throws CommException, EcfException{
		try {
			return ecf.getNumeroLoja();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca no ECF a data do ultimo movimento
	 * @return Date - Data de movimento no ECF
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public Date getDataMovimento() throws CommException, EcfException{
		try {
			return ecf.getDataMovimento();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca no ECF a data e hora atual
	 * @return Date - Data e hora atual
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public Date getDataHora() throws CommException, EcfException{
		try {
			return ecf.getDataHora();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca o Grande Total atual do ECF 
	 * @return BigDecimal - Grande Total do ECF
	 * @throws CommException Exceção de comunicação 
	 * @throws EcfException Exceção do ECF
	 */
	public BigDecimal getGrandeTotal() throws CommException, EcfException{
		try {
			return ecf.getGrandeTotal();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método calcula o total da venda bruta atual do ECF
	 * @return BigDecimal - Total da Venda Bruta atual
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public BigDecimal getVendaBruta() throws CommException, EcfException{
		try {
			return ecf.getVendaBruta();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca no EFC o número do último item vendido 
	 * @return int - Número do último item
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public int getNumeroUltimoItem() throws CommException, EcfException{
		try {
			return ecf.getNumeroUltimoItem();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método verifica se a gaveta conectada ao ECF está aberta
	 * @return boolean
	 * <li> True - Gaveta Aberta
	 * <li> False - Gaveta fechada
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public boolean isGavetaAberta() throws CommException, EcfException{
		try {
			return ecf.isGavetaAberta();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método envia comando a gaveta conectada ao ECF para abri-la
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void abrirGaveta() throws CommException, EcfException{
		try {
			ecf.abrirGaveta();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método retorna o estado do ECF
	 * @return EcfEstado - Estado do ECF
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public EcfEstado getEstado() throws CommException, EcfException{
		try {
			return ecf.getEstado();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca o valor do grande Total no momento da última Redução Z
	 * @return BigDecimal - Grande Total da última redução z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public BigDecimal getGrandeTotalReducaoZ()throws CommException, EcfException{
		try {
			return ecf.getGrandeTotalReducaoZ();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca do ECF o valor de acrescímos da última redução z 
	 * @return BigDecimal - Acrescímos da última Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public BigDecimal getAcrescimoReducaoZ() throws CommException, EcfException{
		try{
			return ecf.getAcrescimoReducaoZ();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}

	/**
	 * Método busca do ECF o valor de descontos da última redução z
	 * @return BigDecimal - Descontos da última Reducao Z
	 * @throws CommException
	 * @throws EcfException
	 */
	public BigDecimal getDescontoReducaoZ() throws CommException, EcfException {
		try{
			return ecf.getDescontoReducaoZ();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca no ECF o número do cupom da última redução z
	 * @return int - COO da última Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public int getCOOReducaoZ() throws CommException, EcfException {
		try{
			return ecf.getCOOReducaoZ();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca no ECF valor total de Sangrias registradas na última redução z
	 * @return BigDecimal - Valor total de Sangrias da ùltima Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public BigDecimal getSangriaReducaoZ() throws CommException, EcfException {
		try{
			return ecf.getSangriaReducaoZ();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca no ECF valor total de Suprimentos registradas na última redução z
	 * @return BigDecimal - Valor total de Suprimentos da ùltima Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public BigDecimal getSuprimentoReducaoZ() throws CommException, EcfException {
		try{
			return ecf.getSuprimentoReducaoZ();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca no ECF valor total de Substrituições Tributárias registradas na última redução z
	 * @return BigDecimal - Valor total de Substrituições Tributárias da última Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public BigDecimal getSubstituicao() throws CommException, EcfException{
		try{
			return ecf.getSubstituicao();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca no ECF valor total de Não Tributados registrados na última redução z
	 * @return BigDecimal - Valor total de Não Tributados da última Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public BigDecimal getNaoTributado() throws CommException, EcfException{
		try{
			return ecf.getNaoTributado();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca no ECF valor total de Isentos registrados na última redução z
	 * @return BigDecimal - Valor total de Isentos da última Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public BigDecimal getIsento() throws CommException, EcfException{
		try{
			return ecf.getIsento();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca o COO inicial da última redução z
	 * @return int - COO inicial da última Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public int getCOOInicial() throws CommException, EcfException{
		try{
			return ecf.getCOOInicial();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca o COO final da última redução z
	 * @return int - COO final da última Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public int getCOOFinal() throws CommException, EcfException{
		try{
			return ecf.getCOOFinal();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca no ECF o Contador de Reinicios de Operação registrado na última reducão z
	 * @return int - CRO registrado na última Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public int getCROReducaoZ() throws CommException, EcfException{
		try{
			return ecf.getCROReducaoZ();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca no ECF o totalizador parcial de Acrescímos
	 * @return BigDecimal - Totalizador de Acrescímos
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public BigDecimal getAcrescimo() throws CommException, EcfException{
		try{
			return ecf.getAcrescimo();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Metodo busca no ECF o totalizador parcial de cancelamentos com incidência em serviços
	 * @return BigDecimal - Totalizador de Cancelamentos ISS
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public BigDecimal getCancelamentoISS()throws CommException, EcfException{
		try {
			return ecf.getCancelamentoISS();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Metodo busca no ECF o totalizador parcial de cancelamentos com incidência em produtos
	 * @return BigDecimal - Totalizador de Cancelamentos ICMS
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public BigDecimal getCancelamentoICMS()throws CommException, EcfException{
		try {
			return ecf.getCancelamentoICMS();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Metodo busca no ECF o totalizador parcial de descontos com incidência em serviços
	 * @return BigDecimal - Totalizador de Descontos ISS
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public BigDecimal getDescontoISS()throws CommException, EcfException{
		try {
			return ecf.getDescontoISS();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Metodo busca no ECF o totalizador parcial de descontos com incidência em produtos
	 * @return BigDecimal - Totalizador de Descontos ICMS
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public BigDecimal getDescontoICMS()throws CommException, EcfException{
		try {
			return ecf.getDescontoICMS();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Metodo busca no ECF o totalizador parcial de acrescímos com incidência em produtos
	 * @return BigDecimal - Totalizador de Acrescímos ICMS
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public BigDecimal getAcrescimoICMS()throws CommException, EcfException{
		try {
			return ecf.getAcrescimoICMS();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Metodo busca no ECF o totalizador parcial de acrescímos com incidência em serviços
	 * @return BigDecimal - Totalizador de Acrescímos ISS
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public BigDecimal getAcrescimoISS()throws CommException, EcfException{
		try {
			return ecf.getAcrescimoISS();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca no ECF o valor da Venda Bruta registrada na última redução z
	 * @return BigDecimal - valor da Venda Bruta na última Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public BigDecimal getVendaBrutaReducaoZ() throws CommException, EcfException{
		try {
			return ecf.getVendaBrutaReducaoZ();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca no ECF o totalizador parcial de cancelamentos
	 * @return BigDecimal - Totalizador de Cancelamentos
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public BigDecimal getCancelamento() throws CommException, EcfException{
		try {
			return ecf.getCancelamento();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca no ECF o totalizador parcial de descontos
	 * @return BigDecimal - Totalizador de Descontos
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public BigDecimal getDesconto() throws CommException, EcfException{
		try {
			return ecf.getDesconto();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	public BigDecimal getSubtotalCupom() throws CommException, EcfException{
		try {
			return ecf.getSubtotalCupom();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	public BigDecimal getValorPagoUltimoCupom() throws CommException, EcfException{
		try {
			return ecf.getValorPagoUltimoCupom();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
		
	
	// Relatórios --------------------------------------------------------------
	/**
	 * Método envia comando ao ECF para que seja emitida a Leitura X
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void leituraX() throws CommException, EcfException{
		try {
			ecf.leituraX();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método envia comando ao ECF para que seja emitida a Redução Z
	 * @param dataMovimentacao <i>Date</i>  
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void reducaoZ(Date dataMovimentacao) throws CommException, EcfException{
		try {
			ecf.reducaoZ(dataMovimentacao);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método envia comando ao ECF para que este, imprima um relatório da memória fiscal filtrado por datas
	 * @param dataInicial <i>Date</i>
	 * @param dataFinal <i>Date</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void leituraMemoriaFiscalData(Date dataInicial, Date dataFinal) 
			throws EcfException, CommException{
		try {
			ecf.leituraMemoriaFiscalData(dataInicial, dataFinal);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método envia comando ao ECF para que este, retorne via porta serial um relatório da memória fiscal filtrado por datas
	 * @param dataInicial <i>Date</i>
	 * @param dataFinal <i>Date</i>
	 * @return String - Relatório da memória fiscal para exibir em video
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public String leituraMemoriaFiscalDataSerial(Date dataInicial, Date dataFinal) 
			throws EcfException, CommException{
		try {
			return ecf.leituraMemoriaFiscalDataSerial(dataInicial, dataFinal);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método envia comando ao ECF para que este, imprima um relatório da memória fiscal de maneira sintética filtrado por datas
	 * @param dataInicial <i>Date</i>
	 * @param dataFinal <i>Date</i>
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public void leituraMemoriaFiscalDataSimplificado(Date dataInicial, Date dataFinal) 
			throws EcfException, CommException{
		try {
			ecf.leituraMemoriaFiscalDataSimplificado(dataInicial, dataFinal);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método envia comando ao ECF para que este, retorne via porta serial um relatório da memória fiscal sintético filtrado por datas
	 * @param dataInicial <i>Date</i>
	 * @param dataFinal <i>Date</i>
	 * @return String - Relatório da memória fiscal para exibir em video
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public String leituraMemoriaFiscalDataSimplificadoSerial(Date dataInicial, Date dataFinal) 
			throws EcfException, CommException{
		try {
			return ecf.leituraMemoriaFiscalDataSimplificadoSerial(dataInicial, dataFinal);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método envia comando ao ECF para que este, imprime um relatório da memória fiscal filtrado por CRZ
	 * @param reducaoInicial <i>int</i> - CRZ inicial
	 * @param reducaoFinal <i>int</i> - CRZ final
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public void leituraMemoriaFiscalReducao(int reducaoInicial, int reducaoFinal) throws EcfException, CommException{
		try {
			ecf.leituraMemoriaFiscalReducao(reducaoInicial, reducaoFinal);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método envia comando ao ECF para que este, retorne via porta serial um relatório da memória fiscal filtrado por CRZ
	 * @param reducaoInicial <i>int</i> - CRZ inicial
	 * @param reducaoFinal <i>int</i> - CRZ final
	 * @return String - Relatório da memória fiscal para exibir em video
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public String leituraMemoriaFiscalReducaoSerial(int reducaoInicial, int reducaoFinal) throws EcfException, CommException{
		try {
			return ecf.leituraMemoriaFiscalReducaoSerial(reducaoInicial, reducaoFinal);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método envia comando ao ECF para que este, imprime um relatório da memória fiscal sintético filtrado por CRZ
	 * @param reducaoInicial <i>int</i> - CRZ inicial
	 * @param reducaoFinal <i>int</i> - CRZ final
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public void leituraMemoriaFiscalReducaoSimplificado(int reducaoInicial, int reducaoFinal) throws EcfException, CommException{
		try {
			ecf.leituraMemoriaFiscalReducaoSimplificado(reducaoInicial, reducaoFinal);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método envia comando ao ECF para que este, retorne via porta serial um relatório da memória fiscal sintético filtrado por CRZ
	 * @param reducaoInicial <i>int</i> - CRZ inicial
	 * @param reducaoFinal <i>int</i> - CRZ final
	 * @return String - Relatório da memória fiscal para exibir em video
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public String leituraMemoriaFiscalReducaoSimplificadoSerial(int reducaoInicial, int reducaoFinal) throws EcfException, CommException{
		try {
			return ecf.leituraMemoriaFiscalReducaoSimplificadoSerial(reducaoInicial, reducaoFinal);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método abre um relatório gerencial no ECF tipo "default"
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public void abrirRelatorioGerencial() throws CommException, EcfException{
		try {
			ecf.abrirRelatorioGerencial();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método imprime uma linha no relatório gerencial aberto
	 * @param texto <i>String</i> texto a ser impresso
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void linhaRelatorioGerencial(String texto) throws CommException, EcfException{
		try {
			ecf.linhaRelatorioGerencial(texto);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método fecha o relatório gerencial
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void fechaRelatorio() throws CommException, EcfException{
		try {
			ecf.fecharRelatorio();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	// Cupom Fiscal ------------------------------------------------------------
	/**
	 * Método abre um cupom fiscal
	 * @param cpfCnpj <i>String</i>
	 * @param nome <i>String</i>
	 * @param endereco <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void abrirCupom(String cpfCnpj, String nome, String endereco) throws CommException, EcfException{
		try {
			ecf.abrirCupom(cpfCnpj, nome, endereco);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método vende um item sem desconto ou acréscimo no ECF
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @param codAliquota <i>String</i>
	 * @param quantidade <i>BigDecimal</i>
	 * @param valorUnitario <i>BigDecimal</i>
	 * @param unidade <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void venderItem(String codigo, String descricao, String codAliquota, 
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade) 
			throws CommException, EcfException{
		try {
			ecf.venderItem(codigo, descricao, codAliquota, quantidade, valorUnitario, unidade);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}		
	}
	
	/**
	 * Método vende um item com acréscimo por valor no ECF
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @param codAliquota <i>String</i>
	 * @param quantidade <i>BigDecimal</i>
	 * @param valorUnitario <i>BigDecimal</i>
	 * @param unidade <i>String</i>
	 * @param acrescimo <i>BigDecimal</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void venderItemAcrescimo(String codigo, String descricao, String codAliquota, 
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade, BigDecimal acrescimo) 
			throws CommException, EcfException{
		try {
			ecf.venderItemAcrescimo(codigo, descricao, codAliquota, quantidade, valorUnitario, unidade, acrescimo);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método vende um item com acréscimo percentual no ECF
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @param codAliquota <i>String</i>
	 * @param quantidade <i>BigDecimal</i>
	 * @param valorUnitario <i>BigDecimal</i>
	 * @param unidade <i>String</i>
	 * @param acrescimo <i>BigDecimal</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void venderItemAcrescimoPerc(String codigo, String descricao, String codAliquota, 
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade, BigDecimal acrescimo) 
			throws CommException, EcfException{
		try {
			ecf.venderItemAcrescimoPerc(codigo, descricao, codAliquota, quantidade, valorUnitario, unidade, acrescimo);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}		
	}
	
	/**
	 * Método vende um item com desconto por valor no ECF
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @param codAliquota <i>String</i>
	 * @param quantidade <i>BigDecimal</i>
	 * @param valorUnitario <i>BigDecimal</i>
	 * @param unidade <i>String</i>
	 * @param desconto <i>BigDecimal</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void venderItemDesconto(String codigo, String descricao, String codAliquota, 
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade, BigDecimal desconto) 
			throws CommException, EcfException{
		try {
			ecf.venderItemDesconto(codigo, descricao, codAliquota, quantidade, valorUnitario, unidade, desconto);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método vende item com desconto percentual no ECF
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @param codAliquota <i>String</i>
	 * @param quantidade <i>BigDecimal</i>
	 * @param valorUnitario <i>BigDecimal</i>
	 * @param unidade <i>String</i>
	 * @param desconto <i>BigDecimal</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void venderItemDescontoPerc(String codigo, String descricao, String codAliquota, 
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade, BigDecimal desconto) 
			throws CommException, EcfException{
		try {
			ecf.venderItemDescontoPerc(codigo, descricao, codAliquota, quantidade, valorUnitario, unidade, desconto);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método imprime o TOTAL vendido no cupom até o momento
	 * @param desconto <i>BigDecimal</i>
	 * @param acrescimo <i>BigDecimal</i>
	 * @param descAcresPerc <i>boolean</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void subtotalizarCupom(BigDecimal desconto, BigDecimal acrescimo, boolean descAcresPerc)
			throws CommException, EcfException{
		try {
			ecf.subtotalizarCupom(desconto, acrescimo, descAcresPerc);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método imprime o valor pago com determinada forma de pagamento
	 * @param codFormaPagamento <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @param observacao <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void efetuarPagamento(String codFormaPagamento, BigDecimal valor, String observacao) 
			throws CommException, EcfException{
		try {
			ecf.efetuarPagamento(codFormaPagamento, valor, observacao);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método finaliza o cupom aberto
	 * @param observacao <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void fecharCupom(String observacao)throws CommException, EcfException{
		try {
			ecf.fecharCupom(observacao);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método canleca cupom aberto ou o último cupom finalizado
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void cancelarCupom() throws CommException, EcfException{
		try {
			ecf.cancelarCupom();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método cancela determinado item do cupom aberto
	 * @param item <i>int</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void cancelarItem(int item)throws CommException, EcfException{
		try {
			ecf.cancelarItem(item);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método cancela o último item registrado, caso o cupom esteja aberto
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void cancelarUltimoItem()throws CommException, EcfException{
		try {
			ecf.cancelarUltimoItem();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	
	// Cupom Não Fiscal --------------------------------------------------------	
	/**
	 * Método emite um cupom não fiscal  <i>(completo)</i>
	 * @param codTotalizadorNaoFiscal <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @param codFormaPagamento <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void emitirNaoFiscal(String codTotalizadorNaoFiscal, BigDecimal valor, 
			String codFormaPagamento) throws CommException, EcfException{
		try {
			ecf.emitirNaoFiscal(codTotalizadorNaoFiscal, valor, codFormaPagamento);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método abre um cupom não fiscal
	 * @param cpfCnpj <i>String</i>
	 * @param nomeConsumidor <i>String</i>
	 * @param enderecoConsumidor <i>Strinf</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void abrirNaoFiscal(String cpfCnpj, String nomeConsumidor, 
			String enderecoConsumidor) throws CommException, EcfException{
				try {
					ecf.abrirNaoFiscal(cpfCnpj, nomeConsumidor, enderecoConsumidor);
				} catch (NullPointerException e) {
					throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
				}	
			}
	
	/**
	 * Método registra um item no cupom não fiscal aberto
	 * @param codTotalizadorNaoFiscal <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void registrarItemNaoFiscal(String codTotalizadorNaoFiscal, BigDecimal valor) 
			throws CommException, EcfException{
		try {
			ecf.registrarItemNaoFiscal(codTotalizadorNaoFiscal, valor);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método subtotaliza Cupom Não fiscal
	 * @param desconto <i>BigDecimal</i>
	 * @param acrescimo <i>BigDecimal</i>
	 * @param descAcresPerc <i>boolean</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void subtotalizarNaoFiscal(BigDecimal desconto, BigDecimal acrescimo, boolean descAcresPerc) 
			throws CommException, EcfException{
		try {
			ecf.subtotalizarNaoFiscal(desconto, acrescimo, descAcresPerc);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método imprime o valor pago com determinada forma de pagamento
	 * @param codFormaPagamento <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @param observacao <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void efetuarPagamentoNaoFiscal(String codFormaPagamento, BigDecimal valor, 
			String observacao) throws CommException, EcfException{
		try {
			ecf.efetuarPagamentoNaoFiscal(codFormaPagamento, valor, observacao);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método finaliza cupom não fiscal aberto
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void fecharNaoFiscal() throws CommException, EcfException{
		try {
			ecf.fecharNaoFiscal();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método canleca cupom não fiscal aberto ou o último cupom não fiscal finalizado
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção de ECF
	 */
	public void cancelarNaoFiscal() throws CommException, EcfException{
		try {
			ecf.cancelarNaoFiscal();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método imprime segunda via de cupom não fiscal
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void reimprimirCupomNaoFiscal() throws CommException, EcfException{
		try {
			ecf.reimprimirCupomNaoFiscal();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	//Comandos de autenticação--------------------------------------------------
	/**
	 * Método imprime uma marca de autenticação em documentos
	 * @param linhasAvanco <i>int</i>
	 * @param linhaAdicional <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void autenticarDocumento(int linhasAvanco, String linhaAdicional) 
			throws CommException, EcfException{
		try {
			ecf.autenticarDocumento(linhasAvanco, linhaAdicional);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	// Cupom Não Fiscal --------------------------------------------------------
	/**
	 * Método emite comprovantes <i>(completo)</i>
	 * @param codFormaPagamento <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @param COO <i>int</i>
	 * @param cpfConsumidor <i>String</i>
	 * @param nomeConsumidor <i>String</i>
	 * @param enderecoConsumidor <i>String</i>
	 * @param texto <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção de ECF
	 */
	public void emitirComprovante(String codFormaPagamento, BigDecimal valor, int COO, String cpfConsumidor, 
			String nomeConsumidor, String enderecoConsumidor, String texto)throws CommException, EcfException{
		try {
			ecf.emitirComprovante(codFormaPagamento, valor, COO, cpfConsumidor, nomeConsumidor, enderecoConsumidor, texto);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método para abrir comprovantes
	 * @param codFormaPagamento <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @param COO <i>int</i>
	 * @param cpfConsumidor <i>String</i>
	 * @param nomeConsumidor <i>String</i>
	 * @param enderecoConsumidor <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção de ECF
	 */
	public void abrirComprovante(String codFormaPagamento, BigDecimal valor, 
			int COO, String cpfConsumidor, String nomeConsumidor, String enderecoConsumidor)
			throws CommException, EcfException{
		try {
			ecf.abrirComprovante(codFormaPagamento, valor, COO, cpfConsumidor, nomeConsumidor, enderecoConsumidor);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}		
	}
	
	/**
	 * Método imprime linhas em um comprovante aberto
	 * @param texto <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void imprimirLinhaComprovante(String texto) throws CommException, EcfException{
		try {
			ecf.imprimirLinhaComprovante(texto);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método finaliza comprovante aberto
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção de ECF
	 */
	public void fecharComprovante() throws CommException, EcfException{
		try {
			ecf.fecharComprovante();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método estorna comprovante emitido
	 * @param cpf <i>String</i>
	 * @param nome <i>String</i>
	 * @param endereco <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void estornarComprovante(String cpf, String nome, String endereco) 
			throws CommException, EcfException{
		try {
			ecf.estornarComprovante(cpf, nome, endereco);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método imprime segunda via do último comprovante emitido
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void imprimirSegundaVia() throws CommException, EcfException{
		try {
			ecf.imprimirSegundaVia();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	
	//ALIQUOTAS-----------------------------------------------------------------
	/**
	 * Método busca todas as aliquotas cadastradas no ECF e retorna em uma lista
	 * @return List - Lista de aliquotas 
	 */
	public List<Aliquota> getAliquotaList (){
		return ecf.getAliquotasList();
	}
	
	/**
	 * Método carrega as aliquotas do ECF 
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void carregarAliquotas() throws CommException, EcfException{
		try {
			ecf.carregarAliquotas();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método programa uma nova aliquota no ECF
	 * @param codigo <i>String</i>
	 * @param aliquota <i>BigDecimal</i>
	 * @param incidencia <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void programarAliquota(String codigo, BigDecimal aliquota, String incidencia) 
			throws CommException, EcfException{
		try {
			ecf.programarAliquota(codigo, aliquota, incidencia);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca uma aliquota a partir de um código
	 * @param codigo <i>String</i>
	 * @return Aliquota
	 * @throws EcfException Exceção do ECF
	 */
	public Aliquota getAliquota(String codigo) throws EcfException{
		try {
			return ecf.getAliquota(codigo);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	//FORMAS DE PAGAMENTO--------------------------------------------------------
	/**
	 * Método busca todas as formas de pagamento cadastradas no ECF e retorna em uma lista
	 * @return Lista de Formas de Pagamento
	 */
	public List<FormaPagamento> getFormasPagamentoList(){
		return ecf.getFormasPagamentoList();
	}
	
	/**
	 * Método carrega as formas de pagamento do ECF 
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void carregarFormasPagamento() throws CommException, EcfException{
		try {
			ecf.carregarFormasPagamento();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método programa uma nova forma de pagamento no ECF
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @param permiteVincular <i>boolean</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void programarFormaPagamento(String codigo, String descricao, boolean permiteVincular) 
			throws CommException, EcfException{
		try {
			ecf.programarFormaPagamento(codigo, descricao, permiteVincular);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca uma forma de pagamento a partir de um código
	 * @param codigo <i>String</i>
	 * @return FormaPagamento
	 * @throws EcfException Exceção do ECF
	 */
	public FormaPagamento getFormaPagamento(String codigo) throws EcfException{
		try {
			return ecf.getFormaPagamento(codigo);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca uma forma de pagamento a partir de uma descrição
	 * @param descricao <i>String</i>
	 * @return FormaPagamento
	 * @throws EcfException Exceção do ECF
	 */
	public FormaPagamento getFormaPagamentoPorDescricao(String descricao) throws EcfException{
		try {
			return ecf.getFormaPagamentoPorDescricao(descricao);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	
	//TOTALIZADORES----------------------------------------------------------------
	/**
	 * Método busca todas os totalizadores cadastradas no ECF e retorna em uma lista
	 * @return
	 */
	public List<TotalizadorNaoFiscal> getTotalizadoresNaoFiscaisList(){
		return ecf.getTotalizadorsNaoFiscaisList();
	}
	
	/**
	 * Método carrega os totalizadores não fiscais do ECF 
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void carregarTotalizadoresNaoFiscais() throws CommException, EcfException{
		try {
			ecf.carregarTotalizadoresNaoFiscais();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método programa um novo totalizador não fiscal no ECF
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void programarTotalizadorNaoFiscal(String codigo, String descricao) 
			throws CommException, EcfException{
		try {
			ecf.programarTotalizadorNaoFiscal(codigo, descricao);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}	
	}
	
	/**
	 * Método busca um totalizador não fiscal a partir de um código
	 * @param codigo <i>String</i>
	 * @return TotalizadorNaoFiscal
	 * @throws EcfException Exceção do ECF
	 */
	public TotalizadorNaoFiscal getTotalizadorNaoFiscal(String codigo) throws EcfException{
		try {
			return ecf.getTotalizadorNaoFiscal(codigo);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método busca um totalizador não fiscal a partir de uam descrição
	 * @param descricao <i>String</i>
	 * @return TotalizadorNaoFiscal
	 * @throws EcfException Exceção do ECF
	 */
	public TotalizadorNaoFiscal getTotalizadorNaoFiscalPorDescricao(String descricao) throws EcfException{
		try {
			return ecf.getTotalizadorNaoFiscalPorDescricao(descricao);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	
	//----------------------------------------------------------------------------
	/**
	 * Método programa no ECF o nome da moeda no singular
	 * @param nome <i>String</i>
	 * @throws CommException Eceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void programarMoedaSingular(String nome) throws CommException, EcfException{
		try {
			ecf.programarMoedaSingular(nome);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método programa no ECF o nome da moeda no plural
	 * @param nome <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void programarMoedaPlural(String nome) throws CommException, EcfException{
		try {
			ecf.programarMoedaPlural(nome);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método imprime cheques no ECF de acordo com os modelos dos banco 
	 * @param banco <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @param favorecido <i>String</i>
	 * @param cidade <i>String</i>
	 * @param data <i>Date</i>
	 * @param observacao <i>Straing</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void imprimirCheque(String banco, BigDecimal valor, String favorecido,
			String cidade, Date data, String observacao) throws CommException, EcfException{
		try {
			ecf.imprimirCheque(banco, valor, favorecido, cidade, data, observacao);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método cancela a impressão no cheque. Esse método só será executado se o ECF estiver aguardando o Cheque
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void cancelarImpressaoCheque() throws CommException, EcfException{
		try {
			ecf.cancelarImpressaoCheque();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método verifica se o ECF está aguardando a colocação do cheque para impressão
	 * @return boolean
	 * <li> True - Caso esteja aguardando
	 * <li> False - Caso não esteja aguardando
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public boolean isAguardandoCheque() throws CommException, EcfException{
		try {
			return ecf.isAguardandoCheque();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método verifica se o ECF está imprimindo cheque
	 * @return boolean
	 * <li> True - Caso estaja imprimindo cheque
	 * <li> False - Caso não esteja imprimindo cheque
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public boolean isImprimindoCheque() throws CommException, EcfException{
		try {
			return ecf.isImprimindoCheque();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método faz com que o ECF pule linhas em sua impressão
	 * @param qtdeLinhas <i>int</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void pularLinhas(int qtdeLinhas) throws CommException, EcfException{
		try {
			ecf.pularLinhas(qtdeLinhas);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método faz com que o ECF corte o papel de impressão
	 * @param corteParcial <i>boolean</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void cortarPapel(boolean corteParcial) throws CommException, EcfException{
		try {
			ecf.cortarPapel(corteParcial);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	
	//----------------------------------------------------------------------------
	/**
	 * Método Carrega os layouts de cheque em uma lista
	 * @throws EcfException Exceção do ECF
	 */
	public void carregarLayoutCheque()throws EcfException{
		try {
			ecf.carregarLayoutCheque();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método carrega os layouts de cheques em uma lista a partir de um XML
	 * @param xml <i>String</i>
	 * @throws EcfException Exceção do ECF
	 */
	public void carregarLayoutCheque(String xml)throws EcfException{
		try {
			ecf.carregarLayoutCheque(xml);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método carrega os layouts de cheques a partir do codigo do banco
	 * @param banco <i>String</i>
	 * @return LayoutCheque
	 * @throws EcfException Exceção do ECF
	 */
	public LayoutCheque getLayoutCheque(String banco) throws EcfException{
		try {
			return ecf.getLayoutCheque(banco);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Método insere um novo layout de cheque
	 * @param layouts <i>List</i>
	 * @throws EcfException
	 */
	public void setLayouts(List<LayoutCheque> layouts) throws EcfException {
		try {
			ecf.setLayouts(layouts);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	public Integer getNumColunas(){
		return ecf.getNumColunas();
	}
}
