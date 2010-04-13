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
package org.ecf4j.ecf;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ecf4j.ecf.exceptions.EcfException;
import org.ecf4j.utils.bigdecimals.BigDecimalUtils;
import org.ecf4j.utils.comm.CommEquipto;
import org.ecf4j.utils.comm.exceptions.CommException;
import org.ecf4j.utils.files.FileUtils;

import com.thoughtworks.xstream.XStream;

/**
 * Classe abstrata do ECF
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 * @extends CommEquipto
 * @see CommEquipto
 */
public abstract class EcfAbstract extends CommEquipto {
	
	protected boolean commEnabled = false;
	
	private List<Aliquota> aliquotas = new ArrayList<Aliquota>();
	private List<FormaPagamento> formasPagamento = new ArrayList<FormaPagamento>();
	private List<TotalizadorNaoFiscal> totalizadoresNaoFiscais = new ArrayList<TotalizadorNaoFiscal>();
	private boolean gavetaSinalInvertido = false;
	private List<LayoutCheque> layoutsCheque = new ArrayList<LayoutCheque>();
	
	
	
	/**
	 * Método verifica o tipo de sinal da gaveta
	 * @return boolean
	 * <li> True - Caso Sinal esteja invertido
	 * <li> False - Caso o sinal não esteja invertido
	 */
	public boolean isGavetaSinalInvertido() {
		return gavetaSinalInvertido;
	}
	/**
	 * Método altera o valor do flag de sinal invertido
	 * @param gavetaSinalInvertido <i>boolean</i>
	 */
	public void setGavetaSinalInvertido(boolean gavetaSinalInvertido) {
		this.gavetaSinalInvertido = gavetaSinalInvertido;
	}
	
	/**
	 * Método prepara o comando para ser enviado para o ECF
	 * @param cmd <i>byte[]</i>
	 * @return resposta do ECF
	 */
	protected byte[] preparaComando(byte[] cmd){
		return preparaComando(cmd, "");
	}
	/**
	 * Método prepara o comando para ser enviado para o ECF
	 * @param cmd <i>byte[]</i>
	 * @param prm <i>String</i>
	 * @return resposta do ECF
	 */
	protected byte[] preparaComando(byte[] cmd, String prm){
		return preparaComando(cmd, prm.getBytes());
	}
	/**
	 * Método abstrato inicializa comunicação com o ECF
	 * @param porta <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void inicializar(String porta) throws CommException, EcfException;
	/**
	 * Método abstrato finaliza comunicação com ECF
	 */
	public abstract void finalizar();
	/**
	 * Método abstrato prepara o comando para ser enviado para o ECF
	 * @param cmd <i>byte[]</i>
	 * @param prm <i>byte[]</i>
	 * @return resposta do ECF
	 */
	protected abstract byte[] preparaComando(byte[] cmd, byte[] prm);
	/**
	 * Método abstrato verifica um retorno simples do ECF
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	protected abstract void verificaRetorno() throws EcfException, CommException;
	/**
	 * Método abstrato verifica um retorno com uma determinada quantidade de caracteres de resposta do ECF
	 * @param len <i>int</i>
	 * @return Pode retornar um erro do ECF 
	 * @throws EcfException Exceção do ECF 
	 * @throws CommException Exceção de comunicação
	 */
	protected abstract byte[] getRetorno(int len) throws EcfException, CommException;
	/**
	 * Método abstrato envia para o ECF um comando formatado
	 * @param cmd <i>byte[]</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	protected abstract void executaComando(byte[] cmd) throws CommException, EcfException;
	/**
	 * Método abstrato busca o fabricante do ECF
	 * @return Fabricanto do ECF
	 */
	public abstract String fabricante();
	/**
	 * Método abstrato busca o modelo do ECF
	 * @return Modelo do ECF
	 */
	public abstract String modelo();
	/**
	 * Método abstrato verifica se o modelo está homologado
	 * @return boolean
	 * <li> True - Caso esteja homologado
	 * <li> False - Caso não esteja homologado
	 */
	public abstract boolean isHomologado();
	/**
	 * Método abstrato busca a quantidade de caracteres por linha no ECF
	 * @return Número de colunas
	 */
	public abstract Integer getNumColunas();
	
	
	@Override
	public String toString() {
		return fabricante()+ " " + modelo();
	}
	
