package it.polito.tdp.crimes.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento>{
	
	public enum EventType {
		daRisolvere, risolto;
	}
	
	Event e;
	EventType tipo;
	LocalDateTime tempo;

	public Evento(Event e, EventType tipo, LocalDateTime tempo) {
		super();
		this.e = e;
		this.tipo = tipo;
		this.tempo=tempo;
	}

	public Event getE() {
		return e;
	}
	
	public EventType getTipo() {
		return tipo;
	}
	public LocalDateTime getTempo() {
		return tempo;
	}

	@Override
	public int compareTo(Evento o) {
		return this.tempo.compareTo(o.tempo);
	}
	
	

}
