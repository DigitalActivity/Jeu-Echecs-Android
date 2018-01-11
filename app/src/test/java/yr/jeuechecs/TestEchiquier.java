package yr.jeuechecs;

import yr.jeuechecs.logique.Echiquier;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.Piece;
import yr.jeuechecs.logique.piece.Pion;
import yr.jeuechecs.logique.piece.Reine;
import yr.jeuechecs.logique.piece.Roi;
import yr.jeuechecs.logique.piece.Tour;
import junit.framework.TestCase;

/**
 * Classe TestEchiquier : Contient les tests sur l'échiquier
 * @author Rabdi Younes
 * @see yr.jeuechecs.logique.Echiquier
 * @see yr.jeuechecs.logique.piece.Piece
 */
public class TestEchiquier extends TestCase {
    private Echiquier echiquier;

    public void setUp() {
        echiquier = Echiquier.Instance();
        echiquier.initialiser();
    }

    /**
     * Test d'initialisation
     */
    public void testEchiquier() {
        // Test nombre de pièces sur l'échiquier
        assertEquals(echiquier.obtenirNombrePieces(), 32);
        assertEquals(echiquier.obtenirNombrePieces(Pion.class, Piece.Couleur.BLANC), 8);
        assertEquals(echiquier.obtenirNombrePieces(Pion.class, Piece.Couleur.NOIR), 8);
        assertEquals(echiquier.obtenirNombrePieces(Reine.class, Piece.Couleur.NOIR), 1);
        assertEquals(echiquier.obtenirNombrePieces(Roi.class, Piece.Couleur.BLANC), 1);

        // Test piece Selon Position
        assertEquals(echiquier.obtenirPieceSelonPosition(Position.creer(Echiquier.Colonne.D, 1)).getClass(), Reine.class);
        assertEquals(echiquier.obtenirPieceSelonPosition(Position.creer(Echiquier.Colonne.E, 8)).getClass(), Roi.class);
        assertEquals(echiquier.obtenirPieceSelonPosition(Position.creer(Echiquier.Colonne.A, 2)).getClass(), Pion.class);
        assertEquals(echiquier.obtenirPieceSelonPosition(Position.creer(Echiquier.Colonne.A, 2)).getCouleur(), Piece.Couleur.BLANC);
        assertEquals(echiquier.obtenirPieceSelonPosition(Position.creer(Echiquier.Colonne.A, 2)).getValeur(), 1.0f);

        // Test piece Selon Position, colonne en format numérique (surcharge de methode)
        assertEquals(echiquier.obtenirPieceSelonPosition(Position.creer(4, 1)).getClass(), Reine.class); // D1
        assertEquals(echiquier.obtenirPieceSelonPosition(Position.creer(5, 8)).getClass(), Roi.class); // E8
        assertEquals(echiquier.obtenirPieceSelonPosition(Position.creer(1, 2)).getClass(), Pion.class);  // A2

        // Test Piece selon position
        Piece p = Pion.creer(Piece.Couleur.BLANC);
        Position pos = Position.creer(Echiquier.Colonne.A, 3);
        echiquier.positionnerPieceSiPossible(pos, p);
        assertEquals(echiquier.obtenirPieceSelonPosition(pos), p);
    } // FIN test echiquier

    /**
     * Test d'affichage de l'échiquier après l'initialisation
     */
    public void testAffichageEchiquier() {
        Position pos;
        Piece p;

        for (int rangee = Echiquier.NB_RANGEES; rangee >= 1 ; rangee--) {
            for (int colonne = 1; colonne <= Echiquier.NB_COLONNES; colonne++) {
                pos = Position.creer(colonne, rangee);
                p = echiquier.obtenirPieceSelonPosition(pos);
                if (p != null) {
                    p.afficher();
                }
                else {
                    System.out.print('X');
                }
            }
            System.out.println();
        }
    } // FIN test affichage

    /**
     * Test des positions des pieces dans l'echiquier
     */
    public void testPositionnement() {
        // test positionnement d'une nouvelle pièce sur l'échiquier
        Piece p = Pion.creer(Piece.Couleur.BLANC);
        Position posOriginal = Position.creer(Echiquier.Colonne.A, 5);
        Position nouvellePos = Position.creer(Echiquier.Colonne.A, 6);
        echiquier.positionnerPieceSiPossible(posOriginal, p); // Nouvelle pièce ajoutée
        assertEquals(echiquier.obtenirPieceSelonPosition(posOriginal), p);

        // test positionnement d'une pièce existante dans l'échiquier.
        echiquier.positionnerPieceSiPossible(nouvellePos, p); // La pièce est déplacée vers la nouvelle position
        assertEquals(echiquier.obtenirPieceSelonPosition(posOriginal), null);
        assertEquals(echiquier.obtenirPieceSelonPosition(nouvellePos), p);
    } // FIN test placement

