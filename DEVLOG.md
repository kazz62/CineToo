# Journal de developpement - Cine Too

Ce document retrace l'evolution du projet, les choix techniques effectues et les problemes rencontres.

---

## Phase 1 : Initialisation du projet

**Branche** : `main`

### Objectif

Mettre en place la base du projet KMP avec l'architecture Clean et les dependances necessaires.

### Travail realise

Creation du projet Kotlin Multiplatform via le wizard Android Studio avec les targets Android et iOS.

Mise en place de l'architecture en 3 couches :
- `data/` : sources de donnees (API, base locale)
- `domain/` : logique metier pure, independante des frameworks
- `presentation/` : ViewModels et UI Compose

Definition des modeles du domaine :
- `Movie`, `TVShow` : entites principales
- `Genre`, `StreamingPlatform` : metadonnees
- `UserPreferences`, `FavoriteItem` : donnees utilisateur
- `CastMember` : informations casting

Creation des interfaces de repository dans le domaine pour respecter le principe d'inversion de dependances.

### Choix techniques

**Kotlin Multiplatform** plutot que Flutter ou React Native : le projet cible principalement Android avec iOS en secondaire. KMP permet de garder une UI native performante tout en partageant la logique metier. L'equipe a deja une expertise Kotlin.

**Compose Multiplatform** : maturite suffisante en 2026 pour une UI partagee. Evite de maintenir deux codebases UI separees.

**Clean Architecture** : separation claire des responsabilites. Le domaine ne depend de rien, ce qui facilite les tests et les evolutions futures.

### Dependances ajoutees

```toml
ktor = "2.3.12"           # Client HTTP multiplateforme
koin = "3.5.6"            # DI legere, adaptee a KMP
sqldelight = "2.0.2"      # Base locale avec typage fort
coroutines = "1.9.0"      # Asynchrone
serialization = "1.7.3"   # JSON parsing
```

**Pourquoi Ktor** : client HTTP natif Kotlin, supporte parfaitement KMP. Alternative a Retrofit qui ne supporte pas iOS.

**Pourquoi Koin** : plus simple que Hilt/Dagger pour KMP. Configuration declarative, pas de generation de code.

**Pourquoi SQLDelight** : genere du code Kotlin type-safe a partir de fichiers SQL. Fonctionne sur toutes les plateformes contrairement a Room.

### Schema de base de donnees

Tables creees via SQLDelight :
- `Favorite` : films/series favoris
- `Note` : notes personnelles sur les favoris
- `UserGenre` : genres selectionnes par l'utilisateur
- `UserPlatform` : plateformes de streaming de l'utilisateur
- `AppSettings` : parametres cle/valeur

---

## Phase 2 : Splash Screen et Navigation

**Branche** : `feat/splash_screen`

### Objectif

Implementer le splash screen avec le logo de l'app et la navigation de base.

### Travail realise

