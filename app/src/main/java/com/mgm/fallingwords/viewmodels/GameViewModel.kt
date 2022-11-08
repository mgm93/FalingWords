package com.mgm.fallingwords.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mgm.fallingwords.models.QuestionModel
import com.mgm.fallingwords.models.WordsEntity
import com.mgm.fallingwords.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Majid-Golmoradi on 11/8/2022.
 * Email: golmoradi.majid@gmail.com
 */
@HiltViewModel
class GameViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {

    val words = MutableLiveData<WordsEntity>()

    private var score = 0

    //current correct word
    var currentWordIndex = 0
    private lateinit var currentQuestion: QuestionModel

    val question = MutableLiveData<QuestionModel>()
    val scoreLiveData = MutableLiveData(score)

    //notify the view when the game is over and passing the final score to display
    val gameOverLiveData = MutableLiveData<Int>()

    init {
        fetchWords()
    }

    fun fetchWords() = viewModelScope.launch {
        val data = repository.fetchData()
        words.value = data
        sendNewWord()
    }

    /**
     * If user doesn't select any answer
     */
    fun onNoAnswer() {
        gameOverLiveData.value = score
    }

    /**
     * If user select the current question translation is correct
     */
    fun onCorrectTransClicked() {
        if (currentQuestion.translation == words.value?.get(currentWordIndex)!!.text_spa) {
            score += 1
        } else {
            gameOverLiveData.value = score
        }
        scoreLiveData.value = score

        sendNewWord()
    }

    /**
     * If user select the current question translation is wrong
     */
    fun onWrongTransClicked() {
        if (currentQuestion.translation != words.value?.get(currentWordIndex)!!.text_spa) {
            score += 1
            scoreLiveData.value = score
        } else {
            gameOverLiveData.value = score
        }
        scoreLiveData.value = score

        sendNewWord()
    }

    /**
     * this function for loading new question and pass it to the view
     * and validating is the game should continue or GameOver
     */
    private fun sendNewWord() {

        //generate random number to decide to show the correct translation or wrong one
        currentWordIndex = if (words.value!!.size > 1) (0 until words.value!!.size).random() else 0
        val wrongIndex= if (words.value!!.size > 1) (0 until words.value!!.size).random() else 0
        val rnd = (0 until 2).random()
        currentQuestion = if (rnd % 2 == 0) {
            //use the correct translation
            QuestionModel(
                words.value!![currentWordIndex].text_eng,
                words.value!![currentWordIndex].text_spa
            )
        } else {
            //use wrong translation
            QuestionModel(words.value!![currentWordIndex].text_eng, words.value!![wrongIndex].text_spa)
        }

        question.value = currentQuestion
    }

}