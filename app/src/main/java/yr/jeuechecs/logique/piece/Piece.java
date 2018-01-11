package yr.jeuechecs.logique.piece;

import android.content.Context;
import yr.jeuechecs.logique.Echiquier;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.representation.Representation;
import yr.jeuechecs.logique.strategiedeplacement.StrategieDeplacement;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Class abstraite Piece : Classe de base pour les pièces
 * @author Rabdi Younes
 */
public abstract class Piece implements Cloneable {
    public enum Couleur { BLANC, NOIR }
    private final Couleur m_couleur;
    StrategieDeplacement m_StrategyDeplacement; // Strategie de déplacement pour cette pièce
    private Representation m_representation; // Représentation de la piece

    /**
     * Factory Methode Pettern : Chaque classe dérivée va définir sa propre strategie de déplacement
     * @return StrategieDeplacement
     */
    protected abstract StrategieDeplacement setDeplacement();

    /**
     * Factory Methode Pettern : Chaque classe dérivée va définir sa représentation
     * @return Representation
     */
    protected abstract Representation setReprensetation();

    /**
     * Chaque classe dérivée va définir sa valeur
     * @return float
     */
    public abstract float getValeur(); // Pour obliger chaque piece à definir sa valeur.

    /**
     * Obtenir la Couleur de la piece
     * @return Couleur
     */
    public Couleur getCouleur() { return this.m_couleur; }

    /**
     * Constructeur Piece
     * @param p_couleur Couleur de la piece
     */
    Piece(Couleur p_couleur) {
        m_couleur = p_couleur;
        m_StrategyDeplacement = setDeplacement();
        m_representation = setReprensetation();
    }

    /**
     * Définir la table du jeu sur lequel la piece se trouve
     * @param p_echiquier table de jeu
     */
    public void setTableDuJeu(LinkedHashMap<Position, Piece> p_echiquier) {
        m_StrategyDeplacement.setTableJeu(p_echiquier);
    }

    /**
     * Définir la position actuelle de la piece.
     * @param p_posActuelle Posisiotn de la piece
     */
    public void setPosActuelle(Position p_posActuelle) {
        m_StrategyDeplacement.setPosActuelle(p_posActuelle);
    }

    /**
     * Obtenir toutes les positions valides pour cette pièce
     * @return Set de positions disponibles pour cette pièce
     */
    public Set<Position> obtenirPosPossiblesDeBase() {
        return m_StrategyDeplacement.obtenirDeplacementsBase();
    }

    /**
     * Vérifier si le déplacement vers la position en parametre est possible (pos valide + ne se cause pas d'echec)
     * @param p_destination Position de la déstination
     * @return true quand le déplacement est valide
     */
    public boolean posEstPossible(Position p_destination) {
        return m_StrategyDeplacement.deplacementEstPossible(p_destination);
    }

    /**
     * Obtenir le nombre de déplacements de la piece
     * @return nombre de deplacements
     */
    public int getNombreDeplacements() {
        return m_StrategyDeplacement.get_cptDeplacements();
    }

    /**
     * Obtenir position actuelle
     * @return position actuelle
     */
    public Position getPosActuelle() {
        return m_StrategyDeplacement.get_posActuelle();
    }

    /**
     * Surcharge posEstPossible
     * @param p_colonne Echiquier.Colonne
     * @param p_rangee numéro rangee
     */
    public boolean posEstPossible(Echiquier.Colonne p_colonne, int p_rangee) {
        return posEstPossible(Position.creer(p_colonne, p_rangee));
    }

    /**
     * Surcharge posEstPossible
     * @param p_colonne numéro colonne
     * @param rangee numéro rangee
     */
    public boolean posEstPossible(int p_colonne, int rangee) {
        return posEstPossible(Position.creer(p_colonne, rangee));
    }

    /**
     * Surcharge setPosActuelle
     * @param p_colonne Echiquier.Colonne
     * @param p_rangee numéro rangee
     */
    public void setPosActuelle(Echiquier.Colonne p_colonne, int p_rangee) {
        setPosActuelle(Position.creer(p_colonne, p_rangee));
    }

    /**
     * Surcharge setPosActuelle
     * @param p_colonne numéro colonne
     * @param rangee numéro rangee
     */
    public void setPosActuelle(int p_colonne, int rangee) {
        setPosActuelle(Position.creer(p_colonne, rangee));
    }

    /**
     * Definir le drawable de la piece à partir des ressources
     * @param context le context de l'activité appelante
     */
    public void setupDrawable(Context context) {
        m_representation.setupDrawable(context);
    }

    /**
     * Obtenir representation
     * @return representation
     */
    public Representation getRepresentation() {
        return this.m_representation;
    }

    /**
     * Vérifier si la couleur de la pièce est noir
     * @return vrai si la pièce est de couleur noir. Faux sinon
     */
    public boolean estNoir() {
        return this.m_couleur.equals(Couleur.NOIR);
    }

    /**
     * Vérifier si la couleur de la pièce est blanc
     * @return vrai si la pièce est de couleur blanc. Faux sinon
     */
    public boolean estBlanc() {
        return this.m_couleur.equals(Couleur.BLANC);
    }

    /**
     * Afficher le character representant de la pièce
     */
    public void afficher() {
        this.m_representation.afficher();
    }

    /**
     * Cloner une piece. Utilisé surtout pour simuler un deplacement et pour sauvegarder un etat du jeu
     * @return Piece clone
     */
    @Override
    public Piece clone()	{
        try {
            Piece cl = (Piece) super.clone();
            cl.m_StrategyDeplacement = this.m_StrategyDeplacement.clone();
            cl.m_representation = this.m_representation;
            return cl;
        }
        catch	(CloneNotSupportedException e)	{
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}