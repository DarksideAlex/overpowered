package de.alex.overpowered.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;

import static com.mojang.serialization.codecs.RecordCodecBuilder.build;

public class ConfigScreenProvider implements ConfigScreenFactory {
    @Override
    public Screen create(Screen parent) {
        OverpoweredConfig config = ConfigManager.CONFIG;
        OverpoweredConfig temp = new OverpoweredConfig();
        temp.copy(config);

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
                                                () -> temp.levelMultiplier,
                                                val -> {
                                                    temp.levelMultiplier = val;
                                                }
                                        )
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .build(),
                                Option.<Boolean>createBuilder()
                                        .name(Text.literal("No Level Limit"))
                                        .description(OptionDescription.of(Text.literal("Removes the annoying \"Too Expensive!\" message from the anvil menu.")))
                                        .binding(
                                                true,
                                                () -> temp.noLevelLimit,
                                                val -> {
                                                    temp.noLevelLimit = val;
                                                }
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .build()
                        ))
                        .build()
                )
                .save(()->{
                    config.copy(temp);
                    ConfigManager.save();
                })
                .build()
                .generateScreen(parent);
    }
}
