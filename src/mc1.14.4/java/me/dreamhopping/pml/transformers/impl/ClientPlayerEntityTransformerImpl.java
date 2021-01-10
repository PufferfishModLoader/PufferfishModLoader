package me.dreamhopping.pml.transformers.impl;

import me.dreamhopping.pml.events.EventBus;
import me.dreamhopping.pml.events.core.client.chat.ChatSentEvent;
import me.dreamhopping.pml.transformers.ClientPlayerEntityTransformer;

/**
 * An implementation for the ClientPlayerEntityTransformer class
 * @see ClientPlayerEntityTransformer
 */
public class ClientPlayerEntityTransformerImpl {
    public static void sendChatMessage(String message) {
        EventBus.INSTANCE.post(new ChatSentEvent(message));
    }
}
