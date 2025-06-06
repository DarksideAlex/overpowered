package de.alex.overpowered.config;

import net.minecraft.nbt.NbtCompound;

public class OverpoweredConfig {
    public int levelMultiplier = 5;
    public Boolean noLevelLimit = true;

    public void copy(OverpoweredConfig other) {
        this.levelMultiplier = other.levelMultiplier;
        this.noLevelLimit = other.noLevelLimit;
    }

    public void fromNbt(NbtCompound tag) {
        levelMultiplier = tag.getInt("levelMultiplier");
        noLevelLimit = tag.getBoolean("noLevelLimit");
    }

    public NbtCompound toNbt() {
        NbtCompound tag = new NbtCompound();
        tag.putInt("levelMultiplier", levelMultiplier);
        tag.putBoolean("noLevelLimit", noLevelLimit);
        return tag;
    }
}