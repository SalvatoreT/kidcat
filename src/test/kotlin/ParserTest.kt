package dev.sal.kidcat

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ParserTest {

  private val activitiesDump =
    this::class.java.getResource("/activities_api28_pixel3emulator.txt").readText()

  @Test
  fun `currentPackage happy`() {
    assertEquals(activitiesDump.currentPackage()?.name, "dev.sal.example")
  }

  @Test
  fun `currentPackage blank`() {
    assertNull("".currentPackage())
  }
}