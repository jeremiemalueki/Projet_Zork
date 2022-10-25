
/**
 * Conteneur d'ObjetZork. Les elements contenus ne sont pas ordonnes et un
 * conteneur peut contenir plusieurs exemplaire d'un meme ObjetZork (au sens
 * equals)
 * 
 * @author Jeremie Malueki-Ngombi
 */
public interface Conteneur {

  /**
   * renvoie le nombre d'exemplaire de l'objet specifique present dans ce
   * conteneur La presence d'un Objet est testee en utilisant la methode equals de
   * la classe ObjetZork. L'argument doit etre non <code> null </code>
   * 
   * @param oz l'objet dont on cherche a connaitre le nombre d'exemplaire
   * @return le nombre d'exemplaire de l'objet
   * @requires oz !=null;
   * @ensures \result >=0;
   * @ensures contient(oz)<=>\result>0;
   * @ensures \result <=getNbObjets();
   * @pure
   */
  public int contientCombienDe(ObjetZork oz);
/**
   * Renvoie true si la personne possede au moins un exemplaire de l'objet
   * specifie. La presence d'un objet est teste en utilisant la methode equals de
   * la classe ObjetZork. L'argument doit etre non <code>null</code>.
   *
   * @ensures \result <==> (contientCombienDe(oz) > 0);
   *
   * @param oz Objet dont on cherche a savoir s'il est present dans cette piece
   * @return true si ce joueur possede au moins un exemplaire de l'objet specifie
   *         ; false sinon.
   * @pure
   */
  public boolean contient(ObjetZork oz);

  /**
   * renvoie le nombre d'objets dans ce conteneur. Chaque occurence est comptee.
   * 
   * @return le nombre d'ObjetZork dans ce conteneur
   * @ensures \result >=0;
   * @pure
   */
  public int getNbObjets();

  /**
   * retourne une nouvelle instance d'ObjetZork dont la taille est le nombre
   * d'elements de ce conteneur. Le tableau renvoye contient les objets ObjetZork
   * contenus dans ce conteneur
   * 
   * @return Un tableau d'ObjetZork contenant tous les elements de ce conteneur
   * @ensures \result !=null && \result.size()==getNbObjets();
   * @ensures(\forall int i>=0 && \result.size().contient(\result))
   */
  public ObjetZork[] getTabObjets();

  /**
   * indique si l'ObjetZork peut etre ajouter dans ce conteneur
   * 
   * @param oz l'objet a ajouter
   * @return true si l'objet peut etre ajouter
   * @pure
   */
  public boolean ajoutEstPossible(ObjetZork oz);

  /**
   * Ajoute l'objet specifie ou conteneur.
   * @param oz l'objet a ajouter dans ce conteneur
   *
   * @requires ajoutPossible(oz);
   * @ensures contient(oz);
   */
  public void ajouter(ObjetZork oz);

  /**
   * retire un exemplaire de l'objet specifie du conteneur si cet objet y est
   * present. Renvoie true si cet objet est effectivement present dans ce
   * conteneur et que l'objet a pu etre retire, renvoie false sinon. la presence
   * d'un objet est testee en utilisant la methode equals de la classe ObjetZork.
   * L'argument doit etre non <code> null </code>
   * 
   * @param oz ObjetZork dont un exemplaire doit etre retirer de ce conteneur
   * @return true si cet objet est effectivement present; false sinon
   * @requires oz!=null;
   * @ensures \old contient(oz)<=>\result
   * @ensures \old contient(oz)<=>contientCombienDe(oz) ==\old
   *          contientCombienDe(oz)\-1;
   */
  public boolean retirer(ObjetZork oz);

  /**
   * Cherche un ObjetZork present dans le conteneur a l'aide d'une
   * une chaine de caracteres qui correspond a la description de l'ObjetZork.
   * Renvoie l'ObjetZork si celui-ci est present dans le conteneur
   * @param s nom de l'objet
   * @return l'objet recherche ou null
   * @requires s!=null;
   */
  public ObjetZork chercheObjet(String s);
}
