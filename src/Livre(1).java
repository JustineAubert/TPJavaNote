public class Livre extends Document {

	public Livre(String ISBN, String EAN, String titre, String editeur, String datePublication, Auteur auteur,
			TypeDocument type, int nbTotalExemplaire) {
		super(ISBN, EAN, titre, editeur, datePublication, auteur, type, nbTotalExemplaire);
	
	}


}
