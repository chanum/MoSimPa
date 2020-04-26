package com.mapx.kosten.mosimpa.data.db.dao

import androidx.room.*
import com.mapx.kosten.mosimpa.data.entities.LocationDB

@Dao
interface LocationDao {

    @Insert
    suspend fun insert(location: LocationDB): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(location: LocationDB): Int

}