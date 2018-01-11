package yr.jeuechecs.affichage;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.util.LinkedHashMap;
import yr.jeuechecs.R;
import yr.jeuechecs.logique.Echiquier;
import yr.jeuechecs.logique.Partie;
import yr.jeuechecs.logique.Position;
import yr.jeuechecs.logique.piece.*;
import static yr.jeuechecs.logique.piece.Piece.Couleur;

/**
 * Activité de jeu. Permet de relier la logique de Partie et les widgets de l'application
 * @author Younes Rabdi
 * @see Partie
 */
public class PartieActivity extends AppCompatActivity {
    // Les cases affichables de l'echiquier.
    private static final LinkedHashMap<Position, CaseEchiquier> m_casesAffichables = new LinkedHashMap<>();
    private Piece m_pieceSelectionee; // Piece selectionnée par le joueur à qui est le tour de jouer
    private Piece pieceChoisiePromotion; // Piece selectionnée dans le menu promotion
    private DisplayMetrics metrics; // Metriques de l'ecrant, permet de créer les m_casesAffichables de bonne taille
    private TextView forceJoueur1;
    private TextView forceJoueur2;
    private ImageView m_case_joueurEnCours;
    private Button boutonBack;
    private Button boutonForward;
    private GridLayout tableJeu; // Table d'affichage du jeu
    private Partie partie;

