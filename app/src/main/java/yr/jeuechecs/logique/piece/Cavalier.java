package yr.jeuechecs.logique.piece;

import yr.jeuechecs.logique.representation.Representation;
import yr.jeuechecs.logique.representation.RepresentationCavalier;
import yr.jeuechecs.logique.strategiedeplacement.StrategieDeplacement;
import yr.jeuechecs.logique.strategiedeplacement.StrategieDeplacementCavalier;

/**
 * Classe Cavalier
 * @author Younes Rabdi
 * @see Piece
 */
public class Cavalier extends Piece {
    private final static float m_valeur = 2.5f;

    /**
     * Créer Cavalier
     * @param p_couleur Couleur
     * @return Cavalier
     */
    public static Piece creer(Couleur p_couleur) {
        return new Cavalier(p_couleur);
    }

    /**
     * Constructeur Privé
     * @param p_couleur Couleur
     */
    private Cavalier(Couleur p_couleur) {
        super(p_couleur);
    }

    /**
     * Définition de la strategie de déplacement
     * @return StrategieDeplacement
     */
    @Override
    protected StrategieDeplacement setDeplacement() {
        return new StrategieDeplacementCavalier(this.getCouleur());
    }

    /**
     * Définition de la représentation
     * @return Representation
     */
    @Override
    protected Representation setReprensetation() {
        return new RepresentationCavalier(getCouleur());
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