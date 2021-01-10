package me.dreamhopping.pml.mods;

import me.dreamhopping.pml.events.EventBus;
import me.dreamhopping.pml.events.InvokeEvent;
import me.dreamhopping.pml.events.core.client.chat.ClientChatReceivedEvent;
import me.dreamhopping.pml.events.core.client.chat.ClientChatSentEvent;
import me.dreamhopping.pml.events.core.client.net.ClientJoinServerEvent;
import me.dreamhopping.pml.events.core.client.player.ClientItemDropEvent;
import me.dreamhopping.pml.events.core.client.player.ClientPlayerRespawnEvent;
import me.dreamhopping.pml.events.core.mod.ModInitEvent;
import me.dreamhopping.pml.mods.core.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;

@Mod("bruh")
public class TestMod {
    public final Logger logger = LogManager.getLogger("TestMod");

    public TestMod() {
        EventBus.INSTANCE.register(this);
    }

    @InvokeEvent
    public void onInit(ModInitEvent event) {
        logger.info("Hello world! TestMod is now awake...");
    }

    @InvokeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        logger.info("TestMod received a chat message: " + event.message);
    }

    @InvokeEvent
    public void onChatSent(ClientChatSentEvent event) {
        logger.info("Client sent message to server: " + event.message);
    }

    @InvokeEvent
    public void onItemDrop(ClientItemDropEvent event) {
        logger.info("The client has dropped an item!");
    }

    @InvokeEvent
    public void onPlayerRespawn(ClientPlayerRespawnEvent event) {
        logger.info("The player has respawned!");
    }

    @InvokeEvent
    public void onJoinServer(ClientJoinServerEvent event) {
        logger.info("The client has connected to a server! " + (event.isLocal ? "It is a local server! (singleplayer)" : "It is an external server (multiplayer) Address: " + ((InetSocketAddress) event.address).getHostString()));
    }
}
