/**
 * 
 */
package org.ecf4j.utils;

import java.io.IOException;

import org.ecf4j.utils.files.FileUtils;

import com.thoughtworks.xstream.XStream;

/**
 * @author pablo
 *
 */
public class EcnfTools {
	
	public static EcnfConfig readEcnfConfig(){
		EcnfConfig ecnfConfig = null;
		String xml = null;
		try {
			xml = FileUtils.carregar("EcnfConfig.xml");
		} catch (Exception e) {
			xml = null;
			e.printStackTrace();
		}
		
		if((xml == null) || (xml.equals(""))){
			ecnfConfig = new EcnfConfig();
			XStream xStream = new XStream();
			xml = xStream.toXML(ecnfConfig);
			try {
				FileUtils.salvar("EcnfConfig.xml", xml, false);
			} catch (IOException e) {}
		}else{
			XStream xStream = new XStream();
			ecnfConfig = (EcnfConfig) xStream.fromXML(xml);
		}
		
		return ecnfConfig;
	}
	
	public static void writeEcnfConfig(EcnfConfig ecnfConfig){
		if(ecnfConfig == null){
			ecnfConfig = new EcnfConfig();
		}
		XStream xStream = new XStream();
		String xml = xStream.toXML(ecnfConfig);
		try {
			FileUtils.salvar("EcnfConfig.xml", xml, false);
		} catch (IOException e) {}
	}

}
