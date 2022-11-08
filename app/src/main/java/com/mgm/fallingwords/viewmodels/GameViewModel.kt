package com.mgm.fallingwords.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun fetchWords() = viewModelScope.launch {
        val data = repository.fetchData()
        words.postValue(data)
    }
}