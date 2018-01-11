package yr.jeuechecs;

import yr.jeuechecs.logique.Echiquier;
import yr.jeuechecs.logique.Partie;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.Piece;
import junit.framework.TestCase;

/**
 * Classe TestPartie : Contient les tests sur la class partie
 * @see Partie
 */
public class TestPartie extends TestCase {
    private Partie partie;

    public void setUp() {
        partie = new Partie("NomJ1", "NomJ2");
    }

    /**
     * test deroulement d'une partie
     */
    public void testPartie() {
        // Joueur Blancs commence toujours le premier
        assertEquals(Piece.Couleur.BLANC ,partie.getProchainÀJouer().getCouleur());
        assertEquals(partie.getJoueurBlanc().getNom(), "NomJ1");
        assertEquals(partie.getJoueurNoir().getNom(), "NomJ2");
        assertFalse(partie.getHistorique().isEmpty());
        assertEquals(partie.getHistorique().size(), 1); // 1 historique : table initiale
        // echiquier initialisé par le constructeur partie
        assertEquals(partie.obtenirTableJeu().size(), 32);

        // Joueur Blancs bougera le pion blanc
        partie.jouer(Position.creer(1, 2), Position.creer(1, 4));
        assertEquals(partie.obtenirTableJeu().get(Position.creer(1, 2)), null);
        assertEquals(partie.obtenirTableJeu().get(Position.creer(1, 4)), partie.obtenirPieceSelonPos(Position.creer(1, 4)));
        assertEquals(Piece.Couleur.NOIR ,partie.getProchainÀJouer().getCouleur());
        System.out.println("Pion blanc déplacé :");
        dessinerEchiquier();

        // Jour Noirs Ne bougera pas le pion blanc
        partie.jouer(Position.creer(1, 4), Position.creer(1, 5));
        assertEquals(partie.obtenirTableJeu().get(Position.creer(1, 5)), null);
        assertEquals(partie.obtenirTableJeu().get(Position.creer(1, 4)), partie.obtenirPieceSelonPos(Position.creer(1, 4)));
        assertEquals(Piece.Couleur.NOIR ,partie.getProchainÀJouer().getCouleur()); // Toujours le tours du jour noir à jouer
        System.out.println("Pion blanc Non déplacé car tour du jour pieces noirs:");
        dessinerEchiquier();
        // Joueur Noirs peut bouger le pion noir
        partie.jouer(Position.creer(1, 7), Position.creer(1, 6));
        assertEquals(partie.obtenirTableJeu().get(Position.creer(1, 7)), null);
        assertEquals(partie.obtenirTableJeu().get(Position.creer(1, 6)), partie.obtenirPieceSelonPos(Position.creer(1, 6)));
        assertEquals(Piece.Couleur.BLANC ,partie.getProchainÀJouer().getCouleur());
        System.out.println("Pion noir déplacé :");
        dessinerEchiquier();
    }

    /**
     * tests sur l'historique des deplacements dans une partie
     */
    public void testHistorique() {
        partie.jouer(Position.creer(1, 2), Position.creer(1, 4)); // Déplacer pion blanc
        assertEquals(partie.getHistorique().size(), 2); // l'état initial + 1 etat apres le déplacement precedent

        partie.jouer(Position.creer(1, 7), Position.creer(1, 6)); // Déplacer pion noir
        assertEquals(partie.getHistorique().size(), 3); // 1 état initial + 2 déplacements

        assertTrue(partie.getHistorique().get(0).containsKey((Position.creer(1, 2))));
        assertFalse(partie.getHistorique().get(0).containsKey((Position.creer(1, 4))));
        assertTrue(partie.getHistorique().get(1).containsKey((Position.creer(1, 4)))); // Déplacement pion blanc
        assertTrue(partie.getHistorique().get(2).containsKey((Position.creer(1, 6)))); // Suivi déplacment pion noir

        System.out.println("retour à l'etat apres 1 deplacement :");
        partie.chargerTableJeu(1);
        assertEquals(partie.obtenirTableJeu().get(Position.creer(1, 7)).getNombreDeplacements(), 0);
        assertTrue(partie.obtenirTableJeu().get(Position.creer(1, 7)).posEstPossible(Echiquier.Colonne.A, 5));
        dessinerEchiquier();

        System.out.println("retour à l'etat original :");
        partie.chargerTableJeu(0);
        assertEquals(partie.obtenirTableJeu().get(Position.creer(1, 2)).getNombreDeplacements(), 0);
        dessinerEchiquier();
    }

    /**
     * (pas un test) Affichage de l'échiquier à la console: Visualiser l'echiquier après les déplacements
     */
    private void dessinerEchiquier() {
        Position pos;
        Piece p;

        for (int rangee = Echiquier.NB_RANGEES; rangee >= 1 ; rangee--) {
            for (int colonne = 1; colonne <= Echiquier.NB_COLONNES; colonne++) {
                pos = Position.creer(colonne, rangee);
                p = partie.obtenirPieceSelonPos(pos);
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
}