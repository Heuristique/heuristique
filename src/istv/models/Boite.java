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

	public Boite() {
		// TODO Auto-generated constructor stub
	}

	/**Compte le nombre de couleur afin de verifier si l'objet o1 est ajoutable dans notre sac ou non**/
	public boolean verifCouleur(Objet o1){
		
		boolean ajoutable = false;
		int compteurCouleur = 0;
		ArrayList<Integer> couleurDansLaListe = new ArrayList<Integer>();
		for(Objet o : listeObjet) {
			if(!couleurDansLaListe.contains(o.getCouleur())) {
				couleurDansLaListe.add(o.getCouleur());
				compteurCouleur ++;
			}
		}
		
		if(couleurDansLaListe.contains(o1.getCouleur())) {
			ajoutable = true;
		}
		else if (compteurCouleur < 2){
			ajoutable = true;
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
	
	@Override
	public String toString() {
		String newString = "";
		for(Objet o : this.listeObjet) {
			newString = newString + o;
		}
		return "\n Boite n°" + this.id + " ayant pour résidu : " + this.résidu +
				" liste des objets qu'elle contient : \n" + newString;
	}
}
