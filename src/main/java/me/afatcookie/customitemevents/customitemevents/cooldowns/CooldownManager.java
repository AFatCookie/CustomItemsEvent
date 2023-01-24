package me.afatcookie.customitemevents.customitemevents.cooldowns;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CooldownManager {

    private final ArrayList<CooldownObject> cooldowns = new ArrayList<>();

    public boolean tryUse(Player player, ItemStack itemStack, long cooldownTime) {
        long now = System.currentTimeMillis();
        CooldownObject object = findCooldownForPlayer(player);
        if (object == null){
            cooldowns.add(new CooldownObject(itemStack, player.getUniqueId(), now));
            return true;
        }
        long lastUse = findCooldownForPlayer(player).getCooldown();
        if (now - lastUse >= cooldownTime ) {
            cooldowns.remove(findCooldownForPlayer(player));
            cooldowns.add(new CooldownObject(itemStack, player.getUniqueId(), now));
            return true;
        } else {
            player.sendMessage("You have " + getRemaining(player, cooldownTime) +" seconds left on this cooldown");
            return false;
        }
    }

    public long getRemaining(Player player, long cooldownTime) {
        CooldownObject cooldownObject = findCooldownForPlayer(player);
        if (cooldownObject != null) {
            return (cooldownObject.getCooldown() + cooldownTime - System.currentTimeMillis()) / 1000;
        }
        return 0L;
    }


    public CooldownObject findCooldownForPlayer(Player player){
        for (CooldownObject cooldownObject : cooldowns){
            if (cooldownObject.getPlayer() == player.getUniqueId()){
                return cooldownObject;
            }
        }
        return null;
    }


}
