package yr.jeuechecs.testpiece;

import junit.framework.TestCase;
import yr.jeuechecs.logique.Echiquier;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.Piece;
import yr.jeuechecs.logique.piece.Tour;

/**
 * Classe TestTour : Contient les tests sur pièce Tour
 * @author Rabdi Younes
 * @see Piece
 * @see Tour
 */
public class TestTour extends TestCase {
    private Piece tour;

    public void setUp() {
        tour = Tour.creer(Piece.Couleur.NOIR);
        Echiquier.Instance().get_tableJeu().clear();
    }

    /**
     * Test piece de type Tour
     */
    public void testTour() {
        assertEquals(tour.getCouleur(), Piece.Couleur.NOIR); // test couleur
        assertEquals(tour.getValeur(), 5.0f);
        tour.setPosActuelle(Echiquier.Colonne.E, 1); // Positionner en E1
        assertTrue(tour.posEstPossible(Echiquier.Colonne.E, 7)); // Peut se déplacer à E7
        assertFalse(tour.posEstPossible(Echiquier.Colonne.B, 3)); // Ne peu pas B3 (diagonal)
    }

    /**
     * Test sur Piece clonée
     */
    public void testClone() {
        tour.setPosActuelle(Position.creer(1,2));

        Piece p1 = tour;
        Piece p2 = tour.clone();
        assertFalse(p1 == p2);
        assertFalse(p2 == tour);
        assertTrue(p2 != tour);
    }
}
