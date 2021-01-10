package me.dreamhopping.pml.transformers.impl;

import me.dreamhopping.pml.events.EventBus;
import me.dreamhopping.pml.events.core.client.chat.ChatSentEvent;
import me.dreamhopping.pml.events.core.client.player.ItemDropEvent;
import me.dreamhopping.pml.events.core.client.player.PlayerRespawnEvent;

/**
 * An implementation for the EntityPlayerSPTransformer class
 * @see me.dreamhopping.pml.transformers.EntityPlayerSPTransformer
 */
public class EntityPlayerSPTransformerImpl {
    public static void sendChatMessage(String message) {
        // Post the chat message event
        EventBus.INSTANCE.post(new ChatSentEvent(message));
    }

    public static void dropItem(boolean dropAllItems) {
        // TODO: Use dropAllItems (needs further research on when it's true / false)
        EventBus.INSTANCE.post(new ItemDropEvent());
    }

    public static void respawn() {
        EventBus.INSTANCE.post(new PlayerRespawnEvent());
    }
}
