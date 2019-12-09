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
import java.util.Iterator;
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
	
	public int calculResiduDepuisSolutionActuelle(ArrayList<Boite> solutionActuelle) {
		int res = 0;
		
		for(Boite b : solutionActuelle) {
			res = res + b.getRésidu();
		}
		
		return res;
	}
	
	
	//Test si 2 objets correspondent a une taille de sac , les ajoutes si oui
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
	/**Test si il y a une boite dont le résidus peut etre completer avec un objet**/
	
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
							if(e.verifCouleur(e.getListeObjet().get(0))){
							if(t == this.listeObjet.get(i).getPoids()+this.listeObjet.get(j).getPoids()+ e.getListeObjet().get(0).getPoids()) {
								Boite b = new Boite(t);
								b.addObjet(this.listeObjet.get(i));
								b.addObjet(this.listeObjet.get(j));
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
						// Supprimer la boite b de la solution et la remplacer par la nouvelle boite
						Boite bo = new Boite(t);
						b.getListeObjet().size();
						for(Objet o1 : b.getListeObjet()) {
							if(bo.verifCouleur(o1)) {
								bo.addObjet(o1);
								System.out.println("OOOO");
							}
													}
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
	 * Solution simple, pas optimale (ce n'est pas une heuristique)
	 */
	public void solutionSimple(){
		for(int i=0; i<this.listeObjet.size(); i++) {

			this.trouvePlusPetiteBoite(this.listeObjet.get(i));
			this.combinaisonSac();

			
			this.listeObjet.remove(i);
			
			i--;
		}

	}
	
	
	/**Lecture du fichier , on recupère les données nécessaire
	 * au traitement du probleme
	 * 
	 * 	 */
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
	
	/**
	 * retourne une liste contenant les pires boites d'une solution, 
	 * c'est à dire les boites avec le résidu le plus élevé
	 */
	public ArrayList<Boite> getPiresBoites(ArrayList<Boite> solution) {
		ArrayList<Boite> piresBoites = new ArrayList<Boite>();
		int max ;
		if(solution.isEmpty()) 	max = -1;
		else {
			max = solution.get(0).getRésidu();
			for(Boite b : solution) {
				if(b.getRésidu() > max) {
					piresBoites.removeAll(piresBoites);
					piresBoites.add(b);
					max = b.getRésidu();
				}
				else if (b.getRésidu() == max) {
					piresBoites.add(b);
				}
				else {
					
				}
			}
		}
		return piresBoites;
	}
	
	/**
	 * Essaye d'inserer l'objet dans une boite existante aléatoire de la solution actuelle
	 * Si ce n'est pas possible alors créer une boite, la plus petite possible en fonction du poids de l'objet
	 * ajoute cet objet à la boite puis la boite à la solution actuelle
	 * Fait tout cela pour chaque objet de la liste stock
	 * @param solutionActuelle
	 * @param o
	 */
	public ArrayList<Boite> insertionDansUneBoiteDejaCreer(ArrayList<Boite> solutionActuelle, ArrayList<Objet> stock) {
		ArrayList<Boite> temporaire = new ArrayList<Boite>();
		temporaire.addAll(solutionActuelle);
		for(Objet o : stock) {
		boolean verif = false;
		for(int i = 1; i<100; i++) {
			int  n= (int)(Math.random() * temporaire.size());
			if(temporaire.get(n).verifCouleur(o) && (temporaire.get(n).getRésidu() >= o.getPoids())) {
				temporaire.get(n).addObjet(o);
					verif = true;
					break;
			}
		}
		if(verif = false) {
			int min = this.tailleDisponible.get(0);
			for(int tailleDispo : this.tailleDisponible) {
				if(tailleDispo > o.getPoids() && tailleDispo < min) {
					min = tailleDispo;
				}
			}
			Boite b = new Boite(min);
			b.addObjet(o);
			temporaire.add(b);
		}
		
		}
		return temporaire;
		
	}
	
	/**
	 * Construit la liste des voisins d'une solution initiale à l'aide d'une liste de ses pires boites
	 * @param solutionActuelle
	 * @param piresBoites
	 * @return
	 */
	public ArrayList<ArrayList<Boite>> voisins(ArrayList<Boite> solutionActuelle, ArrayList<Boite> piresBoites) {
		ArrayList<Objet> stock = new ArrayList<Objet>();
		ArrayList<ArrayList<Boite>> voisins = new ArrayList<ArrayList<Boite>>();
		ArrayList<Boite> temporaire = new ArrayList<Boite>();
		temporaire.addAll(solutionActuelle);
		for(Boite mauvaiseBoite : piresBoites) {
			//for(Boite solution : temporaire) {
			Iterator i = temporaire.iterator();
			Boite b = new Boite(); 
			while(i.hasNext()) {
				b = (Boite) i.next();
				if(b.getId() == mauvaiseBoite.getId()) {
				//	for(Objet objet : b.getListeObjet()) {
					Iterator j = b.getListeObjet().iterator();
					Objet obj = new Objet();
					while(j.hasNext()) {
						obj = (Objet) j.next();
						stock.add(obj);
						j.remove();
					}
					i.remove();;
				}
			}
		}
		voisins.add(insertionDansUneBoiteDejaCreer(temporaire, stock));
		voisins.add(insertionDansUneBoiteDejaCreer(temporaire, stock));
		
		return voisins;
		
	}
	
	/**
	 * Recupère les plus mauvaise boites (plus gros résidu) de la solution actuelle
	 * Puis récupère les voisins de la solution actuelle en fonction de ces mauvaises boites
	 * @param solutionActuelle
	 * @return
	 */
	public ArrayList<ArrayList<Boite>> getVoisins(ArrayList<Boite> solutionActuelle) {
		
		ArrayList<ArrayList<Boite>> voisins = new ArrayList<ArrayList<Boite>>();
		ArrayList<Boite> piresBoites = getPiresBoites(solutionActuelle);
		
		voisins.addAll(voisins(solutionActuelle, piresBoites));
		
		
		return voisins;
	}



	public ArrayList<Boite> getSolution() {
		return solution;
	}
	
	
	
	public ArrayList<Boite> TabuSearch(ArrayList<Boite> solutionInitiale) {
		
		ArrayList<Boite> meilleurSolution = new ArrayList<Boite>();
		meilleurSolution.addAll(solutionInitiale);
		
		ArrayList<Boite> solutionActuelle = new ArrayList<Boite>();
		solutionActuelle.addAll(solutionInitiale);
		
		ArrayList<Boite> tabuList = new ArrayList<Boite>();
		
		ArrayList<Boite> meilleurVoisin = new ArrayList<Boite>();
		
		int iterationCounter = 0;
		int min = 0;
		int cpt = 0;
		//On prédéfinit un nombre d'itération
		while(iterationCounter < 20) {
			
			//Récupère une liste de voisin de la solution actuelle
			ArrayList<ArrayList<Boite>> voisins = getVoisins(solutionActuelle);
			
			
			int temp = calculResiduDepuisSolutionActuelle(solutionActuelle);
			System.out.println("Residu de la solution actuelle : " + temp);
			for(ArrayList<Boite> voisin : voisins) {
				System.out.println("Residu du voisin n°" + cpt + " : " + calculResiduDepuisSolutionActuelle(voisin));
				cpt++;
			}
			
			//Récupère le meilleur voisin, c'est à dire celui avec le plus petit residu
			min = calculResiduDepuisSolutionActuelle(voisins.get(0));
			meilleurVoisin.addAll(voisins.get(0));
			for(ArrayList<Boite> voisin : voisins) {
				if(min > calculResiduDepuisSolutionActuelle(voisin)  && (!tabuList.containsAll(voisin))) {
					System.out.println("Test debug condition meilleur voisin ");
					min = calculResiduDepuisSolutionActuelle(voisin);
					meilleurVoisin.removeAll(meilleurVoisin);
					meilleurVoisin.addAll(voisin);							
				}
			}
			
			if(calculResiduDepuisSolutionActuelle(meilleurVoisin) < calculResiduDepuisSolutionActuelle(meilleurSolution)) {
				meilleurSolution.removeAll(meilleurSolution);
				meilleurSolution.addAll(meilleurVoisin);
			}
			
			tabuList.addAll(solutionActuelle);
			solutionActuelle.removeAll(solutionActuelle);
			solutionActuelle.addAll(meilleurVoisin);
			
			iterationCounter++;
			
		}
		
		return meilleurSolution;
	}



	public static void main(String args[]) throws NumberFormatException, IOException {

		ArrayList<Probleme> listeP = new ArrayList<Probleme>();
	
		/** Test sur tout les benchs **/ 	
	//	for(int i = 2;i<=20;i++) {
	//		for(int j=0;j<=4;j++) {	

				Probleme p1 = new Probleme();
				p1.recupererFichier(8,2);
				p1.solutionSimple();
				p1.calculRésidu();
				System.out.println("Test debug " + p1.getRésiduTotal());
				for(Boite b: p1.getSolution()) {
					System.out.println(b);
				}
				int essaie = p1.getRésiduTotal();
				System.out.println("Fin test debug " + essaie);
				ArrayList<Boite> resultat = new ArrayList<Boite>();
				resultat = p1.TabuSearch(p1.getSolution());
				System.out.println("Test");
				for(Boite solution : resultat) {
					System.out.println(solution);
				}
				int residuFinal = p1.calculResiduDepuisSolutionActuelle(resultat);
				System.out.println("Resultat final : " + residuFinal);
			/*	p1.calculRésidu();
				listeP.add(p1);
				for(Boite solution : p1.getSolution()) {
					System.out.println(solution);
				}
				System.out.println("Résidu final : " + p1.calculResiduDepuisSolutionActuelle(p1.getSolution()));*/
				/*
				ArrayList<ArrayList<Boite>> voisins = new ArrayList<ArrayList<Boite>>();
				voisins = p1.getVoisins(p1.getSolution());
				for(ArrayList<Boite> newSolutions : voisins) {
					System.out.println("\n Voisin :");
					for(Boite voisin : newSolutions) {
						System.out.println(voisin);
					}
				}*/
				
	//		}
	//	}
		
		/**TODO : essayer d'implementer une heuristique afin de traiter le problème 
		 * plus efficacement
		 */
		
		/**Test sur un seul bench **/
	/*
		Probleme p1 = new Probleme();	
		p1.recupererFichier(2,0);
		p1.solutionSimple();
		p1.calculRésidu();
		listeP.add(p1);*/
		
		
	/*	int resmoy = 0;
		int cpt = 0;
		int nbmoyBoite = 0;
		for(Probleme p : listeP) {
			cpt++;
			resmoy = resmoy + p.getRésiduTotal() ;
			nbmoyBoite = nbmoyBoite + p.getSolution().size();
		}
		System.out.println("Le residu moyen est de : "+ resmoy / cpt +  "et le nombre de boite" + nbmoyBoite/cpt);*/

	}
}
