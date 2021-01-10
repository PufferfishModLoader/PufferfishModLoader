package me.dreamhopping.pml.transformers.impl;

import me.dreamhopping.pml.events.EventBus;
import me.dreamhopping.pml.events.core.client.chat.ClientChatReceivedEvent;
import net.minecraft.util.IChatComponent;

/**
 * An implementation for the GuiNewChatTransformer class
 * @see me.dreamhopping.pml.transformers.GuiNewChatTransformer
 */
public class GuiNewChatTransformerImpl {
    public static void printChatMessage(IChatComponent chatComponent) {
        // Post the chat message event
        EventBus.INSTANCE.post(new ClientChatReceivedEvent(chatComponent.getUnformattedText()));
    }
}
