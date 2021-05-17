package mc.obliviate.guienchantments.gui;

import com.sun.istack.internal.Nullable;
import mc.obliviate.guienchantments.GEnchantments;
import mc.obliviate.guienchantments.utils.api.GUI;
import mc.obliviate.guienchantments.utils.api.Hytem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.Arrays;
import java.util.Map;

public class EnchantmentGUI extends GUI {

    private Player player;

    public EnchantmentGUI(GEnchantments plugin) {
        //TODO Sync to messages.yml
        super(plugin.getInventoryAPI(), "enchantment-gui", "Enchantment GUI", 54);
    }


    @Override
    public void onOpen(InventoryOpenEvent event) {
        this.player = (Player) event.getPlayer();
        putDisplaySlotIcon();
    }

    public void onClose(InventoryCloseEvent event) {
        ItemStack item = getInventory().getItem(13);
        if (!item.getType().equals(Material.BARRIER)) {
            event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), item);
        }
    }


    public void onClick(InventoryClickEvent e) {
        if (e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
            //if this event triggered on the gui, never mind it.
            if (e.getRawSlot() == e.getSlot()) return;
            ItemStack slot = getInventory().getItem(13);
            if (slot == null || slot.getType().equals(Material.BARRIER)) {
                putDisplayIcon(e.getCurrentItem());
                e.setCurrentItem(new ItemStack(Material.AIR));
            }
        }

    }

    private void putDisplaySlotIcon() {
        addAnimatedItem(player, 13, getDisplaySlotIcon(), "Put Any Item", new Color(255, 104, 104), new Color(255, 43, 43));
        fillGui(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), Arrays.asList(13));
    }

    private void putDisplayIcon(ItemStack item) {

        addItem(13, new Hytem(item, e2 -> {
            e2.setCancelled(false);

            Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
                ItemStack itemOnSlot = getInventory().getItem(13);
                if (itemOnSlot == null || itemOnSlot.getType().equals(Material.AIR)) {
                    putDisplaySlotIcon();
                } else {

                    updateEnchantmentIcons();
                }
            }, 1);
        }));
        updateEnchantmentIcons();
    }

    private Hytem getDisplaySlotIcon() {
        return new Hytem(new ItemStack(Material.BARRIER), e -> {
            if (e.getCursor() != null && !e.getCursor().getType().equals(Material.AIR)) {
                putDisplayIcon(e.getCursor());
                e.setCursor(new ItemStack(Material.AIR));
            }

        }).setLore("", "§7Click to put item here");

    }


    private void updateEnchantmentIcons() {
        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
            fillGui(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), Arrays.asList(13));
        }, 1);
        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
            ItemStack item = getInventory().getItem(13);
            boolean isBook = item.getType().equals(Material.ENCHANTED_BOOK);
            int slot = 27;
            for (Enchantment ench : Enchantment.values()) {
                if (isBook || ench.canEnchantItem(item)) {
                    boolean isAvailableEnch = true;

                    Map<Enchantment, Integer> enchantmentList;
                    if (isBook) {
                        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
                        enchantmentList = meta.getStoredEnchants();
                    } else {
                        enchantmentList = item.getEnchantments();
                    }
                    for (Enchantment enchOfItem : enchantmentList.keySet()) {
                        if (!enchOfItem.equals(ench) && ench.conflictsWith(enchOfItem)) {
                            isAvailableEnch = false;
                            break;
                        }
                    }

                    if (isAvailableEnch) {
                        addItem(slot, getEnchantmentIcon(ench));
                        slot++;
                        if (slot == 37) {
                            break;
                        }
                    }
                }
            }
        }, 2);
    }

    private Hytem getEnchantmentIcon(Enchantment ench) {

        return new Hytem(new ItemStack(Material.ENCHANTED_BOOK), e -> {

            ItemStack item = getInventory().getItem(13);
            if (item == null) return;
            if (item.getType().equals(Material.ENCHANTED_BOOK)) {
                EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
                meta.addStoredEnchant(ench, meta.getStoredEnchantLevel(ench) + 1, true);
                item.setItemMeta(meta);
            } else {
                ItemMeta meta = item.getItemMeta();
                meta.addEnchant(ench, item.getEnchantmentLevel(ench) + 1, true);
                item.setItemMeta(meta);
            }
            getInventory().setItem(13, item);
            updateEnchantmentIcons();
        }).setLore("§7Click to enchant item").setName("§d§l" + ench.getName());

    }


}
