package istv.models;

import java.util.ArrayList;

public class Boite {

	private int id;
	private int capacit�;
	private int r�sidu;
	private ArrayList<Objet> listeObjet = new ArrayList<Objet>();
	
	public Boite(int id, int capacit�, int r�sidu, ArrayList<Objet> ao) {
		this.id = id;
		this.capacit� = capacit�;
		this.r�sidu = r�sidu;
		this.listeObjet = ao;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getCapacit�()  {
		return this.capacit�;
	}
	
	public void setCapacit�(int capacit�) {
		this.capacit� = capacit�;
	}
	
	public int getR�sidu() {
		return this.r�sidu;
	}
	
	public void setR�sidu(int r�sidu) {
		this.r�sidu = r�sidu;
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
	
	public void calculR�sidu() {
		int temp = 0;
		for(int i = 0; i<this.listeObjet.size(); i++) {
			temp = this.listeObjet.get(i).getPoids();
		}
		this.r�sidu = this.capacit� - temp;
	}
}
