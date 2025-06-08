package dev.sora210.minecraft_plugin;

import org.bukkit.plugin.java.JavaPlugin;

public class MyPlugin extends JavaPlugin
{
    @Override
    public void onEnable() {
        getLogger().info("自作プラグインが有効化されたよ！");
    }

    @Override
    public void onDisable() {
        getLogger().info("自作プラグインが無効化されたよ！");
    }
}
