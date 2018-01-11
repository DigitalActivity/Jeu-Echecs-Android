package yr.jeuechecs.logique.piece;

import yr.jeuechecs.logique.representation.Representation;
import yr.jeuechecs.logique.representation.RepresentationTour;
import yr.jeuechecs.logique.strategiedeplacement.StrategieDeplacement;
import yr.jeuechecs.logique.strategiedeplacement.StrategieDeplacementTour;

/**
 * Classe Tour
 * @author Younes Rabdi
 * @see Piece
 */
public class Tour extends Piece {
    private final static float m_valeur = 5.0f;

    /**
     * Créer Tour
     * @param p_couleur Couleur
     * @return Tour
     */
    public static Piece creer(Couleur p_couleur) {
        return new Tour(p_couleur);
    }

    /**
     * Constructeur Privé
     * @param p_couleur Couleur
     */
    private Tour(Couleur p_couleur) {
        super(p_couleur);
    }

    /**
     * Définition de la strategie de déplacement
     * @return StrategieDeplacement
     */
    @Override
    protected StrategieDeplacement setDeplacement() {
        return new StrategieDeplacementTour(this.getCouleur());
    }

    /**
     * Définition de la représentation
     * @return Representation
     */
    @Override
    protected Representation setReprensetation() {
        return new RepresentationTour(this.getCouleur());
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