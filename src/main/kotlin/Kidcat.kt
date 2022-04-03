package dev.sal.kidcat

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import dadb.Dadb

class Kidcat : CliktCommand() {
  private val current: Boolean by option(help = "Filter logcat by current running app").flag()

  override fun run() {
    val dadb = Dadb.discover("localhost") ?: throw CliktError("No emulator found.")

    dadb.openShell("logcat").use {
      if (current) {
        val runningPackage = dadb.current() ?: throw CliktError("No running Activities found.")
        while (true) {
          val line: String = it.read().toString()
          if (line.contains(runningPackage)) {
            echo(
              message = line,
              trailingNewline = false,
            )
          }
        }
      } else {
        while (true) echo(
          message = it.read(),
          trailingNewline = false,
        )
      }
    }
  }

  /**
   * @return currently running Activity package name
   */
  private fun Dadb.current(): String? = this.shell("dumpsys activity activities").output
    // Looking for the first line like the following in the dump
    // * TaskRecord{c7f29c4 #15 A=com.foo.bar U=0 StackId=4 sz=1}
    .split("\n")
    .first { line -> line.contains("TaskRecord") }
    .split("A=").getOrElse(1) { "" }
    .split(" U=0").first()
    .ifBlank { null }
}
