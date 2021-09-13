package com.waseefakhtar.countryinfo

import java.util.concurrent.ThreadLocalRandom

private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
private val random
    get() = ThreadLocalRandom.current()

fun randomString(size: Int = 20): String = (0..size)
    .map { charPool[random.nextInt(0, charPool.size)] }
    .joinToString("")