    /**
     * Initialiser PartieActivity
     * @param savedInstanceState reference au bundle de sauvegarde de l'activité
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partie);
        metrics = this.getResources().getDisplayMetrics();
        // Créer une nouvelle partie
        partie = new Partie("joueur1", "joueur2");
        /* Setup les drawables de chaque type de piece.
           (les pieces ont besoin du context pour acceder aux ressources de l'application)*/
        for (Piece piece : partie.obtenirTableJeu().values()) {
            piece.setupDrawable(this);
        }
        // Afficher les forces
        forceJoueur1 = (TextView) findViewById(R.id.textView_force_joueur1);
        forceJoueur2 = (TextView) findViewById(R.id.textView_force_joueur2);
        // Relier icon du prochain à jouer
        m_case_joueurEnCours = (ImageView) findViewById(R.id.imageView_joueur_en_cours);
        dessinerImageDuJoueurEnCours();
        // Créer le tableau d'affichage
        tableJeu = (GridLayout) findViewById(R.id.gridlayout_table_echiquier);
        // Une seul colonne pour contenir une ligne chargée avec 8 m_casesAffichables
        tableJeu.setColumnCount(1);
        // Créer les boutons historique
        relierLesBoutonsHistorique();
        // Créer Bouton reset
        CreerBoutonReset();
        // Créer les m_casesAffichables affichables de l'echiquier
        CreerLesCasesAffichables(); // créés une seul fois et contenus dans m_casesAffichables
        // Dessiner table jeu
        dessinerTableJeu();
    } // FIN onCreate()

    /**
     * Créer les casesAffichables de l'echiquier
     */
    private void CreerLesCasesAffichables() {
        // ne rien faire si les cases sont deja crées (pas sensé se produire).
        if (!m_casesAffichables.isEmpty()) {
            return;
        }
        boolean caseBlanche = true; // case est blanche ou noir sinon
        CaseEchiquier.CouleurCase clrCase;
        Position pos;

        for (int rangee = Echiquier.NB_RANGEES; rangee >= 1 ; rangee--) {
            for (int colonne = 1; colonne <= Echiquier.NB_COLONNES; colonne++) {
                clrCase = caseBlanche ? CaseEchiquier.CouleurCase.CASE_BLANCHE :
                        CaseEchiquier.CouleurCase.CASE_NOIRE;
                caseBlanche = !caseBlanche;
                // 8 cases + case avec les numeros des lignes
                CaseEchiquier caseEchiq = new CaseEchiquier(this, metrics.widthPixels / 9, clrCase);
                pos = Position.creer(colonne, rangee);
                caseEchiq.set_pos(pos);
                m_casesAffichables.put(pos, caseEchiq);
            }
            caseBlanche = !caseBlanche;
        }
    }

    /**
     * Créer le bouton reset qui permet de reinitialiser l'echiquier
     */
    private void CreerBoutonReset() {
        // Bouton reset table
        Button boutton_reset = (Button) findViewById(R.id.button_initialiser);
        boutton_reset.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 partie = new Partie(partie.getJoueurBlanc().getNom(), partie.getJoueurNoir().getNom());
                 for (Piece piece : partie.obtenirTableJeu().values()) {
                     piece.setupDrawable(getApplicationContext());
                 }
                 // enlever la tinture des cases
                 for(CaseEchiquier ce : m_casesAffichables.values()) {
                     ce.enleverBrillance();
                 }
                 dessinerTableJeu();
             }
        });
    }

    /**
     * Relier les boutons d'historique
     */
    private void relierLesBoutonsHistorique() {
        // Relier le buton pour aller vers avant
        boutonForward = (Button) findViewById(R.id.button_forward);
        boutonForward.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (partie.getHistorique().size() > partie.indexHistorique + 1) {
                     m_pieceSelectionee = null;
                     partie.chargerTableJeu(++partie.indexHistorique);
                     dessinerTableJeu();
                 }
             }
        });
        // Relier le buton pour aller vers arrier
        boutonBack = (Button) findViewById(R.id.button_back);
        boutonBack.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if (0 <= partie.indexHistorique - 1) {
                      m_pieceSelectionee = null;
                      partie.chargerTableJeu(--partie.indexHistorique);
                      dessinerTableJeu();
                  }
              }
        });
    }

    /**
     * Definir un setOnclick pour CaseEchiquier.
     * Tous les cases de l'echiquier ont le meme code onClick(), mais un comportemenet different
     * grace aux if else. il aurait été peut être plus convenable d'utiliser le patron État.
     * @param p_case CaseEchiquier à setter
     */
    private void setOnClick(final CaseEchiquier p_case) {
        p_case.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(CaseEchiquier ca : m_casesAffichables.values()) {
                    ca.enleverBrillance();
                }
                // Verifier echec seulement si un deplacement est effectué.
                boolean VerifierEchec = false;

                /* Cas 1 : Une piece est selectionnée
                ( Afficher les m_casesAffichables possibles (deplacements par defaut), sans verifier si causent echec ) */
                if (m_pieceSelectionee == null && p_case.get_piece() != null &&
                        p_case.get_piece().getCouleur().equals(partie.getProchainÀJouer().getCouleur())) {
                    m_pieceSelectionee = p_case.get_piece();
                    tinterLesCasesPossibles();
                }
                /* Cas 2 : Une piece est selectionnée et une case valide est choisie
                ( Effectuer le déplacement si possible ) */
                else if (m_pieceSelectionee != null &&
                        m_pieceSelectionee.getCouleur().equals(partie.getProchainÀJouer().getCouleur()) &&
                        m_pieceSelectionee.posEstPossible(p_case.get_pos())) {
                    // Jouer
                    if (partie.jouer(m_pieceSelectionee.getPosActuelle(), p_case.get_pos())) {
                        // Vérifier s'il y a une Promotion
                        for (Piece pi : partie.obtenirTableJeu().values())
                            if (pi.getClass().equals(Pion.class) &&
                                    pi.getPosActuelle().getRangee() == (pi.estBlanc() ? 8 : 1)) {
                                afficherMenuPromotion(pi.getCouleur(), pi.getPosActuelle());
                                break;
                            }
                    }
                    m_pieceSelectionee = null;
                    // verification d'Echec va être faite;
                    VerifierEchec = true;
                }
                /* Cas 3 : Une piece est selectionnée mais le joueur selectionne une autre de ses pieces
                ( Tinter les cases selon la nouvelle piece selectionnée )*/
                else if (m_pieceSelectionee != null && p_case.get_piece() != null &&
                        m_pieceSelectionee.getCouleur().equals(p_case.get_piece().getCouleur())) {
                    m_pieceSelectionee = p_case.get_piece();
                    tinterLesCasesPossibles();
                }
                // verifier si echec ou echec et Mat
                if (VerifierEchec) {
                    detecterSiEchecEtMat();
                } else {
                    detecterSiPat();
                }
                dessinerTableJeu();
            }
        });
    }

    /**
     * Detecter un echec et afficher les messages adaptés en conséquence
     * @return vrai quand un joueur est en echec, faux sinon
     */
    private boolean detecterSiEnEchec() {
        String nomJoueurEnEchec = "";
        Couleur couleur = null;
        switch (partie.detecterEchec()) {
            case 1 : nomJoueurEnEchec = partie.getJoueurBlanc().getNom();
                couleur = Couleur.BLANC; break;
            case 2 : nomJoueurEnEchec = partie.getJoueurNoir().getNom();
                couleur = Couleur.NOIR;
        }
        if (!nomJoueurEnEchec.equals("")) {
            Toast.makeText(getApplication(),
                    nomJoueurEnEchec.concat(" ").concat(getResources().getString(R.string.text_en_echec)),
                    Toast.LENGTH_LONG).show();
            // tinter la case du roi en echec
            for (CaseEchiquier ce : m_casesAffichables.values()) {
                if (ce.get_piece() != null && ce.get_piece().getCouleur().equals(couleur) &&
                        ce.get_piece().getClass().equals(Roi.class)) {
                    ce.ajouterBrillance();
                    break;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Detecter si un joueur est en pat et affiche les dialogues adaptés
     */
    private boolean detecterSiPat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PartieActivity.this);
        boolean pat = false;
        switch (partie.detecterPat()) {
            case 1 : builder.setMessage(partie.getJoueurBlanc().getNom().concat(" ").concat(
                    getResources().getString(R.string.text_perdu_la_partie)))
                    .setTitle(R.string.text_roi_blanc_pat);
                pat = true;
                break;
            case 2 : builder.setMessage(partie.getJoueurNoir().getNom().concat(" ").concat(
                    getResources().getString(R.string.text_perdu_la_partie)))
                    .setTitle(R.string.text_roi_noir_pat);
                pat = true;
                break;
        }
        if (pat) {
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return pat;
    }

    /**
     * Detecter si un joueur est en echec et mat (echec + pat) et affiche les dialogues adaptés
     */
    private void detecterSiEchecEtMat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PartieActivity.this);
        Couleur pc = null;
        boolean echec = false;

        if (detecterSiEnEchec()) {
            switch (partie.detecterPat()) {
                case 1 : builder.setMessage(partie.getJoueurBlanc().getNom().concat(" ").concat(
                        getResources().getString(R.string.text_perdu_la_partie)))
                        .setTitle(R.string.text_roi_blanc_echec_mat);
                    pc = Couleur.BLANC;
                    echec = true;
                    break;
                case 2 : builder.setMessage(partie.getJoueurNoir().getNom().concat(" ").concat(
                        getResources().getString(R.string.text_perdu_la_partie)))
                        .setTitle(R.string.text_roi_noir_echec_mat);
                    pc = Couleur.NOIR;
                    echec = true;
                    break;
            }
            if(echec) {
                for (CaseEchiquier ce : m_casesAffichables.values()) {
                    if (ce.get_piece() != null &&
                            ce.get_piece().getCouleur().equals(pc) &&
                            ce.get_piece().getClass().equals(Roi.class)) {
                        ce.ajouterBrillance();
                        break;
                    }
                }
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

    /**
     * Dessiner la table du jeu. Noms colonnes, numeros lignes, m_casesAffichables, pieces
     */
    private void dessinerTableJeu() {
        tableJeu.removeAllViews();
        dessinerImageDuJoueurEnCours(); // icon du joueur à qui le tour de jouer
        // afficher les derniers deplacements
        afficherDerniersDeplacements();
        // mettre à jour les forces
        forceJoueur1.setText(String.valueOf(partie.getForceBlanc()));
        forceJoueur2.setText(String.valueOf(partie.getForceNoir()));
        // inserer les pieces de l'echiquier dans les m_casesAffichables
        Position pos = null;
        Piece piece;
        for (int rangee = Echiquier.NB_RANGEES; rangee >= 1 ; rangee--) {
            TableRow ligne = new TableRow(this);
            TableRow.LayoutParams p = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.MATCH_PARENT);
            p.setMargins(0,0,0,0);
            ligne.setLayoutParams(new TableRow.LayoutParams(p));
            tableJeu.addView(ligne);
            for (int colonne = 1; colonne <= Echiquier.NB_COLONNES; colonne++) {
                pos = Position.creer(colonne, rangee);
                piece = partie.obtenirPieceSelonPos(pos);
                CaseEchiquier caseEchiq = m_casesAffichables.get(pos);
                if (piece != null) {
                    caseEchiq.set_piece(piece);
                } else {
                    caseEchiq.set_piece(null);
                }
                setOnClick(caseEchiq);
                if(caseEchiq.getParent()!=null) {
                    ((ViewGroup) caseEchiq.getParent()).removeView(caseEchiq);
                }
                ligne.addView(caseEchiq);
            }
            // Afficher les numeros des lignes
            ligne.addView(creerTextAvecNumeroDeLigne((pos.getRangee())));
        }
        // afficher les noms des colonnes
        TableRow ligne = new TableRow(this);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 0, 0, 0);
        ligne.setLayoutParams(new TableRow.LayoutParams(params));
        tableJeu.addView(ligne);
        remplirLigneAvecNomsDesColonnes(ligne);
    } // Fin dessinerEchiquier

    /**
     * Créer un TextView adatpté pour affichage avec le numero de ligne à afficher
     * @param p_numero numero de ligne
     * @return TextView avec le numero de ligne a afficher
     */
    private TextView creerTextAvecNumeroDeLigne(int p_numero) {
        TextView t = new TextView(this);
        t.setText(String.valueOf(p_numero));
        t.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
        t.setHeight(metrics.widthPixels / 9);
        t.setWidth(metrics.widthPixels / 11);
        return t;
    }

    /**
     * Remplir une tableRow avec les noms de colonnes de l'echiquier.
     * @param p_tableRow ligne a remplir
     */
    private void remplirLigneAvecNomsDesColonnes(final TableRow p_tableRow) {
        for (int colonne = 1; colonne <= Echiquier.NB_COLONNES; colonne++) {
            TextView t = new TextView(this);
            t.setText(String.valueOf(Echiquier.Colonne.values()[colonne - 1].name()));
            t.setWidth(metrics.widthPixels / 9);
            t.setGravity(Gravity.CENTER);
            p_tableRow.addView(t);
        }
    }

    /**
     * Dessiner l'image du joueur à qui le tours de jouer
     */
    private void dessinerImageDuJoueurEnCours() {
        if (partie.getProchainÀJouer().getCouleur().equals(Couleur.BLANC)) {
            m_case_joueurEnCours.setImageDrawable(ResourcesCompat.getDrawable(
                    this.getResources(), R.drawable.prochainiconblanc, null));
        }
        else {
            m_case_joueurEnCours.setImageDrawable(ResourcesCompat.getDrawable(
                    this.getResources(), R.drawable.prochainiconnoir, null));
        }
    }

    /**
     * Tinter les cases possibles pour la piece selectionnée
     */
    private void tinterLesCasesPossibles() {
        if (m_pieceSelectionee == null) {
            return;
        }
        for (CaseEchiquier ce : m_casesAffichables.values()) {
            ce.enleverBrillance();
        }

        CaseEchiquier uneCasePossible;
        for (Position p : m_pieceSelectionee.obtenirPosPossiblesDeBase()) {
            uneCasePossible = m_casesAffichables.get(p);
            if(uneCasePossible != null) {// Juste par precaution mais pas supposé se produire.
                uneCasePossible.ajouterBrillance();
                uneCasePossible.setEnabled(true);
            }
        }
        m_casesAffichables.get(m_pieceSelectionee.getPosActuelle()).ajouterBrillance();
    }

    /**
     * Fonction qui affiche les dérniers déplacements et les affiche dans les boutonBack ou boutonForward
     */
    private void afficherDerniersDeplacements() {
        StringBuilder p = new StringBuilder();
        Piece piece = null;
        if (partie.indexHistorique - 1 < partie.getHistorique().size() && partie.indexHistorique - 1 > 0)
        {
            // obtenir la derniere piece déplacée dans l'echiquier
            // (impossible d'acceder au dernier element avec index a partir de linkedHashMap)
            for (Piece piece1 : partie.getHistorique().get(partie.indexHistorique - 1).values()) {
                piece = piece1;
            }
            p.append(piece.getPosActuelle().toString()).append(piece.estBlanc() ? " [Blanc]" : " [Noir]");
        }
        boutonBack.setText(String.valueOf(p.toString().concat(getResources().getString(R.string.go_back))));
        p.setLength(0);
        piece = null;
        if (partie.indexHistorique + 1 < partie.getHistorique().size() && partie.indexHistorique + 1 > 0)
        {
            // obtenir la derniere piece déplacée dans l'echiquier
            // (impossible d'acceder au dernier element avec index a partir de linkedHashMap)
            for (Piece piece1 : partie.getHistorique().get(partie.indexHistorique + 1).values()) {
                piece = piece1;
            }
            p.append(piece.getPosActuelle().toString()).append(piece.estBlanc() ? " [Blanc]" : " [Noir]");
        }
        boutonForward.setText(String.valueOf(getResources().getString(R.string.go_forward).concat(p.toString())));
    }

    /**
     * Afficher Menu promotion, permet au joueur de choisir une piece qui va remplacer son pion
     * @param p_couleur couleur du pion en promotion
     * @param p_pos position du pion en promotion
     */
    private void afficherMenuPromotion(Couleur p_couleur, Position p_pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PartieActivity.this);
        builder.setTitle("Promotion");
        builder.setItems(new CharSequence[] {"Reine", "Tour", "Fou", "Chevalier"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: pieceChoisiePromotion = Reine.creer(p_couleur);
                                break;
                            case 1: pieceChoisiePromotion = Tour.creer(p_couleur);
                                break;
                            case 2: pieceChoisiePromotion = Fou.creer(p_couleur);
                                break;
                            case 3: pieceChoisiePromotion = Cavalier.creer(p_couleur);
                                break;
                            default : pieceChoisiePromotion = Tour.creer(p_couleur);
                        }
                        pieceChoisiePromotion.setupDrawable(getApplicationContext());
                        partie.obtenirTableJeu().put(p_pos, pieceChoisiePromotion);
                        pieceChoisiePromotion.setPosActuelle(p_pos);
                        pieceChoisiePromotion.setTableDuJeu(partie.obtenirTableJeu());
                        partie.getHistorique().remove(partie.getHistorique().size() - 1);
                        partie.getHistorique().add(partie.clonerTableJeu());
                        dessinerTableJeu();
                        // verifier si echec ou echecEtMat
                        if (detecterSiEnEchec()) {
                            detecterSiEchecEtMat();
                        } // sinon verifier si pat
                        else {
                            detecterSiPat();
                        }
                    }
                });
        builder.create().show();
    }
}
