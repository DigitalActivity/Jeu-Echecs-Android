package yr.jeuechecs.logique.piece;

import yr.jeuechecs.logique.representation.Representation;
import yr.jeuechecs.logique.representation.RepresentationReine;
import yr.jeuechecs.logique.strategiedeplacement.StrategieDeplacement;
import yr.jeuechecs.logique.strategiedeplacement.StrategieDeplacementReine;

/**
 * Classe Reine
 * @author Younes Rabdi
 * @see Piece
 */
public class Reine extends Piece {
    private final static float m_valeur = 9.0f;

    /**
     * Créer une nouvelle reine
     * @param p_couleur Couleur
     * @return Reine
     */
    public static Piece creer(Couleur p_couleur) {
        return new Reine(p_couleur);
    }

    /**
     * Constructeur Privé Reine
     * @param p_couleur Couleur
     */
    private Reine(Couleur p_couleur) {
        super(p_couleur);
    }

    /**
     * Définition de la strategie de déplacement
     * @return StrategieDeplacement
     */
    @Override
    protected StrategieDeplacement setDeplacement() {
        return new StrategieDeplacementReine(this.getCouleur());
    }

    /**
     * Définition de la représentation
     * @return Representation
     */
    @Override
    protected Representation setReprensetation() {
        return new RepresentationReine(this.getCouleur());
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