
package net.catrainbow.diona.utils;

import cn.nukkit.Player;
import net.catrainbow.diona.protocol.RemoveObjectivePacket;
import net.catrainbow.diona.protocol.ScorePacketEntry;
import net.catrainbow.diona.protocol.SetDisplayObjectivePacket;
import net.catrainbow.diona.protocol.SetScorePacket;

import java.util.HashMap;
import java.util.List;

public class Scoreboards {
    private static final HashMap<String, String> scoreboards = new HashMap();

    public Scoreboards() {
    }

    public static void add(Player player, String displayName) {
        if (hasPlayer(player)) {
            remove(player);
        }

        String objectiveName = player.getName();
        SetDisplayObjectivePacket pk = new SetDisplayObjectivePacket();
        pk.displaySlot = "sidebar";
        pk.objectiveName = objectiveName;
        pk.displayName = displayName;
        pk.criteriaName = "dummy";
        pk.sortOrder = 0;
        player.dataPacket(pk);
        scoreboards.put(objectiveName, objectiveName);
    }

    public static void remove(Player player) {
        if (scoreboards.containsKey(player.getName())) {
            RemoveObjectivePacket pk = new RemoveObjectivePacket();
            pk.objectiveName = player.getName();
            player.dataPacket(pk);
            scoreboards.remove(player.getName());
        }

    }

    public static void setMessage(Player player, List<String> strings) {
        SetScorePacket pk = new SetScorePacket();
        pk.type = 0;

        for(int line = 0; line < strings.size(); ++line) {
            ScorePacketEntry entry = new ScorePacketEntry();
            entry.objectiveName = player.getName();
            entry.addType = 3;
            entry.fakePlayer = strings.get(line);
            entry.score = line;
            entry.scoreboardId = line;
            pk.entries.add(entry);
        }

        player.dataPacket(pk);
    }

    public static boolean hasPlayer(Player player) {
        return scoreboards.containsKey(player.getName());
    }
}