	//Informações do ECF----------------------------------------------------------
	/**
	 * Método abstrato verifica se há pouco papel no ECF
	 * @return boolean
	 * <li> True - Caso esteja com pouco papel
	 * <li> False - Caso não esteja com pouco papel
	 */
	public abstract boolean isPoucoPapel();
	/**
	 * Método abstrato verifica se o ECF está sem papel
	 * @return Boolean
	 * <li> True - Caso esteja sem papel no ECF
	 * <li> False - Caso não esjeta sem papel no ECF
	 */
	public abstract boolean isSemPapel();
	/**
	 * Método abstrato verifica no ECF se é possível cancelar o último cupom fiscal emitido
	 * @return boolean 
	 * <li> True - Caso seja possível cancelar o último cupom fiscal
	 * <li> False -Caso não seja possível cancelar o último cupom fiscal 
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract boolean isPermiteCancelamentoCupomFiscal() throws CommException, EcfException;
	/**
	 * Método abstrato verifica se há espaço na memória fiscal
	 * @return boolean
	 * <li> True - Caso não haja mais espaço na memória fiscal
	 * <li> False - Caso ainda haja espaço na memória fiscal
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract boolean isMemoriaFiscalSemEspaco() throws CommException, EcfException;
	/**
	 * Método abstrato verifica no ECF se é possível cancelar o último cupom não fiscal
	 * @return boolean
	 * <li> True - Caso seja possível cancelar o último cupom não fiscal emitido
	 * <li> False - Caso não seja possível cancelar o último cupom não fiscal emitido
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract boolean isPermiteCancelamentoNaoFiscal() throws CommException, EcfException;
	/**
	 * Método abstrato verifica no ECF se é possível estornar comprovante
	 * @return boolean
	 * <li> True - Caso seja possível estornar comprovante
	 * <li> False - Caso não seja possível estornar comprovante
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract boolean isPermiteEstornoComprovante() throws CommException, EcfException;
	/**
	 * Método abstrato verifica no ECF se o flag de horário de verão está marcado
	 * @return boolean
	 * <li> True - Caso o ECF esteja trabalhando em horário de verão
	 * <li> False - Caso o ECF não esteja trabalhando em horário de verão
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract boolean isHorarioVerao() throws CommException, EcfException;
	/**
	 * Método abstrato seta o flag de horário de verão do ECF
	 * @param isHorarioVerao <i>boolean</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void setHorarioVerao(boolean isHorarioVerao) throws CommException, EcfException;
	/**
	 * Método abstrato verifica se o ECF está truncando em seus calculos
	 * @return boolean
	 * <li> True - Caso o ECF esteja truncando
	 * <li> False - Caso o ECF não esteja truncando
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract boolean isTruncando() throws CommException, EcfException;
	/**
	 * Método abstrato busca no número serial do ECF
	 * @return <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract String getNumeroSerial() throws CommException, EcfException;
	/**
	 * Método busca no ECF o número do último cupom
	 * @return COO (Contador de Ordem de Operações)
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public int getNumeroCupom() throws CommException, EcfException{
		return getCOO();
	}
	/**
	 * Método abstrato busca no ECF o número do último cupom
	 * @return COO (Contador de Ordem de Operações)
	 *@throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract int getCOO() throws CommException, EcfException;
	/**
	 * Método abstrato busca no ECF o número contador de reinicios de operação
	 * @return CRO(Contador dee Reinícios do Operações)
	 * @throws CommException Exceçao de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract int getCRO() throws CommException, EcfException;
	/**
	 * Método abstrato busca no ECF o número contador de reduções z
	 * @return CRZ(Contador de Reduções Z)
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract int getCRZ() throws CommException, EcfException;
	/**
	 * Método abstrato busca o número do ECF
	 * @return Número do ECF
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract int getNumeroEcf() throws CommException, EcfException;
	/**
	 * Método abstrato busca o número da loja do ECF
	 * @return Número da loja do ECF
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract int getNumeroLoja()  throws CommException, EcfException;
	/**
	 * Método abstrato busca no ECF a data da movimentação 
	 * @return Data da movimentação
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract Date getDataMovimento() throws CommException, EcfException;
	/**
	 * Método abstrato busca no ECF a data e hora atual do ECF
	 * @return Data e Hora no ECF
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract Date getDataHora() throws CommException, EcfException;
	/**
	 * Método abstrato busca o grande total atual do ECF
	 * @return Grande Total atual
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract BigDecimal getGrandeTotal() throws CommException, EcfException;
	/**
	 * Método abstrato busca a venda bruta atual do ECF
	 * @return Venda Bruta atual
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract BigDecimal getVendaBruta() throws CommException, EcfException;
	/**
	 * Método abstrato busca no ECF o número do último item informado
	 * @return Número do último item
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract int getNumeroUltimoItem() throws CommException, EcfException;
	/**
	 * Método abstrato verifica estado da gaveta
	 * @return boolean
	 * <li> True - Caso a gaveta esteja aberta
	 * <li> False - Caso a gaveta esteja fechada
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract boolean isGavetaAberta() throws CommException, EcfException;
	/**
	 * Método abstrato que abre a gaveta
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void abrirGaveta() throws CommException, EcfException;
	/**
	 * Método abstrato verifica o estado da ECF
	 * @return Estado da ECF
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */	
	public abstract EcfEstado getEstado() throws CommException, EcfException;
	/**
	 * Método abstrato retorna o Grande Total da última Redução Z
	 * @return Grande Total da última Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */	
	public abstract BigDecimal getGrandeTotalReducaoZ()throws CommException, EcfException;
	/**
	 * Método abstrato retorna o valor total de serviços cancelados do dia
	 * @return Valor Total de Serviços Cancelados
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */	
	public abstract BigDecimal getCancelamentoISS()throws CommException, EcfException;
	/**
	 * Método abstrato retorna o valor total de produtos cancelados do dia
	 * @return Valor Total de Produtos Cancelados
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */	
	public abstract BigDecimal getCancelamentoICMS()throws CommException, EcfException;
	/**
	 * Método abstrato retorna o valor total de descontos em serviços do dia
	 * @return Valor Total de Descontos em Serviços
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */	
	public abstract BigDecimal getDescontoISS()throws CommException, EcfException;
	/**
	 * Método abstrato retorna o valor total de descontos em produtos do dia
	 * @return Valor Total de Descontos em Produtos
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */	
	public abstract BigDecimal getDescontoICMS()throws CommException, EcfException;
	/**
	 * Método abstrato retorna o valor total de acrescimos em produtos do dia
	 * @return Valor Total de Acrescimos em Produtos
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */	
	public abstract BigDecimal getAcrescimoICMS()throws CommException, EcfException;
	/**
	 * Método abstrato retorna o valor total de acréscimos em serviços do dia
	 * @return Valor Total de Acréscimos em Serviços
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract BigDecimal getAcrescimoISS()throws CommException, EcfException;
	/**
	 * Método abstrato retorna o valor da Venda Bruta registrada na última redução Z
	 * @return Valor Total da Venda Bruta da Última Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract BigDecimal getVendaBrutaReducaoZ() throws CommException, EcfException;
	/**
	 * Método abstrato retorna o valor de Descontos registrados na última redução Z
	 * @return Valor Total de Descontos na Última Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract BigDecimal getDescontoReducaoZ() throws CommException, EcfException;
	/**
	 * Método abstrato retorna o valor de Acréscimos registrados na última redução Z
	 * @return Valor Total de Acréscimos na Última Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract BigDecimal getAcrescimoReducaoZ() throws CommException, EcfException;
	/**
	 * Método abstrato retorna o valor de Sangria registrado na última redução Z
	 * @return Valor Total de Sangria na Última Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract BigDecimal getSangriaReducaoZ() throws CommException, EcfException;
	/**
	 * Método abstrato retorna o valor de Suprimento registrado na última redução Z
	 * @return Valor Total de Suprimento na Última Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract BigDecimal getSuprimentoReducaoZ() throws CommException, EcfException;
	/**
	 * Método abstrato busca no ECF valor total de Substrituições Tributárias registradas na última redução z
	 * @return Valor total de Substrituições Tributárias da última Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract BigDecimal getSubstituicao() throws CommException, EcfException;
	/**
	 * Método abstrato busca no ECF valor total de Não Tributados registradas na última redução z
	 * @return Valor total de Não Tributados da última Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract BigDecimal getNaoTributado() throws CommException, EcfException;
	/**
	 * Método abstrato busca no ECF valor total de Isentos registradas na última redução z
	 * @return Valor total de Isentos da última Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract BigDecimal getIsento() throws CommException, EcfException;
	/**
	 * Método abstrato busca o COO (Contador de Ordem de Operação) inicial registrado na última redução z
	 * @return COO Inicial da Última Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract int getCOOInicial() throws CommException, EcfException;
	/**
	 * Método abstrato busca o COO (Contador de Ordem de Operação) final registrado na última redução z
	 * @return COO Final da Última Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract int getCOOFinal() throws CommException, EcfException;
	/**
	 * Método abstrato busca o COO (Contador de Ordem de Operação) da última redução z
	 * @return COO da Última Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract int getCOOReducaoZ() throws CommException, EcfException;
	/**
	 * Método abstrato busca o CRO (Contador de Reinicios de Operação) registrado na última redução z
	 * @return CRO Registrado na Última Redução Z
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract int getCROReducaoZ() throws CommException, EcfException;
	/**
	 * Método abstrato busca no ECF o totalizador parcial de cancelamentos
	 * @return Totalizador parcial de cancelamentos
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract BigDecimal getCancelamento() throws CommException, EcfException;
	/**
	 * Método abstrato busca no ECF o totalizador parcial de descontos
	 * @return Totalizador parcial de descontos
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract BigDecimal getDesconto() throws CommException, EcfException;
	/**
	 * Método abstrato busca no ECF o totalizador parcial de acréscimos
	 * @return Totalizador parcial de acréscimos
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract BigDecimal getAcrescimo() throws CommException, EcfException;
	
	/**
	 * Método abstrato busca no ECF o Subtotal do cupom
	 * @return Subtotal do cupom
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract BigDecimal getSubtotalCupom() throws CommException, EcfException;
	
	/**
	 * Método abstrato busca no ECF o valor do último cupom fiscal
	 * @return Valor Do Último Cupom Fiscal
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract BigDecimal getValorPagoUltimoCupom() throws CommException, EcfException;

	// Relatórios --------------------------------------------------------------
	/**
	 * Método abstrato envia comando ao ECF para que seja emitida a Leitura X
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void leituraX() throws CommException, EcfException;
	/**
	 * Método abstrato envia comando ao ECF para que seja emitida a Redução Z
	 * @param dataMovimentacao <i>Date</i>  
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void reducaoZ(Date dataMovimentacao) throws CommException, EcfException;
	/**
	 * Método abstrato envia comando ao ECF para que este, imprima um relatório da memória fiscal filtrado por datas
	 * @param dataInicial <i>Date</i>
	 * @param dataFinal <i>Date</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void leituraMemoriaFiscalData(Date dataInicial, Date dataFinal) throws EcfException, CommException;
	/**
	 * Método abstrato envia comando ao ECF para que este, retorne via porta serial um relatório da memória fiscal filtrado por datas
	 * @param dataInicial <i>Date</i>
	 * @param dataFinal <i>Date</i>
	 * @return String - Relatório da memória fiscal para exibir em video
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public abstract String leituraMemoriaFiscalDataSerial(Date dataInicial, Date dataFinal) throws EcfException, CommException;
	/**
	 * Método abstrato envia comando ao ECF para que este, imprima um relatório da memória fiscal de maneira sintética filtrado por datas
	 * @param dataInicial <i>Date</i>
	 * @param dataFinal <i>Date</i>
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public abstract void leituraMemoriaFiscalDataSimplificado(Date dataInicial, Date dataFinal) throws EcfException, CommException;
	/**
	 * Método abstrato envia comando ao ECF para que este, retorne via porta serial um relatório da memória fiscal sintético filtrado por datas
	 * @param dataInicial <i>Date</i>
	 * @param dataFinal <i>Date</i>
	 * @return String - Relatório da memória fiscal para exibir em video
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public abstract String leituraMemoriaFiscalDataSimplificadoSerial(Date dataInicial, Date dataFinal) throws EcfException, CommException;
	/**
	 * Método abstrato envia comando ao ECF para que este, imprime um relatório da memória fiscal filtrado por CRZ
	 * @param reducaoInicial <i>int</i> - CRZ inicial
	 * @param reducaoFinal <i>int</i> - CRZ final
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public abstract void leituraMemoriaFiscalReducao(int reducaoInicial, int reducaoFinal) throws EcfException, CommException;
	/**
	 * Método abstrato envia comando ao ECF para que este, retorne via porta serial um relatório da memória fiscal filtrado por CRZ
	 * @param reducaoInicial <i>int</i> - CRZ inicial
	 * @param reducaoFinal <i>int</i> - CRZ final
	 * @return String - Relatório da memória fiscal para exibir em video
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public abstract String leituraMemoriaFiscalReducaoSerial(int reducaoInicial, int reducaoFinal) throws EcfException, CommException;
	/**
	 * Método abstrato envia comando ao ECF para que este, imprime um relatório da memória fiscal sintético filtrado por CRZ
	 * @param reducaoInicial <i>int</i> - CRZ inicial
	 * @param reducaoFinal <i>int</i> - CRZ final
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public abstract void leituraMemoriaFiscalReducaoSimplificado(int reducaoInicial, int reducaoFinal) throws EcfException, CommException;
	/**
	 * Método abstrato envia comando ao ECF para que este, retorne via porta serial um relatório da memória fiscal sintético filtrado por CRZ
	 * @param reducaoInicial <i>int</i> - CRZ inicial
	 * @param reducaoFinal <i>int</i> - CRZ final
	 * @return String - Relatório da memória fiscal para exibir em video
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public abstract String leituraMemoriaFiscalReducaoSimplificadoSerial(int reducaoInicial, int reducaoFinal) throws EcfException, CommException;
	/**
	 * Método abstrato abre um relatório gerencial no ECF tipo "default"
	 * @throws EcfException Exceção do ECF
	 * @throws CommException Exceção de comunicação
	 */
	public abstract void abrirRelatorioGerencial() throws CommException, EcfException;
	/**
	 * Método abstrato imprime uma linha no relatório gerencial aberto
	 * @param texto <i>String</i> texto a ser impresso
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void linhaRelatorioGerencial(String texto) throws CommException, EcfException;
	/**
	 * Método abstrato fecha o relatório gerencial
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void fecharRelatorio() throws CommException, EcfException;
	
	// Cupom Fiscal ------------------------------------------------------------
	/**
	 * Método abstrato abre um cupom fiscal
	 * @param cpfCnpj <i>String</i>
	 * @param nome <i>String</i>
	 * @param endereco <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void abrirCupom(String cpfCnpj, String nome, String endereco) throws CommException, EcfException;
	
	/**
	 * Método abstrato vende um item sem desconto ou acréscimo no ECF 
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @param codAliquota <i>String</i>
	 * @param quantidade <i>BigDecimal</i>
	 * @param valorUnitario <i>BigDecimal</i>
	 * @param unidade <i>Strng</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void venderItem(String codigo, String descricao, String codAliquota, 
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade) 
			throws CommException, EcfException;
	
	/**
	 * Método abstrato vende um item com desconto de valor no ECF
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
	public abstract void venderItemDesconto(String codigo, String descricao, String codAliquota, 
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade, BigDecimal desconto)
			throws CommException, EcfException;
	
	/**
	 * Método abstrato vende um item com desconto percentual no ECF
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
	public abstract void venderItemDescontoPerc(String codigo, String descricao, String codAliquota, 
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade, BigDecimal desconto) 
			throws CommException, EcfException;
	
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
	public abstract void venderItemAcrescimo(String codigo, String descricao, String codAliquota, 
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade, BigDecimal acrescimo) 
			throws CommException, EcfException;
	
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
	public abstract void venderItemAcrescimoPerc(String codigo, String descricao, String codAliquota, 
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade, BigDecimal acrescimo) 
			throws CommException, EcfException;
	
	/**
	 * Método abstrato imprime o TOTAL vendido no cupom até o momento
	 * @param desconto <i>BigDecimal</i>
	 * @param acrescimo <i>BigDecimal</i>
	 * @param descAcresPerc <i>boolean</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void subtotalizarCupom(BigDecimal desconto, BigDecimal acrescimo, boolean descAcresPerc)throws CommException, EcfException;
	/**
	 * Método abstrato imprime o valor pago com determinada forma de pagamento
	 * @param codFormaPagamento <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @param observacao <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void efetuarPagamento(String codFormaPagamento, BigDecimal valor, String observacao)throws CommException, EcfException;
	/**
	 * Método abstrato finaliza o cupom aberto
	 * @param observacao <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void fecharCupom(String observacao)throws CommException, EcfException;
	/**
	 * Método abstrato canleca cupom aberto ou o último cupom finalizado
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void cancelarCupom() throws CommException, EcfException;
	/**
	 * Método abstrato cancela determinado item do cupom aberto
	 * @param item <i>int</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void cancelarItem(int item)throws CommException, EcfException;
	/**
	 * Método abstrato cancela o último item registrado, caso o cupom esteja aberto
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void cancelarUltimoItem() throws CommException, EcfException;
	
	// Cupom Não Fiscal --------------------------------------------------------
	/**
	 * Método abstrato emite um cupom não fiscal  <i>(completo)</i>
	 * @param codTotalizadorNaoFiscal <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @param codFormaPagamento <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void emitirNaoFiscal(String codTotalizadorNaoFiscal, BigDecimal valor, String codFormaPagamento) throws CommException, EcfException;
	/**
	 * Método abstrato abre um cupom não fiscal
	 * @param cpfCnpj <i>String</i>
	 * @param nomeConsumidor <i>String</i>
	 * @param enderecoConsumidor <i>Strinf</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void abrirNaoFiscal(String cpfCnpj, String nomeConsumidor, String enderecoConsumidor) throws CommException, EcfException;//77
	/**
	 * Método abstrato registra um item no cupom não fiscal aberto
	 * @param codTotalizadorNaoFiscal <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void registrarItemNaoFiscal(String codTotalizadorNaoFiscal, BigDecimal valor) throws CommException, EcfException;
	/**
	 * Método abstrato subtotaliza Cupom Não fiscal
	 * @param desconto <i>BigDecimal</i>
	 * @param acrescimo <i>BigDecimal</i>
	 * @param descAcresPerc <i>boolean</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void subtotalizarNaoFiscal(BigDecimal desconto, BigDecimal acrescimo, boolean descAcresPerc) throws CommException, EcfException;
	/**
	 * Método abstrato imprime o valor pago com determinada forma de pagamento
	 * @param codFormaPagamento <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @param observacao <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void efetuarPagamentoNaoFiscal(String codFormaPagamento, BigDecimal valor, String observacao) throws CommException, EcfException;
	/**
	 * Método abstrato finaliza cupom não fiscal aberto
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void fecharNaoFiscal() throws CommException, EcfException;
	/**
	 * Método abstrato canleca cupom não fiscal aberto ou o último cupom não fiscal finalizado
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção de ECF
	 */
	public abstract void cancelarNaoFiscal() throws CommException, EcfException;
	
