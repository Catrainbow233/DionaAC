package net.catrainbow.diona.form;

import cn.nukkit.Player;
import cn.nukkit.plugin.Plugin;
import moe.him188.gui.window.FormSimple;
import net.catrainbow.diona.DionaAC;
import net.catrainbow.diona.DionaAPI;

public class AntiCheatForm extends FormSimple {

    public AntiCheatForm(Player player) {
        super("AntiCheats", "");
        for (Plugin plugin : DionaAC.getInstance().hookedPlugin.values())
            addButton("§b" + plugin.getName() + "\n§dStatus: " + this.formatBoolean(DionaAPI.getPlayerEnabledAntiCheatList(player).contains(plugin)));
    }

    private String formatBoolean(boolean bool) {
        return bool ? "§aTure" : "§cFalse";
    }

    @Override
    public void onClicked(int id, Player player) {
        DionaAPI.reactionAntiCheat(player, String.valueOf((DionaAC.getInstance().hookedPlugin.values().toArray(new Plugin[0])[id]).getName()));
    }
}
