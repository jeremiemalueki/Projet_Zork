import java.util.Random;

/**
 * Classe principale du jeu "Bavia".
 * <p>
 *
 * Bavia est un jeu d'aventure avec une interface en mode
 * texte: les joueurs peuvent se deplacer parmi les differentes pieces et ramasser divers
 * objets. 
 * </p>
 * <p>
 *
 * Pour jouer a ce jeu, creer une instance de cette classe et appeler sa methode
 * "jouer".
 * </p>
 * <p>
 *
 * Cette classe cree et initialise des instances de toutes les autres classes:
 * elle cree toutes les pieces, cree l'analyseur syntaxique et demarre le jeu.
 * Elle se charge aussi d'executer les commandes que lui renvoie l'analyseur
 * syntaxique.
 * </p>
 *
 * @author Jeremie Malueki
 * @version 1.0
 * @since Octobre 2018
 */

public class Jeu {
	private AnalyseurSyntaxique analyseurSyntaxique;
	//piece ou se situe le joueur
	private Piece pieceCourante;

	//Permet de revenir en arriere avec la commande retour
	private Piece piecePrecedente;
	
	//Pieces du jeu
	private Piece chateau;
	private Piece trone;
	private Piece chambre;
	private Piece armurerie;
	private Piece donjon;
	private Piece village;
	private Piece foret;
	private Piece montagne;
	private Piece templeForet;
	private Piece grotte;
	private Piece teleportation;

	//Etre Animes
	private EtreAnime garde;
	private EtreAnime roi;
	private EtreAnime chien;
	private EtreAnime villageois;

	//joueur
	private Joueur joueur;
	

	/**
	 *  Cree le jeu et initialise la carte du jeu (i.e. les pieces).
	 */
	public Jeu() {
		creerPieces();
		initJoueur();
		analyseurSyntaxique = new AnalyseurSyntaxique();
	}


	/**
	 *  Initialise toutes les pieces et relie leurs sorties les unes aux autres.
	 */
	public void creerPieces() {
		
		// creation des pieces

		chateau= new Piece("entre du chateau");
		//Objets du chateau
		chateau.ajouter(new ObjetZork ("pont-levis"));
		garde=new EtreAnime("garde");
		chateau.ajouter(garde);

		chambre= new Piece("la chambre royale");
		//Objet de la chambre
		chien=new EtreAnime("chien");
		chambre.ajouter(chien);
		chambre.ajouter(new ObjetZork ("cape",1));
		chambre.ajouter(new ObjetZork ("lit"));
		chambre.ajouter(new ObjetZork ("armoire"));


		armurerie= new Piece("l'armurerie");
		//Objets de l'armurerie
		armurerie.ajouter(new ObjetZork ("armure",5));
		armurerie.ajouter(new ObjetZork ("bouclier_fer",2));
		armurerie.ajouter(new ObjetZork ("bouclier_roi",3));
		armurerie.ajouter(new ObjetZork ("epee_fer",2));
		armurerie.ajouter(new ObjetZork("lance",3));
		armurerie.ajouter(new ObjetZork("mannequin"));

		donjon= new Piece("le donjon");
		//Objet du donjon
		donjon.ajouter(new ObjetZork ("bague_blanche",1));
		donjon.ajouter(new ObjetZork ("bague_noire",1));
		donjon.ajouter(new ObjetZork("stele"));

		village= new Piece("le village");
		//Objet du village
		village.ajouter(new ObjetZork ("statue"));
		village.ajouter(new ObjetZork ("banc"));
		villageois=new EtreAnime("villageois");
		village.ajouter(villageois);
		
		foret= new Piece("dans la foret");
		//Objet de la foret
		foret.ajouter(new ObjetZork ("grand_chene"));

		montagne= new Piece("les montagnes");
		//objet de la montagne
		montagne.ajouter(new ObjetZork ("tonne_de_neige"));

		grotte=new Piece("une grotte");
		//Objet de la grotte
		grotte.ajouter(new ObjetZork ("epee_roi",3));
		grotte.ajouter(new ObjetZork ("rocher"));

		templeForet= new Piece("Le temple de la foret");
		//Objet temple foret
		templeForet.ajouter(new ObjetZork("couronne", 2));
		templeForet.ajouter(new ObjetZork("socle"));
		
		trone= new Piece("Salle du trone");
		//Objet de la salle du trone
		trone.ajouter(new ObjetZork("trone"));
		roi=new EtreAnime("roi");
		trone.ajouter(roi);
		

		//Piece de teleportation
		teleportation=new Piece("piece de teleportation");


		// initialise les sorties des pieces
		chateau.setSorties(village,chambre,trone,armurerie);
		trone.setSorties(chateau, null, donjon, null);
		chambre.setSorties(null,null,null,chateau);
		armurerie.setSorties(null,chateau,null,teleportation);
		donjon.setSorties(trone,null,null,null);
		village.setSorties(null,foret,chateau,montagne);
		foret.setSorties(null,templeForet,null,village);
		montagne.setSorties(null,village,null,grotte);
		templeForet.setSorties(null,null,null,foret);
		grotte.setSorties(null, montagne, null, null);
		teleportation.setSorties(null, armurerie, null, null);
		
		// le jeu commence au chateau
		pieceCourante = chateau;
		//Piece precedente
		piecePrecedente=chateau;
		
	}