	//Comandos de autenticação--------------------------------------------------
	/**
	 * Método abstrato imprime uma marca de autenticação em documentos
	 * @param linhasAvanco <i>int</i>
	 * @param linhaAdicional <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void autenticarDocumento(int linhasAvanco, String linhaAdicional) throws CommException, EcfException;
	
	// Cupom Não Fiscal --------------------------------------------------------
	/**
	 * Método abstrato emite comprovantes <i>(completo)</i>
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
	public abstract void emitirComprovante(String codFormaPagamento, BigDecimal valor, int COO, String cpfConsumidor, String nomeConsumidor, String enderecoConsumidor, String texto)throws CommException, EcfException;
	/**
	 * Método abstrato para abrir comprovantes
	 * @param codFormaPagamento <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @param COO <i>int</i>
	 * @param cpfConsumidor <i>String</i>
	 * @param nomeConsumidor <i>String</i>
	 * @param enderecoConsumidor <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção de ECF
	 */
	public abstract void abrirComprovante(String codFormaPagamento, BigDecimal valor, int COO, String cpfConsumidor, String nomeConsumidor, String enderecoConsumidor)throws CommException, EcfException;//66
	/**
	 * Método abstrato imprime linhas em um comprovante aberto
	 * @param texto <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void imprimirLinhaComprovante(String texto) throws CommException, EcfException;
	/**
	 * Método abstrato finaliza comprovante aberto
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção de ECF
	 */
	public abstract void fecharComprovante() throws CommException, EcfException;
	/**
	 * Método abstrato estorna comprovante emitido
	 * @param cpf <i>String</i>
	 * @param nome <i>String</i>
	 * @param endereco <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void estornarComprovante(String cpf, String nome, String endereco) throws CommException, EcfException;
	/**
	 * Método abstrato imprime segunda via do último comprovante emitido
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void imprimirSegundaVia() throws CommException, EcfException;
	/**
	 * Método abstrato reimprime último comprovante emitido
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void reimprimirCupomNaoFiscal() throws CommException, EcfException;
	
	/**
	 * Método inicializa comunicação com o ECF, carrega aliquotas, formas de pagamento, totalizados não fiscal e layouts de cheque
	 * @param porta <i>String</i>
	 * @param velocidade <i>Integer</i>
	 * @param bitsDados <i>Integer</i>
	 * @param paridade <i>Integer</i>
	 * @param bitsParada <i>Integer</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	protected void inicializacao(String porta, Integer velocidade, Integer bitsDados,
			Integer paridade, Integer bitsParada) throws CommException, EcfException{
		comm.abrirPorta(porta, velocidade, bitsDados, paridade, bitsParada);
		commEnabled = true;
		carregarAliquotas();
		carregarFormasPagamento();
		carregarTotalizadoresNaoFiscais();
		carregarLayoutCheque();
	}
	
	/**
	 * Método finaliza comunicação com o ECF
	 */
	protected void finalizacao(){
		comm.fechar();
	}

	
	//ALIQUOTAS
	/**
	 * Método busca todas as aliquotas cadastradas no ECF e retorna em uma lista
	 * @return List - Lista de aliquotas 
	 */
	public List<Aliquota> getAliquotasList() {
		return aliquotas;
	}
	
