import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Gui extends JFrame {

    public Gui(){
        add(new GuiPanel());
        setTitle("Snake");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}

