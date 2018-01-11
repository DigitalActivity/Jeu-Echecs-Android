package yr.jeuechecs;

import junit.framework.TestSuite;

import yr.jeuechecs.testdeplacement.TestDeplacement;
import yr.jeuechecs.testpiece.TestPiece;

/**
 * Classe TestComplet : permet d'executer l'ensemble des tests
 * @author Rabdi Younes
 * @see TestDeplacement
 * @see TestEchiquier
 * @see TestPiece
 * @see TestPartie
 */
public class TestComplet {
    public static TestSuite suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(TestEchiquier.class);
        suite.addTestSuite(TestPartie.class);
        suite.addTestSuite(TestPosition.class);
        suite.addTestSuite(TestRepresentation.class);
        suite.addTest(TestDeplacement.suite());
        suite.addTest(TestPiece.suite());
        return suite;
    }
}