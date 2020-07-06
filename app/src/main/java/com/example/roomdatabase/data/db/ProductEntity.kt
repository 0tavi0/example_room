package com.example.roomdatabase.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.roomdatabase.model.Product

@Entity(tableName = "produtos")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nameProduct: String,
    val completed: Int = 0
)

//Extension Convert to productEntity
fun Product.toProductEntity(): ProductEntity {
    return with(this) {
        ProductEntity(
            nameProduct = this.productName,
            completed = this.completed
        )
    }
}

fun ProductEntity.toProduct(): Product {
    return Product(
        id = this.id,
        productName = this.nameProduct,
        completed = this.completed
    )
}