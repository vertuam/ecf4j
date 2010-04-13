/**
 * 
 */
package org.ecf4j.ecnf.bematech;

import java.math.BigDecimal;

import org.ecf4j.ecnf.EcnfException;
import org.ecf4j.utils.bigdecimals.BigDecimalUtils;
import org.ecf4j.utils.comm.exceptions.CommException;
import org.ecf4j.utils.integers.IntegerUtils;
import org.ecf4j.utils.strings.StringUtils;

/**
 * @author pablo
 *
 */
public class EcnfBemaMp100 extends EcnfBematechAbstract {
	
	@Override
	public void abrirGaveta() throws CommException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void desativarNegritoAbstract() throws CommException {
		// TODO Auto-generated method stub	
	}

	@Override
	public void cortarPapel() throws CommException {
		// TODO Auto-generated method stub
	}

	@Override
	public void cortarParcialmentePapel() throws CommException, EcnfException {
		// TODO Auto-generated method stub
	}

	@Override
	public String getEstado() throws CommException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void imprimirEan13(String ean) throws CommException, EcnfException {
		// TODO Auto-generated method stub

	}

	@Override
	public void imprimirEan8(String ean) throws CommException, EcnfException {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected void desativarItalicoAbstract() throws CommException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void ativarModoNormal() throws CommException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void imprimirLinhaCondensadaAbstract(String linha)
			throws CommException {
		String ss[] = linha.split("\n");
		int len = getNumColunas();
		String p = "";
		int i = 0;
		
		for(String s: ss){
			p = "";
			i = 0;	
			p = StringUtils.strToEcf(alinhamento, s, i++, len);
			
			while(!p.trim().equals("")){
				executaComando(preparaComando((p+"\n").getBytes()));;
				p = StringUtils.strToEcf(alinhamento, s, i++, len);
			}
		}
	}

	@Override
	public String modelo() throws EcnfException {
		return "MP-100";
	}

	@Override
	public void imprimirLinha(String str1, String str2) throws CommException {
		int len = getNumColunas() - str1.length();
		str2 = StringUtils.formatStr(str2, len, StringUtils.ALINHAMENTO_DIREITA);
		imprimirLinha(str1+str2);
	}

	@Override
	public void imprimirSeparador() throws CommException {
		imprimirLinhaAbstract(StringUtils.formatStr("-", getNumColunas(), '-', StringUtils.ALINHAMENTO_ESQUERDA));
	}

	@Override
	protected void imprimirLinhaAbstract(String linha) throws CommException {
		String ss[] = linha.split("\n");
		int len = getNumColunas();
		String p = "";
		int i = 0;
		
		for(String s: ss){
			p = "";
			i = 0;	
			p = StringUtils.strToEcf(alinhamento, s, i++, len);
			
			while(!p.trim().equals("")){
				executaComando(preparaComando((p+"\n").getBytes()));;
				p = StringUtils.strToEcf(alinhamento, s, i++, len);
			}
		}
	}

	@Override
	protected void imprimirLinhaExpandidaAbstract(String linha)
			throws CommException {
		String ss[] = linha.split("\n");
		int len = getNumColunas();
		String p = "";
		int i = 0;
		
		for(String s: ss){
			p = "";
			i = 0;	
			p = StringUtils.strToEcf(alinhamento, s, i++, len);
			
			while(!p.trim().equals("")){
				
				executaComando(preparaComando((p+"\n").getBytes()));;
				p = StringUtils.strToEcf(alinhamento, s, i++, len);
			}
		}
	}

	@Override
	public void imprimirItem(int numItem, String codProduto,
			String descProduto, String un, BigDecimal qtdeItem,
			BigDecimal valorItem) throws CommException {
		String item = IntegerUtils.intToStr(numItem, 3) + " ";
		String qtde = BigDecimalUtils.qtdeToString(qtdeItem) + " ";
		String valor = BigDecimalUtils.moedaToString(valorItem);
		if (!un.equals("")) {
			un = StringUtils.limiteStr(un, 2) + "x";
		}
		String produto = codProduto+" "+descProduto + " ";
		int len = 4 + qtde.length()+valor.length() + un.length() + produto.length();	
				
		imprimirLinha(alinhaItem(item+produto, un+qtde+valor, getNumColunas() - len));	
		
	}

	@Override
	public void imprimirLinhaExpandida(String str1, String str2)
			throws CommException {
		int len = getNumColunas() - str1.length();
		str2 = StringUtils.formatStr(str2, len, StringUtils.ALINHAMENTO_DIREITA);
		imprimirLinhaExpandida(str1+str2);
	}

	@Override
	public Integer getNumColunas() {
		return 40;
	}

}
