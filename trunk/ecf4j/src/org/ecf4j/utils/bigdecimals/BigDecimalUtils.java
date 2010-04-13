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
package org.ecf4j.utils.bigdecimals;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import org.ecf4j.utils.bytes.ByteUtils;

/**
 * Classe para tratamento de BigDecimal
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 */
public class BigDecimalUtils {
	

	public static int decMoeda = 2;
	public static int decQtde = 3;
	public static int roundingMode = BigDecimal.ROUND_DOWN; //ROUND_HALF_UP;
	//public static RoundingMode roundingMode = RoundingMode.DOWN; //HALF_UP;
	
	/**
	 * newMoeda - Cria um novo BigDecimal em escala de moeda com valor 0
	 * @return BigDecimal
	 */
	public static BigDecimal newMoeda(){
		return new BigDecimal("0").setScale(decMoeda, roundingMode);
	}
	/**
	 * newMoeda - Cria um novo BigDecimal em escala de moeda com valor val
	 * @param val
	 * @return BigDecimal
	 */
	public static BigDecimal newMoeda(int val){
		return BigDecimal.valueOf(val).setScale(decMoeda, roundingMode);
	}
	/**
	 * newMoeda - Cria um novo BigDecimal em escala de moeda com valor val
	 * @param val
	 * @return BigDecimal
	 */
	public static BigDecimal newMoeda(long val){
		return BigDecimal.valueOf(val).setScale(decMoeda, roundingMode);
	}
	/**
	 * newMoeda - Cria um novo BigDecimal em escala de moeda com valor val
	 * @param val
	 * @return BigDecimal
	 */
	public static BigDecimal newMoeda(double val){
		return BigDecimal.valueOf(val).setScale(decMoeda, roundingMode);
	}
	/**
	 * newMoeda - Cria um novo BigDecimal em escala de moeda com valor val
	 * @param val
	 * @return BigDecimal
	 */
	public static BigDecimal newMoeda(String val){
		if(!val.isEmpty()){
			return newMoeda(StringToDouble(val)); 
		}else{
			return newMoeda();
		}
	}	
	
	public static BigDecimal bcdToMoeda(byte[] b){
		return bcdToBigDecimal(b, decMoeda);
	}

	public static BigDecimal addMoeda(BigDecimal val1, BigDecimal val2){
		BigDecimal aux = val1;
		aux = aux.setScale(decMoeda, roundingMode);
		return aux.add(val2).setScale(decMoeda, roundingMode);
		//return aux.add(val2, new MathContext(decMoeda, roundingMode)).setScale(decMoeda, roundingMode);
	}	

	public static BigDecimal subtractMoeda(BigDecimal val1, BigDecimal val2){
		BigDecimal aux = val1;
		aux = aux.setScale(decMoeda, roundingMode);
		return aux.subtract(val2).setScale(decMoeda, roundingMode);
		//return aux.subtract(val2, new MathContext(decMoeda, roundingMode)).setScale(decMoeda, roundingMode);
	}	

	public static BigDecimal multiplyMoeda(BigDecimal val1, BigDecimal val2){
		if(val1.scale() < val2.scale()){
			BigDecimal aux = (val1!=null?val1:newMoeda());
			aux = aux.setScale(decMoeda, roundingMode);
			return aux.multiply((val2!=null?val2:newMoeda())).setScale(decMoeda, roundingMode);
			//return aux.multiply(val2, new MathContext(decMoeda, roundingMode)).setScale(decMoeda, roundingMode);			
		} else {
			BigDecimal aux = (val2!=null?val2:newMoeda());
			aux = aux.setScale(decMoeda, roundingMode);
			return aux.multiply((val1!=null?val1:newMoeda())).setScale(decMoeda, roundingMode);
			//return aux.multiply(val2, new MathContext(decMoeda, roundingMode)).setScale(decMoeda, roundingMode);			
		}
	}	

	public static BigDecimal divideMoeda(BigDecimal val1, BigDecimal val2){
		return val1.divide(val2, decMoeda, roundingMode);
	}
	
	public static String moedaToString(BigDecimal value){
		String result = "0";
		String quantCasas = "%."+decMoeda+"f";   
		result = String.format(Locale.getDefault(), quantCasas, value);  
		return result;
	}
	//----------------------------------------------------------------------------
	
