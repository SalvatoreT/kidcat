package dev.sal.kidcat

import com.github.ajalt.clikt.core.CliktCommand
import dadb.Dadb

class Main : CliktCommand() {
  override fun run() {
    val dadb = Dadb.discover("localhost")

    if (dadb == null) {
      println("No emulator found")
      return
    }

    dadb.openShell("logcat").use {
      while (true) {
        print(it.read())
      }
    }
  }
}

fun main(args: Array<String>) = Main().main(args)