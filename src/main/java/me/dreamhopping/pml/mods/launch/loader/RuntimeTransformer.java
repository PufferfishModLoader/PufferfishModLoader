package me.dreamhopping.pml.mods.launch.loader;

import org.objectweb.asm.tree.ClassNode;

public interface RuntimeTransformer {
    boolean willTransform(String name);

    default String transformName(String original) {
        return original;
    }

    default String getImplementationClass(String clazz) {
        return "me/dreamhopping/pml/transformers/impl/" + clazz + "TransformerImpl";
    }

    ClassNode transform(ClassNode node);
}
