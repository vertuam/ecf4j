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
package org.ecf4j.ecf.exceptions;

import org.ecf4j.utils.i18n.MessagesI18n;

/**
 * Classe de exceçõess do ECF 
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 * @extends Exception
 */
public class EcfException extends Exception {
	
	public static final int ERRO_ABSTRACT = 0;
	public static final int ERRO_COMANDO_NAO_RECONHECIDO = 1;
	public static final int ERRO_RETORNO_INVALIDO = 2;
	public static final int ERRO_IMPRESSORA_NAO_RESPONDE = 3;
	public static final int ERRO_FIM_DO_PAPEL = 4;
	public static final int ERRO_POUCO_PAPEL = 5;
	public static final int ERRO_NO_RELOGIO = 6;
	public static final int ERRO_COMANDO_INEXISTENTE = 7;
	public static final int ERRO_CUPOM_ABERTO = 8;
	public static final int ERRO_QUANTIDADE_PARAMETROS_INVALIDO = 9;
	public static final int ERRO_TIPO_PARAMETRO_INVALIDO = 10;
	public static final int ERRO_MEMORIA_FISCAL_LOTADA = 11;
	public static final int ERRO_NO_EQUIPAMENTO_ECF = 12;
	public static final int ERRO_ALIQUOTA_NAO_PROGRAMADA = 13;
	public static final int ERRO_ALIQUOTAS_PRORAMADAS_LOTADA = 14;
	public static final int ERRO_CANCELAMENTO_NAO_PERMITIDO = 15;
	public static final int ERRO_CNPJ_IE_NAO_PROGRAMADOS = 16;
	public static final int ERRO_COMANDO_NAO_EXECUTADO = 17;
	public static final int ERRO_QUANTIDADE_ITEM = 18;	
	public static final int ERRO_ABRIR_CUPOM = 100;
	public static final int ERRO_VENDER_ITEM = 101;
	public static final int ERRO_ALIQUOTA_COM_DIVERGENCIA = 102;
	public static final int ERRO_FORMA_PAGAMENTO_EXISTENTE = 103;
	public static final int ERRO_FORMA_PAGAMENTO_INEXISTENTE = 104;
	public static final int ERRO_TOTALIZADOR_NAO_FISCAL_INEXISTENTE = 105;
	public static final int ERRO_ALIQUOTA_INEXISTENTE = 106;
	public static final int ERRO_ECF_NAO_SUPORTA_QUANTIDADE_ITEM_NAO_FISCAL = 107;
	public static final int ERRO_ECF_NAO_SUPORTA_ACRESCIMO = 108;
	public static final int ERRO_VALOR_CUPOM_NAO_FISCAL_INVALIDO = 109;
	public static final int ERRO_MODELO_CHEQUE_INEXISTENTE = 110;
	public static final int ERRO_COMUNICACAO_NAO_INICIALIZADA = 111;
	public static final int ERRO_INICIALIZAR_ECF = 112;
	public static final int ERRO_BANCO_INEXISTENTE = 113;
	
	private int tipo = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EcfException(int tipo){
		super(defineMensagem(tipo));
		this.tipo = tipo;
	
	}
	
	public EcfException(){
		super("outros");
		this.tipo = 0;
	
	}

