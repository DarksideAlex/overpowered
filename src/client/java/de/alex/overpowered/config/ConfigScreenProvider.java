package de.alex.overpowered.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;

public class ConfigScreenProvider implements ConfigScreenFactory {
    @Override
    public Screen create(Screen parent) {
        OverpoweredConfig config = ConfigManager.CONFIG;

        return YetAnotherConfigLib.createBuilder()
                .title(Text.literal("Overpowered Config"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("General"))
                        .options(List.of(
                                Option.<Integer>createBuilder()
                                        .name(Text.literal("Anvil Level Multiplier"))
                                        .description(OptionDescription.of(Text.literal("XP levels required per final enchantment tier (e.g. Smite VII costs 7 × this value).")))
                                        .binding(
                                                5,
                                                () -> config.levelMultiplier,
                                                val -> {
                                                    config.levelMultiplier = val;
                                                    ConfigManager.save();
                                                }
                                        )
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .build()
                        ))
                        .build()
                )
                .build()
                .generateScreen(parent);
    }
}
