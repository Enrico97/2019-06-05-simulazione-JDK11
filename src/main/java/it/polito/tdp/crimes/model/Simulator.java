package it.polito.tdp.crimes.model;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.crimes.db.Vertici;
import it.polito.tdp.crimes.model.Evento.EventType;

public class Simulator {
	
	private Graph<Vertici, DefaultWeightedEdge> grafo;
	private PriorityQueue<Evento> queue = new PriorityQueue<>();
	int malGestiti;
	private Map<Integer, Integer> mappa;
	private Model model;
	
	
	public void simulator(Model model, Graph<Vertici, DefaultWeightedEdge> grafo, int anno, int mese, int giorno, int N) {
		this.model=model;
		this.grafo=grafo;
		queue.clear();
		malGestiti=0;
		mappa=new HashMap<>();
		
		for(Vertici v : grafo.vertexSet())
			mappa.put(v.getId(), 0);
		
		int minimo = model.criminalita(anno).get(1);
		int id=0;
		for(Integer i : model.criminalita(anno).keySet()) {
			if(model.criminalita(anno).get(i)<minimo) {
				id=i;
			}
			}
		mappa.put(id, N);
		
		for(Event e : model.listaEventi()) {
			if (e.getReported_date().getYear()==anno && e.getReported_date().getMonth().getValue()==mese && e.getReported_date().getDayOfMonth()==giorno)
				queue.add(new Evento(e, EventType.daRisolvere, e.getReported_date()));
		}
		while(!this.queue.isEmpty()) {
			Evento e = this.queue.poll();
			processEvent(e);
	}
	}
	
	public void processEvent(Evento e) {
		
		switch (e.getTipo()) {
		
			case daRisolvere:
				if(mappa.get(e.getE().getDistrict_id())>0) {
					mappa.put(e.getE().getDistrict_id(), mappa.get(e.getE().getDistrict_id())-1);
					if(e.getE().getOffense_category_id().compareTo("all_other_crimes")!=0)
						queue.add(new Evento(null, EventType.risolto, e.getE().getReported_date().plusHours(2)));
					else {
						if(Math.random()<0.5)
							queue.add(new Evento(null, EventType.risolto, e.getE().getReported_date().plusHours(2)));
						else
							queue.add(new Evento(null, EventType.risolto, e.getE().getReported_date().plusHours(1)));
					}
				}
				else {
					Vertici vertice=null;
					Vertici vertice1=null;
					Vertici arrivo=null;
					double peso = 50000;
					for(Vertici v : grafo.vertexSet()) {
						if(v.getId()==e.getE().getDistrict_id())
							vertice=v;
					}
					for(Integer i : mappa.keySet()) {
						if(vertice.getId()!=i && mappa.get(i)>0) {
							for(Vertici v : grafo.vertexSet()) {
								if(v.getId()==i) {
									vertice1=v;
									if(grafo.getEdgeWeight(grafo.getEdge(vertice, vertice1))<peso) {
										peso=grafo.getEdgeWeight(grafo.getEdge(vertice, vertice1));
										arrivo=vertice1;
							}	
							}
						}
						}
					}
					if(grafo.getEdgeWeight(grafo.getEdge(vertice, arrivo))/60>0.25)
						malGestiti++;
					mappa.put(arrivo.getId(), mappa.get(arrivo.getId())-1);
					if(e.getE().getOffense_category_id().compareTo("all_other_crimes")!=0)
						queue.add(new Evento(null, EventType.risolto, e.getE().getReported_date().plusHours(2)));
					else {
						if(Math.random()<0.5)
							queue.add(new Evento(null, EventType.risolto, e.getE().getReported_date().plusHours(2)));
						else
							queue.add(new Evento(null, EventType.risolto, e.getE().getReported_date().plusHours(1)));
					}
					}
		
			break;
			
			case risolto:
				mappa.put(e.getE().getDistrict_id(), mappa.get(e.getE().getDistrict_id())+1);
				break;
				
		}	
	}

	public int malGestiti() {
		return malGestiti;
	}
}
