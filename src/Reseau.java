import java.util.ArrayList;

public class Reseau {
	String nom;
	ArrayList<Bibliotheque> bibliotheques;

	public Reseau(String nom) {
		this.nom=nom; 
		this.bibliotheques=new ArrayList<Bibliotheque>();
	}

	public Boolean ajouterBibliotheque (String  nom) {

		Bibliotheque b = new Bibliotheque(nom);
		for(int i=0; i<this.bibliotheques.size(); i++) {
			if (this.bibliotheques.get(i).nom.contentEquals(b.nom) )
				return false;
		}
		this.bibliotheques.add(b);
		return true;
	}

	public Boolean ajouter (String ISBN, String EAN, String titre, String editeur, String datePublication, String nomAuteur, String prenomAuteur, TypeDocument type, int nbTotalExemplaire, int[] nbExemplaire) {
		/** Vérifier si le document existe dans le réseau*/
		Auteur auteur= new Auteur(nomAuteur, prenomAuteur);
		Document doc=new Document(ISBN, EAN, titre, editeur, datePublication, auteur, type, nbTotalExemplaire);
		for(int i=0; i<this.bibliotheques.size(); i++) {
			for (int j =0; j<this.bibliotheques.get(i).documentsEx.size(); j++) {
				if (this.bibliotheques.get(i).documentsEx.get(j).document.ISBN.equals(doc.ISBN) && this.bibliotheques.get(i).documentsEx.get(j).document.EAN.equals(doc.EAN))
					return false;
			}
		}

		/** Ajouter le document dans le réseau*/
		for(int i=0; i<this.bibliotheques.size(); i++) {
			this.bibliotheques.get(i).ajouter(doc,nbExemplaire[i] );
		}

		return true;
	}


	public String consulter() {
		String res="Liste des documents disponibles dans "+ nom;
		if (this.bibliotheques.size()>0) {
			System.out.println(bibliotheques.get(0).documentsEx.size());
			for (int i=0; i<bibliotheques.get(0).documentsEx.size(); i++) {
				res=res+bibliotheques.get(0).documentsEx.get(i).toString();
				for(int j=1; j<this.bibliotheques.size(); j++) {
					res=res+";"+bibliotheques.get(j).documentsEx.get(i).nbExemplaire ;
				}
			}
		}
		System.out.println(res);
		return res;
	}


}
