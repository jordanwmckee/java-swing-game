import java.awt.*;

public class Score extends Rectangle{
	
	static int GAME_WIDTH;
	static int GAME_HEIGHT;
	int player1;
	
	Score(int GAME_WIDTH, int GAME_HEIGHT) {
		Score.GAME_WIDTH = GAME_WIDTH;
		Score.GAME_HEIGHT = GAME_HEIGHT;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Consolas", Font.PLAIN, 60));
		// score will always has 2 digits (even when it's < 10)		
		g.drawString(String.valueOf(player1/10) + String.valueOf(player1%10), GAME_WIDTH/2-33, 50);
	}
}
