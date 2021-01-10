package me.dreamhopping.pml.events.core.client.net;

import me.dreamhopping.pml.events.Event;

import java.net.SocketAddress;

public class ClientJoinServerEvent extends Event {
    public final boolean isLocal;
    public final SocketAddress address;

    public ClientJoinServerEvent(boolean isLocal, SocketAddress address) {
        this.isLocal = isLocal;
        this.address = address;
    }
}
