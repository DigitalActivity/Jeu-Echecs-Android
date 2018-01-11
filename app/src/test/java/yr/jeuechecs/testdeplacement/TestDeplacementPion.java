package yr.jeuechecs.testdeplacement;

import junit.framework.TestCase;
import yr.jeuechecs.logique.Echiquier;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.Piece;
import yr.jeuechecs.logique.piece.Pion;

/**
 * Test Strategie de deplacement Pion
 * @see yr.jeuechecs.logique.strategiedeplacement.StrategieDeplacementPion
 */
public class TestDeplacementPion  extends TestCase {
    private Piece pion;
    private Position pos;

    public void setUp() {
        Echiquier.Instance().get_tableJeu().clear();
        pion = Pion.creer(Piece.Couleur.BLANC);
        pos = Position.creer(Echiquier.Colonne.C, 3); // C3
    }

    /**
     * Test les deplacements de base de Pion
     */
    public void testDeplacementPion() {
        pion.setPosActuelle(pos); // setPosActuelle sur C3
        System.out.println("Déplacements possibles Pion sur " + pos.toString() + " :");
        for(Position p : pion.obtenirPosPossiblesDeBase()) {
            System.out.print(p.toString() + " | ");
        }
        System.out.println();
        assertTrue(pion.posEstPossible(Echiquier.Colonne.C, 4)); // avancer d'une case
        assertFalse(pion.posEstPossible(Echiquier.Colonne.C, 6)); // NON trop loin
        assertFalse(pion.posEstPossible(Echiquier.Colonne.C, 3)); // NON reculer pas possible
        assertFalse(pion.posEstPossible(Echiquier.Colonne.B, 3)); // NON à gauche pas possible
        assertFalse(pion.posEstPossible(Echiquier.Colonne.D, 3)); // NON à droite pas possible
        assertFalse(pion.posEstPossible(Echiquier.Colonne.D, 9)); // NON out
        assertTrue(pion.posEstPossible(Echiquier.Colonne.C, 5)); // deux cases sont possibles si premier déplacement
        pion.setPosActuelle(Echiquier.Colonne.C, 5);
        assertFalse(pion.posEstPossible(Echiquier.Colonne.C, 7)); // deux cases ne sont pas possibles apres le premier déplacement
    }
}
