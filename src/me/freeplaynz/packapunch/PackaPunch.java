package me.freeplaynz.packapunch;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class PackaPunch extends JavaPlugin {
    private static Economy economy = null;

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PPListener(), this);
        if (!setupEconomy()) { System.out.println("Warning no economy plugin found!"); }
        System.out.println("[PackaPunch] Enabled!");
    }
    
    private Boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        return (economy != null);
    }
    
    public static Economy getEconomy() {
        return economy;
    }
    
    public static void packThePunch(Player player, int id, int level, double amount) {   
        Enchantment enchantment = Enchantment.getById(id);
        
        EnchantmentUtils.result result = EnchantmentUtils.enchantItem(player.getItemInHand(), id, level);
        
        if (result == EnchantmentUtils.result.ENCHANT_SUCCESS) {
            economy.withdrawPlayer(player.getName(), amount);
            player.sendMessage("[PackaPunch] " + EnchantmentUtils.getStringName(id) + " " + EnchantmentUtils.levelToRoman(level) + "!");
        } else if (result == EnchantmentUtils.result.SAME_ENCHANTMENT) {
            player.sendMessage("[PackaPunch] Your item already has " + EnchantmentUtils.getStringName(id) + "!");
        } else {
            player.sendMessage("[PackaPunch] Enchant failed! ):");
        }
    }
}
