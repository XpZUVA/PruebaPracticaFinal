import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;

public class Modelo extends Observable {

    public int barX = 250;
    public int barW, barH;

    ArrayList <Ball> balls = new ArrayList<>();

    public Timer juego;

    public float speedX;
    public float speedY;
    public int initialSpeedX = 6;
    public int initialSpeedY = -6;


    String texto = "Pulsa <Enter> para comenzar";
    public boolean gameStarted = false;

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
        crearPelota();
        gameStarted = true;
        if (gameOver){
            vidas = 3;
            gameOver = false;
        }
        this.init();
        setChanged();
        notifyObservers();
    }

    public void crearPelota(){
        balls.add(new Ball());
    }

    public void eliminarPelota(int indice){
        balls.remove(indice);
        System.out.println(balls.size());
    }


    public void init() {
        juego = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                for(int i = 0; i < balls.size(); i++){
                    balls.get(i).move();
                    setChanged();
                    notifyObservers();
                    //System.out.println("ballX: " + balls.get(i).ballX + " ballY: " + balls.get(i).ballY + " speedX: " + balls.get(i).speedX + " speedY: " + balls.get(i).speedY);
                }
                for(int i = 0; i < balls.size(); i++){
                    if(balls.get(i).ballX < 0 || balls.get(i).ballX > 660){
                        balls.get(i).speedX = -balls.get(i).speedX;
                    }
                }

                for(int i = 0; i < balls.size(); i++){
                    if(balls.get(i).ballY < 50 || balls.get(i).ballY == 49){
                        balls.get(i).speedY = Math.abs(balls.get(i).speedY) + 1;
                    }
                }

                barRect.setBounds(barX, 600, barW, barH);

                for(int i = 0; i < balls.size(); i++){
                    if(balls.get(i).ballRect.intersects(barRect)){
                        int midPointBallX = balls.get(i).ballX + balls.get(i).ballW/2;
                        int midPointBarX = barX + barW/2;
                        if(midPointBallX < midPointBarX){
                            float colisionPoint = 2 * (midPointBallX - barX);
                            float speedMultiplier = colisionPoint / 100;
                            balls.get(i).speedY = -Math.abs(initialSpeedY * speedMultiplier);
                            balls.get(i).speedX = -(initialSpeedX * (1-speedMultiplier));
                        }else if(midPointBallX > midPointBarX){
                            float colisionPoint = 2 * (midPointBallX - midPointBarX);
                            float speedMultiplier = (100 - colisionPoint) / 100;
                            balls.get(i).speedY = -Math.abs(initialSpeedY * speedMultiplier);
                            balls.get(i).speedX = initialSpeedX * (1 - speedMultiplier);
                        }
                    }
                }

                for(int i = 0; i < balls.size(); i++){
                    if(balls.get(i).ballY > 800){
                        if(balls.size() != 1){
                            eliminarPelota(i);
                        }else{
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
                }

            }
        });

        juego.start();
    }

}
