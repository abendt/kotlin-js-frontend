package demo

import kotlin.test.Test
import kotlin.test.assertEquals

class HelloWorldTest {
    @Test
    fun canSayHello() {
        assertEquals("Hello Kotlin", sayHelloTo("Kotlin"))
    }
}