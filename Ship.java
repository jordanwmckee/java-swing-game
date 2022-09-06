
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Ship extends Rectangle{
	
	int id;
	int xVelocity;
	int yVelocity;
	int speed = 10;	// increase this value to make ship move quicker
	Random random = new Random();
	
	Ship(int x, int y, int SHIP_WIDTH, int SHIP_HEIGHT, int id) {
		super(x, y, SHIP_WIDTH, SHIP_HEIGHT);
		this.id = id;
	}
	
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_A) {
			setXDirection(-speed);
			move();
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			setXDirection(speed);
			move();
		}
		if(e.getKeyCode() == KeyEvent.VK_W) {
			setYDirection(-speed);
			move();
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			setYDirection(speed);
			move();
		}
		//-------------------------------alt input
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			setXDirection(speed);
			move();
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			setXDirection(-speed);
			move();
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			setYDirection(-speed);
			move();
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			setYDirection(speed);
			move();
		}
	}
	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_A) {
			setXDirection(0);
			move();
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			setXDirection(0);
			move();
		}
		if(e.getKeyCode() == KeyEvent.VK_W) {
			setYDirection(0);
			move();
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			setYDirection(0);
			move();
		}
		//-------------------------------alt input
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			setXDirection(0);
			move();
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			setXDirection(0);
			move();
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			setYDirection(0);
			move();
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			setYDirection(0);
			move();
		}
	}
	public void setXDirection(int xDirection) {
		xVelocity = xDirection;
	}
	public void setYDirection(int yDirection) {
		yVelocity = yDirection;
	}
	public void move() {
		x = x + xVelocity;
		y = y + yVelocity;
	}
	public void draw(Graphics g) {		
		g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
		g.fillRect(x, y, width, height);
	}
	
}
