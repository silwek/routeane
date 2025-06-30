package com.silwek.routeane.data.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.silwek.routeane.data.AppDatabase
import com.silwek.routeane.data.populateDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RoutineDayAssignmentDaoTest {

    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testGetAssignmentsWithItemsForDayAndModeFlow() = runTest {
        populateDatabase(db)

        val dao = db.routineDayAssignmentDao()
        val today = 1 // Monday
        val modeId = 1 // default

        val assignments = dao.getAssignmentsWithItemsForDayAndModeFlow(today, modeId).first()

        // Should return Make bed, Brush teeth (tous les jours) et Yoga (lundi)
        assertEquals(3, assignments.size)
        assertEquals("Make bed", assignments[0].itemName)
        assertEquals("Brush teeth", assignments[1].itemName)
        assertEquals("Yoga", assignments[2].itemName)
    }
}