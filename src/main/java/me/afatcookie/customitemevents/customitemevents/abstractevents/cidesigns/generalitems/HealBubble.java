package me.afatcookie.customitemevents.customitemevents.abstractevents.cidesigns.generalitems;

import me.afatcookie.customitemevents.customitemevents.abstractevents.RightClickAirEvent;
import me.afatcookie.customitemevents.customitemevents.utils.Utilities;
import org.bukkit.Color;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class HealBubble extends RightClickAirEvent {


    @Override
    public String getID() {
        return "healbubble";
    }

    @Override
    public void execute(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (instance.getCooldownManager().tryUse(player, customItemsAPI.getCustomItemID(e.getItem()), Utilities.convertSecondsIntoMillis(10))) {
                Arrow arrow = (Arrow) player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.ARROW);
                arrow.setVelocity(player.getLocation().getDirection().multiply(2));
                arrow.setDamage(0);
                arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                arrow.setCustomName("healBubbleArrow");
                arrow.setCustomNameVisible(false);
                arrow.setColor(Color.GREEN);
                arrow.setShooter(player);
            }
        }
    }
