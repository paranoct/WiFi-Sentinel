<div align="center">

# WiFi Sentinel

**Мобильная система мониторинга безопасности Wi-Fi**

[![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://www.android.com/)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)
[![Status](https://img.shields.io/badge/Status-Active-success?style=for-the-badge)]()

<p align="center">
  <em>Ваш персональный страж в мире беспроводных сетей. <br>Анализ угроз, защита от Evil Twin и проверка DNS — всё на вашем устройстве.</em>
</p>

[Сообщить об ошибке](https://github.com/paranoct/WiFi-Sentinel/issues) • [Запросить функцию](https://github.com/paranoct/WiFi-Sentinel/issues)

</div>

---

## О проекте

> **Проблема:** Пользователи часто подключаются к публичным сетям, не осознавая рисков (перехват данных, фишинг, подмена DNS).
>
> **Решение:** **WiFi Sentinel** — это инструмент для неспециалистов. Он работает в фоне, анализирует подключение и простым языком объясняет, безопасна ли сеть.

### Основные возможности

| Функция | Описание |
| :--- | :--- |
| **Этикетка безопасности** | Мгновенная оценка сети: уровень шифрования, наличие Captive Portal, статус DNS. |
| **Детектор Evil Twin** | Обнаружение поддельных точек доступа с похожими именами (Homoglyph attacks). |
| **DNS Double-Check** | Сравнение ответов локального DNS с безопасным DoH-резолвером для защиты от подмены. |
| **Анализ Captive Portal** | Проверка страниц авторизации на использование незащищенного HTTP. |
| **Умный мониторинг** | Запоминает "почерк" домашних сетей и предупреждает об аномалиях. |
| **Режим Replay** | Загрузка и анализ сохраненных отчетов без реального подключения (для демо). |

---

## Технологический стек

Проект построен на современных технологиях Android разработки, обеспечивающих надежность и скорость.

<div align="center">

| Категория | Технологии |
| :--- | :--- |
| **Язык** | ![Kotlin](https://img.shields.io/badge/-Kotlin-7F52FF?style=flat&logo=kotlin&logoColor=white) |
| **UI** | ![Jetpack Compose](https://img.shields.io/badge/-Jetpack%20Compose-4285F4?style=flat&logo=google-play&logoColor=white) Material Design 3 |
| **Архитектура** | MVVM, Clean Architecture |
| **Асинхронность** | ![Coroutines](https://img.shields.io/badge/-Coroutines-orange?style=flat) ![Flow](https://img.shields.io/badge/-Flow-orange?style=flat) |
| **DI** | ![Hilt](https://img.shields.io/badge/-Hilt-green?style=flat&logo=android&logoColor=white) |
| **Data** | ![Room](https://img.shields.io/badge/-Room-blue?style=flat) ![DataStore](https://img.shields.io/badge/-DataStore-blue?style=flat) |
| **Background** | WorkManager |

</div>

---

## Приватность и Безопасность

Мы серьезно относимся к вашим данным.

*  **Zero-Knowledge:** Приложение не отправляет данные на внешние серверы.
*  **Local Processing:** Весь анализ происходит строго на процессоре смартфона.
*  **Data Masking:** BSSID и SSID в отчетах маскируются по умолчанию.

---

<div align="center">

## Создатель

*Разработано с ❤️ и заботой о цифровой безопасности.*

[![GitHub](https://img.shields.io/badge/GitHub-paranoct-181717?style=for-the-badge&logo=github)](https://github.com/paranoct)

<br>

```text
 __          ___ ______ _   _____            _   _            _ 
 \ \        / (_)  ____(_) / ____|          | | (_)          | |
  \ \  /\  / / _| |__   _ | (___   ___ _ __ | |_ _ _ __   ___| |
   \ \/  \/ / | |  __| | |  \___ \ / _ \ '_ \| __| | '_ \ / _ \ |
    \  /\  /  | | |    | |  ____) |  __/ | | | |_| | | | |  __/ |
     \/  \/   |_|_|    |_| |_____/ \___|_| |_|\__|_|_| |_|\___|_|
