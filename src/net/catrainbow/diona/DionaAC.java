package net.catrainbow.diona;

import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import dev.diona.pluginhooker.PluginHooker;
import dev.diona.pluginhooker.listeners.PlayerListener;
import net.catrainbow.diona.command.AntiCheatCommand;
import net.catrainbow.diona.task.UpdateMetaTask;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 一个管理反作弊的插件
 * 依赖: PluginHooker
 *
 * @author Catrainbow
 */
public class DionaAC extends PluginBase {

    private static DionaAC instance;

    public static DionaAC getInstance() {
        return instance;
    }

    private final Config config = new Config(this.getDataFolder() + "/config.yml", 2);
    public HashMap<String, Plugin> hookedPlugin = new HashMap<>();

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        this.getLogger().info("§b[DionaAC]§a>> Welcome to use DionaAC by Catrainbow.");
        this.initConfig();
        this.getServer().getCommandMap().register("DionaAC", new AntiCheatCommand("ac", "DionaAC Command"));
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        this.getLogger().info("§b[DionaAC]§a>> Totally hooked " + hookedPlugin.size() + " AntiCheat");
        this.getServer().getScheduler().scheduleRepeatingTask(new UpdateMetaTask(), 20);
    }

    public Config getConfig() {
        return this.config;
    }

    private void initConfig() {
        if (!this.getConfig().exists("hooked-anti-cheats")) {
            this.getConfig().set("hooked-anti-cheats", new ArrayList<>());
            this.getConfig().set("server-name", "§eCatrainbow's Test Server");
            this.getConfig().set("scoreboards", new ArrayList<String>() {
                {
                    add(TextFormat.BLUE.name());
                    add("AC: @ac");
                    add("Flag: @flag");
                    add("Kick: @kick");
                    add("Damage: @damage");
                    add("Hunger: @hunger");
                    add(TextFormat.YELLOW.name());
                    add("§emc.catrainbow.me");
                }
            });
            this.getConfig().set("kick", "§cYou are kicked!");
            this.getConfig().save(true);
        }
        this.getConfig().getStringList("hooked-anti-cheats").forEach(it -> {
            if (this.getServer().getPluginManager().getPlugin(it) != null) {
                Plugin plugin = this.getServer().getPluginManager().getPlugin(it);
                if (!this.hookedPlugin.containsKey(plugin.getName())) {
                    this.hookedPlugin.put(plugin.getName(), plugin);
                    PluginHooker.getPluginManager().addPlugin(plugin);
                    this.getLogger().info("§b[DionaAC]§a>> Hooked AntiCheat " + it + " successfully!");
                } else
                    this.getLogger().warning("§b[DionaAC]§a>> Failed to hook AntiCheat " + it + " because it has been hooked!");
            } else
                this.getLogger().warning("§b[DionaAC]§a>> Failed to hook AntiCheat " + it + " because it doesn't exist.");
        });
    }
}
