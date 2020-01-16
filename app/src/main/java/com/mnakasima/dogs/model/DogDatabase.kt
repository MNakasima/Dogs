package com.mnakasima.dogs.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(DogBreed::class), version = 1)

abstract class DogDatabase:RoomDatabase() {

    abstract fun DogDao():DogDAO

    companion object{
        @Volatile private var instance:DogDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context:Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatatabase(context).also{
                instance= it
            }
        }

        private fun buildDatatabase(context:Context) = Room.databaseBuilder(
            context.applicationContext,
            DogDatabase::class.java,
            "dogdatabase"
        ).build()
    }
}