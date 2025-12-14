package org.pgk.food

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform