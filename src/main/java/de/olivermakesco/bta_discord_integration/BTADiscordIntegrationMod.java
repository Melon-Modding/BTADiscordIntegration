package de.olivermakesco.bta_discord_integration;

import de.olivermakesco.bta_discord_integration.server.DiscordChatRelay;
import de.olivermakesco.bta_discord_integration.server.DiscordClient;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BTADiscordIntegrationMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("BTA Discord Integration");

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
