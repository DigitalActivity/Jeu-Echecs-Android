package yr.jeuechecs.logique;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import yr.jeuechecs.logique.piece.Piece;

/**
 * Classe partie : Controleur du jeu. Intermediaire de communication entre l'echiquier et l'interface.
 *  Permet de jouer à tours de role, mettre à jour les scores, historique et communiquer avec l'echiquier.
 */
public class Partie {
    private final Joueur m_joueurBlanc;
    private final Joueur m_joueurNoir;
    private Joueur m_prochainÀJouer;
    private final Echiquier m_echiquier;
    private final ArrayList<LinkedHashMap<Position, Piece>> m_historique; // Liste des etats de l'echiquier
    public int indexHistorique = 0;
    private boolean historiqueUtilise = false;

    // Getters
    public Joueur getProchainÀJouer() { return m_prochainÀJouer; }
    public Joueur getJoueurBlanc()    { return m_joueurBlanc; }
    public Joueur getJoueurNoir()     { return m_joueurNoir; }
    public float  getForceBlanc()     { return m_echiquier.evaluationDesForces(Piece.Couleur.BLANC); }
    public float  getForceNoir()      { return m_echiquier.evaluationDesForces(Piece.Couleur.NOIR); }
    public LinkedHashMap<Position, Piece> obtenirTableJeu() { return m_echiquier.get_tableJeu(); } // liste des positions
    public ArrayList<LinkedHashMap<Position, Piece>> getHistorique() { return m_historique; } // liste des ensembles de positions avant chaque déplacement

    /**
     * Constructeur partie
     * @param p_nomJoueur1 nom jour 1
     * @param p_nomJoueur2 nom jour 2
     */
    public Partie(String p_nomJoueur1, String p_nomJoueur2) {
        m_historique = new ArrayList<>();
        m_joueurBlanc = new Joueur(p_nomJoueur1, Piece.Couleur.BLANC);
        m_joueurNoir = new Joueur(p_nomJoueur2, Piece.Couleur.NOIR);
        m_echiquier = Echiquier.Instance();
        m_echiquier.initialiser();
        m_historique.add(clonerTableJeu()); // Premier enregistrement dans l'historique c'est la table initiale
        m_prochainÀJouer = m_joueurBlanc;
    }

    /**
     * Permet de faire jouer à tour de rôle.
     * Appeler cette methode quand un joueur selectionne l'une de ses pieces et choisie une position de destination
     * @param p_posPiece position de la piece à déplacer
     * @param p_destination position de destination
     * @return true si le mouvement est valide
     */
    public boolean jouer(Position p_posPiece, Position p_destination) {
        // Ne rien faire si aucune piece n'est choisie (juste au cas ou, mais n'est pas supposé se produire)
        if (m_echiquier.obtenirPieceSelonPosition(p_posPiece) == null) {
            return false;
        }
        // si joueur a choisi une piece lui appartenant
        if (m_prochainÀJouer.getCouleur().equals(m_echiquier.obtenirPieceSelonPosition(p_posPiece).getCouleur())) {
            // Si mouvement est effectué
            if (m_echiquier.positionnerPieceSiPossible(p_destination, m_echiquier.obtenirPieceSelonPosition(p_posPiece))) {
                // Ajuster index de l'historique ( ne pas permettre d'aller en avant aprés avoir fait un nouveau déplacement
                if(historiqueUtilise) {
                    ajusterIndexHistorique();
                }
                indexHistorique = m_historique.size();
                // Mettre à jour le prochain à jouer
                m_prochainÀJouer = m_prochainÀJouer.equals(m_joueurBlanc) ? m_joueurNoir : m_joueurBlanc;
                // Sauvegarder la table du jeu
                m_historique.add(clonerTableJeu());
            }
            return true;
        }
        return false;
    }

    /**
     * Detecter un Echec
     * @return numero du joueur en echec, 0 sinon
     */
    public int detecterEchec() {
        return Echiquier.detecterEchec(m_echiquier.get_tableJeu());
    }

    /**
     * Detecter un Pat
     * @return numero du joueur en Pat, 0 sinon
     */
    public int detecterPat() {
        return m_echiquier.detecterPat(m_echiquier.get_tableJeu());
    }


    /**
     * Detecter echec et mat
     * @return numero du joueur en echec et mat, 0 sinon
     */
    public int detecterEchecEtMat() {
        return m_echiquier.detecterEchecEtMat(m_echiquier.get_tableJeu());
    }

    /**
     * Cloner la table du jeu. Deep copie
     * @return Clone de la table du jeu
     */
    public LinkedHashMap<Position, Piece> clonerTableJeu () {
        LinkedHashMap<Position, Piece> clone = new LinkedHashMap<>();
        for (Map.Entry<Position, Piece> pos : m_echiquier.get_tableJeu().entrySet()) {
            clone.put(pos.getKey(), pos.getValue().clone());
        }
        return clone;
    }

    /**
     * Ajuster index historique
     * Quand un joueur effectue un nouveau movement, il ne peut plus aller en avant dans l'historique
     */
    private void ajusterIndexHistorique() {
        if (m_historique.size() - 1 > indexHistorique && indexHistorique >= 0) {
            int i = m_historique.size() - 1;
            while( i > indexHistorique) {
                m_historique.remove(i);
                i--;
            }
        }
        historiqueUtilise = false;
    }

    /**
     * Charger une table de jeu à un etat anterieur/ultérieur
     * @param p_index index de l'etat  dans l'historique
     */
    public void chargerTableJeu(int p_index) {
        if (m_historique.size() > p_index && p_index >= 0 && !m_historique.isEmpty()) {
            m_echiquier.chargerTableJeu(m_historique.get(p_index));
            historiqueUtilise = true;
            // ajuster le prochain à jouer
            try {
                m_prochainÀJouer = indexHistorique % 2 == 0 ? m_joueurBlanc : m_joueurNoir;
            } catch (Exception e) {
                m_prochainÀJouer = m_joueurBlanc;
            }
        }
    }

    /**
     * Obtenir la piece sur la position en parametre
     * @param p_pos position de la piece à retourner
     * @return piece selon pos en parametre
     */
    public Piece obtenirPieceSelonPos(Position p_pos) {
        return m_echiquier.obtenirPieceSelonPosition(p_pos);
    }
}