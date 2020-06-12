package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.db.EventsDao;
import it.polito.tdp.crimes.db.Vertici;

public class Model {
	
	EventsDao dao = new EventsDao();
	Graph<Vertici, DefaultWeightedEdge> grafo;
	
	public List<Integer> anni(){
		List<Integer> anni = new ArrayList<>();
		for(Event e : dao.listAllEvents()) {
			if(!anni.contains(e.getReported_date().getYear()))
				anni.add(e.getReported_date().getYear());
		}
		return anni;
	}
	
	public Graph<Vertici, DefaultWeightedEdge> creaGrafo(int anno) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, dao.vertici(anno));
		for(Vertici v1 : grafo.vertexSet()) {
			for(Vertici v2 : grafo.vertexSet()) {
				if(!v1.equals(v2) && grafo.getEdge(v1, v2)==null) {
					double peso = LatLngTool.distance(v1.centro(v1.getLat(), v1.getLon()), v2.centro(v2.getLat(), v2.getLon()), LengthUnit.KILOMETER);
					Graphs.addEdge(grafo, v1, v2, peso);
				}
					
			}
		}
		return grafo;
	}
}
