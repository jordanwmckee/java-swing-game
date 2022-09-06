import java.awt.*;

public class Meteor extends Rectangle{

	static float yVelocity = 3;	// increase this to make meteor drop faster
	
	Meteor(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public void move() {
		y += yVelocity;
	}
	public void draw(Graphics g) {
		g.setColor(Color.gray);
		g.fillOval(x, y, height, width);
	}
}
