package net.catrainbow.diona;

import cn.nukkit.Player;
import cn.nukkit.plugin.Plugin;
import dev.diona.pluginhooker.PluginHooker;
import dev.diona.pluginhooker.player.DionaPlayer;

import java.util.ArrayList;

public class DionaAPI {

    public static void reactionAntiCheat(Player player, String name) {
        if (isHookedAntiCheat(name)) {
            DionaPlayer dionaPlayer = PluginHooker.getPlayerManager().getDionaPlayer(player);
            if (dionaPlayer == null) return;
            if (dionaPlayer.isPluginEnabled(getAntiCheat(name))) {
                disablePlayerAntiCheat(player, name);
                player.teleport(DionaAC.getInstance().getServer().getDefaultLevel().getSpawnLocation().add(0.0, 1.0, 0.0));
                player.sendMessage("§b[DionaAC]§a>> Disable AntiCheat " + name + " successfully!");
            } else {
                enablePlayerAntiCheat(player, name);
                player.teleport(DionaAC.getInstance().getServer().getDefaultLevel().getSpawnLocation().add(0.0, 1.0, 0.0));
                player.sendMessage("§b[DionaAC]§a>> Enable AntiCheat " + name + " successfully!");
            }
        } else player.sendMessage("§b[DionaAC]§a>> Unknown AntiCheat name " + name);
    }

    public static void enablePlayerAntiCheat(Player player, String name) {
        if (isHookedAntiCheat(name)) {
            DionaPlayer dionaPlayer = PluginHooker.getPlayerManager().getDionaPlayer(player);
            if (dionaPlayer == null) return;
            dionaPlayer.enablePlugin(getAntiCheat(name));
        }
    }

    public static void disablePlayerAntiCheat(Player player, String name) {
        if (isHookedAntiCheat(name)) {
            DionaPlayer dionaPlayer = PluginHooker.getPlayerManager().getDionaPlayer(player);
            if (dionaPlayer == null) return;
            dionaPlayer.disablePlugin(getAntiCheat(name));
        }
    }

    public static void disableAllAntiCheat(Player player) {
        for (Plugin plugin : DionaAC.getInstance().hookedPlugin.values())
            disablePlayerAntiCheat(player, plugin.getName());
    }

    public static ArrayList<Plugin> getPlayerEnabledAntiCheatList(Player player) {
        DionaPlayer dionaPlayer = PluginHooker.getPlayerManager().getDionaPlayer(player);
        if (dionaPlayer == null) return new ArrayList<>();
        return new ArrayList<>(dionaPlayer.getEnabledPlugins());
    }

    public static boolean isHookedAntiCheat(String name) {
        return DionaAC.getInstance().hookedPlugin.containsKey(name);
    }

    public static Plugin getAntiCheat(String name) {
        return DionaAC.getInstance().hookedPlugin.get(name);
    }

}
