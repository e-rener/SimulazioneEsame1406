package it.polito.tdp.artsmia.model;

public class TestModel {

	public static void main(String[] args) {
		Model model = new Model();
		
		model.creaGrafo(2000);
		System.out.println(model.graph);

	}

}
