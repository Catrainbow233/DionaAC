package net.catrainbow.diona.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerKickEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.server.DataPacketSendEvent;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.DisconnectPacket;
import net.catrainbow.diona.DionaAC;
import net.catrainbow.diona.DionaAPI;
import net.catrainbow.diona.DionaMeta;

public class PluginListener implements Listener {

    @EventHandler
    public void onPlayerJoins(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        DionaMeta.playerDionaMeta.put(player.getName(), new DionaMeta());
        DionaAPI.disableAllAntiCheat(player);
        player.teleport(DionaAC.getInstance().getServer().getDefaultLevel().getSpawnLocation().add(0.0, 1.0, 0.0));
    }

    @EventHandler
    public void onPlayerQuits(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        DionaMeta.playerDionaMeta.remove(player.getName());
    }

    @EventHandler
    public void onEntityDamaged(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (event.getCause() == EntityDamageEvent.DamageCause.VOID)
                player.teleport(DionaAC.getInstance().getServer().getDefaultLevel().getSpawnLocation().add(0.0, 1.0, 0.0));
            if (!DionaMeta.playerDionaMeta.get(player.getName()).damage) event.setCancelled();
            else player.sendTip("§eDamage: §c" + event.getDamage() + " §eFinal: §c" + event.getFinalDamage());
        }
    }

    @EventHandler
    public void onPlayerBeenKicked(PlayerKickEvent event) {
        if (DionaMeta.playerDionaMeta.containsKey(event.getPlayer().getName())) {
            Player player = event.getPlayer();
            if (!DionaMeta.playerDionaMeta.get(player.getName()).kick) {
                event.setCancelled();
                player.sendTitle(DionaAC.getInstance().getConfig().getString("kick"), event.getReason(), 20, 100, 20);
            }
        }
    }

    @EventHandler
    public void onDataPacketReceived(DataPacketSendEvent event) {
        DataPacket packet = event.getPacket();
        if (packet instanceof DisconnectPacket) {
            if (DionaMeta.playerDionaMeta.containsKey(event.getPlayer().getName())) {
                Player player = event.getPlayer();
                if (!DionaMeta.playerDionaMeta.get(player.getName()).kick) {
                    event.setCancelled();
                    player.sendTitle(DionaAC.getInstance().getConfig().getString("kick"), ((DisconnectPacket) packet).message, 20, 100, 20);
                }
            }
        }
    }

}
