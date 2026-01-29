# Cine Too

Application mobile multiplateforme (Android/iOS) de recommandation de films et series TV. L'utilisateur peut decouvrir du contenu via un systeme de swipe, gerer ses favoris et filtrer par genres et plateformes de streaming.

## Fonctionnalites

- Decouverte de films et series via swipe (style Tinder)
- Filtrage par genres preferes
- Filtrage par plateformes de streaming
- Gestion des favoris avec notes personnelles
- Details complets des films/series (casting, synopsis, plateformes disponibles)
- Mode sombre uniquement (design cinematographique)

## Stack technique

Le projet utilise Kotlin Multiplatform (KMP) avec Compose Multiplatform pour partager la quasi-totalite du code entre Android et iOS.

### Architecture

Clean Architecture en 3 couches :

```
presentation/    ViewModels + Compose UI
    |
domain/          Use cases + Models + Repository interfaces
    |
data/            Repository implementations + API + Database
```

### Librairies principales

| Librairie | Version | Usage |
|-----------|---------|-------|
| Compose Multiplatform | 1.10.0 | UI declarative partagee |
| Ktor Client | 2.3.12 | Appels HTTP vers TMDb API |
| SQLDelight | 2.0.2 | Base de donnees locale |
| Koin | 3.5.6 | Injection de dependances |
| Coil | 3.0.4 | Chargement d'images |
| Navigation Compose | 2.8.0-alpha10 | Navigation entre ecrans |

### API

L'application utilise l'API TMDb (The Movie Database) pour recuperer les informations sur les films et series.

## Structure du projet

```
composeApp/
  src/
    commonMain/         Code partage (95% de l'app)
      kotlin/
        data/           Sources de donnees
          local/        SQLDelight database
          remote/       Ktor API client
          repository/   Implementations
        domain/         Logique metier
          model/        Data classes
          repository/   Interfaces
          usecase/      Cas d'utilisation
        presentation/   UI
          navigation/   Routes
          onboarding/   Ecrans d'onboarding
          splash/       Splash screen
          theme/        Couleurs, typo
        di/             Modules Koin
    androidMain/        Code specifique Android
    iosMain/            Code specifique iOS
```

## Configuration

### Pre-requis

- Android Studio Ladybug ou plus recent
- JDK 11+
- Xcode 15+ (pour iOS, macOS uniquement)

### API Key TMDb

1. Creer un compte sur [themoviedb.org](https://www.themoviedb.org/)
2. Generer une API key dans Settings > API
3. Ajouter dans `local.properties` :

```properties
TMDB_API_KEY=votre_cle_api
```

### Build

Android :
```bash
./gradlew :composeApp:assembleDebug
```

iOS (macOS uniquement) :
```bash
./gradlew :composeApp:iosSimulatorArm64Test
```

## Documentation technique

Voir [DEVLOG.md](./DEVLOG.md) pour le journal de developpement detaille avec les choix techniques et l'historique des implementations.
