package me.dreamhopping.pml.mods

/**
 * The class that all PML Mods should implement
 */
abstract class Mod(val id: String) {
    abstract fun initialize()
    fun unload() {}
}