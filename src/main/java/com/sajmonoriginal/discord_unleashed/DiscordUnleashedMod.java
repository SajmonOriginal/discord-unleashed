package com.sajmonoriginal.discord_unleashed;

import com.sajmonoriginal.discord_unleashed.server.DiscordChatRelay;
import com.sajmonoriginal.discord_unleashed.server.DiscordClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.fabricmc.api.ModInitializer;

public class DiscordUnleashedMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Discord Unleashed");

    @Override
    public void onInitialize() {

        new Thread(() -> {
            if (DiscordClient.init()) {
                DiscordChatRelay.sendServerStartMessage();
            }
        }).start();
    }

    public static void info(String s) {
        LOGGER.info(s);
    }
}
