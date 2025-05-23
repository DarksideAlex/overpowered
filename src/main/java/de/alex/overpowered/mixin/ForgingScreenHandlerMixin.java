// src/main/java/de/alex/overpowered/mixin/ForgingScreenHandlerMixin.java
package de.alex.overpowered.mixin;

import net.minecraft.inventory.CraftingResultInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;

// Mixin into the class that actually declares `output`
@Mixin(net.minecraft.screen.ForgingScreenHandler.class)
public abstract class ForgingScreenHandlerMixin {
    @Shadow @Final protected CraftingResultInventory output;
}