	/**
	 * Método carrega as aliquotas do ECF 
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void carregarAliquotas() throws CommException, EcfException{
		aliquotas.clear();
		aliquotas = getAliquotas();
	}
	
	/**
	 * Método abstrato carrega as aliquotas em uma lista
	 * @return Lista de aliquotas
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exeção do ECF
	 */
	protected abstract List<Aliquota> getAliquotas() throws CommException, EcfException;
	
	/**
	 * Método programa Aliquotas no ECF
	 * @param codigo <i>String</i>
	 * @param aliquota <i>BigDecimal</i>
	 * @param incidencia <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void programarAliquota(String codigo, BigDecimal aliquota, String incidencia) 
			throws CommException, EcfException{
		boolean cadastrar = true;
		for(Aliquota aliq : aliquotas){
			if(aliq.getCodigo().equals(codigo)){
				cadastrar = false;
				if((!BigDecimalUtils.isEqualTo(aliq.getAliquota(), aliquota)) ||
						(!aliq.getIncidencia().equalsIgnoreCase(incidencia))){
					throw new EcfException(EcfException.ERRO_ALIQUOTA_COM_DIVERGENCIA);
				}
			}
		}
		if(cadastrar){
			programarAliquotaAbstract(codigo, aliquota, incidencia);
			carregarAliquotas();
		}
	}
	/**
	 * Método abstrato programa aliquota no ECF
	 * @param codigo <i>String</i>
	 * @param aliquota <i>BigDecimal</i>
	 * @param incidencia <i>String</i>
	 * @throws CommException Exceção deo comunicação
	 * @throws EcfException Exceção do ECF
	 */
	protected abstract void programarAliquotaAbstract(String codigo, BigDecimal aliquota, String incidencia) 
			throws CommException, EcfException;
	
