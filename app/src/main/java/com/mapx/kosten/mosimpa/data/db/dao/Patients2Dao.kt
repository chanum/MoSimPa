package com.mapx.kosten.mosimpa.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mapx.kosten.mosimpa.data.entities.PatientDB2

@Dao
interface Patients2Dao {

    @Insert
    suspend fun insert(patient: PatientDB2): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(patient: PatientDB2): Int

}