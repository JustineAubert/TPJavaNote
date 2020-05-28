
public class DocumentEx implements Comparable<DocumentEx> {
	Document document; 
	int nbExemplaire;

	public DocumentEx(Document document, int nbExemplaire ) {
		this.document=document; 
		this.nbExemplaire=nbExemplaire;
	}

	@Override
	public String toString() {
		String res="";
		res=res+"\n"+this.document.toString() + ";" + this.nbExemplaire;

		return res;
	}
	
	/** Pour pouvoir trier les document par date de publication*/
	@Override
	public int compareTo(DocumentEx o) {
		return this.document.datePublication.compareTo(o.document.datePublication);
	}

}