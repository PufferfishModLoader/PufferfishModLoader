package me.dreamhopping.pml.mods.launch.loader;

import me.dreamhopping.pml.launch.AccessTransformer;
import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class TransformingClassLoader extends URLClassLoader {
    private final ClassLoader systemLoader = getClass().getClassLoader();
    private final List<String> classLoaderExclusions = new ArrayList<>();
    private final List<String> transformerExclusions = new ArrayList<>();
    private final List<String> signerExclusions = new ArrayList<>();
    private final Map<String, Class<?>> cachedClasses = new ConcurrentHashMap<>();
    private final Set<String> invalidClasses = new HashSet<>();
    private final Set<String> invalidResources = new HashSet<>();
    private final List<RuntimeTransformer> transformers = new ArrayList<>();

    public TransformingClassLoader() {
        super(new URL[0],null);
        addClassLoadExclusion("java.");
        addClassLoadExclusion("sun.");
        addClassLoadExclusion("me.dreamhopping.pml.mods.launch.loader."); // otherwise you can't use this class anywhere except the main class
        addClassLoadExclusion("org.apache.commons.io.");
        addClassLoadExclusion("org.objectweb.asm.");

        registerTransformer(new AccessTransformer());
    }

    public void registerTransformer(RuntimeTransformer transformer) {
        transformers.add(transformer);
    }

    public void addClassLoadExclusion(String exclusion) {
        classLoaderExclusions.add(exclusion);
    }

    public void addTransformerExclusion(String exclusion) {
        transformerExclusions.add(exclusion);
    }

    public void addSignerExclusion(String exclusion) {
        signerExclusions.add(exclusion);
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (invalidClasses.contains(name)) throw new ClassNotFoundException(name);

        if (exclusionsApply(name, classLoaderExclusions)) return systemLoader.loadClass(name);

        {
            Class<?> cached = cachedClasses.get(name);
            if (cached != null) return cached;
        }

        if (exclusionsApply(name, transformerExclusions)) {
            Class<?> clazz = super.findClass(name);
            cachedClasses.put(name, clazz);
            return clazz;
        }

        try {
            String className = name.replace('.', '/');
            String transformedName = process(className);
            {
                Class<?> cached = cachedClasses.get(transformedName);
                if (cached != null) return cached;
            }

            int lastDot = name.lastIndexOf('.');
            String packageName = "";
            if (lastDot != -1) {
                packageName = name.substring(0, lastDot);
            }
            String filename = name.replace('.', '/') +  ".class";
            URL resource = systemLoader.getResource(filename);
            URLConnection connection = null;
            if (resource != null) connection = resource.openConnection();
            CodeSigner[] signers = null;

            if (!packageName.isEmpty() && !exclusionsApply(name, signerExclusions)) {
                if (connection instanceof JarURLConnection) {
                    JarURLConnection jarCon = (JarURLConnection) connection;
                    JarFile file = jarCon.getJarFile();
                    if (file != null && file.getManifest() != null) {
                        Manifest manifest = file.getManifest();
                        JarEntry entry = file.getJarEntry(filename);

                        if (entry != null) {
                            Package pkg = getPackage(packageName);
                            signers = entry.getCodeSigners();
                            if (pkg == null) {
                                definePackage(packageName, manifest, jarCon.getJarFileURL());
                            }
                        }
                    }
                } else {
                    if (getPackage(packageName) == null) {
                        definePackage(packageName, null, null, null, null, null, null, null);
                    }
                }
            }

            byte[] content = process(transformedName, getClassBytes(name));
            CodeSource codeSource = null;
            if (connection != null) {
                codeSource = new CodeSource(connection.getURL(), signers);
            }
            Class<?> cl = defineClass(transformedName.replace('/', '.'), content, 0, content.length, codeSource);
            cachedClasses.put(name, cl);
            cachedClasses.put(transformedName, cl);
            return cl;
        } catch (Throwable e) {
            invalidClasses.add(name);
            if (e instanceof ClassNotFoundException) {
                throw (ClassNotFoundException) e;
            } else {
                throw new ClassNotFoundException(name, e);
            }
        }
    }

    @Override
    public URL findResource(String name) {
        return systemLoader.getResource(name);
    }

    @Override
    public Enumeration<URL> findResources(String name) throws IOException {
        return systemLoader.getResources(name);
    }

    private byte[] process(String name, byte[] bytes) throws ClassNotFoundException {
        if (bytes == null) throw new ClassNotFoundException(name);
        ClassNode node = null;
        for (RuntimeTransformer transformer : transformers) {
            if (transformer.willTransform(name)) {
                if (node == null) {
                    ClassReader reader = new ClassReader(bytes);
                    node = new ClassNode();
                    reader.accept(node, ClassReader.EXPAND_FRAMES);
                }
                node = transformer.transform(node);
            }
        }
        if (node != null) {
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            node.accept(writer);
            return writer.toByteArray();
        } else {
            return bytes;
        }
    }

    private byte[] getClassBytes(String name) throws IOException {
        if (invalidResources.contains(name)) {
            return null;
        }

        URL resource = systemLoader.getResource(name.replace('.', '/') + ".class");
        if (resource != null) {
            try (InputStream stream = resource.openStream()) {
                return IOUtils.toByteArray(stream);
            }
        }
        invalidResources.add(name);
        return null;
    }

    private String process(String name) {
        for (RuntimeTransformer transformer : transformers) {
            name = transformer.transformName(name);
        }
        return name;
    }

    private boolean exclusionsApply(String name, List<String> exclusions) {
        for (String exclusion : exclusions) {
            if (name.startsWith(exclusion)) return true;
        }
        return false;
    }
}
