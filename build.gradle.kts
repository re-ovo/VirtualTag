import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.apache.tools.ant.filters.ReplaceTokens
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.github.johnrengelman.shadow") version "6.1.0"
    kotlin("jvm") version "1.5.21"
}

group = "me.rerere"
version = "1.0.0"

repositories {
    mavenCentral()
    maven(url = "https://hub.spigotmc.org/nexus/content/groups/public/")
    maven(url = "https://repo.dmulloy2.net/repository/public/")
    maven(url = "https://repo.codemc.io/repository/nms/")
    maven(url = "https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    compileOnly(group = "org.spigotmc", name = "spigot", version = "1.17.1-R0.1-SNAPSHOT")
    compileOnly(group = "org.spigotmc", name = "spigot-api", version = "1.17.1-R0.1-SNAPSHOT")
    compileOnly(group = "com.comphenix.protocol", name = "ProtocolLib", version = "4.7.0")
    compileOnly(group = "me.clip", name = "placeholderapi", version = "2.10.10")

    // Exposed ORM
    implementation("org.jetbrains.exposed:exposed-core:0.32.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.32.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.32.1")

    testImplementation(kotlin("test"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "11"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "11"
}

tasks {
    val fatJar by named("shadowJar", ShadowJar::class) {
        dependencies {
            exclude(dependency("org.slf4j:.*"))
        }
        minimize()
        relocate("kotlin", "me.rerere.virtualtag.thirdparty.kotlin")
        relocate("org.jetbrains", "me.rerere.virtualtag.thirdparty.org.jetbrains")
        relocate("org.intellij", "me.rerere.virtualtag.thirdparty.org.intellij")
    }

    artifacts {
        add("archives", fatJar)
    }
}

(tasks.getByName("processResources") as ProcessResources).apply {
    from("src/main/resources") {
        include("**/*.yml")
        filter<ReplaceTokens>(
            "tokens" to mapOf(
                "VERSION" to project.version
            )
        )
    }
    filesMatching("application.properties") {
        expand(project.properties)
    }
}

tasks.test {
    useJUnit()
}