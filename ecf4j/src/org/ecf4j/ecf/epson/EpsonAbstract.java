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
package org.ecf4j.ecf.epson;

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
import org.ecf4j.utils.comm.exceptions.CommException;

/**
 * Classe Abstrata de ECF Epson
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 * @extends EcfAbstract
 * @see EcfAbstract
 */
public class EpsonAbstract extends EcfAbstract {

	
	@Override
	public void abrirRelatorioGerencial() throws CommException, EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void abrirComprovante(String codFormaPagamento, BigDecimal valor,
			int COO, String cpfConsumidor, String nomeConsumidor,
			String enderecoConsumidor) throws CommException, EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void abrirCupom(String cpfCnpj, String nome, String endereco)
			throws CommException, EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void abrirGaveta() throws CommException, EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void abrirNaoFiscal(String cpfCnpj, String nomeConsumidor,
			String enderecoConsumidor) throws CommException, EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void autenticarDocumento(int linhasAvanco, String linhaAdicional)
			throws CommException, EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelarCupom() throws CommException, EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelarImpressaoCheque() throws CommException, EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelarItem(int item) throws CommException, EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelarNaoFiscal() throws CommException, EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelarUltimoItem() throws CommException, EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void cortarPapel(boolean corteParcial) throws CommException,
			EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void efetuarPagamento(String codFormaPagamento, BigDecimal valor,
			String observacao) throws CommException, EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void efetuarPagamentoNaoFiscal(String codFormaPagamento,
			BigDecimal valor, String observacao) throws CommException,
			EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void emitirComprovante(String codFormaPagamento, BigDecimal valor,
			int COO, String cpfConsumidor, String nomeConsumidor,
			String enderecoConsumidor, String texto) throws CommException,
			EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void emitirNaoFiscal(String codTotalizadorNaoFiscal,
			BigDecimal valor, String codFormaPagamento) throws CommException,
			EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void estornarComprovante(String cpf, String nome, String endereco)
			throws CommException, EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void executaComando(byte[] cmd) throws CommException,
			EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void fecharRelatorio() throws CommException, EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void fecharComprovante() throws CommException, EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void fecharCupom(String observacao) throws CommException,
			EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void fecharNaoFiscal() throws CommException, EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public BigDecimal getAcrescimoICMS() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getAcrescimoISS() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<Aliquota> getAliquotas() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCOO() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCRO() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCRZ() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getCancelamento() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getCancelamentoICMS() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getCancelamentoISS() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDataHora() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDataMovimento() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getDesconto() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getDescontoICMS() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getDescontoISS() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EcfEstado getEstado() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<FormaPagamento> getFormasPagamento() throws CommException,
			EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getGrandeTotal() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getGrandeTotalReducaoZ() throws CommException,
			EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumeroEcf() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumeroLoja() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getNumeroSerial() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumeroUltimoItem() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected byte[] getRetorno(int len) throws EcfException, CommException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<TotalizadorNaoFiscal> getTotalizadoresNaoFiscais()
			throws CommException, EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getVendaBruta() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getVendaBrutaReducaoZ() throws CommException,
			EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void imprimirLinhaComprovante(String texto) throws CommException,
			EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void imprimirSegundaVia() throws CommException, EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isGavetaAberta() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHorarioVerao() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMemoriaFiscalSemEspaco() throws CommException,
			EcfException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPermiteCancelamentoCupomFiscal() throws CommException,
			EcfException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPermiteCancelamentoNaoFiscal() throws CommException,
			EcfException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPermiteEstornoComprovante() throws CommException,
			EcfException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPoucoPapel() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSemPapel() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTruncando() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void leituraMemoriaFiscalData(Date dataInicial, Date dataFinal)
			throws EcfException, CommException {
		// TODO Auto-generated method stub

	}

	@Override
	public String leituraMemoriaFiscalDataSerial(Date dataInicial,
			Date dataFinal) throws EcfException, CommException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void leituraMemoriaFiscalDataSimplificado(Date dataInicial,
			Date dataFinal) throws EcfException, CommException {
		// TODO Auto-generated method stub

	}

