package yr.jeuechecs.logique;

import yr.jeuechecs.logique.piece.Piece;

/**
 * Interface SujetPriseEnPassant : (Observer pattern) l'echiquier implemente cette interface, et
 * notifie les pions abonnés lorsqu'un pion devient vulnérable à la prise en passant
 */
public interface SujetPriseEnPassant {
    void abonnerPriseEnPassant(ObservateurPriseEnPassant o);
    void desabonnerPriseEnPassant(ObservateurPriseEnPassant o);
    void notifierPriseEnPassant(Position p_posPionVulnerable, Piece.Couleur p_couleur);
}
