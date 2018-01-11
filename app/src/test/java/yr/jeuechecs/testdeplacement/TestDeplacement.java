package yr.jeuechecs.testdeplacement;

import junit.framework.TestSuite;

/**
 * Test Suite : Tests sur les strategies de deplacement
 */
public class TestDeplacement {
    public static TestSuite suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(TestDeplacementPion.class);
        suite.addTestSuite(TestDeplacementFou.class);
        suite.addTestSuite(TestDeplacementTour.class);
        suite.addTestSuite(TestDeplacementCavalier.class);
        suite.addTestSuite(TestDeplacementRoi.class);
        suite.addTestSuite(TestDeplacementReine.class);
        return suite;
    }
}