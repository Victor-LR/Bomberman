# TER_Bomberman

Journal de bord des évolutions du projet Bomberman implémenté en JAVA

1/04/19:

Prise en main d'ecplipse à partir du projet Pacman proposé par le professeur. Prise en main de Gitthub pour pouvoir travailler plus facilement à partir d'un espace ou tout les fichiers sont accessibles pour nous deux. Commencement du projet avec l'aide des fichiers de Pacman, insertion de la lecture de fichier pour la réalisation du plateau de jeu.

02/04/19:

Finition de la lecture du fichier et premier map lu avec succès. Réalisation des premiers agents (ennemis), avec les différentes actions gérées. Equivalence avec les fantômes de Pacman. Réflection pour l'implementation d'image dans la carte pour remplacer les cubes créés à partir de JPanel. Sprites pour les differentes orientations des ennemis. Affichage des ennemis dans la map.

03/04/19:

Journée de réflexion sur un problème de type syntaxique, menant à des threads déffectuées. Cependant en cette fin de journée nous avons resolu ce problème. Cela nous a permis de réaliser le deplacement des agents selon des variables aléatoire et un nombre de tour. Néanmoins l'affichage du jeu n'est visble qu'à la fin des tours, et non à chaque tours.

04/04/19:

Nous avons reussi à afficher les déplacements des différents agents en temps réel. De plus, nous avons implémenté les classes pour bomberman et pour les objets (bombe). Ce qui a permis au bomberman de poser des bombes de façon aléatoire sur le terrain. Pour finir on acherché et implementé les sprites pour les différents éléments du projet (bomberman, mur, ennemis, briques, bombes, terrain).

05/04/19:

Les bombes sont désormais active, en effet elles peuvent détruir les blocs ou l'explosion se ropage (grâce à un effet de range pour la bombes dans sa classe). L'animation de l'explosion de la bombe est plus ou moins terminée dans le sens où elle ne supporte pas encore le changement de range.

08/04/19:

   - Penser que les bombes appartiennent à un unique bomberman. Toutes modifications des propriétés de la bombes n'appartient qu'au bomberman qui à réupéré l'item. (fait)
  - Mort de l'ennemie prit dans la range de la bombe. (fait)
   - Finition de l'animation de l'explosion de la bombe en fonction de la range de celle ci.(fait)
   - Ajout d'un compteur de point pour chaque Bomberman, +100 points à la mort d'un ennemie.


L'ajout du layout pour l'affichage des scores n'est pas encore finalisé. En effet nous avons rencontré quelques problèmes dans le création du layout (score), la mise à jour des scores n'est pas optimal (superposition des nouveaux et anciens scores).

09/04/19:

Questions : 
- Fonctionnement GameObserver / InterfaceGame
- Fonctionnement de l'affichage des scores, quelle gestionnaire de layout et problème de JLabel
- Priorités dans les prochains jour (amélioration animation, avance de l'ia, controle d'un bomberman, ajout d'item, menu ...)

L'interfaçage du score à été réglé (subsiste un petit problème de thread lorsque le temps entre les tours est trop petit). Mise en développement des items. Appartennance des bombes pour chaque agents Bomberman et changement d'état pour les bombes (auto explosion quand celle ci sont prise dans une explosion).

10/04/19:

- Explosion en chaine des bombes (fait)
- Implémentation des items dns le jeu 
- Boutons du menu

L'implémentation des item est en progrès, nous pouvons désormais les récupérer et avoir des bonus ou malus ( augmentation de la range et diminution de celle-ci). L'explosion en chaine des bombes à été fixe et marche relatiement bien. Les boutons du menu on été ajouté à l'interface, nous pouvons désormais mettre en pause et relancer le jeu sans aucuns souis. Cependant il fallut plus de temps pour comprendre le fonctionnement du bouton restart (celui-ci nous permet de relancer le jeu). 
De plus Victor a commencé à implementer la différenciation de couleurs pour les différents personnage du bomberman.

11/04/19

Finition des boutons pour la gestion du jeu. Des couleurs differentes sont attribuées à chaque bomberman lors de leur construction. Quelques bugue réglés comme le chargement de l'application qui désormais ne fait plus planter le jeu. Les scores sont différenciable pour chaque Bomberman.

12/04/19

Bugues persistant : 
- 1 Certaines bombes disparraissent au lieu d'exploser lors d'une explosion en chaîne 
- 2 Lorsqu'un Bbomberman meurt les bombes lui appartenant dissparraissent alors qu'elles devraient normalement finir leur boucle d'état
- 3 Si un Bomberman prend un item de changement de range les bombes déjà posées sont affectés directement (?)
- 4 Si au moins deux bombermans prennet un malus swap en même temps PLANTAGE !!

Fait aujourd'hui :
- Un Bombermnan ne peut plus marcher sur une bombe et non plus en poser deux d'affilé 
- Item d'augmentation du nombre de bombe et diminution ajouté
- Item d'invincibilité avec changement de couleur lors de la prise de celui-ci.
- Item "Skull" qui donne une maladie aléatoire au bomberman concerné.
- Implémentation des effets des différentes maladies du Skull.
- Ajout d'un curseur pour régler la vitesse du jeu.

15/04/19

Fait:
- Fini l'item skull (plus ou moins)
- Commencé à implémenter la gestion des touches pour jouer

Nouveaux bugues:
- 1 Manque de fluidité sur les déplacemnt
- 2 Priorité du premier bomberman construit sur un autre au moment d' un duel, renvoie au bugue 2 du 12/04/19
- 3 Quand on intéragie avec le menu (pause,restart ...) les touches ne sont plus recconnues.
- 4 Certaine mlaadies doient être revu 
