package me.dreamhopping.pml.events.core.client;

import me.dreamhopping.pml.events.Event;

/**
 * Called when a chat message is receieved by the client
 * @author dreamhopping
 * @see me.dreamhopping.pml.events.Event
 */
public class ChatMessageEvent extends Event {
    public String message;

    // TODO: Change this to use chat component
    public ChatMessageEvent(String message) {
        this.message = message;
    }
}
