package yr.jeuechecs.testpiece;

import junit.framework.TestCase;

import yr.jeuechecs.logique.Echiquier;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.Fou;
import yr.jeuechecs.logique.piece.Piece;

/**
 * Classe TestFou : Contient les tests sur pièce Fou
 * @author Rabdi Younes
 * @see Piece
 * @see Fou
 */
public class TestFou extends TestCase {
        private Piece fou;

    public void setUp() {
        fou = Fou.creer(Piece.Couleur.BLANC);
        Echiquier.Instance().get_tableJeu().clear();
    }

    /**
     * Test piece de type Fou
     */
    public void testFou() {
        assertEquals(fou.getCouleur(), Piece.Couleur.BLANC); // test couleur
        assertEquals(fou.getValeur(), 3.0f);
        fou.setPosActuelle(Echiquier.Colonne.E, 1); // Positionner en E1
        assertFalse(fou.posEstPossible(Echiquier.Colonne.E, 7)); // Ne Peut se déplacer à E7
        assertTrue(fou.posEstPossible(Echiquier.Colonne.F, 2)); // Ne peut F2 (diagonal)
        assertTrue(fou.posEstPossible(Echiquier.Colonne.G, 3)); // peut G3 (diagonal)
    }

    /**
     * Test sur Piece clonée
     */
    public void testClone() {
        fou.setPosActuelle(Position.creer(1,2));

        Piece p1 = fou;
        Piece p2 = fou.clone();
        assertFalse(p1 == p2);
        assertFalse(p2 == fou);
        assertTrue(p2 != fou);
    }
}
