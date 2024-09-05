package com.example.trivia.logic.enums

enum class Category(val id: String, val categoryName: String) {

    GENERAL_KNOWLEDGE("9", "General Knowledge"),
    VEHICLES("28", "Vehicles"),
    SPORTS("21", "Sports"),
    ENTERTAINMENT_VIDEO_GAMES("15", "Entertainment: Video Games"),
    ENTERTAINMENT_FILM("11", "Entertainment: Film"),
    SCIENCE_NATURE("17", "Science & Nature"),
    CELEBRITIES("26", "Celebrities"),
    ANIMALS("27", "Animals"),
    ENTERTAINMENT_BOOKS("10", "Entertainment: Books"),
    ENTERTAINMENT_MUSIC("12", "Entertainment: Music"),
    ENTERTAINMENT_MUSICAL_THEATRES("13", "Entertainment: Musical & Theatres"),
    ENTERTAINMENT_TELEVISION("14", "Entertainment: Television"),
    ENTERTAINMENT_BOARD_GAMES("16", "Entertainment: Board Games"),
    SCIENCE_COMPUTERS("18", "Science: Computers"),
    SCIENCE_MATHEMATICS("19", "Science: Mathematics"),
    MYTHOLOGY("20", "Mythology"),
    HISTORY("23", "History"),
    GEOGRAPHY("22", "Geography"),
    POLITICS("24", "Politics"),
    ART("25", "Art"),
    ENTERTAINMENT_COMICS("29", "Entertainment: Comics"),
    ENTERTAINMENT_CARTOON_ANIMATIONS("32", "Entertainment: Cartoon & Animations"),
    ENTERTAINMENT_JAPANESE_ANIME_MANGA("31", "Entertainment: Japanese Anime & Manga"),
    SCIENCE_GADGETS("30", "Science: Gadgets");


    companion object {
        fun fromCategoryName(category: String): Category {
            return entries.find { it.categoryName == category } ?: GENERAL_KNOWLEDGE // Default
        }

        fun fromId(id: String): Category {
            return entries.find { it.id == id } ?: GENERAL_KNOWLEDGE // Default
        }


    }
}
