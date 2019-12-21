package net.launcher.components;

import net.launcher.run.Settings;
import net.launcher.utils.BaseUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Avatar extends JComponent {
    public BufferedImage avatar;
    public AvatarStyle at;
    public String prevLogin;

    public Avatar()
    {
        super();
        setOpaque(false);
        setFocusable(false);
    }


    protected void paintComponent(Graphics maing) {
        Graphics2D g = (Graphics2D) maing.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        String login = Frame.login.getText();
        if (null == avatar || !login.equals(prevLogin)) {
            if (null == login) {
                avatar = BaseUtils.getLocalImage("avatar");
            } else if (!login.equals(prevLogin)) {
                avatar = BaseUtils.getRemoteImage(
                        "https://craftorio.com/avatar.php?username=" + login + "&size=" + at.w,
                        "avatar"
                );
                prevLogin = login;
            }
        }
        g.drawImage(avatar, 0, 0, getWidth(), getHeight(), null);
        if (Settings.drawTracers) {
            g.setColor(Color.BLUE);
            g.drawRect(0, 0, getWidth()-1, getHeight()-1);
        }
        g.dispose();

        super.paintComponent(maing);
    }
}
