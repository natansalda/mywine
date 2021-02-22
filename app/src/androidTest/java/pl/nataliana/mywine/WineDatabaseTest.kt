package pl.nataliana.mywine

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import pl.nataliana.mywine.database.WineDatabase
import pl.nataliana.mywine.database.WineDatabaseDao
import pl.nataliana.mywine.model.Wine
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class WineDatabaseTest {

    private lateinit var wineDao: WineDatabaseDao
    private lateinit var db: WineDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, WineDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        wineDao = db.wineDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetWine() {
        val wine = Wine("Wine test", "red", 1234, 3F, 23.0, "dry")
        wineDao.insert(wine)
        val oneWine = wineDao.getWineDetails(1)
        assertEquals(oneWine?.color, "red")
    }
}