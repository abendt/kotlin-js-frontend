package demo

import kotlin.browser.document

fun main(args: Array<String>) {
    if (document.body != null) {
        println("start ${document.body}")

        val button = document.getElementById("kotlin")

        val list = document.getElementById("results")

        button?.addEventListener("click", {
            val result = sayHelloTo("Kotlin")

            println(result)

            val child = document.createElement("li")
            child.appendChild(document.createTextNode(result))
            list?.appendChild(child)

        })

    } else {
        document.addEventListener("DOMContentLoaded", { println("start deferred") })
    }
}