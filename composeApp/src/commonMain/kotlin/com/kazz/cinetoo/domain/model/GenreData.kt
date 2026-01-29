package com.kazz.cinetoo.domain.model

/**
 * Predefined list of genres matching TMDb IDs with emojis for UI display
 */
object GenreData {
    val availableGenres = listOf(
        Genre(id = 28, name = "Action", emoji = "\uD83D\uDCA5"),
        Genre(id = 12, name = "Adventure", emoji = "\uD83C\uDF0D"),
        Genre(id = 16, name = "Animation", emoji = "\uD83C\uDFA8"),
        Genre(id = 7424, name = "Anime", emoji = "\uD83D\uDC79"),
        Genre(id = 35, name = "Comedy", emoji = "\uD83E\uDD23"),
        Genre(id = 80, name = "Crime", emoji = "\uD83D\uDE94"),
        Genre(id = 99, name = "Documentary", emoji = "\uD83C\uDF0E"),
        Genre(id = 18, name = "Drama", emoji = "\uD83C\uDFAD"),
        Genre(id = 10751, name = "Family", emoji = "\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67"),
        Genre(id = 14, name = "Fantasy", emoji = "\uD83E\uDDD9"),
        Genre(id = 10764, name = "Game show", emoji = "\uD83C\uDFB0"),
        Genre(id = 27, name = "Horror", emoji = "\uD83D\uDC7B"),
        Genre(id = 10769, name = "Language", emoji = "\uD83D\uDDE3\uFE0F"),
        Genre(id = 10763, name = "Lifestyle", emoji = "\uD83C\uDFE0"),
        Genre(id = 10402, name = "Music", emoji = "\uD83C\uDFB5"),
        Genre(id = 10403, name = "Musical", emoji = "\uD83D\uDC83"),
        Genre(id = 9648, name = "Mystery", emoji = "\uD83D\uDD75\uFE0F"),
        Genre(id = 10767, name = "Reality TV", emoji = "\uD83D\uDCFA"),
        Genre(id = 10749, name = "Romance", emoji = "\uD83D\uDC96"),
        Genre(id = 878, name = "Sci-Fi", emoji = "\uD83D\uDE80"),
        Genre(id = 10770, name = "Seasonal", emoji = "\u2744\uFE0F"),
        Genre(id = 10766, name = "Short", emoji = "\uD83C\uDFAC"),
        Genre(id = 10765, name = "Sport", emoji = "\u26BD"),
        Genre(id = 53, name = "Thriller", emoji = "\uD83D\uDE28"),
        Genre(id = 37, name = "Western", emoji = "\uD83E\uDD20")
    )
}
