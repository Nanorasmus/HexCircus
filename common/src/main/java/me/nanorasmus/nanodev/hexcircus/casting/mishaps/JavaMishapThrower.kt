package me.nanorasmus.nanodev.hexcircus.casting.mishaps

import at.petrak.hexcasting.api.spell.mishaps.Mishap


/**
 * kotlin doesn't check its exceptions so code extending kotlin compiled stuff can't actually properly throw exceptions
 * This has been stolen straight from Hex Gloop
 */
class JavaMishapThrower {
    companion object {
        @JvmStatic
        fun throwMishap(mishap: Mishap) {
            throw mishap
        }
    }
}