package mc.obliviate.guienchantments.commands;

import com.sun.istack.internal.NotNull;
import mc.obliviate.guienchantments.GEnchantments;
import mc.obliviate.guienchantments.managers.AbstractManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands extends AbstractManager implements CommandExecutor {


    public Commands(GEnchantments plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            plugin.getGuiManager().getEnchantmentGui().open(player);

        }else {
            sender.sendMessage("Only players can execute that command.");
        }

        return false;
    }
}
