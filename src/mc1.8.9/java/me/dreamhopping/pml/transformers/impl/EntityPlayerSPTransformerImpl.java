package me.dreamhopping.pml.transformers.impl;

import me.dreamhopping.pml.events.EventBus;
import me.dreamhopping.pml.events.core.client.chat.ChatReceivedEvent;
import me.dreamhopping.pml.events.core.client.chat.ChatSentEvent;
import net.minecraft.util.IChatComponent;

/**
 * An implementation for the EntityPlayerSPTransformer class
 * @see me.dreamhopping.pml.transformers.EntityPlayerSPTransformer
 */
public class EntityPlayerSPTransformerImpl {
    public static void sendChatMessage(String message) {
        // Post the chat message event
        EventBus.INSTANCE.post(new ChatSentEvent(message));
    }
}
