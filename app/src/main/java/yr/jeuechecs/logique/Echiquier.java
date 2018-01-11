package yr.jeuechecs.logique;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import yr.jeuechecs.logique.piece.Cavalier;
import yr.jeuechecs.logique.piece.Fou;
import yr.jeuechecs.logique.piece.Piece;
import yr.jeuechecs.logique.piece.Pion;
import yr.jeuechecs.logique.piece.Reine;
import yr.jeuechecs.logique.piece.Roi;
import yr.jeuechecs.logique.piece.Tour;
import static yr.jeuechecs.logique.piece.Piece.Couleur;

import yr.jeuechecs.logique.strategiedeplacement.StrategieDeplacementPion;
import yr.jeuechecs.logique.strategiedeplacement.StrategieDeplacementRoi;

/**
 * Classe Singleton Echiquier : Gestion de la Table de jeu.
 * Permet d'initialiser la table, positionner les pieces et enregistrer les déplacements.
 * @author Rabdi Younes
 */
public class Echiquier implements SujetPriseEnPassant {
    public final static int NB_RANGEES  = 8;
    public final static int NB_COLONNES = 8;
    private final LinkedHashMap<Position, Piece> m_tableJeu;
    private ArrayList<ObservateurPriseEnPassant> observPriseEnPassants; // Observers (pions) pour la prise en passant

    /**
     * Singleton Echiquier
     */
    private Echiquier() {
        m_tableJeu = new LinkedHashMap<>();
    }
    private static Echiquier instance = null;

    /**
     * Instance de l'aechiquier
     * @return Echiquier
     */
    public static Echiquier Instance() {
        if(instance == null) {
            instance = new Echiquier();
        }
        return instance;
    }

    /**
     * Obtenir table du jeu. pieces avec leur position
     * @return LinkedHashMap(Position, Piece)
     */
    public  LinkedHashMap<Position, Piece> get_tableJeu() {
        return m_tableJeu;
    }

    /**
     * Initialiser l'échiquier
     */
    public void initialiser() {
        m_tableJeu.clear(); // Vider l'échiquier
        observPriseEnPassants = new ArrayList<>();
        // Positionner les pions
        for (int colonne = 1; colonne <= NB_COLONNES; colonne++) {
            positionnerPiece(Position.creer(colonne, 2), Pion.creer(Couleur.BLANC), m_tableJeu); // Pions blancs sur rangee 2
            positionnerPiece(Position.creer(colonne, 7), Pion.creer(Couleur.NOIR), m_tableJeu);  // Pions noirs sur rangee 7
        }
        // Abonner les pions au sujet prise en passant
        for (Piece pion : m_tableJeu.values()) {
            abonnerPriseEnPassant((ObservateurPriseEnPassant) pion);
        }
        // Positionner les autres pièces
        positionnerPiece(Position.creer(Colonne.D, 1), Reine.creer(Couleur.BLANC), m_tableJeu);
        positionnerPiece(Position.creer(Colonne.E, 1), Roi.creer(Couleur.BLANC), m_tableJeu);
        positionnerPiece(Position.creer(Colonne.D, 8), Reine.creer(Couleur.NOIR), m_tableJeu);
        positionnerPiece(Position.creer(Colonne.E, 8), Roi.creer(Couleur.NOIR), m_tableJeu);

        positionnerPiece(Position.creer(Colonne.A, 1), Tour.creer(Couleur.BLANC), m_tableJeu);
        positionnerPiece(Position.creer(Colonne.H, 1), Tour.creer(Couleur.BLANC), m_tableJeu);
        positionnerPiece(Position.creer(Colonne.A, 8), Tour.creer(Couleur.NOIR), m_tableJeu);
        positionnerPiece(Position.creer(Colonne.H, 8), Tour.creer(Couleur.NOIR), m_tableJeu);

        positionnerPiece(Position.creer(Colonne.C, 1), Fou.creer(Couleur.BLANC), m_tableJeu);
        positionnerPiece(Position.creer(Colonne.F, 1), Fou.creer(Couleur.BLANC), m_tableJeu);
        positionnerPiece(Position.creer(Colonne.C, 8), Fou.creer(Couleur.NOIR), m_tableJeu);
        positionnerPiece(Position.creer(Colonne.F, 8), Fou.creer(Couleur.NOIR), m_tableJeu);

        positionnerPiece(Position.creer(Colonne.G, 1), Cavalier.creer(Couleur.BLANC), m_tableJeu);
        positionnerPiece(Position.creer(Colonne.B, 1), Cavalier.creer(Couleur.BLANC), m_tableJeu);
        positionnerPiece(Position.creer(Colonne.G, 8), Cavalier.creer(Couleur.NOIR), m_tableJeu);
        positionnerPiece(Position.creer(Colonne.B, 8), Cavalier.creer(Couleur.NOIR), m_tableJeu);
    } // FIN initialiser

