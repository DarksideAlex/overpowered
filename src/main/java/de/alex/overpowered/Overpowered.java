package de.alex.overpowered;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.alex.overpowered.config.ConfigManager;

public class Overpowered implements ModInitializer {
	public static final String MOD_ID = "overpowered";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing overpowered!");
		ConfigManager.load();
		LOGGER.info("Overpowered successfully initialized :O");
	}
}