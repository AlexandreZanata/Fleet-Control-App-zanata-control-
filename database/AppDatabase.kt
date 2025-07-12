package com.alexandre.controledefrota.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alexandre.controledefrota.dao.HistoricoDao
import com.alexandre.controledefrota.dao.VeiculoDao
import com.alexandre.controledefrota.model.Historico
import com.alexandre.controledefrota.model.Veiculo
import com.alexandre.controledefrota.util.Converters

@Database(entities = [Veiculo::class, Historico::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun veiculoDao(): VeiculoDao
    abstract fun historicoDao(): HistoricoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "controle_frota_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}