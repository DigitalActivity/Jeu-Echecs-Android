package yr.jeuechecs.logique.piece;

import yr.jeuechecs.logique.ObservateurPriseEnPassant;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.representation.Representation;
import yr.jeuechecs.logique.representation.RepresentationPion;
import yr.jeuechecs.logique.strategiedeplacement.StrategieDeplacement;
import yr.jeuechecs.logique.strategiedeplacement.StrategieDeplacementPion;

/**
 * Class Pion
 * @author Younes Rabdi
 * @see Piece
 */
public class Pion extends Piece implements ObservateurPriseEnPassant {
    private final static float m_valeur = 1.0f;

    /**
     * Créer un nouveau pion
     * @param p_couleur Couleur
     * @return Piece
     */
    public static Piece creer(Piece.Couleur p_couleur) {
        return new Pion(p_couleur);
    }

    /**
     * Constructeur Privé
     * @param p_couleur Couleur
     */
    private Pion(Piece.Couleur p_couleur) {
        super(p_couleur);
    }

    /**
     * Définir la strategie de déplacement
     * @return StrategieDeplacement
     */
    @Override
    protected StrategieDeplacement setDeplacement() {
        return new StrategieDeplacementPion(this.getCouleur());
    }

    /**
     * Définir la représentation
     * @return Representation
     */
    @Override
    protected Representation setReprensetation() {
        return new RepresentationPion(this.getCouleur());
    }

    /**
     * Valeur de la piece
     * @return valeur
     */
    @Override
    public float getValeur() {
        return m_valeur;
    }

    /**
     * Mettre à jour la position du pion vulnérable
     * @param p_positionPionVulnerable position du pion vulnérable
     */
    @Override
    public void mettreÀJour(Position p_positionPionVulnerable, Couleur p_couleur) {
        if(p_couleur != null && !this.getCouleur().equals(p_couleur)) {
            ((StrategieDeplacementPion)m_StrategyDeplacement).mettreÀJourPionVulnerable(p_positionPionVulnerable);
        } else {
            ((StrategieDeplacementPion)m_StrategyDeplacement).mettreÀJourPionVulnerable(null);
        }
    }
}