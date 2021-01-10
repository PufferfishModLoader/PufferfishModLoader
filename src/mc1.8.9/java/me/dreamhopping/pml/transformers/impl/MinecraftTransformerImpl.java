package me.dreamhopping.pml.transformers.impl;

import me.dreamhopping.pml.events.EventBus;
import me.dreamhopping.pml.events.core.client.tick.ClientRenderTickEvent;
import me.dreamhopping.pml.events.core.client.tick.ClientTickEvent;

/**
 * An implementation for the MinecraftTransformer class
 * @see me.dreamhopping.pml.transformers.MinecraftTransformer
 */
public class MinecraftTransformerImpl {
    public static void runTick() {
        EventBus.INSTANCE.post(new ClientTickEvent());
    }

    public static void runGameLoop() {
        EventBus.INSTANCE.post(new ClientRenderTickEvent());
    }
}
