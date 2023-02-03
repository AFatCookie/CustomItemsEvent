package me.afatcookie.customitemevents.customitemevents.cooldowns;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CooldownManager {

    private final ArrayList<CooldownObject> cooldowns = new ArrayList<>();

    public boolean tryUse(Player player, String name, long cooldownTime) {
        long now = System.currentTimeMillis();
        CooldownObject object = findCooldownForPlayer(player, name);
        if (object == null){
            cooldowns.add(new CooldownObject(name, player.getUniqueId(), now));
            return true;
        }
        long lastUse = findCooldownForPlayer(player, name).getCooldown();
        if (now - lastUse >= cooldownTime) {
            cooldowns.remove(findCooldownForPlayer(player, name));
            cooldowns.add(new CooldownObject(name, player.getUniqueId(), now));
            return true;
        } else {
            player.sendMessage("You have " + getRemaining(player, cooldownTime, name) +" seconds left on this cooldown");
            return false;
        }
    }

    public long getRemaining(Player player, long cooldownTime, String itemStack) {
        CooldownObject cooldownObject = findCooldownForPlayer(player, itemStack);
        if (cooldownObject != null) {
            if (((cooldownObject.getCooldown() + cooldownTime - System.currentTimeMillis()) / 1000) < 1){
    return 1;
            }else{
            return (cooldownObject.getCooldown() + cooldownTime - System.currentTimeMillis()) / 1000;
        }
        }
        return 0L;
    }


    public CooldownObject findCooldownForPlayer(Player player, String itemStack){
        for (CooldownObject cooldownObject : cooldowns){
            if (!cooldownObject.getPlayer().equals(player.getUniqueId())) continue;
            if (!cooldownObject.getItemStackName().equals(itemStack)) continue;
            return  cooldownObject;
        }
        return null;
    }


}
