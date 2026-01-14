package com.wifisentinel.core.wifi

enum class Band {
    BAND_2G4,
    BAND_5G,
    BAND_6G
}

object BandMapper {
    fun fromFrequencyMhz(frequencyMhz: Int?): Band? {
        if (frequencyMhz == null) return null
        return when {
            frequencyMhz in 2400..2500 -> Band.BAND_2G4
            frequencyMhz in 4900..5899 -> Band.BAND_5G
            frequencyMhz in 5925..7125 -> Band.BAND_6G
            else -> null
        }
    }
}
