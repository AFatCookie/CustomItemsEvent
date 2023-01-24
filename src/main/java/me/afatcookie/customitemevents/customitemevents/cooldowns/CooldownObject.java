package me.afatcookie.customitemevents.customitemevents.cooldowns;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class CooldownObject {

    private final ItemStack itemStack;

    private final UUID player;

    private final long cooldown;


    public CooldownObject(ItemStack itemStack, UUID player, long cooldown) {
        this.itemStack = itemStack;
        this.player = player;
        this.cooldown = cooldown;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public UUID getPlayer() {
        return player;
    }

    public long getCooldown() {
        return cooldown;
    }
}
