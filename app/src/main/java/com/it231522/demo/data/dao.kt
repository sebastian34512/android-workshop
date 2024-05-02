package com.it231522.demo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface dao {
    @Insert
    suspend fun insertContact (contact: Contact)

    @Delete
    suspend fun deleteContact( contact: Contact)

    @Update
    suspend fun updateContact (contact: Contact)

    @Query("SELECT * FROM contacts")
    fun getContacts(): Flow<List<Contact>>
}