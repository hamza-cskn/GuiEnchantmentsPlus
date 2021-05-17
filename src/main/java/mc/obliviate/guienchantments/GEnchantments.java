package mc.obliviate.guienchantments;

import mc.obliviate.guienchantments.commands.Commands;
import mc.obliviate.guienchantments.managers.ConfigManager;
import mc.obliviate.guienchantments.managers.EnchantmentManager;
import mc.obliviate.guienchantments.managers.GuiManager;
import mc.obliviate.guienchantments.managers.TextAnimationManager;
import mc.obliviate.guienchantments.utils.api.InventoryAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class GEnchantments extends JavaPlugin {

    private ConfigManager configManager;
    private EnchantmentManager enchantmentManager;
    private GuiManager guiManager;
    private TextAnimationManager textAnimationManager;
    private InventoryAPI inventoryAPI;


    @Override
    public void onEnable() {
        Bukkit.getLogger().info("GuiEnchantments+ " + this.getDescription().getVersion() + " enabling...");


        setupManagers();
        setupCommands();


    }

    private void setupCommands() {
        PluginCommand command = Objects.requireNonNull(getCommand("genchs"));
        Commands commands = new Commands(this);
        command.setExecutor(commands);
    }


    private void setupManagers() {
        if (configManager != null) {
            Bukkit.getLogger().severe("configManager is not null!");
            return;
        }
        configManager = new ConfigManager(this);
        enchantmentManager = new EnchantmentManager(this);
        guiManager = new GuiManager(this);
        textAnimationManager = new TextAnimationManager(this);
        inventoryAPI = new InventoryAPI(this);
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public EnchantmentManager getEnchantmentManager() {
        return enchantmentManager;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public TextAnimationManager getTextAnimationManager() {
        return textAnimationManager;
    }

    public InventoryAPI getInventoryAPI() {
        return inventoryAPI;
    }

}
