package net.catrainbow.diona.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.Plugin;
import net.catrainbow.diona.DionaAC;
import net.catrainbow.diona.DionaAPI;
import net.catrainbow.diona.form.GeneralForm;

public class AntiCheatCommand extends Command {

    public AntiCheatCommand(String commandStr, String commandDescription) {
        super(commandStr, commandDescription);
        this.setUsage("§b[DionaAC]§a Command Usage: /ac help");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        switch (strings.length) {
            case 0:
                StringBuilder stringBuilder = new StringBuilder("§e");
                for (Plugin plugin : DionaAC.getInstance().hookedPlugin.values())
                    stringBuilder.append(plugin.getName()).append(" ");
                commandSender.sendMessage("§b[DionaAC]§a>> " + stringBuilder);
                return true;
            case 1:
                if (strings[0].equals("help")) {
                    commandSender.sendMessage("§b[DionaAC]§a>> =====================");
                    commandSender.sendMessage("§b[DionaAC]§a>> /ac - See all available anti-cheats");
                    commandSender.sendMessage("§b[DionaAC]§a>> /ac <name> - enable or disable some anti-cheats");
                    commandSender.sendMessage("§b[DionaAC]§a>> /ac gui - toggle the gui");
                    return true;
                } else if (strings[0].equals("gui")) {
                    ((Player) commandSender).showFormWindow(new GeneralForm());
                    return true;
                } else DionaAPI.reactionAntiCheat((Player) commandSender, strings[0]);
                return true;
            default:
                for (String pluginName : strings)
                    DionaAPI.reactionAntiCheat((Player) commandSender, pluginName);
                break;
        }

        return true;
    }
}
