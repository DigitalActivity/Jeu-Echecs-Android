package yr.jeuechecs.affichage;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TableRow;
import yr.jeuechecs.R;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.Piece;

/**
 * CaseEchiquier represente une case dans la table de jeu, peut contenir une piece ou être vide.
 * @author Younes Rabdi
 */
public class CaseEchiquier extends android.support.v7.widget.AppCompatImageView implements View.OnClickListener {
    public enum CouleurCase { CASE_BLANCHE, CASE_NOIRE }
    private Position m_pos;
    private Piece m_piece;
    private Context m_context;
    private CouleurCase m_couleur;

    /**
     * Getter position de la case
     * @return Position
     */
    public Position get_pos() {
        return m_pos;
    }

    /**
     * Getter Piece sur la case
     * @return Piece
     */
    public Piece get_piece() {
        return m_piece;
    }

    /**
     * Setter position de la case
     * @param p position
     */
    public void set_pos(Position p) {
        m_pos = p;
    }

    /**
     * Mettre une piece sur cette case. Adapte le drawable de la piece
     * @param p piece a mettre sur cette case
     */
    public void set_piece(Piece p) {
        m_piece = p;
        if (m_piece != null) {
            this.setImageDrawable(m_piece.getRepresentation().obtenirDrawable());
            setEnabled(true);
        } else {
            this.setImageResource(android.R.color.transparent);
        }
    }

    /**
     * Constructeur CaseEchiquier
     * @param context context de l'activité appelante
     * @param p_taille taille du le bouton
     */
    public CaseEchiquier(Context context, int p_taille, CouleurCase p_couleur) {
        super(context);
        m_couleur = p_couleur;
        m_context = context;
        switch(p_couleur)
        {
            case CASE_BLANCHE :
                this.setBackgroundColor(ContextCompat.getColor(context, R.color.case_blanche));
                break;
            case CASE_NOIRE :
                this.setBackgroundColor(ContextCompat.getColor(context, R.color.case_noire));
                break;
        }

        this.setEnabled(false);
        this.setLayoutParams(new TableRow.LayoutParams(p_taille,p_taille, 1f));
    }

    /**
     * Ajouter brillance pour la case
     */
    public void ajouterBrillance() {
        switch(m_couleur)
        {
            case CASE_BLANCHE :
                this.setBackgroundColor(ContextCompat.getColor(m_context, R.color.case_blanche_brillance));
                break;
            case CASE_NOIRE :
                this.setBackgroundColor(ContextCompat.getColor(m_context, R.color.case_noire_brillance));
                break;
        }
    }

    /**
     * Enlever la brillance de la case
     */
    public void enleverBrillance() {
        switch (m_couleur)
        {
            case CASE_BLANCHE :
                this.setBackgroundColor(ContextCompat.getColor(m_context, R.color.case_blanche));
                break;
            case CASE_NOIRE :
                this.setBackgroundColor(ContextCompat.getColor(m_context, R.color.case_noire));
                break;
        }
    }

    /**
     * onClick, est redefinie dans PartieActivity
     * @param v view
     */
    @Override
    public void onClick(View v) {
        setEnabled(m_piece != null);
    }

    /**
     * Constructeur suggérés par android Lint
     * @param context context
     */
    public CaseEchiquier(Context context) {
        super(context);
    }

    /**
     * Constructeur suggérés par android Lint
     * @param context context
     * @param attrs attributs
     */
    public CaseEchiquier(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
