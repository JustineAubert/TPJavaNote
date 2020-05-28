import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
	static Reseau reseau;

	public static void getDataFromCSVFile(String csvFilePath)
	{
		String line = "";
		String[] data = null;
		String separator = ";(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

		//Document data
		String isbn;
		String ean;
		String title;
		String publisher;
		String date;
		String seriesTitle;
		Integer seriesNumber;
		String authorName;
		String authorSurname;
		String type;
		int totalCopies;
		int numberCopyAimeCesaire;
		int numberCopyEdmondRostand;
		int numberCopyJeanPierreMelville;
		int numberCopyOscarWilde;
		int numberCopySaintSimon;

		reseau=new Reseau("Reseau de Paris");

		Bibliotheque bibliotheque= new Bibliotheque("AimeCesaire"); 
		reseau.bibliotheques.add(bibliotheque);  

		bibliotheque= new Bibliotheque("EdmondRostand"); 
		reseau.bibliotheques.add(bibliotheque);  

		bibliotheque= new Bibliotheque("JeanPierreMelville"); 
		reseau.bibliotheques.add(bibliotheque);  

		bibliotheque= new Bibliotheque("OscarWilde"); 
		reseau.bibliotheques.add(bibliotheque);  

		bibliotheque= new Bibliotheque("SaintSimon"); 
		reseau.bibliotheques.add(bibliotheque);  



		try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(csvFilePath), StandardCharsets.ISO_8859_1)) 
		{
			//Read the first line
			line = bufferedReader.readLine();

			//Get data from the line
			data = line.split(separator, -1);

			System.out.println (data.length);


			if(data.length != 16)
			{
				System.out.println("[FileReader] The file at " + csvFilePath + " does not contain the right number of columns.");
				return;
			}

			int i = 1;

			//Read the file line by line
			while ((line = bufferedReader.readLine()) != null)
			{
				//Get data from the line
				data = line.split(separator, -1);

				//Sort data

				//Get the ISBN number
				isbn = data[0];

				//Get the EAN number
				ean = data[1];

				//Get the title of the document
				title = data[2];

				//Get the name of the publisher
				publisher = data[3];

				//Get the publication date
				try
				{
					int dateInt = Integer.parseInt(data[4].replaceAll("[^0-9]", ""));

					if(dateInt%10000 >= 2021 || dateInt%10000 < 0)
					{
						date = "?";
					}
					else if(dateInt/10000 == 0)
					{
						date = Integer.toString(dateInt%10000);
					}
					else
					{
						date = dateInt%10000 + "-" + dateInt/10000;
					}
				}
				catch (Exception exception)
				{
					date = "?";
				}

				//Get the title of the series
				seriesTitle = data[5];

				//Get the number of this document in the series
				try
				{
					seriesNumber = Integer.parseInt(data[6]);
				}
				catch (Exception exception)
				{
					seriesNumber = null;
				}

				//Get the name of the author
				authorSurname = data[7];

				//Get the surname of the author
				authorName = data[8];

				//Get the type of the document
				type = data[9];

				//Get the total number of copy in Paris for this document 
				try
				{
					totalCopies = Integer.parseInt(data[10]);
				}
				catch (Exception exception)
				{
					totalCopies = 0;
				}

				//Get the number of copy in the library "Aime Cesaire"
				try
				{
					numberCopyAimeCesaire = Integer.parseInt(data[11]);
				}
				catch (Exception exception)
				{
					numberCopyAimeCesaire = 0;
				}

				//Get the number of copy in the library "Edmond Rostand"
				try
				{
					numberCopyEdmondRostand = Integer.parseInt(data[12]);
				}
				catch (Exception exception)
				{
					numberCopyEdmondRostand = 0;
				}

				//Get the number of copy in the library "Jean-Pierre Melville"
				try
				{
					numberCopyJeanPierreMelville = Integer.parseInt(data[13]);
				}
				catch (Exception exception)
				{
					numberCopyJeanPierreMelville = 0;
				}

				//Get the number of copy in the library "Oscar Wilde"
				try
				{
					numberCopyOscarWilde = Integer.parseInt(data[14]);
				}
				catch (Exception exception)
				{
					numberCopyOscarWilde = 0;
				}

				//Get the number of copy in the library "Saint-Simon"
				try
				{
					numberCopySaintSimon = Integer.parseInt(data[15]);
				}
				catch (Exception exception)
				{
					numberCopySaintSimon = 0;
				}

				//TODO Do something with data
				TypeDocument typedoc;
				Auteur auteur= new Auteur(authorName, authorSurname);
				Serie serie ;
				Document document;

				if (type.substring(0, 5).equalsIgnoreCase("Livre")) typedoc=TypeDocument.LIVRE;
				else
					if (type.substring(0, 3).equalsIgnoreCase("DVD")) typedoc=TypeDocument.DVD;
					else
						if (type.substring(0, 6).equalsIgnoreCase("Disque")) typedoc=TypeDocument.CD;
						else
							if (type.substring(0, 4).equalsIgnoreCase("Jeux")) typedoc=TypeDocument.JEUX;
							else 
								typedoc=TypeDocument.AUTRE;

				if (seriesNumber!=null || seriesTitle.length()!=0) {
					if (seriesNumber == null) seriesNumber=-1;
					serie= new Serie(seriesNumber, seriesTitle);
					document= new Document(isbn, ean, title, publisher, date, serie, auteur, typedoc, totalCopies );
				}else 
					document= new Document(isbn, ean, title, publisher, date, auteur, typedoc, totalCopies );


				reseau.bibliotheques.get(0).documentsEx.add(new DocumentEx(document, numberCopyAimeCesaire));

				reseau.bibliotheques.get(1).documentsEx.add(new DocumentEx(document, numberCopyEdmondRostand));

				reseau.bibliotheques.get(2).documentsEx.add(new DocumentEx(document, numberCopyJeanPierreMelville));

				reseau.bibliotheques.get(3).documentsEx.add(new DocumentEx(document, numberCopyOscarWilde));

				reseau.bibliotheques.get(4).documentsEx.add(new DocumentEx(document, numberCopySaintSimon));

			}
		} 
		catch (IOException exception) 
		{
			System.err.println(exception);
		}
	}


	public static void main(String[] args) throws IOException {

		getDataFromCSVFile("data\\data.csv");
		/**Menu pour choisir la fonctionnalité*/

		int choix = 0;
		String sousChoix;
		Scanner input ;
		Console c = System.console();
		int res; // utiliser dans le menu pour indiquer le numero à saisir pour le reseau 
		boolean arret = false;
		while (!arret){
			System.out.println("|========================================================|");
			System.out.println("|                          Menu                          |");
			System.out.println("|========================================================|");
			System.out.println("1: Ajouter une nouvelle bibliothèque dans le réseau");
			System.out.println("2: Ajouter un nouveau document dans le réseau");
			System.out.println("3: Ajouter un nouvel utilisateur");
			System.out.println("4: Afficher l'ensemble des documents");
			System.out.println("5: Afficher les documents d'une série");
			System.out.println("6: Afficher les documents d'un auteur");
			System.out.println("7: Rechercher document par ISBN");
			System.out.println("8: Rechercher document par EAN");
			System.out.println("9: Afficher nombre de document pour une période");
			System.out.println("10: Emprunter un livre");
			System.out.println("11: Rendre un livre");
			System.out.println("0: quitter");

			System.out.print("\n \n Merci de choisir une action: ");
			input = new Scanner(System.in);
			choix = input.nextInt();

			switch(choix) {
			case 1 :
				System.out.print("Ajouter une bibliotheque dans le réseau  ");
				System.out.print("Merci de choisir le nom de la bibliothèque: ");
				Scanner biblio = new Scanner(System.in);
				String nomBiblio = biblio.nextLine();
				boolean succes=reseau.ajouterBibliotheque(nomBiblio); 
				if (succes) System.out.println("La bibliotheque "+nomBiblio+ "a été ajoutée au réseau");
				break;


			case 2 :
				System.out.print("Ajouter un livre dans le réseau  ");


				System.out.print("ISBN: ");
				Scanner SISBN = new Scanner(System.in);
				String ISBN = SISBN.nextLine();

				System.out.print("EAN: ");
				Scanner SEAN = new Scanner(System.in);
				String EAN = SEAN.nextLine();

				System.out.print("Titre: ");
				Scanner Stitre = new Scanner(System.in);
				String titre = SISBN.nextLine();

				System.out.print("Editeur: ");
				Scanner SEditeur = new Scanner(System.in);
				String editeur = SEditeur.nextLine();

				System.out.print("Date de publication: ");
				Scanner Sdate = new Scanner(System.in);
				String datePublication = Sdate.nextLine();

				System.out.print("Nom auteur: ");
				Scanner Snom = new Scanner(System.in);
				String nomAuteur = Snom.nextLine();


				System.out.print("Prénom auteur: ");
				Scanner Sprenom = new Scanner(System.in);
				String prnomAuteur = Sprenom.nextLine();

				//String ISBN, String EAN, String titre, String editeur, String datePublication, String nomAuteur, String prenomAuteur, TypeDocument type, int nbTotalExemplaire, int[] nbExemplaire
				System.out.println("Type de document: ");
				System.out.println("0:  LIVRE");
				System.out.println("1:  CD");
				System.out.println("2:  DVD");
				System.out.println("3:  JEUX");
				System.out.println("4:  BD");
				System.out.println("5:  Autre");


				System.out.print("Type du document: ");
				Scanner Stype = new Scanner(System.in);
				int t = Stype.nextInt();
				TypeDocument type= TypeDocument.values()[t];
				System.out.print("--------"+type.name() );

				System.out.print("Nombre d'exemplaires total: ");
				Scanner SnbEx = new Scanner(System.in);
				int nbTotalExemplaire = Sprenom.nextInt();

				System.out.println("Nombre d'exemplaire dans les bibliotheques du réseau: ");
				int[] nbExemplaire = new int[reseau.bibliotheques.size()];
				for(int i=0; i<reseau.bibliotheques.size(); i++) {
					System.out.print( reseau.bibliotheques.get(i).nom + " :");
					Scanner Snb = new Scanner(System.in);
					int nb = Snb.nextInt();

					nbExemplaire[i]=nb;
				}

				reseau.ajouter(ISBN, EAN, titre, editeur, datePublication, nomAuteur, prnomAuteur, type, nbTotalExemplaire, nbExemplaire);
				System.out.println("Appuyer sur une touche pour continuer . . . ");
				input.nextLine();
				break;

			case 3 :  
				System.out.println("Ajouter un nouvel utilisateur à une bibliotheque: ");
				for(int i=0; i<reseau.bibliotheques.size(); i++) {
					System.out.println("        "+i+" : " + reseau.bibliotheques.get(i).nom);

				}
				System.out.print("\n Merci de choisir une bibliotheque: ");
				input = new Scanner(System.in);
				int b = input.nextInt();

				System.out.println("\n Merci de saisir les informations de l'utilisateur: ");

				System.out.print("Numéro de la carte bibliothèque: ");
				Scanner SnumUtil = new Scanner(System.in);
				int  num = SnumUtil.nextInt();
				
				System.out.print("Nom: ");
				Scanner SnomUtil = new Scanner(System.in);
				String nom = SnomUtil.nextLine();

				System.out.print("Prénom: ");
				Scanner SprenomUtil = new Scanner(System.in);
				String prenom = SprenomUtil.nextLine();

				System.out.print("Quota: ");
				Scanner Squota = new Scanner(System.in);
				int quota = Squota.nextInt();

				reseau.bibliotheques.get(b).ajouterUtilisateur(num, nom, prenom, quota);
				System.out.println("Appuyer sur une touche pour continuer . . . ");
				input.nextLine();

				break;

			case 4 : 
				res=0;
				System.out.println("Merci de choisir la bibliothèque pour afficher ses documents: ");
				for(int i=0; i<reseau.bibliotheques.size(); i++) {
					System.out.println("        "+i+" : " + reseau.bibliotheques.get(i).nom);
					res=res+1;
				}
				System.out.println("        "+res+" : " + "Tout le réseau");

				System.out.print("\n \n Merci de choisir une bibliotheque: ");
				input = new Scanner(System.in);
				choix = input.nextInt();
				
				if (choix<reseau.bibliotheques.size())
					reseau.bibliotheques.get(choix).consulter();
				if (choix==reseau.bibliotheques.size())
					reseau.consulter();
				System.out.println("Appuyer sur une touche pour continuer . . . ");
				input.nextLine();
				break;

			case 5 :  
				res=0;
				System.out.println("Afficher, pour une série, les documents triés par date de publication");
				for(int i=0; i<reseau.bibliotheques.size(); i++) {
					System.out.println("        "+i+" : " + reseau.bibliotheques.get(i).nom);
					res++;
				}
				//System.out.println("        "+res+" : " + "Tout le réseau");
				
				System.out.print("\n Merci de choisir une bibliotheque: ");
				input = new Scanner(System.in);
				choix = input.nextInt();

				System.out.print("Le titre de la série: ");
				input = new Scanner(System.in);
				sousChoix = input.nextLine();

				reseau.bibliotheques.get(choix).consulterSerie(sousChoix);

				System.out.println("Appuyer sur une touche pour continuer . . . ");
				input.nextLine();


				break;

			case 6 :  
				res=0;
				System.out.println("Merci de choisir la bibliothèque pour afficher les documents d'un auteur: ");
				for(int i=0; i<reseau.bibliotheques.size(); i++) {
					System.out.println("        "+i+" : " + reseau.bibliotheques.get(i).nom);
					res++;
				}
				//System.out.println("        "+res+" : " + "Tout le réseau");

				System.out.print("\n Merci de choisir une bibliotheque: ");
				input = new Scanner(System.in);
				choix = input.nextInt();

				System.out.print("Nom, prenom ou 'prenom nom' de l'auteur: ");
				input = new Scanner(System.in);
				sousChoix = input.nextLine();

				reseau.bibliotheques.get(choix).consulterNomAuteur(sousChoix);

				System.out.println("Appuyer sur une touche pour continuer . . . ");
				input.nextLine();
				break;


			case 7 :  
				res=0;
				System.out.println("Merci de choisir la bibliothèque pour afficher les documents ISBN: ");
				for(int i=0; i<reseau.bibliotheques.size(); i++) {
					System.out.println("        "+i+" : " + reseau.bibliotheques.get(i).nom);
					res++;
				}
				//System.out.println("        "+res+" : " + "Tout le réseau");
				System.out.print("\n Merci de choisir une bibliotheque: ");
				input = new Scanner(System.in);
				choix = input.nextInt();

				System.out.print("ISBN: ");
				input = new Scanner(System.in);
				sousChoix = input.nextLine();

				reseau.bibliotheques.get(choix).consulterISBN(sousChoix);

				System.out.println("Appuyer sur une touche pour continuer . . . ");
				input.nextLine();
				break;

			case 8 :  
				res=0;
				System.out.println("Merci de choisir la bibliothèque pour afficher les documents EAN: ");
				for(int i=0; i<reseau.bibliotheques.size(); i++) {
					System.out.println("        "+i+" : " + reseau.bibliotheques.get(i).nom);
					res++;
				}
				//System.out.println("        "+res+" : " + "Tout le réseau");
				
				System.out.print("\n Merci de choisir une bibliotheque: ");
				input = new Scanner(System.in);
				choix = input.nextInt();

				System.out.print("EAN: ");
				input = new Scanner(System.in);
				sousChoix = input.nextLine();

				reseau.bibliotheques.get(choix).consulterEAN(sousChoix);

				System.out.println("Appuyer sur une touche pour continuer . . . ");
				input.nextLine();
				break;

			case 9 :  
				res=0;
				System.out.println("Merci de choisir la bibliothèque pour afficher les documents d'une période: ");
				for(int i=0; i<reseau.bibliotheques.size(); i++) {
					System.out.println("        "+i+" : " + reseau.bibliotheques.get(i).nom);
					res++;
				}
				//System.out.println("        "+res+" : " + "Tout le réseau");
				System.out.print("\n Merci de choisir une bibliotheque: ");
				input = new Scanner(System.in);
				choix = input.nextInt();

				System.out.print("Période : ");
				input = new Scanner(System.in);
				sousChoix = input.nextLine();

				reseau.bibliotheques.get(choix).consulterIntervalle(sousChoix);

				System.out.println("Appuyer sur une touche pour continuer . . . ");
				input.nextLine();
				break;
				
			case 10: 

				System.out.println("Merci de choisir la bibliothèque pour emprunter un livre: ");
				for(int i=0; i<reseau.bibliotheques.size(); i++) {
					System.out.println("        "+i+" : " + reseau.bibliotheques.get(i).nom);
				}
				input = new Scanner(System.in);
				choix = input.nextInt();

				System.out.print("Numéro de la carte Utilisateur: ");
				input = new Scanner(System.in);
				int numUtil  = input.nextInt();
				
				System.out.print("ISBN: ");
				input = new Scanner(System.in);
				String ISBNU = input.nextLine();
				
				System.out.print("EAN: ");
				input = new Scanner(System.in);
				String EANU = input.nextLine();

				reseau.bibliotheques.get(choix).emprunter(numUtil, ISBNU, EANU);

				System.out.println("Appuyer sur une touche pour continuer . . . ");
				input.nextLine();
				
				break; 
				
			case 11: 
				System.out.println("Merci de choisir la bibliothèque pour rendre un livre: ");
				for(int i=0; i<reseau.bibliotheques.size(); i++) {
					System.out.println("        "+i+" : " + reseau.bibliotheques.get(i).nom);
				}
				input = new Scanner(System.in);
				choix = input.nextInt();

				System.out.print("Numéro de la carte Utilisateur: ");
				input = new Scanner(System.in);
				int numU  = input.nextInt();
				
				System.out.print("ISBN: ");
				input = new Scanner(System.in);
				String ISBNL = input.nextLine();
				
				System.out.print("EAN: ");
				input = new Scanner(System.in);
				String EANL = input.nextLine();

				reseau.bibliotheques.get(choix).rendre(numU, ISBNL, EANL);

				System.out.println("Appuyer sur une touche pour continuer . . . ");
				input.nextLine();
				
				break; 

			case 0 : 
				arret = true; break;

			default : 
				System.out.println("entrez un choix entre 0 et 9"); 
				break;
			}
		}




	}

}
