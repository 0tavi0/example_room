package com.example.roomdatabase.repository

import com.example.roomdatabase.data.db.dao.ProductDao
import com.example.roomdatabase.data.db.toProduct
import com.example.roomdatabase.data.db.toProductEntity
import com.example.roomdatabase.model.Product

class ProductDbDataSource(
    private val productDao: ProductDao
) : ProductRepository {
    override suspend fun createProduct(product: Product): List<Product> {
        val productEntity = product.toProductEntity()
        productDao.saveProduct(productEntity)
        return getAll()
    }

    override suspend fun getAll(): List<Product> {
        val lista = productDao.getItems()
        val listProduct = ArrayList<Product>()
            for (i in lista){
                listProduct.add(i.toProduct())
            }
        return listProduct
    }
}