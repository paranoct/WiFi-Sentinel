package com.wifisentinel.feature.dashboard

data class RouterRecommendations(
    val sections: List<RouterSettingsSection> = emptyList(),
    val currentSections: List<RouterSettingsSection> = emptyList(),
    val scanCount24: Int = 0,
    val scanCount5: Int = 0
)

data class RouterSettingsSection(
    val titleResId: Int,
    val titleArgs: List<String> = emptyList(),
    val items: List<RouterSettingItem>
)

data class RouterSettingItem(
    val labelResId: Int,
    val value: String
)
