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
          if (line.contains(runningPackage.name)) {
            echo(
              message = line,
              trailingNewline = false,
            )
          }
        }
      } else {
        while (true) {
          val line = it.read().toString()

          if (!LOG_LINE.matches(line)) continue

          echo(
            message = line,
            trailingNewline = false,
          )
        }
      }
    }
  }

  companion object {
    val BUG_LINE = Regex(".*nativeGetEnabledTags.*")
    val LOG_LINE = Regex("^([A-Z])/(.+?)\\( *(\\d+)\\): (.*?)\$")
  }

  /**
   * @return currently running Activity package name
   */
  private fun Dadb.current(): Package? = this.shell("dumpsys activity activities").output
    .currentPackage()
}