	/**
	 * Método busca uma aliquota a partir de um código
	 * @param codigo <i>String</i>
	 * @return Aliquota
	 */
	public Aliquota getAliquota(String codigo){
		for(Aliquota a : aliquotas){
			if(a.getCodigo().equals(codigo)){
				return a;
			}
		}
		return null;
	}
	
	//FORMA DE PAGAMENTO
	/**
	 * Método busca todas as formas de pagamento cadastradas no ECF e retorna em uma lista
	 * @return Lista de Formas de Pagamento
	 */
	public List<FormaPagamento> getFormasPagamentoList(){
		return formasPagamento;
	}
	
	/**
	 * Método carrega as formas de pagamento do ECF 
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void carregarFormasPagamento() throws CommException, EcfException{
		formasPagamento.clear();
		formasPagamento = getFormasPagamento();
	}
	/**
	 * Método abstrato carrega formas de pagemento para uma lista
	 * @return Lista de formas de pagamento
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	protected abstract List<FormaPagamento> getFormasPagamento() throws CommException, EcfException;
	
	/**
	 * Método programa Formas de Pagamento
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @param permiteVincular <i>boolean</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void programarFormaPagamento(String codigo, String descricao, boolean permiteVincular) 
			throws CommException, EcfException{
		
		FormaPagamento formaPagto = getFormaPagamento(codigo);
		if(formaPagto == null){
			
			
			programarFormaPagamentoAbstract(codigo, descricao, permiteVincular);
			formasPagamento.add(new FormaPagamento(codigo, descricao, permiteVincular, BigDecimalUtils.newMoeda()));
		}
	}
	/**
	 * Método abstrato programa Formas de Pagamento
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @param permiteVincular <i>boolean</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	protected abstract void programarFormaPagamentoAbstract(String codigo, String descricao, 
			boolean permiteVincular) throws EcfException, CommException;
	
	/**
	 * Método busca uma forma de pagamento a partir de um código
	 * @param codigo <i>String</i>
	 * @return Forma de Pagamento
	 */
	public FormaPagamento getFormaPagamento(String codigo){
		for(FormaPagamento f : formasPagamento){
			if(f.getCodigo().equals(codigo)){
				return f;
			}
		}	
		return null;
	}
	
