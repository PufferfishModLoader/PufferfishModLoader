package me.dreamhopping.pml.events.core.client.net;

import me.dreamhopping.pml.events.Event;

/**
 * Fired when the client is disconnected from the server
 * This is client sided only, for server:
 * @see me.dreamhopping.pml.events.core.server.net.ServerDisconnectEvent
 */
public class ClientDisconnectedEvent extends Event {
    public final String reason;

    /**
     * @param reason: The reason the client was disconnected from the server
     */
    public ClientDisconnectedEvent(String reason) {
        this.reason = reason;
    }
}
