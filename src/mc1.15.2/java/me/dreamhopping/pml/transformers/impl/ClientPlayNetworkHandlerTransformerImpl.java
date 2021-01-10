package me.dreamhopping.pml.transformers.impl;

import me.dreamhopping.pml.events.EventBus;
import me.dreamhopping.pml.events.core.client.net.ClientJoinServerEvent;

import java.net.SocketAddress;

/**
 * An implementation for the ClientPlayNetworkHandlerTransformer class
 *
 * @see me.dreamhopping.pml.transformers.ClientPlayNetworkHandlerTransformer
 */
public class ClientPlayNetworkHandlerTransformerImpl {
    public static void onGameJoin(boolean isLocal, SocketAddress address) {
        EventBus.INSTANCE.post(new ClientJoinServerEvent(isLocal, address));
    }
}
