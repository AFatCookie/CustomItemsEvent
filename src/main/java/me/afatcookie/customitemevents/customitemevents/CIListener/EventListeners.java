package me.afatcookie.customitemevents.customitemevents.CIListener;

import com.mittenmc.customitems.api.CustomItemsAPI;
import me.afatcookie.customitemevents.customitemevents.CustomItemEvents;
import me.afatcookie.customitemevents.customitemevents.abstractevents.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class EventListeners implements Listener {

    private final  CustomItemsAPI customItemsAPI;
    public EventListeners(CustomItemEvents instance){
        customItemsAPI = instance.getCiAPI();
    }
    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        // Get the custom items in the main and offHand
        ItemStack item = determineItemInUse(e.getPlayer(), e.getHand());
        // Check if the item is a custom item
        if (item != null) {
            // Check if the player right-clicked or left-clicked in the air
            if (e.getAction() == Action.RIGHT_CLICK_AIR) {
                for (RightClickAirEvent rightClickAirEvent : RightClickAirEvent.getSubclasses()) {
                    executeEvent(rightClickAirEvent, item, e);
                }
            } else if (e.getAction() == Action.LEFT_CLICK_AIR) {
                for (LeftClickAirEvent leftClickAirEvent : LeftClickAirEvent.getSubclasses()) {
                    executeEvent(leftClickAirEvent, item, e);
                }
            }
        }
    }


    @EventHandler
    public void onBucketEvent(PlayerBucketEmptyEvent e){
        ItemStack item = determineItemInUse(e.getPlayer(), e.getHand());
        if (item != null) {
            for (PlaceBucketEvent useBucketEvent : PlaceBucketEvent.getSubclasses()){
                executeEvent(useBucketEvent, item,  e);
            }
        }
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent e) {
        ItemStack item = determineItemInUse(e.getPlayer(), e.getHand());
        if (item != null) {
            for (PlayerBlockPlaceEvent blockPlaceEvent : PlayerBlockPlaceEvent.getSubclasses()) {
                executeEvent(blockPlaceEvent, item, e);
            }
        }
    }
    private ItemStack validateMainHand(Player player){
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        if (customItemsAPI.isCustomItem(mainHand)) {
            return mainHand;
        }
        return null;
    }
    private ItemStack validateOffHand(Player player){
        ItemStack offHand = player.getInventory().getItemInOffHand();
        if (customItemsAPI.isCustomItem(offHand)) {
            return offHand;
        }
        return null;
    }

    private String getId(ItemStack itemStack){
return customItemsAPI.getCustomItemID(itemStack);
    }

    public <T> void executeEvent(CustomItemEvent<T> eventClass, ItemStack item, T event) {
        // Check if the main hand item is a custom item with the same id as the current event
        if (eventClass.getID().equalsIgnoreCase(getId(item))) {
            eventClass.execute(event);
        }
    }

    private ItemStack determineItemInUse(Player player, EquipmentSlot equipmentSlot){
        ItemStack mainHandItem = validateMainHand(player);
        ItemStack offHandItem = validateOffHand(player);
        return equipmentSlot == EquipmentSlot.HAND ? mainHandItem : offHandItem;
    }


}
