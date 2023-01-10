package com.example.fishka.data;


import android.content.Context
import androidx.room.Database;
import androidx.room.Room
import androidx.room.RoomDatabase;
import com.example.fishka.model.Concept

@Database(entities = [Concept::class], version = 1, exportSchema = false)
abstract class ConceptDatabase: RoomDatabase() {

    abstract fun conceptDao(): ConceptDao

    companion object{
        @Volatile
        private var INSTANCE: ConceptDatabase? = null

        fun getDatabase(context: Context): ConceptDatabase{
            var tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ConceptDatabase::class.java,
                    "concept_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
