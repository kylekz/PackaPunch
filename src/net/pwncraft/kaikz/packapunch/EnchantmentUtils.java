package net.pwncraft.kaikz.packapunch;

import java.util.Random;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Kaikz
 */

public class EnchantmentUtils {
    private static int[] enchantmentIds = {0,1,2,3,4,5,6,16,17,18,19,20,21,32,33,34,35};
    private static int min = 1;
    private static int max = 5;
    private static final String[] RCODE = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    private static final int[] BVAL  = {1000, 900, 500, 400,  100,   90,  50, 40,   10,    9,   5,   4,    1};
    
    public static int getRandomEnchantment() {
        Random rand = new Random();
        int rnd = rand.nextInt(enchantmentIds.length);
        return enchantmentIds[rnd];
    }
    
    public static int getRandomLevelFromId(int id) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }
    
    public static String getStringName(int id) {
        String name = "";
        switch (id) {
            case 0:
                name = "Protection";
                break;
            case 1:
                name = "Fire Protection";
                break;
            case 2:
                name = "Feather Falling";
                break;
            case 3:
                name = "Blast Protection";
                break;
            case 4:
                name = "Projectile Protection";
                break;
            case 5:
                name = "Respiration";
                break;
            case 6:
                name = "Aqua Affinity";
                break;
            case 16:
                name = "Sharpness";
                break;
            case 17:
                name = "Smite";
                break;
            case 18:
                name = "Bane of Arthropods";
                break;
            case 19:
                name = "Knockback";
                break;
            case 20:
                name = "Fire Aspect";
                break;
            case 21:
                name = "Looting";
                break;
            case 32:
                name = "Efficiency";
                break;
            case 33:
                name = "Silk Touch";
                break;
            case 34:
                name = "Unbreaking";
                break;
            case 35:
                name = "Fortune";
                break;
        }
        return name;
    }
    
    public static int[] getEnchantmentIds() {
        return enchantmentIds;
    }
    
    public static result enchantItem(ItemStack item, int id, int level) {
        Enchantment enchantment = Enchantment.getById(id);
        if (enchantment == null) {
            return result.BAD_ENCHANTMENT;
        }
        if (level > enchantment.getMaxLevel()) level = enchantment.getMaxLevel();
        try {
            if (!item.containsEnchantment(enchantment)) {
                item.addUnsafeEnchantment(enchantment, level);
                return result.ENCHANT_SUCCESS;
            } else {
                return result.SAME_ENCHANTMENT;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String levelToRoman(int level) {
        if (level <= 0 || level >= 4000) {
            throw new NumberFormatException("Value outside roman numeral range.");
        }
        String roman = "";
        for (int i = 0; i < RCODE.length; i++) {
            while (level >= BVAL[i]) {
                level -= BVAL[i];
                roman  += RCODE[i];
            }
        }
        return roman;
    }
    
     public static enum result {
        SAME_ENCHANTMENT, BAD_ENCHANTMENT, ENCHANT_SUCCESS;
    }
}
