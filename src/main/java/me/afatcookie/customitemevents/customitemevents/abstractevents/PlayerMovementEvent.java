package me.afatcookie.customitemevents.customitemevents.abstractevents;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class PlayerMovementEvent implements  CustomItemEvent<PlayerMoveEvent>  {

    private static  final List<PlayerMovementEvent> subclasses = new ArrayList<>();
    public abstract String getID();

    static {
        Reflections reflections = new Reflections("me.afatcookie.customitemevents");
        Set<Class<? extends PlayerMovementEvent>> subclassesSet = reflections.getSubTypesOf(PlayerMovementEvent.class);
        for (Class<? extends PlayerMovementEvent> subClass : subclassesSet) {
            try {
                subclasses.add(subClass.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void execute(PlayerMoveEvent e);

    public static List<PlayerMovementEvent> getSubclasses(){
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

