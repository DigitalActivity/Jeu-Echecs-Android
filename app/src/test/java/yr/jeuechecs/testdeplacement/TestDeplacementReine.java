package yr.jeuechecs.testdeplacement;

import junit.framework.TestCase;
import yr.jeuechecs.logique.Echiquier;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.Piece;
import yr.jeuechecs.logique.piece.Reine;

/**
 * Test Strategie de deplacement Reine
 * @see yr.jeuechecs.logique.strategiedeplacement.StrategieDeplacementReine
 */
public class TestDeplacementReine extends TestCase {
    private Piece reine;
    private Position pos;

    public void setUp() {
        reine = Reine.creer(Piece.Couleur.BLANC);
        pos = Position.creer(3, 3); // C3
    }

    /**
     * Test les deplacements de base d'une Reine
     */
    public void testDeplacementReine() {
        reine.setPosActuelle(pos); // setPosActuelle sur C3
        System.out.println("Déplacements possibles Reine sur " + pos.toString() + " :");
        for(Position p : reine.obtenirPosPossiblesDeBase()) {
            System.out.print(p.toString() + " | ");
        }
        System.out.println();
        assertTrue(reine.posEstPossible(Echiquier.Colonne.C, 7)); // avancer 4 cases
        assertTrue(reine.posEstPossible(Echiquier.Colonne.C, 8)); // avancer 5 case
        assertTrue(reine.posEstPossible(Echiquier.Colonne.D, 3)); //  à droite
        assertTrue(reine.posEstPossible(Echiquier.Colonne.B, 3)); //  à gauche
        assertTrue(reine.posEstPossible(Echiquier.Colonne.D, 4)); // diagonal à droite
        assertTrue(reine.posEstPossible(Echiquier.Colonne.B, 4)); // diagonal à gauche
        assertTrue(reine.posEstPossible(Echiquier.Colonne.A, 5)); // diagonal à gauche
        assertTrue(reine.posEstPossible(Echiquier.Colonne.C, 1)); // reculer
        assertFalse(reine.posEstPossible(Echiquier.Colonne.C, 10)); // NON out
        assertFalse(reine.posEstPossible(Echiquier.Colonne.C, 0)); // NON out
    }
}
