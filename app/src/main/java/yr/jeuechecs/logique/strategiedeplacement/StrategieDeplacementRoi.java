package yr.jeuechecs.logique.strategiedeplacement;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import yr.jeuechecs.logique.Echiquier;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.Piece;
import yr.jeuechecs.logique.piece.Tour;

/**
 * Classe StrategieDeplacementRoi
 */
public class StrategieDeplacementRoi extends StrategieDeplacement {
    private boolean petitRoqueEstPossible = true;
    private boolean grandRoqueEstPossible = true;

    /**
     * Constructeur Déplacement
     * @param p_coul couleur de la piece.
     */
    public StrategieDeplacementRoi(Piece.Couleur p_coul) {
        this.m_couleur = p_coul;
    }

    /**
     * Set de positions possibles pour cette pièce
     * @return Set des positions possibles
     */
    @Override
    public Set<Position> obtenirDeplacementsBase() {
        Set<Position> deplacements = new HashSet<>();
        Position p = Position.creer(m_posActuelle.getColonne(), m_posActuelle.getRangee() + 1); // avancer
        if (!m_tableJeu.keySet().contains(p)) {// Si position n'est pas occupée
            deplacements.add(p);
        } else if (m_tableJeu.keySet().contains(p) && !m_tableJeu.get(p).getCouleur().equals(m_couleur))
            deplacements.add(p);

        p = Position.creer(m_posActuelle.getColonne() + 1, m_posActuelle.getRangee() + 1); // avancer à droite
        if (!m_tableJeu.keySet().contains(p)) {// Si position n'est pas occupée
            deplacements.add(p);
        } else if (m_tableJeu.keySet().contains(p) && !m_tableJeu.get(p).getCouleur().equals(m_couleur))
            deplacements.add(p);

        p = Position.creer(m_posActuelle.getColonne() - 1, m_posActuelle.getRangee() + 1); // avancer à gauche
        if (!m_tableJeu.keySet().contains(p)) {// Si position n'est pas occupée
            deplacements.add(p);
        } else if (m_tableJeu.keySet().contains(p) && !m_tableJeu.get(p).getCouleur().equals(m_couleur))
            deplacements.add(p);

        p = Position.creer(m_posActuelle.getColonne(), m_posActuelle.getRangee() - 1); // reculer
        if (!m_tableJeu.keySet().contains(p)) {// Si position n'est pas occupée
            deplacements.add(p);
        } else if (m_tableJeu.keySet().contains(p) && !m_tableJeu.get(p).getCouleur().equals(m_couleur))
            deplacements.add(p);

        p = Position.creer(m_posActuelle.getColonne() + 1, m_posActuelle.getRangee() - 1); // reculer à droite
        if (!m_tableJeu.keySet().contains(p)) {// Si position n'est pas occupée
            deplacements.add(p);
        } else if (m_tableJeu.keySet().contains(p) && !m_tableJeu.get(p).getCouleur().equals(m_couleur))
            deplacements.add(p);

        p = Position.creer(m_posActuelle.getColonne() - 1, m_posActuelle.getRangee() - 1); // avancer à gauche
        if (!m_tableJeu.keySet().contains(p)) {// Si position n'est pas occupée
            deplacements.add(p);
        } else if (m_tableJeu.keySet().contains(p) && !m_tableJeu.get(p).getCouleur().equals(m_couleur))
            deplacements.add(p);

        p = Position.creer(m_posActuelle.getColonne() + 1, m_posActuelle.getRangee()); // à droite
        if (!m_tableJeu.keySet().contains(p)) {// Si position n'est pas occupée
            deplacements.add(p);
        } else if (m_tableJeu.keySet().contains(p) && !m_tableJeu.get(p).getCouleur().equals(m_couleur))
            deplacements.add(p);

        p = Position.creer(m_posActuelle.getColonne() - 1, m_posActuelle.getRangee()); // à gauche
        if (!m_tableJeu.keySet().contains(p)) {// Si position n'est pas occupée
            deplacements.add(p);
        } else if (m_tableJeu.keySet().contains(p) && !m_tableJeu.get(p).getCouleur().equals(m_couleur))
            deplacements.add(p);

        // Verifier si le petit roque est possible
        // La deuxième condition n'est plus appelée une fois que le roque n'est plus autorisé (roi ou tour déplacé)
        if (petitRoqueEstPossible && verifierPetitRoque()) {
            deplacements.add(m_couleur == Piece.Couleur.BLANC ? Position.creer(8, 1) : Position.creer(8, 8));
        }

        // Verifier si le grand roque est possible
        // La deuxième condition n'est plus appelée une fois que le roque n'est plus autorisé (roi ou tour déplacé)
        if (grandRoqueEstPossible && verifierGrandRoque()) {
            deplacements.add(m_couleur == Piece.Couleur.BLANC ? Position.creer(1, 1) : Position.creer(1, 8));
        }

        //deplacements.removeIf(e -> !e.estValide()); // Au cas ou il y a une position invalide (pas sensé se produire)
        Iterator<Position> iter = deplacements.iterator();
        while (iter.hasNext()) {
            if (!iter.next().estValide()) {
                iter.remove();
            }
        }
        return deplacements;
    }

