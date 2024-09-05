package com.example.trivia.logic.enums

enum class Category(val id: String) {

    GENERAL_KNOWLEDGE("9"),
    VEHICLES("28"),
    SPORTS("21"),
    ENTERTAINMENT_VIDEO_GAMES("15"),
    ENTERTAINMENT_FILM("11"),
    SCIENCE_NATURE("17"),
    CELEBRITIES("26"),
    ANIMALS("27"),
    ENTERTAINMENT_BOOKS("10"),
    ENTERTAINMENT_MUSIC("12"),
    ENTERTAINMENT_MUSICAL_THEATRES("13"),
    ENTERTAINMENT_TELEVISION("14"),
    ENTERTAINMENT_BOARD_GAMES("16"),
    SCIENCE_COMPUTERS("18"),
    SCIENCE_MATHEMATICS("19"),
    MYTHOLOGY("20"),
    HISTORY("23"),
    GEOGRAPHY("22"),
    POLITICS("24"),
    ART("25"),
    ENTERTAINMENT_COMICS("29"),
    ENTERTAINMENT_CARTOON_ANIMATIONS("32"),
    ENTERTAINMENT_JAPANESE_ANIME_MANGA("31"),
    SCIENCE_GADGETS("30");

    companion object {
        fun fromCategoryName(category: String): Category {
            return when (category) {
                "General Knowledge" -> GENERAL_KNOWLEDGE
                "Vehicles" -> VEHICLES
                "Sports" -> SPORTS
                "Entertainment: Video Games" -> ENTERTAINMENT_VIDEO_GAMES
                "Entertainment: Film" -> ENTERTAINMENT_FILM
                "Science & Nature" -> SCIENCE_NATURE
                "Celebrities" -> CELEBRITIES
                "Animals" -> ANIMALS
                "Entertainment: Books" -> ENTERTAINMENT_BOOKS
                "Entertainment: Music" -> ENTERTAINMENT_MUSIC
                "Entertainment: Musical & Theatres" -> ENTERTAINMENT_MUSICAL_THEATRES
                "Entertainment: Television" -> ENTERTAINMENT_TELEVISION
                "Entertainment: Board Games" -> ENTERTAINMENT_BOARD_GAMES
                "Science: Computers" -> SCIENCE_COMPUTERS
                "Science: Mathematics" -> SCIENCE_MATHEMATICS
                "Mythology" -> MYTHOLOGY
                "History" -> HISTORY
                "Geography" -> GEOGRAPHY
                "Politics" -> POLITICS
                "Art" -> ART
                "Entertainment: Comics" -> ENTERTAINMENT_COMICS
                "Entertainment: Cartoon & Animations" -> ENTERTAINMENT_CARTOON_ANIMATIONS
                "Entertainment: Japanese Anime & Manga" -> ENTERTAINMENT_JAPANESE_ANIME_MANGA
                "Science: Gadgets" -> SCIENCE_GADGETS
                else -> GENERAL_KNOWLEDGE
            }
        }
    }
}
