package mc.obliviate.guienchantments.managers;

import mc.obliviate.guienchantments.GEnchantments;
import mc.obliviate.guienchantments.gui.EnchantmentGUI;

public class GuiManager extends AbstractManager{
    public GuiManager(GEnchantments plugin) {
        super(plugin);
    }

    public EnchantmentGUI getEnchantmentGui() {

        return new EnchantmentGUI(plugin);
    }

}
