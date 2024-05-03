import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

public class Modelo extends Observable {

    public int barX = 250;
    public int ballX = 325;
    public int ballY = 562;
    public int barW, barH, ballW, ballH;

    public Timer juego;

    public float speedX;
    public float speedY;
    public int initialSpeedX = 6;
    public int initialSpeedY = -6;


    String texto = "Pulsa <Enter> para comenzar";
    public boolean gameStarted = false;

    public Rectangle ballRect = new Rectangle(250, 600, 20, 20);
    public Rectangle barRect = new Rectangle(250, 600, 100, 20);

    public int vidas = 3;
    public boolean gameOver = false;


    public void setBarX(int barX){
        this.barX = barX;
        setChanged();
        notifyObservers();
    }

    public void startGame(){
        texto = "";
        ballX = 325;
        ballY = 562;
        speedX = initialSpeedX;
        speedY = initialSpeedY;
        barX = 250;
        gameStarted = true;
        if (gameOver){
            vidas = 3;
            gameOver = false;
        }
        this.init();
        setChanged();
        notifyObservers();
    }


    public void init() {
        juego = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                ballX += speedX;
                ballY += speedY;
                setChanged();
                notifyObservers();
                System.out.println("ballX: " + ballX + " ballY: " + ballY + " speedX: " + speedX + " speedY: " + speedY);


                if(ballX < 0 || ballX > 660){
                    speedX = -speedX;
                }
                if(ballY < 50 || ballY == 49){
                    speedY = Math.abs(speedY) + 1;
                }
                barRect.setBounds(barX, 600, barW, barH);
                ballRect.setBounds(ballX, ballY, ballH, ballW);

                if(ballRect.intersects(barRect)){
                    int midPointBallX = ballX + ballW/2;
                    int midPointBarX = barX + barW/2;

                    if(midPointBallX < midPointBarX){
                        float colisionPoint = 2 * (midPointBallX - barX);
                        float speedMultiplier = colisionPoint / 100;
                        speedY = -Math.abs(initialSpeedY * speedMultiplier);
                        speedX = -(initialSpeedX * (1-speedMultiplier));

                    }else if(midPointBallX > midPointBarX){
                        float colisionPoint = 2 * (midPointBallX - midPointBarX);
                        float speedMultiplier = (100 - colisionPoint) / 100;
                        speedY = -Math.abs(initialSpeedY * speedMultiplier);
                        speedX = initialSpeedX * (1 - speedMultiplier);

                    }


                }
                if(ballY > 800){
                    gameStarted = false;
                    juego.stop();
                    vidas--;
                    if(vidas == 0){
                        texto = "No te quedan vidas <Enter> para reiniciar";
                        gameOver = true;

                    }else{
                        texto = "Pulsa <Enter> para continuar";
                    }
                    juego.stop();
                    setChanged();
                    notifyObservers();
                }
            }
        });

        juego.start();
    }

}
