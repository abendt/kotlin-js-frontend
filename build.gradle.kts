import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.frontend.KotlinFrontendExtension
import org.jetbrains.kotlin.gradle.frontend.karma.KarmaExtension
import org.jetbrains.kotlin.gradle.frontend.npm.NpmExtension
import org.jetbrains.kotlin.gradle.frontend.util.frontendExtension
import org.jetbrains.kotlin.gradle.frontend.webpack.WebPackExtension
import org.jetbrains.kotlin.gradle.plugin.Kotlin2JsPluginWrapper

buildscript {
    repositories {
        jcenter()
        maven {
            setUrl("https://dl.bintray.com/kotlin/kotlin-eap")
        }
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.2.31")
        classpath("org.jetbrains.kotlin:kotlin-frontend-plugin:0.0.30")
    }
}

plugins {
    kotlin("platform.js") version "1.2.31"
}

apply {
    plugin("org.jetbrains.kotlin.frontend")
}

repositories {
    jcenter()
}

dependencies {
    compile(kotlin("stdlib-js"))
    testCompile(kotlin("test-js"))
}

configure<KotlinFrontendExtension> {
    downloadNodeJsVersion = "8.9.3"

    configure<NpmExtension> {
        devDependency("kotlin-test", "1.2.31")
        devDependency("karma")

        // karma plugins
        devDependency("karma-es6-shim")
        devDependency("karma-mocha-reporter")

        devDependency("karma-chrome-launcher")
    }

    configure<KarmaExtension> {
        port = 9876
        runnerPort = 9100
        browsers = mutableListOf("ChromeHeadless")
        plugins = mutableListOf("karma-mocha-reporter", "karma-es6-shim", "karma-chrome-launcher")
        reporters = mutableListOf("mocha")
        frameworks = mutableListOf("jasmine", "es6-shim") // for now only qunit works as intended
    }

    bundle<WebPackExtension>("webpack") {
        if (this is WebPackExtension) { // -> "Workaround" to be typesafe
            bundleName = "main"
            contentPath = file("src/main/web")
            port = 3000
        }
    }
}

java.sourceSets {
    getByName("main").java.srcDir("src/main/resources")
}

val jsOutputFile = "${project.buildDir}/js/${project.name}.js"
val testJsOutputFile = "${project.buildDir}/js-tests/${project.name}-tests.js"

tasks {

    val compileKotlin2Js by getting(KotlinJsCompile::class) {
        kotlinOptions.metaInfo = true
        kotlinOptions.outputFile = jsOutputFile
        kotlinOptions.sourceMap = true
        kotlinOptions.sourceMapEmbedSources = "always"
        kotlinOptions.moduleKind = "commonjs"
        kotlinOptions.main = "call"
    }

    val compileTestKotlin2Js by getting(KotlinJsCompile::class) {
        kotlinOptions.metaInfo = true
        kotlinOptions.outputFile = testJsOutputFile
        kotlinOptions.sourceMap = true
        kotlinOptions.sourceMapEmbedSources = "always"
        kotlinOptions.moduleKind = "commonjs"
        kotlinOptions.main = "call"
    }
}

project.afterEvaluate{
    val `karma-run-single` by tasks.getting {
        inputs.file(testJsOutputFile)
    }
}