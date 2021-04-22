package me.dreamhopping.pml.v1_15_2.launch;

import me.dreamhopping.pml.PufferfishModLoader;
import net.minecraft.client.main.Main;

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

        PufferfishModLoader.INSTANCE.initialize(workDir);
        Main.main(args);
    }
}
