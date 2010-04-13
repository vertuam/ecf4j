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
package org.ecf4j.ecf.bematech;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ecf4j.ecf.Aliquota;
import org.ecf4j.ecf.EcfAbstract;
import org.ecf4j.ecf.FormaPagamento;
import org.ecf4j.ecf.LayoutCheque;
import org.ecf4j.ecf.TotalizadorNaoFiscal;
import org.ecf4j.ecf.exceptions.EcfException;
import org.ecf4j.utils.bigdecimals.BigDecimalUtils;
import org.ecf4j.utils.bytes.ByteUtils;
import org.ecf4j.utils.comm.exceptions.CommException;
import org.ecf4j.utils.datetimes.DateTimeUtils;
import org.ecf4j.utils.integers.IntegerUtils;
import org.ecf4j.utils.strings.StringUtils;

/**
 * Classe Abstrata para Bematech ECF
 * Implementa o protocolo 1 de comunicação da Bematech
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 * @extends EcfAbstract
 * @see EcfAbstract
 */
public abstract class EcfBematechAbstract extends EcfAbstract {

	private static final int CODIGO_PROTOCOLO = 27;
	protected static final String MASK_DATE = "ddMMyy";
	protected static final String MASK_DATE_TIME = "ddMMyyHHmmss";
	
	private byte ack = 0;
	private byte st1 = 0;
	private byte st2 = 0;
	
	@Override
	public void inicializar(String porta) throws CommException, EcfException {
		inicializacao(porta, 9600, 8, 0, 1);
	}
	
	@Override
	public void finalizar() {
		finalizacao();
		
	}

	@Override
	protected byte[] preparaComando(byte[] cmd, byte[] prm){
		int lenByteArray = prm.length +cmd.length + 7;
		int somaCmd = CODIGO_PROTOCOLO;
		int lenCmd = 2;
		
		for (int i = 0; i < cmd.length;i++){
			somaCmd += (int)cmd[i];
		}
		
		for(int i = 0; i < prm.length; i++){
			somaCmd += (int) prm[i];
		}
		
		lenCmd += cmd.length + prm.length + 1;
		byte nbl = (byte)(lenCmd & 0xFF); 
		byte nbh = (byte)((lenCmd >> 8) & 0xFF);  
		byte csl = (byte)(somaCmd & 0xFF);
		byte csh = (byte)((somaCmd >> 8) & 0xFF);
		
		byte[] result = new byte[lenByteArray];
		int i = 0;
		
		result[i++] = (byte) 2;
		result[i++] = nbl;
		result[i++] = nbh;
		result[i++] = (byte) CODIGO_PROTOCOLO;
		
		for(int j = 0; j < cmd.length; j++){
			result[i++] = (byte)cmd[j]; 
		}
		
		for(int j = 0; j < prm.length; j++){
			result[i++] = (byte) prm[j];
		}		
		
		result[i++] = csl;
		result[i++] = csh;
		result[i++] = (byte) 3;
		
		return result;
	}

	@Override
	protected void executaComando(byte[] cmd) throws CommException,
			EcfException {
	
		comm.write(cmd);
		
	}

