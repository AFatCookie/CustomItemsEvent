package me.afatcookie.customitemevents.customitemevents.CIListener;

import com.mittenmc.customitems.api.CustomItemsAPI;
import me.afatcookie.customitemevents.customitemevents.CustomItemEvents;
import me.afatcookie.customitemevents.customitemevents.abstractevents.*;
import me.afatcookie.customitemevents.customitemevents.abstractevents.cidesigns.ProjectileHitGroundEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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
        ItemStack item = determineHandInUse(e.getPlayer(), e.getHand());
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
        ItemStack item = determineHandInUse(e.getPlayer(), e.getHand());
        if (item != null) {
            for (PlaceBucketEvent useBucketEvent : PlaceBucketEvent.getSubclasses()){
                executeEvent(useBucketEvent, item,  e);
            }
        }
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent e) {
        ItemStack item = determineHandInUse(e.getPlayer(), e.getHand());
        if (item != null) {
            for (PlayerBlockPlaceEvent blockPlaceEvent : PlayerBlockPlaceEvent.getSubclasses()) {
                executeEvent(blockPlaceEvent, item, e);
            }
        }
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent e){
        Player player = e.getPlayer();
        checkPlayerForCI(player, e);
    }

    @EventHandler
    public void onProjectileHitGEvent(ProjectileHitEvent e){
        if (e.getEntity().getCustomName() == null) return;
        for (ProjectileHitGroundEvent projectileHitGroundEvent : ProjectileHitGroundEvent.getSubclasses()){
            executeEvent(projectileHitGroundEvent, e.getEntity().getCustomName(), e);
        }
    }

    private void checkPlayerForCI(Player player, PlayerMoveEvent event){
        ItemStack[] inventoryItems = player.getInventory().getStorageContents();
        ItemStack[] armorItems = player.getInventory().getArmorContents();
        for (ItemStack itemStack : inventoryItems){
            //Makes sure item is a custom item and is not armor.
            if (validateItem(itemStack) && itemNotArmor(itemStack)) {
                for (PlayerMovementEvent playerMovementEvent : PlayerMovementEvent.getSubclasses()) {
                    executeEvent(playerMovementEvent, itemStack, event);
                }
            }
            }

        for (int i = 0; i < armorItems.length; i++) {
            //checks if the item is a custom item and the player is actively wearing it.
            if (validateItem(armorItems[i]) && player.getInventory().getArmorContents()[i].isSimilar(armorItems[i])) {
                for (PlayerMovementEvent playerMovementEvent : PlayerMovementEvent.getSubclasses()) {
                    executeEvent(playerMovementEvent, armorItems[i], event);
                }
            }

        }
    }

    private boolean validateItem(ItemStack itemStack){
        if (itemStack == null) return false;
        if (itemStack.getType() == Material.AIR) return false;
        return customItemsAPI.isCustomItem(itemStack);
    }

    private boolean itemNotArmor(ItemStack itemStack){
        if (itemStack.getType().toString().contains("HELMET")) return false;
        if (itemStack.getType().toString().contains("CHESTPLATE")) return false;
        if (itemStack.getType().toString().contains("LEGGINGS")) return false;
        return !itemStack.getType().toString().contains("BOOTS");
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

    /**
     * This uses the id matching, rather than grabbing the item and checking for the id within the method.
     * @param eventClass which type of event has happened
     * @param id id to compare to
     * @param event gets the event needed to register what happened
     * @param <T> the event
     */
    public <T> void executeEvent(CustomItemEvent<T> eventClass, String id, T event) {
        // checks if the id provided is equal to any of the classes which extend the eventClass.
        if (eventClass.getID().equalsIgnoreCase(id)) {
            eventClass.execute(event);
        }
    }



    private ItemStack determineHandInUse(Player player, EquipmentSlot equipmentSlot){
        ItemStack mainHandItem = validateMainHand(player);
        ItemStack offHandItem = validateOffHand(player);
        return equipmentSlot == EquipmentSlot.HAND ? mainHandItem : offHandItem;
    }


}
