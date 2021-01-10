package me.dreamhopping.pml.transformers;

import me.dreamhopping.pml.mods.launch.loader.RuntimeTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class NetHandlerPlayClientTransformer implements RuntimeTransformer {
    public boolean willTransform(String name) {
        return name.equals("net/minecraft/client/network/NetHandlerPlayClient");
    }

    public ClassNode transform(ClassNode classNode) {
        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals("handleJoinGame")) {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(Opcodes.ALOAD, 0));
                list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/network/NetHandlerPlayClient", "netManager", "Lnet/minecraft/network/NetworkManager;"));
                list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/network/NetworkManager", "isLocalChannel", "()Z"));
                list.add(new VarInsnNode(Opcodes.ALOAD, 0));
                list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/network/NetHandlerPlayClient", "netManager", "Lnet/minecraft/network/NetworkManager;"));
                list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/network/NetworkManager", "getRemoteAddress", "()Ljava/net/SocketAddress;"));
                list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, getImplementationClass("NetHandlerPlayClient"), "handleJoinGame", "(ZLjava/net/SocketAddress;)V"));

                for (AbstractInsnNode node = methodNode.instructions.getLast(); node != null; node = node.getPrevious()) {
                    if (node.getOpcode() == Opcodes.INVOKESTATIC) {
                        MethodInsnNode castedNode = (MethodInsnNode) node;
                        if (castedNode.owner.equals("net/minecraft/network/PacketThreadUtil") && castedNode.name.equals("checkThreadAndEnqueue") && castedNode.desc.equals("(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V")) {
                            methodNode.instructions.insert(castedNode, list);
                        }
                    }
                }
            }
        }

        return classNode;
    }
}