	@Override
	protected byte[] getRetorno(int len) throws EcfException, CommException {
		
		byte[] retorno = comm.readDireto(len+3);
		byte[] result = null;
		ack = retorno[0];
		if(ack != 6){
			switch (ack) {
			case 0:
				throw new EcfException(EcfException.ERRO_IMPRESSORA_NAO_RESPONDE);
				
			case 21:
				throw new EcfException(EcfException.ERRO_COMANDO_NAO_RECONHECIDO);
			
			default:
				throw new EcfException(EcfException.ERRO_RETORNO_INVALIDO);
			}
		}
		else{
			if (len > 0){
				result = ByteUtils.subByteArray(retorno, 1, len);

			}
			st1 = retorno[len+1];
			st2 = retorno[len+2];
			// Verificação do st1
			if (ByteUtils.getBit(st1, 7))
				throw new EcfException(EcfException.ERRO_FIM_DO_PAPEL);
			/*if (ByteUtils.getBit(st1, 6))
				throw new EcfException(EcfException.ERRO_POUCO_PAPEL);*/
			if (ByteUtils.getBit(st1, 5))
				throw new EcfException(EcfException.ERRO_NO_RELOGIO);
			if (ByteUtils.getBit(st1, 4))
				throw new EcfException(EcfException.ERRO_NO_EQUIPAMENTO_ECF);
			if (ByteUtils.getBit(st1, 3))
				throw new EcfException(EcfException.ERRO_COMANDO_NAO_RECONHECIDO);
			if (ByteUtils.getBit(st1, 2))
				throw new EcfException(EcfException.ERRO_COMANDO_INEXISTENTE);
			/*if (ByteUtils.getBit(st1, 1))
				throw new EcfException(EcfException.ERRO_CUPOM_ABERTO);*/
			if (ByteUtils.getBit(st1, 0))
				throw new EcfException(EcfException.ERRO_QUANTIDADE_PARAMETROS_INVALIDO);
			// Verificação do st2
			if (ByteUtils.getBit(st2, 7))
				throw new EcfException(EcfException.ERRO_TIPO_PARAMETRO_INVALIDO);
			if (ByteUtils.getBit(st2, 6))
				throw new EcfException(EcfException.ERRO_MEMORIA_FISCAL_LOTADA);
			if (ByteUtils.getBit(st2, 5))
				throw new EcfException(EcfException.ERRO_NO_EQUIPAMENTO_ECF);
			if (ByteUtils.getBit(st2, 4))
				throw new EcfException(EcfException.ERRO_ALIQUOTA_NAO_PROGRAMADA);
			if (ByteUtils.getBit(st2, 3))
				throw new EcfException(EcfException.ERRO_ALIQUOTAS_PRORAMADAS_LOTADA);
			if (ByteUtils.getBit(st2, 2))
				throw new EcfException(EcfException.ERRO_CANCELAMENTO_NAO_PERMITIDO);
			if (ByteUtils.getBit(st2, 1))
				throw new EcfException(EcfException.ERRO_CNPJ_IE_NAO_PROGRAMADOS);
			if (ByteUtils.getBit(st2, 0))
				throw new EcfException(EcfException.ERRO_COMANDO_NAO_EXECUTADO);	
			//comm.clear();
		}
		
		
		return result;
	}

	@Override
	protected void verificaRetorno() throws EcfException, CommException {
		
		getRetorno(0);	 

	}
	@Override
	public String fabricante() {
		return "Bematech";
	}
	
	//------------------------------------------------------------------------------------------------
	//INFORMAÇÕES DO ECF
	@Override
	public boolean isPoucoPapel() {
		return (ByteUtils.getBit(st1, 6));
	}

	@Override
	public boolean isSemPapel() {
		return (ByteUtils.getBit(st1, 7));
	}
	
