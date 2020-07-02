package com.example.roomdatabase.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.roomdatabase.model.Product

@Entity(tableName = "produtos")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nameProduct: String
)

//Extension Convert to productEntity
fun Product.toProductEntity(): ProductEntity {
    return with(this) {
        ProductEntity(
            nameProduct = this.productName
        )
    }
}

fun ProductEntity.toProduct() : Product{
    return Product(
        productName = this.nameProduct
    )
}