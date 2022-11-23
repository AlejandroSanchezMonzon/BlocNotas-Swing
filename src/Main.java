import views.View;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        System.setProperty("apple.awt.application.appearance", "system");
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "WikiTeX");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                 IllegalAccessException ignored){
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                View view = new View();
                view.setTitle("Bloc de notas");
                view.setVisible(true);

                Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(("res/icon.png")));
                view.setIconImage(icon);
            }
        });
    }
}