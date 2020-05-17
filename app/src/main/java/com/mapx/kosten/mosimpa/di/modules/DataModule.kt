package com.mapx.kosten.mosimpa.di.modules

import android.content.Context
import androidx.room.Room
import com.mapx.kosten.mosimpa.data.db.MosimpaDatabase
import com.mapx.kosten.mosimpa.data.repositories.InternmentsRepositoryImpl
import com.mapx.kosten.mosimpa.data.repositories.SensorsRepositoryImpl
import com.mapx.kosten.mosimpa.data.repositories.ServersRepositoryImpl
import com.mapx.kosten.mosimpa.data.repositories.SettingsRepositoryImpl
import com.mapx.kosten.mosimpa.domain.data.InternmentsRepository
import com.mapx.kosten.mosimpa.domain.data.SensorsRepository
import com.mapx.kosten.mosimpa.domain.data.ServersRepository
import com.mapx.kosten.mosimpa.domain.data.SettingsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context): MosimpaDatabase {
        return Room.databaseBuilder(
            context,
            MosimpaDatabase::class.java,
            "mosimpa_db")
            .build()
    }

    @Singleton
    @Provides
    fun provideSensorsRepository(
        context: Context,
        database: MosimpaDatabase
    ): SensorsRepository {
        return SensorsRepositoryImpl(context, database)
    }

    @Singleton
    @Provides
    fun provideSettingsRepository(
        context: Context
    ): SettingsRepository {
        return SettingsRepositoryImpl(context)
    }

    @Singleton
    @Provides
    fun provideInternmentsRepository(
        database: MosimpaDatabase
    ): InternmentsRepository {
        return InternmentsRepositoryImpl(database)
    }

    @Singleton
    @Provides
    fun provideServersRepository(
        database: MosimpaDatabase
    ): ServersRepository {
        return ServersRepositoryImpl(database)
    }
}