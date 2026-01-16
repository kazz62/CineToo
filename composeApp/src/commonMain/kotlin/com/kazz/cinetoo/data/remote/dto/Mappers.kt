package com.kazz.cinetoo.data.remote.dto

import com.kazz.cinetoo.data.remote.api.ApiConstants
import com.kazz.cinetoo.domain.model.*

// Genre emoji mapping based on genre name
private val genreEmojiMap = mapOf(
    "Action" to "💥",
    "Adventure" to "🗻",
    "Animation" to "🎨",
    "Comedy" to "😂",
    "Crime" to "🔫",
    "Documentary" to "📹",
    "Drama" to "🎭",
    "Family" to "👨‍👩‍👧‍👦",
    "Fantasy" to "🧙",
    "History" to "📜",
    "Horror" to "👻",
    "Music" to "🎵",
    "Mystery" to "🔍",
    "Romance" to "❤️",
    "Science Fiction" to "🚀",
    "TV Movie" to "📺",
    "Thriller" to "😱",
    "War" to "⚔️",
    "Western" to "🤠"
)

fun GenreDto.toDomain(): Genre {
    return Genre(
        id = id,
        name = name,
        emoji = genreEmojiMap[name] ?: "🎬"
    )
}

fun CastMemberDto.toDomain(): CastMember {
    return CastMember(
        id = id,
        name = name,
        character = character,
        profilePath = ApiConstants.getPosterUrl(profilePath)
    )
}

fun WatchProviderDto.toDomain(): StreamingPlatform {
    return StreamingPlatform(
        id = providerId,
        name = providerName,
        logoPath = ApiConstants.getPosterUrl(logoPath)
    )
}

fun MovieDetailsDto.toDomain(): Movie {
    val director = credits?.crew?.firstOrNull { it.job == "Director" }?.name
    val castList = credits?.cast?.take(10)?.map { it.toDomain() } ?: emptyList()

    // Get streaming providers (prioritize flatrate/subscription)
    val providers = watchProviders?.results?.get("US")?.flatrate
        ?: watchProviders?.results?.get("FR")?.flatrate
        ?: emptyList()

    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = ApiConstants.getPosterUrl(posterPath),
        backdropPath = ApiConstants.getBackdropUrl(backdropPath),
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        genres = genres.map { it.toDomain() },
        director = director,
        duration = runtime,
        cast = castList,
        availableOn = providers.map { it.toDomain() }
    )
}

fun TVShowDetailsDto.toDomain(): TVShow {
    val castList = credits?.cast?.take(10)?.map { it.toDomain() } ?: emptyList()

    // Get streaming providers (prioritize flatrate/subscription)
    val providers = watchProviders?.results?.get("US")?.flatrate
        ?: watchProviders?.results?.get("FR")?.flatrate
        ?: emptyList()

    return TVShow(
        id = id,
        name = name,
        overview = overview,
        posterPath = ApiConstants.getPosterUrl(posterPath),
        backdropPath = ApiConstants.getBackdropUrl(backdropPath),
        firstAirDate = firstAirDate,
        voteAverage = voteAverage,
        genres = genres.map { it.toDomain() },
        numberOfSeasons = numberOfSeasons,
        cast = castList,
        availableOn = providers.map { it.toDomain() }
    )
}
