package pl.nataliana.mywine.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import pl.nataliana.mywine.model.Wine

@Database(entities = [Wine::class], version = 10, exportSchema = false)
abstract class WineDatabase : RoomDatabase() {

    abstract val wineDatabaseDao: WineDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: WineDatabase? = null

        private val MIGRATION_9_10 = object : Migration(9, 10) {
            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, `name` TEXT, " +
//                        "PRIMARY KEY(`id`))")
            }
        }

        fun getInstance(context: Context): WineDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WineDatabase::class.java,
                        "wines_database"
                    )
                        .addMigrations(MIGRATION_9_10)
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
