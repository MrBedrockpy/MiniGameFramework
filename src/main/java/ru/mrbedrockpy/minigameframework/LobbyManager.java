package ru.mrbedrockpy.minigameframework;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.mrbedrockpy.minigameframework.timeover.AbstractTimeOverRunnable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public final class LobbyManager implements Listener {

    private final MiniGameCore core;

    private final List<AbstractMiniGame> queue = new ArrayList<>();

    private final TimeOverManager timeOverManager = new TimeOverManager();

    public LobbyManager(MiniGameCore core) {
        this.core = core;
        core.getServer().getPluginManager().registerEvents(this, core);
    }

    public boolean addPlayerInQueue(Class<? extends AbstractMiniGame> miniGameClass, Player player) {
        AbstractMiniGame miniGame = getMiniGame(miniGameClass);
        if (miniGame == null) {
            miniGame = createMiniGame(miniGameClass);
            if (miniGame == null) return false;
            queue.add(miniGame);
        }
        if (miniGame.getMaxPlayers() >= miniGame.getPlayers().size()) {
            timeOverManager.stopTimeOverAfterGame(miniGame);
            core.getMiniGameManager().addMiniGame(miniGame);
            return false;
        }
        miniGame.addPlayer(player);
        if (miniGame.getMinPlayers() <= miniGame.getPlayers().size())
            timeOverManager.startTimeOverAfterGame(miniGame);
        return true;
    }

    private AbstractMiniGame createMiniGame(Class<? extends AbstractMiniGame> miniGame) {
        try {
            Constructor<? extends AbstractMiniGame> constructor = miniGame.getDeclaredConstructor(String.class);
            return constructor.newInstance(core);
        } catch (NoSuchMethodException e) {
            core.getLogger().warning("MiniGame class " + miniGame.getName() + " does not have a constructor");
        } catch (InvocationTargetException e) {
            core.getLogger().warning("MiniGame class " + miniGame.getName() + " threw an exception while executing constructor");
        } catch (InstantiationException e) {
            core.getLogger().warning("MiniGame class " + miniGame.getName() + " threw an exception while instantiating constructor");
        } catch (IllegalAccessException e) {
            core.getLogger().warning("MiniGame class " + miniGame.getName() + " threw an exception while accessing constructor");
        }
        return null;
    }

    private AbstractMiniGame getMiniGame(Class<? extends AbstractMiniGame> miniGameClass) {
        for (AbstractMiniGame miniGame: queue) {
            if (miniGame.getClass().equals(miniGameClass)) return miniGame;
        }
        return null;
    }

    @EventHandler
    public void onPlayerQuitInQueue(PlayerQuitEvent event) {
        for (AbstractMiniGame miniGame: queue) {
            if (miniGame.getPlayers().contains(event.getPlayer())) {
                miniGame.removePlayer(event.getPlayer());
                if (miniGame.getMinPlayers() > miniGame.getPlayers().size())
                    timeOverManager.stopTimeOverAfterGame(miniGame);
                return;
            }
        }
    }

    private static class TimeOverManager {

        private List<AbstractTimeOverRunnable> timeOverRunnables = new ArrayList<>();

        public void startTimeOverAfterGame(AbstractMiniGame miniGame) {
            miniGame.getTimeOverRunnable().start();
            timeOverRunnables.add(miniGame.getTimeOverRunnable());
        }

        public void stopTimeOverAfterGame(AbstractMiniGame miniGame) {
            for (AbstractTimeOverRunnable runnable: timeOverRunnables) {
                if (!runnable.getMiniGame().equals(miniGame)) continue;
                runnable.cancel();
                timeOverRunnables.remove(runnable);
            }
        }

    }
}
