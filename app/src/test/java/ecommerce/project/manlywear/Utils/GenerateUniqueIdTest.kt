package ecommerce.project.manlywear.Utils

import org.junit.Assert.*
import org.junit.Test

class GenerateUniqueIdTest{

    @Test
    fun `test uniqueness of generated order IDs`() {
        val orderIds = mutableSetOf<Int>()
        val numIdsToGenerate = 10000

        repeat(numIdsToGenerate) {
            val id = generateUniqueOrderId()
            assertTrue("Duplicate order ID found", orderIds.add(id))
        }
    }

    @Test
    fun `test consistency in generating unique IDs within short span`() {
        val id1 = generateUniqueOrderId()
        val id2 = generateUniqueOrderId()

        assertNotEquals("Order IDs should be unique even within a short time", id1, id2)
    }

    @Test
    fun `test range of generated order ID`() {
        val id = generateUniqueOrderId()
        assertTrue("Order ID should be within Int range", id in Int.MIN_VALUE..Int.MAX_VALUE)
        assertTrue("Order ID should be non-negative", id >= 0)
    }

    @Test
    fun `performance test for order ID generation`() {
        val startTime = System.currentTimeMillis()
        repeat(10000) {
            generateUniqueOrderId()
        }
        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime

        assertTrue("Performance issue: Took too long to generate IDs", duration < 1000)
    }

}