package com.mgm.fallingwords.repository

import com.mgm.fallingwords.models.WordsEntity
import javax.inject.Inject

class DataRepository @Inject constructor(private val wordsEntity: WordsEntity) {

    fun fetchData() = wordsEntity
}