# CinéToo - KMP Project Brief

## 🎯 Objectif du Projet

Développer une application mobile multiplateforme (Android/iOS) de type IMDB utilisant l'API The Movie Database (TMDb). L'application doit permettre aux utilisateurs de découvrir des films et séries, de gérer leurs favoris et de personnaliser leur expérience.

---

## 📋 Spécifications Fonctionnelles

### Écrans à Implémenter

#### 1. **Splash Screen**
- Logo "Ciné too" centré sur fond sombre (#0A0E27)
- Transition automatique vers l'onboarding

#### 2. **Onboarding - Sélection des Genres** 
- Titre : "Your favorite genres"
- Grid de genres avec emojis (Action 💥, Adventure 🗻, Animation 🎨, etc.)
- Sélection multiple avec état visuel (fond légèrement coloré quand sélectionné)
- Bouton violet "Validate my choices" en bas
- Liste complète des genres visibles :
  - Action, Adventure, Animation, Anime, Comedy, Crime
  - Documentary, Drama, Family, Fantasy, Game show, Horror
  - Language, Lifestyle, Music, Musical, Mystery, Reality TV
  - Romance, Sci-Fi, Seasonal, Short, Sport, Thriller, Western

#### 3. **Onboarding - Streaming Platforms**
- Titre : "Streaming Platforms"
- Liste verticale avec checkboxes à droite
- Plateformes : Netflix, Disney+, Prime Video, HBO Max, Apple TV+, Paramount+
- Bouton violet "Validate my choices"

#### 4. **Détail Film/Série**
- Poster en grand en haut (presque plein écran)
- Bouton X en haut à droite pour fermer
- Overlay gradient du bas avec :
  - Année (icône calendrier 📅) + Note (étoile ⭐)
  - Titre en gros
  - Genres séparés par tirets
- Bouton violet "Add to favorites ❤️"
- Section "Synopsis" avec texte descriptif
- Infos supplémentaires :
  - Director (nom)
  - Duration (nombre de saisons ou durée)
- Section "Cast" avec chips de noms d'acteurs
- Section "Available on" avec chips de plateformes
- Section "Personal note" :
  - TextField multi-lignes avec placeholder
  - Bouton blanc "Save my note"

#### 5. **Discover (Swipe)**
- Titre "Discover" en haut
- Card centrale avec poster de film/série
- Infos en overlay : année, note, titre, genres
- 2 boutons en bas :
  - Bouton rouge avec X (skip)
  - Bouton violet avec ❤️ (add to favorites)
- Bottom navigation avec 4 onglets :
  - Home, Favorites, Discover (actif, violet), Settings

#### 6. **Favorites**
- Titre "Favorites"
- État vide : 
  - Emojis 🎥 🎬 🍿 centrés
  - "No favorites at the moment"
  - "Add movies and shows to your favorites to find them here"
- État rempli : Grid de posters avec infos

#### 7. **Settings**
- Titre "Settings"
- Section "Favorites genres" :
  - Chips avec X pour retirer (Animation, Sci-Fi, Documentary, Comedy, Crime)
  - Bouton + pour ajouter
- Section "Streaming platforms" :
  - Chips avec X (Netflix, Disney+, Prime Video, Apple TV+)
  - Bouton + pour ajouter
- Section "Data storage" :
  - Texte d'avertissement : "This will delete all your favorites as well as your comments."
  - Bouton rouge "Delete all my data 🗑️"

---

## 🏗️ Architecture Technique

### Clean Architecture (3 couches obligatoires)

```
shared/
├── commonMain/
│   ├── data/              # Couche Data
│   │   ├── remote/        # API (Ktor)
│   │   │   ├── api/
│   │   │   │   └── TMDbApi.kt
│   │   │   ├── dto/       # Data Transfer Objects
│   │   │   └── interceptors/
│   │   ├── local/         # Base de données locale
│   │   │   ├── database/
│   │   │   └── entities/
│   │   └── repository/    # Implémentations des repositories
│   │       └── MovieRepositoryImpl.kt
│   │
│   ├── domain/            # Couche Domain
│   │   ├── model/         # Modèles métier
│   │   │   ├── Movie.kt
│   │   │   ├── Genre.kt
│   │   │   ├── StreamingPlatform.kt
│   │   │   └── UserPreferences.kt
│   │   ├── repository/    # Interfaces des repositories
│   │   │   └── MovieRepository.kt
│   │   └── usecase/       # Use cases
│   │       ├── GetDiscoverMoviesUseCase.kt
│   │       ├── GetMovieDetailsUseCase.kt
│   │       ├── AddToFavoritesUseCase.kt
│   │       └── SaveUserPreferencesUseCase.kt
│   │
│   └── presentation/      # Couche Presentation (ViewModels partagés)
│       ├── discover/
│       │   └── DiscoverViewModel.kt
│       ├── detail/
│       │   └── MovieDetailViewModel.kt
│       ├── favorites/
│       │   └── FavoritesViewModel.kt
│       ├── onboarding/
│       │   └── OnboardingViewModel.kt
│       └── settings/
│           └── SettingsViewModel.kt
│
├── androidMain/           # Code spécifique Android
│   └── kotlin/
│       └── ui/            # Composables Jetpack Compose
│
└── iosMain/              # Code spécifique iOS
    └── swift/            # Views SwiftUI
```

### Principe de Séparation des Responsabilités

**Data Layer :**
- Gestion des sources de données (API, BDD locale)
- Mapping DTO → Domain Models
- Pas de logique métier

**Domain Layer :**
- Modèles métier purs (Kotlin sans dépendances Android/iOS)
- Use Cases : une responsabilité = un use case
- Repositories (interfaces seulement)
- Pas de dépendances aux frameworks

**Presentation Layer :**
- ViewModels partagés entre Android et iOS
- Gestion de l'état UI
- Appel des use cases
- Pas d'accès direct aux repositories

---

## 🔧 Stack Technique Recommandée

### Shared Module (KMP)

**Networking :**
- `Ktor Client` (2.3.x) : appels HTTP
- `kotlinx.serialization` : parsing JSON
- Interceptors pour l'API key TMDb

**Database :**
- `SQLDelight` (2.0.x) : base de données locale multiplateforme
- Tables : favorites, user_preferences, notes

**Injection de Dépendances :**
- `Koin` (3.5.x) : DI multiplateforme
- Modules : dataModule, domainModule, presentationModule

**Coroutines & Flow :**
- `kotlinx.coroutines` : gestion asynchrone
- `StateFlow` / `SharedFlow` : gestion d'état réactive

**ViewModel :**
- `androidx.lifecycle:lifecycle-viewmodel-compose` (pour Android)
- Wrapper KMM pour iOS

### Android App

**UI :**
- `Jetpack Compose` (Material 3)
- `Navigation Compose`
- `Accompanist` (permissions, system UI controller)

**Image Loading :**
- `Coil` : chargement des posters

**Theme :**
- Couleurs :
  - Background: `#0A0E27`
  - Primary (violet): `#8B5CF6` (ou similaire du figma)
  - Surface: `#1A1F3A`
  - Texte: `#FFFFFF`

### iOS App (si besoin de détails)

**UI :**
- SwiftUI
- Combine (ou Async/Await)

**Image Loading :**
- AsyncImage native ou Kingfisher

---

## 🌐 API TMDb

**Base URL :** `https://api.themoviedb.org/3/`

**Authentification :**
- API Key dans les headers ou query params
- Header : `Authorization: Bearer <API_KEY>`

### Endpoints Principaux (à confirmer avec specs finales)

**Discover Movies/TV :**
```
GET /discover/movie
GET /discover/tv

Query params (probables) :
- with_genres: liste d'IDs de genres
- with_watch_providers: liste d'IDs de plateformes
- sort_by: popularity.desc
- page: numéro de page
```

**Détails :**
```
GET /movie/{movie_id}
GET /tv/{tv_id}

Includes: credits, videos, watch/providers
```

**Genres :**
```
GET /genre/movie/list
GET /genre/tv/list
```

**Configuration :**
```
GET /configuration
(pour obtenir les URLs des images)
```

**Images :**
```
Base URL: https://image.tmdb.org/t/p/
Tailles: w500 (posters), original
```

### Modèles de Données Principaux

```kotlin
// Domain Models

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val genres: List<Genre>,
    val director: String?,
    val duration: Int?, // en minutes
    val cast: List<CastMember>,
    val availableOn: List<StreamingPlatform>
)

data class TVShow(
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val firstAirDate: String,
    val voteAverage: Double,
    val genres: List<Genre>,
    val numberOfSeasons: Int,
    val cast: List<CastMember>,
    val availableOn: List<StreamingPlatform>
)

data class Genre(
    val id: Int,
    val name: String,
    val emoji: String
)

data class StreamingPlatform(
    val id: Int,
    val name: String,
    val logoPath: String?
)

data class CastMember(
    val id: Int,
    val name: String,
    val character: String?,
    val profilePath: String?
)

data class UserPreferences(
    val favoriteGenres: List<Genre>,
    val streamingPlatforms: List<StreamingPlatform>
)

data class FavoriteItem(
    val id: Int,
    val type: MediaType, // MOVIE or TV_SHOW
    val posterPath: String?,
    val title: String,
    val note: String?
)

enum class MediaType {
    MOVIE, TV_SHOW
}
```

---

## ✅ Fonctionnalités à Implémenter

### Phase 1 : Setup & Core Features
- [x] Setup projet KMP
- [ ] Configuration Koin (DI)
- [ ] Configuration Ktor (API client)
- [ ] Configuration SQLDelight (DB locale)
- [ ] Models Domain
- [ ] Repository interfaces & implémentations
- [ ] Use Cases de base

### Phase 2 : Onboarding
- [ ] Splash Screen
- [ ] Sélection des genres
- [ ] Sélection des plateformes
- [ ] Sauvegarde des préférences (BDD locale)

### Phase 3 : Discover & Détails
- [ ] Écran Discover avec swipe
- [ ] Appel API discover avec filtres (genres + plateformes)
- [ ] Écran détail film/série
- [ ] Add to favorites
- [ ] Personal note

### Phase 4 : Favorites & Settings
- [ ] Liste des favoris (avec état vide)
- [ ] Settings avec gestion des préférences
- [ ] Delete all data

### Phase 5 : Navigation & Polish
- [ ] Bottom Navigation
- [ ] Animations de transition
- [ ] Gestion des erreurs (pas de connexion, API down)
- [ ] Loading states
- [ ] Tests unitaires (Use Cases)

---

## 📝 Conventions de Code

**Kotlin :**
- Camel case pour les fonctions/variables
- Pascal case pour les classes
- Suffixes : `ViewModel`, `UseCase`, `Repository`, `Dto`, `Entity`

**Git :**
- Branches : `feature/nom-feature`, `fix/nom-bug`
- Commits conventionnels : `feat:`, `fix:`, `refactor:`, etc.

**Tests :**
- Tests unitaires pour les Use Cases
- Nommage : `should_returnSuccess_when_validInput()`

---

## 🎨 Design System (à extraire du Figma)

**Typography :**
- Titres : Bold, 24-28sp
- Body : Regular, 14-16sp
- Chips : Medium, 14sp

**Spacing :**
- Padding écran : 16-20dp
- Spacing entre éléments : 8-16dp

**Border Radius :**
- Cards : 12-16dp
- Buttons : 24-28dp
- Chips : 20dp

**Elevation :**
- Cards : légère (2-4dp)

---

## 🚀 Points d'Attention

1. **API Key TMDb :** Ne JAMAIS commiter l'API key dans le code. Utiliser `local.properties` ou variables d'environnement.

2. **Gestion des images :** 
   - Utiliser les tailles appropriées (w500 pour les posters)
   - Gérer le cas où posterPath est null

3. **Offline-first :**
   - Sauvegarder les favoris en local
   - Permettre de consulter les favoris hors ligne

4. **États UI :**
   - Loading
   - Success
   - Error
   - Empty

5. **iOS :**
   - Tester régulièrement sur le MacBook Air
   - Compiler avec `./gradlew iosSimulatorArm64Test`

---

## 📦 Fichiers de Configuration à Créer

**gradle/libs.versions.toml** (Version Catalog)
```toml
[versions]
kotlin = "2.0.21"
compose = "1.7.1"
ktor = "2.3.12"
koin = "3.5.6"
sqldelight = "2.0.2"
coroutines = "1.9.0"

[libraries]
ktor-client-core = { module = "io.ktor:ktor-client-core", version.ref = "ktor" }
ktor-client-logging = { module = "io.ktor:ktor-client-logging", version.ref = "ktor" }
ktor-serialization = { module = "io.ktor:ktor-serialization-kotlinx-json", version.ref = "ktor" }
# ... etc
```

**local.properties**
```properties
TMDB_API_KEY=your_api_key_here
```

---

## 🎯 Livrables Attendus

1. Code source sur GitHub (public ou privé Listen Too)
2. README.md avec :
   - Instructions de setup
   - Comment obtenir une API key TMDb
   - Comment lancer l'app Android & iOS
3. Architecture documentée (schémas si possible)
4. Tests unitaires pour les Use Cases critiques

---

## 📞 Points de Synchronisation

- Meetings réguliers pour follow-up
- Code review via GitHub Copilot (si disponible)
- Documentation des choix techniques dans le README

---

## 🧪 Exemple de Use Case

```kotlin
// domain/usecase/GetDiscoverMoviesUseCase.kt
class GetDiscoverMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        genres: List<Int>,
        platforms: List<Int>,
        page: Int = 1
    ): Result<List<Movie>> {
        return try {
            val movies = movieRepository.discoverMovies(
                genreIds = genres,
                platformIds = platforms,
                page = page
            )
            Result.success(movies)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

---

## 🔗 Ressources Utiles

- [TMDb API Docs](https://developer.themoviedb.org/docs)
- [Kotlin Multiplatform Docs](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Koin Documentation](https://insert-koin.io/docs/reference/koin-mp/kmp)

---

**Bon courage et n'hésite pas à adapter ce brief selon tes besoins ! 🚀**
