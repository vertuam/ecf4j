/**
 * 
 */
package org.ecf4j.ecnf.epson;

import org.ecf4j.ecnf.EcnfException;
import org.ecf4j.utils.bytes.ByteUtils;
import org.ecf4j.utils.comm.exceptions.CommException;

/**
 * @author Pablo
 *
 */
public class EcnfEpsonTMU220PD extends EcnfEpsonAbstract {


	@Override
	protected void ativarSublinhadoAbstract() throws CommException {
		executaComando(preparaComando(ByteUtils.newByteArray(27,45,1)));
	}

	@Override
	protected void desativarSublinhadoAbstract() throws CommException {
		executaComando(preparaComando(ByteUtils.newByteArray(27,45,0)));
	}
	@Override
	public String modelo() throws EcnfException {
		return "TM-U220PD";
	}

	@Override
	public Integer getNumColunas() {
		return 40;
	}
}
