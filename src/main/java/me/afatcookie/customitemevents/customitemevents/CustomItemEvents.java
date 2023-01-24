package me.afatcookie.customitemevents.customitemevents;

import com.mittenmc.customitems.api.CustomItemsAPI;
import me.afatcookie.customitemevents.customitemevents.CIListener.EventListeners;
import me.afatcookie.customitemevents.customitemevents.cooldowns.CooldownManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomItemEvents extends JavaPlugin {


    private static CustomItemEvents instance;

    private CooldownManager cooldownManager;

    private CustomItemsAPI customItemsAPI;

    public static CustomItemEvents getInstance(){
        return instance;
    }
    @Override
    public void onEnable() {
        instance = this;
        customItemsAPI = CustomItemsAPI.getInstance(); // must register this before registering events
        cooldownManager = new CooldownManager();
        getServer().getPluginManager().registerEvents(new EventListeners(instance), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public CustomItemsAPI getCiAPI(){
        return customItemsAPI;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
}
