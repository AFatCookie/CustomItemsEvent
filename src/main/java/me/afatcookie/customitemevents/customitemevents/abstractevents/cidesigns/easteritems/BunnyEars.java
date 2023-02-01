package me.afatcookie.customitemevents.customitemevents.abstractevents.cidesigns.easteritems;

import me.afatcookie.customitemevents.customitemevents.abstractevents.PlayerMovementEvent;
import me.afatcookie.customitemevents.customitemevents.utils.Utilities;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BunnyEars extends PlayerMovementEvent {
    @Override
    public String getID() {
        return "bunnyears";
    }

    @Override
    public void execute(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Utilities.convertSecondsIntoTicks(5), 3, false));
    }
}
