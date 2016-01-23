package net.launcher.components;

import net.launcher.utils.BaseUtils;

import java.awt.event.MouseEvent;

public class ButtonAccount extends Button {
    public ButtonAccount(String text, String url) {
        super(text);
        this.url = url;
    }

    /**
     *
     * @param e
     */
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (null != url) {
                BaseUtils.openURL(url + "?session=" + BaseUtils.getPropertyString("session"));
            }
        }
    }
}