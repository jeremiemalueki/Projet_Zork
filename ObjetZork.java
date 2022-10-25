/**
 * <p> Cree un objet dans le jeu d'aventure Zork
 * permet de definir 2 types d'objets: transportable et non transportable
 * avec une description et un poids
 * </p>
 *
 * @invariant getDescription() != null;
 * @invariant descriptionLongue() != null;
 * @invariant getPoids() >= 0;
 * @invariant estTransportable() ==> (getPoids() > 0);
 * @author     Jeremie Malueki
 * @version    1.0
 * @since      Octobre 2018
 */

public class ObjetZork
{
	private String description;
	private int poids;
	private boolean transportable;


	/**
	 *  Initialise un objet non transportable identifie par la chaine de caracteres
	 *  specifiee et de poids nul. La chaine de caracteres specifiee doit être differente 
	 *  de null.
	 *
	 * @requires description != null;
	 * @ensures descriptionCourte() == description;
	 * @ensures getPoids() == 0;
	 * @ensures !estTransportable();
	 *
	 * @param  description  La description de cet objet
	 * @throws NullPointerException si la chaîne de caracteres specifiee est null.
	 */
	public ObjetZork(String description)
	{
		if (description == null)
		{
	        throw new NullPointerException("La description doit être non nulle");
        }
		this.description=description;
		this.transportable=false;
		this.poids=0;
	}


	/**
	 *  Initialise un objet transportable identifie par la chaine de caracteres
	 *  specifiee et dont le poids est l'entier specifie. La chaine de caracteres
	 *  specifiee doit être differente de null et le poids doit etre strictement 
	 *  superieur a 0.
	 *
	 * @requires description != null;
	 * @requires poids > 0;
	 * @ensures getDescription() == description;
	 * @ensures getPoids() == poids;
	 * @ensures estTransportable();
	 *
	 * @param  description  La description de cet objet
	 * @param  poids        Le poids de l'objet
	 * @throws NullPointerException si la chaîne de caracteres specifiee est null.
	 * @throws IllegalArgumentException si le poids specifie est stictement negatif
	 */
	public ObjetZork(String description,int poids)
	{
		this(description);
		if(poids<=0){
			throw new IllegalArgumentException("Le poids doit etre strictement positif");
		}
		this.poids=poids;
		this.transportable=true;
	}


	/**
	 *Renvoie le poids de l'objet Zork
	 *
	 *le poids doit etre positif
	 *@return le poids de cet ObjetZork
	 *@pure
	 */
	public int getPoids()
	{
		return (poids);
	}

	/**
	 *Renvoie la description de l'objet Zork
	 *
	 *@return la description de cet ObjetZork
	 *@pure
	 */
	public String getDescription()
	{
		return (description);
	}

	/**
	 *  Renvoie une description de cet objet contenant l'ensemble de ses
	 *  caracteristiques. Cette description est destinee a fournir une description
	 *  complete de cet objet formatee pour un affichage presente a l'utilisateur.
	 *
	 * @return    Renvoie une description de cet objet contenant l'ensemble de ses
	 *      caracteristiques
	 *
	 * @pure
	 */
	public String descriptionLongue()
	{
		String descriptionLongue = description + "(";
		if (transportable) {
			descriptionLongue += poids + "kgs";
		} else {
			descriptionLongue += "non transportable";
		}
		return descriptionLongue + ")";
	}

	/**
	 *Indique si l'objetZork est transportable.
	 *renvoie "true" si l'objet est transportable
	 *renvoie "false" si l'objet n'est pas transportable
	 *
	 *@return true ou false
	 *@pure
	 */
	public boolean estTransportable()
	{
		return this.transportable;
	}

    /**
	 * Methode booleene equals pour les objetZork
     *renvoie true si l'objetZork est egal a l'obet en argument
	 *renvoie false sinon
	 * @param o objet
	 * @return true ou false
	 */
    public boolean equals (Object o)
    {
		if(!(o instanceof ObjetZork)){
			return false;
		}
		ObjetZork oz=(ObjetZork) o;
		if(getPoids()!=oz.getPoids()){
			return false;
		}
		if (estTransportable()!=oz.estTransportable()) {
			return false;
		}
		if (getDescription()!=oz.getDescription()) {
			return false;
		}
		return true;
	}

	/**
	 *  Renvoie un code de hashage pour cet ObjetZork
	 *
	 * @return    un code de hashage pour cet ObjetZork
	 */
	public int hashCode() {
		if (estTransportable()) {
			return poids + 31 * description.hashCode();
		}
		return description.hashCode() + 31 * poids;
	}

	/**
	 *  Renvoie une description succincte sous forme de chaîne de caracteres
	 *  de cet ObjetZork.
	 *
	 * @return    Une description succincte de cet ObjetZork
	 */	
	public String toString()
	{
		if(this.transportable==true)
		{
			return "Objet: "+this.description+" Poids: "+this.poids;
		}
		else
		{
			return "Objet: "+this.description+" Poids: non transportable";
		}
	}
}