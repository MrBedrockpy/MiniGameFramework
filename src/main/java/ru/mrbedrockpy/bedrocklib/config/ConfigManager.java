package ru.mrbedrockpy.bedrocklib.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import ru.mrbedrockpy.bedrocklib.ChatUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class ConfigManager {

    private final Plugin plugin;
    private final FileConfiguration config;
    private final String name;

    public ConfigManager(Plugin plugin, FileConfiguration config) {
        this.plugin = plugin;
        this.config = config;
        this.name = "config.yml";
    }

    public ConfigManager(Plugin plugin, String config) {

        File customConfigFile = new File(plugin.getDataFolder(), config);

        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            plugin.saveResource(config, false);
        }

        FileConfiguration customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().log(Level.WARNING, e.toString());
        }

        this.plugin = plugin;
        this.config = YamlConfiguration.loadConfiguration(customConfigFile);
        this.name = config;
    }

    public String getString(String path) {
        return ChatUtil.format(config.getString(path));
    }

    public String getString(String path, String def) {
        return ChatUtil.format(config.getString(path, def));
    }

    public List<String> getStringList(String path) {
        return ChatUtil.format(config.getStringList(path));
    }

    public Integer getInt(String path) {
        return config.getInt(path);
    }

    public Double getDouble(String path) {
        return config.getDouble(path);
    }

    public Boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return config.getConfigurationSection(path);
    }

    public void set(String path, Object value) {
        Bukkit.getLogger().info("Setting " + path + " = " + value);
        config.set(path, value);

    }

    public Map<String, Object> getValues() {
        return config.getValues(false);
    }

    public void save() {
        try {
            config.save(plugin.getDataFolder() + "/" + name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
