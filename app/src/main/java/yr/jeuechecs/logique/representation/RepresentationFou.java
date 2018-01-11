package yr.jeuechecs.logique.representation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import yr.jeuechecs.R;
import yr.jeuechecs.logique.piece.Piece;

/**
 * Classe RepresentationFou
 */
public class RepresentationFou extends Representation {
    private final static char p = 'f'; // Representation reine

    /**
     * Afficher le caracter correspondant à la piece dans la console
     */
    @Override
    public void afficher() {
        System.out.print(m_couleur == Piece.Couleur.BLANC ? p : Character.toUpperCase(p));
    }

    @Override
    public Drawable obtenirDrawable() {
        return m_drawable;
    }

    /**
     * Definir un context pour faire adapter les drawables à une taille optimale pour l'ecrant.
     * doit etre appelée une fois après la création d'une piece (Dans une activité)
     * @param p_context context contient les données de l'activité
     */
    public void setupDrawable(Context p_context) {
        switch (m_couleur) {
            case BLANC :
                m_drawable = ResourcesCompat.getDrawable(p_context.getResources(), R.drawable.foublanc_image, null);
                break;
            case NOIR :
                m_drawable = ResourcesCompat.getDrawable(p_context.getResources(), R.drawable.founoir_image, null);
        }
    }

    /**
     * Constructeur Representation Fou
     * @param p_couleur couleur du Fou à représenté
     */
    public RepresentationFou(Piece.Couleur p_couleur) {
        m_couleur = p_couleur;
    }
}