package me.afatcookie.customitemevents.customitemevents.abstractevents.cidesigns;

import com.mittenmc.customitems.api.CustomItemsAPI;
import me.afatcookie.customitemevents.customitemevents.CustomItemEvents;
import me.afatcookie.customitemevents.customitemevents.abstractevents.CustomItemEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class ProjectileHitGroundEvent implements CustomItemEvent<ProjectileHitEvent> {


    private static final List<ProjectileHitGroundEvent> subclasses = new ArrayList<>();

    static {
        Reflections reflections = new Reflections("me.afatcookie.customitemevents");
        Set<Class<? extends ProjectileHitGroundEvent>> subclassesSet = reflections.getSubTypesOf(ProjectileHitGroundEvent.class);
        for (Class<? extends ProjectileHitGroundEvent> subClass : subclassesSet) {
            try {
                subclasses.add(subClass.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    protected CustomItemEvents instance = CustomItemEvents.getInstance();

    protected CustomItemsAPI customItemsAPI = instance.getCiAPI();

    public abstract String getID();

    public abstract void execute(ProjectileHitEvent e);

    public static List<ProjectileHitGroundEvent> getSubclasses(){
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

    protected void createCircleOnLocationWithEffect(ProjectileHitEvent e, int circles, int radius, Particle particle, PotionEffect potionEffect, int iterationIncrease){
        // Get the location where the projectile hit
        Location entityLocation = e.getEntity().getLocation();

        // Remove the projectile entity
        e.getEntity().remove();

        // Start a new BukkitRunnable
        new BukkitRunnable() {
            int i = 0; // Initialize the rotation angle

            @Override
            public void run() {
                // If the angle has completed 3 full rotations, cancel the task
                if (i >= 360 * circles) {
                    cancel();
                }

                // Calculate the x and z position of the particle using the angle
                double x = Math.cos(Math.toRadians(i)) * radius;
                double z = Math.sin(Math.toRadians(i)) * radius;

                // Create a new location for the particle, offset from the entity location
                Location particleLoc = entityLocation.clone().add(x, 0, z);

                // Spawn the particle at the calculated location
                entityLocation.getWorld().spawnParticle(particle, particleLoc, 5, 0, 0, 0, 0);

                // Check if any players are within a 3 block radius of the entity location
                for (Entity entity : entityLocation.getWorld().getNearbyEntities(entityLocation, 3, 3, 3)) {
                    if (entity instanceof Player){
                        Player player = (Player) entity;
                        // Apply the ABSORPTION potion effect to any nearby players
                        player.addPotionEffect(potionEffect);
                    }
                }

                // Increase the angle by 5 for the next iteration
                i = i+ iterationIncrease;
            }
        }.runTaskTimer(instance, 0 ,1L); // Run the task every tick (20 times per second)
    }
}
