plugins {
    id("org.jetbrains.kotlin.jvm") version "2.2.21"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("it.skrape:skrapeit:1.2.2")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("org.apache.commons:commons-collections4:4.4")
}

application {
    mainClass.set("MainKt")
}

tasks {
    val run by existing(JavaExec::class)

    register("latest") {
        doFirst {
            run.configure {
                args = listOf("r")
            }
        }
        finalizedBy(run)
    }

    register("all") {
        doFirst {
            run.configure {
                args = listOf("a")
            }
        }
        finalizedBy(run)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
