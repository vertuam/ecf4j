/**
 * 
 */
package org.ecf4jtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Date;

import org.ecf4j.Balanca;
import org.ecf4j.Ecf;
import org.ecf4j.Scanner;
import org.ecf4j.balanca.BalancaListener;
import org.ecf4j.scanner.ScannerListener;
import org.ecf4j.utils.Ecf4jFactory;

/**
 * @author Agner
 *
 */
public class Main {
	
	private static String readString(){
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			return reader.readLine();
		} catch (IOException e) {
			return "";
		}
	}

	private static String readString(String label){
		System.out.printf(label);
		return readString();
	}
	
	private static int readInt(){
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			return Integer.parseInt(reader.readLine());
		} catch (IOException e) {
			return -1;
		}
	}

	private static int readInt(String label){
		System.out.printf(label);
		return readInt();
	}
	
	private static BigDecimal readBigDecimal(){
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			return new BigDecimal(reader.readLine());
		} catch (IOException e) {
			return new BigDecimal(0);
		}
	}

	private static BigDecimal readBigDecimal(String label){
		System.out.printf(label);
		return readBigDecimal();
	}
	
	/**
	 * Exemplo de utilização da classe Ecf
	 */
    private static void ecfTest(){
    	String modelo = "";
		String porta = "";
		// Criar o objeto Ecf
		Ecf ecf = new Ecf();
		// Criar uma lista de modelos implementados
		Object[][] ecfLista = Ecf4jFactory.getEcfLista();
		
		System.out.println("");
		System.out.println("");
    	System.out.println("Teste de ECF");
		try {
			System.out.println("Informe o codigo do modelo do ECF");
			System.out.println("Codigo| Modelo");
			String s = "";
			for(Object[] reg: ecfLista){
				s += reg[0].toString();
				s += " | ";
				s += reg[2];
				s += '\n';
			}
			System.out.println(s);
			System.out.println("modelo: ");			
			modelo = readString();
			System.out.println("Informe a porta utilizada (Ex. Win: COM1, Ex. Linux: /dev/ttyS0)");
			System.out.println("porta: ");			
			porta = readString();
			// Inicialiar comunicação com o Ecf
			ecf.inicializar(modelo, porta);
			
			int opcao = -1;
			do{
				System.out.println("Seleciona a opção desejada:");
				System.out.println("  1 - Leitura X");
				System.out.println("  2 - Redução Z");
				System.out.println("  3 - Abrir Cupom");
				System.out.println("  4 - Vender Item");
				System.out.println("  5 - Vender Item com desconto percentual");
				System.out.println("  6 - Totalizar Cupom");
				System.out.println("  7 - Efetuar Pagamento");
				System.out.println("  8 - Finalizar Cupom");
				System.out.println("  9 - Cancelar Cupom");
				System.out.println("  10 - Cancelar Item");
				System.out.println("  11 - Cancelar Último Item");
				System.out.println("  0 - Voltar");				
				System.out.printf("Opção: ");
				try {
					opcao = readInt();
					switch (opcao){
					case 1:
						ecf.leituraX();
						break;
					case 2:
						ecf.reducaoZ(new Date());
						break;
					case 3:
						ecf.abrirCupom(readString("Cpf/Cnpj: "), 
								readString("Consumidor: "), "");
						break;
					case 4:
						ecf.venderItem(readString("Código do Item: "), 
								readString("Descrição: "), 
								readString("Alíquota: "), 
								readBigDecimal("Quantidade: "), 
								readBigDecimal("Preço: "), 
								readString("Unidade: "));
						break;
					case 5:
						ecf.venderItemDescontoPerc(readString("Código do Item: "), 
								readString("Descrição: "), 
								readString("Alíquota: "), 
								readBigDecimal("Quantidade: "), 
								readBigDecimal("Preço: "), 
								readString("Unidade: "),
								readBigDecimal("Percentual de Desconto: "));
						break;	
					case 6:
						ecf.subtotalizarCupom(new BigDecimal(0),//desconto, 
								new BigDecimal(0),//acrescimo, 
								true); //descAcresPerc);
						break;
					case 7:
						ecf.efetuarPagamento(readString("Código da Forma de Pagamento: "), 
								readBigDecimal("Valor do Pagamento: "), "");
						break;
					case 8:
						ecf.fecharCupom(readString("Mensagem Final do Cupom: "));
						break;
					case 9:
						ecf.cancelarCupom();
						break;
					case 10:
						ecf.cancelarItem(readInt("Número do Item a ser cancelado: "));
						break;
					case 11:
						ecf.cancelarUltimoItem();
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("");			
			}while(opcao != 0);
			ecf.finalizar();
			ecf = null;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("");
		}	
    }
    
    /**
     * Exemplo de utilização da classe Scanner
     */
    private static void scannerTest(){
    	String modelo = "";
		String porta = "";
		// Criar o objeto Scanner
		Scanner scanner = new Scanner();
		// Criar uma lista de modelos implementados
		Object[][] scannerLista = Ecf4jFactory.getScannerLista();
		
		System.out.println("");
		System.out.println("");
    	System.out.println("Teste de Scanner");
		try {
			System.out.println("Informe o codigo do modelo do Scanner");
			System.out.println("Codigo| Modelo");
			String s = "";
			for(Object[] reg: scannerLista){
				s += reg[0].toString();
				s += " | ";
				s += reg[2];
				s += '\n';
			}
			System.out.println(s);
			System.out.println("modelo: ");			
			modelo = readString();
			System.out.println("Informe a porta utilizada (Ex. Win: COM1, Ex. Linux: /dev/ttyS0)");
			System.out.println("porta: ");			
			porta = readString();
			// Inicialiar comunicação com o Scanner
			scanner.addScannerListener(new ScannerListener(){
				@Override
				public void onRead(String codigo) {
					System.out.println(codigo);
					System.out.println("Passe um código no scanner ou digite 0 para voltar :");
				}
			});
			scanner.inicializar(modelo, porta, 9600, 8, 3, 1);
			int opcao = -1;
			do{
				System.out.println("Passe um código no scanner ou digite 0 para voltar :");
				try {
					opcao = readInt();
					/*
					switch (opcao){
					case 1:
						break;
					}
					*/
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("");			
			}while(opcao != 0);
			scanner.finalizar();
			scanner = null;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("");
		}	
    }

    /**
     * Exemplo de utilização da classe Balança
     */
    private static void balancaTest(){
    	String modelo = "";
		String porta = "";
		// Criar o objeto Balança
		Balanca balanca = new Balanca();
		// Criar uma lista de modelos implementados
		Object[][] balancaLista = Ecf4jFactory.getBalancaLista();
		
		System.out.println("");
		System.out.println("");
    	System.out.println("Teste de Balança");
		try {
			System.out.println("Informe o codigo do modelo do Balança");
			System.out.println("Codigo| Modelo");
			String s = "";
			for(Object[] reg: balancaLista){
				s += reg[0].toString();
				s += " | ";
				s += reg[2];
				s += '\n';
			}
			System.out.println(s);
			System.out.println("modelo: ");			
			modelo = readString();
			System.out.println("Informe a porta utilizada (Ex. Win: COM1, Ex. Linux: /dev/ttyS0)");
			System.out.println("porta: ");			
			porta = readString();
			// Inicialiar comunicação com o Balança
			balanca.addBalancaListener(new BalancaListener() {				
				@Override
				public void onChangeWeight(BigDecimal arg0) {
					System.out.println(arg0);
					System.out.println("Coloque um peso na balança ou digite 0 para voltar :");			
				}
			});
			balanca.inicializar(modelo, porta, 9600, 8, 3, 1);
			int opcao = -1;
			do{
				System.out.println("Coloque um peso na balança ou digite 0 para voltar :");
				try {
					opcao = readInt();
					/*
					switch (opcao){
					case 1:
						break;
					}
					*/
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("");			
			}while(opcao != 0);
			balanca.finalizar();
			balanca = null;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("");
		}	

		
    }

	/**
	 * Método main do programa
	 * @param args
	 */
	public static void main(String[] args) {
		int opcao = -1;
		do{
			System.out.println("Seleciona a opção desejada:");
			System.out.println("  1 - Ecf");
			System.out.println("  2 - Scanner");
			System.out.println("  3 - Balança");
			System.out.println("  0 - Sair");
			System.out.printf("Opção: ");
			try {
				opcao = readInt();
				switch (opcao){
				case 1:
					ecfTest();
					break;
				case 2:
					scannerTest();
					break;
				case 3:
					balancaTest();
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("");
		}while(opcao != 0);	
		System.out.println("ecf4jTest finalizado!!!");
		System.exit(0);
	}

}
