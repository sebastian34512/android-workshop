package com.it231522.demo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact (
    val name: String = "",
    val phone: String = "",
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)