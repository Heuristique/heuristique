package istv.main;

import java.util.ArrayList;

import istv.models.Boite;
import istv.models.Objet;

public class Probleme {
	
	private ArrayList<Objet> listeObjet = new ArrayList();
	private ArrayList<Boite> disponible = new ArrayList();
	private ArrayList<Boite> solution = new ArrayList();
	private int r�siduTotal;
	
	public void insertionObjetDansBoite(Objet o, Boite b) {
		b.addObjet(o);
		System.out.println("Insertion Objet : " + o.getId() + " ayant un poids de " + o.getPoids());
		System.out.println("dans la boite " + b.getId() + " qui a un r�sidu de " + b.getR�sidu());
		b.calculR�sidu();
		System.out.println("Objet " + b.getId() + " nouveau r�sidu : " + b.getR�sidu());
		
	}
	
	public void calculResidu() {
		for(int i = 0; i<this.solution.size(); i++) {
			this.solution.get(i).calculR�sidu();
			this.r�siduTotal = this.r�siduTotal + this.solution.get(i).getR�sidu();
		}
		
	}
	
	public void insertionDansBoiteM�mePoids(Objet o) {
		
	}
	
	
	
}
