package me.afatcookie.customitemevents.customitemevents.abstractevents;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.inventory.ItemStack;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class PlaceBucketEvent implements CustomItemEvent<PlayerBucketEmptyEvent> {


    private static final List<PlaceBucketEvent> subclasses = new ArrayList<>();
    public abstract String getID();

    static {
        // Initialize a new Reflections object, searching for classes within the "me.afatcookie.cievents" package
        Reflections reflections = new Reflections("me.afatcookie.customitemevents");
        // Get all subclasses of RightClickAirEvent
        Set<Class<? extends PlaceBucketEvent>> subclassesSet = reflections.getSubTypesOf(PlaceBucketEvent.class);
        // Iterate through all subclasses
        for (Class<? extends PlaceBucketEvent> subClass : subclassesSet) {
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



    public abstract void execute(PlayerBucketEmptyEvent e);

    public static List<PlaceBucketEvent> getSubclasses(){
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
           if (player.getInventory().getItem(i) != itemStack) continue;
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
            if (player.getInventory().getItem(i) != itemStack) continue;
            return player.getInventory().getItem(i);
        }
        return null;
    }
    }

