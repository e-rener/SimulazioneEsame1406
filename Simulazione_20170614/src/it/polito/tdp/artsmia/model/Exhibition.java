package it.polito.tdp.artsmia.model;

public class Exhibition implements Comparable<Exhibition>{

	private int id;
	private int begin;
	private int end;
	private int numObjects;

	public Exhibition(int id, int begin, int end, int numObjects) {
		this.id = id;
		this.begin = begin;
		this.end = end;
		this.numObjects = numObjects;
	}

	@Override
	public String toString() {
		return "Exhibition " + id + begin + end +", numObjects=" + numObjects;
	}

	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public int compareTo(Exhibition o) {
		return o.numObjects - this.numObjects;
	}
	
	

}
