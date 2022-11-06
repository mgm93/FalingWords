package com.mgm.fallingwords.models

class WordsEntity : ArrayList<WordsEntity.WordEntityItem>(){
    data class WordEntityItem(
        val text_eng: String = "",
        val text_spa: String = ""
    )
}