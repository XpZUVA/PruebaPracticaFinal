import java.awt.*;

public class Ball {

    public int ballX = 325;
    public int ballY = 562;
    public int ballW, ballH;

    public float speedX, speedY;
    public int intialSpeedX = 6;
    public int intialSpeedY = -6;

    public Rectangle ballRect = new Rectangle(250, 600, 20, 20);

    public Ball(){
        speedX = intialSpeedX;
        speedY = intialSpeedY;
    }

    public void move(){
        ballX += speedX;
        ballY += speedY;
        ballRect.setLocation(ballX, ballY);
    }
}