    /**
     * Charger la table du jeu avec une table de l'historique
     * @param p_etatEchiquier Etat de l'echiquier
     */
    public void chargerTableJeu(LinkedHashMap<Position, Piece> p_etatEchiquier) {
        m_tableJeu.clear();
        observPriseEnPassants.clear();
        for (Map.Entry<Position, Piece> pos : p_etatEchiquier.entrySet()) {
            m_tableJeu.put(pos.getKey(), pos.getValue().clone());
            m_tableJeu.get(pos.getKey()).setTableDuJeu(m_tableJeu);
        }
        // Abonner les pions au sujet prise en passant
        for (Piece p : m_tableJeu.values()) {
            if(p.getClass().equals(Pion.class)) {
                abonnerPriseEnPassant((Pion)p);
            }
        }
    }

    /**
     * Verifié la strategie de déplacement de la pièce et placer/déplacer la piece si deplacement autorisé
     * @param p_posDestination position déstination
     * @param p_piece pièce à positionner
     * @return true si le positionnement est effectué, false sinon
     */
    public boolean positionnerPieceSiPossible(Position p_posDestination, Piece p_piece) {
        // positionnement non autorisé (roque et echec inclus)
        if (!p_piece.posEstPossible(p_posDestination)) {
            return false;
        }
        // verifier si le deplacement est par un pion et 2 cases en avant
        verifierVulnerabilitePriseEnPassant(p_posDestination, p_piece);

        // verifier si déplacement roque et l'appliquer
        if(verifierSiDeplacementRoque(p_posDestination, p_piece)) {
            return StrategieDeplacementRoi.appliquerDeplacementRoque(p_posDestination, p_piece, m_tableJeu);
        } // Verifier si Prise en passant et l'ppliquer
        else if(verifierSiDeplacementPriseEnPassant(p_posDestination, p_piece)) {
                return StrategieDeplacementPion.appliquerPriseEnPassant(p_posDestination,p_piece, m_tableJeu);
        }
        // sinon Deplacement normal
        return positionnerPiece(p_posDestination, p_piece, m_tableJeu);
    }

    /**
     * Placer/déplacer une piece à une position et l'enregistrer dans l'echiquier
     * Sans vérifier la stratégie de déplacement.
     * @param p_position position déstination
     * @param p_piece pièce à positionner
     * @return true si le positionnement est effectué, false sinon
     */
    public boolean positionnerPiece(Position p_position, final Piece p_piece, LinkedHashMap<Position, Piece> p_table) {
        try {
            // Enlever l'ancienne position si la pièce existe sur l'echiquier
            p_table.remove(p_piece.getPosActuelle());
            // mettre la nouvelle position de la piece dans l'echiquier
            p_table.put(p_position, p_piece);
            p_piece.setPosActuelle(p_position);
            return true; // positionnement effectué
        }
        catch (Exception e) {
            System.out.println("Erreur pendant un positionnement : " + e.getMessage());
        }
        return false; // positionnement non effectué
    }

