package br.usp.principal;

import java.util.Scanner;

import br.usp.operacoes.Backtracking;
import br.usp.util.*;


public class Principal {

	public static void main(String[] args)
	{
		Integer nCasosTeste = 0;
		Integer dim, rest;
		Integer indCasos= 0;
		Variavel matrizVariaveis[][];
		Restricao restricoes[];
		Boolean mvr = false, adiante = false;
		
		Scanner leitor = new Scanner(System.in);
		
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
					matrizVariaveis[i][j].setValorAtual(leitor.nextInt()); //popula a matriz das variaveis
				}
			}
			
			for(int i = 0;i<rest;i++){
				restricoes[i] = new Restricao(0,0,0,0);
				restricoes[i].setX1(leitor.nextInt());
				restricoes[i].setY1(leitor.nextInt());
				restricoes[i].setX2(leitor.nextInt());
				restricoes[i].setY2(leitor.nextInt());
			}
			
			Backtracking back = new Backtracking();
			back.preencheComRestricoes(matrizVariaveis, restricoes, 0, 0, mvr, adiante, dim);
		}
		leitor.close();
	}
}
