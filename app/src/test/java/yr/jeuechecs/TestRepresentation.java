package yr.jeuechecs;

import yr.jeuechecs.logique.piece.Piece;
import yr.jeuechecs.logique.representation.Representation;
import yr.jeuechecs.logique.representation.RepresentationPion;
import junit.framework.TestCase;

/**
 * Classe TestRepresentation : Contient les tests sur représentations des pieces
 * @see Representation
 */
public class TestRepresentation extends TestCase {
    public void testRepresentation() {
        Representation repPion = new RepresentationPion(Piece.Couleur.BLANC);
        assertEquals(repPion.getClass(), RepresentationPion.class);
        //assertEquals(repPion.m_couleur, Piece.Couleur.BLANC);

        //Representation repReine = new RepresentationReine(Piece.Couleur.NOIR);
        //assertEquals(repReine.m_couleur, Piece.Couleur.NOIR);

        //Representation repRoi = new RepresentationRoi(Piece.Couleur.NOIR);
        //assertEquals(repRoi.m_couleur, Piece.Couleur.NOIR);
    }
}