	/**
	 * newQtde - Cria um novo BigDecimal em escala de qtde com valor 0
	 * @return BigDecimal
	 */
	public static BigDecimal newQtde(){
		return new BigDecimal("0").setScale(decQtde, roundingMode);
	}
	/**
	 * newQtde - Cria um novo BigDecimal em escala de qtde com valor val
	 * @param val
	 * @return BigDecimal
	 */
	public static BigDecimal newQtde(int val){
		return BigDecimal.valueOf(val).setScale(decQtde, roundingMode);
	}
	/**
	 * newQtde - Cria um novo BigDecimal em escala de qtde com valor val
	 * @param val
	 * @return BigDecimal
	 */
	public static BigDecimal newQtde(long val){
		return BigDecimal.valueOf(val).setScale(decQtde, roundingMode);
	}
	/**
	 * newQtde - Cria um novo BigDecimal em escala de qtde com valor val
	 * @param val
	 * @return BigDecimal
	 */
	public static BigDecimal newQtde(double val){
		return BigDecimal.valueOf(val).setScale(decQtde, roundingMode);
	}
	/**
	 * newQtde - Cria um novo BigDecimal em escala de qtde com valor val
	 * @param val
	 * @return BigDecimal
	 */
	public static BigDecimal newQtde(String val){
		if(!val.isEmpty()){
			return newQtde(StringToDouble(val)); 
		}else{
			return newQtde();
		}
	}
	
	public static BigDecimal bcdToQtde(byte[] b){
		return bcdToBigDecimal(b, decQtde);
	}
	
	public static BigDecimal addQtde(BigDecimal val1, BigDecimal val2){
		BigDecimal aux = val1;
		aux = aux.setScale(decQtde, roundingMode);
		return aux.add(val2).setScale(decQtde, roundingMode);
		//return aux.add(val2, new MathContext(decQtde, roundingMode)).setScale(decQtde, roundingMode);
	}	

	public static BigDecimal subtractQtde(BigDecimal val1, BigDecimal val2){
		BigDecimal aux = val1;
		aux = aux.setScale(decQtde, roundingMode);
		return aux.subtract(val2).setScale(decQtde, roundingMode);
		//return aux.subtract(val2, new MathContext(decQtde, roundingMode)).setScale(decQtde, roundingMode);
	}	

	public static BigDecimal multiplyQtde(BigDecimal val1, BigDecimal val2){
		BigDecimal aux = val1;
		aux = aux.setScale(decQtde, roundingMode);
		return aux.multiply(val2).setScale(decQtde, roundingMode);
		//return aux.multiply(val2, new MathContext(decQtde, roundingMode)).setScale(decQtde, roundingMode);
	}	

	public static BigDecimal divideQtde(BigDecimal val1, BigDecimal val2){
		BigDecimal aux = val1;
		aux = aux.setScale(decQtde, roundingMode);
		return aux.divide(val2, decQtde, roundingMode);
	}
	
	public static String qtdeToString(BigDecimal value){
		String result = "0";
		String quantCasas = "%."+decQtde+"f";   
		result = String.format(Locale.getDefault(), quantCasas, value);  
		return result;
	}

	//----------------------------------------------------------------------------
	
