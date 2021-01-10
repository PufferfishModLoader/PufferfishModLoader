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

        // Transform the window title
        classLoader.registerTransformer(new RuntimeTransformer() {
            @Override
            public boolean willTransform(String name) {
                return name.equals("net/minecraft/client/MinecraftClient");
            }

            @Override
            public ClassNode transform(ClassNode classNode) {
                for (MethodNode methodNode : classNode.methods) {
                    if (methodNode.name.equals("getWindowTitle")) {
                        InsnList list = new InsnList();
                        list.add(new LdcInsnNode(" | PufferfishModLoader (dev/1.0)"));
                        list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;"));

                        // Find the "toString" call and insert before that
                        for (AbstractInsnNode node = methodNode.instructions.getLast(); node != null; node = node.getPrevious()) {
                            if (node.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                                MethodInsnNode castedNode = (MethodInsnNode) node;
                                if (castedNode.owner.equals("java/lang/StringBuilder") && castedNode.name.equals("toString") && castedNode.desc.equals("()Ljava/lang/String;")) {
                                    methodNode.instructions.insertBefore(castedNode, list);
                                }
                            }
                        }
                    }
                }

                return classNode;
            }
        });

        Minecraft.setInstance(new MinecraftImpl());

        PufferfishModLoader.INSTANCE.logger.info("PML starting...");
        PufferfishModLoader.INSTANCE.gameDir = new File(args[Arrays.asList(args).indexOf("--gameDir") + 1]);

        EventBus.INSTANCE.register(PufferfishModLoader.INSTANCE);
        EventBus.INSTANCE.post(new GameStartEvent());

        Main.main(args);
    }
}
