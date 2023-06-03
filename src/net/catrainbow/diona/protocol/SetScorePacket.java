
package net.catrainbow.diona.protocol;

import cn.nukkit.network.protocol.DataPacket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SetScorePacket extends DataPacket {
    public static final int TYPE_CHANGE = 0;
    public static final int TYPE_REMOVE = 1;
    public byte type;
    public List<ScorePacketEntry> entries = new ArrayList();

    public SetScorePacket() {
    }

    public void decode() {
        this.type = (byte)this.getByte();
        this.entries = this.getScorePacketInfos();
    }

    public void encode() {
        this.reset();
        this.putByte(this.type);
        this.putScorePacketInfos(this.entries);
    }

    public byte pid() {
        return 108;
    }

    public void putScorePacketInfos(List<ScorePacketEntry> info) {
        this.putUnsignedVarInt((long)info.size());
        Iterator var2 = info.iterator();

        while(var2.hasNext()) {
            ScorePacketEntry entry = (ScorePacketEntry)var2.next();
            this.putVarLong(entry.scoreboardId);
            this.putString(entry.objectiveName);
            this.putLInt(entry.score);
            this.putByte(entry.addType);
            switch (entry.addType) {
                case 1:
                case 2:
                    this.putEntityUniqueId(entry.entityId);
                    break;
                case 3:
                    this.putString(entry.fakePlayer);
            }
        }

    }

    public List<ScorePacketEntry> getScorePacketInfos() {
        List<ScorePacketEntry> info = new ArrayList();
        long length = this.getUnsignedVarInt();

        for(int i = 0; i <= (int)length; ++i) {
            ScorePacketEntry entry = new ScorePacketEntry();
            entry.scoreboardId = this.getVarLong();
            entry.objectiveName = this.getString();
            entry.score = this.getLInt();
            if (this.type == 0) {
                entry.addType = (byte)this.getByte();
                switch (entry.addType) {
                    case 1:
                    case 2:
                        entry.entityId = this.getEntityUniqueId();
                        break;
                    case 3:
                        entry.fakePlayer = this.getString();
                }
            }

            info.add(entry);
        }

        return info;
    }
}
