package istv.main;

import java.util.ArrayList;

import istv.models.Boite;
import istv.models.Objet;

public class Probleme {
	
	private ArrayList<Objet> listeObjet = new ArrayList();
	private ArrayList<Integer> tailleDisponible = new ArrayList();
	private ArrayList<Boite> solution = new ArrayList();
	private int résiduTotal;
	
	public Probleme(ArrayList<Objet> lo, ArrayList<Integer> td, ArrayList<Boite> sol, int rt) {
		this.listeObjet = lo;
		this.tailleDisponible = td;
		this.solution = sol;
		this.résiduTotal = rt;
	}
	
	public void insertionObjetDansBoite(Objet o, Boite b) {
		b.addObjet(o);
		System.out.println("Insertion Objet : " + o.getId() + " ayant un poids de " + o.getPoids());
		System.out.println("dans la boite " + b.getId() + " qui a un résidu de " + b.getRésidu());
		b.calculRésidu();
		System.out.println("Objet " + b.getId() + " nouveau résidu : " + b.getRésidu());
		
	}
	
	public void calculRésidu() {
		for(int i = 0; i<this.solution.size(); i++) {
			this.solution.get(i).calculRésidu();
			this.résiduTotal = this.résiduTotal + this.solution.get(i).getRésidu();
		}
		
	}
	
	
	
	
	/**
	 * Insertion de l'objet dans la boite de plus petite capacité supérieur au poids de l'objet
	 * @param o
	 */
	public void trouvePlusPetiteBoite(Objet o) {
		int min = Integer.MAX_VALUE; 
		boolean newBoite = true;
		for(int i=0; i<this.tailleDisponible.size(); i++) {
			if(this.tailleDisponible.get(i) > o.getPoids() && this.tailleDisponible.get(i) < min)
				min = this.tailleDisponible.get(i);
		}
		if(this.solution.isEmpty() == false) {
			for(int j=0; j<this.solution.size(); j++) {
				//Rajouter la vérif de  : "est ce que l'objet a une couleur satisfaisante pour être inséré dans la boite 
				if(this.solution.get(j).getRésidu() > o.getPoids() && this.solution.get(j).getRésidu() < min 
						/*&& this.solution.get(j).??(o)*/) {
					newBoite = false;
					min = j;
				} 
			}
		}
		if(newBoite == false) {
			this.solution.get(min).addObjet(o);
		}
		else {
			Boite b = new Boite(min);
			b.addObjet(o);
		}				
	}
	
	
	/**
	 * Solution simple, pas optimale 
	 */
	public void solutionSimple() {
		for(int i=0; i<this.listeObjet.size(); i++) {
			this.trouvePlusPetiteBoite(this.listeObjet.get(i));
			this.listeObjet.remove(i);
		}
	}
	
	
	
	
}
