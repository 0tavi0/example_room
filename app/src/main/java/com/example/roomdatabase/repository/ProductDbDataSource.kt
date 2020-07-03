package com.example.roomdatabase.repository

import com.example.roomdatabase.data.db.ProductEntity
import com.example.roomdatabase.data.db.dao.ProductDao
import com.example.roomdatabase.data.db.toProduct
import com.example.roomdatabase.model.Product

class ProductDbDataSource(
    private val productDao: ProductDao
) : ProductRepository {
    override suspend fun insertProduct(nameProduct: String): List<Product> {
        val productEntity = ProductEntity(nameProduct = nameProduct)
        productDao.saveProduct(productEntity)
        return getAll()
    }

    override suspend fun getAll(): List<Product> {
        val lista = productDao.getItems()
        val listProduct = ArrayList<Product>()
        for (i in lista) {
            listProduct.add(i.toProduct())
        }
        return listProduct
    }

    override suspend fun markCompleted(product: Product): List<Product> {
        productDao.markCompleted(product.id.toInt())
        return getAll()
    }
}