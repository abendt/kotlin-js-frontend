pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap-1.2/") }
    }

    resolutionStrategy {
        eachPlugin {
            when {
                requested.id.id == "org.jetbrains.kotlin.platform.js" -> useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
                requested.id.id == "org.jetbrains.kotlin.platform.jvm" -> useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
                requested.id.id == "org.jetbrains.kotlin.platform.common" -> useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
            }
        }
    }
}