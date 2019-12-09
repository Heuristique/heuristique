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
	
	@Override
	public String toString() {
		String newString = "";
		for(Objet o : this.listeObjet) {
			newString = newString + o;
		}
		return "\n Boite n�" + this.id + " ayant pour r�sidu : " + this.r�sidu +
				" liste des objets qu'elle contient : \n" + newString;
	}
}
