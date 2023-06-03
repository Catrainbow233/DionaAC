package net.catrainbow.diona.form;

import cn.nukkit.Player;
import moe.him188.gui.window.FormSimple;
import net.catrainbow.diona.DionaMeta;

public class SettingForm extends FormSimple {

    public SettingForm(Player player) {
        super("Settings", "set your own profile");
        DionaMeta meta = DionaMeta.playerDionaMeta.get(player.getName());
        addButton("§dKick\n" + formatBoolean(meta.kick));
        addButton("§dFlag\n" + formatBoolean(meta.flag));
        addButton("§dHunger\n" + formatBoolean(meta.hunger));
        addButton("§dDamage\n" + formatBoolean(meta.damage));
    }

    private String formatBoolean(boolean bool) {
        return bool ? "§aTrue" : "§cFalse";
    }

    @Override
    public void onClicked(int id, Player player) {
        DionaMeta meta = DionaMeta.playerDionaMeta.get(player.getName());
        switch (id) {
            case 0:
                meta.kick = !meta.kick;
                break;
            case 1:
                meta.flag = !meta.flag;
            case 2:
                meta.hunger = !meta.hunger;
                break;
            case 3:
                meta.damage = !meta.damage;
                break;
        }
        player.sendMessage("§b[DionaAC]§a>> Update profile successfully!");
    }
}
