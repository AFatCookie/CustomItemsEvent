package me.afatcookie.customitemevents.customitemevents.abstractevents.cidesigns;

import com.github.mittenmc.serverutils.Colors;
import me.afatcookie.customitemevents.customitemevents.CustomItemEvents;
import me.afatcookie.customitemevents.customitemevents.abstractevents.RightClickAirEvent;
import me.afatcookie.customitemevents.customitemevents.utils.Utilities;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ChocolateCarrot extends RightClickAirEvent {
    @Override
    public String getID() {
        return "chocolatecarrot";
    }

    @Override
    public void execute(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (CustomItemEvents.getInstance().getCooldownManager().tryUse(player, e.getItem(), Utilities.convertSecondsIntoMillis(10))) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Utilities.convertSecondsIntoTicks(5),
                    5, false, false, false));
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 5, 10);
            player.sendMessage(Colors.conv("&3SUGAR RUSH!!"));
        }
    }
}
