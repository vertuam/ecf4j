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
import org.ecf4j.ecf.EcfEstado;
import org.ecf4j.ecf.FormaPagamento;
import org.ecf4j.ecf.TotalizadorNaoFiscal;
import org.ecf4j.ecf.exceptions.EcfException;
import org.ecf4j.utils.bigdecimals.BigDecimalUtils;
import org.ecf4j.utils.bytes.ByteUtils;
import org.ecf4j.utils.comm.exceptions.CommException;
import org.ecf4j.utils.datetimes.DateTimeUtils;
import org.ecf4j.utils.integers.IntegerUtils;
import org.ecf4j.utils.strings.StringUtils;

/**
 * Classe ECF específicas do modelo Bematech MP-20
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 * @extends EcfBematechAbstract
 * @see EcfBematechAbstract
 */
public class EcfBemaMp20 extends EcfBematechAbstract {
	
	
	@Override
	public boolean isHomologado() {
		return false;
	}

	@Override
	public String modelo() {
		return "MP-20";
	}
	
	@Override
	public Integer getNumColunas() {
		return 48;
	}
	
	//---------------------------------------------------------------------
	//INFORMAÇÕES DO ECF

	@Override
	public void autenticarDocumento(int linhasAvanco, String linhaAdicional)
			throws CommException, EcfException {
		//executaComando(preparaComando(ByteUtils.newByteArray(64, 1, 2, 4, 8, 16, 32, 64, 128, 64, 032, 16, 8, 4, 2, 1, 129, 129, 129)));
		//verificaRetorno();
		
		executaComando(preparaComando(ByteUtils.newByteArray(16)));
		verificaRetorno();
	}
	
