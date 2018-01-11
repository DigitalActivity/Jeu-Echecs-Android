package yr.jeuechecs.logique.representation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import yr.jeuechecs.R;
import yr.jeuechecs.logique.piece.Piece;

/**
 * Classe RepresentationTour
 */
public class RepresentationTour extends Representation {
    private final static char p = 't'; // Representation reine

    @Override
    public void afficher() {
        System.out.print(m_couleur == Piece.Couleur.BLANC ? p : Character.toUpperCase(p));
    }

    /**
     * Obtenir le drawable correspondant à la piece. Peut etre null.
     * N'utiliser cette methode qu'apres avoir appelé setupDrawable(Context p_context)
     * @return Drawable ou null
     */
    @Override
    public Drawable obtenirDrawable() {
        return m_drawable;
    }

    /**
     * Definir un context pour faire obtenir les drawables à une taille optimale pour l'ecrant.
     * doit etre appelée une fois après la création d'une piece ou l'initialisation de l'echiquier (Dans une activité)
     * @param p_context context contient les données de l'activité
     */
    public void setupDrawable(Context p_context) {
        switch (m_couleur) {
            case BLANC :
                m_drawable = ResourcesCompat.getDrawable(p_context.getResources(), R.drawable.tourblanche_image, null);
                break;
            case NOIR :
                m_drawable = ResourcesCompat.getDrawable(p_context.getResources(), R.drawable.tournoir_image, null);
        }
    }

    /**
     * Constructeur Representation Tour
     * @param p_couleur couleur du Tour à représenté
     */
    public RepresentationTour(Piece.Couleur p_couleur) {
        m_couleur = p_couleur;
    }
}