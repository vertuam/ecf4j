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
package org.ecf4j.ecnf;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelas configurações do cupom da ECNF
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 */
public class CupomConfig {

	private List<LinhaFormatada> cabecalho = new ArrayList<LinhaFormatada>();	
	private List<LinhaFormatada> rodape = new ArrayList<LinhaFormatada>();
	private int linhasAvanco = 9;

	public List<LinhaFormatada> getCabecalho() {
		return cabecalho;
	}

	public void setCabecalho(List<LinhaFormatada> cabecalho) {
		this.cabecalho = cabecalho;
	}

	public List<LinhaFormatada> getRodape() {
		return rodape;
	}

	public void setRodape(List<LinhaFormatada> rodape) {
		this.rodape = rodape;
	}

	public int getLinhasAvanco() {
		return linhasAvanco;
	}

	public void setLinhasAvanco(int linhasAvanco) {
		this.linhasAvanco = linhasAvanco;
	}

	public void initDefaultValues(){
		cabecalho.clear();
		rodape.clear();
		cabecalho.add(new LinhaFormatada("Cabecalho Linha 1","EXPANDIDO","CENTRO",true, true, true));
		cabecalho.add(new LinhaFormatada("Cabecalho Linha 2","NORMAL","ESQUERDA",false, false, false));
		cabecalho.add(new LinhaFormatada("Cabecalho Linha 3","CONDENSADA","DIREITA",false, false, false));

		rodape.add(new LinhaFormatada("Rodape Linha 1","NORMAL","ESQUERDA",false, false, false));
		rodape.add(new LinhaFormatada("Rodape Linha 2","NORMAL","CENTRO",false, false, false));
		rodape.add(new LinhaFormatada("Rodape Linha 3","NORMAL","DIREITA",false, false, false));
		
		linhasAvanco = 9;
	}
	
}