    /**
     * Test d'evaluation des forces
     */
    public void testEvaluationDesForces() {
        // test score des joueurs à l'initialisation
        assertEquals(echiquier.evaluationDesForces(Piece.Couleur.BLANC), 38.0f);
        assertEquals(echiquier.evaluationDesForces(Piece.Couleur.NOIR), 38.0f);
    } // FIN test evaluation des forces

    /**
     * Test Detecter Echec
     */
    public void testdetecterEchec() {
        echiquier.get_tableJeu().clear();

        Piece roiBlanc = Roi.creer(Piece.Couleur.BLANC);
        roiBlanc.setTableDuJeu(echiquier.get_tableJeu());
        Piece tourBlanche = Tour.creer(Piece.Couleur.BLANC);
        tourBlanche.setTableDuJeu(echiquier.get_tableJeu());
        Piece tourNoir = Tour.creer(Piece.Couleur.NOIR);
        tourNoir.setTableDuJeu(echiquier.get_tableJeu());

        echiquier.positionnerPieceSiPossible(Position.creer(1,1), roiBlanc); // Roi blanc sur A1
        assertEquals(Echiquier.detecterEchec(echiquier.get_tableJeu()), 0); // personne n'est en echec
        echiquier.positionnerPieceSiPossible(Position.creer(1,8), tourNoir); // Tour noir sur A8
        assertEquals(Echiquier.detecterEchec(echiquier.get_tableJeu()), 1); // Roi blanc en echec
        assertTrue(echiquier.positionnerPieceSiPossible(Position.creer(1,3), tourBlanche)); // Proteger le roi blanc
        assertEquals(Echiquier.detecterEchec(echiquier.get_tableJeu()), 0); // personne n'est en echec
    }

    /**
     * Test Detecter si deplacement cause echec
     */
    public void testDetecterDeplacementCauseEchec() {
        echiquier.get_tableJeu().clear();

        Piece roiBlanc = Roi.creer(Piece.Couleur.BLANC);
        roiBlanc.setTableDuJeu(echiquier.get_tableJeu());
        Piece tourBlanche = Tour.creer(Piece.Couleur.BLANC);
        tourBlanche.setTableDuJeu(echiquier.get_tableJeu());
        Piece tourNoir = Tour.creer(Piece.Couleur.NOIR);
        tourNoir.setTableDuJeu(echiquier.get_tableJeu());

        echiquier.positionnerPieceSiPossible(Position.creer(1,1), roiBlanc); // Roi blanc sur A1
        assertEquals(Echiquier.detecterEchec(echiquier.get_tableJeu()), 0); // personne n'est en echec
        echiquier.positionnerPieceSiPossible(Position.creer(1,8), tourNoir); // Tour noir sur A8
        assertEquals(Echiquier.detecterEchec(echiquier.get_tableJeu()), 1); // Roi blanc en echec
        assertTrue(echiquier.positionnerPieceSiPossible(Position.creer(1,3), tourBlanche)); // Proteger le roi blanc
        assertFalse(tourNoir.obtenirPosPossiblesDeBase().contains(Position.creer(1,1)));
        assertEquals(Echiquier.detecterEchec(echiquier.get_tableJeu()), 0); // personne n'est en echec
        assertTrue(tourNoir.posEstPossible(Position.creer(1,3))); // tour noir peut capturer tour blanche
        assertFalse(tourNoir.posEstPossible(Position.creer(1,2))); // tous noir ne peut depacer tour blanche
        assertTrue(echiquier.positionnerPieceSiPossible(Position.creer(1,3), tourNoir)); // capturer la piece blanche et mettre le roi en echec
        assertEquals(Echiquier.detecterEchec(echiquier.get_tableJeu()), 1);  // Roi blanc en echec
        assertFalse(roiBlanc.posEstPossible(Position.creer(1,2))); // roi ne peut plus se deplacer vers A2
    }

