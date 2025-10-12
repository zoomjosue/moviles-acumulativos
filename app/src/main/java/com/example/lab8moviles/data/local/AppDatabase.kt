package com.example.lab8moviles.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lab8moviles.data.local.dao.CharacterDao
import com.example.lab8moviles.data.local.dao.LocationDao
import com.example.lab8moviles.data.local.entity.CharacterEntity
import com.example.lab8moviles.data.local.entity.LocationEntity

// Josué García - 24918
@Database(
    entities = [CharacterEntity::class, LocationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
    abstract fun locationDao(): LocationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "rick_and_morty_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}