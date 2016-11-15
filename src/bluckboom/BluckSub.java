package bluckboom;

public class BluckSub {
	 int x, y;
    public BluckSub(int rex, int rey) {
        x = rex;
        y = rey;
    }
    public boolean equals(BluckSub BluckSubsub) {
        return this.x == BluckSubsub.x && this.y == BluckSubsub.y;
    }
}
