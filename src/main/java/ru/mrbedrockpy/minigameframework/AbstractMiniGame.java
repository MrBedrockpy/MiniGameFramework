package ru.mrbedrockpy.minigameframework;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import org.bukkit.entity.Player;
import ru.mrbedrockpy.minigameframework.timeover.AbstractTimeOverRunnable;
import ru.mrbedrockpy.minigameframework.timeover.DefaultTimeOverRunnable;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMiniGame {

    protected long tick = 0;

    private final List<Player> players = new ArrayList<>();

    public abstract MultiverseWorld getWorld();

    public abstract int getMinPlayers();
    public abstract int getMaxPlayers();

    public AbstractTimeOverRunnable getTimeOverRunnable() {
        return new DefaultTimeOverRunnable(this);
    }

    public void tick() {}

    public final void addTick() {
        tick++;
    }

    public void onInitialize() {}

    public void onShutdown() {}

    public void onPlayerDeath(Player player) {}

    public void onPlayerKill(Player player, Player killer) {}

    public void onPlayerQuit(Player player) {}

    public long getTick() {
        return tick;
    }

    public List<Player> getPlayers() {
        return players;
    }
    public void addPlayer(Player player) {
        players.add(player);
    }
    public void removePlayer(Player player) {
        players.remove(player);
    }
}