	/**
	 * Método busca uma forma de pagamento a partir de uma descrição
	 * @param descricao <i>String</i>
	 * @return Forma de Pagamento
	 */
	public FormaPagamento getFormaPagamentoPorDescricao(String descricao){
		for(FormaPagamento f : formasPagamento){
			if(f.getDescricao().trim().equalsIgnoreCase(descricao)){
				return f;
			}
		}
		return null;
	}
	
	
	//TOTALIZADORES
	/**
	 * Método busca todas os totalizadores cadastradas no ECF e retorna em uma lista
	 * @return
	 */
	public List<TotalizadorNaoFiscal> getTotalizadorsNaoFiscaisList(){
		return totalizadoresNaoFiscais;
	}
	
	/**
	 * Método carrega os totalizadores não fiscais do ECF 
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void carregarTotalizadoresNaoFiscais() throws CommException, EcfException{
		totalizadoresNaoFiscais.clear();
		totalizadoresNaoFiscais = getTotalizadoresNaoFiscais();
	}
	/**
	 * Método abstrato carrega os totalizadore não fiscal em uma lista
	 * @return lista de totalizadores não fiscal
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	protected abstract List<TotalizadorNaoFiscal> getTotalizadoresNaoFiscais() 
			throws CommException, EcfException;
			
	/**
	 * Método programa totalizado não fiscal no ECF
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public void programarTotalizadorNaoFiscal(String codigo, String descricao) 
			throws CommException, EcfException{
		programarTotalizadorNaoFiscalAbstract(codigo, descricao);
		carregarTotalizadoresNaoFiscais();		
	}
	/**
	 * Método abstrato programa totalizado não fiscal no ECF
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	protected abstract void programarTotalizadorNaoFiscalAbstract(String codigo, String descricao) 
			throws CommException, EcfException;
	
	/**
	 * Método busca no ECF um totalizador não fiscal a partir de um código
	 * @param codigo <i>String</i>
	 * @return Totalizador Não Fiscal
	 */
	public TotalizadorNaoFiscal getTotalizadorNaoFiscal(String codigo){
		for(TotalizadorNaoFiscal t : totalizadoresNaoFiscais){
			if(t.getCodigo().equals(codigo)){
				return t;
			}
		}
		return null;
	}
	
