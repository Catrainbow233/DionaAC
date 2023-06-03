package net.catrainbow.diona.protocol;

import cn.nukkit.network.protocol.DataPacket;

public class RemoveObjectivePacket extends DataPacket {
    public String objectiveName;

    public RemoveObjectivePacket() {
    }

    public void decode() {
        this.objectiveName = this.getString();
    }

    public void encode() {
        this.reset();
        this.putString(this.objectiveName);
    }

    public byte pid() {
        return 106;
    }
}

