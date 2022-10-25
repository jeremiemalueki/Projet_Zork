
/**
 * <p> Un joueur dans un jeu d'aventure </p>
 * <p> Cette classe fait partie du jeu Zork, un jeu d'aventure simple en mode texe</p>
 * <p> Un joeueur peut transporter des objets que dans la mesure du poids total des objets transportes
 * ne depasse pas une certaine limite fixe a l'avance.Le joueur peut transporter plusieurs occurences
 * d'un meme objet (les objets sont distingues par la methode equals)
 * Un joueur est caracterise par 
 * <ul>
 * 		<li> Son nom </Li>
 * 		<li> les objets qu'il transporte </Li>
 * 		<li> le poids total maximal des objets que le joueur peut transporter </Li>
 * </ul>
 * </p>
 * @invariant !contient(null);
 * @invariant getPoidsMax() > 0;
 * @invariant getPoids() >= 0 && getPoids() <= getPoidsMax();
 * @invariant getNbObjets() >= 0;
 * @invariant getNom() != null;
 * 
 * @author     Jeremie Malueki
 * @version    1.0
 * @since      Octobre 2018
 */

public class Joueur extends ArrayListConteneur
{
	//Nom du joueur
	private String nom;

	//indique le nombre d'objets
	private int nbObjets;

	//indique le poids des objets transporte par le joueur
	private int poids;

	// indique le poids maximum que peut transporter le joueur
	private int poidsMax;


	 /**
     * Initalise un joueur ne transportant aucun objet et dont le nom est
     * la chaine de caracteres specifiee.
     * Le poids maximal est initialise a 10.
     *
     * @requires nom != null;
     * @ensures getNom().equals(nom);
     * @ensures getPoidsMax() == 10;
     * @ensures getPoids() == 0;
     * @ensures getNbObjets() == 0;
     *
     * @param nom Le nom du joueur
     *
     * @throws NullPointerException si le nom specifie est null
     */
	public Joueur (String nom)
	{
		super();
		if (nom == null) {
			throw new NullPointerException("le nom ne peut pas etre null");
		}
		this.nom=nom;
		this.poidsMax=30;
		
		
	}


 /**
     * Initalise un joueur ne transportant aucun objet, dont le nom est la chaîne
     * de caracteres specifiee et le poids maximal est l'entier specifie.
     *
     * @requires nom != null;
     * @requires poidsMax > 0;
     * @ensures getNom().equals(nom);
     * @ensures getPoidsMax() == poidsMax;
     * @ensures getPoids() == 0;
     * @ensures getNbObjets() == 0;
	 *
     * @param nom Le nom du joueur.
     * @param poidsMax Le poids maximal transportable par ce joueur.
     *
     * @throws NullPointerException si le nom specifie est null
     * @throws IllegalArgumentException si le poids maximal specifie est negatif ou nul
     */
	public Joueur (String nom,int poidsMax)
	{
		this(nom);
		if (poidsMax <= 0) {
            throw new IllegalArgumentException("Le poids maximal transportable par un Joueur doit être strictement positif");
		}
		this.poids=0;
		this.poidsMax=poidsMax;
	}
	

	/**
	 * renvoie le nom du joueur
	 * @return nom le nom du joueur
	 * @pure
	 */
	public String getNom()
	{
		return nom;
	}
	
	/**
	 * Methode qui renvoie le poids total des objets que transporte le joueur
	 * @return poids le poids des objets que transporte le joueur 
	 * @pure
	 */
	public int getPoids()
	{
		return poids;	
	}

	/**
	 * methode qui renvoie le poids maximum que peut transporter le joueur
	 * @return poidsMax
	 * @pure
	 */
	public int getPoidsMax() 
	{
		return poidsMax;	
	}
	
	
	/**
     * Renvoie true si l'ObjetZork specifie peut être ajoute a ce Joueur.
     * Un ObjetZork peut-etre ajoute s'il est different de null, s'il est
     * transportable et si le poids des ObjetZork transportes apres l'ajout
     * reste inferieur au poids maximal fixe pour ce Joueur.
     *
     * @ensures \result <==>
     *     (oz != null) && ((getPoids() + oz.getPoids()) <= getPoidsMax());
     *
     * @param oz L'objet dont on souhaite savoir s'il peut être ajoute aux
     * objets transportes par ce Joueur
     * @return true si l'ObjetZork specifie peut-être ajoute a la charge de
     * ce Joueur ; false sinon.
     * @pure
     */
	@Override
    public boolean ajoutEstPossible(ObjetZork oz) {
        return (oz != null)
                && oz.estTransportable()
                && ((getPoids() + oz.getPoids()) <= getPoidsMax());
    }
	

	@Override
	public void ajouter(ObjetZork oz) {
		if (ajoutEstPossible(oz)) {
		  contenu.add(oz);
		  this.poids=this.poids + oz.getPoids();
		  return;
		}
		System.err.println("Ajout impossible");
	  }


	  @Override
	  public boolean retirer(ObjetZork oz) {
		if (this.contient(oz)) {
			this.poids-=oz.getPoids();
			this.contenu.remove(oz);
		  	return true;
		}
		return false;
	  }

	/**
	 * methode qui permet de faire des test d'egalite avec la classe joueur
	 * retourne true si les deux objet sont egaux false sinon
	 * 
	 * @return true ou false
	 */
	public boolean equals(Object o) 
	{
		if(!(o instanceof Joueur)){
			return false;
		}
		Joueur j=(Joueur) o;
		if(getNom()!=j.getNom())
		{
			return false;
		}
		
		if (getNbObjets()!=j.getNbObjets()) {
			return false;
		}
		
		if (getPoids()!=j.getPoids()) {
			return false;
		}
		if (getPoidsMax()!=j.getPoidsMax()) {
			return false;
		}
		for(int i=0;i<nbObjets;i++)
		{
			if(!(contenu.get(i).equals(j.contenu.get(i))))
			{
				return false;
			}
		}
		return true;
	}

	public int hashCode() 
	{
		int code=nom.hashCode()*poids*poidsMax*nbObjets;
		for(int i=0;i<contenu.size();i++)
		{
			code=code*31+contenu.get(i).hashCode();
		}
		
		return code;
	}
	/**
	 * Retourne une chaine de caracteres representant l'objet. 
	 * @return r Chaine de carateres
	 **/
	public String toString()
	{
		String r="Nom: "+this.nom+"\n poids: "+this.poids+"\n poids max: "+this.poidsMax+"\n nbObjet: "+this.contenu.size()+"\n";
		for(int i=0;i<this.contenu.size();i++)
		{
			r=r+this.contenu.get(i)+"\n";
		}
		return r;
	}
	


}