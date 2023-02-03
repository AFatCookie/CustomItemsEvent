package me.afatcookie.customitemevents.customitemevents.abstractevents.cidesigns.particledesigns;

import me.afatcookie.customitemevents.customitemevents.CustomItemEvents;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class ParticleCircle {
    private final int radius;
    private final Location center;
    public ParticleCircle(Player player, int radius) {
        center = player.getLocation();
        this.radius = radius;
    }

    /**
     * Create a sphere around the player with the particle of choice, and how many times you want it to create a circle
     * @param particle Particle of choice
     * @param end how many times u want it to create sphere. (i.e. 8 would mean that it will create sphere 8 times).
     */
    public void display(Particle particle, int end) {
        // Create a new BukkitRunnable to display the particle effect in a spherical shape
        new BukkitRunnable() {
            int t = 0; // Counter variable to keep track of how many iterations have passed

            @Override
            public void run() {
                // Check if the end has been reached, if so cancel the runnable
                if (t >= end) {
                    cancel();
                }
                // Loop through all angles of the sphere
                for (int i = 0; i < 360; i += 4) {
                    for (int j = 0; j < 180; j += 4) {
                        double theta = Math.toRadians(i); // Convert the angle to radians
                        double phi = Math.toRadians(j); // Convert the angle to radians
                        // Calculate the x, y, and z positions of the particle based on the spherical coordinates
                        double x = center.getX() + radius * Math.sin(phi) * Math.cos(theta);
                        double y = center.getY() + radius * Math.cos(phi);
                        double z = center.getZ() + radius * Math.sin(phi) * Math.sin(theta);
                        // Create a new Location object with the calculated x, y, and z positions
                        Location particleLocation = new Location(center.getWorld(), x, y, z);
                        // Spawn the particle at the calculated location
                        center.getWorld().spawnParticle(particle, particleLocation, 1);
                    }
                }
                t++; // Increment the counter variable
            }
        }.runTaskTimer(CustomItemEvents.getInstance(), 0L, 2L); // Run the runnable with a delay of 0 ticks and a repeating interval of 2 ticks.
    }





    public void removeProjectilesInCircle(Particle particle, int endDisplay, int endEntityCheck) {
        for (int i = 0; i < endEntityCheck; i++) {
            Collection<Entity> nearbyEntities = center.getWorld().getNearbyEntities(center, radius, radius, radius);
            for (Entity entity : nearbyEntities) {
                if (entity.getLocation().distanceSquared(center) <= radius * radius) {
                    if (!(entity instanceof LivingEntity)) {
                        entity.remove();
                    }
                }
            }
        }
        display(particle, endDisplay);
    }
}