	public static String bigDecimalToEcf(BigDecimal valor, int len, int dec){
		String result = "";
		String quantCasas = "%."+dec+"f";   
		result = String.format(Locale.getDefault(), quantCasas, valor);
		result = result.replace(".", "");
		result = result.replace(",", "");
		if (result.length() > len){
			try {
				throw new Exception("Valor inválido.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		while (result.length() < len){
			result = "0" + result;
		}
		return result;
	}
	
	//----------------------------------------------------------------------------
	
	public static boolean isEqualTo(BigDecimal val1, BigDecimal val2){
		return val1.compareTo(val2)==0;
	}

	public static boolean isLessEqualTo(BigDecimal val1, BigDecimal val2){
		return val1.compareTo(val2)<=0;
	}

	public static boolean isGreaterEqualTo(BigDecimal val1, BigDecimal val2){
		return val1.compareTo(val2)>=0;
	}
	
	public static boolean isLessOf(BigDecimal val1, BigDecimal val2){
		return val1.compareTo(val2)<0;
	}

	public static boolean isGreaterOf(BigDecimal val1, BigDecimal val2){
		return val1.compareTo(val2)>0;
	}

	public static boolean isZero(BigDecimal val){
		return val.compareTo(new BigDecimal("0"))==0;
	}

	public static boolean isLessEqualToZero(BigDecimal val){
		return val.compareTo(new BigDecimal("0"))<=0;
	}

	public static boolean isGreaterEqualToZero(BigDecimal val){
		return val.compareTo(new BigDecimal("0"))>=0;
	}
	
	public static boolean isLessOfZero(BigDecimal val){
		return val.compareTo(new BigDecimal("0"))<0;
	}

	public static boolean isGreaterOfZero(BigDecimal val){
		return val.compareTo(new BigDecimal("0"))>0;
	}

	public static boolean isDifferent(BigDecimal val1, BigDecimal val2){
		return val1.compareTo(val2)!=0;
	}
	
	//----------------------------------------------------------------------------
	/**
	 * Este mï¿½todo retorna o valor de value arredondado para
	 * dec digitos decimais no formato String
	 * @param value
	 * @param dec
	 * @return
	 */
	public static String RoundToStr(double value, int dec){
		return String.format("%8."+dec+"%", value);
	}

	/**
	 * Este mï¿½todo retorna o valor de value truncado para
	 * dec digitos decimais no formato String
	 * @param value
	 * @param dec
	 * @return
	 */
	public static String TruncToStr(double value, int dec) throws Exception
	{
		/*Float f = new Float(value);
		String x = f.toString();
		x = x.toString().substring(0, x.indexOf(".")+dec);
		return x;*/
		double fator = 0;
		if (value == 0){
			fator = 0;
		}
		else{
			if (value > 0){
				fator = -1;
			}
			else{
				fator = 1;
			}
		}

		switch (dec){
		case 0:
			value = value + (0.5d * fator);
			break;
		case 1:
			value = value + (0.05d * fator);
			break;
		case 2:
			value = value + (0.005d * fator);
			break;
		case 3:
			value = value + (0.0005d * fator);
			break;
		case 4:
			value = value + (0.00005d * fator);
			break;
		default :
			throw new Exception("Truncagem de valor não suportada");
		}

		return String.format("%8."+dec+"f", value);

	}

	public static String TruncToStr(BigDecimal value, int dec) throws Exception{
		String result = "0";
		String quantCasas = "%."+dec+"f";   
		result = String.format(Locale.getDefault(), quantCasas, value);  
		return result;
	}

	
	public static String FormatIntToStr(int value, int digits){
		NumberFormat nf=NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(digits);
		nf.setMaximumIntegerDigits(digits);
		return nf.format(value);
	}

	public static double StringToDouble(String str){
		str = str.replaceAll(",", ".");
		if(!str.isEmpty()){
			return Double.parseDouble(str);
		}else{
			return 0;
		}
	}

	public static double tratarDecimal(double vlr, int dig){
		int pot = (int)Math.pow(10, dig);
		vlr = vlr * pot;
		long i = (int)vlr;
		vlr = i;
		vlr = vlr / pot;
		return vlr;
	}

	public static double currencyAjust(double value){
		return tratarDecimal(value, 2);
	}
	
	public static boolean isParsableToInt(String str)
	{
		try
		{
			Integer.parseInt(str);
			return true;
		}
		catch(NumberFormatException nfe)
		{
			return false;
		}
	}
	
	public static boolean isParsableToLong(String str)
	{
		try
		{
			Long.parseLong(str);
			return true;
		}
		catch(NumberFormatException nfe)
		{
			return false;
		}
	}
	
	public static boolean isParsableToDouble(String str)
	{
		try
		{
			Double.parseDouble(str);
			return true;
		}
		catch(NumberFormatException nfe)
		{
			return false;
		}
	}

	public static BigDecimal bcdToBigDecimal(byte[] b, int scale){
		StringBuilder hexval = new StringBuilder();
		// converte os valores ASCII da string para hexadecimal
		for (int i = 0; i < b.length; i++) {
			StringBuilder hex = new StringBuilder(ByteUtils.byteToString(b[i]));//  Integer.toString((int)b[i], 16));
			hexval.append(hex);
		}
		
		if (scale > 0) {
			if (scale > hexval.length()) {
				// completa com zeros antes da posição de inserção do ponto decimal
				int count = scale - hexval.length();

			}
				// insere um ponto decimal na posição indicada
			hexval.insert(hexval.length()-scale, ".");
		}
		
		return new BigDecimal(hexval.toString()).setScale(scale, roundingMode);
	}


}
