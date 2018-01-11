package yr.jeuechecs.testpiece;

import junit.framework.TestCase;
import yr.jeuechecs.logique.Echiquier;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.Piece;
import yr.jeuechecs.logique.piece.Pion;

/**
 * Classe TestPion : Contient les tests sur les pion
 * @author Rabdi Younes
 * @see Piece
 * @see Pion
 */
public class TestPion extends TestCase {
    private Piece pion;

    public void setUp() {
        pion = Pion.creer(Piece.Couleur.BLANC);
        Echiquier.Instance().get_tableJeu().clear();
    }

    /**
     * Test piece de type Pion
     */
    public void testPion() {
        assertEquals(pion.getCouleur(), Piece.Couleur.BLANC);
        assertEquals(pion.getValeur(), 1.0f);
        assertTrue(pion.estBlanc());
        assertFalse(pion.estNoir());
        pion.setPosActuelle(Echiquier.Colonne.D, 2); // Positionner en D2
        assertTrue(pion.posEstPossible(Echiquier.Colonne.D, 3)); // Peut se déplacer à D3
        pion.setPosActuelle(Echiquier.Colonne.D, 3); // Positionner en D3
        assertFalse(pion.posEstPossible(Echiquier.Colonne.D, 5)); // Ne peut pas se déplacer de 2 cases après la premiere fois
        assertTrue(pion.posEstPossible(Echiquier.Colonne.D, 4)); // peut se déplacer D4
    }

    /**
     * Test sur Piece clonée
     */
    public void testClone() {
        pion.setPosActuelle(Position.creer(1,2));

        Piece p1 = pion;
        Piece p2 = pion.clone();
        assertFalse(p1 == p2);
        assertFalse(p2 == pion);
        assertTrue(p2 != pion);

        assertTrue(p1.obtenirPosPossiblesDeBase().contains(Position.creer(1, 4)));
        assertTrue(p2.obtenirPosPossiblesDeBase().contains(Position.creer(1, 4)));

        pion.setPosActuelle(Position.creer(1,4));

        assertEquals(p1.getNombreDeplacements(), 1);
        assertEquals(p2.getNombreDeplacements(), 0);
        assertTrue(p1.obtenirPosPossiblesDeBase().contains(Position.creer(1, 5)));
        assertFalse(p2.obtenirPosPossiblesDeBase().contains(Position.creer(1, 5)));
    }
}
