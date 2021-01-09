package me.dreamhopping.pml.mods;

import me.dreamhopping.pml.events.EventBus;
import me.dreamhopping.pml.events.InvokeEvent;
import me.dreamhopping.pml.events.core.client.ChatMessageEvent;
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
    public void onChatMessage(ChatMessageEvent event) {
        logger.info("TestMod received a chat message: " + event.message);
    }
}
