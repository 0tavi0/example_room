package com.example.roomdatabase.repository

import com.example.roomdatabase.model.Product

interface ProductRepository {
    suspend fun createProduct(product: Product): List<Product>
    suspend fun getAll(): List<Product>

}