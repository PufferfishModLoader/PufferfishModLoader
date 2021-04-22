package me.dreamhopping.pml.mods.impl

import me.dreamhopping.pml.mods.Mod
import org.apache.logging.log4j.LogManager

class TestMod : Mod("bruh") {
    private val logger = LogManager.getLogger("TestMod")

    override fun initialize() {
        logger.info("Hello world")
    }
}
