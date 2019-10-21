package istv.main;

import java.util.ArrayList;

import istv.models.Boite;
import istv.models.Objet;

public class Probleme {
	
	private ArrayList<Objet> listeObjet = new ArrayList();
	private ArrayList<Boite> disponible = new ArrayList();
	private ArrayList<Boite> solution = new ArrayList();
	private int résiduTotal;
	
	public void insertionObjetDansBoite(Objet o, Boite b) {
		b.addObjet(o);
		System.out.println("Insertion Objet : " + o.getId() + " ayant un poids de " + o.getPoids());
		System.out.println("dans la boite " + b.getId() + " qui a un résidu de " + b.getRésidu());
		b.calculRésidu();
		System.out.println("Objet " + b.getId() + " nouveau résidu : " + b.getRésidu());
		
	}
	
	public void calculResidu() {
		for(int i = 0; i<this.solution.size(); i++) {
			this.solution.get(i).calculRésidu();
			this.résiduTotal = this.résiduTotal + this.solution.get(i).getRésidu();
		}
		
	}
	
	public void insertionDansBoiteMêmePoids(Objet o) {
		
	}
	
	
	
}
