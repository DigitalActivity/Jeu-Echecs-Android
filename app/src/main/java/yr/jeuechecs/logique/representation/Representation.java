package yr.jeuechecs.logique.representation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import yr.jeuechecs.logique.piece.Piece;

/**
 * Classe abstraite Representation : Classe de base pour les representations dérivées de chque pi
 */
public abstract class Representation {
    Piece.Couleur m_couleur;
    Drawable m_drawable;

    /**
     * Afficher le caracter correspondant à la piece dans la console
     */
    public abstract void afficher();

    /**
     * Obtenir le Drawable correspondant à la piece.
     * (Utiliser à partir d'une activité après avoir appelé la fonction setupDrawable(Context p_context))
     * @return drawable correspondant à la piece
     */
    public abstract Drawable obtenirDrawable();

    /**
     * Setup les drawables permet aux pieces d'acceder au context de l'application pour obtenir leurs drawables
     * @param p_context context ( pour pouvoir acceder aux ressources)
     */
    public abstract void setupDrawable(Context p_context);
}