	@Override
	public boolean isPermiteCancelamentoCupomFiscal() throws CommException, EcfException{
		executaComando(preparaComando(ByteUtils.newByteArray(35, 17)));
		byte b = getRetorno(1)[0];
		return 	ByteUtils.getBit(b, 5);
	}
	@Override
	public boolean isMemoriaFiscalSemEspaco() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(35, 17)));
		byte b = getRetorno(1)[0];
		return 	ByteUtils.getBit(b, 7);
	}

	@Override
	public boolean isPermiteCancelamentoNaoFiscal() throws CommException, EcfException{
		executaComando(preparaComando(ByteUtils.newByteArray(35, 65)));
		byte b = getRetorno(1)[0];
		return 	ByteUtils.getBit(b, 5);
	}
	/* Método implementado nas classes específicas
	@Override
	public void autenticarDocumento(int linhasAvanco, String linhaAdicional) 
			throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/

	@Override
	public boolean isPermiteEstornoComprovante() throws CommException, EcfException{
		executaComando(preparaComando(ByteUtils.newByteArray(35, 65)));
		byte b = getRetorno(1)[0];
		return 	ByteUtils.getBit(b, 6);
	}

	@Override
	public boolean isHorarioVerao() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(35, 17)));
		return ByteUtils.getBit(getRetorno(1)[0], 2);
	}

	@Override
	public void setHorarioVerao(boolean isHorarioVerao) throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(35, 17)));
		if (ByteUtils.getBit(getRetorno(1)[0], 2) != isHorarioVerao){
			executaComando(preparaComando(ByteUtils.newByteArray(18)));
			verificaRetorno();	
		}
		
	}
	
	@Override
	public boolean isTruncando() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(35, 28)));
		byte b = getRetorno(1)[0];
		return (b == 0x00);	
	}
	
	/* Método implementado nas classes específicas
	@Override
	public String getNumeroSerial() throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/

	@Override
	public int getCOO() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(30)));
		return IntegerUtils.bcdToInt(getRetorno(3));
	}
	
	@Override
	public int getCRO() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(35, 10)));
		return IntegerUtils.bcdToInt(getRetorno(2));
	}

	@Override
	public int getCRZ() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(35, 9)));
		return IntegerUtils.bcdToInt(getRetorno(2));
	}
	
	@Override
	public int getNumeroEcf() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(35, 14)));
		return IntegerUtils.bcdToInt(getRetorno(2));
	}
	
	@Override
	public int getNumeroLoja() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(35, 15)));
		return IntegerUtils.bcdToInt(getRetorno(2));
	}
	
	@Override
	public Date getDataMovimento() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(35, 27)));
		String data = StringUtils.bcdToString(getRetorno(3));
		return DateTimeUtils.stringToDate(data, MASK_DATE);
	}
	
	@Override
	public Date getDataHora() throws CommException, EcfException{
		executaComando(preparaComando(ByteUtils.newByteArray(35, 23)));
		return DateTimeUtils.stringToDate(StringUtils.bcdToString(getRetorno(6)), MASK_DATE_TIME);
	}
	
	@Override
	public BigDecimal getGrandeTotal() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(35, 03)));
		return BigDecimalUtils.bcdToBigDecimal(getRetorno(9), 2);
	}
	
	@Override
	public BigDecimal getVendaBruta() throws CommException, EcfException {
		BigDecimal valorGtUltimaReducao = getGrandeTotalReducaoZ();
		BigDecimal valorGtAtual = getGrandeTotal();
		return BigDecimalUtils.subtractMoeda(valorGtAtual, valorGtUltimaReducao);
	}
	
	@Override
	public int getNumeroUltimoItem() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(35, 12)));
		return IntegerUtils.bcdToInt(getRetorno(2));
	}
	
	@Override
	public boolean isGavetaAberta() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(23)));
		byte b = getRetorno(1)[0];
		if(isGavetaSinalInvertido()){
			return (b != 0);
		}
		else{
			return (b == 0);
		}
	}
	
	@Override
	public void abrirGaveta() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(22), "100"));
		verificaRetorno();		
	}
	/* Método implementado nas classes específicas
	@Override
	public EcfEstado getEstado() throws CommException, EcfException {
		
	
	}*/

	/* Método implementado nas classes específicas
	@Override
	public BigDecimal getGrandeTotalReducaoZ() throws CommException,
			EcfException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);	
	}
	*/
	
	/* Método implementado nas classes específicas
	@Override
	public BigDecimal getAcrecimaoReducaoZ() throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/

	/* Método implementado nas classes específicas
	@Override
	public BigDecimal getCancelamentoReducaoZ() throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/

	/* Método implementado nas classes específicas
	@Override
	public BigDecimal getDescontoReducaoZ() throws CommException, EcfException  {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/
	
	/* Método implementado nas classes específicas
	@Override
	public int getCOOReducaoZ() throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/

	/* Método implementado nas classes específicas
	@Override
	public BigDecimal getSangriaReducaoZ() throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/

	/* Método implementado nas classes especifícas
	@Override
	public BigDecimal getSuprimentoReducaoZ() throws CommException,
			EcfException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/	
	
	
	/* Método implementado nas classes específicas
	@Override
	public BigDecimal getCancelamentoISS() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(87)));
		byte[] b = getRetorno(436);
		b = ByteUtils.subByteArray(b, 189, 7);
		return BigDecimalUtils.bcdToMoeda(b);
	}
	*/
	
	/* Método implementado nas classes específicas
	@Override
	public BigDecimal getCancelamentoICMS() throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/

	@Override
	public BigDecimal getDescontoISS() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(87)));
		byte[] b = getRetorno(436);
		b = ByteUtils.subByteArray(b, 175, 7);
		return BigDecimalUtils.bcdToMoeda(b);
	}

	@Override
	public BigDecimal getDescontoICMS() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(87)));
		byte[] b = getRetorno(436);
		b = ByteUtils.subByteArray(b, 154, 7);
		return BigDecimalUtils.bcdToMoeda(b);
	}
	
	@Override
	public BigDecimal getAcrescimoICMS() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(87)));
		byte[] b = getRetorno(436);
		b = ByteUtils.subByteArray(b, 161, 7);
		return BigDecimalUtils.bcdToMoeda(b);
	}

	@Override
	public BigDecimal getAcrescimoISS() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(87)));
		byte[] b = getRetorno(436);
		b = ByteUtils.subByteArray(b, 182, 7);
		return BigDecimalUtils.bcdToMoeda(b);
	}
	
	/* Método implementado nas classes específicas
	@Override
	public BigDecimal getVendaBrutaReducaoZ() throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/
	
	@Override
	public BigDecimal getCancelamento() throws CommException,
			EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(35, 04)));
		return BigDecimalUtils.bcdToBigDecimal(getRetorno(7), 2);	
	}
	
	@Override
	public BigDecimal getDesconto() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(35, 05)));
		return BigDecimalUtils.bcdToBigDecimal(getRetorno(7), 2);	
	}
	
	@Override
	public BigDecimal getSubtotalCupom() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(29)));
		return BigDecimalUtils.bcdToBigDecimal(getRetorno(7), 2);
	}
	
	@Override
	public BigDecimal getValorPagoUltimoCupom() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(35, 22)));
		return BigDecimalUtils.bcdToBigDecimal(getRetorno(7), 2);
	}

	//---------------------------------------------------------------------------------------------
	//RELATÓRIOS
	@Override
	public void leituraX() throws CommException, EcfException{
		
		executaComando(preparaComando(ByteUtils.newByteArray(6))); 
		verificaRetorno();
	}
	
	@Override
	public void reducaoZ(Date dataMovimentacao) throws CommException, EcfException {
			executaComando(preparaComando(ByteUtils.newByteArray(5), 
			DateTimeUtils.dateToString(dataMovimentacao, MASK_DATE_TIME)));
			verificaRetorno();
	}
	
	@Override
	public void leituraMemoriaFiscalData(Date dataInicial, Date dataFinal)
			throws EcfException, CommException {
		String dataIni = DateTimeUtils.dateToString(dataInicial, MASK_DATE);
		String dataFim = DateTimeUtils.dateToString(dataFinal, MASK_DATE);
		executaComando(preparaComando(ByteUtils.newByteArray(8), dataIni + dataFim + "I"));
		verificaRetorno();
	}
	
	@Override
	public String leituraMemoriaFiscalDataSerial(Date dataInicial,
			Date dataFinal) throws EcfException, CommException {
		String dataIni = DateTimeUtils.dateToString(dataInicial, MASK_DATE);
		String dataFim = DateTimeUtils.dateToString(dataFinal, MASK_DATE);
		executaComando(preparaComando(ByteUtils.newByteArray(8), dataIni + dataFim + "R"));
		verificaRetorno();
		return comm.readString((byte)0x03);
	}
	
	/* Método implementado nas classes específicas
	@Override
	public void leituraMemoriaFiscalDataSimplificado(Date dataInicial,
			Date dataFinal) throws EcfException, CommException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/	
	
	@Override
	public void leituraMemoriaFiscalReducao(int reducaoInicial, int reducaoFinal) throws EcfException, CommException{
		String reducaoIni = IntegerUtils.intToStr(reducaoInicial, 4);
		String reducaoFim = IntegerUtils.intToStr(reducaoFinal, 4);
		executaComando(preparaComando(ByteUtils.newByteArray(8), "00" + reducaoIni + "00" + reducaoFim + "I"));
		verificaRetorno();
	}
	
	/* Método implementado nas classes específicas
	@Override
	public String leituraMemoriaFiscalDataSimplificadoSerial(Date dataInicial,
			Date dataFinal) throws EcfException, CommException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/
	
	@Override
	public String leituraMemoriaFiscalReducaoSerial(int reducaoInicial,
			int reducaoFinal) throws EcfException, CommException {
		String reducaoIni = IntegerUtils.intToStr(reducaoInicial, 4);
		String reducaoFim = IntegerUtils.intToStr(reducaoFinal, 4);
		executaComando(preparaComando(ByteUtils.newByteArray(8), "00" + reducaoIni + "00" + reducaoFim + "R"));
		verificaRetorno();
		return comm.readString((byte)0x03);
	}
	/* Método implementado nas classes específicas
	@Override
	public void leituraMemoriaFiscalReducaoSimplificado(int reducaoInicial,
			int reducaoFinal) throws EcfException, CommException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
		
	}
	*/
	
	/* Método implementado nas classes específicas
	@Override
	public String leituraMemoriaFiscalReducaoSimplificadoSerial(
			int reducaoInicial, int reducaoFinal) throws EcfException,
			CommException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/
		
	@Override
	public void abrirRelatorioGerencial() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(20)));
		verificaRetorno();
	}
	
	@Override
	public void linhaRelatorioGerencial(String texto) throws CommException, EcfException{
		int len = 620;
		String p = "";
		int i = 0;
		
		p = StringUtils.strToEcf(texto, i++, len);
		
		while(!p.equals("")){
			executaComando(preparaComando(ByteUtils.newByteArray(20), p));
			verificaRetorno();
			p = StringUtils.strToEcf(texto, i++, len);
		}
	}
	
	@Override
	public void fecharRelatorio() throws CommException, EcfException{
		executaComando(preparaComando(ByteUtils.newByteArray(21)));
		verificaRetorno();
	}
	//--------------------------------------------------------------------------------------
		
	//CUPOM FISCAL
	
	/* Método implementado nas classes específicas
	@Override
	public void abrirCupom(String cpfCnpj, String nome, String endereco) throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/
	
	/* Método implementado nas classes específicas
	@Override
	public void venderItem(String codigo, String descricao, String codAliquota, 
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade, BigDecimal desconto,
			BigDecimal acrescimo, boolean descAcresPerc) throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/
	
	/* Método implementado nas classes específicas
	@Override
	public void venderItem(String codigo, String descricao, String codAliquota,
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade)
			throws CommException, EcfException {
		// TODO Auto-generated method stub
		
	}
	*/
	
	/* Método implementado nas classes específicas
	@Override
	public void venderItemAcrescimo(String codigo, String descricao,
			String codAliquota, BigDecimal quantidade,
			BigDecimal valorUnitario, String unidade, BigDecimal acrescimo)
			throws CommException, EcfException {
		// TODO Auto-generated method stub
		
	}
	*/

	/* Método implementado nas classes específicas
	@Override
	public void venderItemAcrescimoPerc(String codigo, String descricao,
			String codAliquota, BigDecimal quantidade,
			BigDecimal valorUnitario, String unidade, BigDecimal acrescimo)
			throws CommException, EcfException {
		// TODO Auto-generated method stub
		
	}
	*/

	/* Método implementado nas classes específicas
	@Override
	public void venderItemDesconto(String codigo, String descricao,
			String codAliquota, BigDecimal quantidade,
			BigDecimal valorUnitario, String unidade, BigDecimal desconto)
			throws CommException, EcfException {
		// TODO Auto-generated method stub
		
	}
	*/

	/* Método implementado nas classes específicas
	@Override
	public void venderItemDescontoPerc(String codigo, String descricao,
			String codAliquota, BigDecimal quantidade,
			BigDecimal valorUnitario, String unidade, BigDecimal desconto)
			throws CommException, EcfException {
		// TODO Auto-generated method stub
		
	}*/	
	
	@Override
	public void subtotalizarCupom(BigDecimal desconto, BigDecimal acrescimo, boolean descAcresPerc) throws CommException, EcfException {
		String sDescontoAcrescimo = "d" + BigDecimalUtils.bigDecimalToEcf(BigDecimalUtils.newMoeda(0), 14, 2);
		
		if (BigDecimalUtils.isGreaterOfZero(desconto)){
		
			if (descAcresPerc){
				sDescontoAcrescimo = "D" + BigDecimalUtils.bigDecimalToEcf(desconto, 4, 2);
			}
		    else{
		    	sDescontoAcrescimo = "d" + BigDecimalUtils.bigDecimalToEcf(desconto, 14, 2);
		    }			
		}
		else{
			if (BigDecimalUtils.isGreaterOfZero(acrescimo)){
				if (descAcresPerc){
			    	sDescontoAcrescimo = "A" + BigDecimalUtils.bigDecimalToEcf(acrescimo, 4, 2);
			    }
			    else{
			    	sDescontoAcrescimo = "a" + BigDecimalUtils.bigDecimalToEcf(acrescimo, 14, 2);
			    }
			}			
		}
		
		executaComando(preparaComando(ByteUtils.newByteArray(32),
				sDescontoAcrescimo));	  
		verificaRetorno();
	}
	
	@Override
	public void efetuarPagamento(String codFormaPagamento, BigDecimal valor, 
			String observacao) throws CommException, EcfException {
		
		FormaPagamento f = getFormaPagamento(codFormaPagamento);
		if (f == null) {
			throw new EcfException(EcfException.ERRO_FORMA_PAGAMENTO_INEXISTENTE);
		}
				
		codFormaPagamento = StringUtils.strToEcf(codFormaPagamento, 2);
		String sValor = BigDecimalUtils.bigDecimalToEcf(valor, 14, 2);
		observacao = StringUtils.strToEcfLimiteLen(observacao, 80);
		
		executaComando(preparaComando(ByteUtils.newByteArray(72),
				codFormaPagamento + sValor + observacao));
		verificaRetorno();
	}
	
	@Override
	public void fecharCupom(String observacao) throws CommException, EcfException {
		
		observacao = StringUtils.strToEcfLimiteLen(observacao, 491)+"\n";
		
		executaComando(preparaComando(ByteUtils.newByteArray(34),
				observacao));
		verificaRetorno();
	}
	
	@Override
	public void cancelarCupom() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(14), ""));
		verificaRetorno();
	}

	@Override
	public void cancelarItem(int item) throws CommException, EcfException {
		String sItem = IntegerUtils.intToStr(item, 4);
		executaComando(preparaComando(ByteUtils.newByteArray(31), sItem));
		verificaRetorno();
	}
	
	@Override
	public void cancelarUltimoItem() throws CommException, EcfException  {
		
		executaComando(preparaComando(ByteUtils.newByteArray(13)));
		verificaRetorno();
	}
	
	//------------------------------------------------------------------------------
	
	//CUPOM NÃO FISCAL
	/* Método implementado nas classes especifícas
	@Override
	public void abrirNaoFiscal(String cpfCnpj, String nomeConsumidor,
			String enderecoConsumidor) throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}*/
	
	@Override
	public void emitirNaoFiscal(String codTotalizadorNaoFiscal, BigDecimal valor, String codFormaPagamento) 
			throws CommException, EcfException {
		
		TotalizadorNaoFiscal t = getTotalizadorNaoFiscal(codTotalizadorNaoFiscal);
		
		if (t == null) {
			throw new EcfException(EcfException.ERRO_TOTALIZADOR_NAO_FISCAL_INEXISTENTE);
		}
				
		codTotalizadorNaoFiscal = StringUtils.strToEcf(codTotalizadorNaoFiscal, 2);
		String sValor = BigDecimalUtils.bigDecimalToEcf(valor, 14, 2);
		
		FormaPagamento f = getFormaPagamento(codFormaPagamento);
		if (f == null) {
			throw new EcfException(EcfException.ERRO_FORMA_PAGAMENTO_INEXISTENTE);
		}
		String formaPagamento = StringUtils.strToEcf(f.getDescricao(), 16);
		executaComando(preparaComando(ByteUtils.newByteArray(25), codTotalizadorNaoFiscal + sValor + formaPagamento));
		verificaRetorno();
	}
	
	/* Método implementado nas classes específicas
	@Override
	public void registrarItemNaoFiscal(String codTotalizadorNaoFiscal, BigDecimal valor) throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/
	
	/* Método implementado nas classes específicas
	@Override
	public void subtotalizarNaoFiscal(BigDecimal desconto, BigDecimal acrescimo, boolean descAcresPerc) throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/
	
	/* Método implementado nas classes específicas
	@Override
	public void efetuarPagamentoNaoFiscal(String codFormaPagamento, 
			BigDecimal valor, String observacao) throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/
	
	/* Método implementado nas classes específicas
	@Override
	public void fecharNaoFiscal() throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/
	
	/* Método implementado nas classes específicas
	@Override
	public void cancelarNaoFiscal() throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/
	
	
	
	//-------------------------------------------------------------------------------
	//CUPOM NÃO FISCAL (COMPROVANTE)	
	
	/* Método implementado nas classes específicas
	@Override
	public void abrirComprovante(String codFormaPagamento, BigDecimal valor,
			int COO, String cpfConsumidor, String nomeConsumidor,
			String enderecoConsumidor) throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);

	}
	*/
	
	@Override
	public void emitirComprovante(String codFormaPagamento, BigDecimal valor, int COO, String cpfConsumidor, String nomeConsumidor, String enderecoConsumidor, String texto)throws EcfException, CommException{
		abrirComprovante(codFormaPagamento, valor, COO, cpfConsumidor, nomeConsumidor, enderecoConsumidor);
		imprimirLinhaComprovante(texto);
		fecharComprovante();
	}
	
	@Override
	public void imprimirLinhaComprovante(String texto) throws CommException, EcfException  {
		int len = 620;
		String p = "";
		int i = 0;
		
		p = StringUtils.strToEcf(texto, i++, len);
		
		while(!p.equals("")){
			executaComando(preparaComando(ByteUtils.newByteArray(67), p));
			verificaRetorno();
			p = StringUtils.strToEcf(texto, i++, len);
		}
	}
	
	@Override
	public void fecharComprovante() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(21)));
		verificaRetorno();		
	}
	
	/* Método implementado nas classes específicas
	@Override
	public void estornarComprovante(String cpf, String nome, String endereco)
			throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);

	}
	*/

	/* Método implementado nas classes específicas
	@Override
	public void imprimirSegundaVia() throws CommException, EcfException{
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/
	
	/* Método implementado nas classes específicas
	@Override
	public void reimprimirCupomNaoFiscal() throws CommException, EcfException{
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/
	
	//-----------------------------------------------------------------------------
	
	//ALIQUOTAS
	@Override
	protected List<Aliquota> getAliquotas() throws CommException, EcfException{
		List<Aliquota> result = new ArrayList<Aliquota>();

		//Não tributados
		result.add(new Aliquota("F1", BigDecimalUtils.newMoeda(), getSubstituicao(), "P", "F"));
		result.add(new Aliquota("I1", BigDecimalUtils.newMoeda(), getIsento(), "P", "I"));
		result.add(new Aliquota("N1", BigDecimalUtils.newMoeda(), getNaoTributado(), "P", "N"));
		
		//FLAGS DE VINCULAÇÃO AO ISS
		executaComando(preparaComando(ByteUtils.newByteArray(35, 29)));
		byte[] flagsISS = getRetorno(2);
		
		executaComando(preparaComando(ByteUtils.newByteArray(26)));
		byte[] b = getRetorno(33);
		
		executaComando(preparaComando(ByteUtils.newByteArray(27)));
		byte[] acumulados = getRetorno(219);
		acumulados = ByteUtils.subByteArray(acumulados, 0, 112);
		
		int qtdeAliq = b[0];
		for(int i = 0; i < qtdeAliq; i++){
			String codigo = IntegerUtils.intToStr(i+1, 2);
			BigDecimal aliquota = BigDecimalUtils.bcdToBigDecimal(
					ByteUtils.subByteArray(b, i * 2 + 1, 2), 2);
			
			BigDecimal valorAcumulado = BigDecimalUtils.bcdToBigDecimal(ByteUtils.subByteArray(acumulados, i * 7, 7), 2);
			
			String incidencia = "P";
			if (i < 8){
				incidencia = (ByteUtils.getBit(flagsISS[0], 7-i) ? "S" : "P");
			}else{
				incidencia = (ByteUtils.getBit(flagsISS[1], 7-(i-8)) ? "S" : "P");				
			}
			result.add(new Aliquota(codigo, aliquota, valorAcumulado, incidencia, "T"));
		}

		return result;
	}
	
	@Override
	protected void programarAliquotaAbstract(String codigo, BigDecimal aliquota,
			String incidencia) throws CommException, EcfException {
		String sAliq = BigDecimalUtils.bigDecimalToEcf(aliquota, 4, 2);
		incidencia = (incidencia.equalsIgnoreCase("S") ? "1" : "0");
		executaComando(preparaComando(ByteUtils.newByteArray(7), sAliq + incidencia));
		verificaRetorno();
	}
	
	//-----------------------------------------------------------------------------
	
	//FORMAS DE PAGAMENTO
	/* Mátodo implementado nas classes específicas
	@Override
	protected List<FormaPagamento> getFormasPagamento() throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/
	
	
	/* Método implementado nas classes específicas
	@Override
	protected void programarFormaPagamentoAbstract(String codigo, String descricao,
			boolean permiteVincular) throws EcfException, CommException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);		
	}
	*/
		
	//-----------------------------------------------------------------------------
	
	//TOTALIZADORES NÃO FISCAIS
	/* Método implementado nas classes específicas
	@Override
	protected List<TotalizadorNaoFiscal> getTotalizadoresNaoFiscais() throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/
	@Override
	protected void programarTotalizadorNaoFiscalAbstract(String codigo, String descricao)
			throws CommException, EcfException {
		
		String sIndice = StringUtils.strToEcf(codigo, 2); 
		
		descricao = StringUtils.strToEcf(descricao, 19);
		executaComando(preparaComando(ByteUtils.newByteArray(40), sIndice + descricao));
		verificaRetorno();	
		
	}
	
	//-----------------------------------------------------------------------------
	
	/* Método implementado ans classes especifícas
	@Override
	public void cancelarImpressaoCheque() throws CommException, EcfException{
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/
	
	/* Método implementado nas classes especifícas
	@Override
	public void imprimirCheque(String banco, BigDecimal valor, String favorecido,
			String cidade, Date data, String observacao) throws CommException, EcfException{
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/

	/* Método implementaod nas classes especifícas
	@Override
	public boolean isChequePronto() throws CommException, EcfException{
		throw new EcfException(EcfException.ERRO_ABSTRACT);
	}
	*/

	@Override
	public void cortarPapel(boolean corteParcial) throws CommException,
			EcfException {
		throw new EcfException(EcfException.ERRO_COMANDO_INEXISTENTE);
	}

	@Override
	public void pularLinhas(int qtdeLinhas) throws CommException, EcfException{
		throw new EcfException(EcfException.ERRO_COMANDO_INEXISTENTE);
	}

	@Override
	protected List<LayoutCheque> carregarLayoutsChequeDefault() throws EcfException {
		return null;
	}
}
