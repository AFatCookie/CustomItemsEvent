package me.afatcookie.customitemevents.customitemevents.abstractevents;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class LeftClickAirEvent implements  CustomItemEvent<PlayerInteractEvent>{

    private static final List<LeftClickAirEvent> subclasses = new ArrayList<>();

    static {
        Reflections reflections = new Reflections("me.afatcookie.customitemevents");
        Set<Class<? extends LeftClickAirEvent>> subclassesSet = reflections.getSubTypesOf(LeftClickAirEvent.class);
        for (Class<? extends LeftClickAirEvent> subClass : subclassesSet) {
            try {
                subclasses.add(subClass.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract String getID();

    public abstract void execute(PlayerInteractEvent e);

    public static List<LeftClickAirEvent> getSubclasses(){
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
