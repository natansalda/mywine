package pl.nataliana.mywine.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import pl.nataliana.mywine.model.Wine

@Database(entities = [Wine::class], version = 11, exportSchema = false)
abstract class WineDatabase : RoomDatabase() {

    abstract val wineDatabaseDao: WineDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: WineDatabase? = null

        fun getInstance(context: Context): WineDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WineDatabase::class.java,
                        "wines_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addMigrations(MIGRATION_8_9, MIGRATION_9_10)
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

        private val MIGRATION_8_9: Migration = object : Migration(8, 9) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS wines_table_new (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT NOT NULL, color TEXT NOT NULL, year INTEGER, rate REAL, price INTEGER NOT NULL, type TEXT)"
                )
                database.execSQL(
                    "INSERT INTO wines_table_new (id, name, color, year, rate, price, type) SELECT id, name, color, year, rate, price, type FROM wines_table"
                )
                database.execSQL("DROP TABLE wines_table")
                database.execSQL("ALTER TABLE wines_table_new RENAME TO wines_table")
            }
        }

        private val MIGRATION_9_10: Migration = object : Migration(9, 10) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `wines_table_new` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `color` TEXT NOT NULL, `year` INTEGER, `rate` REAL, `price` REAL NOT NULL, `type` TEXT)"
                )
                database.execSQL(
                    "INSERT INTO wines_table_new (id, name, color, year, rate, price, type) SELECT id, name, color, year, rate, price, type FROM wines_table"
                )
                database.execSQL("DROP TABLE wines_table")
                database.execSQL("ALTER TABLE wines_table_new RENAME TO wines_table")
            }
        }
    }
}