	/**
	 * Método busca no ECF um totalizador não fiscal a partir de uma descrição
	 * @param descricao <i>String</i>
	 * @return Totalizador Não Fiscal
	 */
	public TotalizadorNaoFiscal getTotalizadorNaoFiscalPorDescricao(String descricao){
		for(TotalizadorNaoFiscal t : totalizadoresNaoFiscais){
			if(t.getDescricao().equals(descricao)){
				return t;
			}
		}
		return null;
	}
	
	//--------------------------------------------------------------------------------------------------------

	/**
	 * Método abstrato programa no ECF o nome da moeda no singular
	 * @param nome <i>String</i>
	 * @throws CommException Eceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void programarMoedaSingular(String nome) throws CommException, EcfException;
	/**
	 * Método abstrato programa no ECF o nome da moeda no plural
	 * @param nome <i>String</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void programarMoedaPlural(String nome) throws CommException, EcfException;
	/**
	 * Método abstrato imprime cheques no ECF de acordo com os modelos dos banco 
	 * @param banco <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @param favorecido <i>String</i>
	 * @param cidade <i>String</i>
	 * @param data <i>Date</i>
	 * @param observacao <i>Straing</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void imprimirCheque(String banco, BigDecimal valor, String favorecido, 
			String cidade, Date data, String observacao) throws CommException, EcfException;
	/**
	 * Método abstrato cancela a impressão no cheque. Esse método só será executado se o ECF estiver aguardando o Cheque
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void cancelarImpressaoCheque() throws CommException, EcfException;
	/**
	 * Método abstrato verifica se o ECF está aguardando a colocação do cheque para impressão
	 * @return boolean
	 * <li> True - Caso esteja aguardando
	 * <li> False - Caso não esteja aguardando
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract boolean isAguardandoCheque() throws CommException, EcfException;
	/**
	 * Método abstrato verifica se o ECF está imprimindo cheque
	 * @return boolean
	 * <li> True - Caso estaja imprimindo cheque
	 * <li> False - Caso não esteja imprimindo cheque
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract boolean isImprimindoCheque() throws CommException, EcfException;
	
	/**
	 * Método abstrato faz com que o ECF pule linhas em sua impressão
	 * @param qtdeLinhas <i>int</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void pularLinhas(int qtdeLinhas) throws CommException, EcfException;
	/**
	 * Método abstrato faz com que o ECF corte o papel de impressão
	 * @param corteParcial <i>boolean</i>
	 * @throws CommException Exceção de comunicação
	 * @throws EcfException Exceção do ECF
	 */
	public abstract void cortarPapel(boolean corteParcial) throws CommException, EcfException;
	/**
	 * Método abstrato Carrega os layouts de cheque em uma lista
	 * @return lista de Layouts de cheque
	 * @throws EcfException
	 */
	protected abstract List<LayoutCheque> carregarLayoutsChequeDefault() throws EcfException;
	
