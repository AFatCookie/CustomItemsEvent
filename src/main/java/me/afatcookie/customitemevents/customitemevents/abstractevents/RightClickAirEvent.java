package me.afatcookie.customitemevents.customitemevents.abstractevents;

import com.mittenmc.customitems.api.CustomItemsAPI;
import me.afatcookie.customitemevents.customitemevents.CustomItemEvents;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class RightClickAirEvent implements CustomItemEvent<PlayerInteractEvent> {

    private static final List<RightClickAirEvent> subclasses = new ArrayList<>();
    public abstract String getID();

    protected CustomItemEvents instance = CustomItemEvents.getInstance();

    protected CustomItemsAPI customItemsAPI = instance.getCiAPI();

    static {
        // Initialize a new Reflections object, searching for classes within the "me.afatcookie.cievents" package
        Reflections reflections = new Reflections("me.afatcookie.customitemevents");
        // Get all subclasses of RightClickAirEvent
        Set<Class<? extends RightClickAirEvent>> subclassesSet = reflections.getSubTypesOf(RightClickAirEvent.class);
        // Iterate through all subclasses
        for (Class<? extends RightClickAirEvent> subClass : subclassesSet) {
            try {
                // Attempt to add a new instance of the subclass to the subclasses list
                // using the class's default constructor
                subclasses.add(subClass.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                // Print the stack trace of any exceptions that occur
                e.printStackTrace();
            }
        }
    }



    public abstract void execute(PlayerInteractEvent e);

    public static List<RightClickAirEvent> getSubclasses(){
        return subclasses;
    }


    /**
     * Find the slot of the itemstack parameterized in the parameterized player's inventory
     * @param itemStack ItemStack to look for
     * @param player Player to look in inventory of
     * @return the slot of the itemstack.
     */
    protected int findItemSlot(ItemStack itemStack, Player player){
        for (int i = 0; i < player.getInventory().getSize(); i++){
            if (player.getInventory().getItem(i) == null) continue;
            if (!player.getInventory().getItem(i).isSimilar(itemStack)) continue;
            return i;
        }
        return -1;
    }
    /**
     * Find the itemstack of the itemstack parameterized in the parameterized player's inventory
     * @param itemStack ItemStack to look for
     * @param player Player to look in inventory of
     * @return the Itemstack, in the slot
     */
    protected ItemStack findMatchingItemStack(ItemStack itemStack, Player player){
        for (int i = 0; i < player.getInventory().getSize(); i++){
            if (player.getInventory().getItem(i) == null) continue;
            if (!player.getInventory().getItem(i).isSimilar(itemStack)) continue;
            return player.getInventory().getItem(i);
        }
        return null;
    }

    /**
     * Checks if the item has the ability to have usages, and then gets the item uses. if the item uses are less than or
     * equal to 0, it'll remove the item. This is meant to be used before executing the functionality of usage items
     * to prevent any glitches and players getting an extra use.
     * @param itemStack The item stack to check
     * @param player Player to check inventory to destroy item
     * @return if the item has potential to be used or not.
     */
    protected boolean hasUsesPotential(ItemStack itemStack, Player player) {
        if (!customItemsAPI.doesItemHaveUses(itemStack)) return false;
        if (customItemsAPI.getItemUses(itemStack) <= 0){
            player.getInventory().setItem(findItemSlot(itemStack, player), new ItemStack(Material.AIR));
            player.sendMessage(ChatColor.RED + "Your item ran out of usages!");
            return false;
        }
        return true;
    }
    /**
     * Updates the item usage amount, by decreasing it. if the item was on it's last use, it will remove the item.
     * @param currentUsages current usages at time of execution for the item.
     * @param decrease the amount to decrease the usages of the item by
     * @param player Player to look at
     * @param itemStack Itemstack to check and manage
     */
    protected void decreaseItemUsage(int currentUsages, int decrease, Player player, ItemStack itemStack){
        if (currentUsages > 1) {
            customItemsAPI.decreaseItemUses(itemStack, decrease);
        } else {
            player.getInventory().setItem(findItemSlot(itemStack, player), new ItemStack(Material.AIR));
            player.sendMessage(ChatColor.RED + "Your item ran out of usages!");
        }
    }
}

