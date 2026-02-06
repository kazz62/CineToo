package com.kazz.cinetoo.data.remote.dto.imdb

import com.kazz.cinetoo.domain.model.*

// Genre emoji mapping
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
    "Sci-Fi" to "🚀",
    "Thriller" to "😱",
    "War" to "⚔️",
    "Western" to "🤠"
)

fun TitleDto.toDomain(): Any {
    val genresList = genres.map { genreName ->
        Genre(
            id = genreName.hashCode(),
            name = genreName,
            emoji = genreEmojiMap[genreName] ?: "🎬"
        )
    }

    return when (type) {
        "MOVIE", "SHORT", "VIDEO", "TV_MOVIE" -> Movie(
            id = id.hashCode(),
            title = primaryTitle,
            overview = plot?.text ?: "",
            posterPath = primaryImage?.url,
            backdropPath = primaryImage?.url,
            releaseDate = startYear?.toString() ?: "",
            voteAverage = rating?.averageRating ?: 0.0,
            genres = genresList,
            director = null,
            duration = runtimeSeconds?.div(60),
            cast = emptyList(),
            availableOn = emptyList()
        )
        "TV_SERIES", "TV_MINI_SERIES", "TV_SPECIAL" -> TVShow(
            id = id.hashCode(),
            name = primaryTitle,
            overview = plot?.text ?: "",
            posterPath = primaryImage?.url,
            backdropPath = primaryImage?.url,
            firstAirDate = startYear?.toString() ?: "",
            voteAverage = rating?.averageRating ?: 0.0,
            genres = genresList,
            numberOfSeasons = 0,
            cast = emptyList(),
            availableOn = emptyList()
        )
        else -> Movie(
            id = id.hashCode(),
            title = primaryTitle,
            overview = plot?.text ?: "",
            posterPath = primaryImage?.url,
            backdropPath = primaryImage?.url,
            releaseDate = startYear?.toString() ?: "",
            voteAverage = rating?.averageRating ?: 0.0,
            genres = genresList,
            director = null,
            duration = runtimeSeconds?.div(60),
            cast = emptyList(),
            availableOn = emptyList()
        )
    }
}

fun TitleDetailsDto.toDomainMovie(): Movie {
    val genresList = genres.map { genreName ->
        Genre(
            id = genreName.hashCode(),
            name = genreName,
            emoji = genreEmojiMap[genreName] ?: "🎬"
        )
    }

    val castList = stars.map { person ->
        CastMember(
            id = person.id.hashCode(),
            name = person.name,
            character = null,
            profilePath = person.profileImage?.url
        )
    }

    return Movie(
        id = id.hashCode(),
        title = primaryTitle,
        overview = plot?.text ?: "",
        posterPath = primaryImage?.url,
        backdropPath = primaryImage?.url,
        releaseDate = startYear?.toString() ?: "",
        voteAverage = rating?.averageRating ?: 0.0,
        genres = genresList,
        director = directors.firstOrNull()?.name,
        duration = runtimeSeconds?.div(60),
        cast = castList,
        availableOn = emptyList()
    )
}

fun TitleDetailsDto.toDomainTVShow(): TVShow {
    val genresList = genres.map { genreName ->
        Genre(
            id = genreName.hashCode(),
            name = genreName,
            emoji = genreEmojiMap[genreName] ?: "🎬"
        )
    }

    val castList = stars.map { person ->
        CastMember(
            id = person.id.hashCode(),
            name = person.name,
            character = null,
            profilePath = person.profileImage?.url
        )
    }

    return TVShow(
        id = id.hashCode(),
        name = primaryTitle,
        overview = plot?.text ?: "",
        posterPath = primaryImage?.url,
        backdropPath = primaryImage?.url,
        firstAirDate = startYear?.toString() ?: "",
        voteAverage = rating?.averageRating ?: 0.0,
        genres = genresList,
        numberOfSeasons = 0,
        cast = castList,
        availableOn = emptyList()
    )
}

fun CastMemberDto.toDomain(): CastMember {
    return CastMember(
        id = id.hashCode(),
        name = name,
        character = characters.firstOrNull(),
        profilePath = profileImage?.url
    )
}
