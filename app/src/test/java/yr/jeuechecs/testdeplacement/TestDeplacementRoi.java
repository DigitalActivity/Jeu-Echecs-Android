package yr.jeuechecs.testdeplacement;

import junit.framework.TestCase;
import yr.jeuechecs.logique.Echiquier;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.Piece;
import yr.jeuechecs.logique.piece.Roi;
import yr.jeuechecs.logique.piece.Tour;

/**
 * Test Strategie de deplacement Roi
 * @see yr.jeuechecs.logique.strategiedeplacement.StrategieDeplacementRoi
 */
public class TestDeplacementRoi extends TestCase {
    private Piece roi;
    private Position pos;
    private Echiquier echiquier; // Pour tester le grand et le petit roque

    public void setUp() {
        roi = Roi.creer(Piece.Couleur.BLANC);
        pos = Position.creer(Echiquier.Colonne.C, 3); // C3
        echiquier = Echiquier.Instance();
        echiquier.get_tableJeu().clear();
    }

    /**
     * Test les deplacements de base de Roi
     */
    public void testDeplacementRoi() {
        roi.setPosActuelle(pos); // setPosActuelle sur C3
        System.out.println("Déplacements possibles Roi sur " + pos.toString() + " :");
        for(Position p : roi.obtenirPosPossiblesDeBase()) {
            System.out.print(p.toString() + " | ");
        }
        System.out.println();
        assertTrue(roi.posEstPossible(Echiquier.Colonne.C, 4)); // avancer
        assertTrue(roi.posEstPossible(Echiquier.Colonne.C, 2)); // reculer
        assertTrue(roi.posEstPossible(Echiquier.Colonne.D, 3)); //  à droite
        assertTrue(roi.posEstPossible(Echiquier.Colonne.B, 3)); //  à gauche
        assertTrue(roi.posEstPossible(Echiquier.Colonne.D, 4)); // diagonal à gauche
        assertTrue(roi.posEstPossible(Echiquier.Colonne.B, 4)); // diagonal à droite
        assertTrue(roi.posEstPossible(Echiquier.Colonne.D, 2)); // diagonal bas à gauche
        assertTrue(roi.posEstPossible(Echiquier.Colonne.B, 2)); // diagonal bas à droite
        assertFalse(roi.posEstPossible(Echiquier.Colonne.C, 5)); // NON avancer 2 cases
        assertFalse(roi.posEstPossible(Echiquier.Colonne.C, 0)); // NON out
    }

    /**
     * Test de deplacement Roque de couleur blanc
     */
    public void testDeplacementRoqueBlanc() {
        echiquier.initialiser();
        Piece roiBlanc = echiquier.obtenirPieceSelonPosition(Position.creer(Echiquier.Colonne.E, 1));
        assertFalse(((Roi)roiBlanc).grandRoqueEstPossible()); // impossible car cases non vide entre le roi et la tour
        assertFalse(((Roi)roiBlanc).petitRoqueEstPossible()); // impossible car cases non vide entre le roi et la tour
        assertFalse(roiBlanc.posEstPossible(8,1));
        assertFalse(roiBlanc.posEstPossible(1,1));
        echiquier.get_tableJeu().remove(Position.creer(7, 1));
        echiquier.get_tableJeu().remove(Position.creer(6, 1));
        assertFalse(((Roi)roiBlanc).grandRoqueEstPossible()); // impossible car cases non vide entre le roi et la tour
        assertTrue(((Roi)roiBlanc).petitRoqueEstPossible()); // possible car cases vides entre le roi et la tour

        // effectuer le deplacement roque de la meme facon que n'import quelle autre piece, l'echiquier s'occupe de la logique
        assertTrue(echiquier.positionnerPieceSiPossible(Position.creer(8, 1), roiBlanc)); // petit roque
        assertEquals(echiquier.obtenirPieceSelonPosition(Position.creer(7,1)), roiBlanc);
        assertEquals(echiquier.obtenirPieceSelonPosition(Position.creer(6,1)).getClass(), Tour.class);
        assertFalse(((Roi)roiBlanc).grandRoqueEstPossible()); // impossible car déja effectué
        assertFalse(((Roi)roiBlanc).petitRoqueEstPossible()); // impossible car déja effectué

        // Repositionner les memes roi et tour et aux positions originals et réessayer d'effectuer un petit roque (Doit être impossible)
        echiquier.positionnerPieceSiPossible(Position.creer(Echiquier.Colonne.E, 1), roiBlanc);
        echiquier.positionnerPieceSiPossible(Position.creer(Echiquier.Colonne.H, 1), echiquier.obtenirPieceSelonPosition(Position.creer(6, 1)));
        assertFalse(((Roi)roiBlanc).petitRoqueEstPossible()); // impossible car roi et tour déja déplacés
        // essayer un grand roque (doit être impossible)
        echiquier.get_tableJeu().remove(Position.creer(2, 1)); // faire de la place
        echiquier.get_tableJeu().remove(Position.creer(3, 1));
        echiquier.get_tableJeu().remove(Position.creer(4, 1));
        assertFalse(((Roi)roiBlanc).grandRoqueEstPossible()); // impossible car roi déja déplacé

        echiquier.initialiser(); // Réinitialiser echiquier et essayer un grand roque en premier
        roiBlanc = echiquier.obtenirPieceSelonPosition(Position.creer(Echiquier.Colonne.E, 1));
        // essayer un grand roque (doit être possible)
        echiquier.get_tableJeu().remove(Position.creer(2, 1)); // faire de la place
        echiquier.get_tableJeu().remove(Position.creer(3, 1));
        echiquier.get_tableJeu().remove(Position.creer(4, 1));
        assertTrue(((Roi)roiBlanc).grandRoqueEstPossible()); // impossible car roi jamais déplacé
    }

