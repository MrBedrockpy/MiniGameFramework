package ru.mrbedrockpy.minigameframework;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public final class MiniGameManager extends BukkitRunnable implements Listener {

    private final MiniGameCore core;

    private final List<AbstractMiniGame> sessions = new ArrayList<>();

    public MiniGameManager(MiniGameCore core) {
        this.core = core;
        core.getServer().getPluginManager().registerEvents(this, core);
        this.runTaskTimer(core, 0L, 1L);
    }

    public void addMiniGame(AbstractMiniGame game) {
        game.onInitialize();
        sessions.add(game);
    }

    public void shutdownMiniGame(AbstractMiniGame game, List<Player> winners) {
        game.onShutdown();
        sessions.remove(game);
    }

    @Override
    public void run() {
        sessions.forEach(miniGame -> {
            miniGame.tick();
            miniGame.addTick();
        });
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        sessions.forEach(game -> {
            if (game.getPlayers().contains(event.getEntity())) game.onPlayerDeath(event.getEntity());
        });
        if (event.getEntity().getKiller() == null) return;
        sessions.forEach(game -> {
            if (game.getPlayers().contains(event.getEntity())) game.onPlayerKill(event.getEntity(), event.getEntity().getKiller());
        });
    }

    @EventHandler
    public void onPLayerQuit(PlayerQuitEvent event) {
        sessions.forEach(game -> {
            if (game.getPlayers().contains(event.getPlayer())) game.onPlayerQuit(event.getPlayer());
        });
    }

    public List<AbstractMiniGame> getSessions() {
        return sessions;
    }
}
