package yr.jeuechecs.testpiece;

import junit.framework.TestCase;
import yr.jeuechecs.logique.Echiquier;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.Cavalier;
import yr.jeuechecs.logique.piece.Piece;

/**
 * Classe TestCavalier : Contient les tests sur piece cavalier
 * @author Rabdi Younes
 * @see Piece
 * @see Cavalier
 */
public class TestCavalier  extends TestCase {
    private Piece cavalier;

    public void setUp() {
        cavalier = Cavalier.creer(Piece.Couleur.NOIR);
        Echiquier.Instance().get_tableJeu().clear();
    }

    /**
     * Test piece de type Cavalier
     */
    public void testCavalier() {
        assertEquals(cavalier.getCouleur(), Piece.Couleur.NOIR); // test couleur
        assertEquals(cavalier.getValeur(), 2.5f);
        assertFalse(cavalier.estBlanc());
        assertTrue(cavalier.estNoir());
        cavalier.setPosActuelle(Echiquier.Colonne.B, 3); // Positionner en B3
        assertFalse(cavalier.posEstPossible(Echiquier.Colonne.E, 3)); // Ne Peut se déplacer à E3
        assertTrue(cavalier.posEstPossible(Echiquier.Colonne.C, 1)); // peut C1
        assertTrue(cavalier.posEstPossible(Echiquier.Colonne.D, 4)); // peut D4
        assertTrue(cavalier.posEstPossible(Echiquier.Colonne.C, 5)); // peut D4
    }

    /**
     * Test sur Piece clonée
     */
    public void testClone() {
        cavalier.setPosActuelle(Position.creer(1,2));

        Piece p1 = cavalier;
        Piece p2 = cavalier.clone();
        assertFalse(p1 == p2);
        assertFalse(p2 == cavalier);
        assertTrue(p2 != cavalier);
    }
}
