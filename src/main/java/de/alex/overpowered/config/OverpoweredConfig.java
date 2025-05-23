package de.alex.overpowered.config;

import net.minecraft.nbt.NbtCompound;

public class OverpoweredConfig {
    public int levelMultiplier = 5;

    public void fromNbt(NbtCompound tag) {
        levelMultiplier = tag.getInt("levelMultiplier");
    }

    public NbtCompound toNbt() {
        NbtCompound tag = new NbtCompound();
        tag.putInt("levelMultiplier", levelMultiplier);
        return tag;
    }
}