    /**
     * Trouver si un joueur est en echec
     * @return 0:pas d'echec, 1:blancs en echec, 2:noirs en echec, 3:les deux en echec
     */
    public static int detecterEchec(LinkedHashMap<Position, Piece> p_echiquier) {
        if(p_echiquier.isEmpty()) {
            return 0;
        }

        int estEnEchec = 0;
        Position posRoiBlanc = null;
        Position posRoiNoir = null;
        Set<Position> posPossiblesBlancs = new HashSet<>(); // L'ensemble des positions possibles des pieces blanches
        Set<Position> posPossiblesNoirs = new HashSet<>(); // L'ensemble des positions possibles des pieces noirs
        // Trouver la position des deux rois et les positions possibles pour chaque couleur
        for (Map.Entry<Position, Piece> pos : p_echiquier.entrySet()) {
            switch (pos.getValue().getCouleur()) {
                case BLANC:
                    if(!pos.getValue().obtenirPosPossiblesDeBase().isEmpty()) {
                        posPossiblesBlancs.addAll(pos.getValue().obtenirPosPossiblesDeBase());
                    }
                    if (pos.getValue().getClass().equals(Roi.class))
                        posRoiBlanc = pos.getKey();
                    break;
                case NOIR:
                    if(!pos.getValue().obtenirPosPossiblesDeBase().isEmpty()) {
                        posPossiblesNoirs.addAll(pos.getValue().obtenirPosPossiblesDeBase());
                    }
                    if (pos.getValue().getClass().equals(Roi.class))
                        posRoiNoir = pos.getKey();
                    break;
            }
        }

        if (posRoiBlanc != null) {
            if (posPossiblesNoirs.contains(posRoiBlanc)) {
                estEnEchec = 1; // couleur blanc en echec
            }
        }
        if (posRoiNoir != null) {
            if (posPossiblesBlancs.contains(posRoiNoir)) {
                estEnEchec += 2; // couleur noir en echec
            }
        }
        return estEnEchec;
    }

    /**
     * Trouver si un joueur est en echec et mat
     * Echec et mat c'est un echec et pat réunis
     * @return 0:pas d'echec et mat, 1:blancs en echec et mat, 2:noirs en echec et mat
     */
    public int detecterEchecEtMat(LinkedHashMap<Position, Piece> p_echiquier) {
        if(p_echiquier.isEmpty()) {
            return 0;
        }
        if(detecterEchec(p_echiquier) != 0) {
            return detecterPat(p_echiquier);
        }
        return 0;
    }

    /**
     * Detecter Pat
     * @param p_echiquier echiquier à verifier
     * @return numero du joueur en pat, 0 sinon
     */
    public int detecterPat(LinkedHashMap<Position, Piece> p_echiquier) {
        if(p_echiquier.isEmpty()) {
            return 0;
        }
        boolean blancsEnPat = true;
        boolean noirsEnPat =  true;
        for (Piece p : p_echiquier.values()) {
            for (Position pos : p.obtenirPosPossiblesDeBase()) {
                // position est possible seulement quand elle ne cause pas d'Echec
                if (p.estBlanc() && p.posEstPossible(pos)) {
                    // Si au moins une position est possible, il n'y a plus d'echc et mat
                    blancsEnPat = false;
                    break;
                }
                else if (p.estNoir() && p.posEstPossible(pos)) {
                    noirsEnPat = false;
                    break;
                }
            }
        }
        int estEnEchec = 0;
        if(blancsEnPat) {
            estEnEchec = 1;
        }
        if(noirsEnPat) {
            estEnEchec += 2;
        }
        return estEnEchec;
    }

    /**
     * S'abbonner au sujet Prise en passant,
     *  recevoir des notifications sur les pions vulnérables à la prise en passant
     * @param o ObservateurPriseEnPassant
     */
    @Override
    public void abonnerPriseEnPassant(ObservateurPriseEnPassant o) {
        observPriseEnPassants.add(o);
    }

    /**
     * Se désabonner du sujet prise en passant
     * @param o ObservateurPriseEnPassant
     */
    @Override
    public void desabonnerPriseEnPassant(ObservateurPriseEnPassant o) {
        observPriseEnPassants.remove(observPriseEnPassants.indexOf(o));
    }

    /**
     * Notifier les abonnée de la nouvelle situation
     * @param p_posPionVulnerable position du pion vulnérable
     * @param p_couleur couleur du pion vulnérable
     */
    @Override
    public void notifierPriseEnPassant(Position p_posPionVulnerable, Couleur p_couleur) {
        for( ObservateurPriseEnPassant p : observPriseEnPassants) {
            p.mettreÀJour(p_posPionVulnerable, p_couleur);
        }
    }

