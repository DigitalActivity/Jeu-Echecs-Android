package yr.jeuechecs.logique.strategiedeplacement;

import java.util.Set;

import yr.jeuechecs.logique.Position;

/**
 * Interface Déplacable : Methodes à implementer par les Strategies de déplacements
 */
interface Deplacable {
    boolean deplacementEstPossible(Position p_destination);
    Set<Position> obtenirDeplacementsBase(); // Set de positions possibles
}