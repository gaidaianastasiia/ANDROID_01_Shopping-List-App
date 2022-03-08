package com.example.android_shopping_list_app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "productOwnerId") val productOwnerId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "amount") val amount: String,
    @ColumnInfo(name = "activeState") val activeState: Boolean,
)
