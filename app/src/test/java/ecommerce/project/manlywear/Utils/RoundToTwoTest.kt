package ecommerce.project.manlywear.Utils

import org.junit.Assert.*
import org.junit.Test

class RoundToTwoTest {

    @Test
    fun `rounds positive decimal value`() {
        val result = 3.456.roundToTwoDecimalPlaces()
        assertEquals(3.46, result, 0.0)
    }

    @Test
    fun `rounds negative decimal value`() {
        val result = -2.678.roundToTwoDecimalPlaces()
        assertEquals(-2.68, result, 0.0)
    }

    @Test
    fun `rounds positive value ending in 5`() {
        val result = 1.235.roundToTwoDecimalPlaces()
        assertEquals(1.24, result, 0.0)
    }

    @Test
    fun `rounds zero value`() {
        val result = 0.0.roundToTwoDecimalPlaces()
        assertEquals(0.0, result, 0.0)
    }

    @Test
    fun `rounds large decimal value`() {
        val result = 12345.6789.roundToTwoDecimalPlaces()
        assertEquals(12345.68, result, 0.0)
    }

    @Test
    fun `rounds small decimal value close to zero`() {
        val result = 0.004.roundToTwoDecimalPlaces()
        assertEquals(0.0, result, 0.0)
    }
}