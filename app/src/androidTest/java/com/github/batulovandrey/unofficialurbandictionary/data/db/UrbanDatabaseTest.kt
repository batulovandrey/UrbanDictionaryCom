package com.github.batulovandrey.unofficialurbandictionary.data.db

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.Definition
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.QueryDao
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.SavedUserQuery
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UrbanDatabaseTest {

    private lateinit var db: UrbanDatabase
    private lateinit var definitionDao: DefinitionDao
    private lateinit var queryDao: QueryDao

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, UrbanDatabase::class.java).build()
        definitionDao = db.definitionDataDao()
        queryDao = db.queryDataDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadDefinition() {
        val definition = Definition(0,
                "definition",
                "permalink",
                1,
                2,
                "author",
                "word",
                "example")
        definitionDao.insert(definition)
        val definitions = definitionDao.getAll().blockingFirst()
        val result = definitions[0]
        assertThat(definition, equalTo(result))
    }

    @Test
    fun writeAndReadFavoriteDefinition() {
        val definition = Definition(0,
                "definition",
                "permalink",
                1,
                2,
                "author",
                "word",
                "example",
                1)
        definitionDao.insert(definition)
        val favoriteOfDefinitions = definitionDao.getAllFavorites()
        val result = favoriteOfDefinitions[0]
        assertThat(definition, equalTo(result))
    }

    @Test
    fun deleteFavoriteDefinition() {
        val definition = Definition(0,
                "definition",
                "permalink",
                1,
                2,
                "author",
                "word",
                "example",
                1)
        definitionDao.insert(definition)

        assertTrue(definitionDao.getAllFavorites().isNotEmpty())

        definitionDao.delete(definition)
        assertTrue(definitionDao.getAllFavorites().isEmpty())
    }

    @Test
    fun writeAndReadQueries() {
        val query = SavedUserQuery(0, "text")
        queryDao.insert(query)
        val queries = queryDao.getAll()
        val result = queries[0]
        assertThat(query, equalTo(result))
    }

    @Test
    fun deleteQueries() {
        queryDao.insert(SavedUserQuery(0, "text"))
        queryDao.deleteAll()
        assertTrue(queryDao.getAll().isEmpty())
    }

    @Test
    fun deleteQuery() {
        val query = SavedUserQuery(0, "test")
        queryDao.insert(query)
        queryDao.delete(query)
        assertTrue(queryDao.getAll().isEmpty())
    }
}