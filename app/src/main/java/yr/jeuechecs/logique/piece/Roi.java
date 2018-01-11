package yr.jeuechecs.logique.piece;

import yr.jeuechecs.logique.representation.Representation;
import yr.jeuechecs.logique.representation.RepresentationRoi;
import yr.jeuechecs.logique.strategiedeplacement.StrategieDeplacement;
import yr.jeuechecs.logique.strategiedeplacement.StrategieDeplacementRoi;

/**
 * Classe Roi
 * @author Younes Rabdi
 * @see Piece
 */
public class Roi extends Piece {
    private final static float m_valeur = 0.0f;

    /**
     * Créer un nouveau Roi
     * @param p_couleur Couleur
     * @return Roi
     */
    public static Piece creer(Couleur p_couleur) {
        return new Roi(p_couleur);
    }

    /**
     * Verifier si le petit roque est possible
     * @return vrai si possible
     */
    public boolean petitRoqueEstPossible() {
        return ((StrategieDeplacementRoi) m_StrategyDeplacement).verifierPetitRoque();
    }

    /**
     * Verifier si le grand roque est possible
     * @return vrai si possible
     */
    public boolean grandRoqueEstPossible() {
        return ((StrategieDeplacementRoi) m_StrategyDeplacement).verifierGrandRoque();
    }

    /**
     * Constructeur Privé
     * @param p_couleur Couleur
     */
    private Roi(Couleur p_couleur) {
        super(p_couleur);
    }

    /**
     * Définition de la strategie de déplacement
     * @return StrategieDeplacement
     */
    @Override
    protected StrategieDeplacement setDeplacement() {
        return new StrategieDeplacementRoi(this.getCouleur());
    }

    /**
     * Définition de la représentation
     * @return Representation
     */
    @Override
    protected Representation setReprensetation() {
        return new RepresentationRoi(this.getCouleur());
    }

    /**
     * Valeur de la piece
     * @return valeur
     */
    @Override
    public float getValeur() {
        return m_valeur;
    }
}