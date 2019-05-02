# TER_Bomberman

Tuto git :
http://rogerdudler.github.io/git-guide/index.fr.html

Journal de bord des évolutions du projet Bomberman implémenté en JAVA

Bugs Actuels :
- 1 Certaines bombes disparraissent au lieu d'exploser lors d'une explosion en chaîne (régler)
- 2 Lorsqu'un Bbomberman meurt les bombes lui appartenant dissparraissent alors qu'elles devraient normalement finir leur boucle d'état (régler)
- 3 Si un Bomberman prend un item de changement de range les bombes déjà posées sont affectés directement (régler)

- 4 Si au moins deux bombermans prennent un malus swap en même temps PLANTAGE !! (régler)
      Lorsque que le bomberman malade swap et qu'il prend une direction qui n'est pas "légale" dans sa nouvelle position, le jeu plante.

- 5 Manque de fluidité sur les déplacemnt (régler + ou -)
- 6 Priorité du premier bomberman construit sur un autre au moment d' un duel, renvoie au bugue 2 du 12/04/19 (régler)
- 7 Quand on intéragie avec le menu (pause,restart ...) les touches ne sont plus recconnues. (régler)
- 8 Certaines maladies doivent être revues (régler)

- 9 Lors de la mort d'un bomberman si celui-ci pause une bombe à la frame de sa disparition et que la bombe pausée est prise dans une explosion en chaîne alors l'image de l'explosion freez. (régler)
- 10 Un bomberman peu encore se bloquer (probleme thread) si il décide de bouger au moment ou un bomberman vient de poser une  bombe dans la direction de son mouvement. (régler)

- 11 Perte de focus au restart, pour les touches (régler)
- 12 Les bombes explose bcps trop rapidement vis à vis de leur implémentation. (Nous ne savons pas d'où cela pourratit provenir). Ce problème n'est arrivé que depuis la choix de stage. (régler)
- 13 perte de fludité de mouvement au moment du release des touches ( répéter une même touche rapidement).
- 14 Problèmes de chooseAction qui ne fonctionne pas parfaitement quand un agent choisi une direction et que la position futur est prise par un autre agent au moment du déplacement (reference au bug 10).

Problème stratégies : 

A_items : Quand le bomberman doit prendre un item il se positionne d'abord sur l'axe x puis se dirige vers celui des y. Si il y a un obstacle entre lui et l'item, celui répete le même mouvement (se bloque).
B/C : Quand tout les bombermans disposent de ces stratégies le pourcentage de chance qu'ils se tuent est minime voir innexistant.
Lorsqu'un bomberman est en danger il se met en sécurité et attend que celui disparaisse (bombe à portée d'explosion). Cette dynamique peut engendrer un blocage des bombermans, donc ex aequo

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

11/04/19:

Finition des boutons pour la gestion du jeu. Des couleurs differentes sont attribuées à chaque bomberman lors de leur construction. Quelques bugue réglés comme le chargement de l'application qui désormais ne fait plus planter le jeu. Les scores sont différenciable pour chaque Bomberman.

12/04/19:

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

15/04/19:

Fait:
- Fini l'item skull (plus ou moins)
- Commencé à implémenter la gestion des touches pour jouer

Nouveaux bugues:
- 1 Manque de fluidité sur les déplacemnt
- 2 Priorité du premier bomberman construit sur un autre au moment d' un duel, renvoie au bugue 2 du 12/04/19
- 3 Quand on intéragie avec le menu (pause,restart ...) les touches ne sont plus recconnues.
- 4 Certaine mlaadies doient être revu 

16/04/19:

Compte rendu rendez-vous :

