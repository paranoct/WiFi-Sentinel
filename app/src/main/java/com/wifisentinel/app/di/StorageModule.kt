package com.wifisentinel.app.di

import android.content.Context
import androidx.room.Room
import com.wifisentinel.core.storage.DefaultNetworkRepository
import com.wifisentinel.core.storage.NetworkRepository
import com.wifisentinel.core.storage.db.MIGRATION_2_3
import com.wifisentinel.core.storage.db.MIGRATION_3_4
import com.wifisentinel.core.storage.db.WiFiSentinelDatabase
import com.wifisentinel.core.storage.settings.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WiFiSentinelDatabase {
        return Room.databaseBuilder(
            context,
            WiFiSentinelDatabase::class.java,
            "wifi_sentinel.db"
        ).addMigrations(MIGRATION_2_3, MIGRATION_3_4).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideNetworkRepository(db: WiFiSentinelDatabase): NetworkRepository {
        return DefaultNetworkRepository(
            snapshotDao = db.snapshotDao(),
            findingDao = db.findingDao(),
            trustedNetworkDao = db.trustedNetworkDao(),
            eventDao = db.eventDao()
        )
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(@ApplicationContext context: Context): SettingsRepository {
        return SettingsRepository(context)
    }
}
