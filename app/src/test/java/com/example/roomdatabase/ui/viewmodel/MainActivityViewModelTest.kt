package com.example.roomdatabase.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.roomdatabase.model.Product
import com.example.roomdatabase.repository.ProductRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainActivityViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    private val productRepositoryMock: ProductRepository = mockk()
    private val listProductsMockk: Observer<List<Product>> = mockk(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when products is called then it should call repository to fetch products`() {
        coEvery {
            productRepositoryMock.insertProduct(any())
        } returns listProductsMocked()
        instantiationViewModel().saveProduct("teste")
        coVerify { productRepositoryMock.insertProduct("teste") }

    }

    @Test
    fun `when getAll is called it should list all`() {
        coEvery {
            productRepositoryMock.getAll()
        } returns listProductsMocked()

        instantiationViewModel().getAll()
        coVerify { listProductsMockk.onChanged(listProductsMocked()) }
    }

    @Test
    fun `when insertProduct is called it should list count`() {
        coEvery {
            productRepositoryMock.insertProduct(any())
        } returns listProductsMocked()

        instantiationViewModel().saveProduct("any")
        assertEquals(0, instantiationViewModel().productSize())

    }

    @Test
    fun `when markCompleted is called it should list`() {
        coEvery {
            productRepositoryMock.markCompleted(any())
        } returns listProductsMocked()

        instantiationViewModel().markCompleted(Product(1, "teste", 1))
        coVerify { listProductsMockk.onChanged(listProductsMocked()) }

    }

    @Test
    fun `when delete is called it should list`() {
        coEvery {
            productRepositoryMock.deleteProduct(any())
        } returns listProductsMocked()

        instantiationViewModel().deleteProduct(Product(1, "teste", 1))
        coVerify { listProductsMockk.onChanged(listProductsMocked()) }

    }


    private fun instantiationViewModel(): MainActivityViewModel {
        val viewModel = MainActivityViewModel(productRepositoryMock)
        viewModel.listProduct.observeForever(listProductsMockk)
        return viewModel
    }

    private fun listProductsMocked(): List<Product> {
        return listOf(
            Product(1, "teste1", 1),
            Product(2, "teste2", 1),
            Product(3, "teste3", 1)
        )

    }

}