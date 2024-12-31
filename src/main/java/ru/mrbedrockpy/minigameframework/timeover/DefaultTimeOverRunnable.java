package ru.mrbedrockpy.minigameframework.timeover;

import ru.mrbedrockpy.bedrocklib.ChatUtil;
import ru.mrbedrockpy.minigameframework.AbstractMiniGame;

public class DefaultTimeOverRunnable extends AbstractTimeOverRunnable {

    public DefaultTimeOverRunnable(AbstractMiniGame miniGame) {
        super(miniGame);
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
