package dev.sal.kidcat

enum class Color(private val value: Int) {
  BLACK(0),
  RED(1),
  GREEN(2),
  YELLOW(3),
  BLUE(4),
  MAGENTA(5),
  CYAN(6),
  WHITE(7);

  companion object {
    private const val RESET = "\u001B[0m"

    /**
     * ### Example
     *
     * ```
     * Color.colorize(background = Color.RED) { "Hello World" }
     * ```
     *
     * @return color formatted [message]
     */
    fun colorize(
      foreground: Color? = null,
      background: Color? = null,
      message: () -> String,
    ): String {
      val startColorCode = listOfNotNull(
        foreground?.let { "3${foreground.value}" },
        background?.let { "10${background.value}" }
      ).joinToString(";")

      return "\u001B[${startColorCode}m${message()}$RESET"
    }
  }
}