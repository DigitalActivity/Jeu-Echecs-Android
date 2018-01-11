package yr.jeuechecs.testpiece;

import junit.framework.TestCase;
import yr.jeuechecs.logique.Echiquier;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.Piece;
import yr.jeuechecs.logique.piece.Roi;

/**
 * Classe TestRoi : Contient les tests sur pièce Roi
 * @author Rabdi Younes
 * @see Piece
 * @see Roi
 */
public class TestRoi extends TestCase {

    private Piece roi;

    public void setUp() {
        roi = Roi.creer(Piece.Couleur.BLANC);
        Echiquier.Instance().get_tableJeu().clear();
    }

    /**
     * Test piece de type Roi
     */
    public void testRoi() {
        assertEquals(roi.getCouleur(), Piece.Couleur.BLANC); // test couleur
        assertEquals(roi.getValeur(), 0.0f);
        assertTrue(roi.estBlanc());
        assertFalse(roi.estNoir());
        roi.setPosActuelle(Echiquier.Colonne.E, 1); // Positionner en E1
        assertTrue(roi.posEstPossible(Echiquier.Colonne.E, 2)); // Peut se déplacer à E2
        assertFalse(roi.posEstPossible(Echiquier.Colonne.E, 3)); // Ne peu pas E3
    }

    /**
     * Test sur Piece clonée
     */
    public void testClone() {
        roi.setPosActuelle(Position.creer(1,2));

        Piece p1 = roi;
        Piece p2 = roi.clone();
        assertFalse(p1 == p2);
        assertFalse(p2 == roi);
        assertTrue(p2 != roi);
    }
}
