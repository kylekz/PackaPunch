package me.freeplaynz.packapunch;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

public class PPPlayerListener extends PlayerListener {
    public static PackaPunch plugin;

    public PPPlayerListener(PackaPunch instance) {
        plugin = instance;
    }
    
    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getClickedBlock().getType().equals(Material.SIGN_POST) || event.getClickedBlock().getType().equals(Material.WALL_SIGN)) {
                Sign sign = (Sign)event.getClickedBlock().getState();
                if (sign.getLine(0).equalsIgnoreCase("[Pack-a-Punch]")) {
                    double amount = 0;
                    try {
                        amount = Double.parseDouble(sign.getLine(3));
                    } catch (NumberFormatException ex) {
                        player.sendMessage(ChatColor.RED + ex.toString());
                        return;
                    }
                    if (plugin.economy.has(player.getName(), amount)) {
                        if (sign.getLine(1).equalsIgnoreCase("random") && player.hasPermission("packapunch.random")) {
                            int newEnchant = EnchantmentUtils.getRandomEnchantment();
                            int newEnchantLevel = EnchantmentUtils.getRandomLevelFromId(newEnchant);

                            PackaPunch.packThePunch(player, newEnchant, newEnchantLevel, amount);
                        } else if (sign.getLine(1).equalsIgnoreCase("fixed") && player.hasPermission("packapunch.fixed")) {
                            String enchantShit = sign.getLine(2);
                            if (enchantShit.equals("") || enchantShit == null || !enchantShit.contains(":")) {
                                player.sendMessage(ChatColor.RED + "[Pack-a-Punch] Invalid ID or level!");
                            } else {
                                String[] enchantSplit = enchantShit.split(":");
                                int newEnchant = 0, newEnchantLevel = 1;
                                try {
                                    newEnchant = Integer.parseInt(enchantSplit[0]);
                                } catch (NumberFormatException ex) {
                                    player.sendMessage(ChatColor.RED + "[Pack-a-Punch] Invalid ID!");
                                    return;
                                }
                                try {
                                    newEnchantLevel = Integer.parseInt(enchantSplit[1]);
                                } catch (NumberFormatException ex) {
                                    player.sendMessage(ChatColor.RED + "[Pack-a-Punch] Invalid level!");
                                return;
                                }
                                PackaPunch.packThePunch(player, newEnchant, newEnchantLevel, amount);
                            }
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "[Pack-a-Punch] You don't have enough!");
                    }
                }
            }
        }
    }
}
