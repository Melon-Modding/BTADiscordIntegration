package de.olivermakesco.bta_discord_integration.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BTADiscordIntegrationConfig {
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static boolean discord_enable = false;
    public static String discord_token = "SUPER SECRET TOKEN";
    public static String discord_channel = "CHANNEL ID";
    public static String discord_serverpfp_url = "https://i.imgur.com/SXd58i2.png";
    public static String discord_servername = "Server";

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

    public static void updateValues(JsonObject object) {
            discord_enable = get(object, "enable", discord_enable);
            discord_token = get(object, "token", discord_token);
            discord_channel = get(object, "channel", discord_channel);
            discord_serverpfp_url = get(object, "serverpfp_url", discord_serverpfp_url);
            discord_servername = get(object, "servername", discord_servername);
    }

    public static File getFilePath() {
        return FabricLoader.getInstance().getConfigDir().resolve("bta_discord_integration.json").toFile();
    }

    public static void printConfigValues() {
        de.olivermakesco.bta_discord_integration.BTADiscordIntegrationMod.info("discord.enable = " + discord_enable);
    }

    static {
        load();
    }
}
