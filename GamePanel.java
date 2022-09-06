import java.awt.*;	
import java.awt.event.*;
import java.lang.System.Logger.Level;
import java.util.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	
	static final int GAME_WIDTH = 600;
	static final int GAME_HEIGHT = 720;
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
	static final int METEOR_DIAMETER = 70;
	static final int BULLET_HEIGHT = 25;
	static final int BULLET_WIDTH = 35;
	static final int SHIP_WIDTH = 60;
	static final int SHIP_HEIGHT = 35;
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Ship ship;
	Meteor meteor;
	Meteor meteor2;
	Score score;
	Bullet bullet1;
	Bullet bullet2;
	boolean running = true;
	
	GamePanel() {
		newShip();
		newMeteor();
		meteor2 = new Meteor(random.nextInt(GAME_WIDTH-METEOR_DIAMETER), -100, METEOR_DIAMETER, METEOR_DIAMETER);
		score = new Score(GAME_WIDTH, GAME_HEIGHT);
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(SCREEN_SIZE);
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void newMeteor() {
		random = new Random();
		meteor = new Meteor(random.nextInt(GAME_WIDTH-METEOR_DIAMETER), 0, METEOR_DIAMETER, METEOR_DIAMETER);
	}
	public void newMeteor2() {
		random = new Random();
		meteor2 = new Meteor(random.nextInt(GAME_WIDTH-METEOR_DIAMETER), 0, METEOR_DIAMETER, METEOR_DIAMETER);
	}
	public void newShip() {
		ship = new Ship((GAME_WIDTH/2)-(SHIP_WIDTH/2), (GAME_HEIGHT-SHIP_HEIGHT-75), SHIP_WIDTH, SHIP_HEIGHT, 1); 
	}
	public void newBullet1() {
		bullet1 = new Bullet(ship.x, ship.y, BULLET_WIDTH, BULLET_HEIGHT);
	}
	public void newBullet2() {
		bullet2 = new Bullet(ship.x + (SHIP_WIDTH/2), ship.y, BULLET_WIDTH, BULLET_HEIGHT);
	}
	public void paint(Graphics g) {
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image, 0, 0, this);
	}
	public void draw(Graphics g) {
		if (running) {
			ship.draw(g);
			meteor.draw(g);
			meteor2.draw(g);
			score.draw(g);
			//only draw bullets if an instance of Bullets is instantiated
			if(bullet1 != null)
				bullet1.draw(g);
			if(bullet2 != null)
				bullet2.draw(g);
		} else {
			gameOver(g);
		}
	}
	public void move() {
		ship.move();
		meteor.move();
		meteor2.move();
		//only draw bullets if an instance of Bullets is instantiated
		if(bullet1 != null)
			bullet1.move();
		if(bullet2 != null)
			bullet2.move();
	}
	public void checkCollision() {
		// meteor gets shot 2 cases 1 for each meteor
		if(bullet1 != null && bullet1.intersects(meteor)) {
			meteor.yVelocity += 0.02;		//makes meteor faster each hit
			score.player1 += 10;
			bullet1 = null;
			newMeteor();
		}
		if(bullet1 != null && bullet1.intersects(meteor2)) {
			meteor.yVelocity += 0.02;		//makes meteor faster each hit
			score.player1 += 10;
			bullet1 = null;
			newMeteor2();
		}
		
		if(bullet2 != null && bullet2.intersects(meteor)) {
			meteor.yVelocity += 0.02;		//makes meteor faster each hit
			score.player1 += 10;
			bullet2 = null;
			newMeteor();
		}
		if(bullet2 != null && bullet2.intersects(meteor2)) {
			meteor.yVelocity += 0.02;		//makes meteor faster each hit
			score.player1 += 10;
			bullet2 = null;
			newMeteor2();
		}
		// meteor hits ship (ends game and displays score by calling gameOver())
		if(meteor.intersects(ship)) {
			meteor.yVelocity = 0;
			running = false;
		}
		if(meteor2.intersects(ship)) {
			meteor.yVelocity = 0;
			running = false;
		}
		// stop ship at edge of screen
		if(ship.x <= 0)
			ship.x = 0;
		if(ship.x >= (GAME_WIDTH - SHIP_WIDTH))
			ship.x = (GAME_WIDTH - SHIP_WIDTH);
		if(ship.y <= 0)
			ship.y = 0;
		if(ship.y >= (GAME_HEIGHT - SHIP_HEIGHT))
			ship.y = (GAME_HEIGHT - SHIP_HEIGHT);
		// meteor goes off screen
		if(meteor.y > GAME_HEIGHT-METEOR_DIAMETER) {
			score.player1 -= 100;
			if (score.player1 < 0) {
				score.player1 = 0;
				meteor.yVelocity = 0;
				running = false;
			}
			newMeteor();
		}
		if(meteor2.y > GAME_HEIGHT-METEOR_DIAMETER) {
			score.player1 -= 100;
			if (score.player1 < 0) {
				score.player1 = 0;
				meteor.yVelocity = 0;
				running = false;
			}
			newMeteor2();
		}
		// bullet hits top of screen
		if(bullet1 != null && bullet1.y <= 0) {
			bullet1.setYDirection(0);
			bullet1 = null;
		}
		if(bullet2 != null && bullet2.y <= 0) {
			bullet2.setYDirection(0);
			bullet2 = null;
		}

	}
	public void run() {
		// game loop
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while(true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				move();
				checkCollision();
				repaint();
				delta--;
			}
		}
	}
	
	public void gameOver(Graphics g) {
		// Game Over text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (GAME_WIDTH - metrics1.stringWidth("Game Over"))/2, GAME_HEIGHT/2);
		// Score text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Score: " + score.player1, (GAME_WIDTH - metrics2.stringWidth("Score: " + score.player1))/2, g.getFont().getSize());
	}
	
	public class AL extends KeyAdapter {
		
		public void keyPressed(KeyEvent e) {
			ship.keyPressed(e);
			
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				
				if (bullet1 == null) {
					newBullet1();
					bullet1.keyPressed(e);
					return;
				}
				if (bullet1.y >= ship.y) {	
					newBullet1();
					bullet1.keyPressed(e);
				}
				if (bullet2 == null) {
					newBullet2();
					bullet2.keyPressed(e);
					return;
				}
				if (bullet2.y >= ship.y) {	
					newBullet1();
					bullet2.keyPressed(e);
				}
			}
		}
		public void keyReleased(KeyEvent e) {
			ship.keyReleased(e);
		}
	}
}
