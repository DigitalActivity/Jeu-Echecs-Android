package yr.jeuechecs.logique.strategiedeplacement;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.Piece;

/**
 * Classe StrategieDeplacementReine
 */
public class StrategieDeplacementReine extends StrategieDeplacement {
    private final StrategieDeplacement diagonal;
    private final StrategieDeplacement horiVerti;
    /**
     * Constructeur Déplacement
     * @param p_coul couleur de la piece.
     */
    public StrategieDeplacementReine(Piece.Couleur p_coul) {
        this.m_couleur = p_coul;
        diagonal = new StrategieDeplacementFou(this.m_couleur); // Deplacements comme un fou
        horiVerti = new StrategieDeplacementTour(this.m_couleur); // Deplacements comme une tour
    }
    /**
     * Set de positions possibles pour cette pièce
     * @return Set des positions possibles
     */
    @Override
    public Set<Position> obtenirDeplacementsBase() {
        Set<Position> deplacements = new HashSet<>();
        diagonal.setTableJeu(m_tableJeu);
        horiVerti.setTableJeu(m_tableJeu);
        diagonal.setPosActuelle(this.m_posActuelle);
        horiVerti.setPosActuelle(this.m_posActuelle);
        deplacements.addAll(diagonal.obtenirDeplacementsBase());
        deplacements.addAll(horiVerti.obtenirDeplacementsBase());

        Iterator<Position> iter = deplacements.iterator();
        while (iter.hasNext()) {
            if (!iter.next().estValide()) {
                iter.remove();
            }
        }
        return deplacements;
    }
}