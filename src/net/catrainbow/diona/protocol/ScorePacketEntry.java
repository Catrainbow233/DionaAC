package net.catrainbow.diona.protocol;

public class ScorePacketEntry {
    public static final int TYPE_PLAYER = 1;
    public static final int TYPE_ENTITY = 2;
    public static final int TYPE_FAKE_PLAYER = 3;
    public long scoreboardId;
    public String objectiveName;
    public int score;
    public long entityId;
    public String fakePlayer;
    public byte addType;

    public ScorePacketEntry() {
    }
}
