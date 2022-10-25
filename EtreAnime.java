import java.util.ArrayList;

/**
 * Les etres animes (personnages, animaux, monstres...) sont des entites
 * capable de se deplacer. Comme les objets ou le joueur, les etres animes sont localises dans des pieces, mais a
 * la difference des objets, les etres animes peuvent se deplacer de leur propre initiative.
 * @invariant getDescription() != null;
 * @invariant descriptionLongue() != null;
 * @invariant getPoids() >= 0;
 * @invariant !estTransportable();
 * @author     Jeremie Malueki
 * @version    1.0
 * @since      Janvier 2019
 */
public class EtreAnime extends ObjetZork{
    
    
    private ArrayList<ObjetZork> Objets;

    /**
     * @require nom!=null;
     * @ensures getDescription()==nom;
     * @param nom le nom de l'etre anime
     * @throws NullPointerException si le nom est null
     */
    public EtreAnime(String nom)
    {
        super(nom);
        this.Objets=new ArrayList<ObjetZork>();
    }


    /**
     * 
     * @param nom   nom de l'etre anime
     * @param objets    arrayList d'objetZork
     * @requires nom!=null
     * 
     */
    public EtreAnime(String nom, ArrayList<ObjetZork> objets)
    {
        super(nom);
        this.Objets=new ArrayList<ObjetZork>(10);
    }

  /**
   * Ajoute l'objet passe en argument au conteneur.
   * @param oz l'objet a ajouter dans ce conteneur
   *@requires oz!=null
   * @ensures contient(oz);
   */
    public void ajouter (ObjetZork oz){
        if(oz.estTransportable()){
            Objets.add(oz);
            return;
        }
    }
    
    public boolean contient(ObjetZork oz){
        return Objets.contains(oz);
    }

    /**
     * Methode qui renvoie l'arrayList d'objet de l'etre anime;
     * @return l'arrayList d'objets
     */
    public ArrayList<ObjetZork> getObjets() {
        return Objets;
    }
    
}