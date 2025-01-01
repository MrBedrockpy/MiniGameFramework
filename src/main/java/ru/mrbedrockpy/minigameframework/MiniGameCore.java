package ru.mrbedrockpy.minigameframework;

import com.onarandombox.MultiverseCore.MultiverseCore;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.mrbedrockpy.bedrocklib.config.ConfigManager;

public final class MiniGameCore extends JavaPlugin {

    private static MiniGameCore instance;

    private ConfigManager config;

    private MultiverseCore multiverseCore;

    private MiniGameManager miniGameManager;
    private LobbyManager lobbyManager;

    @Override
    public void onEnable() {
        instance = this;

        setupStorage();

        multiverseCore = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");

        miniGameManager = new MiniGameManager(this);
        lobbyManager = new LobbyManager(this);
    }

    private void setupStorage() {
        saveDefaultConfig();
        config = new ConfigManager(this, getConfig());
    }

    @Override
    public void onDisable() {
        this.miniGameManager.cancel();
        this.multiverseCore = null;
        instance = null;
    }

    public static MiniGameCore getInstance() {
        return instance;
    }

    public ConfigManager getCfg() {
        return config;
    }

    public MultiverseCore getMultiverseCore() {
        return multiverseCore;
    }

    public MiniGameManager getMiniGameManager() {
        return miniGameManager;
    }

    public LobbyManager getLobbyManager() {
        return lobbyManager;
    }
}
