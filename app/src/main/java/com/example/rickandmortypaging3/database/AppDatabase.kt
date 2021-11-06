package com.example.rickandmortypaging3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rickandmortypaging3.network.model.PojoResult

@Database(entities = [PojoResult::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private const val DB_NAME = "main.db"
        private var db: AppDatabase? = null
        private val LOCK = Any()
        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK) {
                val instance = Room.databaseBuilder(
                    context, AppDatabase::class.java, DB_NAME
                )
                    .build()
                db = instance
                return instance
            }
        }
    }
    abstract fun characterDao(): CharacterDao
}