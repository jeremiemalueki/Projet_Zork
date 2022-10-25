import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 *  Une piece dans un jeu d'aventure. <p>
 *
 *  Cette classe fait partie du logiciel Zork, un jeu d'aventure simple en mode
 *  texte.</p> <p>
 *
 *  Une "Piece" represente un des lieux dans lesquels se deroule l'action du
 *  jeu. Elle est reliee a au plus quatre autres "Piece" par des sorties. Les
 *  sorties sont etiquettees "nord", "est", "sud", "ouest". Pour chaque
 *  direction, la "Piece" possede une reference sur la piece voisine ou null
 *  s'il n'y a pas de sortie dans cette direction.</p>
 *  <p>Une Piece est aussi un conteneur d'ObjetZork possedant les proprietes
 *  suivantes:
 * <ul>
 *  <li>Un nombre non borne d'ObjetZork peut être ajoute dans la Piece.</li>
 *  <li>La Piece peut contenir plusieurs exemplaires d'un même ObjetZork</li>
 *  <li>La valeur null n'est pas autorisee parmi les ObjetZork presents dans
 *  la Piece</li>
 *  <li>Les objets contenus dans la Piece ne sont pas ordonnes</li>
 *  <li>La methode equals de ObjetZork est utilisee pour toutes les operations
 *  necessitant de tester la presence d'un ObjetZork</li>
 * </ul>
 * </p>
 * @invariant !contient(null);
 * @invariant descriptionCourte() != null;
 * @invariant descriptionLongue() != null;
 * @invariant descriptionSorties() != null;
 * @invariant getNbObjets() >= 0;
 * @author     Jeremie Malueki
 * @version    1.0
 * @since      Octobre 2018
 */

public class Piece extends ArrayListConteneur{

	//Description de la piece.
	private String description;

	// memorise les sorties de cette piece.
	private HashMap<String, Piece> sorties;


	/**
	 *  Initialise une piece decrite par la chaine de caracteres specifiee.
	 *  Initialement, cette piece ne possede aucune sortie. La description fournie
	 *  est une courte phrase comme "la bibliotheque" ou "la salle de TP".
     * @requires description != null;
     * @ensures descriptionCourte() == description;
     * @ensures getNbObjets() == 0;
     * @ensures pieceSuivante("nord") == null;
     * @ensures pieceSuivante("est") == null;
     * @ensures pieceSuivante("sud") == null;
     * @ensures pieceSuivante("ouest") == null;
     *
     * @param  description  Description de la piece.
     * @throws NullPointerException si le parametre est null
     */
	public Piece(String description) 
	{
		if(description==null){
			throw new NullPointerException("La description d'une Piece ne peut etre null");
		}
		this.description = description;
		sorties = new HashMap<String,Piece>(4);
		this.contenu=new ArrayList <ObjetZork> (10);
	}

	/**
	 * Initialise une piece decrite par la chaine de caracteres specifiee
	 * et un tableau d'objet et le nombre d'objet de la piece
     * @requires description != null;
     * @requires tabObjets != null;
     * @requires nbObjets >= 0;
     * @requires tabObjets.size() >= nbObjets;
     * @ensures pieceSuivante("nord") == null;
     * @ensures pieceSuivante("est") == null;
     * @ensures pieceSuivante("sud") == null;
     * @ensures pieceSuivante("ouest") == null;
	 *@param  description  Description de la piece.
	 *@param  ObjetZork	ArrayList d'objets Zork  
	 *@param  nbObjets  entier indiquant le nombre d'objets contenu dans l'ArrayList objetZork
     *@throws NullPointerException si l'un des parametres ou l'un des nbObjets premiers
     * elements de tabObjets est null.
     *@throws IllegalArgumentException si le nombre d'objets specifie est strictement
     * superieur à la taille du tableau specifie
	 */
	public Piece (String description,ArrayList<ObjetZork> tabObjets,int nbObjets) {
		this(description);
		if (nbObjets > tabObjets.size()) {
            throw new IllegalArgumentException("Le nombre d'objets spécifié doit être inférieur à la taille du tableau");
		}
		this.contenu=new ArrayList<ObjetZork>(nbObjets);
		for(int i=0;i<nbObjets;i++)
		{
			if (tabObjets.get(i) == null) {
                throw new NullPointerException("Les " + nbObjets
                                + " premiers éléments du tableau doivent être non null");
            }
			contenu.add(tabObjets.get(i));
		}
	}


