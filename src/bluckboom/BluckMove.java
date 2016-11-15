package bluckboom;

public class BluckMove {
	  public static  int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	    public static  int up = 0, right = 1, down = 2, left = 3;
	    public static  int NONE = -1, none = -1;
	    public static int getblucksub(BluckSub a, BluckSub b) {
	        if(a.x == b.x && a.y == b.y) return NONE;
	        if(a.x == b.x) {
	            if(a.y - b.y < 0) {
	                return DOWN;
	            } else {
	                return UP;
	            }
	        }
	        if(a.y == b.y) {
	            if(a.x - b.x < 0) {
	                return RIGHT;
	            } else {
	                return LEFT;
	            }
	        } else {
	            return NONE;
	        }
	    }
}
