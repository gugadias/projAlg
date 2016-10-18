package br.usp.operacoes;

import br.usp.util.Restricao;
import br.usp.util.Variavel;

public class Backtracking {

	public boolean preencheComRestricoes(Variavel[][] matrizVariaveis, Restricao[] restricoes,
								      Integer xAtual,Integer yAtual, Boolean mvr, Boolean verifAdiante,
								      Integer dim) {
		
		if(todasCorretamentePreenchidas(matrizVariaveis,dim)) {
			imprimeResultado(matrizVariaveis,dim);
			return true;
		}
		
		for(Integer valorAtual : matrizVariaveis[xAtual][yAtual].getValoresDisponiveis()) {
			if(restricoesRespeitadas(matrizVariaveis, restricoes, xAtual, yAtual, valorAtual,dim)) {//verifica se respeita as restricoes
				
				if(verifAdiante) {
					if(!verificacaoAdiante(matrizVariaveis, restricoes, xAtual, yAtual, valorAtual)){
						matrizVariaveis[xAtual][yAtual].setValorAtual(0);
						resetaRestricoes(matrizVariaveis,xAtual,yAtual,valorAtual,dim);
						return false;
					}
				}
				
				matrizVariaveis[xAtual][yAtual].setValorAtual(valorAtual);
				
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
						resetaRestricoes(matrizVariaveis,xAtual,yAtual,valorAtual,dim);
				}
			}
		}
		return false;
	}
	
	public void resetaRestricoes(Variavel[][] matrizVariaveis, Integer xAtual, Integer yAtual,Integer valorAtual, Integer dim) {
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
		for(int i = 0; i < matrizVariaveis.length; i++){
			if(matrizVariaveis[i][yAtual].getValorAtual() == 0){
				matrizVariaveis[i][yAtual].excluiValor(valorAtual);
				if(matrizVariaveis[i][yAtual].listaVazia()) return false;
				//se alguma lista de disponiveis estiver vazia, retorna false
			}
		}
		for(int i = 0; i < matrizVariaveis.length; i++){
			if(matrizVariaveis[xAtual][i].getValorAtual() == 0){
				matrizVariaveis[xAtual][i].excluiValor(valorAtual);
				if(matrizVariaveis[xAtual][i].listaVazia()) return false;
				//se alguma lista de disponiveis estiver vazia, retorna false
			}
		}
		return true;
	}
	
}
