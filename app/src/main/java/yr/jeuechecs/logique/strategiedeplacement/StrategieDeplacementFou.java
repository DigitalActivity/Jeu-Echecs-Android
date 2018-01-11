package yr.jeuechecs.logique.strategiedeplacement;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import yr.jeuechecs.logique.Echiquier;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.Piece;

/**
 * Classe StrategieDeplacementFou
 */
public class StrategieDeplacementFou extends StrategieDeplacement {
    /**
     * Constructeur Déplacement
     * @param p_coul couleur de la piece.
     */
    public StrategieDeplacementFou(Piece.Couleur p_coul) {
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

        // haut à gauche tant qu'aucun obstacle ou limite n'est rencontré
        for (int i = 1; i <= Echiquier.NB_RANGEES; i++) {
            p = Position.creer(m_posActuelle.getColonne() - i, m_posActuelle.getRangee() + i);
            if (m_tableJeu.containsKey(p) && p != m_posActuelle) {
                if (!m_couleur.equals(m_tableJeu.get(p).getCouleur())) {
                    deplacements.add(p);
                }
                break;
            }
            deplacements.add(p);
        }
        // bas à gauche tant qu'aucun obstacle ou limite n'est rencontré
        for (int i = 1; i <= Echiquier.NB_RANGEES; i++) {
            p = Position.creer(m_posActuelle.getColonne() - i, m_posActuelle.getRangee() - i);
            if (m_tableJeu.containsKey(p) && p != m_posActuelle) {
                if (!m_couleur.equals(m_tableJeu.get(p).getCouleur())) {
                    deplacements.add(p);
                }
                break;
            }
            deplacements.add(p);
        }
        // haut à droite tant qu'aucun obstacle ou limite n'est rencontré
        for (int i = 1; i <= Echiquier.NB_RANGEES; i++) {
            p = Position.creer(m_posActuelle.getColonne() + i, m_posActuelle.getRangee() + i);
            if (m_tableJeu.containsKey(p) && p != m_posActuelle) {
                if (!m_couleur.equals(m_tableJeu.get(p).getCouleur())) {
                    deplacements.add(p);
                }
                break;
            }
            deplacements.add(p);
        }
        // bas à droite tant qu'aucun obstacle ou limite n'est rencontré
        for (int i = 1; i <= Echiquier.NB_RANGEES; i++) {
            p = Position.creer(m_posActuelle.getColonne() + i, m_posActuelle.getRangee() - i);
            if (m_tableJeu.containsKey(p) && p != m_posActuelle) {
                if (!m_couleur.equals(m_tableJeu.get(p).getCouleur())) {
                    deplacements.add(p);
                }
                break;
            }
            deplacements.add(p);
        }

        deplacements.remove(m_posActuelle); // Enlever la position actuelle des positions possibles
        Iterator<Position> iter = deplacements.iterator();
        while (iter.hasNext()) {
            if (!iter.next().estValide()) {
                iter.remove();
            }
        }

        return deplacements;
    }
}
