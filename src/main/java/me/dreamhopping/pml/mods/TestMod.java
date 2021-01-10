package me.dreamhopping.pml.mods;

import me.dreamhopping.pml.events.EventBus;
import me.dreamhopping.pml.events.InvokeEvent;
import me.dreamhopping.pml.events.core.client.chat.ChatReceivedEvent;
import me.dreamhopping.pml.events.core.client.chat.ChatSentEvent;
import me.dreamhopping.pml.events.core.client.player.ItemDropEvent;
import me.dreamhopping.pml.events.core.mod.ModInitEvent;
import me.dreamhopping.pml.mods.core.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    public void onChatReceived(ChatReceivedEvent event) {
        logger.info("TestMod received a chat message: " + event.message);
    }

    @InvokeEvent
    public void onChatSent(ChatSentEvent event) {
        logger.info("Client sent message to server: " + event.message);
    }

    @InvokeEvent
    public void onItemDrop(ItemDropEvent event) {
        logger.info("The client has dropped an item!");
    }
}

