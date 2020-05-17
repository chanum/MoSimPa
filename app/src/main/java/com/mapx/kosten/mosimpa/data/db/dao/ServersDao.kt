package com.mapx.kosten.mosimpa.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mapx.kosten.mosimpa.data.db.Constants.Companion.SERVERS_TABLE
import com.mapx.kosten.mosimpa.data.entities.ServerDB

@Dao
interface ServersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(server: ServerDB): Long

    @Query("SELECT * FROM " + SERVERS_TABLE)
    fun getAll(): LiveData<List<ServerDB>>

    @Query("DELETE FROM " + SERVERS_TABLE + " WHERE id=:id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM " + SERVERS_TABLE + " WHERE name=:name")
    suspend fun deleteByName(name: String)

    @Query("SELECT * FROM " + SERVERS_TABLE + " WHERE name=:name LIMIT 1")
    suspend fun getServerByName(name: String): ServerDB?
}