- Discussion sur l'implémentation d'ia, avec possibilité du multi threads (faire differentes IA qui s'affrontent, pour voir la plus performante)
- Régler les bugues sur les bombes (bombes qui deviennent indé lors de leur mise sur le terrain, plus d'apparetennace avec le  bbm) 
- Revoir le focus pour la gestion du KeyListener sur le panel du jeu 
- Lier le version automatique avec la version jouable
- Créer l'indépendance des touches pour pouvoir jouer plusieurs Bbm en même temps

Utilisation de github dans son entiereté. Réglage de ombreux bugue.

17/04/19:

Kevin après midi : 
Lors de la mort d'un bomberman, il n efaudrait plus le le remove mais lepasser à isDead et faire les modification necésaire pour qu'il ne puyisse plus intéragir avec le jeu. Pour cela des modifications doivent etre apportés au niveau de paint_bomberman et de choosAction

Bugues du jour :
- 1 Lors de la mort d'un bomberman si celui-ci pause une bombe à la frame de sa disparition et que la bombe pausée est prise dans une explosion en chaîne alors l'image de l'explosion freez
- 2 Un bomberman peu encore se bloquer (probleme thread) si il décide de bouger au moment ou un bomberman vient de poser une  bombe dans la direction de son mouvement.
PERSISTANT :
- 3 Perte de focus au restart, pour les touches

Choses faites :
- Fusion de la partie automatique de bbm et de la partie jouable (toujours en dévloppement).
- Réglage du problème d'appartennance des bombes vis à vis des bbm.
- Création d'un menu permettant de choisir son mode et son stage.

18/04/19:

Bugs du jour : 
- 1 Les bombes explose bcps trop rapidement vis à vis de leur impléméntation. (Nous ne savons pas d'où cela pourratit provenir). Ce problème n'est arrivé que depuis la choix de stage.

Choses faites :
- Implémentation d'un deuxième joueur clavier avec les différentes touches qui en suivent.
- Création de la fin de partie avec tout les aspects (execo, fin de la parie avec le nombre de tours...). Manque simplement la partie PVE.

19/04/19:
- Ajout de la condition de fin de partie PVE.
- Réglement des bugs : 7,8,11.
- Amélioration du menu avec l'ajout d'une prévisualisation de la carte sélectionnée.

23/04/19:

Questions : 
- Fluidité des inputs pour le jeu.
- Mettre stop au threads est il suffisant ?

Différentes strtégies :
- Basique 1
   Il s'écarte des pnj étant à sa porté, pause des des bombes quand il est bloqué.
- Basique 2
   Reprend les aspects de Basique 1, s'écarte de l'axe de l'explosion d'une bombe ennemie quand celle-ci se trouve dans sa position.
- Basique 3
   Reprend les aspects de Basique 2, en modifiant l'écartement de l'axe, l'ia à désormais accès à l'information Range de la bombe qui se trouve ans son axe.
- Avancé 1 
   Reprend les aspects de Basique 3, lors de la découverte d'un item l'ia se dirige vers celui-ci.
- Avancé 2
   Reprend les aspects de Avancé 1, fait la différenciation entre les malus et les bonus.
- Avancé 3
   Reprend les aspects de Avancé 2, se déplace en posant des bombes quand cela est nécéssaire vers le bomberman advairse le plus proche de lui pour éssayer de le tuer.
- Final 
   Reprend touts les aspects précédent en implémentant la recherche du plus court chemin.
   
fait : 
- Implementation du package strategie (ne reste plus qu'à devellopper des strats)
- Multithreads ajouté dans les choix de mode

24/04/19:

- Pourcentage de victoire par joueur / stratégie implémentés sous forme de jframe à la fin du multithread (avec la mise en attente de  la fin de tout les threads)
- Dévloppement de deux IA basique, une orienté PVE qui va se rapprocher des ennemies pour les tuer avec une bombe, et une autre plus orienté PVP  qui elle va déplacer les BBM entre eux pour qu'ils s'entretuent.

25/04/19:
- Continuation de développement de comportements des agents Bomberman en reprenant la base de la veille. 
- Stratégie A : Orienté sur l'évitement des bombes adverses et sur la pose de bombes a portée d'autres bombermans.
                  Déplacement aléatoire dans les autres situations.
- Stratégie B : Orienté sur l'élimination du bomberman le plus proche de lui tout en esquivant les bombes adverses.
                  Pose des bombes celons certaines situations (pris entre deux briques/murs ou plus, a portée d'ennemis).
                  Lorsque que plusieurs stratégies B sont choisies certains bombermans peuvent se bloquer entre eux tout en ne pouvant se tuer.
 - Stratégie A items : Reprend le comportement de A en rajoutant la prise d'items spécifique dans une zone autour du bomberman.
                  (Possibilité de se bloquer lorsque qu'il doit choisir un chemin vers un item).
 - Stratégie C : Reprend le comportement de B et inclu la prise d'item. Lorsque un item est découvert et choisi la cible la plus proche entre l'item et un autre bomberman.
   
26/04/19:

- Implémentation des Stratégies pour chaque bombermans de la carte sélectionnée. Marche en mode normal et en Multithreads.
- Changement visuelle du menu.
- Résolution de bugs engendrés par cette implémentation.
   
29/04/19:

- Reglage de bugs (collision) nuisant au bon fonctionnement du jeu, ainsi le bug du à la maladie swap. Pour plus d'information se rediriger vers les bugs cités au début de ce read me.

30/04/29:

- Implémentation de nouvelles stratégies, visant à régler les problèmes rencontrés avec les ancienne strats. (stratégie D, stratégie A_PVP).
- La stratégie D doit pouvoir permettre à un bomberman de se diriger vers un autre bomberman/item,mais en prenant en compte les différents osbtacles. Si celui-ci est bloqué en prenant un certain chemin, il  choisira un autre chemin pour atteindre sa cible.
- Amélioration de A_items, choisi de récupérer l'item de plus proche dans sa portée et non plus le premier apparu (évite de se bloquer).Quand un item apparait derrière l'explosion d'une bombe, le bomberman ne fonce pas directement pour aller le chercher.
- La stratégie A_PVP reprend les comportements de A_items met en rajoutant un comportement d'approche d'un bomberman adverse sur le même princicipe que la récupération d'items. 

02/04/19:

Compte rendu rendez-vous avec Mr. Goudet:

 - Rajouter du contenu dans les resultats du multithread, plus de statistique, plus de descriptif de victoire.
 - Finir la stratégie D, et plus ou moins A_PVP.
 - Revoir l'effet de vitesse sur les differents agents, une action peu par exemple prendre plus d'un tour à etre               exécuté
      
 - Ajout de contenue :
      
            - Mode coopératif, le principe serait de choisir à la maniere de la stratégie l'equipe de chaque bomberman, mise par defaut à l'ID du BBM.
            - Ajout d'item suplémentaire, la variation de la vitesse (speed down, speed up)
            - Ajout de nouveaux ennemie
            - Implémentation du mode campagne, idée d'un JMenu permettant de faire le choix entre les différents mode (normal ou campagne), ce mode genererais une map la map du début, et à chaque victoir sur map, on chargerais une nouvelle map jusqu'à arriver à un boss et finir le jeu.
            - Après avoir tuer un autre bbm le bbm concerné gagne une vie lui permettant de resister à une explosion de bombe, ceci est cumulable.
            
        
