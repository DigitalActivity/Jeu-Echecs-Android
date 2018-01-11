package yr.jeuechecs.logique.strategiedeplacement;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.Piece;

/**
 * Classe StrategieDeplacementCavalier
 */
public class StrategieDeplacementCavalier extends StrategieDeplacement {
    /**
     * Constructeur Déplacement
     * @param p_coul couleur de la piece.
     */
    public StrategieDeplacementCavalier(Piece.Couleur p_coul) {
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

        p = Position.creer(m_posActuelle.getColonne() - 1, m_posActuelle.getRangee() + 2);
        if (!m_tableJeu.keySet().contains(p) && p.estValide()) // Si position n'est pas occupée et valide
            deplacements.add(p);
        else if (m_tableJeu.keySet().contains(p) && !m_tableJeu.get(p).getCouleur().equals(m_couleur))
            deplacements.add(p);

        p = Position.creer(m_posActuelle.getColonne() + 1, m_posActuelle.getRangee() + 2);
        if (!m_tableJeu.keySet().contains(p) && p.estValide()) // Si position n'est pas occupée et valide
            deplacements.add(p);
        else if (m_tableJeu.keySet().contains(p) && !m_tableJeu.get(p).getCouleur().equals(m_couleur))
            deplacements.add(p);

        p = Position.creer(m_posActuelle.getColonne() - 2, m_posActuelle.getRangee() + 1);
        if (!m_tableJeu.keySet().contains(p) && p.estValide()) // Si position n'est pas occupée et valide
            deplacements.add(p);
        else if (m_tableJeu.keySet().contains(p) && !m_tableJeu.get(p).getCouleur().equals(m_couleur))
            deplacements.add(p);

        p = Position.creer(m_posActuelle.getColonne() + 2, m_posActuelle.getRangee() + 1);
        if (!m_tableJeu.keySet().contains(p) && p.estValide()) // Si position n'est pas occupée et valide
            deplacements.add(p);
        else if (m_tableJeu.keySet().contains(p) && !m_tableJeu.get(p).getCouleur().equals(m_couleur))
            deplacements.add(p);

        p = Position.creer(m_posActuelle.getColonne() - 1, m_posActuelle.getRangee() - 2);
        if (!m_tableJeu.keySet().contains(p) && p.estValide()) // Si position n'est pas occupée et valide
            deplacements.add(p);
        else if (m_tableJeu.keySet().contains(p) && !m_tableJeu.get(p).getCouleur().equals(m_couleur))
            deplacements.add(p);

        p = Position.creer(m_posActuelle.getColonne() + 1, m_posActuelle.getRangee() - 2);
        if (!m_tableJeu.keySet().contains(p) && p.estValide()) // Si position n'est pas occupée et valide
            deplacements.add(p);
        else if (m_tableJeu.keySet().contains(p) && !m_tableJeu.get(p).getCouleur().equals(m_couleur))
            deplacements.add(p);

        p = Position.creer(m_posActuelle.getColonne() - 2, m_posActuelle.getRangee() - 1);
        if (!m_tableJeu.keySet().contains(p) && p.estValide()) // Si position n'est pas occupée et valide
            deplacements.add(p);
        else if (m_tableJeu.keySet().contains(p) && !m_tableJeu.get(p).getCouleur().equals(m_couleur))
            deplacements.add(p);

        p = Position.creer(m_posActuelle.getColonne() + 2, m_posActuelle.getRangee() - 1);
        if (!m_tableJeu.keySet().contains(p) && p.estValide()) // Si position n'est pas occupée et valide
            deplacements.add(p);
        else if (m_tableJeu.keySet().contains(p) && !m_tableJeu.get(p).getCouleur().equals(m_couleur))
            deplacements.add(p);

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