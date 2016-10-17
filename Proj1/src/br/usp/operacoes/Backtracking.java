package br.usp.operacoes;

import br.usp.util.Restricao;;

public class Backtracking {

	public void preencheComRestricoes(Integer[][] matrizVariaveis, Restricao[] restricoes,
								      Integer xAtual,Integer yAtual, Integer valoresDisponiveis) {
		
		Boolean mvr = false;
		Boolean verifAdiante = false;
		
		while(!todasCorretamentePreenchidas(matrizVariaveis, restricoes)) {
			//Integer valor = recebeProximoValor
			//escolhe valor
			//verifica se respeita as restricoes
		}
	}
	
	public Boolean restricoesRespeitadas(Integer[][] matrizVariaveis, Restricao[] restricoes,
								      Integer xAtual,Integer yAtual, Integer valorAtual) {
		for(int i = xAtual;i < matrizVariaveis.length;i++) {
			for(int j = 0;j < matrizVariaveis.length;j++) {
					
			}
		}
		return false;
	}
	
	public Boolean todasCorretamentePreenchidas(Integer[][] matrizVariaveis, Restricao[] restricoes) {
		
		return false;
	}
}
