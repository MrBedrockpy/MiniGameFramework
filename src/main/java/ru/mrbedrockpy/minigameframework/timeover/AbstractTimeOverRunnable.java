package ru.mrbedrockpy.minigameframework.timeover;

import org.bukkit.scheduler.BukkitRunnable;
import ru.mrbedrockpy.minigameframework.AbstractMiniGame;
import ru.mrbedrockpy.minigameframework.MiniGameCore;

public abstract class AbstractTimeOverRunnable extends BukkitRunnable {

    protected final AbstractMiniGame miniGame;

    protected int timeOver = getStartTime();

    public AbstractTimeOverRunnable(AbstractMiniGame miniGame) {
        this.miniGame = miniGame;
    }

    @Override
    public final void run() {
        if (timeOver <= 0) {
            cancel();
            MiniGameCore.getInstance().getMiniGameManager().addMiniGame(miniGame);
        }
        timeOver--;
        tick();
    }

    public void start() {
        this.runTaskTimer(MiniGameCore.getInstance(), 0L, 20L);
    }

    public abstract void tick();

    public abstract int getStartTime();

    public AbstractMiniGame getMiniGame() {
        return miniGame;
    }
}
