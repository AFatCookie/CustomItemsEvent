package me.afatcookie.customitemevents.customitemevents.abstractevents.cidesigns.commonitems;

import me.afatcookie.customitemevents.customitemevents.CustomItemEvents;
import me.afatcookie.customitemevents.customitemevents.abstractevents.PlayerBlockPlaceEvent;
import me.afatcookie.customitemevents.customitemevents.utils.Utilities;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class InfiniteTorch extends PlayerBlockPlaceEvent {
    @Override
    public String getID() {
        return "infinitetorch";
    }

    @Override
    public void execute(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();
        ItemStack heldItem = (CustomItemEvents.getInstance().getCiAPI().getCustomItemID(mainHand) != null && CustomItemEvents.getInstance().getCiAPI().
                getCustomItemID(mainHand).equals(getID())) ? mainHand :
                offHand;
        if (CustomItemEvents.getInstance().getCooldownManager().tryUse(player, CustomItemEvents.getInstance().getCiAPI().getCustomItemID(heldItem), Utilities.convertSecondsIntoMillis(10))) {
            EquipmentSlot hand = e.getHand();
            if (hand == EquipmentSlot.HAND) {
                player.getInventory().setItemInMainHand(heldItem);
            } else if (hand == EquipmentSlot.OFF_HAND) {
                player.getInventory().setItemInOffHand(heldItem);
            }
        }else{
            e.setCancelled(true);
        }
    }
    }
