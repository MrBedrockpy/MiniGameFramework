package ru.mrbedrockpy.minigameframework;

import com.onarandombox.MultiverseCore.MultiverseCore;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MiniGame extends JavaPlugin {

    private static MiniGame instance;

    private MultiverseCore multiverseCore = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
        this.multiverseCore = null;
        instance = null;
    }

    public static MiniGame getInstance() {
        return instance;
    }

    public MultiverseCore getMultiverseCore() {
        return multiverseCore;
    }
}