	public int getTipo() {
		return tipo;
	}
	

	
	private static String defineMensagem(int tipo){
		switch (tipo) {
		case ERRO_ABSTRACT:
			return "Abstract";
		case ERRO_COMANDO_NAO_RECONHECIDO:
			return MessagesI18n.BUNDLE.getString("erroComandoNaoReconhecido");
		case ERRO_RETORNO_INVALIDO:
			return MessagesI18n.BUNDLE.getString("erroRetornoInvalido");
		case ERRO_IMPRESSORA_NAO_RESPONDE:
			return MessagesI18n.BUNDLE.getString("erroImpressoraNaoResponde");
		case ERRO_FIM_DO_PAPEL:
			return MessagesI18n.BUNDLE.getString("erroFimDoPapel");
		case ERRO_POUCO_PAPEL:
			return MessagesI18n.BUNDLE.getString("erroPoucoPapel");
		case ERRO_NO_RELOGIO:
			return MessagesI18n.BUNDLE.getString("erroNoRelogio");
		case ERRO_COMANDO_INEXISTENTE:
			return MessagesI18n.BUNDLE.getString("erroComandoInexistente");
		case ERRO_CUPOM_ABERTO:
			return MessagesI18n.BUNDLE.getString("erroCupomAberto");
		case ERRO_QUANTIDADE_PARAMETROS_INVALIDO:
			return MessagesI18n.BUNDLE.getString("erroQuantidadeDeParametrosInvalida");
		case ERRO_TIPO_PARAMETRO_INVALIDO:
			return MessagesI18n.BUNDLE.getString("erroTipoDeParametroInvalido");
		case ERRO_MEMORIA_FISCAL_LOTADA:
			return MessagesI18n.BUNDLE.getString("erroMemoriaFiscalLotada");
		case ERRO_NO_EQUIPAMENTO_ECF:
			return MessagesI18n.BUNDLE.getString("erroNoEquipamentoEcf");
		case ERRO_ALIQUOTA_NAO_PROGRAMADA:
			return MessagesI18n.BUNDLE.getString("erroAliquotaNaoProgramada");
		case ERRO_ALIQUOTAS_PRORAMADAS_LOTADA:
			return MessagesI18n.BUNDLE.getString("erroAliquotasProgramadasLotada");
		case ERRO_CANCELAMENTO_NAO_PERMITIDO:
			return MessagesI18n.BUNDLE.getString("erroCancelamentoNaoPermitido");
		case ERRO_CNPJ_IE_NAO_PROGRAMADOS:
			return MessagesI18n.BUNDLE.getString("erroCnpjIeNaoProgramados");
		case ERRO_COMANDO_NAO_EXECUTADO:
			return MessagesI18n.BUNDLE.getString("erroComandoNaoExecutado");
		case ERRO_ABRIR_CUPOM:
			return MessagesI18n.BUNDLE.getString("erroAbrirCupom");
		case ERRO_VENDER_ITEM:
			return MessagesI18n.BUNDLE.getString("erroVenderItem");
		case ERRO_QUANTIDADE_ITEM:
			return MessagesI18n.BUNDLE.getString("erroQuantidadeItens");
		case ERRO_ALIQUOTA_COM_DIVERGENCIA:
			return MessagesI18n.BUNDLE.getString("erroAliquotaComDivergencia");
		case ERRO_FORMA_PAGAMENTO_EXISTENTE:
			return MessagesI18n.BUNDLE.getString("erroFormaPagamentoExistente");
		case ERRO_FORMA_PAGAMENTO_INEXISTENTE:
			return MessagesI18n.BUNDLE.getString("erroFormaPagamentoInexistente");
		case ERRO_TOTALIZADOR_NAO_FISCAL_INEXISTENTE:
			return MessagesI18n.BUNDLE.getString("erroTotalizadorNaoFiscalInexistente");
		case ERRO_ALIQUOTA_INEXISTENTE:
			return MessagesI18n.BUNDLE.getString("erroAliquotaNaoExistente");
		case ERRO_ECF_NAO_SUPORTA_QUANTIDADE_ITEM_NAO_FISCAL:
			return MessagesI18n.BUNDLE.getString("erroEcfNaoSuportaQuantidadeItemNaoFiscal");
		case ERRO_VALOR_CUPOM_NAO_FISCAL_INVALIDO:
			return MessagesI18n.BUNDLE.getString("erroValorCupomNaoFiscalInvalido");
		case ERRO_MODELO_CHEQUE_INEXISTENTE:
			return MessagesI18n.BUNDLE.getString("erroModeloChequeInexistente");
		case ERRO_COMUNICACAO_NAO_INICIALIZADA:
			return MessagesI18n.BUNDLE.getString("erroComunicacaoNaoInicializada");
		case ERRO_INICIALIZAR_ECF:
			return MessagesI18n.BUNDLE.getString("erroInicializarEcf");
		case ERRO_BANCO_INEXISTENTE:
			return MessagesI18n.BUNDLE.getString("erroBancoInexistente");
		case ERRO_ECF_NAO_SUPORTA_ACRESCIMO:
			return MessagesI18n.BUNDLE.getString("erroEcfNaoSuportaAcrescimo");
		default:
			return MessagesI18n.BUNDLE.getString("erroComandoNaoExecutado");
		}
		
	}
	
}
