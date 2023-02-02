package me.afatcookie.customitemevents.customitemevents.abstractevents.cidesigns.projectiles;

import me.afatcookie.customitemevents.customitemevents.abstractevents.cidesigns.ProjectileHitGroundEvent;
import org.bukkit.Particle;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HealBubbleProjectile extends ProjectileHitGroundEvent {
    @Override
    public String getID() {
        return "healBubbleArrow";
    }

    @Override
    public void execute(ProjectileHitEvent e) {
        createCircleOnLocationWithEffect(e, 3, 3, Particle.VILLAGER_HAPPY,new PotionEffect(PotionEffectType.ABSORPTION, 5, 3, true, false), 5 );
    }

    }
