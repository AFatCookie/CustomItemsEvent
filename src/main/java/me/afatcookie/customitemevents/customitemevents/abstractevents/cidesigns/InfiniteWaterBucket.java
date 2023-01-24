package me.afatcookie.customitemevents.customitemevents.abstractevents.cidesigns;

import me.afatcookie.customitemevents.customitemevents.CustomItemEvents;
import me.afatcookie.customitemevents.customitemevents.abstractevents.PlaceBucketEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import java.util.logging.Level;

public class InfiniteWaterBucket extends PlaceBucketEvent {
    @Override
    public String getID() {
        return "infinitywaterbucket";
    }

    @Override
    public void execute(PlayerBucketEmptyEvent e) {
        CustomItemEvents.getInstance().getLogger().log(Level.INFO, "HORRAY");
    }
}
