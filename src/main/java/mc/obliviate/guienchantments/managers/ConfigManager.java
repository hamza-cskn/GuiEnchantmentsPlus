package mc.obliviate.guienchantments.managers;

import mc.obliviate.guienchantments.GEnchantments;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager extends AbstractManager{

    private YamlConfiguration config;

    public ConfigManager(GEnchantments plugin) {
        super(plugin);

    }




    public void loadConfig() {
        config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder().getPath() + File.separator + "config.yml"));
    }


    public void resetConfiguration() {

    }

    public YamlConfiguration getConfig() {
        return config;
    }
}
