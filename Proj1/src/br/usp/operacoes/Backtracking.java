package br.usp.operacoes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import br.usp.util.Restricao;
import br.usp.util.Variavel;

public class Backtracking {

	//funcao do backtracking
	public boolean preencheComRestricoes(Variavel[][] matrizVariaveis, Restricao[] restricoes,
								      Integer xAtual,Integer yAtual, Boolean mvr, Boolean verifAdiante,
								      Integer dim, Integer quantasInt, PrintWriter escreveArquivo) throws FileNotFoundException {
		
		//verifica se terminou
		if(todasCorretamentePreenchidas(matrizVariaveis,dim)) {
			imprimeResultado(matrizVariaveis,dim,quantasInt,escreveArquivo);
			return true;
		}
		//auxiliar para verificacao adiante
		Boolean corretoAux = true; 
		
		//loop
		for(Integer valorAtual : matrizVariaveis[xAtual][yAtual].getValoresDisponiveis()) {
			//soma de interacoes
			quantasInt++;
			
			//verifica se o proximo valor respeita as restricoes
			if(restricoesRespeitadas(matrizVariaveis, restricoes, xAtual, yAtual, valorAtual,dim)) {//verifica se respeita as restricoes
				
				//seta na matriz
				matrizVariaveis[xAtual][yAtual].setValorAtual(valorAtual);
				
				//verifica se acabou
				if(todasCorretamentePreenchidas(matrizVariaveis,dim)) {
					imprimeResultado(matrizVariaveis,dim, quantasInt,escreveArquivo);
					return true;
				}
				
				//verificacao adiante
				if(verifAdiante) 
					corretoAux = verificacaoAdiante(matrizVariaveis, restricoes, xAtual, yAtual, valorAtual); 
				
				//caso alguma lista ficou vazia pela verificação adiante
				if(!corretoAux) {
					matrizVariaveis[xAtual][yAtual].setValorAtual(0);
					//metodo que vai resetar as listas de disponiveis
					resetaRestricoes(matrizVariaveis,xAtual,yAtual,restricoes,valorAtual,dim);
				}
				else {
					
					//se acabou
					if(xAtual == dim-1 && yAtual == dim-1 && !mvr) {
						imprimeResultado(matrizVariaveis,dim,quantasInt, escreveArquivo);
						return true;
					}
					
					//verificará qual o proximo a ser escolhido
					int xProx = xAtual,yProx = yAtual;
					if(mvr) {
						String proxIndice;
						//metodo do mvr
						proxIndice = escolheProximoIndice(matrizVariaveis, dim);
						String[] aux = proxIndice.split(",");
						xProx = Integer.parseInt(aux[0]);
						yProx = Integer.parseInt(aux[1]);
					}
					else {
						//proximo valor normal
						if(xAtual == dim-1) {
							yProx = yAtual+1;
						}
						else if (yAtual == dim-1) {
							xProx = xAtual+1;
							yProx = 0;
						}
						else {
							yProx = yAtual+1;
						}
					}
					//retorno do backtracking
					Boolean retorno = preencheComRestricoes(matrizVariaveis,restricoes,xProx,yProx,mvr,verifAdiante,dim,quantasInt,escreveArquivo);
					if(retorno)
						return true;
					else {
						//caso nao deu certo, testa com os outros valores
						matrizVariaveis[xAtual][yAtual].setValorAtual(0);
						//no caso de verificacao adiante, reseta as restricoes para ir para o proximo valor
						if(verifAdiante)
							resetaRestricoes(matrizVariaveis,xAtual,yAtual,restricoes,valorAtual,dim);
					}
				}
			}
		}
		return false;
	}
	
	//metodo que resetara as listas de disponiveis para os adjacentes e para os restritivos do valoratual
	public void resetaRestricoes(Variavel[][] matrizVariaveis, Integer xAtual, Integer yAtual,
								 Restricao[] restricoes, Integer valorAtual, Integer dim) {
		for(int j = 0;j < dim;j++) {
			if(matrizVariaveis[xAtual][j].getValorAtual() == 0) {
				if(!matrizVariaveis[xAtual][j].getValoresDisponiveis().contains(valorAtual))
					matrizVariaveis[xAtual][j].getValoresDisponiveis().add(valorAtual);
			}
		}
		
		for(int i = 0; i< dim;i++) {
			if(matrizVariaveis[i][yAtual].getValorAtual() == 0) {
				if(!matrizVariaveis[i][yAtual].getValoresDisponiveis().contains(valorAtual))
					matrizVariaveis[i][yAtual].getValoresDisponiveis().add(valorAtual);
			}
		}
		
		//bloco que adiciona os valores de restricao nos excluidos previamente
		for(Restricao r : restricoes) {
			if(r.getX1() == xAtual && r.getY1() == yAtual) {
				ArrayList<Integer> nova = matrizVariaveis[xAtual][yAtual].listaNova(dim, valorAtual, false);
				for(Integer c : nova) {
					if(restricoesRespeitadas(matrizVariaveis, restricoes, r.getX2(), r.getY2(), c, dim) &&
					   !matrizVariaveis[r.getX2()][r.getY2()].getValoresDisponiveis().contains(c))
						matrizVariaveis[r.getX2()][r.getY2()].getValoresDisponiveis().add(c);
				}
			}
			else if (r.getX2() == xAtual && r.getY2() == yAtual) {
				ArrayList<Integer> nova = matrizVariaveis[xAtual][yAtual].listaNova(dim, valorAtual, true);
				for(Integer c : nova) {
					if(restricoesRespeitadas(matrizVariaveis, restricoes, r.getX1(), r.getY1(), c, dim) &&
					   !matrizVariaveis[r.getX1()][r.getY1()].getValoresDisponiveis().contains(c)){
						matrizVariaveis[r.getX1()][r.getY1()].getValoresDisponiveis().add(c);
					}
				}
			}
		}
	}
	
