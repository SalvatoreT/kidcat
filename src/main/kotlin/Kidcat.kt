package dev.sal.kidcat

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import dadb.Dadb

class Kidcat : CliktCommand() {
  private val current: Boolean by option(help = "Filter logcat by current running app").flag()

  override fun run() {
    val dadb = Dadb.discover("localhost")

    if (dadb == null) {
      println("No emulator found")
      return
    }

    dadb.openShell("logcat").use {
      if (current) {
        val runningPackage = dadb.current()
        if (runningPackage.isNullOrBlank()) {
          print("No running Activities found.")
          return
        }
        print(runningPackage)
        while (true) {
          val line: String = it.read().toString()
          if (line.contains(runningPackage)) {
            print(line)
          }
        }
      } else {
        while (true) print(it.read())
      }
    }
  }

  /**
   * @return currently running Actiivty package name
   */
  private fun Dadb.current() = this.shell("dumpsys activity activities").output
    // Looking for the first line like the following in the dump
    // * TaskRecord{c7f29c4 #15 A=com.foo.bar U=0 StackId=4 sz=1}
    .split("\n")
    .first { line -> line.contains("TaskRecord") }
    .split("A=").getOrElse(1) { "" }
    .split(" U=0").first()
    .ifBlank { null }
}
