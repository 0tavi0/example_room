package com.example.roomdatabase.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomdatabase.data.db.ProductEntity

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProduct(product: ProductEntity)

    @Query("SELECT * FROM produtos ORDER BY id")
    suspend fun getItems(): List<ProductEntity>

    @Query("UPDATE produtos SET completed = 1 WHERE id = :id")
    suspend fun markCompleted(id: Int)

    @Query("DELETE FROM produtos WHERE id = :id ")
    suspend fun deleteProduct(id: Int)
}