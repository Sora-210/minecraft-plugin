package dev.sora210;

import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin
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
