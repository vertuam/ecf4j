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
 * Esse arquivo usa a biblioteca RXTX Copyright 1997-2007 by Trent Jarvi.
 * disponível em: <http://users.frii.com/jarvi/rxtx/index.html> 21/09/2009.
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
package org.ecf4j.utils.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe de manuseio de arquivos
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 */

public class FileUtils {
	/**
	* Salva o conteúdo de uma variável em um arquivo
	* @param arquivo
	* @param conteudo
	* @param adicionar se true adicionar no final do arquivo
	* @throws IOException
	*/
	public static void salvar(String arquivo, String conteudo, boolean adicionar)
	throws IOException {

		FileWriter fw = new FileWriter(arquivo, adicionar);
		BufferedWriter b = new BufferedWriter(fw);

		//fw.write(conteudo);
		//fw.close();
		
		String[] linhas = conteudo.split("\n");
		for(String l : linhas){
			b.write(l);
			b.newLine();
		}
		
		b.close();
	}
	
	public static void salvar(String path, String arquivo, String conteudo, boolean adicionar)
	throws Exception {
		String[] pastas = path.split("/");
		String root = "";
		String sep = "";
		for (int i = 0; i<pastas.length; i++) {
			root = root + sep + pastas[i].toString();
			sep = "/";
			File dir = new File(root);
			if (!dir.exists()) {
				if (!dir.mkdir()) {
					throw new Exception("Não foi possível criar a pasta "+path);
				}
			}
		}
		root = root + "/" + arquivo;
		salvar(root, conteudo, adicionar);
	}

	/**
	* Carrega o conteúdo de um arquivo em uma String, se o aquivo
	* não existir, retornará null.
	* @param arquivo
	* @return conteúdo
	* @throws Exception
	*/
	public static String carregar(String arquivo)
	throws FileNotFoundException, IOException {

		File file = new File(arquivo);

		if (! file.exists()) {
			return null;
		}

		BufferedReader br = new BufferedReader(new FileReader(arquivo));
		StringBuffer bufSaida = new StringBuffer();

		String linha;
		while( (linha = br.readLine()) != null ){
			bufSaida.append(linha + "\n");
		}
		br.close();
		return bufSaida.toString();
	}

}