	// metodo que imprime o resultado
	public void imprimeResultado(Variavel[][] matrizVariaveis, Integer dim, Integer quantasInt, PrintWriter escreveArquivo) throws FileNotFoundException {
		for(int i =0;i < dim;i++) {
			
			for(int j = 0;j < dim;j++)
				escreveArquivo.print(matrizVariaveis[i][j].getValorAtual()+" ");

			escreveArquivo.println();
		}
		escreveArquivo.println();
		escreveArquivo.println("Foram realizadas: "+quantasInt+" atribuições");
		escreveArquivo.flush();
	}
	
	//metodo do mvr
	public String escolheProximoIndice(Variavel[][] matrizVariaveis,Integer dim) {
		Integer menorValoresDisponiveis = dim+1;
		Integer xMaior = 0,yMaior = 0;
		
		for(int i = 0; i < dim;i++) {
			for(int j = 0; j < dim;j++)
			{
				if(matrizVariaveis[i][j].getValorAtual() == 0) {
					if(matrizVariaveis[i][j].getValoresDisponiveis().size() < menorValoresDisponiveis) {
						menorValoresDisponiveis = matrizVariaveis[i][j].getValoresDisponiveis().size();
						xMaior = i;
						yMaior = j;
					}
				}
			}
		}
		return xMaior +","+ yMaior;
	}
	
	//funcao que verifica se dado um valor e uma posicao, as restricoes serao respeitadas
	public Boolean restricoesRespeitadas(Variavel[][] matrizVariaveis, Restricao[] restricoes,
								      Integer xAtual,Integer yAtual, Integer valorAtual, Integer dim) {
		for(int j = 0;j < dim;j++) {
			if(j != yAtual) {
				if(matrizVariaveis[xAtual][j].getValorAtual() == valorAtual || matrizVariaveis[xAtual][j].listaVazia())
					return false;
			}
		}
		
		for(int i = 0; i< dim;i++) {
			if(i != xAtual)
				if(matrizVariaveis[i][yAtual].getValorAtual() == valorAtual || matrizVariaveis[i][yAtual].listaVazia())
					return false;
		}
		
		for(Restricao r : restricoes) {
			if(r.getX1() == xAtual && r.getY1() == yAtual)
			{
				if(matrizVariaveis[r.getX2()][r.getY2()].getValorAtual() != 0) {
					if(matrizVariaveis[r.getX2()][r.getY2()].getValorAtual() < valorAtual)
						return false;
				}
			}
			else if (r.getX2() == xAtual && r.getY2() == yAtual)
			{
				if(matrizVariaveis[r.getX1()][r.getY1()].getValorAtual() != 0) {
					if(matrizVariaveis[r.getX1()][r.getY1()].getValorAtual() > valorAtual)
						return false;
				}
			}
		}
		return true; //todas as restricoes estao sendo respeitadas;
	}
	
	//verifica se esta tudo preenchido
	public Boolean todasCorretamentePreenchidas(Variavel[][] matrizVariaveis, Integer dim) {
		for(int i = 0; i < dim;i++) {
			for(int j = 0; j < dim;j++) {
				if(matrizVariaveis[i][j].getValorAtual() == 0)
					return false;
			}
		}
		return true;
	}
	
	//rotina para a verificacao adiante
	public Boolean verificacaoAdiante(Variavel[][] matrizVariaveis, Restricao[] restricoes,
									Integer xAtual, Integer yAtual, Integer valorAtual){
		//bloco que tira o valorAtual dos adjacentes na matriz
		for(int i = 0; i < matrizVariaveis.length; i++){
			if(matrizVariaveis[i][yAtual].getValorAtual() == 0 && i != xAtual){
				matrizVariaveis[i][yAtual].excluiValor(valorAtual);
				if(matrizVariaveis[i][yAtual].listaVazia()) return false;
				//se alguma lista de disponiveis estiver vazia, retorna false
			}
		}
		for(int i = 0; i < matrizVariaveis.length; i++){
			if(matrizVariaveis[xAtual][i].getValorAtual() == 0 && i != yAtual){
				matrizVariaveis[xAtual][i].excluiValor(valorAtual);
				if(matrizVariaveis[xAtual][i].listaVazia()) return false;
				//se alguma lista de disponiveis estiver vazia, retorna false
			}
		}
		
		//bloco que tira os valores possiveis de acordo com as restrições de maior/menor
		for(Restricao r : restricoes) {
			if(r.getX1() == xAtual && r.getY1() == yAtual && matrizVariaveis[r.getX2()][r.getY2()].getValorAtual() == 0) {
				matrizVariaveis[r.getX2()][r.getY2()].removeValores(valorAtual,false);
				if(matrizVariaveis[r.getX2()][r.getY2()].listaVazia()) return false;
			}
			else if (r.getX2() == xAtual && r.getY2() == yAtual && matrizVariaveis[r.getX1()][r.getY1()].getValorAtual() == 0) {
				matrizVariaveis[r.getX1()][r.getY1()].removeValores(valorAtual,true);
				if(matrizVariaveis[r.getX1()][r.getY1()].listaVazia()) return false;
			}
		}
		
		return true;
	}
	
}