	/**
	 * Permet de savoir si on peut ajouter un objet dans le conteneur ici dans le
	 * cas d'une piece cette ajoute est toujours possible
	 * 
	 * @return true
	 */
	@Override
	public boolean ajoutEstPossible(ObjetZork oz) {
		return true;
	}

	/**
	 * Retire un ObjetZork a l'ArrayList "contenu" de la classe piece
	 * @param oz
	 * @requires oz!=null;
	 */
	public boolean retirer(ObjetZork oz) 
	{
		if(oz.estTransportable()==true)
		{
			this.contenu.remove(oz);
			contenu.size();
			return true;
		}
		return false;
		
	}

	
	/**
	 *  Definie les sorties de cette piece. A chaque direction correspond ou bien
	 *  une piece ou bien la valeur null signifiant qu'il n'y a pas de sortie dans
	 *  cette direction.
	 *
	 * @param  nord   La sortie nord
	 * @param  est    La sortie est
	 * @param  sud    La sortie sud
	 * @param  ouest  La sortie ouest
	 */
	public void setSorties(Piece nord, Piece est, Piece sud, Piece ouest) {
		if (nord != null) {
			sorties.put("nord", nord);
		}
		if (est != null) {
			sorties.put("est", est);
		}
		if (sud != null) {
			sorties.put("sud", sud);
		}
		if (ouest != null) {
			sorties.put("ouest", ouest);
		}
	}


	/**
	 *  Renvoie la description de cette piece (i.e. la description specifiee lors
	 *  de la creation de cette instance).
	 * @return    Description de cette piece
	 * @pure
	 */
	public String descriptionCourte() {
		return description;
	}


	/**
	 *  Renvoie une description de cette piece mentionant ses sorties et
	 *  directement formatee pour affichage, de la forme: <pre>
	 *  Vous etes dans la bibliotheque.
	 *  Sorties: nord ouest</pre> Cette description utilise la chaine de caracteres
	 *  renvoyee par la methode descriptionSorties pour decrire les sorties de
	 *  cette piece.
	 *
	 * @return    Description affichable de cette piece
	 * @pure
	 */
	public String descriptionLongue() {
		return "Vous etes dans " + description + ".\n" + descriptionSorties();
	}


	/**
	 *  Renvoie une description des sorties de cette piece, de la forme: <pre>
	 *  Sorties: nord ouest</pre> Cette description est utilisee dans la
	 *  description longue d'une piece.
	 *
	 * @return    Une description des sorties de cette piece.
	 * @pure
	 */
	public String descriptionSorties() {
		String resulString = "Sorties:";
		Set keys = sorties.keySet();
		for (Iterator iter = keys.iterator(); iter.hasNext(); ) {
			resulString += " " + iter.next();
		}
		return resulString;
	}


	/**
	 *  Renvoie la piece atteinte lorsque l'on se deplace a partir de cette piece
	 *  dans la direction specifiee. Si cette piece ne possede aucune sortie dans cette direction,
	 *  renvoie null.
	 *
	 * @param  direction  La direction dans laquelle on souhaite se deplacer
	 * @return            Piece atteinte lorsque l'on se deplace dans la direction
	 *      specifiee ou null.
	 */
	public Piece pieceSuivante(String direction) {
		return (Piece) sorties.get(direction);
	}

}