    /**
     * Colonne : Lettre et valeur numérique assossiées à chaque colonne de la table.
     *       Les colonnes contiennent les valeurs associées à chaqu'une d'elles
     */
    public enum Colonne {
        A(1),
        B(2),
        C(3),
        D(4),
        E(5),
        F(6),
        G(7),
        H(8);

        public int getNum() { return m_num; }
        private final int m_num; // Numéro assossié à une colonne
        Colonne(int p_numColonne) { m_num = p_numColonne; }
    } // Fin colonne

    /**
     * Verifier si le deplacement est une prise en passant
     * @param p_destination position de destination
     * @param p_piece piece à deplacer
     * @return
     */
    private boolean verifierSiDeplacementPriseEnPassant(Position p_destination, Piece p_piece) {
        if(p_piece.getClass().equals(Pion.class)) {
            Position pp = Position.creer(p_destination.getColonne(),
                    p_destination.getRangee() + (p_piece.estBlanc() ? -1 : 1));

            return pp.estValide() && obtenirPieceSelonPosition(pp) != null &&
                    obtenirPieceSelonPosition(pp).getClass().equals(Pion.class) &&
                    obtenirPieceSelonPosition(pp).getNombreDeplacements() == 1 &&
                    obtenirPieceSelonPosition(pp).getPosActuelle().getRangee() ==
                            (obtenirPieceSelonPosition(pp).estBlanc() ? 4 : 5);
        }
        else return false;
    }

    /**
     * verifier si deplacement est un pion et 2 cases en avant, et notifier les abonnées
     * @param p_posDestination position de déstination de la piece
     * @param p_piece piece à déplacer
     */
    private void verifierVulnerabilitePriseEnPassant(Position p_posDestination, Piece p_piece) {
        // Notifier les abonnées. (aucun n'est vulnerable)
        notifierPriseEnPassant(null, null);
        // si Pion avance 2 cases , il devient vulnérable à la prise en passant
        if(p_piece.getClass().equals(Pion.class) &&
                p_piece.getNombreDeplacements() == 0 &&
                p_posDestination.getRangee() == (p_piece.estBlanc() ? 4 : 5)) {
            // Notifier les pions de la position du pion vulnerable
            notifierPriseEnPassant(p_posDestination, p_piece.getCouleur()); // Notifier les abonnées
        }
    }


    private boolean verifierSiDeplacementRoque(Position p_destination, Piece p_piece) {
        return p_piece.getClass().equals(Roi.class) &&
                obtenirPieceSelonPosition(p_destination) != null &&
                obtenirPieceSelonPosition(p_destination).getClass().equals(Tour.class) &&
                obtenirPieceSelonPosition(p_destination).getCouleur().equals(p_piece.getCouleur());
    }

    /**
     * Pièce selon la position en paramètre
     * (Pour tests seulement, pas utilisé dans la logique)
     * @param p_position position de la pièce à retourner
     * @return piece ou null si aucune piece ne se trouve à la position indiquée
     */
    public Piece obtenirPieceSelonPosition(Position p_position) {
        return m_tableJeu.get(p_position);
    }

    /**
     * Obtenir le nombre de pièces selon type et couleur en paramètres
     * (Pour tests seulement, pas utilisé dans la logique)
     * @param p_typePiece type de la pièce
     * @param p_couleur couleur de la pièce
     * @return nombre de pièces trouvées réspectants les critères
     */
    public int obtenirNombrePieces(Class p_typePiece, Couleur p_couleur) {
        int compteur = 0;
        for (Piece p : m_tableJeu.values()) {
            if (p.getClass().equals(p_typePiece) && p_couleur.equals(p.getCouleur())) {
                compteur++; // Incrémenter quand critères trouvées
            }
        }
        return compteur;
    }

    /**
     * Obtenir le nombre de pièces sur l'échiquier
     * @return nombre total des piéces sur l'échiquier
     */
    public int obtenirNombrePieces() {
        return m_tableJeu.size();
    }

    /**
     * Évaluation des forces
     * @param p_couleur couleur des pièces à évaluer
     * @return somme des pièces
     */
    public float evaluationDesForces(Couleur p_couleur) {
        float somme = 0.0f;
        for (Piece p : m_tableJeu.values()) {
            if (p.getCouleur().equals(p_couleur)) {
                somme += p.getValeur();
            }
        }
        return somme;
    }
}