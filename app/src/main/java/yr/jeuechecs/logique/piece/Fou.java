package yr.jeuechecs.logique.piece;

import yr.jeuechecs.logique.representation.Representation;
import yr.jeuechecs.logique.representation.RepresentationFou;
import yr.jeuechecs.logique.strategiedeplacement.StrategieDeplacement;
import yr.jeuechecs.logique.strategiedeplacement.StrategieDeplacementFou;

/**
 * Classe Fou
 * @author Younes Rabdi
 * @see Piece
 */
public class Fou extends Piece {
    private final static float m_valeur = 3.0f;

    /**
     * Créer Fou
     * @param p_couleur Couleur
     * @return Pice Fou
     */
    public static Piece creer(Couleur p_couleur) {
        return new Fou(p_couleur);
    }

    /**
     * Constructeur Privé
     * @param p_couleur Couleur
     */
    private Fou(Couleur p_couleur) {
        super(p_couleur);
    }

    /**
     * Définition de la strategie de déplacement
     * @return StrategieDeplacement
     */
    @Override
    protected StrategieDeplacement setDeplacement() {
        return new StrategieDeplacementFou(this.getCouleur());
    }

    /**
     * Définition de la représentation
     * @return Representation
     */
    @Override
    protected Representation setReprensetation() {
        return new RepresentationFou(this.getCouleur());
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