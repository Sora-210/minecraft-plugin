package dev.sora210.minecraft_plugin;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.block.Action;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

public class MyPlugin extends JavaPlugin implements Listener
{
    private final NamespacedKey CUSTOM_ITEM = new NamespacedKey(this, "custom_item");
    private final NamespacedKey ITEM_STOCK = new NamespacedKey(this, "item_stock");

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
    }

    public ItemStack createMagicWand() {
        ItemStack magicWand = new ItemStack(Material.STICK);
        ItemMeta meta = magicWand.getItemMeta();

        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "すごい銃 (30/30)" + ChatColor.RESET);
        meta.setLore(Arrays.asList(
            ChatColor.BLUE + "★ 操作方法 ★",
            ChatColor.YELLOW + "【右クリック】リロード",
            ChatColor.YELLOW + "【左クリック】発射"
        ));
        
        // より強い光る効果
        meta.addEnchant(Enchantment.DURABILITY, 2, true);
        meta.addEnchant(Enchantment.MENDING, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.getPersistentDataContainer().set(CUSTOM_ITEM, PersistentDataType.INTEGER, 200);
        meta.getPersistentDataContainer().set(ITEM_STOCK, PersistentDataType.INTEGER, 30);

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

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null || meta.getPersistentDataContainer().get(CUSTOM_ITEM, PersistentDataType.INTEGER) != 200) return;

        // 右クリックと左クリックを区別
        boolean isRightClick = (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK);
        boolean isLeftClick = (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK);

        if (isRightClick) {
            player.sendMessage(ChatColor.GREEN + "チャージしました！");
            // ここに魔法の効果を追加することができます
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + "すごい銃 (30/30)" + ChatColor.RESET);
            meta.getPersistentDataContainer().set(ITEM_STOCK, PersistentDataType.INTEGER, 30);
            item.setItemMeta(meta);
        } else if (isLeftClick) {
            player.sendMessage(ChatColor.RED + "発射しました！");
            int stock = meta.getPersistentDataContainer().get(ITEM_STOCK, PersistentDataType.INTEGER);
            if (stock <= 0) {
                player.sendMessage(ChatColor.RED + "リロードが必要です！");
                return;
            }
            stock -= 1;
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + "すごい銃 (" + stock + "/30)" + ChatColor.RESET);
            meta.getPersistentDataContainer().set(ITEM_STOCK, PersistentDataType.INTEGER, stock);
            item.setItemMeta(meta);
            shootSmallFireball(player);
            
            // ここに別の効果を追加することができます
        }
    }

    private void shootSmallFireball(Player player) {
        SmallFireball smallFireball = player.launchProjectile(SmallFireball.class);
        
        Vector direction = player.getLocation().getDirection();
        smallFireball.setVelocity(direction.multiply(2.5));
        
        // 小さいので爆発力は控えめ
        // SmallFireballは自動的に小さな爆発を起こす
        
        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 0.8f, 1.5f);
        player.getWorld().spawnParticle(Particle.FLAME, player.getLocation().add(0, 1, 0), 5, 0.2, 0.2, 0.2, 0.05);
    }
}
