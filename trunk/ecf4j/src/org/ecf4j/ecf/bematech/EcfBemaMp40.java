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
import org.ecf4j.ecf.CoordenadaCheque;
import org.ecf4j.ecf.EcfEstado;
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
 * Classe ECF específicas do modelo Bematech MP-40
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 * @extends EcfBematechAbstract
 * @see EcfBematechAbstract
 */
public class EcfBemaMp40 extends EcfBematechAbstract {

	
	@Override
	public boolean isHomologado() {
		return false;
	}
	
	@Override
	public String modelo() {
		return "MP-40";
	}

	@Override
	public Integer getNumColunas() {
		return 40;
	}

	//----------------------------------------------------------------------------
	//INFORMAÇÕES DO ECF
	@Override
	public void autenticarDocumento(int linhasAvanco, String linhaAdicional)
			throws CommException, EcfException {
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
		
		executaComando(preparaComando(ByteUtils.newByteArray(62, 48)));
		byte[] bs = getRetorno(1);
		
		if(!ByteUtils.getBit(bs[0], 2)){
			return EcfEstado.IMPRIMINDO_CHEQUE;
		}
		if(ByteUtils.getBit(bs[0], 3)){
			return EcfEstado.AGUARDANDO_CHEQUE;
		}
		/*
		if(ByteUtils.getBit(bs[0], 5)){
			return EcfEstado.CHEQUE_PRONTO;
		}
		*/
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
		return BigDecimalUtils.newMoeda();
	}
	
	@Override
	public BigDecimal getCancelamentoICMS() throws CommException, EcfException {
		return BigDecimalUtils.newMoeda();
	}
	
	@Override
	public BigDecimal getDescontoISS() throws CommException, EcfException {
		return BigDecimalUtils.newMoeda();
	}
	
	@Override
	public BigDecimal getDescontoICMS() throws CommException, EcfException {
		return BigDecimalUtils.newMoeda();
	}
	
	@Override
	public BigDecimal getAcrescimoICMS() throws CommException, EcfException {
		return BigDecimalUtils.newMoeda();
	}

	@Override
	public BigDecimal getAcrescimoISS() throws CommException, EcfException {
		return BigDecimalUtils.newMoeda();
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
	
	
	
	//----------------------------------------------------------------------------
	//RELATÓRIOS
	
	@Override
	public void leituraMemoriaFiscalDataSimplificado(Date dataInicial,
			Date dataFinal) throws EcfException, CommException {
		leituraMemoriaFiscalData(dataInicial, dataFinal);
	}

	@Override
	public String leituraMemoriaFiscalDataSimplificadoSerial(Date dataInicial,
			Date dataFinal) throws EcfException, CommException {
		return leituraMemoriaFiscalDataSerial(dataInicial, dataFinal);
	}
	
	@Override
	public void leituraMemoriaFiscalReducaoSimplificado(int reducaoInicial,
			int reducaoFinal) throws EcfException, CommException {
		leituraMemoriaFiscalReducao(reducaoInicial, reducaoFinal);
	}
	
	@Override
	public String leituraMemoriaFiscalReducaoSimplificadoSerial(
			int reducaoInicial, int reducaoFinal) throws EcfException,
			CommException {
		return leituraMemoriaFiscalReducaoSerial(reducaoInicial, reducaoFinal);
	}
	
	
	//----------------------------------------------------------------------------
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
		
		BigDecimal valorTotal = BigDecimalUtils.multiplyMoeda(valorUnitario, quantidade);
		
		desconto = BigDecimalUtils.divideMoeda(BigDecimalUtils.multiplyMoeda(valorTotal, desconto), 
				BigDecimalUtils.newMoeda(100));
		
		String sDesconto = BigDecimalUtils.bigDecimalToEcf(desconto, 4, 2);
		
		executaComando(preparaComando(ByteUtils.newByteArray(9),
				codigo + descricao + codAliquota + sQuantidade + 
				valor + sDesconto));
		verificaRetorno();
		
	}
	
	
	//----------------------------------------------------------------------------
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
	
	
	//----------------------------------------------------------------------------
	//CUPOM NÃO FISCAL(COMPROVANTE)
	
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
	
