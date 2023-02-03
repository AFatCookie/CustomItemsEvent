package me.afatcookie.customitemevents.customitemevents.abstractevents.cidesigns.particledesigns;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class ParticleCylinder {

    private Player player;
    private int radius;
    private int height;
    private int count;

    public ParticleCylinder(Player player, int radius, int height, int count) {
        this.player = player;
        this.radius = radius;
        this.height = height;
        this.count = count;
    }

    public void createCylinder(Particle particle) {
        Location loc = player.getLocation();
        double x, y, z;
        for (double angle = 0; angle < 360; angle += 10) {
            x = loc.getX() + radius * Math.cos(Math.toRadians(angle));
            z = loc.getZ() + radius * Math.sin(Math.toRadians(angle));
            for (int i = 0; i < height; i++) {
                y = loc.getY() + i;
                loc.setX(x);
                loc.setY(y);
                loc.setZ(z);
                player.getWorld().spawnParticle(particle, loc, count);
            }
        }
    }
}