	/**
	 * Initialise le joueur
	 */
	public void initJoueur() {
		this.joueur=new Joueur("joueur", 20);

	}

	/**
	 *  Pour lancer le jeu. Boucle jusqu'a la fin du jeu.
	 */
	public void jouer() {
		afficherMsgBienvennue();
		// Entree dans la boucle principale du jeu. Cette boucle lit
		// et execute les commandes entrees par l'utilisateur, jusqu'a
		// ce que la commande choisie soit la commande "quitter"
		boolean termine = false;
		
		while (!termine) {
			Commande commande = analyseurSyntaxique.getCommande();
			termine = traiterCommande(commande);
			if(perdre()==true){
				termine=true;
			}
			if(gagner()==true){
				termine=true;
			}
			teleportation();
		}
		System.out.println("Merci d'avoir jouer.  Au revoir.");
	}


	/**
	 *  Affiche le message d'accueil pour le joueur.
	 */
	public void afficherMsgBienvennue() {
		System.out.println();
		System.out.println("Bienvennue dans le monde de Bavia !");
		System.out.println("Bavia est un nouveau jeu d'aventure ou vous etes pretendant au trone.");
		System.out.println("le roi actuel est un peu tete en l'air et a perdu ses bien les plus precieux vous devez les retrouver.");
		System.out.println("Pour etre couronne roi a votre tour, vous devez rassembler les objets royaux perdus dissemines dans le royaume : l'epee du roi,la cape,le bouclier du roi,la bague et la couronne");
		System.out.println("il faut les ramener a la salle du trone du chateau.");
		System.out.println("Mais surtout rendre sa couronne perdu au Roi !");
		System.out.println("Tapez 'aide' si vous avez besoin d'aide.");
		System.out.println();
		System.out.println(pieceCourante.descriptionLongue());
	}


	/**
	 *  Execute la commande specifiee. Si cette commande termine le jeu, la valeur
	 *  true est renvoyee, sinon false est renvoyee
	 *
	 * @param  commande  La commande a executer
	 * @return           true si cette commande termine le jeu ; false sinon.
	 */
	public boolean traiterCommande(Commande commande) {
		if (commande.estInconnue()) {
			System.out.println("Je ne comprends pas ce que vous voulez...");
			return false;
		}

		String motCommande = commande.getMotCommande();
		if (motCommande.equals("aide")) {
			afficherAide();
		} else if (motCommande.equals("aller")) {
			deplacerVersAutrePiece(commande);
		} else if (motCommande.equals("quitter")) {
			if (commande.aSecondMot()) {
				System.out.println("Quitter quoi ?");
			} else {
				return true;
			}
		}
		else if (motCommande.equals("prendre")){
			prendreObjet(commande);
		}
		else if (motCommande.equals("deposer")){
			deposer(commande);
		}
		else if(motCommande.equals("etat")){
			etatJoueur();
		}
		else if(motCommande.equals("retour")){
			retour();
		}
		else if(motCommande.equals("regarder")){
			regarder();
		}
		else if(motCommande.equals("donner")){
			donner(commande);
		}
		return false;
	}


	// implementations des commandes utilisateur:

	/**
	 *  Affichage de l'aide. Affiche notament la liste des commandes utilisables.
	 */
	public void afficherAide() {
		System.out.println("Vous etes dans le royaume de Bavia.");
		System.out.println("Vous devez retrouvez les objets royaux perdus: 'epee_roi', 'cape', 'bouclier_roi', 'bague' et la 'couronne'");
		System.out.println("Retrouvez et 'donner' la couronne au Roi");
		System.out.println();
		System.out.println("Les commandes reconnues sont:");
		analyseurSyntaxique.afficherToutesLesCommandes();
	}


