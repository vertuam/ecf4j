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
package org.ecf4j.utils.datetimes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.ecf4j.ecf.exceptions.EcfException;

/**
 * Classe para tratamento de DateTime
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 */
public class DateTimeUtils {
	
	public static Date stringToDate(String str, String mask) throws EcfException{
		Date dtTmp = null;
		try {
			dtTmp = new SimpleDateFormat(mask).parse(str);
		} catch (ParseException e) {
			throw new EcfException();
		}
		return dtTmp;
	}
	
	public static String dateToString(Date date, String mask)	{
		DateFormat dateFormat = new SimpleDateFormat(mask);
	    return dateFormat.format(date);
	}
	
	public static int dayOfDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		return day;
	}
	
	public static int monthOfDate(Date date){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int month = cal.get(Calendar.MONTH) + 1;
		return month;
	}
	
	public static int yearOfDate(Date date){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int year = cal.get(Calendar.YEAR);
		
		return year;
	}
	
	public static String mesPorExtenso(Date date){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int month = monthOfDate(date);
		
		String mes = "";
		
		if(month == 1)
			mes = "Janeiro";
		if(month == 2)
			mes = "Fevereiro";
		if(month == 3)
			mes = "Março";
		if(month == 4)
			mes = "Abril";
		if(month == 5)
			mes = "Maio";
		if(month == 6)
			mes = "Junho";
		if(month == 7)
			mes = "Julho";
		if(month == 8)
			mes = "Agosto";
		if(month == 9)
			mes = "Setembro";
		if(month == 10)
			mes = "Outubro";
		if(month == 11)
			mes = "Novembro";
		if(month == 12)
			mes = "Dezembro";
		
		return mes;
	}


}
