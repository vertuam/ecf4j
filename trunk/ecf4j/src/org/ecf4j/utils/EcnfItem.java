/**
 * 
 */
package org.ecf4j.utils;

import java.math.BigDecimal;

import org.ecf4j.utils.bigdecimals.BigDecimalUtils;

/**
 * @author pablo
 *
 */
public class EcnfItem {
	public int numItem = 0;
	public BigDecimal valorItem = BigDecimalUtils.newMoeda();
	public boolean cancelado = false;
}
