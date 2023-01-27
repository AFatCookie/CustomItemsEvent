package me.afatcookie.customitemevents.customitemevents.abstractevents.cidesigns.easteritems;
import me.afatcookie.customitemevents.customitemevents.abstractevents.RightClickAirEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EggLauncher extends RightClickAirEvent {
    @Override
    public String getID() {
        return "egglauncher";
    }

    @Override
    public void execute(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (player.getInventory().containsAtLeast(new ItemStack(Material.EGG), 1)) {
            if (hasUsesPotential(e.getItem(), player)) {
                int usages = customItemsAPI.getItemUses(e.getItem());
                Arrow arrow = (Arrow) player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.ARROW);
                arrow.setVelocity(player.getLocation().getDirection().multiply(2));
                arrow.setDamage(0);
                Egg egg = (Egg) arrow.getWorld().spawnEntity(arrow.getLocation(), EntityType.EGG);
                egg.setVelocity(arrow.getVelocity());
                egg.setCustomNameVisible(false);
                egg.setShooter(arrow.getShooter());
                arrow.remove();
                int slot = player.getInventory().first(Material.EGG);
                player.getInventory().getItem(slot).setAmount(player.getInventory().getItem(slot).getAmount() - 1);
                decreaseItemUsage(usages, 1, player, e.getItem());
            }

        }else{
            player.sendMessage(ChatColor.RED + "Please have at least one egg in your inventory to use this item!");
        }
    }

}
