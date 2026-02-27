package com.wifisentinel.app.security

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AutoDisconnectController @Inject constructor(
    @ApplicationContext private val context: Context
) {
    @SuppressLint("MissingPermission")
    fun disconnectAndRestrictAutoJoin(ssid: String?): AutoDisconnectResult {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        var systemAutoJoinDisabled = false

        val currentNetworkId = runCatching { wifiManager.connectionInfo?.networkId ?: -1 }
            .getOrDefault(-1)

        if (!ssid.isNullOrBlank()) {
            systemAutoJoinDisabled = systemAutoJoinDisabled || tryDisableEphemeralNetwork(wifiManager, ssid)
        }

        if (currentNetworkId >= 0) {
            systemAutoJoinDisabled = systemAutoJoinDisabled || if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                tryDisableOrForgetLegacy(wifiManager, currentNetworkId)
            } else {
                tryDisableAutoJoinReflective(wifiManager, currentNetworkId)
            }
        }

        val disconnected = try {
            wifiManager.disconnect()
        } catch (_: SecurityException) {
            false
        }
        return AutoDisconnectResult(
            disconnected = disconnected,
            systemAutoJoinDisabled = systemAutoJoinDisabled
        )
    }

    @Suppress("DEPRECATION")
    private fun tryDisableOrForgetLegacy(wifiManager: WifiManager, networkId: Int): Boolean {
        return runCatching {
            val disabled = wifiManager.disableNetwork(networkId)
            val removed = wifiManager.removeNetwork(networkId)
            if (removed) {
                wifiManager.saveConfiguration()
            }
            disabled || removed
        }.getOrDefault(false)
    }

    private fun tryDisableEphemeralNetwork(wifiManager: WifiManager, ssid: String): Boolean {
        return runCatching {
            val method = WifiManager::class.java.methods.firstOrNull { candidate ->
                candidate.name == "disableEphemeralNetwork" &&
                    candidate.parameterTypes.size == 1 &&
                    candidate.parameterTypes[0] == String::class.java
            } ?: return@runCatching false
            method.invoke(wifiManager, ssid)
            true
        }.getOrElse {
            runCatching {
                val method = WifiManager::class.java.methods.firstOrNull { candidate ->
                    candidate.name == "disableEphemeralNetwork" &&
                        candidate.parameterTypes.size == 1 &&
                        candidate.parameterTypes[0] == String::class.java
                } ?: return@runCatching false
                method.invoke(wifiManager, "\"$ssid\"")
                true
            }.getOrDefault(false)
        }
    }

    private fun tryDisableAutoJoinReflective(wifiManager: WifiManager, networkId: Int): Boolean {
        return runCatching {
            val method = WifiManager::class.java.methods.firstOrNull { candidate ->
                candidate.name == "allowAutojoin" &&
                    candidate.parameterTypes.size == 2 &&
                    candidate.parameterTypes[0] == Int::class.javaPrimitiveType &&
                    candidate.parameterTypes[1] == Boolean::class.javaPrimitiveType
            } ?: return@runCatching false
            method.invoke(wifiManager, networkId, false)
            true
        }.getOrDefault(false)
    }
}

data class AutoDisconnectResult(
    val disconnected: Boolean,
    val systemAutoJoinDisabled: Boolean
)
