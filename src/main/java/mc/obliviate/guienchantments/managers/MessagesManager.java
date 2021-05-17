package mc.obliviate.guienchantments.managers;

import mc.obliviate.guienchantments.GEnchantments;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MessagesManager extends AbstractManager{

    private YamlConfiguration messages;

    public MessagesManager(GEnchantments plugin) {
        super(plugin);
    }

    public String getMessage(String key) {
        return color(messages.getString(key));
    }

    private String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public void loadMessages() {
        messages = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder().getPath() + File.separator + "messages.yml"));
    }

    public void resetMessagesConfiguration() {

    }

    public YamlConfiguration getMessagesConfiguration() {
        return messages;
    }

}
