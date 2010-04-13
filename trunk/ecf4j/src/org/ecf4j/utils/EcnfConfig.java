/**
 * 
 */
package org.ecf4j.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.ecf4j.ecf.EcfEstado;
import org.ecf4j.utils.bigdecimals.BigDecimalUtils;

/**
 * @author pablo
 *
 */
public class EcnfConfig {
	
	public boolean desv = false;
	public String modelo = "BM001";
	public String porta = "/dev/lp0";
	public int numCupom = 0;
	public boolean podeCancelar = false;
	public int numCRO = 1;
	public int numEcf = 999;
	public String numSerial = ""; 
	public int numItem = 0;
	public List<EcnfItem> itens = new ArrayList<EcnfItem>();
	public BigDecimal valorTotal = BigDecimalUtils.newMoeda();
	public BigDecimal valorPago = BigDecimalUtils.newMoeda();
	public String cartaoAtivacao = "9999999999994";
	public EcfEstado estado = EcfEstado.LIVRE;
	//public boolean ultimoCupomCancelado = false;

}
