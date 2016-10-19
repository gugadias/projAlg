package br.usp.principal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import br.usp.operacoes.Backtracking;
import br.usp.util.*;


public class Principal {

	public static void main(String[] args) throws FileNotFoundException
	{
		Integer nCasosTeste = 0;
		Integer dim, rest;
		Integer indCasos= 0;
		Variavel matrizVariaveis[][];
		Restricao restricoes[];
		Boolean mvr = true, //flag para controle de mvr
				adiante = true; //flag para controle de adiante
		File arquivoSaida = new File("saida.dat");
		PrintWriter escreveArquivo = new PrintWriter(arquivoSaida);
		Scanner leitor = new Scanner(new File("entrada.dat"));
		
		nCasosTeste = leitor.nextInt(); //leitura de quantos casos de teste terão.
		
		for(indCasos = 0;indCasos < nCasosTeste;indCasos++)
		{
			//le dimensao e restricoes
			dim = leitor.nextInt();
			rest = leitor.nextInt();
			
			matrizVariaveis = new Variavel[dim][dim]; //cria matriz
			restricoes = new Restricao[rest];
			
			for(int i = 0;i < dim;i++) {//le as linhas
				for(int j = 0; j < dim; j++) {
					matrizVariaveis[i][j] = new Variavel(dim);
					Integer atu = leitor.nextInt();
					matrizVariaveis[i][j].setValorAtual(atu); //popula a matriz das variaveis
					if(atu != 0) {
						matrizVariaveis[i][j].getValoresDisponiveis().clear();
						matrizVariaveis[i][j].getValoresDisponiveis().add(atu);
					}
				}
			}
			
			//le as restricoes
			for(int i = 0;i<rest;i++){
				restricoes[i] = new Restricao(0,0,0,0);
				restricoes[i].setX1(leitor.nextInt()-1);
				restricoes[i].setY1(leitor.nextInt()-1);
				restricoes[i].setX2(leitor.nextInt()-1);
				restricoes[i].setY2(leitor.nextInt()-1);
			}
			
			Backtracking back = new Backtracking();
			escreveArquivo.println("Caso de teste: "+(indCasos+1)+", Resposta:");
			long start = System.currentTimeMillis(); //calcula o inicio
			back.preencheComRestricoes(matrizVariaveis, restricoes, 0, 0, mvr, adiante, dim, 0, escreveArquivo);
			long elapsed = System.currentTimeMillis() - start; //calcula o fim - inicio
			escreveArquivo.println(String.format("%02d segundos/%02d milisegundos", elapsed/1000,elapsed));
			escreveArquivo.println();
			escreveArquivo.flush();
		}
		leitor.close();
	}
}
