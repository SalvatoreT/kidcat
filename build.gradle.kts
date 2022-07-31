import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.7.10"
  application
}

group = "dev.sal"
version = "0.1.0"

repositories {
  mavenCentral()
}

dependencies {
  implementation("dev.mobile:dadb:0.0.10")
  implementation("com.github.ajalt.clikt:clikt:3.5.0")
  testImplementation(kotlin("test"))
}

tasks.test {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "11"
}

application {
  mainClass.set("dev.sal.kidcat.Main")
}