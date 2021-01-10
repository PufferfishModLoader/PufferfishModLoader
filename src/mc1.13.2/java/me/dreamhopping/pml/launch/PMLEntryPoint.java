package me.dreamhopping.pml.launch;

import me.dreamhopping.pml.PufferfishModLoader;
import me.dreamhopping.pml.events.EventBus;
import me.dreamhopping.pml.events.core.GameStartEvent;
import me.dreamhopping.pml.impl.MinecraftImpl;
import me.dreamhopping.pml.mods.launch.loader.TransformingClassLoader;
import me.dreamhopping.pml.api.Minecraft;
import me.dreamhopping.pml.transformers.*;
import net.minecraft.client.main.Main;

import java.io.File;
import java.util.Arrays;

public class PMLEntryPoint {
    public static void start(String[] args, boolean server) { // Called by PMLClientMain and PMLServerMain via reflection
        TransformingClassLoader classLoader = (TransformingClassLoader) PMLEntryPoint.class.getClassLoader();
        classLoader.registerTransformer(new GuiNewChatTransformer());
        classLoader.registerTransformer(new EntityPlayerSPTransformer());
        classLoader.registerTransformer(new MainWindowTransformer());
        classLoader.registerTransformer(new NetHandlerPlayClientTransformer());
        classLoader.registerTransformer(new MinecraftTransformer());

        Minecraft.setInstance(new MinecraftImpl());

        PufferfishModLoader.INSTANCE.logger.info("PML starting...");
        PufferfishModLoader.INSTANCE.gameDir = new File(args[Arrays.asList(args).indexOf("--gameDir") + 1]);

        EventBus.INSTANCE.register(PufferfishModLoader.INSTANCE);
        EventBus.INSTANCE.post(new GameStartEvent());

        Main.main(args);
    }
}
