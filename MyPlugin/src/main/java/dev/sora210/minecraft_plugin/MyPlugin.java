package dev.sora210.minecraft_plugin;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class MyPlugin extends JavaPlugin implements Listener
{
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        getLogger().info("自作プラグインが有効化されたよ！");

    }

    @Override
    public void onDisable() {
        getLogger().info("自作プラグインが無効化されたよ！");
    }

    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        event.getPlayer().sendMessage("ようこそ、" + event.getPlayer().getName() + "さん！");
        ItemStack diamond_sword = new ItemStack(Material.DIAMOND_SWORD);
        event.getPlayer().getInventory().addItem(diamond_sword);
    }
}