    /**
     * Test de deplacement roque de couleur noir
     */
    public void testDeplacementRoqueNoir() {
        echiquier.initialiser();
        Piece roiNoir = echiquier.obtenirPieceSelonPosition(Position.creer(Echiquier.Colonne.E, 8));
        assertFalse(((Roi)roiNoir).grandRoqueEstPossible()); // impossible car cases non vide entre le roi et la tour
        assertFalse(((Roi)roiNoir).petitRoqueEstPossible()); // impossible car cases non vide entre le roi et la tour
        assertFalse(roiNoir.posEstPossible(8,8));
        assertFalse(roiNoir.posEstPossible(1,8));
        echiquier.get_tableJeu().remove(Position.creer(7, 8));
        echiquier.get_tableJeu().remove(Position.creer(6, 8));
        assertFalse(((Roi)roiNoir).grandRoqueEstPossible()); // impossible car cases non vide entre le roi et la tour
        assertTrue(((Roi)roiNoir).petitRoqueEstPossible()); // possible car cases vides entre le roi et la tour

        // effectuer le deplacement roque de la meme facon que n'import quel autre deplacement, l'echiquier s'occupe de la logique
        assertTrue(echiquier.positionnerPieceSiPossible(Position.creer(8, 8), roiNoir)); // petit roque
        assertEquals(echiquier.obtenirPieceSelonPosition(Position.creer(7,8)), roiNoir);
        assertEquals(echiquier.obtenirPieceSelonPosition(Position.creer(6,8)).getClass(), Tour.class);
        assertFalse(((Roi)roiNoir).grandRoqueEstPossible()); // impossible car déja effectué
        assertFalse(((Roi)roiNoir).petitRoqueEstPossible()); // impossible car déja effectué

        // Repositionner les memes roi et tour et aux positions originals et réessayer d'effectuer un petit roque (Doit être impossible)
        echiquier.positionnerPiece(Position.creer(Echiquier.Colonne.E, 8), roiNoir, echiquier.get_tableJeu());
        echiquier.positionnerPiece(Position.creer(Echiquier.Colonne.H, 8), echiquier.obtenirPieceSelonPosition(Position.creer(6, 8)), echiquier.get_tableJeu());
        assertFalse(((Roi)roiNoir).petitRoqueEstPossible()); // impossible car roi et tour déja déplacés
        // essayer un grand roque (doit etre impossible)
        echiquier.get_tableJeu().remove(Position.creer(2, 8)); // faire de la place
        echiquier.get_tableJeu().remove(Position.creer(3, 8));
        echiquier.get_tableJeu().remove(Position.creer(4, 8));
        assertFalse(((Roi)roiNoir).grandRoqueEstPossible()); // impossible car roi déja déplacé

        echiquier.initialiser(); // Réinitialiser echiquier et essayer un grand roque
        roiNoir = echiquier.obtenirPieceSelonPosition(Position.creer(Echiquier.Colonne.E, 8));
        // essayer un grand roque (doit etre possible)
        echiquier.get_tableJeu().remove(Position.creer(2, 8)); // faire de la place
        echiquier.get_tableJeu().remove(Position.creer(3, 8));
        echiquier.get_tableJeu().remove(Position.creer(4, 8));
        assertTrue(((Roi)roiNoir).grandRoqueEstPossible()); // possible car roi jamais déplacé
    }
}