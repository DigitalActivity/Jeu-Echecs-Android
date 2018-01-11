package yr.jeuechecs.testpiece;

import junit.framework.TestCase;

import yr.jeuechecs.logique.Echiquier;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.Piece;
import yr.jeuechecs.logique.piece.Reine;

/**
 * Classe TestReine : Contient les tests sur  pièce reine
 * @author Rabdi Younes
 * @see Piece
 * @see Reine
 */
public class TestReine extends TestCase {
    private Piece reine;

    public void setUp() {
        reine = Reine.creer(Piece.Couleur.NOIR);
        Echiquier.Instance().get_tableJeu().clear();
    }

    /**
     * Test piece de type Reine
     */
    public void testReine() {
        assertEquals(reine.getCouleur(), Piece.Couleur.NOIR); // test couleur
        assertEquals(reine.getValeur(), 9.0f);
        assertFalse(reine.estBlanc());
        assertTrue(reine.estNoir());
        reine.setPosActuelle(Echiquier.Colonne.D, 1); // Positionner en D1
        assertTrue(reine.posEstPossible(Echiquier.Colonne.D, 7)); // Peut se déplacer à D7
        assertFalse(reine.posEstPossible(Echiquier.Colonne.E, 7)); // Ne peu pas E7
    }

    /**
     * Test sur Piece clonée
     */
    public void testClone() {
        reine.setPosActuelle(Position.creer(1,2));

        Piece p1 = reine;
        Piece p2 = reine.clone();
        assertFalse(p1 == p2);
        assertFalse(p2 == reine);
        assertTrue(p2 != reine);
    }
}