	//----------------------------------------------------------------------------
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
			descricao = new String(ByteUtils.subByteArray(arrayDesc, i*16, 16));
			codigo ++;
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
	
	
	//----------------------------------------------------------------------------
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
	
	//----------------------------------------------------------------------------
	
	@Override
	public void programarMoedaPlural(String nome) throws CommException,
			EcfException {
		nome = StringUtils.strToEcf(nome, 22);
		
		executaComando(preparaComando(ByteUtils.newByteArray(59), nome));
		verificaRetorno();		
	}

	@Override
	public void programarMoedaSingular(String nome) throws CommException,
			EcfException {
		nome = StringUtils.strToEcf(nome, 19);
		
		executaComando(preparaComando(ByteUtils.newByteArray(58), nome));
		verificaRetorno();
	}
	
	@Override
	public void imprimirCheque(String banco, BigDecimal valor, String favorecido,
			String cidade, Date data, String observacao) throws CommException, EcfException{
		
		String sValor = BigDecimalUtils.bigDecimalToEcf(valor, 14, 2);
		favorecido = StringUtils.strToEcf(favorecido, 45);
		cidade = StringUtils.strToEcf(cidade, 27);
		
		String dia = IntegerUtils.intToStr(DateTimeUtils.dayOfDate(data), 2);
		String mes = IntegerUtils.intToStr(DateTimeUtils.monthOfDate(data), 2);
		String ano = IntegerUtils.intToStr(DateTimeUtils.yearOfDate(data), 4);
	
        observacao = StringUtils.strToEcfLimiteLen(observacao, 120);
        
        String prm = sValor + favorecido + cidade + dia + mes + ano;
        
        LayoutCheque l = getLayoutCheque(banco);
        
    	
		if (l.equals("")){
			throw new EcfException(EcfException.ERRO_BANCO_INEXISTENTE);
		}
        
        


		//colunaValor + colunaExtenso1 + colunaExtenso2 + colunaFavorecido +
		//colunaLocal + colunaDia + colunaMes + colunaAno + linhaValor + linhaExtenso1 +
		//linhaExtenso2 + linhaFavorecido + linhaLocal;
        
		executaComando(preparaComando(ByteUtils.newByteArray(57),
			ByteUtils.encondeByteArray(
				prm.getBytes(), 
				ByteUtils.newByteArray(	l.getCoordenadaCheque("VALOR_X"),
										l.getCoordenadaCheque("EXT1_X"),
										l.getCoordenadaCheque("EXT2_X"),
										l.getCoordenadaCheque("FAV_X"),					
										l.getCoordenadaCheque("LOCAL_X"),				
										l.getCoordenadaCheque("DIA_X"),					
										l.getCoordenadaCheque("MES_X"),					
										l.getCoordenadaCheque("ANO_X"),					
										l.getCoordenadaCheque("VALOR_Y"),				
										l.getCoordenadaCheque("EXT1_Y"),				
										l.getCoordenadaCheque("EXT2_Y"),				
										l.getCoordenadaCheque("FAV_Y"),					
										l.getCoordenadaCheque("LOCAL_Y")),			
				observacao.getBytes())));
		verificaRetorno();
	}

	@Override
	public boolean isAguardandoCheque() throws CommException, EcfException {
		
		executaComando(preparaComando(ByteUtils.newByteArray(62, 48)));
		byte[] b = getRetorno(1);
		
		return (ByteUtils.getBit(b[0], 3));
	}
	
