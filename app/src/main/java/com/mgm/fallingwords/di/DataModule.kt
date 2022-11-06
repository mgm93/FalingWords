package com.mgm.fallingwords.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mgm.fallingwords.R
import com.mgm.fallingwords.models.WordsEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideFile(@ApplicationContext context: Context): InputStream = context.resources.openRawResource(R.raw.words)

    @Provides
    @Singleton
    fun provideFileBuffer(inputStream : InputStream): CharArray = CharArray(1024)


    @Provides
    @Singleton
    fun provideLocalWords(inputStream: InputStream, buffer : CharArray):WordsEntity {
        val writer: Writer = StringWriter()
        inputStream.use { it ->
            val reader: Reader = BufferedReader(InputStreamReader(it, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        }
        val jsonString = writer.toString()
        return Gson().fromJson(jsonString, WordsEntity::class.java)
    }
}