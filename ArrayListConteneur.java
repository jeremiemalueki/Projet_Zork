import java.util.ArrayList;

/**
 * @author Jeremie Malueki-Ngombi
 */
public abstract class ArrayListConteneur implements Conteneur{

  protected ArrayList<ObjetZork> contenu;

  public ArrayListConteneur(){
    contenu=new ArrayList<ObjetZork>();
  }
  
  /**
   * Renvoie true si le conteneur possede au moins un exemplaire de l'objet
   * specifie.
   * L'argument doit etre non <code>null</code>.
   * @requires oz!=null;
   * @ensures \result <==> (contientCombienDe(oz) > 0);
   *
   * @param oz Objet dont on cherche a savoir s'il est present dans cette piece
   * @return true si ce joueur possede au moins un exemplaire de l'objet specifie
   *         ; false sinon.
   * @pure
   */
  public boolean contient(ObjetZork oz) {
    return contenu.contains(oz);
  }

  
  /**
   * renvoie le nombre d'objets dans ce conteneur. Chaque occurence est comptee.
   * 
   * @return le nombre d'ObjetZork dans ce conteneur
   * @ensures \result >=0;
   * @pure
   */
  public int getNbObjets() {
    return contenu.size();
  }

  /**
   * Ajoute l'objet passe en argument au conteneur.
   * @param oz l'objet a ajouter dans ce conteneur
   *
   * @requires ajoutPossible(oz);
   * @ensures contient(oz);
   */
  public void ajouter(ObjetZork oz) {
    if (ajoutEstPossible(oz)==true) {
      contenu.add(oz);
      return;
    }
    System.err.println("Ajout impossible");
  }


  /**
   * retourne une nouvelle instance d'ObjetZork dont la taille est le nombre
   * d'elements de ce conteneur. Le tableau renvoye contient les objets ObjetZork
   * contenus dans ce conteneur
   * 
   * @return Un tableau d'ObjetZork contenant tous les elements de ce conteneur
   * @ensures \result !=null && \result.size()==getNbObjets();
   * @ensures(\forall int i>=0 && \result.size().contient(\result))
   */
  public ObjetZork[] getTabObjets() {
    ObjetZork tabObjets[] = new ObjetZork[contenu.size()];
    tabObjets = contenu.toArray(tabObjets);
    return tabObjets;
  }


   /**
    * methode qui affiche tout les objet present dans l'arrayListConteneur.
    * 
    */
	public void afficherListe()
	{
		for (ObjetZork oz : contenu) {
      System.out.println(oz.getDescription());
    }
	}


  /**
   * Retire un exemplaire de l'objet specifie du conteneur si cet objet y est
   * present. Renvoie true si cet objet etait effectivement present et que l'objet
   * a pu etre effectivement retire ; renvoie false sinon. La presence d'un objet
   * est teste en utilisant la methode contient. L'argument
   * doit etre non <code>null</code>.
   *
   * @ensures \old(contient(oz)) <==> \result;
   * @ensures \old(contient(oz)) <==> (contientCombienDe(oz) ==
   *          \old(contientCombienDe(oz)) - 1);
   * @ensures \old(contientCombienDe(oz) == 1) ==> !contient(oz);
   * @ensures \old(contientCombienDe(oz) > 1) <==> contient(oz);
   * @ensures \result ==> (getPoids() == (\old(getPoids()) - oz.getPoids()));
   *
   * @param oz Objet dont un exemplaire doit etre retirer
   * @return true si cet objet etait effectivement present ; false sinon.
   */
  public boolean retirer(ObjetZork oz) {
    if (this.contient(oz)) {
      this.contenu.remove(oz);
      return true;
    }
    return false;
  }

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
  public int contientCombienDe(ObjetZork oz) {
    int result=0;
    for (ObjetZork objet : contenu) {
      if(objet.equals(oz)){
        result++;
      }
    }
    return result;
  }

  @Override
  public ObjetZork chercheObjet(String s) {
    for (ObjetZork oz : contenu) {
      if(oz.getDescription().equals(s)){
        return oz;
      }
    }
    return null; 
  }

  /**
   * Renvoie true si l'ObjetZork specifie peut être ajoute a ce Joueur. Un
   * ObjetZork peut-etre ajoute s'il est different de null, s'il est transportable
   * et si le poids des ObjetZork transportes apres l'ajout reste inferieur au
   * poids maximal fixe pour ce Joueur.
   *
   * @ensures \result <==> (oz != null) && ((getPoids() + oz.getPoids()) <=
   *          getPoidsMax());
   *
   * @param oz L'objet dont on souhaite savoir s'il peut être ajoute aux objets
   *           transportes par ce Joueur
   * @return true si l'ObjetZork specifie peut-être ajoute a la charge de ce
   *         Joueur ; false sinon.
   *
   * @pure
   */
  public abstract boolean ajoutEstPossible(ObjetZork oz);

 
}