	@Override
	public String getNumeroSerial() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(35, 00)));
		return StringUtils.ascToString(getRetorno(15));
	}
	
	@Override
	public EcfEstado getEstado() throws CommException, EcfException {
		if(!commEnabled){
			return EcfEstado.NAO_INICIALIZADO;
		}
		executaComando(preparaComando(ByteUtils.newByteArray(35, 17)));
		byte b = getRetorno(1)[0];
		if(ByteUtils.getBit(b, 3)){
			return EcfEstado.BLOQUEADO;
		}
		if(ByteUtils.getBit(b, 1)){
			return EcfEstado.EFETUANDO_PAGAMENTO;
		}
		if(ByteUtils.getBit(b, 0)){
			return EcfEstado.VENDENDO;
		}
		
		executaComando(preparaComando(ByteUtils.newByteArray(35, 27)));
		String dataMov = StringUtils.bcdToString(getRetorno(3));
		
		executaComando(preparaComando(ByteUtils.newByteArray(35, 26)));
		String dataImp = StringUtils.bcdToString(getRetorno(6));
		
		if(!dataMov.equals(dataImp.substring(0, 6))){
		//	return EcfEstado.REQUER_REDUCAOZ;
		}
		
		return EcfEstado.LIVRE;
	}
	
	@Override
	public BigDecimal getGrandeTotalReducaoZ() throws CommException,
			EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(62, 55)));
		byte [] b = getRetorno(308);
		BigDecimal valor = BigDecimalUtils.bcdToBigDecimal(ByteUtils.subByteArray(b, 1, 9), 2);
		return valor;
	}
	
	@Override
	public BigDecimal getCancelamentoISS() throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_COMANDO_INEXISTENTE);
	}
	
	@Override
	public BigDecimal getCancelamentoICMS() throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_COMANDO_INEXISTENTE);
	}
	
	@Override
	public BigDecimal getDescontoISS() throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_COMANDO_INEXISTENTE);
	}
	
	@Override
	public BigDecimal getDescontoICMS() throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_COMANDO_INEXISTENTE);
	}
	
	@Override
	public BigDecimal getAcrescimoICMS() throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_COMANDO_INEXISTENTE);
	}

	@Override
	public BigDecimal getAcrescimoISS() throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_COMANDO_INEXISTENTE);
	}
	
	@Override
	public BigDecimal getVendaBrutaReducaoZ() throws CommException,
			EcfException {
		throw new EcfException(EcfException.ERRO_COMANDO_INEXISTENTE);
	}
	
	@Override
	public BigDecimal getAcrescimoReducaoZ() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(62, 55)));
		byte[] b = getRetorno(308); 
		b = ByteUtils.subByteArray(b, 294, 7);
		return BigDecimalUtils.bcdToBigDecimal(b, 2);
	}

	@Override
	public BigDecimal getDescontoReducaoZ() throws CommException, EcfException  {
		executaComando(preparaComando(ByteUtils.newByteArray(62, 55)));
		byte[] b = getRetorno(308); 
		b = ByteUtils.subByteArray(b, 17, 7);
		return BigDecimalUtils.bcdToBigDecimal(b, 2);
	}
	
	@Override
	public int getCOOReducaoZ() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(62, 55)));
		byte[] b = getRetorno(308);
		b = ByteUtils.subByteArray(b, 284, 3);
		return IntegerUtils.bcdToInt(b);
	}

	@Override
	public BigDecimal getSangriaReducaoZ() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(62, 55)));
		byte[] b = getRetorno(308); 
		b = ByteUtils.subByteArray(b, 157, 7);
		return BigDecimalUtils.bcdToBigDecimal(b, 2);
	}

	@Override
	public BigDecimal getSuprimentoReducaoZ() throws CommException,
			EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(62, 55)));
		byte[] b = getRetorno(308); 
		b = ByteUtils.subByteArray(b, 164, 7);
		return BigDecimalUtils.bcdToBigDecimal(b, 2);
	}
	
	@Override
	public BigDecimal getAcrescimo() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(35, 30)));
		return BigDecimalUtils.bcdToBigDecimal(getRetorno(7), 2);
	}

	@Override
	public int getCOOFinal() throws CommException, EcfException {
		return (getCOO()-1);
	}

	@Override
	public int getCOOInicial() throws CommException, EcfException {
		if((getCOOReducaoZ() + 1) > getCOOFinal()){
			return 1;
		}
		else {
			return getCOOReducaoZ() + 1;
		}
	}

	@Override
	public BigDecimal getIsento() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(27)));
		byte[] b = getRetorno(219);
		return BigDecimalUtils.bcdToBigDecimal(ByteUtils.subByteArray(b, 112, 7), 2);
	}

	@Override
	public BigDecimal getNaoTributado() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(27)));
		byte[] b = getRetorno(219);
		return BigDecimalUtils.bcdToBigDecimal(ByteUtils.subByteArray(b, 119, 7), 2);
	}

	@Override
	public BigDecimal getSubstituicao() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(27)));
		byte[] b = getRetorno(219);
		return BigDecimalUtils.bcdToBigDecimal(ByteUtils.subByteArray(b, 126, 7), 2);
	}
	

	@Override
	public int getCROReducaoZ() throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_COMANDO_INEXISTENTE);
	}

	
	
	//---------------------------------------------------------------------
	//RETATÓRIOS

	@Override
	public void leituraMemoriaFiscalDataSimplificado(Date dataInicial,
			Date dataFinal) throws EcfException, CommException {
		String dataIni = DateTimeUtils.dateToString(dataInicial, MASK_DATE);
		String dataFim = DateTimeUtils.dateToString(dataFinal, MASK_DATE);
		executaComando(preparaComando(ByteUtils.newByteArray(8), dataIni + dataFim + "i"));
		verificaRetorno();
	}
	
	@Override
	public String leituraMemoriaFiscalDataSimplificadoSerial(Date dataInicial,
			Date dataFinal) throws EcfException, CommException {
		String dataIni = DateTimeUtils.dateToString(dataInicial, MASK_DATE);
		String dataFim = DateTimeUtils.dateToString(dataFinal, MASK_DATE);
		executaComando(preparaComando(ByteUtils.newByteArray(8), dataIni + dataFim + "r"));
		verificaRetorno();
		return comm.readString((byte)0x03);
	}
	
	@Override
	public void leituraMemoriaFiscalReducaoSimplificado(int reducaoInicial,
			int reducaoFinal) throws EcfException, CommException {
		String reducaoIni = IntegerUtils.intToStr(reducaoInicial, 4);
		String reducaoFim = IntegerUtils.intToStr(reducaoFinal, 4);
		executaComando(preparaComando(ByteUtils.newByteArray(8), "00" + reducaoIni + "00" + reducaoFim + "i"));
		verificaRetorno();		
	}
	
	@Override
	public String leituraMemoriaFiscalReducaoSimplificadoSerial(
			int reducaoInicial, int reducaoFinal) throws EcfException,
			CommException {
		String reducaoIni = IntegerUtils.intToStr(reducaoInicial, 4);
		String reducaoFim = IntegerUtils.intToStr(reducaoFinal, 4);
		executaComando(preparaComando(ByteUtils.newByteArray(8), "00" + reducaoIni + "00" + reducaoFim + "r"));
		verificaRetorno();
		return comm.readString((byte)0x03);
	}
	
	
	
	
	//---------------------------------------------------------------------
	//CUPOM FISCAL	
	
	@Override
	public void abrirCupom(String cpfCnpj, String nome, String endereco)
			throws CommException, EcfException {
		if(cpfCnpj.trim() != ""){
			cpfCnpj = StringUtils.strToEcf(cpfCnpj.trim(), 29);			
		}
		
		executaComando(preparaComando(
				ByteUtils.newByteArray(0), 
				cpfCnpj));
		verificaRetorno();
	}
	
	@Override
	public void venderItem(String codigo, String descricao, String codAliquota,
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade)
			throws CommException, EcfException {
		
		Aliquota a = getAliquota(codAliquota);
		if(a == null){
			throw new EcfException(EcfException.ERRO_ALIQUOTA_INEXISTENTE);
		}
		
		codigo = StringUtils.strToEcf(codigo, 13);
		descricao = StringUtils.strToEcf(descricao, 29);
		codAliquota = StringUtils.strToEcf(codAliquota, 2);
		String sQuantidade = BigDecimalUtils.bigDecimalToEcf(quantidade, 7, 3);
		String valor = BigDecimalUtils.bigDecimalToEcf(valorUnitario, 8, 2);
		
		unidade = StringUtils.strToEcf(unidade, 2);
		if(unidade != "UN"){
			executaComando(preparaComando(ByteUtils.newByteArray(62, 51), unidade));
		    verificaRetorno();
		}
		
		executaComando(preparaComando(ByteUtils.newByteArray(9),
				codigo + descricao + codAliquota + sQuantidade + 
				valor + "00000000"));
		verificaRetorno();		
	}

	@Override
	public void venderItemAcrescimo(String codigo, String descricao,
			String codAliquota, BigDecimal quantidade,
			BigDecimal valorUnitario, String unidade, BigDecimal acrescimo)
			throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_ECF_NAO_SUPORTA_ACRESCIMO);
	}

	@Override
	public void venderItemAcrescimoPerc(String codigo, String descricao,
			String codAliquota, BigDecimal quantidade,
			BigDecimal valorUnitario, String unidade, BigDecimal acrescimo)
			throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_ECF_NAO_SUPORTA_ACRESCIMO);
		
	}

	@Override
	public void venderItemDesconto(String codigo, String descricao,
			String codAliquota, BigDecimal quantidade,
			BigDecimal valorUnitario, String unidade, BigDecimal desconto)
			throws CommException, EcfException {
		
		Aliquota a = getAliquota(codAliquota);
		if(a == null){
			throw new EcfException(EcfException.ERRO_ALIQUOTA_INEXISTENTE);
		}
		
		codigo = StringUtils.strToEcf(codigo, 13);
		descricao = StringUtils.strToEcf(descricao, 29);
		codAliquota = StringUtils.strToEcf(codAliquota, 2);
		String sQuantidade = BigDecimalUtils.bigDecimalToEcf(quantidade, 7, 3);
		String valor = BigDecimalUtils.bigDecimalToEcf(valorUnitario, 8, 2);
		
		unidade = StringUtils.strToEcf(unidade, 2);
		if(unidade != "UN"){
			executaComando(preparaComando(ByteUtils.newByteArray(62, 51), unidade));
		    verificaRetorno();
		}
		
		String sDesconto = BigDecimalUtils.bigDecimalToEcf(desconto, 8, 2);
		
		executaComando(preparaComando(ByteUtils.newByteArray(9),
				codigo + descricao + codAliquota + sQuantidade + 
				valor + sDesconto));
		verificaRetorno();
	}

	@Override
	public void venderItemDescontoPerc(String codigo, String descricao,
			String codAliquota, BigDecimal quantidade,
			BigDecimal valorUnitario, String unidade, BigDecimal desconto)
			throws CommException, EcfException {
		
		Aliquota a = getAliquota(codAliquota);
		if(a == null){
			throw new EcfException(EcfException.ERRO_ALIQUOTA_INEXISTENTE);
		}
		
		codigo = StringUtils.strToEcf(codigo, 13);
		descricao = StringUtils.strToEcf(descricao, 29);
		codAliquota = StringUtils.strToEcf(codAliquota, 2);
		String sQuantidade = BigDecimalUtils.bigDecimalToEcf(quantidade, 7, 3);
		String valor = BigDecimalUtils.bigDecimalToEcf(valorUnitario, 8, 2);
		
		unidade = StringUtils.strToEcf(unidade, 2);
		if(unidade != "UN"){
			executaComando(preparaComando(ByteUtils.newByteArray(62, 51), unidade));
		    verificaRetorno();
		}
		
		String sDesconto = BigDecimalUtils.bigDecimalToEcf(desconto, 4, 2);
		
		executaComando(preparaComando(ByteUtils.newByteArray(9),
				codigo + descricao + codAliquota + sQuantidade + 
				valor + sDesconto));
		verificaRetorno();
		
	}
	
	//---------------------------------------------------------------------
	//CUPOM NÃO FISCAL
	

	private String cupomNaoFiscalCodTotalizadorNaoFiscal = null;
	private String cupomNaoFiscalCodFormaPagamento = null;
	private BigDecimal cupomNaoFiscalValor = null;
	
	@Override
	public void abrirNaoFiscal(String cpfCnpj, String nomeConsumidor,
			String enderecoConsumidor) throws CommException, EcfException {
		cupomNaoFiscalCodTotalizadorNaoFiscal = null;
		cupomNaoFiscalCodFormaPagamento = null;
		cupomNaoFiscalValor = null;		
	}
	
	@Override
	public void registrarItemNaoFiscal(String codTotalizadorNaoFiscal,
			BigDecimal valor) throws CommException, EcfException {
		if(!(cupomNaoFiscalCodTotalizadorNaoFiscal != null)){
			
			cupomNaoFiscalCodTotalizadorNaoFiscal = codTotalizadorNaoFiscal;
			cupomNaoFiscalValor = valor;			
		}
		else{
			throw new EcfException(EcfException.ERRO_ECF_NAO_SUPORTA_QUANTIDADE_ITEM_NAO_FISCAL);
		}
	}
	
	@Override
	public void subtotalizarNaoFiscal(BigDecimal desconto,
			BigDecimal acrescimo, boolean descAcresPerc) throws CommException,
			EcfException {
		
		if (BigDecimalUtils.isGreaterOfZero(desconto)){
			if (descAcresPerc){
				cupomNaoFiscalValor = BigDecimalUtils.subtractMoeda(cupomNaoFiscalValor, 
						(BigDecimalUtils.divideMoeda((BigDecimalUtils.multiplyMoeda(desconto,
						 cupomNaoFiscalValor)), BigDecimalUtils.newMoeda(100))));
			}
		    else{
		    	cupomNaoFiscalValor = BigDecimalUtils.subtractMoeda(cupomNaoFiscalValor, desconto);
		    }			
		}
		else{
			if (BigDecimalUtils.isGreaterOfZero(acrescimo)){
				if (descAcresPerc){
					cupomNaoFiscalValor = BigDecimalUtils.addMoeda(cupomNaoFiscalValor, 
							(BigDecimalUtils.divideMoeda((BigDecimalUtils.multiplyMoeda(acrescimo,
							 cupomNaoFiscalValor)), BigDecimalUtils.newMoeda(100))));
			    }
			    else{
			    	cupomNaoFiscalValor = BigDecimalUtils.addMoeda(cupomNaoFiscalValor, acrescimo);
			    }
			}			
		}
		
	}
	
	@Override
	public void efetuarPagamentoNaoFiscal(String codFormaPagamento,
			BigDecimal valor, String observacao) throws CommException,
			EcfException {
		if(BigDecimalUtils.isGreaterEqualTo(valor, cupomNaoFiscalValor)){
			cupomNaoFiscalCodFormaPagamento = codFormaPagamento;			
		}
		else{
			throw new EcfException(EcfException.ERRO_VALOR_CUPOM_NAO_FISCAL_INVALIDO);
		}
		
	}

	@Override
	public void fecharNaoFiscal() throws CommException, EcfException {
		if ((cupomNaoFiscalCodTotalizadorNaoFiscal != null)&&
				(cupomNaoFiscalCodFormaPagamento != null)&&
				(cupomNaoFiscalValor != null)){
			
			emitirNaoFiscal(cupomNaoFiscalCodTotalizadorNaoFiscal, 
					cupomNaoFiscalValor, 
					cupomNaoFiscalCodFormaPagamento);			
		}
		cupomNaoFiscalCodTotalizadorNaoFiscal = null;
		cupomNaoFiscalCodFormaPagamento = null;
		cupomNaoFiscalValor = null;
	}
	
	@Override
	public void cancelarNaoFiscal() throws CommException, EcfException {
		cupomNaoFiscalCodTotalizadorNaoFiscal = null;
		cupomNaoFiscalCodFormaPagamento = null;
		cupomNaoFiscalValor = null;
		
	}
	
	
	
	
	//---------------------------------------------------------------------
	//CUPOM NÃO FISCAL (COMPROVANTE)
	
	@Override
	public void abrirComprovante(String codFormaPagamento, BigDecimal valor,
			int COO, String cpfConsumidor, String nomeConsumidor,
			String enderecoConsumidor) throws CommException, EcfException {
		FormaPagamento f = getFormaPagamento(codFormaPagamento);
		if (f == null) {
			throw new EcfException(EcfException.ERRO_FORMA_PAGAMENTO_INEXISTENTE);
		}
		String formaPgto = StringUtils.strToEcf(f.getDescricao(), 16);
		
		String sValor = BigDecimalUtils.bigDecimalToEcf(valor, 14, 2);
		String numCupom = IntegerUtils.intToStr(COO, 6);//  StringUtils.strToEcf(COO, 6);
		
		executaComando(preparaComando(ByteUtils.newByteArray(66), formaPgto + sValor + 
				numCupom));
		verificaRetorno();
		
	}
	
	@Override
	public void imprimirSegundaVia() throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_COMANDO_INEXISTENTE);		
	}

	@Override
	public void reimprimirCupomNaoFiscal() throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_COMANDO_INEXISTENTE);
		
	}
	
	@Override
	public void estornarComprovante(String cpf, String nome, String endereco)
			throws CommException, EcfException {
		// TODO Auto-generated method stub
		
	}


	
	
	//---------------------------------------------------------------------
	//FORMAS DE PAGAMENTO
	
	@Override
	protected List<FormaPagamento> getFormasPagamento() throws CommException,
			EcfException {
		
		List<FormaPagamento> result = new ArrayList<FormaPagamento>();
		executaComando(preparaComando(ByteUtils.newByteArray(35, 32)));
		
		byte[] b = getRetorno(1925);
		int codigo = 0;
		byte[] arrayDesc = ByteUtils.subByteArray(b, 1, 832);
		String descricao = "";
		byte[] arrayValorTotal = ByteUtils.subByteArray(b, 833, 520);
		BigDecimal valor = BigDecimalUtils.newMoeda(0); 
		byte[] arrayVinculado = new byte[50];		
		arrayVinculado = ByteUtils.subByteArray(b, 1873, 52);
		boolean permiteVincular = false;
		String sCodigo = "";
		
		
		for(int i = 0; i < 52; i++){
			descricao = "";
			codigo ++;
			descricao = new String(ByteUtils.subByteArray(arrayDesc, i*16, 16));
			if(!descricao.trim().equals("")){
				if(arrayVinculado[i] == 1)
					permiteVincular = true;
				
				byte[] bv = ByteUtils.subByteArray(arrayValorTotal, i*10 , 10);
				valor = BigDecimalUtils.bcdToBigDecimal(bv, 4);
				sCodigo = IntegerUtils.intToStr(codigo, 2);
				
				result.add(new FormaPagamento(sCodigo, descricao, permiteVincular, valor));
			}	
		}
		
		return result;
	}
	
	@Override
	protected void programarFormaPagamentoAbstract(String codigo, String descricao,
			boolean permiteVincular) throws EcfException, CommException {
		descricao = StringUtils.strToEcf(descricao, 16);

		executaComando(preparaComando(ByteUtils.newByteArray(71), descricao));
		getRetorno(2);		
	}
	
	
	
	//---------------------------------------------------------------------
	//TOTALIZADORES
	@Override
	protected List<TotalizadorNaoFiscal> getTotalizadoresNaoFiscais()
			throws CommException, EcfException {
List<TotalizadorNaoFiscal> result = new ArrayList<TotalizadorNaoFiscal>();
		
		
		result.add(new TotalizadorNaoFiscal("SA", "Sangria", BigDecimalUtils.newMoeda(), "0000"));
		result.add(new TotalizadorNaoFiscal("SU", "Suprimento", BigDecimalUtils.newMoeda(), "0000"));
		
		executaComando(preparaComando(ByteUtils.newByteArray(35, 33)));
		byte[] arrayTotalizadores = getRetorno(1550);
		
		String descricao = "";
		BigDecimal valor = BigDecimalUtils.newMoeda();
		String contador = "";
		for(int i = 0; i < 50; i++){
			
			byte[] b = (ByteUtils.subByteArray(arrayTotalizadores, i*31, 31));
			contador = IntegerUtils.intToStr(b[0], 4);
			valor = BigDecimalUtils.bcdToBigDecimal((ByteUtils.subByteArray(b, 2, 10)), 4);
			descricao = new String(ByteUtils.subByteArray(b, 12, 19));
			
			if(!descricao.trim().equals("")){
				result.add(new TotalizadorNaoFiscal(IntegerUtils.intToStr(i+1, 2), descricao, valor, contador));
			}
		}
		return result;
	}

	@Override
	public void cancelarImpressaoCheque() throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_COMANDO_INEXISTENTE);
	}

	@Override
	public void imprimirCheque(String banco, BigDecimal valor,
			String favorecido, String cidade, Date data, String observacao)
			throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_COMANDO_INEXISTENTE);	
	}

	@Override
	public boolean isAguardandoCheque() throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_COMANDO_INEXISTENTE);
	}

	@Override
	public void programarMoedaPlural(String nome) throws CommException,
			EcfException {
		throw new EcfException(EcfException.ERRO_COMANDO_INEXISTENTE);
	}

	@Override
	public void programarMoedaSingular(String nome) throws CommException,
			EcfException {
		throw new EcfException(EcfException.ERRO_COMANDO_INEXISTENTE);
	}

	@Override
	public boolean isImprimindoCheque() throws CommException, EcfException {
		throw new EcfException(EcfException.ERRO_COMANDO_INEXISTENTE);
	}
	
	//---------------------------------------------------------------------
	
}
