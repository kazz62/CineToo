# CinéToo - Architecture Setup Guide

## Architecture Clean implementée

L'architecture Clean a été mise en place avec 3 couches distinctes :

### 1. Domain Layer (Logique métier)

**Location:** `composeApp/src/commonMain/kotlin/com/kazz/cinetoo/domain/`

#### Modèles
- `MediaType.kt` - Enum pour Movie/TVShow
- `Genre.kt` - Modèle de genre
- `StreamingPlatform.kt` - Modèle de plateforme de streaming
- `CastMember.kt` - Modèle de membre du cast
- `Movie.kt` - Modèle de film
- `TVShow.kt` - Modèle de série TV
- `UserPreferences.kt` - Modèle de préférences utilisateur
- `FavoriteItem.kt` - Modèle d'item favori

#### Repositories (Interfaces)
- `MovieRepository.kt` - Interface pour les opérations liées aux films/séries
- `FavoritesRepository.kt` - Interface pour la gestion des favoris
- `UserPreferencesRepository.kt` - Interface pour les préférences utilisateur

### 2. Data Layer (Sources de données)

**Location:** `composeApp/src/commonMain/kotlin/com/kazz/cinetoo/data/`

#### Remote (API)
- `ApiConstants.kt` - Constantes de l'API TMDb
- `TMDbApi.kt` - Client API pour TMDb
- `HttpClientFactory.kt` - Factory pour créer le client HTTP (expect/actual)
- `AuthInterceptor.kt` - Interceptor pour ajouter l'API key

#### Local (Database)
- **SQLDelight Schema:** `composeApp/src/commonMain/sqldelight/com/kazz/cinetoo/database/`
  - `Favorite.sq` - Table des favoris
  - `Note.sq` - Table des notes personnelles
  - `UserGenre.sq` - Table des genres utilisateur
  - `UserPlatform.sq` - Table des plateformes utilisateur
  - `AppSettings.sq` - Table des paramètres de l'application

- **Database Driver:**
  - `DatabaseDriverFactory.kt` - Factory expect/actual pour Android/iOS
  - `DatabaseDriverFactory.android.kt` - Implémentation Android
  - `DatabaseDriverFactory.ios.kt` - Implémentation iOS

### 3. Presentation Layer (ViewModels)

**Location:** `composeApp/src/commonMain/kotlin/com/kazz/cinetoo/presentation/`

Structure créée pour les écrans :
- `discover/` - Pour l'écran de découverte (swipe)
- `detail/` - Pour les détails film/série
- `favorites/` - Pour la liste des favoris
- `onboarding/` - Pour l'onboarding (genres + plateformes)
- `settings/` - Pour les paramètres

## Stack Technique Configurée

### Networking - Ktor Client (2.3.12)
- Client HTTP multiplateforme
- Content negotiation avec kotlinx.serialization
- Logging activé
- Auth interceptor pour l'API key TMDb

### Database - SQLDelight (2.0.2)
- Base de données locale multiplateforme
- Driver Android (AndroidSqliteDriver)
- Driver iOS (NativeSqliteDriver)
- Database: `CineTooDatabase`

### Dependency Injection - Koin (3.5.6)
- DI multiplateforme
- 3 modules configurés:
  - `platformModule()` - Dépendances spécifiques à la plateforme
  - `dataModule()` - Couche Data
  - `domainModule()` - Use cases (à remplir)
  - `presentationModule()` - ViewModels (à remplir)

### Coroutines & Serialization
- `kotlinx-coroutines-core` (1.9.0)
- `kotlinx-serialization-json` (1.7.3)

## Configuration de l'API TMDb

### 1. Obtenir une API Key

1. Créer un compte sur [TMDb](https://www.themoviedb.org/)
2. Aller dans Settings > API
3. Générer une API Key (v4 auth)

### 2. Configurer l'API Key

Éditer le fichier `local.properties` à la racine du projet :

```properties
TMDB_API_KEY=votre_clé_api_ici
```

**IMPORTANT:** Ne JAMAIS commiter l'API key dans Git !

## Initialisation Koin

### Android

La classe `CineTooApplication` initialise Koin au démarrage :

```kotlin
class CineTooApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@CineTooApplication)
            properties(mapOf("TMDB_API_KEY" to BuildConfig.TMDB_API_KEY))
        }
    }
}
```

### iOS

À configurer dans le code Swift (voir brief du projet).

## Structure des dossiers

```
composeApp/src/
├── commonMain/
│   ├── kotlin/com/kazz/cinetoo/
│   │   ├── data/
│   │   │   ├── remote/
│   │   │   │   ├── api/
│   │   │   │   ├── dto/
│   │   │   │   └── interceptors/
│   │   │   ├── local/
│   │   │   │   ├── database/
│   │   │   │   └── entities/
│   │   │   └── repository/
│   │   ├── domain/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   └── usecase/
│   │   ├── presentation/
│   │   │   ├── discover/
│   │   │   ├── detail/
│   │   │   ├── favorites/
│   │   │   ├── onboarding/
│   │   │   └── settings/
│   │   └── di/
│   └── sqldelight/com/kazz/cinetoo/database/
├── androidMain/
└── iosMain/
```

## Prochaines étapes

### Phase 1 : Compléter la couche Data
- [ ] Créer les DTOs pour les réponses API
- [ ] Implémenter les repositories (MovieRepositoryImpl, etc.)
- [ ] Mapper les DTOs vers les modèles Domain

### Phase 2 : Use Cases
- [ ] GetDiscoverMoviesUseCase
- [ ] GetMovieDetailsUseCase
- [ ] AddToFavoritesUseCase
- [ ] SaveUserPreferencesUseCase
- [ ] Etc.

### Phase 3 : ViewModels
- [ ] DiscoverViewModel
- [ ] MovieDetailViewModel
- [ ] FavoritesViewModel
- [ ] OnboardingViewModel
- [ ] SettingsViewModel

### Phase 4 : UI (Jetpack Compose)
- [ ] Écrans selon le brief
- [ ] Navigation
- [ ] Thème Material 3 personnalisé

## Build & Run

### Sync Gradle
```bash
./gradlew clean build
```

### Run Android
```bash
./gradlew :composeApp:installDebug
```

### Run iOS (sur macOS uniquement)
```bash
./gradlew :composeApp:iosSimulatorArm64Test
```

## Dépendances ajoutées

Consultez `gradle/libs.versions.toml` pour la liste complète des versions.

## Ressources

- [TMDb API Docs](https://developer.themoviedb.org/docs)
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
- [Koin Documentation](https://insert-koin.io/docs/reference/koin-mp/kmp)
- [SQLDelight](https://cashapp.github.io/sqldelight/)
- [Ktor Client](https://ktor.io/docs/client.html)
