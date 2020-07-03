package com.example.roomdatabase.repository

import com.example.roomdatabase.model.Product

interface ProductRepository {
    suspend fun insertProduct(nameProduct: String): List<Product>
    suspend fun getAll(): List<Product>
    suspend fun markCompleted(product: Product): List<Product>

}