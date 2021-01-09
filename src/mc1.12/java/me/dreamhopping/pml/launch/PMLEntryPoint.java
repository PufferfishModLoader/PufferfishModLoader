package me.dreamhopping.pml.launch;

import me.dreamhopping.pml.PufferfishModLoader;
import me.dreamhopping.pml.events.core.GameStartEvent;
import me.dreamhopping.pml.api.Minecraft;
import me.dreamhopping.pml.events.EventBus;
import me.dreamhopping.pml.impl.MinecraftImpl;
import me.dreamhopping.pml.mods.launch.loader.RuntimeTransformer;
import me.dreamhopping.pml.mods.launch.loader.TransformingClassLoader;
import me.dreamhopping.pml.transformers.GuiNewChatTransformer;
import net.minecraft.client.main.Main;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.io.File;
import java.util.Arrays;

public class PMLEntryPoint {
    public static void start(String[] args, boolean server) { // Called by PMLClientMain and PMLServerMain via reflection
        TransformingClassLoader classLoader = (TransformingClassLoader) PMLEntryPoint.class.getClassLoader();
        classLoader.registerTransformer(new GuiNewChatTransformer());

        // Transform the window title
        classLoader.registerTransformer(new RuntimeTransformer() {
            @Override
            public boolean willTransform(String name) {
                return name.equals("net/minecraft/client/Minecraft");
            }

            @Override
            public ClassNode transform(ClassNode classNode) {
                for (MethodNode methodNode : classNode.methods) {
                    if (methodNode.name.equals("createDisplay")) {
                        InsnList list = new InsnList();
                        list.add(new LdcInsnNode("Minecraft 1.12 | PufferfishModLoader (dev/1.0)"));

                        // Find the "toString" call and insert before that
                        for (AbstractInsnNode node = methodNode.instructions.getLast(); node != null; node = node.getPrevious()) {
                            if (node.getOpcode() == Opcodes.INVOKESTATIC) {
                                MethodInsnNode castedNode = (MethodInsnNode) node;
                                if (castedNode.owner.equals("org/lwjgl/opengl/Display") && castedNode.name.equals("setTitle") && castedNode.desc.equals("(Ljava/lang/String;)V")) {
                                    // Remove the previous "Minecraft (version)" LDC
                                    methodNode.instructions.remove(node.getPrevious());
                                    methodNode.instructions.insertBefore(castedNode, list);                                }
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