	//--------------------------------------------------------------------------------------------------------
	
	/**
	 * Método verifica se a porta esta habilitada
	 * @return boolean 
	 * <li> True - Se a porta esteja habilitada
	 * <li> False - Caso a porta esteja desabilitada
	 * @throws EcfException Exceção do ECF
	 */
	public boolean isCommEnabled() {
		return commEnabled;
	}
	
	
	//------------------------------------------------------------------------
	/**
	 * Método Carrega os layouts de cheque
	 * @throws EcfException Exceção do ECF
	 */
	public void carregarLayoutCheque()throws EcfException{
		layoutsCheque.clear();
		layoutsCheque = getLayoutsCheque();
	}
	
	/**
	 * Método carrega os layouts de cheques a partir de um XML
	 * @param xml <i>String</i>
	 * @throws EcfException Exceção do ECF
	 */
	public void carregarLayoutCheque(String xml)throws EcfException{
		layoutsCheque.clear();
		layoutsCheque = getLayoutsCheque(xml);
	}
	
	/**
	 * Método carrega os layouts de cheques em uma lista
	 * @param xml <i>String</i>
	 * @return Lista de Layouts de Cheques
	 * @throws EcfException Exceção do ECF
	 */
	protected List<LayoutCheque> getLayoutsCheque() throws EcfException{
		List<LayoutCheque> lista = null;
		List<LayoutCheque> result = new ArrayList<LayoutCheque>();
		try {
			String arq = FileUtils.carregar("EcfLayoutsCheque.xml");
			if (arq != null){
				XStream xStream = new XStream();
				lista = (List) xStream.fromXML(arq);
				for(LayoutCheque l: lista){
					if((l.getFabricante().equalsIgnoreCase(fabricante())) &&  
							(l.getModelo().equalsIgnoreCase(modelo()))){
						result.add(l);
					}
				}
				if (lista.isEmpty()){
					lista = null;
				}
			}
		} catch (Exception e) {
			lista = null;
			e.printStackTrace();
		}
		
		if(lista == null){
			result = carregarLayoutsChequeDefault();
			if(lista != null){
				XStream xStream = new XStream();
				String arq = xStream.toXML(result);
				try {
					FileUtils.salvar("EcfLayoutsCheque.xml", arq, false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * Método carrega os layouts de cheques em uma lista a partir de um XML
	 * @param xml <i>String</i>
	 * @return Lista de Layouts de Cheques
	 * @throws EcfException Exceção do ECF
	 */	
	protected List<LayoutCheque> getLayoutsCheque(String xml) throws EcfException{
		List<LayoutCheque> lista = null;
		try {
			if (xml != null){
				XStream xStream = new XStream();
				lista = (List) xStream.fromXML(xml);
				for(LayoutCheque l: lista){
					if((!l.getFabricante().equalsIgnoreCase(fabricante())) || 
							(!l.getModelo().equalsIgnoreCase(modelo()))){
						lista.remove(l);
					}
				}
				if (lista.isEmpty()){
					lista = null;
				}
			}
		} catch (Exception e) {
			lista = null;
			e.printStackTrace();
		}
		
		if(lista == null){
			lista = getLayoutsCheque();
		}
		return lista;
	}

	/**
	 * Método carrega os layouts de cheques a partir do codigo do banco
	 * @param banco <i>String</i>
	 * @return LayoutCheque
	 * @throws EcfException Exceção do ECF
	 */
	public LayoutCheque getLayoutCheque(String banco) throws EcfException{
		if(layoutsCheque != null){
			for(LayoutCheque l : layoutsCheque){
				if(l.getBanco().equals(banco)){
					return l;
				}
			}	
		}
		throw new EcfException(EcfException.ERRO_MODELO_CHEQUE_INEXISTENTE);
	}
	
	

	/**
	 * Método insere um novo layout de cheque
	 * @param layouts <i>List</i>
	 * @throws EcfException
	 */
	public void setLayouts(List<LayoutCheque> layouts) {
		this.layoutsCheque = layouts;
	}
	
	/**
	 * Método busca a Lista de Layouts de Cheques
	 * @return lista de Layouts de cheque
	 */
	public List<LayoutCheque> getLayouts() {
		return layoutsCheque;
	}
}
