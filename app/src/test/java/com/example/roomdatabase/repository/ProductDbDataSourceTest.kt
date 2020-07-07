package com.example.roomdatabase.repository

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.roomdatabase.data.db.AppDatabase
import com.example.roomdatabase.data.db.dao.ProductDao
import com.example.roomdatabase.model.Product
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1], manifest = Config.NONE)
class ProductDbDataSourceTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    private var dao: ProductDao = mockk()
    private var database: AppDatabase = mockk()
    private var productDbDataSource: ProductDbDataSource = mockk()


    @Before
    fun setUp() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        database =
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries()
                .build()
        dao = database.productDao()

        productDbDataSource = ProductDbDataSource(dao)

        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        database.close()
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()

    }

    @Test
    fun `when insertProduct called then it should list bigger zero`() {
        runBlockingTest {
            productDbDataSource.insertProduct("teste")
            val result = productDbDataSource.getAll()
            assertEquals(1, result.size)

        }

    }

    @Test
    fun `when markCompleted is called then must mark as complete`() {
        runBlockingTest {
           val items = getBaseList()
            addItems(items)

            productDbDataSource.markCompleted(items[0])
            val result = productDbDataSource.getAll()
            assertEquals(1, result[0].completed)
        }

    }

    @Test
    fun `when delete product is called then it should deleted product `() {
        runBlockingTest {

            val item = getBaseList()
            addItems(item)
            productDbDataSource.deleteProduct(item[0])
            val result = productDbDataSource.getAll()
            assertEquals(2, result.size)

        }

    }

    private fun getBaseList() = listOf(
        Product(1, "Task 1", 0),
        Product(2, "Task 3", 0),
        Product(3, "Task 4", 0)
    )

    private fun addItems(list: List<Product>) = runBlockingTest {
        list.forEach {
            productDbDataSource.insertProduct(it.productName)
        }
    }
}