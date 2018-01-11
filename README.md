# Jeu Echecs pour Android

Jeu d’échecs interactif à deux joueurs sur appareil mobile Android.

FONCTIONNALITÉS
-Interface graphique permettant à deux joueurs de déplacer des pièces de manière valide et d’effectuer des prises réglementaires.
-Détection des échecs au roi et empêchera ainsi les déplacements qui mettraient ou laisseraient le roi en échec.
-Détection échec et mat

NOTES
- la logique du jeu est indépendante des interfaces d'affichage
- Les strategies de deplacement sont gérées avec Factory Methode Pattern. Chaque class définit son type de deplacements et sa representation
- La logique "prise en passant" utilise Observer Pattern. Les pions sont abonnées au sujet "Prise en passant" et sont notifiés des changements
- La class echiquier est un Singleton

(16 mai 2017)