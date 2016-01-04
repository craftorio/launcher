package net.launcher.components;

import net.launcher.run.Settings;
import net.launcher.utils.BaseUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Serverbar extends JComponent {
    private static final long serialVersionUID = 1L;

    public BufferedImage img = BaseUtils.genServerIcon(new String[]{null, "0", null});
    public String text = BaseUtils.genServerStatus(new String[]{null, "0", null});

    public ServerbarStyle sb;

    protected void paintComponent(Graphics maing) {
        Graphics2D g = (Graphics2D) maing.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (sb != null && sb.useIcon) {
            g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
        }
        g.drawString(text, img.getWidth() + 7, img.getHeight() / 2 + g.getFontMetrics().getLineMetrics(text, g).getHeight() / 4 );
        if (Settings.drawTracers) {
            g.setColor(Color.YELLOW);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
        g.dispose();
        super.paintComponent(maing);
    }

    public void updateBar(String text, BufferedImage img) {
        this.text = text;
        this.img = img;
        repaint();
    }
}
