package com.example.roomdatabase.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.roomdatabase.model.Product
import com.example.roomdatabase.repository.ProductRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivityViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val _listProducts = MutableLiveData<List<Product>>()
    val listProduct: LiveData<List<Product>> get() = _listProducts

    fun saveProduct(product: Product) {
        viewModelScope.launch {
            try {
                val listProducts = productRepository.createProduct(product)
                _listProducts.value = listProducts

            }catch (e: Exception){
                Log.e("Erro","$e")
            }

        }
    }

    fun getAll(){
        viewModelScope.launch {
            try {
                _listProducts.value = productRepository.getAll()

            }catch (e:Exception){
                Log.e("Erro","$e")

            }
        }
    }

    class MainActivityViewModelFactory(private val productRepository: ProductRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainActivityViewModel(productRepository) as T
        }
    }
}