package istv.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import istv.models.Boite;
import istv.models.Objet;

public class Probleme {
	
	private ArrayList<Objet> listeObjet;
	private ArrayList<Integer> tailleDisponible;
	private ArrayList<Boite> solution;
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
	
	
	
	public Probleme(){
	
		this.listeObjet = new ArrayList<Objet>();
		this.tailleDisponible = new ArrayList<Integer>();
		this.solution = new ArrayList<Boite>();

	}

	public void removeBoiteById(int id) {
		int rem = -1;
		for (int i = 0;i<solution.size();i++) {
			if(solution.get(i).getId() == id) {
				rem = i;
			}
		}
		if(rem != -1) {
		solution.remove(rem);
		}
		else {
			System.out.println("Pas de boite contenant cet id");
		}
	}
	public void SupressObjetById(int id) {
		for(int i = 0; i<this.solution.size(); i++) {
			if(this.solution.get(i).getId() == id)
				this.solution.remove(i);
		}
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
	
	
	
	public void combinaisonSac() {
		
		for(int e : this.tailleDisponible) {
			for(int i = 0;i<this.listeObjet.size();i++) {
				for(int j = 0;j<this.listeObjet.size();j++) {
				if(this.listeObjet.get(i).getId() != this.listeObjet.get(j).getId()) {
					if(this.listeObjet.get(i).getPoids() + this.listeObjet.get(j).getPoids() == e) {
						Boite b = new Boite(e);
						
						b.addObjet(this.listeObjet.get(i));
						if(i == this.listeObjet.size()-2) {
							i = i-1;
						}
						if(i == this.listeObjet.size()-1) {
							i = i-2;
						}
						b.addObjet(this.listeObjet.get(j));
						
						this.solution.add(b);
						System.out.println("Trouvé : " + this.listeObjet.get(i).getId() + " ! "+ this.listeObjet.get(j).getId());
						this.listeObjet.remove(j);
						this.listeObjet.remove(i);
						}
					}
				}
			}
		}
		
	}
	
	/**Completer residu boite**/
	
	public void combinaisonSac2() {
		int remove = 0;
		for(Boite e : this.solution) {
			for(int i = 0;i<this.listeObjet.size();i++) {
				for(int j = 0;j<this.listeObjet.size();j++) {
				if(this.listeObjet.get(i).getId() != this.listeObjet.get(j).getId()) {
					if(this.listeObjet.get(i).getPoids() + this.listeObjet.get(j).getPoids() == e.getRésidu()) {
						e.addObjet(this.listeObjet.get(i));
						e.addObjet(this.listeObjet.get(j));
						System.out.println("Boite complété : " + this.listeObjet.get(i).getId() + " ! "+ this.listeObjet.get(j).getId());
						this.listeObjet.remove(j);
						this.listeObjet.remove(i);
						}
					else{
						for(int t : this.tailleDisponible) {
							if(e.getListeObjet().size() == 1) {
							if(t == this.listeObjet.get(i).getPoids()+this.listeObjet.get(j).getPoids()+ e.getListeObjet().get(0).getPoids()) {
								Boite b = new Boite(t);
								b.addObjet(this.listeObjet.get(i));
								b.addObjet(this.listeObjet.get(j));
								//b.verifCouleur(e.getListeObjet().get(0));
								b.addObjet(e.getListeObjet().get(0));
								e.getListeObjet().remove(0);
								remove = e.getId();
									}
								}
							}
						}
					}
				}
			}
		}
		if(remove != 0) {
			this.SupressObjetById(remove);
		}
	}
	
	/**Explore les boites remplis , les compares avec les tailles disponibles, si la capacité
	  d'une boite  + un objet =  taille dispo , creation d'une nouvelle boite , suppr l'ancienne
	   et insertion des objets dans cette nouvelle**/
	  	 
	public void combinaisonSac3() {
		int idBoiteSuppr = -1;
		for(int t : tailleDisponible) {
			for(Boite b : solution) {
				for(Objet o : listeObjet){
					if(b.getCapacité() + o.getPoids() == t) {
						//TODO : Supprimer la boite b de la solution et la remplacer par la nouvelle boite
						Boite bo = new Boite(t);
						b.getListeObjet().size();
						for(Objet o1 : b.getListeObjet()) {
							bo.addObjet(o1);
							b.verifCouleur(o1);
						}
						bo.verifCouleur(o);
						bo.addObjet(o);
						idBoiteSuppr = b.getId();
					}
					
					
				}
			}
		}
		if(idBoiteSuppr != -1) {
			this.removeBoiteById(idBoiteSuppr);
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
			System.out.println("Objet n° : " + o.getId() + " a été ajouté à la boite solution : " + this.solution.get(min).getId());
		}
		else {
			Boite b = new Boite(min);
			b.addObjet(o);
			this.solution.add(b);
			
			System.out.println("Création d'une nouvelle Boite : " + b.getId() + " de capacité : " + b.getCapacité() + 
					" ajout de l'objet " + o.getId());
		}
		combinaisonSac2();


	}
	
	
	/**
	 * Solution simple, pas optimale 
	 */
	public void solutionSimple(){
		for(int i=0; i<this.listeObjet.size(); i++) {
			this.combinaisonSac();
			this.combinaisonSac2();
			this.trouvePlusPetiteBoite(this.listeObjet.get(i));
			this.combinaisonSac2();

			
			this.listeObjet.remove(i);
			
			i--;
		}

	}
	
	
	public void recupererFichier(int k,int p) {
		
		int i= 0;
		int repere = 0;
		ArrayList<Integer> listeNombres= new ArrayList<Integer>();

		/**Lecture du fichier **/
		try {
		InputStream flux=new FileInputStream("bench//bench_"+k+"_"+p); 
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
		
		for (int j = 1;j<=nombreDeBoite;j++) {
			this.tailleDisponible.add(listeNombres.get(j));
			
		}
		 repere = nombreDeBoite+1;
		 nombreDeCouleur = listeNombres.get(repere);
		 repere++;
		 this.nombreDobjet = listeNombres.get(repere);
		 repere++;
		 
		 for(i = repere;i<listeNombres.size();i++) {
			 Objet o1 = new Objet(listeNombres.get(i),listeNombres.get(i+1));
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
	
	public int getRésiduTotal() {
		return résiduTotal;
	}



	public ArrayList<Boite> getSolution() {
		return solution;
	}



	public static void main(String args[]) throws NumberFormatException, IOException {

		ArrayList<Probleme> listeP = new ArrayList<Probleme>();
	
		/** Test sur tout les benchs **/ 	
	/*	for(int i = 2;i<=20;i++) {
			for(int j=0;j<=4;j++) {	

				Probleme p1 = new Probleme();
				p1.recupererFichier(i,j);
				p1.solutionSimple();
				p1.calculRésidu();
				listeP.add(p1);
				System.out.println("Le résidu total est de "+ p1.getRésiduTotal() + "pour " + p1.getSolution().size()+" boites");
			}
		}*/
		
		/**Test sur un seul bench **/
	
		Probleme p1 = new Probleme();	
		p1.recupererFichier(2,0);
		p1.solutionSimple();
		p1.calculRésidu();
		listeP.add(p1);
		
		
		int resmoy = 0;
		int cpt = 0;
		int nbmoyBoite = 0;
		for(Probleme p : listeP) {
			cpt++;
		resmoy = resmoy + p.getRésiduTotal() ;
		nbmoyBoite = nbmoyBoite + p.getSolution().size();
		}
		System.out.println("Le residu total est de " +p1.getRésiduTotal());
		System.out.println("Le residu moyen est de : "+ resmoy / cpt +  "et le nombre de boite" + nbmoyBoite/cpt);

	}
}
