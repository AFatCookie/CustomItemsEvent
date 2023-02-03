package me.afatcookie.customitemevents.customitemevents.abstractevents.cidesigns.generalitems;

import me.afatcookie.customitemevents.customitemevents.abstractevents.RightClickAirEvent;
import me.afatcookie.customitemevents.customitemevents.abstractevents.cidesigns.particledesigns.ParticleCircle;
import me.afatcookie.customitemevents.customitemevents.utils.Utilities;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class ProjectileDismantle extends RightClickAirEvent {
    @Override
    public String getID() {
        return "projectiledismantle";
    }

    @Override
    public void execute(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (instance.getCooldownManager().tryUse(player, customItemsAPI.getCustomItemID(e.getItem()), Utilities.convertSecondsIntoMillis(10))) {
            ParticleCircle circle = new ParticleCircle(player, 3);
                circle.removeProjectilesInCircle(Particle.VILLAGER_HAPPY, 1, 4);
        }
    }
}
