package yr.jeuechecs.testdeplacement;

import junit.framework.TestCase;
import yr.jeuechecs.logique.Echiquier;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.Fou;
import yr.jeuechecs.logique.piece.Piece;

/**
 * Test Strategie de deplacement Fou
 * @see yr.jeuechecs.logique.strategiedeplacement.StrategieDeplacementFou
 */
public class TestDeplacementFou extends TestCase {
    private Piece fou;
    private Position pos;

    public void setUp() {
        fou = Fou.creer(Piece.Couleur.NOIR);
        pos = Position.creer(Echiquier.Colonne.C, 3); // C3
    }

    /**
     * Test les deplacements de base d'un fou
     */
    public void testDeplacementFou() {
        fou.setPosActuelle(pos); // setPosActuelle sur C3
        System.out.println("Déplacements possibles Fou sur " + pos.toString() + " :");
        for(Position p : fou.obtenirPosPossiblesDeBase()) {
            System.out.print(p.toString() + " | ");
        }
        System.out.println();
        assertFalse(fou.posEstPossible(Echiquier.Colonne.C, 4)); // NON avancer
        assertFalse(fou.posEstPossible(Echiquier.Colonne.C, 2)); // NON reculer
        assertFalse(fou.posEstPossible(Echiquier.Colonne.D, 3)); // NON à droite
        assertFalse(fou.posEstPossible(Echiquier.Colonne.B, 3)); // NON à gauche
        assertTrue(fou.posEstPossible(Echiquier.Colonne.D, 4)); // diagonal à gauche
        assertTrue(fou.posEstPossible(Echiquier.Colonne.B, 4)); // diagonal à droite
        assertTrue(fou.posEstPossible(Echiquier.Colonne.D, 2)); // diagonal bas à gauche
        assertTrue(fou.posEstPossible(Echiquier.Colonne.B, 2)); // diagonal bas à droite
        assertFalse(fou.posEstPossible(Echiquier.Colonne.C, 5)); // NON avancer 2 cases
        assertFalse(fou.posEstPossible(Echiquier.Colonne.C, 0)); // NON out
    }

    /**
     * Test les deplacements de base d'un fou avec d'autres pieces sur l'echiquier
     */
    public void testDeplacementsFouEchiquierPlein() {
        Echiquier echiquier = Echiquier.Instance();
        echiquier.initialiser();
        Piece piece = echiquier.obtenirPieceSelonPosition(Position.creer(Echiquier.Colonne.C, 1));
        assertEquals(piece.getClass(), Fou.class); // Fou selectionné
        assertTrue(piece.obtenirPosPossiblesDeBase().isEmpty());
        echiquier.get_tableJeu().remove(Position.creer(Echiquier.Colonne.B, 2)); // Enlever le pion qui fait obstacle
        assertFalse(piece.obtenirPosPossiblesDeBase().isEmpty());
        assertTrue(piece.posEstPossible(1, 3));
        assertTrue(piece.posEstPossible(2, 2));
        assertFalse(piece.posEstPossible(1, 8));
        assertFalse(piece.posEstPossible(5, 2));
        assertFalse(piece.posEstPossible(3, 1));
        assertFalse(piece.posEstPossible(4, 8));
    }
}
