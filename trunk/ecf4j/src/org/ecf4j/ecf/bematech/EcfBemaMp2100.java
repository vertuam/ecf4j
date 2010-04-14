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
 *Classe ECF específicas do modelo Bematech MP-2100
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 * @extends EcfBematechAbstract
 * @see EcfBematechAbstract
 */
public class EcfBemaMp2100 extends EcfBematechAbstract {
	

	@Override
	public boolean isHomologado() {
		return false;
	}

	@Override
	public String modelo() {
		return "MP-2100";
	}
	
	@Override
	public Integer getNumColunas() {
		return 48;
	}
	
	//----------------------------------------------------------------------
	//INFORMAÇÕES DO ECF

	@Override
	public void autenticarDocumento(int linhasAvanco, String linhaAdicional)
			throws CommException, EcfException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getNumeroSerial() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(35, 40)));
		return StringUtils.ascToString(getRetorno(20));
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
		
		executaComando(preparaComando(ByteUtils.newByteArray(35, 23)));
		String dataImp = StringUtils.bcdToString(getRetorno(6));
		
		if((!dataMov.equals(dataImp.substring(0, 6))) && (!dataMov.equals("000000"))){
			return EcfEstado.REQUER_REDUCAOZ;
		}
		
		executaComando(preparaComando(ByteUtils.newByteArray(35, 65)));
		b = getRetorno(1)[0];
		if(ByteUtils.getBit(b, 0)){
			return EcfEstado.IMPRIMINDO_NAO_FISCAL;
		}
		if(ByteUtils.getBit(b, 1)){
			return EcfEstado.IMPRIMINDO_COMPROVANTE;
		}
		if(ByteUtils.getBit(b, 2)){
			return EcfEstado.IMPRIMINDO_RELATORIO;
		}
		
		return EcfEstado.LIVRE;
	}
	
	@Override
	public BigDecimal getCancelamentoISS() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(87)));
		byte[] b = getRetorno(436);
		b = ByteUtils.subByteArray(b, 189, 7);
		return BigDecimalUtils.bcdToMoeda(b);
	}
	
	@Override
	public BigDecimal getGrandeTotalReducaoZ() throws CommException,
			EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(35, 68)));
		return BigDecimalUtils.bcdToBigDecimal(getRetorno(9), 2);
	}
	
	@Override
	public BigDecimal getCancelamentoICMS() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(87)));
		byte[] b = getRetorno(436);
		b = ByteUtils.subByteArray(b, 168, 7);
		return BigDecimalUtils.bcdToMoeda(b);
	}
	
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

	@Override
	public BigDecimal getVendaBrutaReducaoZ() throws CommException,
			EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(35, 73)));	
		byte[] b = getRetorno(18);
		byte[] b1 = ByteUtils.subByteArray(b, 0, 9);
		byte[] b2 = ByteUtils.subByteArray(b, 9, 9);
		BigDecimal bd1 = BigDecimalUtils.bcdToMoeda(b1);
		BigDecimal bd2 = BigDecimalUtils.bcdToMoeda(b2);
		return (BigDecimalUtils.subtractMoeda(bd2, bd1));
	}
	
	@Override
	public BigDecimal getAcrescimoReducaoZ() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(88)));
		byte[] b = getRetorno(621);
		BigDecimal icms = BigDecimalUtils.bcdToBigDecimal(ByteUtils.subByteArray(b, 327, 7), 2);
		BigDecimal iss = BigDecimalUtils.bcdToBigDecimal(ByteUtils.subByteArray(b, 334, 7), 2);
		return BigDecimalUtils.addMoeda(icms, iss);
	}

	@Override
	public int getCOOReducaoZ() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(88)));
		byte[] b = getRetorno(621);
		b = ByteUtils.subByteArray(b, 5, 3);
		return IntegerUtils.bcdToInt(b);
	}

	@Override
	public BigDecimal getDescontoReducaoZ() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(88)));
		byte[] b = getRetorno(621);
		BigDecimal icms = BigDecimalUtils.bcdToBigDecimal(ByteUtils.subByteArray(b, 313, 7), 2);
		BigDecimal iss = BigDecimalUtils.bcdToBigDecimal(ByteUtils.subByteArray(b, 320, 7), 2);
		return BigDecimalUtils.addMoeda(icms, iss);
	}

	@Override
	public BigDecimal getSangriaReducaoZ() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(88)));
		byte[] b = getRetorno(621);
		b = ByteUtils.subByteArray(b, 551, 7);
		return BigDecimalUtils.bcdToBigDecimal(b, 2);
	}

	@Override
	public BigDecimal getSuprimentoReducaoZ() throws CommException,
			EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(88)));
		byte[] b = getRetorno(621);
		b = ByteUtils.subByteArray(b, 558, 7);
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
		if (getCRO() == getCROReducaoZ()){
			return getCOOReducaoZ() + 1;
		} else {
			return 1;
		}
	}

	@Override
	public int getCROReducaoZ() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(88)));
		byte[] b = getRetorno(621);
		return IntegerUtils.bcdToInt(ByteUtils.subByteArray(b, 1, 2));
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

	//----------------------------------------------------------------------
	//RELATÓRIOS
	
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
	
	
	
	//----------------------------------------------------------------------
	//CUPOM FISCAL
	
	@Override
	public void abrirCupom(String cpfCnpj, String nome, String endereco)
			throws CommException, EcfException {
		String consumidor = "";
		
		if(cpfCnpj.trim() != ""){
			consumidor += StringUtils.strToEcf(cpfCnpj.trim(), 29);			
		}
		
		if(nome.trim() != ""){
			consumidor += StringUtils.strToEcf(nome.trim(), 30);
		}
		
		if(endereco.trim() != ""){
			consumidor += StringUtils.strToEcf(endereco.trim(), 80);
		}
		
		executaComando(preparaComando(
				ByteUtils.newByteArray(0), 
				consumidor));
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
		
		codigo = StringUtils.strToEcfLimiteLen(codigo, 48);
		codigo += (char)0;
		
		descricao = StringUtils.strToEcfLimiteLen(descricao, 200);
		descricao += (char)0;
		
		codAliquota = StringUtils.strToEcf(codAliquota, 2);
		unidade = StringUtils.strToEcf(unidade, 2);
		
		String valor = BigDecimalUtils.bigDecimalToEcf(valorUnitario, 9, 3);
	    String sQuantidade = BigDecimalUtils.bigDecimalToEcf(quantidade, 7, 3);
		
		executaComando(preparaComando(ByteUtils.newByteArray(63),
				codAliquota + valor + sQuantidade + "0000000000" + "0000000000" + 
				"0000000000000000000000" + unidade + codigo + descricao));
		verificaRetorno();
	}

	@Override
	public void venderItemAcrescimo(String codigo, String descricao,
			String codAliquota, BigDecimal quantidade,
			BigDecimal valorUnitario, String unidade, BigDecimal acrescimo)
			throws CommException, EcfException {
		
		Aliquota a = getAliquota(codAliquota);
		if(a == null){
			throw new EcfException(EcfException.ERRO_ALIQUOTA_INEXISTENTE);
		}
		
		codigo = StringUtils.strToEcfLimiteLen(codigo, 48);
		codigo += (char)0;
		
		descricao = StringUtils.strToEcfLimiteLen(descricao, 200);
		descricao += (char)0;
		
		codAliquota = StringUtils.strToEcf(codAliquota, 2);
		unidade = StringUtils.strToEcf(unidade, 2);
		
		String valor = BigDecimalUtils.bigDecimalToEcf(valorUnitario, 9, 3);
	    String sQuantidade = BigDecimalUtils.bigDecimalToEcf(quantidade, 7, 3);
	    
	    String sAcrescimo = BigDecimalUtils.bigDecimalToEcf(acrescimo, 10, 2);
	    				
		executaComando(preparaComando(ByteUtils.newByteArray(63),
				codAliquota + valor + sQuantidade + "0000000000" + sAcrescimo + 
				"0000000000000000000000" + unidade + codigo + descricao));
		verificaRetorno();
		
	}

	@Override
	public void venderItemAcrescimoPerc(String codigo, String descricao,
			String codAliquota, BigDecimal quantidade,
			BigDecimal valorUnitario, String unidade, BigDecimal acrescimo)
			throws CommException, EcfException {
		
		Aliquota a = getAliquota(codAliquota);
		if(a == null){
			throw new EcfException(EcfException.ERRO_ALIQUOTA_INEXISTENTE);
		}
		
		codigo = StringUtils.strToEcfLimiteLen(codigo, 48);
		codigo += (char)0;
		
		descricao = StringUtils.strToEcfLimiteLen(descricao, 200);
		descricao += (char)0;
		
		codAliquota = StringUtils.strToEcf(codAliquota, 2);
		unidade = StringUtils.strToEcf(unidade, 2);
		
		String valor = BigDecimalUtils.bigDecimalToEcf(valorUnitario, 9, 3);
	    String sQuantidade = BigDecimalUtils.bigDecimalToEcf(quantidade, 7, 3);
		
	    BigDecimal valorTotal = BigDecimalUtils.multiplyMoeda(valorUnitario, quantidade);
	    
	    acrescimo = BigDecimalUtils.divideMoeda((BigDecimalUtils.multiplyMoeda(valorTotal, acrescimo)), 
	    		BigDecimalUtils.newMoeda(100));
	    
	    String sAcrescimo = BigDecimalUtils.bigDecimalToEcf(acrescimo, 10, 2);
	    
	    executaComando(preparaComando(ByteUtils.newByteArray(63),
				codAliquota + valor + sQuantidade + "0000000000" + sAcrescimo + 
				"0000000000000000000000" + unidade + codigo + descricao));
		verificaRetorno();		
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
		
		codigo = StringUtils.strToEcfLimiteLen(codigo, 48);
		codigo += (char)0;
		
		descricao = StringUtils.strToEcfLimiteLen(descricao, 200);
		descricao += (char)0;
		
		codAliquota = StringUtils.strToEcf(codAliquota, 2);
		unidade = StringUtils.strToEcf(unidade, 2);
		
		String valor = BigDecimalUtils.bigDecimalToEcf(valorUnitario, 9, 3);
	    String sQuantidade = BigDecimalUtils.bigDecimalToEcf(quantidade, 7, 3);
	    
	    String sDesconto = BigDecimalUtils.bigDecimalToEcf(desconto, 10, 2);
	    				
		executaComando(preparaComando(ByteUtils.newByteArray(63),
				codAliquota + valor + sQuantidade + sDesconto + "0000000000" + 
				"0000000000000000000000" + unidade + codigo + descricao));
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
		
		codigo = StringUtils.strToEcfLimiteLen(codigo, 48);
		codigo += (char)0;
		
		descricao = StringUtils.strToEcfLimiteLen(descricao, 200);
		descricao += (char)0;
		
		codAliquota = StringUtils.strToEcf(codAliquota, 2);
		unidade = StringUtils.strToEcf(unidade, 2);
		
		String valor = BigDecimalUtils.bigDecimalToEcf(valorUnitario, 9, 3);
	    String sQuantidade = BigDecimalUtils.bigDecimalToEcf(quantidade, 7, 3);
		
	    BigDecimal valorTotal = BigDecimalUtils.multiplyMoeda(valorUnitario, quantidade);
	    
	    desconto = BigDecimalUtils.divideMoeda((BigDecimalUtils.multiplyMoeda(valorTotal, desconto)), 
	    		BigDecimalUtils.newMoeda(100));
	    
	    String sDesconto = BigDecimalUtils.bigDecimalToEcf(desconto, 10, 2);
	    
	    executaComando(preparaComando(ByteUtils.newByteArray(63),
				codAliquota + valor + sQuantidade + sDesconto + "0000000000" + 
				"0000000000000000000000" + unidade + codigo + descricao));
		verificaRetorno();
		
	}		
	
	//----------------------------------------------------------------------
	//CUPOM NÃO FISCAL

	@Override
	public void abrirNaoFiscal(String cpfCnpj, String nomeConsumidor,
			String enderecoConsumidor) throws CommException, EcfException {
		String cpf = StringUtils.strToEcf(cpfCnpj, 29);
		String nome = StringUtils.strToEcf(nomeConsumidor, 30);
		String endereco = StringUtils.strToEcf(enderecoConsumidor, 80);
		
		executaComando(preparaComando(ByteUtils.newByteArray(77), cpf + nome + endereco));
		verificaRetorno();
		
	}
	
	@Override
	public void registrarItemNaoFiscal(String codTotalizadorNaoFiscal,
			BigDecimal valor) throws CommException, EcfException {
		
		TotalizadorNaoFiscal t = getTotalizadorNaoFiscal(codTotalizadorNaoFiscal);
		if(t == null){
			throw new EcfException(EcfException.ERRO_TOTALIZADOR_NAO_FISCAL_INEXISTENTE);
		}
		
		codTotalizadorNaoFiscal = StringUtils.strToEcf(codTotalizadorNaoFiscal, 2);
		String sValor = BigDecimalUtils.bigDecimalToEcf(valor, 14, 2);
		executaComando(preparaComando(ByteUtils.newByteArray(78), codTotalizadorNaoFiscal + sValor));
		verificaRetorno();
	}
	
	@Override
	public void subtotalizarNaoFiscal(BigDecimal desconto,
			BigDecimal acrescimo, boolean descAcresPerc) throws CommException,
			EcfException {
		String sDescontoAcrescimo = "d00000000000000";
		
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
			    	sDescontoAcrescimo = "a" + BigDecimalUtils.bigDecimalToEcf(desconto, 14, 2);
			    }
			}			
		}
		
		executaComando(preparaComando(ByteUtils.newByteArray(79),
				sDescontoAcrescimo));	  
		verificaRetorno();
		
	}
	
	@Override
	public void efetuarPagamentoNaoFiscal(String codFormaPagamento,
			BigDecimal valor, String observacao) throws CommException,
			EcfException {
		
		codFormaPagamento = StringUtils.strToEcf(codFormaPagamento, 2);
		String sValor = BigDecimalUtils.bigDecimalToEcf(valor, 14, 2);
		observacao = StringUtils.strToEcfLimiteLen(observacao, 80);
		
		executaComando(preparaComando(ByteUtils.newByteArray(72), codFormaPagamento + sValor + observacao));
		verificaRetorno();		
	}

	@Override
	public void fecharNaoFiscal() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(34)));
		verificaRetorno();		
	}
	
	@Override
	public void cancelarNaoFiscal() throws CommException, EcfException {
		executaComando(preparaComando(ByteUtils.newByteArray(81)));
		verificaRetorno();		
	}
	
	
	
	
	
	//----------------------------------------------------------------------
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
		if (cpfConsumidor.length() > 0){
			cpfConsumidor = StringUtils.strToEcf(cpfConsumidor, 29);
		}
		if (nomeConsumidor.length() > 0){
			nomeConsumidor = StringUtils.strToEcf(nomeConsumidor, 30);
		}
		if (enderecoConsumidor.length() > 0){
			enderecoConsumidor = StringUtils.strToEcf(enderecoConsumidor, 80);
		}
		
		executaComando(preparaComando(ByteUtils.newByteArray(66), formaPgto + sValor + 
				numCupom + cpfConsumidor + nomeConsumidor + enderecoConsumidor));
		verificaRetorno();
	}
	
	@Override
	public void imprimirSegundaVia() throws CommException, EcfException{
		executaComando(preparaComando(ByteUtils.newByteArray(91)));
		verificaRetorno();
	}
	
	@Override
	public void reimprimirCupomNaoFiscal() throws CommException, EcfException{
		executaComando(preparaComando(ByteUtils.newByteArray(92)));
		verificaRetorno();
		
	}
	
	@Override
	public void estornarComprovante(String cpf, String nome, String endereco)
			throws CommException, EcfException {
		cpf = StringUtils.strToEcf(cpf, 29);
		nome = StringUtils.strToEcf(nome, 30);
		endereco = StringUtils.strToEcf(endereco, 80);
		
		executaComando(preparaComando(ByteUtils.newByteArray(102), cpf + nome + endereco));
		verificaRetorno();
	}
	
	
	
	//----------------------------------------------------------------------
	//FORMAS DE PAGAMENTO
	
	@Override
	protected List<FormaPagamento> getFormasPagamento() throws CommException,
			EcfException {
		List<FormaPagamento> result = new ArrayList<FormaPagamento>();
		executaComando(preparaComando(ByteUtils.newByteArray(35, 49)));
		
		byte[] b = getRetorno(620);
		int codigo = 0;
		byte[] totalDesc = ByteUtils.subByteArray(b, 0, 320);
		String descricao = "";
		byte[] totalVinculado = new byte[20]; 
		totalVinculado = ByteUtils.subByteArray(b, 600, 20);
		boolean permiteVincular = false;
		byte[] valorTotal = ByteUtils.subByteArray(b, 320, 140);
		BigDecimal valor = BigDecimalUtils.newMoeda(0); 
		String sCodigo = "";
		
		for(int i = 0; i < 20; i++){
			descricao = "";
			descricao = new String(ByteUtils.subByteArray(totalDesc, i*16, 16));
			if(!descricao.trim().equals("")){
				codigo ++;
				if((totalVinculado[i] == 85) && (totalVinculado[i + 1] == 85))
					permiteVincular = true;
				
				byte[] bv = ByteUtils.subByteArray(valorTotal, i*7 , 7);
				valor = BigDecimalUtils.bcdToBigDecimal(bv, 2);
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
		String vincula = "2";
		if(permiteVincular){
			vincula = "1";
		}
		executaComando(preparaComando(ByteUtils.newByteArray(71), descricao + vincula));
		getRetorno(2);		
	}
	
	
	
	//----------------------------------------------------------------------
	//TOTALIZADORES
	
	@Override
	protected List<TotalizadorNaoFiscal> getTotalizadoresNaoFiscais()
			throws CommException, EcfException {
		List<TotalizadorNaoFiscal> result = new ArrayList<TotalizadorNaoFiscal>();
		
		executaComando(preparaComando(ByteUtils.newByteArray(35, 50)));
		byte[] b = getRetorno(780);
		
		/*String descricao = "";
		BigDecimal valor = BigDecimalUtils.newMoeda();
		
		for(int i = 0; i < 30; i++){
			byte[] b = ByteUtils.subByteArray(arrayTotalizadores, i*19, 19);
			
			descricao = new String(ByteUtils.subByteArray(b, 0, 19));
			valor = BigDecimalUtils.newMoeda();//BigDecimalUtils.bcdToBigDecimal((ByteUtils.subByteArray(b, 19, 7)), 2);
			
			if(!descricao.trim().equals("")){
				result.add(new TotalizadorNaoFiscal(IntegerUtils.intToStr(i+1, 2), descricao.trim(), valor, "0"));			
			}
		}	*/	
		
		byte[] totalDescricoes = ByteUtils.subByteArray(b, 0, 570);  
		byte[] totalValores =  ByteUtils.subByteArray(b, 570, 210);		
		
		for (int i = 0; i < 30; i++){
			byte[] vlr = ByteUtils.subByteArray(totalValores, i*7, 7);
			
			String descricao = new String(ByteUtils.subByteArray(totalDescricoes, i*19, 19));
			descricao = descricao.trim();
			BigDecimal valor = BigDecimalUtils.bcdToBigDecimal(vlr, 2);
			
			if(!descricao.equals("")){
				result.add(new TotalizadorNaoFiscal(IntegerUtils.intToStr(i+1, 2), descricao, valor, "0"));
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
	//----------------------------------------------------------------------
	
}
