package net.launcher.components;

import net.launcher.theme.Message;
import net.launcher.utils.BaseUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerSelect extends JComponent {
    private static final long serialVersionUID = 1L;

    protected BufferedImage serverOnlineImage = BaseUtils.getServerOnlineImage();
    protected BufferedImage serverOfflineImage = BaseUtils.getServerOfflineImage();

    protected int width = 0;
    protected int height = 0;

    protected int imageRightMargin = 25;
    protected int rowBottomMargin = 7;

    protected ArrayList<HashMap> servers = BaseUtils.getServersStatus();

    public int rowHeight = 0;
    public ServerSelectStyle sss;

    public ServerSelect(ServerSelectStyle style) {
        sss = style;
        Graphics2D g = serverOnlineImage.createGraphics();

        for (int i = 0; i < servers.size(); i++) {
            HashMap<String, String> server = servers.get(i);
            String text = String.format(Message.serverSelectRowOnline, server.get("name"), server.get("online"), server.get("max_online"));
            int itemWidth = sss.x + serverOnlineImage.getWidth() + imageRightMargin + g.getFontMetrics().stringWidth(text);
            int rowWidth =  Integer.max(itemWidth, sss.texture.getWidth());

            if (width < rowWidth) {
                width = rowWidth;
            }

            height += Integer.max(serverOnlineImage.getHeight(), sss.rowHeight);

            if (i < servers.size()-1) {
                height += rowBottomMargin;
            }
        }

        addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent arg0) {
            }

            public void mousePressed(MouseEvent arg0) {
            }

            public void mouseExited(MouseEvent arg0) {
                //setForeground(Color.WHITE);
            }

            public void mouseEntered(MouseEvent arg0) {
                //setForeground(Color.WHITE);
            }

            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    double index = Math.ceil(e.getY() / (rowHeight + rowBottomMargin));
                    if (e.getY() <= (((1 + index) * (rowHeight + rowBottomMargin)) - rowBottomMargin)) {
                        Frame.main.toGame(e, (int) index);
                    }
                }
            }
        });
    }

    protected void paintComponent(Graphics maing) {
        Graphics2D g = (Graphics2D) maing.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        BufferedImage serverOnlineImage = BaseUtils.getServerOnlineImage();
        BufferedImage serverOfflineImage = BaseUtils.getServerOfflineImage();

        int startX = 0;
        int startY = 0;
        int imageRightMargin = 12;
        int rowBottomMargin = 7;
        int totalWidth = 0;
        int totalHeight = 0;

        BufferedImage img;
        String text;
        Boolean serverOnline;

        for (int i = 0; i < servers.size(); i++) {
            HashMap<String, String> server = servers.get(i);
            if (null != server.get("online")) {
                serverOnline = true;
                img = serverOnlineImage;
                text = String.format(Message.serverSelectRowOnline, server.get("name"), server.get("online"), server.get("max_online"));
            } else {
                serverOnline = false;
                img = serverOfflineImage;
                text = String.format(Message.serverSelectRowOffline, server.get("name"));
            }

            if (serverOnline) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.GRAY);
            }
            //g.setColor(Color.YELLOW);
            //g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            int imgWith = 24;
            int imgHeight = 24;

            g.drawImage(sss.texture.getSubimage(0, 0, sss.texture.getWidth(), this.rowHeight), startX, startY, sss.texture.getWidth(), this.rowHeight, null);
            g.setColor(new Color(33,33,33));
            g.fillRoundRect(startX, startY, 308, this.rowHeight, 7, 7);
            g.setColor(new Color(99,99,99));
            g.drawRoundRect(startX, startY, 308, this.rowHeight, 7, 7);
            if (serverOnline) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.GRAY);
            }
            g.drawImage(img, startX + 12, startY + 12, imgWith, imgHeight, null);
            g.drawString(text, startX + 12 + imgWith + imageRightMargin, 3 + totalHeight + Integer.max(imgHeight, this.rowHeight) / 2 + g.getFontMetrics().getLineMetrics(text, g).getHeight() / 4);
            //startY += img.getHeight() + rowBottomMargin; // Shift down
            int itemWidth = imgWith + rowBottomMargin + g.getFontMetrics().stringWidth(text);
            int rowWidth = Integer.max(itemWidth, sss.texture.getWidth());
            int rowHeight = Integer.max(imgHeight, this.rowHeight);

            if (totalWidth < rowWidth) {
                totalWidth = rowWidth;
            }

            totalHeight += rowHeight;
            if (i < servers.size()-1) {
                totalHeight += rowBottomMargin;
            }
            startY = totalHeight;
        }

//        g.drawString(text, img.getWidth() + 7, img.getHeight() / 2 + g.getFontMetrics().getLineMetrics(text, g).getHeight() / 4 );
//        if (Settings.drawTracers) {
//            g.setColor(Color.YELLOW);
//            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
//        }
        g.dispose();
        super.paintComponent(maing);
    }
}
