package me.dreamhopping.pml.transformers.impl;

import me.dreamhopping.pml.events.EventBus;
import me.dreamhopping.pml.events.core.client.chat.ChatReceivedEvent;
import net.minecraft.util.text.ITextComponent;

/**
 * An implementation for the GuiNewChatTransformer class
 * @see me.dreamhopping.pml.transformers.GuiNewChatTransformer
 */
public class GuiNewChatTransformerImpl {
    public static void printChatMessage(ITextComponent chatComponent) {
        EventBus.INSTANCE.post(new ChatReceivedEvent(chatComponent.getUnformattedComponentText()));
    }
}
