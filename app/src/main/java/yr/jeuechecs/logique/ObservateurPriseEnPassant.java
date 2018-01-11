package yr.jeuechecs.logique;

import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.Piece;

/**
 * Interface ObservateurPriseEnPassant
 *  Permet de recevoir les mises à jour à propos du pion vulnérable aux prises en passant
 *  Doit etre implementé par les classes qui veulent s'abonner au sujet prise en passant
 */
public interface ObservateurPriseEnPassant {
    /**
     * Mettre à jour les observateur de prise en passant
     * @param p_positionPionVulnerable pos du pion vulnerable
     * @param p_couleur couleur du pion vulnerable
     */
    void mettreÀJour(Position p_positionPionVulnerable, Piece.Couleur p_couleur);
}
