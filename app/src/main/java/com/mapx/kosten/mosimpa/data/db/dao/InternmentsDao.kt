package com.mapx.kosten.mosimpa.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mapx.kosten.mosimpa.data.db.Constants.Companion.INTERNMENTS_TABLE
import com.mapx.kosten.mosimpa.data.entities.InternmentDB

@Dao
interface InternmentsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(internmentDB: InternmentDB): Long

    @Query("SELECT * FROM " + INTERNMENTS_TABLE)
    fun getAll(): LiveData<List<InternmentDB>>

    @Query("SELECT * FROM " + INTERNMENTS_TABLE + " WHERE id=:id LIMIT 1")
    suspend fun getDeviceIdById(id: Long): InternmentDB?
}