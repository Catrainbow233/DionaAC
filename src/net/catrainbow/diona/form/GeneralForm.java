package net.catrainbow.diona.form;

import cn.nukkit.Player;
import moe.him188.gui.window.FormSimple;

public class GeneralForm extends FormSimple {

    public GeneralForm() {
        super("General", "Diona AntiCheat Control Panel");
        this.addButton("AntiCheats");
        this.addButton("Settings");
    }

    @Override
    public void onClicked(int id, Player player) {
        switch (id) {
            case 0:
                player.showFormWindow(new AntiCheatForm(player));
                break;
            case 1:
                player.showFormWindow(new SettingForm(player));
                break;
        }
    }
}
