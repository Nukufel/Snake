import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;



public class GuiPanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 20;
    static final int GAME_UNITS = (SCREEN_HEIGHT*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int eatenAppels = 0;
    int xApple;
    int yApple;
    char direction = 'D';
    boolean running = false;
    Timer timer;
    Random random;
    private JButton restart = new JButton();


    public GuiPanel(){
        setLayout(new BorderLayout());
        restart.setVisible(false);
        restart.setFont(new Font("Ink Free",Font.BOLD, 30));
        restart.setText("Restart");
        restart.setForeground(Color.RED);
        restart.setBackground(new Color(0,0,0,0.5f));
        restart.setBorderPainted(false);
        restart.setFocusPainted(false);
        add(restart, BorderLayout.SOUTH);
        restart.setSize(100,300);

        random = new Random();
        setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        setBackground(Color.GREEN);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        startGame();



    }

    public void paintBackground(Graphics g){
        g.setColor(new Color(124, 255, 112));
        for (int i=0; i<GAME_UNITS;i++){
            if (i%2==0) {
                for (int j = 0;j<SCREEN_HEIGHT/UNIT_SIZE; j++) {
                    if (j%2==0) {
                        g.fillRect(i * UNIT_SIZE+UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                    }else{
                        g.fillRect(i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                    }
                }
            }
        }
    }


    public void startGame(){
        addApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        paintBackground(g);
        draw(g);


    }

    public void draw(Graphics g){


        if (running == true) {
            /*
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
             */



            g.setColor(Color.RED);
            g.fillOval(xApple, yApple, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.PINK);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(227, 3, 252));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free",Font.BOLD, 30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score "+eatenAppels,(SCREEN_WIDTH-metrics.stringWidth("Score "+eatenAppels))/2, g.getFont().getSize());
        }else{
            lost(g);
        }
    }

    public void addApple(){
        xApple= random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        yApple= random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
    }

    public void move(){
        for (int i = bodyParts; i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }

        switch (direction){
            case 'U':
                y[0] = y[0]-UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0]-UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0]+UNIT_SIZE;
                break;
        }
    }

    public void checkApple(){
        if ((x[0]==xApple)&&y[0]==yApple){
            bodyParts++;
            eatenAppels++;
            addApple();
        }
    }

    public void checkCollision(){

        for (int i = bodyParts;i>0;i--){
            if((x[0] == x[i]&&y[0]==y[i])){
                running = false;
            }
        }

        if (x[0]<0){
            running = false;
        }

        if (x[0]>SCREEN_WIDTH){
            running = false;
        }

        if (y[0]<0){
            running = false;
        }


        if (y[0]>SCREEN_HEIGHT){
            running = false;
        }

        if (running == false){
            timer.stop();
        }

    }

    public void lost(Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free",Font.BOLD, 30));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score "+eatenAppels,(SCREEN_WIDTH-metrics2.stringWidth("Score "+eatenAppels))/2, g.getFont().getSize());

        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free",Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",(SCREEN_WIDTH-metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
/*
        restart.setVisible(true);
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

 */
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running == true){
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
         @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
         }
    }


}
