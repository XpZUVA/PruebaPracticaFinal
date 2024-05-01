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

    public int speedX = -3;
    public int speedY = 3;
    public int initialSpeedY = speedY + 1;
    public int intialSpeedX = speedX + 1;


    String texto = "Pulsa <Enter> para comenzar";
    public boolean gameStarted = false;

    public Rectangle ballRect = new Rectangle(250, 600, 20, 20);
    public Rectangle barRect = new Rectangle(0, 0, 100, 20);

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
        speedX = -3;
        speedY = 3;
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
                ballX -= speedX;
                ballY -= speedY;
                setChanged();
                notifyObservers();


                if(ballX < 0 || ballX > 660){
                    speedX = -speedX;
                }
                if(ballY < 50){
                    speedY = -speedY;
                }
                barRect.setBounds(barX, 600, barW, barH);
                ballRect.setBounds(ballX, ballY, ballH, ballW);

                if(ballRect.intersects(barRect)){
                    //calcula el angulo de rebote
                    double intersectX = ballX + (ballW / 2) - barX;
                    double normalizedIntersectX = intersectX / barW;
                    double bounceAngle = normalizedIntersectX * Math.toRadians(60);

                    // Ajusta la velocidad de la pelota según el ángulo de rebote
                    double newSpeedX = speedY * Math.sin(bounceAngle);
                    double newSpeedY = -speedY * Math.cos(bounceAngle);
                    speedX = (int) Math.round(newSpeedX);
                    speedY = (int) Math.round(newSpeedY);

                    speedY = (int) Math.signum(speedY) * initialSpeedY;
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