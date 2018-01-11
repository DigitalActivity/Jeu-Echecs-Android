package yr.jeuechecs.logique.strategiedeplacement;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import yr.jeuechecs.logique.Echiquier;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.Piece;
import yr.jeuechecs.logique.piece.Pion;
import static yr.jeuechecs.logique.piece.Piece.Couleur;

/**
 * Classe Strategie Deplacement Pion
 */
public class StrategieDeplacementPion extends StrategieDeplacement {
    private Position posDuPionVulnerable = null;

    /**
     * Constructeur Déplacement
     * @param p_coul couleur de la piece.
     */
    public StrategieDeplacementPion(Couleur p_coul) {
        this.m_couleur = p_coul;
    }

    /**
     * Set de positions possibles pour cette pièce
     * @return Set des positions possibles
     */
    @Override
    public Set<Position> obtenirDeplacementsBase() {
        Set<Position> deplacements = new HashSet<>();
        Position p;
        // 2 m_casesAffichables possibles quand le premier mouvement
        if (m_cptDeplacements < 1) { // Premier déplacement
            p = Position.creer(m_posActuelle.getColonne(), m_posActuelle.getRangee() +
                    ((this.m_couleur == Couleur.BLANC) ? 2: -2));
            if(!m_tableJeu.keySet().contains(p) &&
                    !m_tableJeu.keySet().contains(Position.creer(m_posActuelle.getColonne(),
                            m_posActuelle.getRangee() + ((this.m_couleur == Couleur.BLANC) ? 1 : -1)))) {
                deplacements.add(p);
            }
        }

        p = Position.creer(m_posActuelle.getColonne() + 1, m_posActuelle.getRangee() +
                ((this.m_couleur == Couleur.BLANC) ? 1 : -1));
        if (m_tableJeu.keySet().contains(p) && !m_tableJeu.get(p).getCouleur().equals(m_couleur))
            deplacements.add(p);

        p = Position.creer(m_posActuelle.getColonne() - 1, m_posActuelle.getRangee() +
                ((this.m_couleur == Couleur.BLANC) ? 1 : -1));
        if (m_tableJeu.keySet().contains(p) && !m_tableJeu.get(p).getCouleur().equals(m_couleur))
            deplacements.add(p);

        p = Position.creer(m_posActuelle.getColonne(), m_posActuelle.getRangee() +
                ((this.m_couleur == Couleur.BLANC) ? 1 : -1));
        if (!m_tableJeu.keySet().contains(p)) // Si position n'est pas occupée
            deplacements.add(p);

        // verifier prises en passant
        p = Position.creer(m_posActuelle.getColonne() - 1, m_posActuelle.getRangee() +
                ((this.m_couleur == Couleur.BLANC) ? 1 : -1));
        if (p.estValide() && verifierPriseEnPassant(p))
            deplacements.add(p);
        p = Position.creer(m_posActuelle.getColonne() + 1, m_posActuelle.getRangee() +
                ((this.m_couleur == Couleur.BLANC) ? 1 : -1));
        if (p.estValide() && verifierPriseEnPassant(p))
            deplacements.add(p);

        Iterator<Position> iter = deplacements.iterator();
        while (iter.hasNext()) {
            if (!iter.next().estValide()) {
                iter.remove();
            }
        }

        return deplacements;
    }

    /**
     * Vérifier si prise en passant est possible
     * @param p_position position du pion actuel
     * @return vrai si un deplacement prise en passant est possible
     */
    private boolean verifierPriseEnPassant(Position p_position) {
        int rangee = p_position.getRangee() + (this.m_couleur == Couleur.BLANC ? -1 : 1);
        Position pos = Position.creer(p_position.getColonne(), rangee);
        return (m_tableJeu.containsKey(pos) &&
                m_tableJeu.get(pos).getClass().equals(Pion.class) &&
                pos.equals(posDuPionVulnerable));
    }

    /**
     * Appliquer la prise en passant
     * @param p_destination destination de la piece attaquante
     * @param p_pion pion attaquant
     * @param p_table table du jeu
     * @return Vrai si le mouvement est effectué
     */
    public static boolean appliquerPriseEnPassant(Position p_destination, Piece p_pion, LinkedHashMap<Position, Piece> p_table) {
        int rangee = p_destination.getRangee() + (p_pion.estBlanc() ? -1 : 1);
        Position posPionVulnerable = Position.creer(p_destination.getColonne(), rangee);

        if (p_table.containsKey(posPionVulnerable) &&
                p_table.get(posPionVulnerable).getClass().equals(Pion.class) &&
                !p_table.get(posPionVulnerable).getCouleur().equals(p_pion.getCouleur())) {
            p_table.remove(posPionVulnerable);
        }
        return Echiquier.Instance().positionnerPiece(p_destination, p_pion, p_table);
    }

    /**
     * Mettre à jour la position du pion vulnerable
     * @param p_positionPionVulnerable pos du pion vulnerable
     */
    public void mettreÀJourPionVulnerable(Position p_positionPionVulnerable) {
        posDuPionVulnerable = p_positionPionVulnerable;
    }
}