	@Override
	public boolean isImprimindoCheque() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(62, 48)));
		byte[] b = getRetorno(1);
		
		return (!ByteUtils.getBit(b[0], 2));
	}

	@Override
	public void cancelarImpressaoCheque() throws CommException, EcfException{
		executaComando(preparaComando(ByteUtils.newByteArray(62, 49)));
		verificaRetorno();
	}

	
	private LayoutCheque newLayoutCheque(String banco, int valorX, int ext1X, 
			int ext2X, int favX, int localX, int diaX, int mesX, int anoX,int valorY, 
			int ext1Y, int ext2Y, int favY, int localY) throws EcfException{
		LayoutCheque result = new LayoutCheque();
		result.setFabricante(fabricante());
		result.setModelo(modelo());
		result.setBanco(banco);
	
		
		if (valorY > 14) System.out.printf("\n\nErro layout cheque banco "+ banco);
		if (ext1Y > 14) System.out.printf("\n\nErro layout cheque banco "+ banco);
		if (ext2Y > 14) System.out.printf("\n\nErro layout cheque banco "+ banco);
		if (favY > 14) System.out.printf("\n\nErro layout cheque banco "+ banco);
		if (localY > 14) System.out.printf("\n\nErro layout cheque banco "+ banco);
		
		result.getCoordenadas().add(new CoordenadaCheque("VALOR_X", valorX));
		result.getCoordenadas().add(new CoordenadaCheque("EXT1_X", ext1X));
		result.getCoordenadas().add(new CoordenadaCheque("EXT2_X", ext2X));
		result.getCoordenadas().add(new CoordenadaCheque("FAV_X", favX));
		result.getCoordenadas().add(new CoordenadaCheque("LOCAL_X", localX));
		result.getCoordenadas().add(new CoordenadaCheque("DIA_X", diaX));
		result.getCoordenadas().add(new CoordenadaCheque("MES_X", mesX));
		result.getCoordenadas().add(new CoordenadaCheque("ANO_X", anoX));
		result.getCoordenadas().add(new CoordenadaCheque("VALOR_Y", valorY));
		result.getCoordenadas().add(new CoordenadaCheque("EXT1_Y", ext1Y));
		result.getCoordenadas().add(new CoordenadaCheque("EXT2_Y", ext2Y));
		result.getCoordenadas().add(new CoordenadaCheque("FAV_Y", favY));
		result.getCoordenadas().add(new CoordenadaCheque("LOCAL_Y", localY));
		return result;
	}
	
	@Override
	protected List<LayoutCheque> carregarLayoutsChequeDefault() throws EcfException	{
		
		try{
			List<LayoutCheque> lista = new ArrayList<LayoutCheque>();		
			
			lista.add(newLayoutCheque("000",51,4,1,5,6,60,65,81,1,6,8,11,14));
			lista.add(newLayoutCheque("001",51,10,1,6,18,50,54,71,2,5,8,10,12));
			lista.add(newLayoutCheque("003",49,8,1,5,18,52,55,72,1,5,7,9,12));
			lista.add(newLayoutCheque("004",52,9,1,5,18,50,53,72,2,6,9,11,13));
			lista.add(newLayoutCheque("006",56,10,1,5,15,43,48,72,1,6,8,10,13));
			lista.add(newLayoutCheque("008",56,17,1,7,18,50,55,71,3,6,9,11,13));
			lista.add(newLayoutCheque("021",52,12,1,4,18,49,53,71,2,7,9,11,13));
			lista.add(newLayoutCheque("022",52,7,1,4,15,44,49,71,2,6,8,10,13));
			lista.add(newLayoutCheque("024",51,7,1,5,18,48,52,72,1,5,7,9,12));
			lista.add(newLayoutCheque("027",51,10,1,6,18,50,54,71,2,5,8,10,12));
			lista.add(newLayoutCheque("028",55,6,1,5,18,50,53,71,1,5,8,10,12));
			lista.add(newLayoutCheque("029",55,12,1,4,18,50,55,72,1,6,8,10,13));
			lista.add(newLayoutCheque("031",51,10,1,6,18,50,54,71,2,5,8,10,12));
			lista.add(newLayoutCheque("032",51,10,1,6,18,50,54,71,2,5,8,10,12));
			lista.add(newLayoutCheque("033",48,17,1,6,18,46,50,71,2,6,8,11,13));
			lista.add(newLayoutCheque("034",49,14,1,4,15,45,57,71,1,5,7,9,11));
			lista.add(newLayoutCheque("035",51,10,1,6,18,50,54,71,2,5,8,10,12));
			lista.add(newLayoutCheque("036",51,10,1,6,18,50,54,71,2,5,8,10,12));
			lista.add(newLayoutCheque("037",51,10,1,6,18,50,54,71,2,5,8,10,12));
			lista.add(newLayoutCheque("038",56,10,1,4,18,51,56,72,2,7,10,12,14));
			lista.add(newLayoutCheque("039",51,10,1,6,18,50,54,71,2,5,8,10,12));
			lista.add(newLayoutCheque("041",56,9,1,4,18,54,61,72,3,7,9,12,14));
			lista.add(newLayoutCheque("047",52,8,1,5,18,47,50,72,1,5,7,10,12));
			lista.add(newLayoutCheque("048",51,10,1,6,18,50,54,71,2,5,8,10,12));
			lista.add(newLayoutCheque("059",50,15,1,5,18,55,59,72,1,5,7,9,11));
			lista.add(newLayoutCheque("070",54,5,1,5,18,48,52,72,2,6,8,10,12));
			lista.add(newLayoutCheque("104",56,13,1,4,18,48,53,72,1,4,7,10,12));
			lista.add(newLayoutCheque("106",52,12,1,5,18,52,55,71,2,7,9,11,13));
			lista.add(newLayoutCheque("151",54,6,1,4,18,47,52,71,1,5,7,10,12));
			lista.add(newLayoutCheque("153",51,9,1,5,18,51,55,72,1,5,8,10,13));
			lista.add(newLayoutCheque("168",53,5,1,5,18,54,57,71,2,6,8,11,13));
			lista.add(newLayoutCheque("200",52,6,1,5,18,47,52,71,1,5,7,10,12));
			lista.add(newLayoutCheque("201",52,11,1,4,18,47,51,71,1,5,7,9,11));
			lista.add(newLayoutCheque("206",56,14,1,6,18,53,56,72,1,6,8,10,13));
			lista.add(newLayoutCheque("207",50,4,1,5,18,48,52,71,2,6,8,11,13));
			lista.add(newLayoutCheque("211",48,11,1,5,18,52,56,71,3,7,9,12,14));
			lista.add(newLayoutCheque("215",55,6,1,5,18,51,54,71,2,5,8,10,13));
			lista.add(newLayoutCheque("220",56,9,1,5,18,49,53,71,2,5,8,10,12));
			lista.add(newLayoutCheque("230",50,12,1,5,18,54,58,71,2,5,8,10,13));
			lista.add(newLayoutCheque("231",52,12,1,5,18,53,58,72,2,6,8,10,12));
			lista.add(newLayoutCheque("237",50,1,1,4,18,50,54,71,2,6,9,11,14));
			lista.add(newLayoutCheque("244",48,14,1,4,18,49,53,71,3,6,9,11,13));
			lista.add(newLayoutCheque("254",51,9,1,5,18,53,56,71,1,5,8,11,14));
			lista.add(newLayoutCheque("275",51,7,1,4,18,46,52,68,3,8,10,12,14));
			lista.add(newLayoutCheque("282",56,12,1,5,18,50,54,71,2,6,8,10,13));
			lista.add(newLayoutCheque("291",51,10,1,6,18,50,54,71,2,5,8,10,12));
			lista.add(newLayoutCheque("294",51,10,1,6,18,50,54,71,2,5,8,10,12));
			lista.add(newLayoutCheque("302",51,7,1,5,18,47,51,71,2,6,8,10,13));
			lista.add(newLayoutCheque("308",51,10,1,6,18,50,54,71,2,5,8,10,12));
			lista.add(newLayoutCheque("320",54,6,1,4,18,48,51,72,2,5,8,10,13));
			lista.add(newLayoutCheque("334",54,6,1,4,18,54,57,71,2,6,8,10,12));
			lista.add(newLayoutCheque("341",54,8,1,5,18,50,54,72,2,6,9,12,15));
			lista.add(newLayoutCheque("346",54,12,1,5,18,54,57,71,2,5,8,10,12));
			lista.add(newLayoutCheque("347",53,15,1,4,18,47,51,72,2,6,9,11,14));
			lista.add(newLayoutCheque("351",52,14,1,5,18,55,58,72,1,5,7,10,12));
			lista.add(newLayoutCheque("353",52,7,1,5,18,53,58,71,2,5,7,10,12));
			lista.add(newLayoutCheque("356",51,10,1,6,18,50,54,71,2,5,8,10,12));
			lista.add(newLayoutCheque("369",47,7,1,5,18,51,55,71,2,6,8,10,12));
			lista.add(newLayoutCheque("370",52,6,1,5,18,47,50,71,1,5,7,10,12));
			lista.add(newLayoutCheque("372",51,7,1,4,18,46,49,71,2,6,8,11,13));
			lista.add(newLayoutCheque("376",54,7,1,4,18,54,58,72,2,6,8,10,12));
			lista.add(newLayoutCheque("388",46,9,1,6,18,48,52,72,2,6,9,11,14));
			lista.add(newLayoutCheque("389",52,6,1,5,18,53,58,72,2,7,9,12,14));
			lista.add(newLayoutCheque("392",49,12,1,5,18,54,58,72,2,5,7,11,13));
			lista.add(newLayoutCheque("394",51,5,1,5,18,51,55,71,1,5,7,9,13));
			lista.add(newLayoutCheque("399",54,12,1,4,18,52,57,72,1,5,7,10,12));
			lista.add(newLayoutCheque("409",55,12,1,4,23,52,58,71,4,7,9,11,13));
			lista.add(newLayoutCheque("415",54,12,1,6,18,50,54,72,3,7,10,12,14));
			lista.add(newLayoutCheque("420",54,8,1,4,18,50,54,72,2,6,8,10,13));
			lista.add(newLayoutCheque("422",51,10,1,6,18,50,54,71,2,5,8,10,12));
			lista.add(newLayoutCheque("424",51,10,1,6,18,50,54,71,2,5,8,10,12));
			lista.add(newLayoutCheque("434",56,8,1,5,18,50,54,72,2,6,9,11,13));
			lista.add(newLayoutCheque("453",54,12,1,5,18,51,56,72,3,7,10,12,14));
			lista.add(newLayoutCheque("456",48,11,1,5,18,47,50,71,2,6,8,10,12));
			lista.add(newLayoutCheque("464",51,10,1,6,18,50,54,71,2,5,8,10,12));
			lista.add(newLayoutCheque("472",53,12,1,5,18,50,53,71,2,6,9,10,14));
			lista.add(newLayoutCheque("477",55,8,1,5,18,52,57,72,3,7,9,11,14));
			lista.add(newLayoutCheque("479",53,7,1,5,18,50,53,71,2,6,8,10,12));
			lista.add(newLayoutCheque("483",52,8,1,5,18,47,50,71, 2,5,7,9,11));
			lista.add(newLayoutCheque("487",52,8,1,5,18,47,50,71, 2,5,7,9,11));
			//lista.add(newLayoutCheque("487",58,17,1,5,18,48,52,72, 2,6,8,11,13));
			
			lista.add(newLayoutCheque("494",51,9,1,5,18,50,53,71,2,6,8,10,13));
			lista.add(newLayoutCheque("602",56,10,1,3,18,47,52,66,2,5,7,10,13));
			lista.add(newLayoutCheque("603",51,10,1,6,18,50,54,71,2,5,8,10,12));
			lista.add(newLayoutCheque("607",51,9,1,5,18,53,56,72,2,5,8,10,12));
			lista.add(newLayoutCheque("610",55,15,1,5,18,53,58,71,1,6,8,10,12));
			lista.add(newLayoutCheque("630",49,5,1,5,18,47,52,71,1,6,8,10,13));
			lista.add(newLayoutCheque("718",51,7,1,5,18,48,53,71,1,6,8,10,13));
			lista.add(newLayoutCheque("756",51,10,1,6,18,50,54,71,2,5,8,10,12));
			


			return lista;//super.carregarLayoutsChequeDefault();
		
		} catch (Exception e) {	
			return null;
		}
	}
	
	
}
