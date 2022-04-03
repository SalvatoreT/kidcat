import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.6.10"
  application
}

group = "dev.sal"
version = "1.0"

repositories {
  mavenCentral()
}

dependencies {
  implementation("dev.mobile:dadb:0.0.9")
  implementation("com.github.ajalt.clikt:clikt:3.4.0")
  testImplementation(kotlin("test"))
}

tasks.test {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "11"
}

application {
  mainClass.set("Main")
}