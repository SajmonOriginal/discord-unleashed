package com.sajmonoriginal.discord_unleashed.util;

import java.util.regex.Pattern;

public class ChatFormatting {

    public static final String BLACK = "§0";
    public static final String DARK_BLUE = "§1";
    public static final String DARK_GREEN = "§2";
    public static final String DARK_AQUA = "§3";
    public static final String DARK_RED = "§4";
    public static final String DARK_PURPLE = "§5";
    public static final String GOLD = "§6";
    public static final String GRAY = "§7";
    public static final String DARK_GRAY = "§8";
    public static final String BLUE = "§9";
    public static final String GREEN = "§a";
    public static final String AQUA = "§b";
    public static final String RED = "§c";
    public static final String LIGHT_PURPLE = "§d";
    public static final String YELLOW = "§e";
    public static final String WHITE = "§f";


    public static final String OBFUSCATED = "§k";
    public static final String BOLD = "§l";
    public static final String STRIKETHROUGH = "§m";
    public static final String UNDERLINE = "§n";
    public static final String ITALIC = "§o";
    public static final String RESET = "§r";

    public static String process(String text) {
        return text;
    }

    public static String stripDiscordFormatting(String message) {
        return message
                .replaceAll("\\*\\*(.*?)\\*\\*", "$1")    // bold
                .replaceAll("\\*(.*?)\\*", "$1")          // italic
                .replaceAll("__(.*?)__", "$1")            // underline
                .replaceAll("~~(.*?)~~", "$1")            // strikethrough
                .replaceAll("`{1,3}(.*?)`{1,3}", "$1");   // inline code or block code
    }
}