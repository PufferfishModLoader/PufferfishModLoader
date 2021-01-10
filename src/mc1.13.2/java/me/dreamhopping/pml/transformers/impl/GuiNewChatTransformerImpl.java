package me.dreamhopping.pml.transformers.impl;

import net.minecraft.util.text.ITextComponent;

/**
 * An implementation for the GuiNewChatTransformer class
 * @see me.dreamhopping.pml.transformers.GuiNewChatTransformer
 */
public class GuiNewChatTransformerImpl {
    public static void printChatMessage(ITextComponent chatComponent) {
        // Post the chat message event
        // EventBus.INSTANCE.post(new ChatMessageEvent(chatComponent.getUnformattedText()));
        // TODO: Post event with text (need to wait for mappings to be fixed)
    }
}
