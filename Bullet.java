import java.awt.*;	
import java.awt.event.*;
import java.util.*;
import javax.swing.JPanel;

public class Bullet extends Rectangle{
	
	int yVelocity;
	int speed = 15;	//increase this to make bullet move quicker
	int colors = 1;
	
	Bullet(int x, int y, int width, int height) {
		super(x, y, width, height);
		yVelocity = speed;
	}

	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			setYDirection(-speed);
			move();
		}
	}
	public void setYDirection(int yDirection) {
		yVelocity = yDirection;
	}
	public void move() {
		y += yVelocity;
	}
	public void draw(Graphics g) {
		if(colors == 1) {
			g.setColor(Color.orange);
			colors = 2;
		} else {
			g.setColor(Color.red);
			colors = 1;
		}
		g.fillOval(x, y, height, width);
	}
}
