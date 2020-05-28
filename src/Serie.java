import java.util.ArrayList;

public class Serie {
	int numero; 
	 String titre; 
	private ArrayList<Document> documents;
	
	public Serie(int numero, String titre) {
		this.titre=titre; 
		this.numero=numero; 
		this.documents=new ArrayList<Document>();
	}
	
	@Override
	public String toString() {
	
		String res="";
		res=res+";"+ this.titre+ ";"+this.numero;
		return res;
	}

}
