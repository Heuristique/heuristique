package istv.models;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Boite {

	private static final AtomicInteger count = new AtomicInteger(0);
	private int id;
	private int capacit�;
	private int r�sidu;
	private ArrayList<Objet> listeObjet = new ArrayList<Objet>();
	
	public Boite(int capacit�)  {
		id = count.incrementAndGet();
		this.capacit� = capacit�;
	}
	
	public Boite(int id, int capacit�, int r�sidu, ArrayList<Objet> ao) {
		this.id = id;
		this.capacit� = capacit�;
		this.r�sidu = r�sidu;
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
		if(verifCouleur(o)) {
		this.listeObjet.add(o);
		this.calculR�sidu();
		}
	}
	
	public void removeObjet(Objet o) {
		this.listeObjet.remove(o);
		this.calculR�sidu();

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
	public void calculR�sidu() {
		int temp = 0;
		for(int i = 0; i<this.listeObjet.size(); i++) {
			temp = temp + this.listeObjet.get(i).getPoids();
		}
		this.r�sidu = this.capacit� - temp;
	}
}
