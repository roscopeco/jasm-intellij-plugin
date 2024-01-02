import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.markdownToHTML
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Java support
    java
    // Kotlin support
    id("org.jetbrains.kotlin.jvm") version "1.9.0"
    // Gradle IntelliJ Plugin
    id("org.jetbrains.intellij") version "1.16.1"
    // Gradle Changelog Plugin
    id("org.jetbrains.changelog") version "2.2.0"
    // Gradle Qodana Plugin
    id("org.jetbrains.qodana") version "2023.2.1"
}

val pluginGroup: String by properties
val pluginVersion: String by properties
val pluginName: String by properties

val pluginSinceBuild: String by properties
val pluginUntilBuild: String by properties

val platformVersion: String by properties
val platformType: String by properties
val platformPlugins: String by properties

val javaVersion: String by properties
val gradleVersion: String by properties

val MutableMap<String, *>.pluginName: String
    get() = this["pluginName"] as String
val MutableMap<String, *>.gradleVersion: String
    get() = this["gradleVersion"] as String

group = pluginGroup
version = pluginVersion

// Configure project's dependencies
repositories {
    mavenCentral()
}

dependencies {
    implementation("org.antlr:antlr4-intellij-adaptor:0.1")
    implementation("com.roscopeco.jasm:jasm:0.7.0")
    implementation("org.ow2.asm:asm:9.6")
}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    pluginName = properties.pluginName
    version = platformVersion
    type = platformType

    // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file.
    plugins.set(platformPlugins.split(',').map(String::trim).filter(String::isNotEmpty))
}

// Configure Gradle Changelog Plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    version = pluginVersion
    groups = emptyList()
}

// Configure Gradle Qodana Plugin - read more: https://github.com/JetBrains/gradle-qodana-plugin
qodana {
    cachePath = projectDir.resolve(".qodana").canonicalPath
    resultsPath = projectDir.resolve("build/reports/inspections").canonicalPath
}

tasks {
    // Set the JVM compatibility versions
    javaVersion.let {
        withType<JavaCompile> {
            options.release = it.toInt()
        }
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = it
        }
    }

    wrapper {
        gradleVersion = project.properties.gradleVersion
    }

    patchPluginXml {
        version = pluginVersion
        sinceBuild = pluginSinceBuild
        untilBuild = pluginUntilBuild

        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        pluginDescription = projectDir.resolve("README.md").readText().lines().run {
            val start = "<!-- Plugin description -->"
            val end = "<!-- Plugin description end -->"

            if (!containsAll(listOf(start, end))) {
                throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
            }
            subList(indexOf(start) + 1, indexOf(end))
        }.joinToString("\n").run { markdownToHTML(this) }

        // Get the latest available change notes from the changelog file
        changeNotes = provider {
            changelog.run {
                renderItem(getOrNull(pluginVersion) ?: getLatest(), Changelog.OutputType.HTML)
            }
        }
    }

    // Configure UI tests plugin
    // Read more: https://github.com/JetBrains/intellij-ui-test-robot
    runIdeForUiTests {
        systemProperty("robot-server.port", "8082")
        systemProperty("ide.mac.message.dialogs.as.sheets", "false")
        systemProperty("jb.privacy.policy.text", "<!--999.999-->")
        systemProperty("jb.consents.confirmation.enabled", "false")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        dependsOn("patchChangelog")
        token.set(System.getenv("COM_JASM_INTELLIJ_intellijPublishToken"))
        // pluginVersion is based on the SemVer (https://semver.org) and supports pre-release labels, like 2.1.7-alpha.3
        // Specify pre-release label to publish the plugin in a custom Release Channel automatically. Read more:
        // https://plugins.jetbrains.com/docs/intellij/deployment.html#specifying-a-release-channel
        channels.set(listOf(pluginVersion.split('-').getOrElse(1) { "default" }.split('.').first()))
    }
}
