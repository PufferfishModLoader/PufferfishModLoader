package me.dreamhopping.pml.events.core.client.net;

import me.dreamhopping.pml.events.Event;

public class ClientJoinServerEvent extends Event {
    public final boolean isLocal;

    public ClientJoinServerEvent(boolean isLocal) {
        this.isLocal = isLocal;
    }
}
