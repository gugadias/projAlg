package br.usp.util;

import java.util.ArrayList;

public class Variavel {

	private ArrayList<Integer> valoresDisponiveis;
	private Integer valorAtual;

	public void excluiValor(Integer valor) {
		valoresDisponiveis.remove(valor);
	}
	
	public void removeValores(Integer valorAtual, Boolean maior) {
		ArrayList<Integer> copia = new  ArrayList<Integer>();
		for(Integer i : valoresDisponiveis)
			copia.add(i);
		for(Integer i : copia) {
			if(!maior && i < valorAtual)
				valoresDisponiveis.remove(i);
			else if(maior && i > valorAtual)
				valoresDisponiveis.remove(i);
		}
	}
	
	public ArrayList<Integer> listaNova(Integer dim, Integer valorAtual, Boolean maior) {
		ArrayList<Integer> nova = new ArrayList<Integer>();
		for(int i = 1;i <= dim; i++) {
			if(maior && i > valorAtual)
				nova.add(i);
			else if(!maior && i < valorAtual)
				nova.add(i);
		}
		return nova;
	}
	
	public void adicionaValores(Integer valorAtual, Boolean maior) {
		ArrayList<Integer> copia = valoresDisponiveis;
		for(Integer i : copia) {
			if(!maior && i < valorAtual)
				valoresDisponiveis.remove(i);
			else if(maior && i > valorAtual)
				valoresDisponiveis.remove(i);
		}
	}
	
	public void resetaLista() {
		Integer dim = valoresDisponiveis.size(); 
		valoresDisponiveis.clear();
		setaLista(dim);
	}
	
	public Boolean listaVazia(){
		return valoresDisponiveis.isEmpty();
	}
	
	public void setaLista(Integer dimensao) {
		if(this.valoresDisponiveis == null)
			this.valoresDisponiveis = new ArrayList<Integer>();
		
		for(int i = 1; i <= dimensao;i++)
			this.valoresDisponiveis.add(i);

	}
	
	public Variavel(Integer dimensao) {
		this.valorAtual = 0;
		this.valoresDisponiveis = new ArrayList<Integer>();
		setaLista(dimensao);
	}
	
	public Variavel(ArrayList<Integer> valoresDisponiveis, Integer valorAtual) {
		super();
		this.valoresDisponiveis = valoresDisponiveis;
		this.valorAtual = valorAtual;
	}
	
	public ArrayList<Integer> getValoresDisponiveis() {
		return valoresDisponiveis;
	}
	
	public void setValoresDisponiveis(ArrayList<Integer> valoresDisponiveis) {
		this.valoresDisponiveis = valoresDisponiveis;
	}
	
	public Integer getValorAtual() {
		return valorAtual;
	}
	
	public void setValorAtual(Integer valorAtual) {
		this.valorAtual = valorAtual;
	}
}
