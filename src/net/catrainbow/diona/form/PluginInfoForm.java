package net.catrainbow.diona.form;

import cn.nukkit.Player;
import cn.nukkit.plugin.Plugin;
import moe.him188.gui.window.FormSimple;
import net.catrainbow.diona.DionaAC;
import net.catrainbow.diona.DionaAPI;

public class PluginInfoForm extends FormSimple {

    public PluginInfoForm() {
        super("AntiCheats' Info", "");
        for (Plugin plugin : DionaAC.getInstance().hookedPlugin.values())
            this.addButton(plugin.getName());
    }

    @Override
    public void onClicked(int id, Player player) {
        Plugin plugin = DionaAC.getInstance().hookedPlugin.values().toArray(new Plugin[0])[id];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(plugin.getName()).append("\n");
        stringBuilder.append("Description: ").append(plugin.getDescription().getDescription()).append("\n");
        stringBuilder.append("Author: ");
        plugin.getDescription().getAuthors().forEach(it -> stringBuilder.append("- ").append(it).append("\n"));
        stringBuilder.append("Version: ").append(plugin.getDescription().getVersion());
        FormSimple formSimple = new FormSimple(plugin.getName(), stringBuilder.toString());
        formSimple.addButton("§b" + plugin.getName() + "\n§dStatus: " + this.formatBoolean(DionaAPI.getPlayerEnabledAntiCheatList(player).contains(plugin)));
        player.showFormWindow(formSimple.onClicked(subId -> DionaAPI.reactionAntiCheat(player, plugin.getName())));
    }

    private String formatBoolean(boolean bool) {
        return bool ? "§aTrue" : "§cFalse";
    }
}
