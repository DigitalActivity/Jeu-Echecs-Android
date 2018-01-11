package yr.jeuechecs.testdeplacement;

import junit.framework.TestCase;
import yr.jeuechecs.logique.Echiquier;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.Cavalier;
import yr.jeuechecs.logique.piece.Piece;

/**
 * Test Strategie de deplacements cavalier
 * @see yr.jeuechecs.logique.strategiedeplacement.StrategieDeplacementCavalier
 */
public class TestDeplacementCavalier extends TestCase {
    private Piece cavalier;
    private Position pos;

    public void setUp() {
        cavalier = Cavalier.creer(Piece.Couleur.NOIR);
        pos = Position.creer(Echiquier.Colonne.C, 3); // C3
        Echiquier.Instance().get_tableJeu().clear();
    }

    /**
     * Test les deplacements de base du cavalier
     */
    public void testDeplacementCavalier() {
        cavalier.setPosActuelle(pos); // setPosActuelle sur C3
        System.out.println("Déplacements possibles Cavalier sur " + pos.toString() + " :");
        for(Position p : cavalier.obtenirPosPossiblesDeBase()) {
            System.out.print(p.toString() + " | ");
        }
        System.out.println();
        assertFalse(cavalier.posEstPossible(Echiquier.Colonne.C, 4));
        assertFalse(cavalier.posEstPossible(Echiquier.Colonne.C, 2));
        assertFalse(cavalier.posEstPossible(Echiquier.Colonne.D, 7));
        assertFalse(cavalier.posEstPossible(Echiquier.Colonne.C, 7));
        assertTrue(cavalier.posEstPossible(Echiquier.Colonne.A, 2));
        assertTrue(cavalier.posEstPossible(Echiquier.Colonne.B, 1));
        assertTrue(cavalier.posEstPossible(Echiquier.Colonne.B, 5));
    }
}
