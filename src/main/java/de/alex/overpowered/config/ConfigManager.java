package de.alex.overpowered.config;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;

import static de.alex.overpowered.Overpowered.MOD_ID;

public class ConfigManager {
    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("overpowered_config.nbt").toFile();
    public static final OverpoweredConfig CONFIG = new OverpoweredConfig();

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try {
                byte[] data = Files.readAllBytes(CONFIG_FILE.toPath());
                NbtCompound tag = NbtIo.readCompressed(new ByteArrayInputStream(data));
                CONFIG.fromNbt(tag);
                LOGGER.info("Loaded config successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            LOGGER.info("No config found, generating config...");
            save();
            LOGGER.info("Config generated!");
            // This will probably work (I hope)
        }
    }

    public static void save() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            NbtIo.writeCompressed(CONFIG.toNbt(), out);
            Files.write(CONFIG_FILE.toPath(), out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
