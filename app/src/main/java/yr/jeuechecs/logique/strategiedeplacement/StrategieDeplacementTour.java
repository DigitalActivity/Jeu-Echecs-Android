package yr.jeuechecs.logique.strategiedeplacement;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import yr.jeuechecs.logique.Echiquier;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.Piece;

/**
 * Classe StrategieDeplacementTour
 */
public class StrategieDeplacementTour extends StrategieDeplacement {
    /**
     * Constructeur Déplacement
     * @param p_coul couleur de la piece.
     */
    public StrategieDeplacementTour(Piece.Couleur p_coul) {
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

        // Horizontalement vers la droite
        for (int colonne = 1; colonne <= Echiquier.NB_COLONNES; colonne++) {
            p = Position.creer(m_posActuelle.getColonne() + colonne, m_posActuelle.getRangee());

            if (!p.estValide()) break;
            if (!m_tableJeu.containsKey(p) && p != m_posActuelle) {
                deplacements.add(p);
            } else if (m_tableJeu.containsKey(p) && !m_couleur.equals(m_tableJeu.get(p).getCouleur())) { // Position possible avec piece opposante
                deplacements.add(p);
                break;
            } else if (m_tableJeu.containsKey(p) && m_couleur.equals(m_tableJeu.get(p).getCouleur())) { // Limite echiquier ou piece meme couleur
                break;
            }
        }
        // Horizontalement vers la gauche
        for (int colonne = 1; colonne <= Echiquier.NB_COLONNES; colonne++) {
            p = Position.creer(m_posActuelle.getColonne() - colonne, m_posActuelle.getRangee());

            if (!p.estValide()) break;
            if (!m_tableJeu.containsKey(p) && p != m_posActuelle) {
                deplacements.add(p);
            } else if (m_tableJeu.containsKey(p) && !m_couleur.equals(m_tableJeu.get(p).getCouleur())) { // Position possible avec piece opposante
                deplacements.add(p);
                break;
            } else if (m_tableJeu.containsKey(p) && m_couleur.equals(m_tableJeu.get(p).getCouleur())) { // Limite echiquier ou piece meme couleur
                break;
            }
        }
        // Verticalement vers le haut
        for (int rangee = 1; rangee <= Echiquier.NB_RANGEES; rangee++) {
            p = Position.creer(m_posActuelle.getColonne(), m_posActuelle.getRangee() + rangee);

            if (!p.estValide()) break;

            if (!m_tableJeu.containsKey(p) && p != m_posActuelle) {
                deplacements.add(p);
            } else if (m_tableJeu.containsKey(p) && !m_couleur.equals(m_tableJeu.get(p).getCouleur())) { // Position possible avec piece opposante
                deplacements.add(p);
                break;
            } else if (m_tableJeu.containsKey(p) && m_couleur.equals(m_tableJeu.get(p).getCouleur())) { // Limite echiquier ou piece meme couleur
                break;
            }
        }
        // Verticalement vers le bas
        for (int rangee = 1; rangee <= Echiquier.NB_RANGEES; rangee++) {
            p = Position.creer(m_posActuelle.getColonne(), m_posActuelle.getRangee() - rangee);

            if (!p.estValide()) break;

            if (!m_tableJeu.containsKey(p) && p != m_posActuelle) {
                deplacements.add(p);
            } else if (m_tableJeu.containsKey(p) && !m_couleur.equals(m_tableJeu.get(p).getCouleur())) { // Position possible avec piece opposante
                deplacements.add(p);
                break;
            } else if (m_tableJeu.containsKey(p) && m_couleur.equals(m_tableJeu.get(p).getCouleur())) { // Limite echiquier ou piece meme couleur
                break;
            }
        }

        deplacements.remove(m_posActuelle); // Enlever la position actuelle des positions possibles
        //deplacements.removeIf(e -> !e.estValide());
        Iterator<Position> iter = deplacements.iterator();
        while (iter.hasNext()) {
            if (!iter.next().estValide()) {
                iter.remove();
            }
        }
        return deplacements;
    }
}