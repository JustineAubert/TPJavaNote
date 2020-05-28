import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Bibliotheque {
	String nom;
	ArrayList<DocumentEx> documentsEx;
	ArrayList<Utilisateur> utilisateurs; 

	public Bibliotheque(String nom) {
		this.nom=nom; 
		this.documentsEx=new ArrayList<DocumentEx>();
		this.utilisateurs=new ArrayList<Utilisateur>();
	}

	/**Consulter l’ensemble des documents de la bibliotheque */

	public String consulter() {
		String res="Liste des documents disponibles dans "+ nom;
		res=res+this.documentsEx.toString();
		System.out.println(res);
		return res;
	}


	/**pour une série, les documents triés par date de publication */ 

	public String consulterSerie(String titre) {
		String res="Liste des documents de la serie : "+ titre;
		ArrayList<DocumentEx> docs = new ArrayList<DocumentEx>();
		for (int i=0; i<documentsEx.size(); i++) {
			if (documentsEx.get(i).document.serie != null){
				if (documentsEx.get(i).document.serie.titre.equalsIgnoreCase(titre) )
					docs.add(documentsEx.get(i));
			}
		}
		Collections.sort(docs);

		res=res+"\n"+docs.toString();	

		System.out.println(res);

		return res;
	}



	/**les documents d’un même auteur (par nom, prénom ou les deux)*/ 
	public String consulterNomAuteur(String str) {
		String res=new String("Liste des documents des auteurs ayant le nom "+ str + " de la bibliotheque " + this.nom);

		for (int i=0; i<documentsEx.size(); i++) {
			if (this.documentsEx.get(i).document.auteur.nom.equalsIgnoreCase(str) 
					|| this.documentsEx.get(i).document.auteur.prenom.equalsIgnoreCase(str)
					||(this.documentsEx.get(i).document.auteur.prenom + " "+ this.documentsEx.get(i).document.auteur.nom).equalsIgnoreCase(str)) {
				res=res+"\n"+this.documentsEx.get(i).toString();	
			}
		}
		System.out.println(res);
		return res;
	}


	/**la recherche d’un livre par son ISBN */

	public String consulterISBN(String ISBN) {
		String res="Liste des documents avec ISBN = "+ ISBN;

		for (int i=0; i<documentsEx.size(); i++) {
			if (this.documentsEx.get(i).document.ISBN.equalsIgnoreCase(ISBN))
				res=res+"\n"+this.documentsEx.get(i).toString();		
		}
		System.out.println(res);
		return res;
	}


	/**La recherche d’un document par son EAN */
	public String consulterEAN(String EAN) {
		String res="Liste des documents avec EAN = "+ EAN;

		for (int i=0; i<documentsEx.size(); i++) {
			if (this.documentsEx.get(i).document.EAN.equalsIgnoreCase(EAN))
				res=res+"\n"+this.documentsEx.get(i).toString();		
		}
		System.out.println(res);
		return res;
	}

	/**Le nombre de documents de chaque type publiés dans un intervalle de 	temps*/
	public String consulterIntervalle(String date) {
		String res="Nombre de documents publié en  = "+ date + "\n";

		int[] nb = {0,0,0,0,0,0};
		String[] types = {"LIVRE", "CD", "DVD", "JEUX", "BD", "AUTRE"};

		for (int j=0; j<6; j++) {
			for (int i=0; i<documentsEx.size(); i++) {
				if (this.documentsEx.get(i).document.type.name().equalsIgnoreCase(types[j]) && this.documentsEx.get(i).document.datePublication.equals(date))
					nb[j]++;		
			}
		}

		for (int i=0; i<6; i++) {
			res=res+types[i] + " : " + nb[i]+"\n";
		}
		System.out.println(res);
		return res;
	}

	/**Ajouter un document dans 1 bibliotheque en precisant le nombre d'exemplaires*/
	public void ajouter(Document doc,int nbExemplaire ) {
		DocumentEx d=new DocumentEx(doc, nbExemplaire);
		this.documentsEx.add(d);
	}

	/**Ajouter un nouvel utilisateur à 1 bibliotheque en précisant le quota d'emprunt*/
	public void ajouterUtilisateur(int num, String nom, String prenom, int quota) {
		Utilisateur u=new Utilisateur(num, nom, prenom, quota);
		this.utilisateurs.add(u);
	}

	public void emprunter(int num, String ISBN, String EAN) {
		int x;
		boolean inscrit=false; 
		for(int i=0; i<this.utilisateurs.size(); i++) {
			if(this.utilisateurs.get(i).num==num ) {
				inscrit =true;
				if ( this.utilisateurs.get(i).quota>0) {
					x=this.peutEtreEmprunte(ISBN, EAN);
					if(x>=0) {
						this.documentsEx.get(x).nbExemplaire--;
						this.utilisateurs.get(i).quota--;
						System.out.println("Document emprunté");
					}else System.out.println("Livre indisponible");
				} else System.out.println("Le quota est dépassé");
			}
		}
		if (inscrit==false)  System.out.println("Cet utilisateur n'est pas inscrit à la bibliotheque");
	}

	public void rendre(int num, String ISBN, String EAN) {
		int x;
		boolean inscrit=false; 
		for(int i=0; i<this.utilisateurs.size(); i++) {
			if(this.utilisateurs.get(i).num==num ) {
				inscrit =true;
				x=this.positionDoc(ISBN, EAN);
				if(x>=0) {
					this.documentsEx.get(x).nbExemplaire++;
					this.utilisateurs.get(i).quota++;
					System.out.println("Document rendu");
				}else System.out.println("Livre indisponible");

			}
		}
		if (inscrit==false)  System.out.println("Cet utilisateur n'est pas inscrit à la bibliotheque");
	}


	public int positionDoc(String ISBN, String EAN) {
		for(int i=0; i<this.documentsEx.size(); i++) {
			if (this.documentsEx.get(i).document.ISBN.equalsIgnoreCase(ISBN) && this.documentsEx.get(i).document.EAN.equals(EAN) && this.documentsEx.get(i).nbExemplaire>0)
				return i;
		}
		return -1;
	}

	public int peutEtreEmprunte(String ISBN, String EAN) {

		int i= positionDoc(ISBN, EAN);
		if (i>=0 ) {
			if(documentsEx.get(i).nbExemplaire>0)
				return i;
		}
		return -1;
	}

}
