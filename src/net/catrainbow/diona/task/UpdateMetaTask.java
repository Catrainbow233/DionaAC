package net.catrainbow.diona.task;

import cn.nukkit.Player;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.Task;
import net.catrainbow.diona.DionaAC;
import net.catrainbow.diona.DionaAPI;
import net.catrainbow.diona.DionaMeta;
import net.catrainbow.diona.utils.Scoreboards;

import java.util.ArrayList;

public class UpdateMetaTask extends Task {

    private final ArrayList<String> updatedList = (ArrayList<String>) DionaAC.getInstance().getConfig().getStringList("scoreboards");

    @Override
    public void onRun(int i) {
        for (Player player : DionaAC.getInstance().getServer().getOnlinePlayers().values()) {
            if (!DionaMeta.playerDionaMeta.containsKey(player.getName())) return;
            Scoreboards.add(player, DionaAC.getInstance().getConfig().getString("server-name"));
            ArrayList<String> subList = new ArrayList<>();
            updatedList.forEach(it -> subList.add(it.replace("@ac", this.getPlayerAntiCheats(player)).replace("@hunger", formatBoolean(DionaMeta.playerDionaMeta.get(player.getName()).hunger)).replace("@kick", formatBoolean(DionaMeta.playerDionaMeta.get(player.getName()).kick)).replace("@damage", formatBoolean(DionaMeta.playerDionaMeta.get(player.getName()).damage)).replace("@flag", formatBoolean(DionaMeta.playerDionaMeta.get(player.getName()).flag))));
            Scoreboards.setMessage(player, subList);
            if (!DionaMeta.playerDionaMeta.get(player.getName()).hunger)
                player.getFoodData().reset();
        }
        DionaAC.getInstance().getServer().getDefaultLevel().setRaining(false);
        DionaAC.getInstance().getServer().getDefaultLevel().setTime(1000);
        DionaAC.getInstance().getServer().getDefaultLevel().setThundering(false);
    }

    private String getPlayerAntiCheats(Player player) {
        StringBuilder stringBuilder = new StringBuilder();
        if (DionaAPI.getPlayerEnabledAntiCheatList(player).size() == 0) return "§eNone";
        for (Plugin plugin : DionaAPI.getPlayerEnabledAntiCheatList(player)) {
            stringBuilder.append(plugin.getName()).append(" ");
        }
        return stringBuilder.toString();
    }

    private String formatBoolean(boolean bool) {
        return bool ? "§aTrue" : "§cFalse";
    }

}