Creation du theme de l'application :
- Palette sombre avec violet comme couleur principale (#8B5CF6)
- Fond tres sombre (#0A0E27) pour un rendu cinematographique
- Typographie Material 3

Implementation du `SplashScreen` :
- Logo anime (bobine de film stylisee dessinee via Canvas)
- Texte "Cine too" avec animation fade-in
- Verification de l'etat de l'onboarding au demarrage

Mise en place de la navigation avec Navigation Compose :
- Routes definies comme des objets serialisables (type-safe)
- Gestion du back stack pour eviter de revenir au splash

Creation du `SplashViewModel` :
- Verifie si l'utilisateur a complete l'onboarding
- Emet la destination appropriee (Onboarding ou Home)

### Choix techniques

**Navigation Compose type-safe** : utilisation des routes comme objets Kotlin plutot que des strings. Permet d'avoir des arguments types et evite les erreurs a l'execution.

**ViewModel avec StateFlow** : pattern unidirectionnel. L'UI observe un state immutable, les actions passent par le ViewModel.

**Canvas pour le logo** : pas de dependance a des assets externes, le logo est genere en code. Facilite les modifications et garantit une qualite parfaite a toutes les densites.

### Problemes rencontres

Le Canvas seul sans conteneur causait un crash au demarrage (pas de taille intrinseque). Resolution en l'enveloppant dans un Box avec une taille explicite.

---

## Phase 3 : Onboarding - Selection des genres

**Branche** : `feat/splash_screen`

### Objectif

Permettre a l'utilisateur de selectionner ses genres preferes pour personnaliser les recommandations.

### Travail realise

Creation de `GenreData` : liste des 25 genres disponibles avec leurs IDs TMDb et emojis associes.

Implementation du repository :
- `UserPreferencesRepository` : interface dans le domaine
- `UserPreferencesRepositoryImpl` : implementation avec SQLDelight
- Sauvegarde des genres en base locale

Use cases crees :
- `GetAvailableGenresUseCase` : retourne la liste des genres
- `SaveSelectedGenresUseCase` : persiste la selection
- `GetSelectedGenresUseCase` : recupere la selection (Flow)

`OnboardingGenresViewModel` :
- Gestion du state (genres disponibles, selection courante, loading)
- Validation de la selection
- Emission d'evenements de navigation

`OnboardingGenresScreen` :
- Grille de 2 colonnes avec les genres
- Chips selectionnables (emoji + nom)
- Bouton de validation actif uniquement si au moins un genre selectionne

### Choix techniques

**Grille de 2 colonnes** : meilleur compromis lisibilite/densite sur mobile. 3 colonnes rendait les chips trop petits.

**Flow pour les donnees persistees** : permet une reactivite automatique si les donnees changent. Le repository expose des Flow, les ViewModels les collectent.

**SharedFlow pour les evenements one-shot** : la navigation ne doit pas etre rejouee en cas de recomposition. SharedFlow evite ce probleme contrairement a StateFlow.

### Problemes rencontres

Crash a cause d'un ID duplique dans la liste des genres (10764 utilise deux fois). Les keys doivent etre uniques dans LazyColumn/LazyGrid.

---

## Phase 4 : Onboarding - Selection des plateformes

**Branche** : `feat/splash_screen`

### Objectif

Permettre a l'utilisateur de selectionner ses plateformes de streaming pour filtrer le contenu.

### Travail realise

Creation de `PlatformData` : 6 plateformes principales (Netflix, Disney+, Prime Video, HBO Max, Apple TV+, Paramount+) avec leurs IDs TMDb.

Extension du repository pour les plateformes :
- Memes patterns que pour les genres
- Sauvegarde en base locale

Use cases crees :
- `GetAvailablePlatformsUseCase`
- `SaveSelectedPlatformsUseCase`
- `SetOnboardingCompletedUseCase` : marque l'onboarding comme termine

`OnboardingPlatformsViewModel` : similaire au ViewModel des genres.

`OnboardingPlatformsScreen` :
- Liste simple (LazyColumn)
- Chaque plateforme sur une ligne avec checkbox
- Design epure selon les maquettes

### Choix techniques

**Liste simple plutot que grille** : les noms des plateformes sont plus longs que les genres, une liste verticale est plus lisible.

**Checkbox Material 3** : composant natif, accessibilite integree, theming automatique.

**Ajout de Coil** : prevu pour afficher les logos des plateformes depuis TMDb. Pour l'instant non utilise dans cet ecran mais la dependance est en place.

### Patterns appliques

Safe area padding sur tous les ecrans (`statusBarsPadding()` + `navigationBarsPadding()`) pour eviter que le contenu soit masque par les barres systeme.

---

## Prochaines etapes

- Ecran Discover (swipe cards)
- Ecran Details (film/serie)
- Ecran Favoris
- Bottom Navigation
- Integration complete de l'API TMDb
- Ecran Settings