    /**
     * Test detecter echec et mat
     */
    public void testdetecterEchecEtMat() {
        echiquier.get_tableJeu().clear();

        Piece roiBlanc = Roi.creer(Piece.Couleur.BLANC);
        roiBlanc.setTableDuJeu(echiquier.get_tableJeu());
        Piece tourNoir = Tour.creer(Piece.Couleur.NOIR);
        tourNoir.setTableDuJeu(echiquier.get_tableJeu());
        Piece tourNoir2 = Tour.creer(Piece.Couleur.NOIR);
        tourNoir2.setTableDuJeu(echiquier.get_tableJeu());

        echiquier.positionnerPieceSiPossible(Position.creer(1,1), roiBlanc); // Roi blanc sur A1
        assertEquals(Echiquier.detecterEchec(echiquier.get_tableJeu()), 0); // personne n'est en echec
        echiquier.positionnerPieceSiPossible(Position.creer(1,8), tourNoir); // Tour noir sur A8
        // roi blanc en echec
        assertEquals(Echiquier.detecterEchec(echiquier.get_tableJeu()), 1);
        // Roi blanc pas en echec et mat
        assertEquals(echiquier.detecterEchecEtMat(echiquier.get_tableJeu()), 0);
        // Tour noir sur B8
        echiquier.positionnerPieceSiPossible(Position.creer(2,8), tourNoir2);
        // Roi blanc en echec et mat
        assertEquals(echiquier.detecterEchecEtMat(echiquier.get_tableJeu()), 1);
        // Ajouter une tour blanche sur B3
        Piece tourBlanche = Tour.creer(Piece.Couleur.BLANC);
        tourBlanche.setTableDuJeu(echiquier.get_tableJeu());
        echiquier.positionnerPieceSiPossible(Position.creer(2,3), tourBlanche);
        // roi blanc en echec
        assertEquals(Echiquier.detecterEchec(echiquier.get_tableJeu()), 1);
        // Roi blanc pas en echec et mat
        assertEquals(echiquier.detecterEchecEtMat(echiquier.get_tableJeu()), 0);
        // capturer la piece blanche et mettre le roi en echec et mat
        assertTrue(echiquier.positionnerPieceSiPossible(Position.creer(2,3), tourNoir2));
        // Roi blanc en echec
        assertEquals(Echiquier.detecterEchec(echiquier.get_tableJeu()), 1);
        // Roi blanc pas en echec et mat
        assertEquals(echiquier.detecterEchecEtMat(echiquier.get_tableJeu()), 1);
        // roi ne peut plus se deplacer vers A2
        assertFalse(roiBlanc.posEstPossible(Position.creer(1,2)));
    }

    /**
     * Test detecter echec et mat
     */
    public void testdetecterPat() {
        echiquier.get_tableJeu().clear();
        Piece roiNoir = Roi.creer(Piece.Couleur.NOIR);
        roiNoir.setTableDuJeu(echiquier.get_tableJeu());
        Piece roiBlanc = Roi.creer(Piece.Couleur.BLANC);
        roiBlanc.setTableDuJeu(echiquier.get_tableJeu());
        Piece tourNoir = Tour.creer(Piece.Couleur.NOIR);
        tourNoir.setTableDuJeu(echiquier.get_tableJeu());
        Piece tourNoir2 = Tour.creer(Piece.Couleur.NOIR);
        tourNoir2.setTableDuJeu(echiquier.get_tableJeu());

        echiquier.positionnerPieceSiPossible(Position.creer(8,8), roiNoir); // Roi blanc sur A1
        echiquier.positionnerPieceSiPossible(Position.creer(1,1), roiBlanc); // Roi blanc sur A1
        assertEquals(echiquier.detecterPat(echiquier.get_tableJeu()), 0);
        echiquier.positionnerPieceSiPossible(Position.creer(2,3), tourNoir); // tourNoir sur B3
        echiquier.positionnerPieceSiPossible(Position.creer(3,2), tourNoir2); // tourNoir2 sur C2

        assertEquals(echiquier.detecterPat(echiquier.get_tableJeu()), 1);
    }

    /**
     * Test prise en passant
     */
    public void testPriseEnPassant() {
        // joueur 1 : A4
        echiquier.positionnerPieceSiPossible(Position.creer(1,4), echiquier.obtenirPieceSelonPosition(Position.creer(1, 2)));
        // joueur 2 : H5
        echiquier.positionnerPieceSiPossible(Position.creer(8,5), echiquier.obtenirPieceSelonPosition(Position.creer(8, 7)));
        // joueur 1 : A5
        echiquier.positionnerPieceSiPossible(Position.creer(1,5), echiquier.obtenirPieceSelonPosition(Position.creer(1, 4)));
        // joueur 2 : B5
        echiquier.positionnerPieceSiPossible(Position.creer(2,5), echiquier.obtenirPieceSelonPosition(Position.creer(2, 7)));
        // joueur 1 (Possible de deplacer A5 vers B6) car pion B5 est vulnerable
        assertTrue(echiquier.obtenirPieceSelonPosition((Position.creer(1, 5))).posEstPossible(Position.creer(2, 6)));
        // joueur 1 joue une autre piece
        echiquier.positionnerPieceSiPossible(Position.creer(8,4), echiquier.obtenirPieceSelonPosition(Position.creer(8, 2)));
        // joueur 2 joue une piece
        echiquier.positionnerPieceSiPossible(Position.creer(7,5), echiquier.obtenirPieceSelonPosition(Position.creer(7, 7)));
        // joueur 1 ne peut plus deplacer A5 vers B6 car le pion B5 n'est plus vulnerable
        assertFalse(echiquier.obtenirPieceSelonPosition((Position.creer(1, 5))).posEstPossible(Position.creer(2, 6)));
    }
}