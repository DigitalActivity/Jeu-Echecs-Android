package yr.jeuechecs.testpiece;

import yr.jeuechecs.logique.piece.Piece;
import junit.framework.TestSuite;

/**
 * TestPiece Suite : Contient les tests sur les pièces
 * @author Rabdi Younes
 * @see Piece
 */
public class TestPiece {
    public static TestSuite suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(TestPion.class);
        suite.addTestSuite(TestFou.class);
        suite.addTestSuite(TestTour.class);
        suite.addTestSuite(TestCavalier.class);
        suite.addTestSuite(TestRoi.class);
        suite.addTestSuite(TestReine.class);
        return suite;
    }
}