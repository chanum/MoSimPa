package com.mapx.kosten.mosimpa.data.db.dao

import androidx.room.*
import com.mapx.kosten.mosimpa.data.db.Constants.Companion.INTERNMENTS_TABLE
import com.mapx.kosten.mosimpa.data.entities.InternmentDB
import kotlinx.coroutines.flow.Flow

@Dao
interface InternmentsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(internment: InternmentDB): Long

    @Query("SELECT * FROM " + INTERNMENTS_TABLE)
    fun getAll(): Flow<List<InternmentDB>>

    @Query("SELECT * FROM " + INTERNMENTS_TABLE + " WHERE id=:id LIMIT 1")
    suspend fun getDeviceIdById(id: Long): InternmentDB?
}