package it.polito.tdp.artsmia.db;

import java.util.List;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Model;

public class TestArtsmiaDAO {

	public static void main(String[] args) {
		
		ArtsmiaDAO dao = new ArtsmiaDAO() ;
		Model model = new Model();
		
		List<ArtObject> objects = dao.listObject() ;
		System.out.println(objects.size());
		
		System.out.println(dao.findVertices(2000, model.exhibitions));

	}

}
