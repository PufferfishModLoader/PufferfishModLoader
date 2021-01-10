package me.dreamhopping.pml.transformers.impl;

import me.dreamhopping.pml.events.EventBus;
import me.dreamhopping.pml.events.core.client.tick.ClientRenderTickEvent;
import me.dreamhopping.pml.events.core.client.tick.ClientTickEvent;

/**
 * An implementation for the MinecraftClientTransformer class
 * @see me.dreamhopping.pml.transformers.MinecraftClientTransformer
 */
public class MinecraftClientTransformerImpl {
    public static void tick() {
        EventBus.INSTANCE.post(new ClientTickEvent());
    }

    public static void render() {
        EventBus.INSTANCE.post(new ClientRenderTickEvent());
    }
}
