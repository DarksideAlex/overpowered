package de.alex.overpowered.mixin;

import de.alex.overpowered.config.ConfigManager;
import de.alex.overpowered.config.OverpoweredConfig;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.Property;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {
    //@Final
    //@Shadow
    //protected CraftingResultInventory output;

    @Final
    @Shadow
    public Property levelCost;

    @Inject(method = "updateResult", at = @At("HEAD"), cancellable = true)
    private void overpowerEm(CallbackInfo ci) {

        OverpoweredConfig config = ConfigManager.CONFIG;

        AnvilScreenHandler self = (AnvilScreenHandler) (Object) this;

        ItemStack left = self.getSlot(0).getStack();
        ItemStack right = self.getSlot(1).getStack();

        if ((right.getItem() == Items.ENCHANTED_BOOK || right.getItem() == left.getItem()) && left.isEnchantable()) {
            Map<Enchantment, Integer> resultMap = new HashMap<>(EnchantmentHelper.get(left));
            EnchantmentHelper.get(right).forEach((enchant, level) -> {
                int current = resultMap.getOrDefault(enchant, 0);
                int newLevel = current + level;
                int cappedLevel = (enchant.getMaxLevel() == 1) ? 1 : newLevel;
                resultMap.put(enchant, cappedLevel);
            });

            ItemStack result = left.copy();

            float repairCost = 0;
            if (left.isDamageable() && right.isDamageable() && left.getItem() == right.getItem()) {
                float leftDur = left.getMaxDamage() - left.getDamage();
                float rightDur = right.getMaxDamage() - right.getDamage();
                float combined = leftDur + rightDur;
                float newDamage = Math.min(left.getMaxDamage(), Math.max(left.getMaxDamage() - combined, 0));
                result.setDamage((int) newDamage);

                repairCost = ( 1 + (float) left.getMaxDamage() / 128) * (Math.max(left.getDamage(), right.getDamage()) - newDamage) / left.getMaxDamage();
            }

            EnchantmentHelper.set(resultMap, result);

            int enchantCost = EnchantmentHelper.get(result)
                    .values()
                    .stream()
                    .mapToInt(Integer::intValue)
                    .sum();

            int levelModifier = config.levelMultiplier;

            float totalCost = enchantCost + repairCost;

            self.output.setStack(0, result);
            this.levelCost.set((int) (totalCost * levelModifier));

            ci.cancel();
        }
    }
}
