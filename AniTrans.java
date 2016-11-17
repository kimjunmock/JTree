package aniboom;


public class AniTrans {
	public int x, y;
    public AniTrans(int x1, int y1) {
        x = x1;
        y = y1;
    }
    public boolean equals(AniTrans anitrans) {
        return this.x == anitrans.x && this.y == anitrans.y;
    }
}
