package cn.edu.wku.util;

public class GamePackage {

    // Package Type
    // Client -> Host: Join Room, Share Name
    // Example: SYN | Xue Haotian
    public static final int SYN = 0;
    // Host -> Client: Determine B/W, Share Name
    // Example: SYN ACK | Black $ Xue Haotian
    public static final int SYN_ACK = 1;
    // Host <-> Client: (Set Ready), Inform Move
    // Example: ACK | 10 10
    public static final int ACK = 2;

    public static final GamePackage BARE_ACK = new GamePackage(2, false, null, null);

    // Always Available
    private int type;
    // Available in: SYN_ACK
    private boolean color;
    // Available in: SYN, SYN ACK
    private String name;
    // Available in: ACK
    private Move move;

    public static GamePackage getGamePackage(String message) {
        message = message.trim();
        // Is bare ACK (Packet #3)
        if (message.equals("ACK")) {
            return BARE_ACK;
        }
        // Split type area and content area
        String[] temp = message.split("\\|");
        String[] types = temp[0].trim().split(" ");
        if (types.length == 1) {
            // Is SYN
            if (types[0].equals("SYN")) {
                return new GamePackage(SYN, false, temp[1].trim(), null);
            }
            // Is ACK
            else {
                String[] nums = temp[1].trim().split(" ");
                return new GamePackage(ACK, false, null, new Move(Integer.parseInt(nums[0]), Integer.parseInt(nums[1])));
            }
        }
        // Is SYN ACK
        else {
            temp = temp[1].trim().split("\\$");
            return new GamePackage(SYN_ACK, temp[0].trim().equals("Black"), temp[1].trim(), null);
        }
    }

    private GamePackage(int type, boolean color, String name, Move move) {
        this.type = type;
        this.color = color;
        this.name = name;
        this.move = move;
    }

    public boolean isType(int type) {
        return this.type==type;
    }

    public int getType() {
        return type;
    }

    public boolean getColor() {
        if (type != SYN_ACK) throw new RuntimeException("Try to get color from package of wrong type");
        return color;
    }

    public String getName() {
        if (type == ACK) throw new RuntimeException("Try to get name from package of wrong type");
        return name;
    }

    public Move getMove() {
        if (type != ACK || this == BARE_ACK) throw new RuntimeException("Try to get move from package of wrong type");
        return move;
    }

    public static GamePackage getSYN(String name) {
        return new GamePackage(SYN, false, name, null);
    }

    public static GamePackage getSYNACK(String name, boolean color) {
        return new GamePackage(SYN_ACK, color, name, null);
    }

    public static GamePackage getBareAck() {
        return BARE_ACK;
    }

    public static GamePackage getACK(Move move) {
        return new GamePackage(ACK, false, null, move);
    }
    @Override
    public String toString() {
        if (type == SYN) return String.format("SYN | %s\n", name);
        if (type == SYN_ACK) return String.format("SYN ACK | %s $ %s\n", color?"Black":"White", name);
        if (this == BARE_ACK) return "ACK\n";
        else return String.format("ACK | %d %d\n", move.getRowNum(), move.getColNum());
    }
}
