package com.mgm.fallingwords.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Majid-Golmoradi on 11/8/2022.
 * Email: golmoradi.majid@gmail.com
 */
class StoreUserData @Inject constructor(@ApplicationContext val context: Context) {
    companion object{
        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
            SCORE_DATA_STORE)
        val score = stringPreferencesKey(SCORE)
    }

    suspend fun saveUserScore(newScore:String){
        context.dataStore.edit {
            it[score] = newScore
        }
    }

    fun getUserScore() = context.dataStore.data.map { it[score]?.toInt() ?: 0}
}