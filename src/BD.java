
public class BD extends Document {

	public BD(String ISBN, String EAN, String titre, String editeur, String datePublication, Serie serie, Auteur auteur,
			TypeDocument type, int nbTotalExemplaire) {
		super(ISBN, EAN, titre, editeur, datePublication, serie, auteur, type, nbTotalExemplaire);
		
	}

}
