package com.wifisentinel.core.storage.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE trusted_networks ADD COLUMN category TEXT NOT NULL DEFAULT 'HOME'")
        db.execSQL("ALTER TABLE trusted_networks ADD COLUMN meshMode INTEGER NOT NULL DEFAULT 0")
        db.execSQL("ALTER TABLE trusted_networks ADD COLUMN pinnedDns TEXT NOT NULL DEFAULT '[]'")
        db.execSQL("CREATE INDEX IF NOT EXISTS index_trusted_networks_category ON trusted_networks(category)")
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE trusted_networks ADD COLUMN maxNewBssidPerDay INTEGER NOT NULL DEFAULT 3")
        db.execSQL("ALTER TABLE trusted_networks ADD COLUMN bssidLearning INTEGER NOT NULL DEFAULT 0")
        db.execSQL("UPDATE trusted_networks SET maxNewBssidPerDay = 1 WHERE category = 'HOME'")
        db.execSQL("UPDATE trusted_networks SET maxNewBssidPerDay = 3 WHERE category = 'WORK'")
        db.execSQL("UPDATE trusted_networks SET maxNewBssidPerDay = 10 WHERE category = 'PUBLIC'")
        db.execSQL("ALTER TABLE findings ADD COLUMN actions TEXT NOT NULL DEFAULT '[]'")
        db.execSQL("ALTER TABLE findings ADD COLUMN dedupKey TEXT NOT NULL DEFAULT ''")
        db.execSQL("CREATE INDEX IF NOT EXISTS index_findings_dedupKey ON findings(dedupKey)")
    }
}