	/**
	 *  Tente d'aller dans la direction specifiee par la commande. Si la piece
	 *  courante possede une sortie dans cette direction, la piece correspondant a
	 *  cette sortie devient la piece courante, dans les autres cas affiche un
	 *  message d'erreur.
	 *
	 * @param  commande  Commande dont le second mot specifie la direction a suivre
	 */
	public void deplacerVersAutrePiece(Commande commande) {
		if (!commande.aSecondMot()) {
			// si la commande ne contient pas de second mot, nous ne
			// savons pas ou aller..
			System.out.println("Aller où ?");
			return;
		}

		String direction = commande.getSecondMot();

		piecePrecedente=pieceCourante;
		// Tentative d'aller dans la direction indiquee.
		Piece pieceSuivante = pieceCourante.pieceSuivante(direction);

		if (pieceSuivante == null) {
			System.out.println("Il n'y a rien dans cette direction !");
		} else {
			pieceCourante = pieceSuivante;
			System.out.println(pieceCourante.descriptionLongue());
		}
	}
	/**
	 * Methode de la commande "retour"
	 * permet de revenir a la piece precedente.
	 */
	public void retour(){
		pieceCourante=piecePrecedente;
		System.out.println("Vous etes revenu dans la piece precedente");
		System.out.println(pieceCourante.descriptionLongue());
	}

	/**
	 * Tente de prendre un objet dans une piece. 
	 * Si l'objet est present l'objet est ajoute a l'inventaire du joueur
	 * si il n'est pas present la methode affiche un message d'erreur.
	 * @param commande commande dont le second mot designe l'objet a prendre.
	 */
	public void prendreObjet(Commande commande) {
		if(!commande.aSecondMot()){
			//si la commande n'a pas de second mot, nous ne
			//savons pas quoi prendre
			System.out.println("Prendre quoi ?");
			return;
		}
		else if(pieceCourante.chercheObjet(commande.getSecondMot())==null)
		{
			System.out.println("Il n'y a pas cet objet.");
			return;
		}
		else if (pieceCourante.chercheObjet(commande.getSecondMot()).estTransportable()==false) {
			System.out.println("Vous ne pouvez pas prendre ca!");
			return;
		}
		this.joueur.ajouter(pieceCourante.chercheObjet(commande.getSecondMot()));
		this.pieceCourante.retirer(pieceCourante.chercheObjet(commande.getSecondMot()));
		
		System.out.println("vous avec pris : "+commande.getSecondMot());
	}

	/**
	 * Depose un objet possede par le joueur dans la piece ou il se situe.
	 * Si le joueur ne possede pas l'objet passe en commande la methode affiche
	 * une message d'erreur.
	 * @param commande la commande passe en argument le second mot designe l'objet
	 */
	public void deposer(Commande commande) {
		if(!commande.aSecondMot()){
			System.out.println("Deposer quoi ?");
			return;
		}
		else if(this.joueur.chercheObjet(commande.getSecondMot())==null)
		{
			System.out.println("Vous ne possedez pas cet objet.");
			return;
		}
		else{
			this.pieceCourante.ajouter(joueur.chercheObjet(commande.getSecondMot()));
			this.joueur.retirer(joueur.chercheObjet(commande.getSecondMot()));
			System.out.println("vous avez deposer : "+commande.getSecondMot());
		}
	}

	/**
	 * Affiche l'etat du joueur :le poids qu'il transporte et les objets qu'il possede
	 */
	public void etatJoueur()
	{
		System.out.println("\npoids total: "+joueur.getPoids());
		System.out.println("Inventaire:\n");
		this.joueur.afficherListe();
	}

	/**methode qui permet de voir les objets present dans la piece courante.
	 * Donne des indication et des description concernant la piece
	*/
	public void regarder()
	{
		if(pieceCourante.descriptionCourte()=="le donjon"){
			System.out.println("prenez garde il pourrait y avoir des pieges\n");
		}
		else if(pieceCourante.descriptionCourte()=="entre du chateau"){
			System.out.println("le chateau de Bavia");
		}
		else if(pieceCourante.descriptionCourte()=="Salle du trone"){
			System.out.println("Le roi est affale sur son trone et reclame sa couronne");
		}
		else if(pieceCourante.descriptionCourte()=="l'armurerie"){
			System.out.println("l'armurerie du chateau");
		}
		else if(pieceCourante.descriptionCourte()=="dans la foret"){
			System.out.println("il y a des grands chenes et des petits animaux");
		}
		else if(pieceCourante.descriptionCourte()=="le village"){
			System.out.println("le village est animee !");
		}
		else if(pieceCourante.descriptionCourte()=="les montagnes"){
			System.out.println("il fait tres froid !");
		}
		else if(pieceCourante.descriptionCourte()=="Le temple de la foret"){
			System.out.println("un temple perdu au beau milieu de la foret en son centre vous apercevez un socle");
		}
		else if(pieceCourante.descriptionCourte()=="une grotte"){
			System.out.println("au fond de la grotte vous voyez une epee plante dans un rocher..");
		}
		System.out.println(this.pieceCourante.descriptionLongue());
		System.out.println("Vous voyez :\n");
		this.pieceCourante.afficherListe();
		
	}

