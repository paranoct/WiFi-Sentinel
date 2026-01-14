pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "WiFiSentinel"

include(
    ":app",
    ":core:wifi",
    ":core:net",
    ":core:storage",
    ":core:detectors",
    ":core:risk",
    ":feature:dashboard",
    ":feature:networkdetails",
    ":feature:trusted",
    ":feature:timeline",
    ":feature:settings"
)
