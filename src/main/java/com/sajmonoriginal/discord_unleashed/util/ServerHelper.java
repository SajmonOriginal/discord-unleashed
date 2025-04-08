package com.sajmonoriginal.discord_unleashed.util;

import com.sajmonoriginal.discord_unleashed.DiscordUnleashedMod;
import net.minecraft.server.MinecraftServer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;


public class ServerHelper {
    private static MinecraftServer cachedServer = null;


    public static void setServer(MinecraftServer server) {
        cachedServer = server;
        DiscordUnleashedMod.LOGGER.info("Server instance has been set.");
    }


    public static MinecraftServer getServer() {
        return cachedServer;
    }


    public static void broadcastMessage(String message) {
        MinecraftServer server = getServer();
        if (server == null) {
            DiscordUnleashedMod.LOGGER.info("Cannot broadcast message - server not found: " + message);
            return;
        }

        try {
            Object playerManager = server.getClass().getField("field_2842").get(server); // playerManager

            for (Field f : playerManager.getClass().getDeclaredFields()) {
                if (f.getType().getName().contains("List")) {
                    f.setAccessible(true);
                    List<?> players = (List<?>) f.get(playerManager);

                    for (Object player : players) {
                        for (Method m : player.getClass().getMethods()) {
                            if (m.getParameterCount() == 1 && m.getParameterTypes()[0] == String.class) {
                                // Send message to player
                                m.invoke(player, message);
                                break;
                            }
                        }
                    }
                    break;
                }
            }

        } catch (Exception e) {
            DiscordUnleashedMod.LOGGER.info("Error broadcasting message: " + e.toString());
        }
    }
}
