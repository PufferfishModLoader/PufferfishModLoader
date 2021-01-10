package me.dreamhopping.pml.transformers.impl;

import me.dreamhopping.pml.events.EventBus;
import me.dreamhopping.pml.events.core.client.chat.ChatReceivedEvent;
import me.dreamhopping.pml.transformers.InGameHudTransformer;
import net.minecraft.text.Text;

/**
 * An implementation for the InGameHudTransformer class
 * @see InGameHudTransformer
 */
public class InGameHudTransformerImpl {
    public static void addChatMessage(Text text) {
        // Post the chat message event
        EventBus.INSTANCE.post(new ChatReceivedEvent(text.getString()));
    }
}
