package istv.models;

import java.util.concurrent.atomic.AtomicInteger;

public class Objet {
	
	private static final AtomicInteger count = new AtomicInteger(0);
	private int id;
	private int poids;
	private int couleur;
	
	public Objet() { }

	
	public Objet(int poids,int couleur) {
		this.id = count.incrementAndGet();
		this.poids = poids;
		this.couleur = couleur;
	}
	public Objet(int id,int poids,int couleur) {
		this.id = id;
		this.poids = poids;
		this.couleur = couleur;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getPoids() {
		return this.poids;
	}
	
	public void setPoids(int poids) {
		this.poids = poids;
	}
	
	public int getCouleur() {
		return this.couleur;
	}
	
	public void setCouleur(int couleur) {
		this.couleur = couleur;
	}
	
	@Override
	public String toString() {
		return "\n Objet n�" + this.id + " est de couleur n�" + this.couleur + " et p�se " + this.poids;
	}

}
