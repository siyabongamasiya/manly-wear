package ecommerce.project.manlywear.Utils

import org.junit.Assert.*
import org.junit.Test

class GenerateUsernameFromEmailTest {

    @Test
    fun `should return username for valid email`() {
        val result = getUsernameFromEmail("user@example.com")
        assertEquals("user", result)
    }

    @Test
    fun `should return username with dots for email with multiple dots`() {
        val result = getUsernameFromEmail("user.name@example.com")
        assertEquals("user.name", result)
    }

    @Test
    fun `should return empty string when email has no username`() {
        val result = getUsernameFromEmail("@example.com")
        assertEquals("", result)
    }

    @Test
    fun `should return empty string for empty email input`() {
        val result = getUsernameFromEmail("")
        assertEquals("", result)
    }

    @Test
    fun `should return entire string if email has no at character`() {
        val result = getUsernameFromEmail("usernameonly")
        assertEquals("usernameonly", result)
    }

    @Test
    fun `should return username with special characters`() {
        val result = getUsernameFromEmail("user_name+123@example.com")
        assertEquals("user_name+123", result)
    }

}
