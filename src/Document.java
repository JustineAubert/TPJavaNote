import java.sql.Date;

public class Document {

	 String ISBN ; //(10 ou 13 chiffres)
	 String EAN; // (8 ou 10 chiffres)
	 String titre; 
	private String editeur;
	 String datePublication;
	 Serie serie; 
	Auteur auteur;
	 TypeDocument type;
	private int nbTotalExemplaire;

	public Document(String ISBN, String EAN, String titre, String editeur, String datePublication,  Serie serie, Auteur auteur, TypeDocument type, int nbTotalExemplaire) {
		this.ISBN=ISBN;
		this.EAN=EAN; 
		this.titre=titre; 
		this.editeur=editeur; 
		this.datePublication=datePublication;
		this.serie=serie; 
		this.auteur=auteur;
		this.type=type;
		this.nbTotalExemplaire=nbTotalExemplaire;
		
	}
	
	public Document(String ISBN, String EAN, String titre, String editeur, String datePublication, Auteur auteur, TypeDocument type, int nbTotalExemplaire) {
		this.ISBN=ISBN;
		this.EAN=EAN; 
		this.titre=titre; 
		this.editeur=editeur; 
		this.datePublication=datePublication;
		this.auteur=auteur;
		this.type=type;
		this.nbTotalExemplaire=nbTotalExemplaire;
		
	}

	@Override
	public String toString() {
	
		String res="";
		res=res+ this.ISBN + ";" +
				this.EAN + ";" +
				this.titre + ";" +
				this.editeur + ";" +
				this.datePublication + ";" ; 
		if (serie != null)
				res=res+this.serie.toString() + ";" ;//a faire
		
		res=res+this.auteur.toString() + ";" + //a faire
				this.type.name() + ";" +
				this.nbTotalExemplaire;
		
		return res;

	}

}
