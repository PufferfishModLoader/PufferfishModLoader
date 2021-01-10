package me.dreamhopping.pml.launch;

import me.dreamhopping.pml.PufferfishModLoader;
import me.dreamhopping.pml.impl.MinecraftImpl;
import me.dreamhopping.pml.api.Minecraft;
import me.dreamhopping.pml.events.EventBus;
import me.dreamhopping.pml.events.core.GameStartEvent;
import me.dreamhopping.pml.mods.launch.loader.RuntimeTransformer;
import me.dreamhopping.pml.mods.launch.loader.TransformingClassLoader;
import me.dreamhopping.pml.transformers.ClientPlayNetworkHandlerTransformer;
import me.dreamhopping.pml.transformers.ClientPlayerEntityTransformer;
import me.dreamhopping.pml.transformers.InGameHudTransformer;
import me.dreamhopping.pml.transformers.MinecraftClientTransformer;
import net.minecraft.client.main.Main;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.io.File;
import java.util.Arrays;

public class PMLEntryPoint {
    public static void start(String[] args, boolean server) { // Called by PMLClientMain and PMLServerMain via reflection
        TransformingClassLoader classLoader = (TransformingClassLoader) PMLEntryPoint.class.getClassLoader();
        classLoader.registerTransformer(new InGameHudTransformer());
        classLoader.registerTransformer(new ClientPlayerEntityTransformer());
        classLoader.registerTransformer(new ClientPlayNetworkHandlerTransformer());
        classLoader.registerTransformer(new MinecraftClientTransformer());

        Minecraft.setInstance(new MinecraftImpl());

        PufferfishModLoader.INSTANCE.logger.info("PML starting...");
        PufferfishModLoader.INSTANCE.gameDir = new File(args[Arrays.asList(args).indexOf("--gameDir") + 1]);

        EventBus.INSTANCE.register(PufferfishModLoader.INSTANCE);
        EventBus.INSTANCE.post(new GameStartEvent());

        Main.main(args);
    }
}
