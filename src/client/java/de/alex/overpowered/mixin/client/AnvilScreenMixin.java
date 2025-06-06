package de.alex.overpowered.mixin.client;

import de.alex.overpowered.config.ConfigManager;
import de.alex.overpowered.config.OverpoweredConfig;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.entity.player.PlayerAbilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilScreen.class)
public class AnvilScreenMixin {
    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerAbilities;creativeMode:Z"), method = "drawForeground")
    private boolean creativeMode(PlayerAbilities instance) {
        OverpoweredConfig config = ConfigManager.CONFIG;
        return config.noLevelLimit || instance.creativeMode;
    }
}