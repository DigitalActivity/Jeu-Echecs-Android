package yr.jeuechecs.logique;

/**
 * Classe Position : Gestion des positions de l'echiquier. Permet de Créer, valider, comparer les positions.
 * @author Younes Rabdi
 */
public class Position {
    private final Pair pos;
    public int getColonne() { return pos.m_col; } // Obtenir la colonne
    public int getRangee() { return pos.m_rang; } // Obtenir la rangée

    /**
     * Classe Pair : Contenir les coordonnées (x, y) d'une position sous forme de pair.
     *               Invisible à l'extérieur de la classe Position
     */
     private class Pair {
        private final int m_col;
        private final int m_rang;
        Pair(int col, int rang) {
            m_col = col;
            m_rang = rang;
        }
    }

    /**
     * Créer nouvelle position
     * @param p_colonne lettre Colonne (X)
     * @param p_rangee numero Rangée (Y)
     * @return Position actuelle
     */
    public static Position creer(Echiquier.Colonne p_colonne, int p_rangee) {
        return new Position(p_colonne.getNum(), p_rangee);
    }

    /**
     * Surcharge creer nouvelle position
     * @param p_colonne numéro Colonne (X)
     * @param p_rangee  numéro Rangée (Y)
     * @return Position
     */
    public static Position creer(int p_colonne, int p_rangee) {
        return new Position(p_colonne, p_rangee);
    }

    /**
     * Constructeur Position.
     * @param p_colonne colonne
     * @param p_rangee rangee
     */
    private Position(int p_colonne, int p_rangee) {
        pos = new Pair(p_colonne, p_rangee);
    }

    /**
     * Vérifier qu'une position ne dépasse pas les limites de l'échiquier
     * @return Vrai si la position est à l'interieur de l'echiquier
     */
    public boolean estValide() {
        return this.getColonne() > 0 && this.getColonne() <= Echiquier.NB_COLONNES &&
                this.getRangee() > 0 && this.getRangee() <= Echiquier.NB_RANGEES;
    }

    /**
     * valeur position en string
     * @return String représentation des opsitions (ex A1, B2, F4)
     */
    @Override
    public String toString()
    {
        if (this.estValide()) {
            return Echiquier.Colonne.values()[pos.m_col - 1].name().concat(Integer.toString(pos.m_rang));
        }
        return String.valueOf(pos.m_col).concat(String.valueOf(pos.m_rang));
    }

    /**
     * equals(Position p) Permet de comparer deux positions
     * @param o_p posision à comparer avec
     * @return true si même position
     */
    @Override
    public boolean equals(final Object o_p)
    {
        return o_p != null && (o_p instanceof Position) && this.toString().equals(o_p.toString());
    }

    /**
     * hashCode
     * @return identifiant unique pour position
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + pos.m_rang;
        result = 31 * result + pos.m_col;
        return result;
    }
}