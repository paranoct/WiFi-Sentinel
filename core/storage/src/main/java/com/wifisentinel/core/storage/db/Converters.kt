package com.wifisentinel.core.storage.db

import androidx.room.TypeConverter
import com.wifisentinel.core.detectors.FindingActionType
import com.wifisentinel.core.detectors.Severity
import com.wifisentinel.core.wifi.Band
import com.wifisentinel.core.wifi.NetworkCategory
import com.wifisentinel.core.wifi.SecurityType
import org.json.JSONArray
import org.json.JSONObject

class Converters {
    @TypeConverter
    fun stringListToJson(value: List<String>?): String {
        val array = JSONArray()
        value.orEmpty().forEach { array.put(it) }
        return array.toString()
    }

    @TypeConverter
    fun jsonToStringList(value: String?): List<String> {
        if (value.isNullOrBlank()) return emptyList()
        val array = JSONArray(value)
        return List(array.length()) { index -> array.getString(index) }
    }

    @TypeConverter
    fun stringSetToJson(value: Set<String>?): String = stringListToJson(value?.toList())

    @TypeConverter
    fun jsonToStringSet(value: String?): Set<String> = jsonToStringList(value).toSet()

    @TypeConverter
    fun securitySetToJson(value: Set<SecurityType>?): String {
        return stringListToJson(value?.map { it.name })
    }

    @TypeConverter
    fun jsonToSecuritySet(value: String?): Set<SecurityType> {
        return jsonToStringList(value).mapNotNull { name ->
            SecurityType.values().firstOrNull { it.name == name }
        }.toSet()
    }

    @TypeConverter
    fun bandSetToJson(value: Set<Band>?): String {
        return stringListToJson(value?.map { it.name })
    }

    @TypeConverter
    fun jsonToBandSet(value: String?): Set<Band> {
        return jsonToStringList(value).mapNotNull { name ->
            Band.values().firstOrNull { it.name == name }
        }.toSet()
    }

    @TypeConverter
    fun severityToString(value: Severity): String = value.name

    @TypeConverter
    fun stringToSeverity(value: String): Severity = Severity.valueOf(value)

    @TypeConverter
    fun securityToString(value: SecurityType): String = value.name

    @TypeConverter
    fun stringToSecurity(value: String): SecurityType = SecurityType.valueOf(value)

    @TypeConverter
    fun categoryToString(value: NetworkCategory): String = value.name

    @TypeConverter
    fun stringToCategory(value: String): NetworkCategory {
        return NetworkCategory.values().firstOrNull { it.name == value } ?: NetworkCategory.HOME
    }

    @TypeConverter
    fun mapToJson(map: Map<String, String>?): String {
        val obj = JSONObject()
        map.orEmpty().forEach { (key, value) -> obj.put(key, value) }
        return obj.toString()
    }

    @TypeConverter
    fun jsonToMap(value: String?): Map<String, String> {
        if (value.isNullOrBlank()) return emptyMap()
        val obj = JSONObject(value)
        val keys = obj.keys()
        val map = mutableMapOf<String, String>()
        while (keys.hasNext()) {
            val key = keys.next()
            map[key] = obj.getString(key)
        }
        return map
    }

    @TypeConverter
    fun actionsToJson(value: List<FindingActionType>?): String {
        return stringListToJson(value?.map { it.name })
    }

    @TypeConverter
    fun jsonToActions(value: String?): List<FindingActionType> {
        return jsonToStringList(value).mapNotNull { name ->
            FindingActionType.values().firstOrNull { it.name == name }
        }
    }
}
