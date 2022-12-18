package ir.mctracker.mctrackervote.config;

import ir.jeykey.megacore.MegaPlugin;
import ir.jeykey.megacore.config.Configurable;
import ir.mctracker.mctrackervote.utilities.Utils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Messages extends Configurable {
    public static String PREFIX;
    public static List<String> VOTE_MESSAGES;
    public static String NO_PERMISSION;
    public static String CONSOLE_NOT_ALLOWED;
    public static String INVALID_ARG;

    public Messages(MegaPlugin instance) {
        super(instance, "messages.yml");
    }

    @Override
    public void init() {
        PREFIX = format(getConfig().getString("prefix"));
        VOTE_MESSAGES = format(getConfig().getStringList("vote-messages"));
        NO_PERMISSION = format(getConfig().getString("no-permission"));
        CONSOLE_NOT_ALLOWED = format(getConfig().getString("console-not-allowed"));
        INVALID_ARG = format(getConfig().getString("invalid-arg"));
    }

    private List<String> format(List<String> texts) {
        return texts.stream().map(this::format).collect(Collectors.toList());
    }

    private String format(String text) {
        return Utils.colorize(
                text.replace("{prefix}", PREFIX != null ? PREFIX : "")
        );
    }
}
