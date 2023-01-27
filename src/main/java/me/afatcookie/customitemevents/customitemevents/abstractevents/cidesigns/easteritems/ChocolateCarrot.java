package me.afatcookie.customitemevents.customitemevents.abstractevents.cidesigns.easteritems;

import com.github.mittenmc.serverutils.Colors;
import me.afatcookie.customitemevents.customitemevents.abstractevents.RightClickAirEvent;
import me.afatcookie.customitemevents.customitemevents.utils.Utilities;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * USAGE ITEM
 */
public class ChocolateCarrot extends RightClickAirEvent {
    @Override
    public String getID() {
        return "chocolatecarrot";
    }

    @Override
    public void execute(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (instance.getCooldownManager().tryUse(player, customItemsAPI.getCustomItemID(e.getItem()), Utilities.convertSecondsIntoMillis(10))) {
            if (hasUsesPotential(e.getItem(), player)) {
                int usages = customItemsAPI.getItemUses(e.getItem());
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Utilities.convertSecondsIntoTicks(5),
                        5, false, false, false));
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 5, 10);
                player.sendMessage(Colors.conv("&3SUGAR RUSH!!"));
                decreaseItemUsage(usages, 1, player, e.getItem());
            }
        }
    }

}