	/**
	 * Permet de tester si le joueur reuni toute les condition de pour gagner le jeu
	 * retour true si oui retourne false sinon
	 * @return true ou false
	 */
	public boolean gagner()
	{
		//Objets a ramasser qui permettent de gagner
		ObjetZork cape=new ObjetZork ("cape",1);
		ObjetZork bouclier_roi=new ObjetZork ("bouclier_roi",3);
		ObjetZork bague_blanche=new ObjetZork ("bague_blanche",1);
		ObjetZork epee_roi=new ObjetZork ("epee_roi",3);
		ObjetZork couronne=new ObjetZork("couronne", 2);

		/*Condition de validation de la victoire si le joueur 
		possede les objets cape,armure,bouclier_roi,bague_blanche,epee_roi,couronne*/
		if((this.joueur.contient(cape)&&this.joueur.contient(bouclier_roi)&&this.joueur.contient(bague_blanche)&&this.joueur.contient(epee_roi))
		&& pieceCourante==trone&& roi.contient(couronne)){
			System.out.println("\nVous avez recuperer tout les objets! Bravo!");
			System.out.println("\nVous allez bientot etre couronne !\n");
			return true;
		}
		return false;
	}
	/**
	 * methode qui verifie si le joueur reunis les condition pour perdre
	 * @return true ou false
	 */
	public boolean perdre()
	{
		//Objets qui font perdre le joueur si ramasser
		ObjetZork bague_noire=new ObjetZork ("bague_noire",1);
		if(this.joueur.contient(bague_noire))
		{
			System.out.println("\nOh non! Vous avez declenche un piege, vous mourrez et echouer dans votre quete.\n");
			return true;
		}
		return false;
	}

	public boolean donner(Commande commande){
		if(!commande.aSecondMot()){
			System.out.println("Donner quoi?");
			return false;
		}
		//si la commande n'a pas de troisieme mot
		if(!commande.aTroisiemeMot()){
			System.out.println("Donner a qui?");
			return false;
		}
		//Si le joueur n'a pas l'objet dans son conteneur
		if(!joueur.contient(joueur.chercheObjet(commande.getSecondMot()))){
			System.out.println("vous n'avez pas cet objet!");
			return false;
		}
		//cas ou la piece ne contient pas l'EtreAnime passe en argument de la commande
		if(!pieceCourante.contient(pieceCourante.chercheObjet(commande.getTroisiemeMot()))){
			System.out.println("donner a qui?");
			return false;
		}
		
		//donne l'objet au garde
		if(commande.getTroisiemeMot().equals(garde.getDescription())){

			garde.ajouter(joueur.chercheObjet(commande.getSecondMot()));
			joueur.retirer(joueur.chercheObjet(commande.getSecondMot()));
			System.out.println("vous avez donner "+commande.getSecondMot()+" au garde");
			return true;
		}
		//donne l'objet au roi
		if(commande.getTroisiemeMot().equals(roi.getDescription())){

			roi.ajouter(joueur.chercheObjet(commande.getSecondMot()));
			joueur.retirer(joueur.chercheObjet(commande.getSecondMot()));
			System.out.println("vous avez donner "+commande.getSecondMot()+" au roi");
			return true;
		}
		System.out.println("vous ne pouvez pas lui donner ca");
		return false;
	}

	public void moveEtreAnime(){
		Piece tabGarde[]={armurerie,chateau,village,trone};
		//Mouvement du garde
		if(pieceCourante==chateau||pieceCourante==armurerie||pieceCourante==trone||pieceCourante==village){
			int n=tabGarde.length;
        	int rnd=new Random().nextInt(n);
			tabGarde[rnd].ajouter(garde);
			//couranteGarde.retirer(garde);
			pieceCourante.retirer(garde);
			//couranteGarde=tabGarde[rnd];
			
		}
	}

	/**
	 * methode de la teleportation s'active lorsque le joueur penetre dans la
	 * salle de teleportation. Renvoie true si la teleportation a lieu false sinon
	 * @return true ou false
	 */
	public boolean teleportation(){
		if(pieceCourante==teleportation){
			//Tableau contenant toutes les pieces pour pouvoir se teleporter
			Piece tabTeleport[]={chambre,donjon,armurerie,chateau,grotte,templeForet,village,montagne,foret,trone};
			int n=tabTeleport.length;
        	int rnd=new Random().nextInt(n);
			pieceCourante=tabTeleport[rnd];
			System.out.println("Vous etes entre dans la salle de teleportation, vous avez ete teleporte !");
			System.out.println(pieceCourante.descriptionCourte());
			return true;
		}
		return false;
	}
}