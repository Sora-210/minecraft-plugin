package dev.sora210.minecraft_plugin;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class MyPlugin extends JavaPlugin implements Listener
{
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        registerMagicWandRecipes();
        getLogger().info("自作プラグインが有効化されたよ！");

    }

    @Override
    public void onDisable() {
        getLogger().info("自作プラグインが無効化されたよ！");
    }

    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        event.getPlayer().sendMessage("ようこそ、" + event.getPlayer().getName() + "さん！");
        ItemStack magicWand = createMagicWand();
        event.getPlayer().getInventory().addItem(magicWand);
    }

    public ItemStack createMagicWand() {
        ItemStack magicWand = new ItemStack(Material.STICK);
        ItemMeta meta = magicWand.getItemMeta();

        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "魔術師の杖" + ChatColor.RESET);
        meta.setLore(Arrays.asList(
            ChatColor.GRAY + "初級者のための魔法の杖",
            ChatColor.YELLOW + "右クリックで魔法を詠唱",
            "",
            ChatColor.BLUE + "★ 使用可能な魔法 ★",
            ChatColor.YELLOW + "・ライトニング (マナ: 30)"
        ));
        
        // より強い光る効果
        meta.addEnchant(Enchantment.DURABILITY, 2, true);
        meta.addEnchant(Enchantment.MENDING, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        
        magicWand.setItemMeta(meta);

        return magicWand;
    }


    private void registerMagicWandRecipes() {
        // 見習い魔導師の杖のレシピ
        ItemStack magicWand = createMagicWand();
        NamespacedKey wandKey = new NamespacedKey(this, "magic_wand");
        ShapedRecipe wandRecipe = new ShapedRecipe(wandKey, magicWand);
        
        // レシピパターンを設定
        wandRecipe.shape(
            "DDD",
            "DSD",
            "DDD"
        );
        
        // 材料を設定
        wandRecipe.setIngredient('D', Material.DIAMOND);
        wandRecipe.setIngredient('S', Material.STICK);
        
        // レシピを登録
        Bukkit.addRecipe(wandRecipe);
    }
}
