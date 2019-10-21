package istv.models;

import java.util.ArrayList;

public class Boite {

	private int id;
	private int capacité;
	private int résidu;
	private ArrayList<Objet> listeObjet = new ArrayList<Objet>();
	
	public Boite(int id, int capacité, int résidu, ArrayList<Objet> ao) {
		this.id = id;
		this.capacité = capacité;
		this.résidu = résidu;
		this.listeObjet = ao;
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
		this.listeObjet.add(o);
	}
	
	public void removeObjet(Objet o) {
		this.listeObjet.remove(o);
	}
	
	public Objet findObjetById(int id) {
		for(int i = 0; i<this.listeObjet.size(); i++) {
			if(this.listeObjet.get(i).getId() == id)
				return this.listeObjet.get(i);
		}
		return new Objet();
	}
	
	public void calculRésidu() {
		int temp = 0;
		for(int i = 0; i<this.listeObjet.size(); i++) {
			temp = this.listeObjet.get(i).getPoids();
		}
		this.résidu = this.capacité - temp;
	}
}
