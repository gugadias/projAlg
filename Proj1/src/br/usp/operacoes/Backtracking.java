package br.usp.operacoes;

import br.usp.util.Restricao;
import br.usp.util.Variavel;

public class Backtracking {

	public boolean preencheComRestricoes(Variavel[][] matrizVariaveis, Restricao[] restricoes,
								      Integer xAtual,Integer yAtual, Integer dim) {
		
		Boolean mvr = false; //flag para mvr
		Boolean grau = false; //flag para grau
		Boolean verifAdiante = false; //flag para adiante
		
		if(todasCorretamentePreenchidas(matrizVariaveis, restricoes))
			return true;
		
		Integer valorAtual = recebeProximoValor(matrizVariaveis[xAtual][yAtual]);
		if(restricoesRespeitadas(matrizVariaveis, restricoes, xAtual, yAtual, valorAtual,dim)) {//verifica se respeita as restricoes
			String proxIndice = escolheProximoIndice(matrizVariaveis,restricoes,mvr,grau, dim);
			return preencheComRestricoes(matrizVariaveis,restricoes,xAtual,yAtual,dim);
		}
		
		return false;
	}
	
	public String escolheProximoIndice(Variavel[][] matrizVariaveis, Restricao[] restricoes,
									   Boolean mvr, Boolean grau, Integer dim) {
		
		if(mvr) //deve escolher o valor mais restritivo
		{
			for(int i = 0; i < dim;i++) {
				for(int j = 0; j < dim;i++){
					if(matrizVariaveis[i][j].getValorAtual() == 0) {
						
					}
				}
			}
		}
		return null;
	}
	
	public Integer recebeProximoValor(Variavel varAtual) {
		return varAtual.getValoresDisponiveis().remove(0);
	}
	
	public Boolean restricoesRespeitadas(Variavel[][] matrizVariaveis, Restricao[] restricoes,
								      Integer xAtual,Integer yAtual, Integer valorAtual, Integer dim) {
		for(int j = 0;j < dim;j++) {
			if(j != yAtual) {
				if(matrizVariaveis[xAtual][j].getValorAtual() == valorAtual || matrizVariaveis[xAtual][j].getValoresDisponiveis().isEmpty())
					return false;
			}
		}
		
		for(int i = 0; i< dim;i++) {
			if(i != xAtual)
				if(matrizVariaveis[i][yAtual].getValorAtual() == valorAtual || matrizVariaveis[i][yAtual].getValoresDisponiveis().isEmpty())
					return false;
		}
		
		for(Restricao r : restricoes) {
			if(r.getX1() == xAtual && r.getY1() == yAtual)
			{
				if(matrizVariaveis[r.getX2()][r.getX2()].getValorAtual() < valorAtual)
					return false;
			}
			else if (r.getX2() == xAtual && r.getY2() == yAtual)
			{
				if(matrizVariaveis[r.getX1()][r.getX1()].getValorAtual() > valorAtual)
					return false;
			}
		}
		return true; //todas as restricoes estao sendo respeitadas;
	}
	
	public Boolean todasCorretamentePreenchidas(Variavel[][] matrizVariaveis, Restricao[] restricoes) {
		
		return false;
	}
}
