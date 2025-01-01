package ru.mrbedrockpy.minigameframework.timeover;

import ru.mrbedrockpy.bedrocklib.ChatUtil;
import ru.mrbedrockpy.minigameframework.AbstractMiniGame;
import ru.mrbedrockpy.minigameframework.MiniGameCore;

public class DefaultTimeOverRunnable extends AbstractTimeOverRunnable {

    public DefaultTimeOverRunnable(MiniGameCore core, AbstractMiniGame miniGame) {
        super(core, miniGame);
    }

    @Override
    public void tick() {
        miniGame.getPlayers()
                .forEach(player -> player.sendMessage(
                        ChatUtil.format("&eНачала игры через " + timeOver + "...")
                ));
    }

    @Override
    public int getStartTime() {
        return 60;
    }

}
