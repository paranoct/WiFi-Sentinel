# WiFi Sentinel

[![Platform](https://img.shields.io/badge/platform-Android-3DDC84)](https://www.android.com/)
[![Language](https://img.shields.io/badge/language-Kotlin-7F52FF)](https://kotlinlang.org/)
[![Min SDK](https://img.shields.io/badge/minSdk-26-0A66C2)](https://developer.android.com/)
[![License](https://img.shields.io/badge/license-MIT-black)](LICENSE)

Language: [Russian](#russian) | [English](#english)

---

## Russian

### О проекте

WiFi Sentinel — Android-приложение для мониторинга безопасности Wi-Fi-сетей на устройстве пользователя.  
Проект фокусируется на практической защите: обнаружение подозрительных признаков, оценка риска, предупреждение пользователя и действия при опасных сценариях.

### Ключевые возможности

- Фоновый мониторинг подключений Wi-Fi.
- Набор детекторов угроз:
  - признаки Evil Twin и подмены точки доступа
  - аномалии MAC/OUI
  - проверка captive portal и фишинговых признаков
  - слабый/неизвестный тип защиты
  - DNS-аномалии и отклонение от закреплённого DNS
  - аномалии шлюза и частых переподключений
  - baseline-анализ по BSSID/истории
  - похожие и маскированные SSID
- Risk Engine с оценкой `0..100` и уровнями `LOW / MEDIUM / HIGH / CRITICAL`.
- Управление доверенными сетями (включая mesh-сценарии).
- Лента сессий и событий по сетям.
- Экспорт отчётов по сети в `JSON` и `HTML`.
- Демо/Replay-режим для воспроизведения сохранённых данных.
- Авто-реакции на высокий риск (с учётом ограничений Android/OEM).

### Архитектура и модули

| Модуль | Назначение |
|---|---|
| `app` | Точка входа, DI, навигация, мониторинг, уведомления, экспорт отчётов |
| `core:wifi` | Наблюдение за сетью, снапшоты, сканер |
| `core:net` | Сетевые проверки (DNS, captive portal) |
| `core:storage` | Хранение (Room/DataStore), репозитории |
| `core:detectors` | Детекторы угроз и контекст анализа |
| `core:risk` | Движок расчёта риска |
| `feature:dashboard` | Дашборд текущей сети и риска |
| `feature:networkdetails` | Технические детали сети |
| `feature:trusted` | Управление доверенными сетями |
| `feature:timeline` | История и события по сетям |
| `feature:settings` | Настройки и demo/replay |

### Технологии

- Kotlin, Coroutines, Flow
- Jetpack Compose, Material 3, Navigation Compose
- Hilt
- Room + DataStore
- OkHttp + DoH-компоненты
- WorkManager
- Android SDK 34, Java/Kotlin target 17

### Требования

- Android Studio (актуальная стабильная версия)
- JDK 17
- Android SDK 34
- Устройство/эмулятор Android API 26+

### Быстрый старт

1. Клонирование:

```bash
git clone https://github.com/paranoct/WiFi-Sentinel.git
cd WiFi-Sentinel
```

2. Сборка debug APK (Windows):

```powershell
.\gradlew.bat :app:assembleDebug
```

3. Установка на устройство:

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

Путь к APK:

`app/build/outputs/apk/debug/app-debug.apk`

### Release-сборка

Рекомендуемый путь:

1. `Build > Generate Signed Bundle / APK` в Android Studio.
2. Выбрать или создать release keystore (`.jks`).
3. Собрать подписанный `release` APK.

CLI (если подпись настроена в Gradle):

```powershell
.\gradlew.bat :app:assembleRelease
```

### Тесты

```powershell
.\gradlew.bat :app:testDebugUnitTest
```

Основные unit-тесты:

- `core/detectors/src/test/...`
- `app/src/test/...`

### Разрешения

Приложение использует:

- `ACCESS_FINE_LOCATION`
- `NEARBY_WIFI_DEVICES`
- `ACCESS_WIFI_STATE`
- `CHANGE_WIFI_STATE`
- `POST_NOTIFICATIONS`
- `FOREGROUND_SERVICE`
- `RECEIVE_BOOT_COMPLETED`

### Ограничения платформы

- На некоторых OEM-прошивках системные действия по Wi-Fi ограничены.
- Фоновая работа может зависеть от политики энергосбережения.
- Часть защитных сценариев требует ручного подтверждения в системных настройках Wi-Fi.

### Приватность

- Анализ выполняется локально на устройстве.
- Отчёты формируются локально.
- Передача отчётов происходит только по явному действию пользователя.

---

## English

### Project Summary

WiFi Sentinel is an Android application for on-device Wi-Fi security monitoring.  
It focuses on practical defense workflows: suspicious behavior detection, risk scoring, user warnings, and response actions for unsafe network states.

### Core Features

- Background Wi-Fi connection monitoring.
- Threat detector set:
  - evil twin and AP spoofing indicators
  - MAC/OUI anomaly checks
  - captive portal and phishing hint checks
  - weak/unknown security type detection
  - DNS anomaly and pinned DNS drift checks
  - gateway and reconnect anomaly detection
  - BSSID/history baseline analysis
  - lookalike and obfuscated SSID detection
- Risk Engine with `0..100` scoring and `LOW / MEDIUM / HIGH / CRITICAL` levels.
- Trusted networks policy management (including mesh scenarios).
- Network timeline with session and event history.
- Network report export in `JSON` and `HTML`.
- Demo/replay mode for saved scan data.
- Auto-response workflows for high-risk states (subject to Android/OEM constraints).

### Architecture and Modules

| Module | Responsibility |
|---|---|
| `app` | Entry point, DI, navigation, monitoring, notifications, report export |
| `core:wifi` | Network observation, snapshots, scanner abstractions |
| `core:net` | DNS and captive portal probing |
| `core:storage` | Persistence (Room/DataStore), repositories |
| `core:detectors` | Threat detectors and analysis context |
| `core:risk` | Risk scoring engine |
| `feature:dashboard` | Current network and risk dashboard |
| `feature:networkdetails` | Low-level network details |
| `feature:trusted` | Trusted network management |
| `feature:timeline` | Session and event history |
| `feature:settings` | Runtime settings and demo/replay |

### Technology Stack

- Kotlin, Coroutines, Flow
- Jetpack Compose, Material 3, Navigation Compose
- Hilt
- Room + DataStore
- OkHttp + DoH components
- WorkManager
- Android SDK 34, Java/Kotlin target 17

### Requirements

- Android Studio (recent stable build)
- JDK 17
- Android SDK 34
- Android device/emulator API 26+

### Quick Start

1. Clone:

```bash
git clone https://github.com/paranoct/WiFi-Sentinel.git
cd WiFi-Sentinel
```

2. Build debug APK (Windows):

```powershell
.\gradlew.bat :app:assembleDebug
```

3. Install on device:

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

APK path:

`app/build/outputs/apk/debug/app-debug.apk`

### Release Build

Recommended flow:

1. Use `Build > Generate Signed Bundle / APK` in Android Studio.
2. Select or create a release keystore (`.jks`).
3. Build a signed `release` APK.

CLI (if signing is configured in Gradle):

```powershell
.\gradlew.bat :app:assembleRelease
```

### Tests

```powershell
.\gradlew.bat :app:testDebugUnitTest
```

Primary unit tests are in:

- `core/detectors/src/test/...`
- `app/src/test/...`

### Permissions

The app relies on:

- `ACCESS_FINE_LOCATION`
- `NEARBY_WIFI_DEVICES`
- `ACCESS_WIFI_STATE`
- `CHANGE_WIFI_STATE`
- `POST_NOTIFICATIONS`
- `FOREGROUND_SERVICE`
- `RECEIVE_BOOT_COMPLETED`

### Platform Constraints

- Some OEM firmware restricts programmatic Wi-Fi management.
- Background behavior may vary under battery optimization policies.
- Some defensive actions require manual confirmation in system Wi-Fi settings.

### Privacy

- Analysis is performed locally on-device.
- Reports are generated locally.
- Report sharing is always an explicit user action.

---

## Repository Structure

```text
.
|-- app
|-- core
|   |-- wifi
|   |-- net
|   |-- storage
|   |-- detectors
|   `-- risk
|-- feature
|   |-- dashboard
|   |-- networkdetails
|   |-- trusted
|   |-- timeline
|   `-- settings
`-- README.md
```

## License

MIT License. See [LICENSE](LICENSE).

## Maintainer

GitHub: [paranoct](https://github.com/paranoct)
