package yr.jeuechecs.logique;

import yr.jeuechecs.logique.piece.Piece;

/**
 * Classe Joueur
 */
public class Joueur {
    private final String m_nom;
    private final Piece.Couleur m_couleur;
    public String getNom() {
        return m_nom;
    }
    public Piece.Couleur getCouleur() {
        return m_couleur;
    }

    /**
     * Constructeur joueur
     * @param nom nom du joueur
     * @param couleur couleur des pieces du joueur
     */
    public Joueur(String nom, Piece.Couleur couleur) {
        this.m_nom     = nom;
        this.m_couleur = couleur;
    }
}
