package yr.jeuechecs.affichage;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import yr.jeuechecs.R;

/**
 * Fragment contient l'entête de la partie :
 *  Les logos des joueurs, forces, icon du joueur a qui le tour de jouer
 * @author Younes rabdi
 */
public class ScoreFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.score_fragment, container, false);
    }
}