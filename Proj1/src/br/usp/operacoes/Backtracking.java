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
		
		Integer valorAtual = recebeProximoValor(matrizVariaveis[xAtual][yAtual]);
		
		if(verifAdiante) {
			if(verificacaoAdiante(matrizVariaveis, restricoes, xAtual, yAtual, valorAtual))
				return false;
		}
		
		if(restricoesRespeitadas(matrizVariaveis, restricoes, xAtual, yAtual, valorAtual,dim)) {//verifica se respeita as restricoes
			if(xAtual == dim-1 && yAtual == dim-1) {
				imprimeResultado(matrizVariaveis,dim);
				return true;
			}
			
			if(mvr) {
				String proxIndice;
				proxIndice = escolheProximoIndice(matrizVariaveis,restricoes,mvr, dim);
				String[] aux = proxIndice.split(",");
				xAtual = Integer.parseInt(aux[0]);
				yAtual = Integer.parseInt(aux[1]);
			}
			else {
				if(xAtual == dim-1) {
					yAtual++;
				}
				else if (yAtual == dim-1) {
					xAtual++;
					yAtual = 0;
				}
				else {
					xAtual++;
					yAtual++;
				}
			}
			return preencheComRestricoes(matrizVariaveis,restricoes,xAtual,yAtual,mvr,verifAdiante,dim);
		}
		System.out.println("Nao foi possivel solucionar o problema");
		return false;
	}
	
	public void imprimeResultado(Variavel[][] matrizVariaveis, Integer dim) {
		for(int i =0;i < dim;i++) {
			
			for(int j = 0;j < dim;j++)
				System.out.print(matrizVariaveis[i][j].getValorAtual()+" ");

			System.out.println();
		}
	}
	
	public String escolheProximoIndice(Variavel[][] matrizVariaveis, Restricao[] restricoes,
									   Boolean mvr, Integer dim) {
		Integer menorValoresDisponiveis = dim+1;
		Integer xMaior = 0,yMaior = 0;
		
		for(int i = 0; i < dim;i++) {
			for(int j = 0; j < dim;i++)
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
			for(int j = 0; j < dim;i++) {
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
