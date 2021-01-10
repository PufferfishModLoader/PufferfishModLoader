package me.dreamhopping.pml.transformers.impl;

import io.netty.channel.local.LocalAddress;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/*
 * An implementation for the NetHandlerPlayClientTransformer class
 * @see me.dreamhopping.pml.transformers.NetHandlerPlayClientTransformer
 */
public class NetHandlerPlayClientTransformerImpl {
    public static void handleJoinGame(boolean isLocal, SocketAddress address) {
        if (address instanceof InetSocketAddress) {
            System.out.println("Joining game at address: " + ((InetSocketAddress) address).getAddress().toString());
        } else {
            System.out.println("Joining game, is local: " + isLocal);
        }
    }
}
