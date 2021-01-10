package me.dreamhopping.pml.events.core.client.chat;

import me.dreamhopping.pml.events.Event;

/**
 * Called when a chat message is receieved by the client
 * @author dreamhopping
 * @see me.dreamhopping.pml.events.Event
 */
public class ClientChatReceivedEvent extends Event {
    public String message;

    // TODO: Change this to use chat component
    public ClientChatReceivedEvent(String message) {
        this.message = message;
    }
}
