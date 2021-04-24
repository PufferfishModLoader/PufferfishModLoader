package me.dreamhopping.pml.v1_8_9.launch;

import me.dreamhopping.pml.PufferfishModLoader;
import me.dreamhopping.pml.launch.loader.PMLClassLoader;
import me.dreamhopping.pml.launch.transformer.ClassTransformer;
import net.minecraft.client.main.Main;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PMLEntryPoint {
    public static void start(String[] args, boolean server) throws IOException {
        File workDir;
        if (server) {
            workDir = new File(".").getCanonicalFile();
        } else {
            List<String> argsList = Arrays.asList(args);
            if (argsList.contains("--gamedir")) {
                workDir = new File(argsList.get(argsList.indexOf("--gameDir") + 1));
            } else {
                // Most likely a development environment
                workDir = new File(".").getCanonicalFile();
            }
        }

        PMLClassLoader classLoader = (PMLClassLoader) Thread.currentThread().getContextClassLoader();
        classLoader.addTransformer(new ClassTransformer() {
            @Override
            public boolean willTransform(@NotNull String name) {
                return name.equals("net/minecraft/client/Minecraft");
            }

            @NotNull
            @Override
            public ClassNode transformClass(@NotNull ClassNode classNode) {
                for (MethodNode methodNode : classNode.methods) {
                    if (methodNode.name.equals("startGame")) {
                        InsnList insnList = new InsnList();
                        insnList.add(new FieldInsnNode(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
                        insnList.add(new LdcInsnNode("(startGame) Hello from a 1.8.9 transformer!"));
                        insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));

                        methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), insnList);
                    }
                }
                return classNode;
            }
        });

        PufferfishModLoader.INSTANCE.initialize(workDir);
        Main.main(args);
    }
}
