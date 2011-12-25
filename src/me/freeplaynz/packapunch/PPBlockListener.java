package me.freeplaynz.packapunch;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PPBlockListener extends BlockListener {
    public static PackaPunch plugin;

    public PPBlockListener(PackaPunch instance) {
        plugin = instance;
    }
    
    @Override
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        
        if (event.getBlock().getType().equals(Material.SIGN_POST) || event.getBlock().getType().equals(Material.WALL_SIGN)) {
            Sign sign = (Sign)event.getBlock().getState();
            String[] signLines = sign.getLines();
            if (signLines[0].equalsIgnoreCase("[Pack-a-Punch]")) {
                if (signLines[1].equalsIgnoreCase("random") && !player.hasPermission("packapunch.place.random")) {
                    player.sendMessage(ChatColor.RED + "[Pack-a-Punch] You don't have permission to do that!");
                    event.setCancelled(true);
                }
                if (signLines[1].equalsIgnoreCase("fixed") && !player.hasPermission("packapunch.place.fixed")) {
                    player.sendMessage(ChatColor.RED + "[Pack-a-Punch] You don't have permission to do that!");
                    event.setCancelled(true);
                }
            }
        }
    }
}
