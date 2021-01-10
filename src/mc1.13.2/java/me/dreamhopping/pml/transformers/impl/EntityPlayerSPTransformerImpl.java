package me.dreamhopping.pml.transformers.impl;

import me.dreamhopping.pml.events.EventBus;
import me.dreamhopping.pml.events.core.client.chat.ClientChatSentEvent;
import me.dreamhopping.pml.events.core.client.player.ClientItemDropEvent;
import me.dreamhopping.pml.events.core.client.player.ClientPlayerRespawnEvent;

/**
 * An implementation for the EntityPlayerSPTransformer class
 * @see me.dreamhopping.pml.transformers.EntityPlayerSPTransformer
 */
public class EntityPlayerSPTransformerImpl {
    public static void sendChatMessage(String message) {
        // Post the chat message event
        EventBus.INSTANCE.post(new ClientChatSentEvent(message));
    }

    public static void dropItem(boolean dropAllItems) {
        // TODO: Use dropAllItems (needs further research on when it's true / false)
        EventBus.INSTANCE.post(new ClientItemDropEvent());
    }

    public static void respawn() {
        EventBus.INSTANCE.post(new ClientPlayerRespawnEvent());
    }
}
