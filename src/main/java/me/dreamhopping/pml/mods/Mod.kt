package me.dreamhopping.pml.mods

/**
 * The class that all PML Mods should implement
 */
interface Mod {
    fun initialize()
    fun unload() {}
}