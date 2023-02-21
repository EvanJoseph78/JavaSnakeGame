package javasnake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener{

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DALAY = 75;
    final int x[] = new int[GAME_UNITS]; 
    final int y[] = new int[GAME_UNITS]; 
    int partesCorpo = 6;
    int frutasComidas;
    int frutaX;
    int frutaY;
    char direcao = 'R';
    boolean running = false;
    Timer timer;
    Random random;


    GamePanel () {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        iniciarJogo();
    }

    public void iniciarJogo() {
        novaFruta();
        running = true;
        timer = new Timer(DALAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        desenha(g);
    }

    public void desenha(Graphics g) { 

        if(running) {
            // for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
            //     g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            //     g.drawLine(0, i*UNIT_SIZE, SCREEN_HEIGHT, i*UNIT_SIZE);
            // }
            g.setColor(Color.red);
            g.fillOval(frutaX, frutaY, UNIT_SIZE, UNIT_SIZE);
    
            for (int i = 0; i < partesCorpo; i++) {
                if(i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
    
                }else{
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            
        }else{
            gameOver(g);
        }

        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Placar:" + frutasComidas, (SCREEN_WIDTH - metrics.stringWidth("Placar:" + frutasComidas)) / 2, g.getFont().getSize());
    }
    public void novaFruta() {
        frutaX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        frutaY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void movimenta() { 
        for (int i = partesCorpo; i > 0 ; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direcao) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE; 
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE; 
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE; 
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE; 
                break;
        
        }
    }
    public void verificaFruta() {
        if((x[0] == frutaX) && (y[0] == frutaY)){
            partesCorpo++;
            frutasComidas++;
            novaFruta();
        }
    }
    public void verificaColisoes() {
        // verifica se a cabeca colide com o corpo
        for (int i = partesCorpo; i > 0; i--) {
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            } 
        }
        // verifica se a cabeca toca a borda esquerda
        if(x[0] < 0) {
            running = false;
        }
        // verifica se a cabeca toca a borda direita
        if(x[0] > SCREEN_WIDTH) {
            running = false;
        }
        // verifica se a cabeca toca a borda de cima
        if(y[0] < 0) {
            running = false;
        }
        // verifica se a cabeca toca a borda de baixo
        if(y[0] > SCREEN_HEIGHT) {
            running = false;
        }

        if(running == false) {
            timer.stop();
        }
    }
    public void gameOver(Graphics g) {
        //texto de game over
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Placar:" + frutasComidas, (SCREEN_WIDTH - metrics2.stringWidth("Placar:" + frutasComidas)) / 2, g.getFont().getSize());
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(running){
            movimenta();
            verificaFruta();
            verificaColisoes();
        }

        repaint();
    }

    public class myKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direcao != 'R'){
                        direcao = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direcao != 'L'){
                        direcao = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direcao != 'D'){
                        direcao = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direcao != 'U'){
                        direcao = 'D';
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
