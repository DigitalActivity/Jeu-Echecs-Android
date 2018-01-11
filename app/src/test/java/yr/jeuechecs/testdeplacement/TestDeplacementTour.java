package yr.jeuechecs.testdeplacement;

import junit.framework.TestCase;
import yr.jeuechecs.logique.Echiquier;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.Piece;
import yr.jeuechecs.logique.piece.Tour;

/**
 * Test Strategie de deplacement Tour
 * @see yr.jeuechecs.logique.strategiedeplacement.StrategieDeplacementTour
 */
public class TestDeplacementTour extends TestCase {
    private Piece tour;
    private Position pos;
    private Echiquier echiquier;

    public void setUp() {
        tour = Tour.creer(Piece.Couleur.BLANC);
        pos = Position.creer(Echiquier.Colonne.C, 3); // C3
        echiquier = Echiquier.Instance();
        echiquier.get_tableJeu().clear();
    }

    /**
     * Test les deplacements de base de Tour
     */
    public void testDeplacementTour() {
        tour.setPosActuelle(pos); // setPosActuelle sur C3
        System.out.println("Déplacements possibles Tour sur " + pos.toString() + " :");
        for(Position p : tour.obtenirPosPossiblesDeBase()) {
            System.out.print(p.toString() + " | ");
        }
        System.out.println();
        assertTrue(tour.posEstPossible(Echiquier.Colonne.C, 7)); // avancer
        assertTrue(tour.posEstPossible(Echiquier.Colonne.C, 1)); // reculer
        assertTrue(tour.posEstPossible(Echiquier.Colonne.H, 3)); //  à droite
        assertTrue(tour.posEstPossible(Echiquier.Colonne.A, 3)); //  à gauche
        assertFalse(tour.posEstPossible(Echiquier.Colonne.D, 4)); // NON diagonal à gauche
        assertFalse(tour.posEstPossible(Echiquier.Colonne.B, 4)); // NON diagonal à droite
        assertFalse(tour.posEstPossible(Echiquier.Colonne.D, 2)); // NON diagonal bas à gauche
        assertFalse(tour.posEstPossible(Echiquier.Colonne.B, 2)); // NON diagonal bas à droite
        assertFalse(tour.posEstPossible(Echiquier.Colonne.C, 0)); // NON out
    }

    /**
     * Test deplacements tour avec d'autres pieces sur l'echiquier
     */
    public void testDeplacementsTourEchiquierPlein() {
        echiquier.initialiser();
        Piece piece2 = echiquier.obtenirPieceSelonPosition(Position.creer(Echiquier.Colonne.A, 1));
        assertEquals(piece2.getClass(), Tour.class); // Tour selectionné
        assertTrue(piece2.obtenirPosPossiblesDeBase().isEmpty());
        echiquier.get_tableJeu().remove(Position.creer(Echiquier.Colonne.A, 2)); // Enlever le pion qui fait obstacle
        assertFalse(piece2.obtenirPosPossiblesDeBase().isEmpty());
        assertTrue(piece2.posEstPossible(1, 2));
        assertTrue(piece2.posEstPossible(1, 3));
        assertTrue(piece2.posEstPossible(1, 4));
        assertTrue(piece2.posEstPossible(1, 5));
        assertTrue(piece2.posEstPossible(1, 6));
        assertTrue(piece2.posEstPossible(1, 7)); // peut capturer le pion noir
        assertFalse(piece2.posEstPossible(1, 8));
        assertFalse(piece2.posEstPossible(2, 2));
        assertFalse(piece2.posEstPossible(3, 1));
        assertFalse(piece2.posEstPossible(4, 8));
        assertFalse(piece2.posEstPossible(2, 1));
    }
}