	@Override
	public String leituraMemoriaFiscalDataSimplificadoSerial(Date dataInicial,
			Date dataFinal) throws EcfException, CommException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void leituraMemoriaFiscalReducao(int reducaoInicial, int reducaoFinal)
			throws EcfException, CommException {
		// TODO Auto-generated method stub

	}

	@Override
	public String leituraMemoriaFiscalReducaoSerial(int reducaoInicial,
			int reducaoFinal) throws EcfException, CommException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void leituraMemoriaFiscalReducaoSimplificado(int reducaoInicial,
			int reducaoFinal) throws EcfException, CommException {
		// TODO Auto-generated method stub

	}

	@Override
	public String leituraMemoriaFiscalReducaoSimplificadoSerial(
			int reducaoInicial, int reducaoFinal) throws EcfException,
			CommException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void leituraX() throws CommException, EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void linhaRelatorioGerencial(String texto) throws CommException,
			EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	protected byte[] preparaComando(byte[] cmd, String prm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void programarAliquotaAbstract(String codigo,
			BigDecimal aliquota, String incidencia) throws CommException,
			EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void programarFormaPagamentoAbstract(String codigo,
			String descricao, boolean permiteVincular) throws EcfException,
			CommException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void programarTotalizadorNaoFiscalAbstract(String codigo,
			String descricao) throws CommException, EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void pularLinhas(int qtdeLinhas) throws CommException, EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void reducaoZ(Date dataMovimentacao) throws CommException,
			EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void registrarItemNaoFiscal(String codTotalizadorNaoFiscal,
			BigDecimal valor) throws CommException, EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void reimprimirCupomNaoFiscal() throws CommException, EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHorarioVerao(boolean isHorarioVerao) throws CommException,
			EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void subtotalizarCupom(BigDecimal desconto, BigDecimal acrescimo,
			boolean descAcresPerc) throws CommException, EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void subtotalizarNaoFiscal(BigDecimal desconto,
			BigDecimal acrescimo, boolean descAcresPerc) throws CommException,
			EcfException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void verificaRetorno() throws EcfException, CommException {
		// TODO Auto-generated method stub

	}

	@Override
	public String fabricante() {
		return "Epson";
	}

	@Override
	public boolean isHomologado() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String modelo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void imprimirCheque(String banco, BigDecimal valor,
			String favorecido, String cidade, Date data, String observacao)
			throws CommException, EcfException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAguardandoCheque() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected byte[] preparaComando(byte[] cmd, byte[] prm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void programarMoedaPlural(String nome) throws CommException,
			EcfException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void programarMoedaSingular(String nome) throws CommException,
			EcfException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected List<LayoutCheque> carregarLayoutsChequeDefault() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void finalizar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inicializar(String porta) throws CommException, EcfException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isImprimindoCheque() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BigDecimal getAcrescimoReducaoZ() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getDescontoReducaoZ() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCOOReducaoZ() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getSangriaReducaoZ() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getSuprimentoReducaoZ() throws CommException,
			EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getAcrescimo() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCOOFinal() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCOOInicial() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCROReducaoZ() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getIsento() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public BigDecimal getNaoTributado() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public BigDecimal getSubstituicao() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getSubtotalCupom() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getValorPagoUltimoCupom() throws CommException, EcfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void venderItem(String codigo, String descricao, String codAliquota,
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade)
			throws CommException, EcfException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void venderItemAcrescimo(String codigo, String descricao,
			String codAliquota, BigDecimal quantidade,
			BigDecimal valorUnitario, String unidade, BigDecimal acrescimo)
			throws CommException, EcfException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void venderItemAcrescimoPerc(String codigo, String descricao,
			String codAliquota, BigDecimal quantidade,
			BigDecimal valorUnitario, String unidade, BigDecimal acrescimo)
			throws CommException, EcfException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void venderItemDesconto(String codigo, String descricao,
			String codAliquota, BigDecimal quantidade,
			BigDecimal valorUnitario, String unidade, BigDecimal desconto)
			throws CommException, EcfException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void venderItemDescontoPerc(String codigo, String descricao,
			String codAliquota, BigDecimal quantidade,
			BigDecimal valorUnitario, String unidade, BigDecimal desconto)
			throws CommException, EcfException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getNumColunas() {
		// TODO Auto-generated method stub
		return null;
	}

}
