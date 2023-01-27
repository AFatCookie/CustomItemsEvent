package me.afatcookie.customitemevents.customitemevents.abstractevents.cidesigns.commonitems;

import me.afatcookie.customitemevents.customitemevents.CustomItemEvents;
import me.afatcookie.customitemevents.customitemevents.abstractevents.PlaceBucketEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemStack;

public class InfiniteLavaBucket extends PlaceBucketEvent {
    @Override
    public String getID() {
        return "infinitelavabucket";
    }

    @Override
    public void execute(PlayerBucketEmptyEvent e) {
        Player player = e.getPlayer();

        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();
        ItemStack heldItem = (CustomItemEvents.getInstance().getCiAPI().getCustomItemID(mainHand) != null && CustomItemEvents.getInstance().getCiAPI().
                getCustomItemID(mainHand).equals(getID())) ? mainHand :
                offHand;
        e.setItemStack(heldItem);
    }
}
