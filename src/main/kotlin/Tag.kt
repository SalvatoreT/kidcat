package dev.sal.kidcat

import dev.sal.kidcat.Color.*
import dev.sal.kidcat.Color.Companion.colorize

object Tag {
  val V = colorize(foreground = WHITE, background = BLACK) { " V " }
  val D = colorize(foreground = BLACK, background = BLUE) { " D " }
  val I = colorize(foreground = BLACK, background = GREEN) { " I " }
  val W = colorize(foreground = BLACK, background = YELLOW) { " W " }
  val E = colorize(foreground = BLACK, background = RED) { " E " }
  val F = colorize(foreground = BLACK, background = RED) { " F " }
}