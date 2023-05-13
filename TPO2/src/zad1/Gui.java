package zad1;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {

    private String city;
    private Service service;

    public Gui(){
        init();
    }

    private void init(){
        setTitle("ネットワーク サービス クライアント");
        setSize(new Dimension(640, 480));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        service = new Service(JOptionPane.showInputDialog("Provide country"));
        city = JOptionPane.showInputDialog("Provide city");

        setLayout(new BorderLayout());

        JButton jb1 = new JButton("Get weather");
        jb1.addActionListener(e -> JOptionPane.showMessageDialog(null, service.getWeather(city)));

        JButton jb2 = new JButton("Get rate with provided currency");
        jb2.addActionListener(e -> JOptionPane.showMessageDialog(null, service.getRateFor(JOptionPane.showInputDialog("Currency code (3 letters)"))));

        JButton jb3 = new JButton("Get NBP rate");
        jb3.addActionListener(e -> JOptionPane.showMessageDialog(null, service.getNBPRate()));

        JPanel jp = new JPanel();
        jp.add(jb1);
        jp.add(jb2);
        jp.add(jb3);

        add(jp, BorderLayout.NORTH);

        JFXPanel jfxPanel = new JFXPanel();
        add(jfxPanel);

        Platform.runLater(() -> {
            WebView webView = new WebView();
            jfxPanel.setScene(new Scene(webView));
            webView.getEngine().load("https://en.wikipedia.org/wiki/" + city);
        });


        setVisible(true);
    }
}
