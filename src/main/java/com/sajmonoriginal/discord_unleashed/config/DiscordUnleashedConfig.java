package com.sajmonoriginal.discord_unleashed.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.sajmonoriginal.discord_unleashed.DiscordUnleashedMod;

public class DiscordUnleashedConfig {
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static boolean discord_enable = false;
    public static String discord_token = "SUPER-SECRET-TOKEN";
    public static String discord_channel = "CHANNEL-ID";
    public static String discord_serverpfp_url = "https://i.imgur.com/B1QXfMt.png";
    public static String discord_servername = "SERVER-NAME";
    public static String discord_webhook_url = "WEBHOOK-URL";

    public static void load() {
        File file = getFilePath();

        if (!file.exists()) {
            initFile(file);
        }

        try {
            FileReader reader = new FileReader(file);
            JsonObject obj = GSON.fromJson(reader, JsonObject.class);
            reader.close();

            updateValues(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        save();
    }

    public static void save() {
        File file = getFilePath();
        JsonObject obj = new JsonObject();
        updateValues(obj);

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(GSON.toJson(obj));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initFile(File file) {
        try {
            boolean ignore = file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write("{}");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T get(JsonObject object, String key, T defaultValue) {
        JsonElement element = object.get(key);
        if (element == null) {
            object.add(key, GSON.toJsonTree(defaultValue));
            return defaultValue;
        }
        return GSON.fromJson(element, (Class<T>)defaultValue.getClass());
    }

    public static <discord_webhook_url> void updateValues(JsonObject object) {
            discord_enable = get(object, "enable", discord_enable);
            discord_token = get(object, "token", discord_token);
            discord_channel = get(object, "channel", discord_channel);
            discord_serverpfp_url = get(object, "serverpfp_url", discord_serverpfp_url);
            discord_servername = get(object, "servername", discord_servername);
            discord_webhook_url = get(object, "discord_webhook_url", discord_webhook_url);
    }

    public static File getFilePath() {
        return FabricLoader.getInstance().getConfigDir().resolve("discord_unleashed.json").toFile();
    }

    public static void printConfigValues() {
        DiscordUnleashedMod.info("discord.enable = " + discord_enable);
    }

    static {
        load();
    }
}