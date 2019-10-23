package istv.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import istv.models.Boite;
import istv.models.Objet;

public class Probleme {
	
	private ArrayList<Objet> listeObjet = new ArrayList();
	private ArrayList<Integer> tailleDisponible = new ArrayList();
	private ArrayList<Boite> solution = new ArrayList();
	private int résiduTotal;
	private int nombreDeCouleur;
	private int nombreDeBoite;
	private int nombreDobjet;
	
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
	
	public void affichageSolutiuon() {
		for(int i = 0; i<this.solution.size(); i++) {
			
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
			if(this.tailleDisponible.get(i) >= o.getPoids() && this.tailleDisponible.get(i) < min)
				min = this.tailleDisponible.get(i);
		}
		if(this.solution.isEmpty() == false) {
			for(int j=0; j<this.solution.size(); j++) {
				//Rajouter la vérif de  : "est ce que l'objet a une couleur satisfaisante pour être inséré dans la boite 
				if(this.solution.get(j).getRésidu() >= o.getPoids() && this.solution.get(j).getRésidu() < min 
						 && this.solution.get(j).verifCouleur(o)) {
					newBoite = false;
					min = j;
				} 
			}
		}
		if(newBoite == false) {
			this.solution.get(min).addObjet(o);
			System.out.println("Objet n° : " + o.getId() + " a été ajouté à la boite solution : " + this.solution.get(min));
		}
		else {
			Boite b = new Boite(min);
			b.addObjet(o);
			this.solution.add(b);
			System.out.println("Création d'une nouvelle Boite : " + b.getId() + " de capacité : " + b.getCapacité() + 
					" ajout de l'objet " + o);
		}				
	}
	
	
	/**
	 * Solution simple, pas optimale 
	 */
	public void solutionSimple() {
		for(int i=0; i<this.listeObjet.size(); i++) {
			this.trouvePlusPetiteBoite(this.listeObjet.get(i));
			this.listeObjet.remove(i);
			i--;
		}
	}
	
public void recupererFichier() {
		
		int i= 0;
		int repere = 0;
		ArrayList<Integer> listeNombres= new ArrayList<Integer>();

		/**Lecture du fichier **/
		try {
		InputStream flux=new FileInputStream("test.txt"); 
		InputStreamReader lecture=new InputStreamReader(flux);
		BufferedReader buff=new BufferedReader(lecture);
		String ligne;
		while ((ligne=buff.readLine())!=null){
			System.out.println(ligne);
			Scanner scanner = new Scanner(ligne);
			while( scanner.hasNextInt()) {
				   int value = scanner.nextInt();
				   listeNombres.add(value);
			}
		}
		buff.close(); 
		}		
		catch (Exception e){
		System.out.println(e.toString());
		}
		/**Assignation des données récupéré à des variables/tableaux**/
		nombreDeBoite = listeNombres.get(0);
		
		for (int j = 1;i<=nombreDeBoite;i++) {
			this.tailleDisponible.add(listeNombres.get(j));
			
		}
		 repere = nombreDeBoite+1;
		 nombreDeCouleur = listeNombres.get(repere);
		 repere++;
		 this.nombreDobjet = listeNombres.get(repere);
		 repere++;
		 
		 for(i = repere;i<listeNombres.size();i++) {
			 Objet o1 = new Objet(listeNombres.get(i+1),listeNombres.get(i));
			 this.listeObjet.add(o1);
			 i++;
		 }
		 
		 /**Verification des résultats obtenus**/
		 
		System.out.println("Nombre de couleur : "+this.nombreDeCouleur);
		System.out.println("Nombre de boite :" +this.nombreDeBoite);
		System.out.println("Nombre d'objet :" +this.nombreDobjet);
		
		for(Objet o : this.listeObjet) {
			System.out.println("Objet n°" + o.getId() +"de couleur : "+
					o.getCouleur() + " et de poids" + o.getPoids());	
			}
	}
	
	public static void main(String args[]) {
		Objet o1 = new Objet(1,1,1);
		Objet o2 = new Objet(2,3,2);
		Objet o3 = new Objet(3,2,2);
		Objet o4 = new Objet(4,9,1);
		Objet o5 = new Objet(5,9,1);
		Objet o6 = new Objet(6,11,3);
		Objet o7 = new Objet(7,3,1);
		Objet o8 = new Objet(8,3,3);
		Objet o9 = new Objet(9,5,2);
		Objet o10 = new Objet(10,2,1);
		ArrayList<Objet> listeObjet = new ArrayList();
		listeObjet.add(o1);
		listeObjet.add(o2);
		listeObjet.add(o3);
		listeObjet.add(o4);
		listeObjet.add(o5);
		listeObjet.add(o6);
		listeObjet.add(o7);
		listeObjet.add(o8);
		listeObjet.add(o9);
		listeObjet.add(o10);
		ArrayList<Integer> tailleDisponible = new ArrayList();
		tailleDisponible.add(5);
		tailleDisponible.add(7);
		tailleDisponible.add(9);
		tailleDisponible.add(11);
		tailleDisponible.add(15);
		tailleDisponible.add(18);
		Probleme p = new Probleme(listeObjet, tailleDisponible, new ArrayList<Boite>(), 0);
		p.solutionSimple();
	}
	
	
	
	
}
