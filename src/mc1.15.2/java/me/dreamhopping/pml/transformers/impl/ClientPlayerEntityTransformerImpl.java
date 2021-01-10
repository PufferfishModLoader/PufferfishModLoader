package me.dreamhopping.pml.transformers.impl;

import me.dreamhopping.pml.events.EventBus;
import me.dreamhopping.pml.events.core.client.chat.ClientChatSentEvent;
import me.dreamhopping.pml.events.core.client.player.ClientItemDropEvent;
import me.dreamhopping.pml.events.core.client.player.ClientPlayerRespawnEvent;
import me.dreamhopping.pml.transformers.ClientPlayerEntityTransformer;

/**
 * An implementation for the ClientPlayerEntityTransformer class
 * @see ClientPlayerEntityTransformer
 */
public class ClientPlayerEntityTransformerImpl {
    public static void sendChatMessage(String message) {
        EventBus.INSTANCE.post(new ClientChatSentEvent(message));
    }

    public static void dropSelectedItem(boolean dropAllItems) {
        // TODO: Use dropAllItems (needs further research on when it's true / false)
        EventBus.INSTANCE.post(new ClientItemDropEvent());
    }

    public static void respawn() {
        EventBus.INSTANCE.post(new ClientPlayerRespawnEvent());
    }
}
