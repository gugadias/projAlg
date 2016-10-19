package br.usp.operacoes;

import java.util.ArrayList;

import br.usp.util.Restricao;
import br.usp.util.Variavel;

public class Backtracking {

	public boolean preencheComRestricoes(Variavel[][] matrizVariaveis, Restricao[] restricoes,
								      Integer xAtual,Integer yAtual, Boolean mvr, Boolean verifAdiante,
								      Integer dim) {
		imprimeResultado(matrizVariaveis, dim);
		if(todasCorretamentePreenchidas(matrizVariaveis,dim)) {
			imprimeResultado(matrizVariaveis,dim);
			return true;
		}
		
		Boolean corretoAux = true; 
		
		for(Integer valorAtual : matrizVariaveis[xAtual][yAtual].getValoresDisponiveis()) {
			if(restricoesRespeitadas(matrizVariaveis, restricoes, xAtual, yAtual, valorAtual,dim)) {//verifica se respeita as restricoes
				
				matrizVariaveis[xAtual][yAtual].setValorAtual(valorAtual);
				
				if(todasCorretamentePreenchidas(matrizVariaveis,dim)) {
					imprimeResultado(matrizVariaveis,dim);
					return true;
				}
				
				if(verifAdiante) 
					corretoAux = verificacaoAdiante(matrizVariaveis, restricoes, xAtual, yAtual, valorAtual); 
					
				if(!corretoAux) {
					matrizVariaveis[xAtual][yAtual].setValorAtual(0);
					resetaRestricoes(matrizVariaveis,xAtual,yAtual,restricoes,valorAtual,dim);
				}
				else {
						
					if(xAtual == dim-1 && yAtual == dim-1 && !mvr) {
						imprimeResultado(matrizVariaveis,dim);
						return true;
					}
						
					int xProx = xAtual,yProx = yAtual;
					if(mvr) {
						String proxIndice;
						proxIndice = escolheProximoIndice(matrizVariaveis, dim);
						String[] aux = proxIndice.split(",");
						xProx = Integer.parseInt(aux[0]);
						yProx = Integer.parseInt(aux[1]);
					}
					else {
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
					Boolean retorno = preencheComRestricoes(matrizVariaveis,restricoes,xProx,yProx,mvr,verifAdiante,dim);
					if(retorno)
						return true;
					else {
						matrizVariaveis[xAtual][yAtual].setValorAtual(0);		
						if(verifAdiante)
							resetaRestricoes(matrizVariaveis,xAtual,yAtual,restricoes,valorAtual,dim);
					}
				}
			}
		}
		return false;
	}
	
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
						matrizVariaveis[r.getX2()][r.getX2()].getValoresDisponiveis().add(c);
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
	
	public void imprimeResultado(Variavel[][] matrizVariaveis, Integer dim) {
		for(int i =0;i < dim;i++) {
			
			for(int j = 0;j < dim;j++)
				System.out.print(matrizVariaveis[i][j].getValorAtual()+" ");

			System.out.println();
		}
		System.out.println();
	}
	
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
	
	public Integer recebeProximoValor(Variavel varAtual) {
		return varAtual.getValoresDisponiveis().remove(0);
	}
	
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
	
	public Boolean todasCorretamentePreenchidas(Variavel[][] matrizVariaveis, Integer dim) {
		for(int i = 0; i < dim;i++) {
			for(int j = 0; j < dim;j++) {
				if(matrizVariaveis[i][j].getValorAtual() == 0)
					return false;
			}
		}
		return true;
	}
	
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
		
		//bloco que tira os valores de restricao dos valores remanescentes
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
