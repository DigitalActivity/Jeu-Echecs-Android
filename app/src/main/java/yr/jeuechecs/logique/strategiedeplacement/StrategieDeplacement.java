package yr.jeuechecs.logique.strategiedeplacement;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import yr.jeuechecs.logique.Echiquier;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.Piece;
import yr.jeuechecs.logique.piece.Roi;
import yr.jeuechecs.logique.piece.Tour;

/**
 * Classe abstraite Déplacement: Gestion des Strategies de déplacement
 * @author Younes Rabdi
 */
public abstract class StrategieDeplacement implements Deplacable, Cloneable {
    int m_cptDeplacements = -1; // Nombre de déplacements de la piece.
    Position m_posActuelle;
    Piece.Couleur m_couleur; // Couleur necessaire pour definir certains déplacements des pieces comme le pion qui ne peut qu'avancer
    // reference à la table du singleton echiquier
    LinkedHashMap<Position, Piece> m_tableJeu = Echiquier.Instance().get_tableJeu();

    /**
     * Obtenir position actuelle
     * @return Position
     */
    public Position get_posActuelle() {
        return m_posActuelle;
    }

    /**
     * Obtenir nombre de deplacements
     * @return nombre de deplacements
     */
    public int get_cptDeplacements() {
        return m_cptDeplacements;
    }

    /**
     * Mettre à jour la position de la piece. et incrementer le compteur des deplacements
     * @param p_posActuelle Position actuelle de la piece sur l'echiquier
     */
    public void setPosActuelle(Position p_posActuelle) {
        this.m_cptDeplacements++;
        this.m_posActuelle = p_posActuelle;
    }

    /**
     * Définir la table du jeu actuelle. (Table de l'echiquier par defaut)
     * Une piece prends en compte les pieces sur cette table pour définir ses déplacements possibles
     * @param p_tableJeu table du jeu
     */
    public void setTableJeu(LinkedHashMap<Position, Piece> p_tableJeu) {
        this.m_tableJeu = p_tableJeu;
    }

    /**
     * Vérifie si la position en parametre est une position possible pour la piece. position validée
     * @param p_destination position de destination
     * @return true si la position est possible, false autrement
     */
    @Override
    public boolean deplacementEstPossible(Position p_destination) {
        if (m_posActuelle == null) {
            // Si la piece n'a aucune position definie, elle peut accépter n'import quelle position valide
            return p_destination.estValide();
        }

        boolean causeEchec = false;
        // Verifier si piece cause echec seulement quand un echiquier existe
        if (m_tableJeu != null && !m_tableJeu.isEmpty())
            causeEchec = verifierSiDeplacementCauseEchec(m_tableJeu.get(m_posActuelle), p_destination);

        return obtenirDeplacementsBase().contains(p_destination) && p_destination.estValide() && !causeEchec;
    }

    /**
     * Verifie si le deplacement cause un echec contre le joueur en action
     * @param p_piece Piece a déplacer
     * @param p_posDestination position de destination
     * @return vrai si cause echec contre le joueur en action, faux sinon
     */
    private boolean verifierSiDeplacementCauseEchec(Piece p_piece, Position p_posDestination) {
        LinkedHashMap<Position, Piece> copieTableJeu = new LinkedHashMap<>();

        if (p_piece.getPosActuelle().equals(p_posDestination) || m_tableJeu.isEmpty())
            return false;

        for (Map.Entry<Position, Piece> p : m_tableJeu.entrySet()) {
            copieTableJeu.put(p.getKey(), p.getValue().clone());
            copieTableJeu.get(p.getKey()).setTableDuJeu(copieTableJeu);
        }

        // la piece pour simuler sont deplacement
        Piece pClone;
        try {
            pClone = copieTableJeu.get(p_piece.getPosActuelle());
            // Si roque simuler le deplacement et verifier si cause echec
            if(pClone.getClass().equals(Roi.class) &&
                    copieTableJeu.get(p_posDestination) != null &&
                    copieTableJeu.get(p_posDestination).getClass().equals(Tour.class) &&
                    copieTableJeu.get(p_posDestination).getCouleur().equals(pClone.getCouleur())) {
                StrategieDeplacementRoi.appliquerDeplacementRoque(p_posDestination, pClone, copieTableJeu);
            }
            else Echiquier.Instance().positionnerPiece(p_posDestination, pClone, copieTableJeu);
        }
        catch (Exception e) {
            return false;
        }
        // 1:blancs en echec; 2:noirs en echec; 3:les deux en echec (pas supposé se produire)
        int estEnEchec = Echiquier.detecterEchec(copieTableJeu);

        boolean joueurEnEchec;
        if (m_couleur == Piece.Couleur.BLANC) {
            joueurEnEchec = estEnEchec == 1 || estEnEchec == 3; // joueurBlancs en echec
        }
        else {
            joueurEnEchec = estEnEchec >= 2; // joueurNoirs en echec
        }
        return joueurEnEchec;
    }

    /**
     * Toutes les positions possibles pour cette piece. Positions Valides. (sans verifier l'echec)
     * @return Set de toutes les positions valides
     */
    @Override
    public abstract Set<Position> obtenirDeplacementsBase();

    /**
     * Clonner la strategie de deplacement
     * @return StrategieDeplacement
     */
    @Override
    public StrategieDeplacement clone()	{
        try {
            return (StrategieDeplacement) super.clone();
        }
        catch	(CloneNotSupportedException e)	{
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}