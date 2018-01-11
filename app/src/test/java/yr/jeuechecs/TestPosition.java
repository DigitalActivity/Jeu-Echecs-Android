package yr.jeuechecs;

import junit.framework.TestCase;

import yr.jeuechecs.logique.Echiquier;
import yr.jeuechecs.logique.Position;

/**
 * Classe TestPosition : Contient les tests sur les positions de l'echiquier
 * @see Position
 */
public class TestPosition extends TestCase {
    private Position p1;
    private Position p2;

    /**
     * Test les coordonnées d'une position
     */
    public void testPosition() {
        // test positions avec les mêmes coordonnées
        p1 = Position.creer(Echiquier.Colonne.A, 2); // A2
        assertEquals(p1.getColonne(), 1);
        assertEquals(p1.getRangee(), 2);
        assertEquals(p1.toString(), "A2");

        p2 = Position.creer(1, 2); // Colone A2
        assertEquals(p1.getColonne(), 1);
        assertEquals(p1.getRangee(), 2);
        assertEquals(p1.toString(), "A2");
        assertEquals(p2.hashCode(), p1.hashCode());
        assertTrue(p1.equals(p2));

        p1 = Position.creer(Echiquier.Colonne.B, 2); // B2
        assertEquals(p1.getColonne(), 2);
        assertEquals(p1.getRangee(), 2);
        assertEquals(p1.toString(), "B2");
        p2 = Position.creer(2, 2); // B2
        assertTrue(p1.equals(p2));

        // test  positions avec les coordonnées differents
        p1 = Position.creer(Echiquier.Colonne.B, 1); // B1
        assertEquals(p1.getColonne(), 2);
        assertEquals(p1.getRangee(), 1);
        assertEquals(p1.toString(), "B1");

        p2 = Position.creer(1, 2); // A2
        assertEquals(p2.getColonne(), 1);
        assertEquals(p2.getRangee(), 2);
        assertEquals(p2.toString(), "A2");

        assertFalse(p1.equals(p2));

        p1 = Position.creer(Echiquier.Colonne.D, 2); // D2
        p2 = Position.creer(Echiquier.Colonne.D, 3); // D3
        assertFalse(p1.equals(p2));

        // test obtenir les coordonnées d'une position
        p1 = Position.creer(Echiquier.Colonne.H, 6);
        assertEquals(p1.getColonne(), 8);
        assertEquals(p1.getRangee(), 6);

        p2 = Position.creer(8, 6);
        assertEquals(p2.getColonne(), 8);
        assertEquals(p2.getRangee(), 6);
    }

    /**
     * Test les limites de l'Echiquier
     */
    public void testLimitesEchiquier() {
        p1 = Position.creer(1, 8); // posistion valide
        assertTrue(p1.estValide());
        p1 = Position.creer(1, 4); // posistion valide
        assertTrue(p1.estValide());
        p1 = Position.creer(5, 8); // posistion valide
        assertTrue(p1.estValide());
        p1 = Position.creer(0, 10); // posistion non valide
        assertFalse(p1.estValide());
        p1 = Position.creer(1, 0); // posistion non valide
        assertFalse(p1.estValide());
        p1 = Position.creer(11, 0); // posistion non valide
        assertFalse(p1.estValide());
    }

    /**
     * Test sur le hashcode d'une position
     */
    public void testHashCode() {
        p1 = Position.creer(1, 2);
        p2 = Position.creer(2, 1);
        assertNotSame(p1.hashCode(), p2.hashCode()); // Differents

        p1 = Position.creer(8, 8);
        p2 = Position.creer(1, 1);
        assertNotSame(p1.hashCode(), p2.hashCode()); // Differents

        p1 = Position.creer(4, 2);
        p2 = Position.creer(2, 4);
        assertNotSame(p1.hashCode(), p2.hashCode()); // Differents

        p1 = Position.creer(5, 5);
        p2 = Position.creer(5, 1);
        assertNotSame(p1.hashCode(), p2.hashCode()); // Differents

        p1 = Position.creer(1, 1);
        p2 = Position.creer(Echiquier.Colonne.A, 1);
        assertEquals(p1.hashCode(), p2.hashCode());

        p1 = Position.creer(1, 2);
        p2 = Position.creer(1, 2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    /**
     * Test la fonction equals des positions
     */
    public void testEquals() {
        p1 = Position.creer(1, 2);
        p2 = Position.creer(2, 1);
        assertFalse(p1.equals(p2)); // Differents

        p1 = Position.creer(8, 8);
        p2 = Position.creer(1, 1);
        assertFalse(p1.equals(p2)); // Differents

        p1 = Position.creer(4, 1);
        p2 = Position.creer(1, 4);
        assertFalse(p1.equals(p2)); // Differents

        p1 = Position.creer(5, 5);
        p2 = Position.creer(5, 1);
        assertFalse(p1.equals(p2)); // Differents

        p1 = Position.creer(1, 1);
        p2 = Position.creer(Echiquier.Colonne.A, 1);
        assertTrue(p1.equals(p2));

        p1 = Position.creer(1, 2);
        p2 = Position.creer(1, 2);
        assertTrue(p1.equals(p2));
    }

    /**
     * Test la valeur string d'une position
     */
    public void testToString() {
        p1 = Position.creer(1, 2);
        assertEquals(p1.toString(), "A2");

        p1 = Position.creer(1, 1);
        assertEquals(p1.toString(), "A1");

        p1 = Position.creer(8, 3);
        assertEquals(p1.toString(), "H3");

        p1 = Position.creer(Echiquier.Colonne.C, 2);
        assertEquals(p1.toString(), "C2");

        p1 = Position.creer(7, 8);
        assertEquals(p1.toString(), "G8");
    }
}