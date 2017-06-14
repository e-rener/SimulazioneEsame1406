package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	ArtsmiaDAO dao = new ArtsmiaDAO();
	List<Integer> anni;
	DirectedGraph<Exhibition, DefaultEdge> graph;
	public Map<Integer, Exhibition> exhibitions; //change to private
	
	public Model(){
		this.anni = new ArrayList<Integer>();
		exhibitions = new HashMap<Integer, Exhibition>();
	}

	public List<Integer> getAnni() {
		anni.addAll(dao.getAnni());
		Collections.sort(anni);
		return anni;
	}

	public void creaGrafo(int anno) {
		
		this.graph = new SimpleDirectedGraph<Exhibition, DefaultEdge>(DefaultEdge.class);
		
		Graphs.addAllVertices(graph, dao.findVertices(anno, exhibitions));
		
		for(CoupleExhibition ce: dao.getEdges(anno, exhibitions)){
			graph.addEdge(ce.getE1(), ce.getE2());
		}
		
	}

	public String isConnected() {
		String res = "";
		Map <Integer,Exhibition> temp = new HashMap<Integer,Exhibition>();
//		BreadthFirstIterator<Exhibition, DefaultEdge> bfi = new BreadthFirstIterator<Exhibition, DefaultEdge>(graph);
//		while(bfi.hasNext()){
//			Exhibition e = bfi.next();
//			if(!temp.containsKey(e.getId()));
//				temp.put(e.getId(),e);
//		}
		ConnectivityInspector<Exhibition, DefaultEdge>  ci = new ConnectivityInspector<Exhibition, DefaultEdge> (graph);
		if(ci.isGraphConnected())
			res = "Il grafo è connesso\n";
		else
			res = "Il grafo non è connesso\n";
		return res;
	}

	public Exhibition getBiggestExhibition() {
		List<Exhibition> temp = new LinkedList<Exhibition>(exhibitions.values());
		Collections.sort(temp);
		
		return temp.get(0);
	}

}
