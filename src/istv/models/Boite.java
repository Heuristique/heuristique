package istv.models;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Boite {

	private static final AtomicInteger count = new AtomicInteger(0);
	private int id;
	private int capacité;
	private int résidu;
	private ArrayList<Objet> listeObjet = new ArrayList<Objet>();
	
	public Boite(int capacité)  {
		id = count.incrementAndGet();
		this.capacité = capacité;
	}
	
	public Boite(int id, int capacité, int résidu, ArrayList<Objet> ao) {
		this.id = id;
		this.capacité = capacité;
		this.résidu = résidu;
		this.listeObjet = ao;
	}

	/**Compte le nombre de couleur afin de verifier si l'objet o1 est ajoutable dans notre sac ou non**/
	public boolean verifCouleur(Objet o1){

		ArrayList<Integer> listeCouleurBoite = new ArrayList<>();
		boolean notIn = true;
		boolean ajoutable = false;
		
		for(Objet o : listeObjet) {
			notIn = true;
			for(Integer i : listeCouleurBoite) {
				if(i == o.getCouleur()) {
					notIn = false;
				}
			}
			if(notIn == true && listeCouleurBoite.size() < 2) {
				listeCouleurBoite.add(o.getCouleur());
			}
			
			/**La couleur de l'objet est dans la liste , ou il n'y a qu'une seule couleur, on peut 
			donc l'ajouter**/
			
			for(Integer i2 : listeCouleurBoite) {
				if(o1.getCouleur() == i2 || listeCouleurBoite.size()  < 2){
					ajoutable = true;
				}
			}
		}
		
		return ajoutable;
	}
	

	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getCapacité()  {
		return this.capacité;
	}
	
	public void setCapacité(int capacité) {
		this.capacité = capacité;
	}
	
	public int getRésidu() {
		return this.résidu;
	}
	
	public void setRésidu(int résidu) {
		this.résidu = résidu;
	}
	
	public ArrayList<Objet> getListeObjet() {
		return this.listeObjet;
	}
	
	public void setListeObjet(ArrayList<Objet> objets) {
		this.listeObjet = objets;
	}
	
	public void addObjet(Objet o) {
		if(verifCouleur(o)) {
		this.listeObjet.add(o);
		this.calculRésidu();
		}
	}
	
	public void removeObjet(Objet o) {
		this.listeObjet.remove(o);
		this.calculRésidu();

	}
	
	public Objet findObjetById(int id) {
		for(int i = 0; i<this.listeObjet.size(); i++) {
			if(this.listeObjet.get(i).getId() == id)
				return this.listeObjet.get(i);
		}
		return new Objet();
	}
	public void SupressObjetById(int id) {
		for(int i = 0; i<this.listeObjet.size(); i++) {
			if(this.listeObjet.get(i).getId() == id)
				this.listeObjet.remove(i);
		}
	}
	public void calculRésidu() {
		int temp = 0;
		for(int i = 0; i<this.listeObjet.size(); i++) {
			temp = temp + this.listeObjet.get(i).getPoids();
		}
		this.résidu = this.capacité - temp;
	}
}
