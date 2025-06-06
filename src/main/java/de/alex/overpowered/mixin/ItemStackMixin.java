package de.alex.overpowered.mixin;

import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.*;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "isEnchantable", at = @At("HEAD"), cancellable = true)
    private void MOAR_ENCHANTS(CallbackInfoReturnable<Boolean> cir) { // Do I look like I fucking care about naming conventions!?!?!?
        // This check to make sure, that we don't accidentally enchant a grass block ¯\_(ツ)_/¯
        // Actually, who gives a fuck? Let's make grass blocks enchantable!!! (Removed if-statement)
        // Oh wait, nvm... Grass blocks don't have any valid enchants...
        cir.setReturnValue(true);
    }

    @Inject(method = "addEnchantment", at = @At("TAIL")) // Who the fuck coded the original method!?!? That system is SO not scalable!!!
    private void mergeEnchants(Enchantment enchantment, int level, CallbackInfo ci) {
        ItemStack self = (ItemStack) (Object) this;
        NbtList enchantmentList = self.getEnchantments();
        if (enchantmentList == null || enchantmentList.size() < 2) return;

        Map<Identifier, Integer> totalLevels = new HashMap<>();
        Map<Identifier, Integer> firstOccurrence = new HashMap<>();
        List<Integer> duplicates = new ArrayList<>();

        for (int i = 0; i < enchantmentList.size(); i++) {
            NbtCompound tag = enchantmentList.getCompound(i);
            Identifier id = new Identifier(tag.getString("id"));
            int currentLevel = tag.getShort("lvl");

            if (totalLevels.containsKey(id)) {
                duplicates.add(i);
                totalLevels.put(id, totalLevels.get(id) + currentLevel);
            } else {
                totalLevels.put(id, currentLevel);
                firstOccurrence.put(id, i);
            }
        }

        for (Map.Entry<Identifier, Integer> entry : totalLevels.entrySet()) {
            int firstIndex = firstOccurrence.get(entry.getKey());
            if (firstIndex < enchantmentList.size()) { // Safety check
                NbtCompound firstTag = enchantmentList.getCompound(firstIndex);
                firstTag.putShort("lvl", (short) Math.min(entry.getValue(), 255)); // Cap at 255 (NBT limit)
            }
        }

        duplicates.sort(Comparator.reverseOrder());
        for (int index : duplicates) {
            enchantmentList.remove(index);
        }
    }
}