    /**
     * Verifier si le petit roque est possible
     * @return vrai si possible
     */
    public boolean verifierPetitRoque() {
        Boolean estpossible;
        Piece tour = m_tableJeu.get(m_couleur == Piece.Couleur.BLANC ? Position.creer(8, 1) : Position.creer(8, 8));
        // une fois la tour est déplacée, plus possible de faire le petit roque.
        if (tour == null) {
            petitRoqueEstPossible = false;
            return petitRoqueEstPossible;
        }
        // même si la piece revient à sa position original, le roque n'est plus possible
        petitRoqueEstPossible = tour.getNombreDeplacements() == 0 && this.m_cptDeplacements == 0;
        if (!petitRoqueEstPossible) {
            return false;
        }
        // vérifier que les positions entr le roi et la tour sont vides
        if (m_couleur == Piece.Couleur.BLANC) {
            estpossible = petitRoqueEstPossible &&
                            !m_tableJeu.containsKey(Position.creer(7, 1)) &&
                            !m_tableJeu.containsKey(Position.creer(6, 1));
        }
        else {
            estpossible = petitRoqueEstPossible &&
                            !m_tableJeu.containsKey(Position.creer(7, 8)) &&
                            !m_tableJeu.containsKey(Position.creer(6, 8));
        }
        return estpossible;
    }

    /**
     * Verifier si le grand roque est possible
     * @return vrai si possible
     */
    public boolean verifierGrandRoque() {
        Boolean estpossible;
        Piece tour = m_tableJeu.get(m_couleur == Piece.Couleur.BLANC ? Position.creer(1, 1) : Position.creer(1, 8));
        // une fois la tour est déplacée, plus possible de faire le grand roque.
        if (tour == null) {
            grandRoqueEstPossible = false;
            return grandRoqueEstPossible;
        }
        // même si la piece revient à sa position originale, le roque n'est plus possible
        grandRoqueEstPossible = tour.getNombreDeplacements() == 0 && this.m_cptDeplacements == 0;
        if (!petitRoqueEstPossible) {
            return false;
        }
        // vérifier que les positions entre le roi et la tour sont vides
        if (m_couleur == Piece.Couleur.BLANC) {
            estpossible = petitRoqueEstPossible &&
                            !m_tableJeu.containsKey(Position.creer(2, 1)) &&
                            !m_tableJeu.containsKey(Position.creer(3, 1)) &&
                            !m_tableJeu.containsKey(Position.creer(4, 1));
        }
        else { estpossible = petitRoqueEstPossible &&
                            !m_tableJeu.containsKey(Position.creer(2, 8)) &&
                            !m_tableJeu.containsKey(Position.creer(3, 8)) &&
                            !m_tableJeu.containsKey(Position.creer(4, 8));
        }
        return estpossible;
    }

    /**
     * Applique les positionnement quand c'est un roque, petit ou grand
     * @param p_destination position de la destination (Contiens une tour)
     * @param p_roi Piece qui represent le roi
     * @return true quand le deplacment effectué, false sinon
     */
    public static boolean appliquerDeplacementRoque(Position p_destination, Piece p_roi, LinkedHashMap<Position, Piece> p_table) {
        Piece tour = p_table.get(p_destination);
        if(tour == null || !tour.getClass().equals(Tour.class))
            return false;

        // petit roque
        Position p = (p_roi.estBlanc() ? Position.creer(8, 1) : Position.creer(8, 8));
        if (p_destination.equals(p)) {
            return Echiquier.Instance().positionnerPiece(p_roi.estBlanc() ?
                    Position.creer(7, 1) : Position.creer(7, 8), p_roi, p_table) && // positionner roi
                    Echiquier.Instance().positionnerPiece(p_roi.estBlanc() ?
                            Position.creer(6, 1) : Position.creer(6, 8), tour, p_table);    // Positionner tour
        }
        // grand roque
        else if (p_destination.equals(p_roi.estBlanc() ? Position.creer(1, 1) : Position.creer(1, 8))) {
            return Echiquier.Instance().positionnerPiece(p_roi.estBlanc() ?
                    Position.creer(3, 1) : Position.creer(3, 8), p_roi, p_table) && // positionner roi
                    Echiquier.Instance().positionnerPiece(p_roi.estBlanc() ?
                            Position.creer(4, 1) : Position.creer(4, 8), tour, p_table);    // Positionner tour
        }
        return false;
    }
}