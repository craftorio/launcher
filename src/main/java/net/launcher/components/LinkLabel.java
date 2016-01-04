package net.launcher.components;

import net.launcher.run.Settings;
import net.launcher.utils.BaseUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LinkLabel extends JLabel {
    private static final long serialVersionUID = 1L;

    public Color idleColor = Color.decode("0x999999");
    public Color activeColor = Color.WHITE;

    public LinkLabel(String title, final String url) {
        setText(title);
        setOpaque(false);
        setForeground(idleColor);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent arg0) {
            }

            public void mousePressed(MouseEvent arg0) {
            }

            public void mouseExited(MouseEvent arg0) {
                setForeground(idleColor);
            }

            public void mouseEntered(MouseEvent arg0) {
                setForeground(activeColor);
            }

            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1)
                    BaseUtils.openURL(url);
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (Settings.drawTracers) {
            g.setColor(Color.GRAY);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }
}