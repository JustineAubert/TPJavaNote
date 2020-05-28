import java.sql.Date;

public class Auteur {
	String nom; 
	String prenom;

	
	public Auteur (String nom, String prenom) {
		this.nom=nom; 
		this.prenom=prenom;
	}
	
	@Override
	public String toString() {
	
		String res="";
		res=res+";"+ this.prenom+ ";"+this.nom;
		return res;
	}
}
