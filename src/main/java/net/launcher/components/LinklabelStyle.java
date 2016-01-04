package net.launcher.components;

import net.launcher.utils.BaseUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.Map;

public class LinklabelStyle {
    public int x = 0;
    public int y = 0;
    public int w = 0;
    public int h = 0;
    public int margin = 0;
    public String fontName = BaseUtils.empty;
    public float fontSize = 1F;
    public Color idleColor;
    public Color activeColor;
    public Align align;

    public LinklabelStyle(int x, int y, int w, int h, String fontName, float fontSize, Color idleColor, Color activeColor, Align align) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.fontName = fontName;
        this.fontSize = fontSize;
        this.idleColor = idleColor;
        this.activeColor = activeColor;
        this.align = align;
    }

    public LinklabelStyle(int x, int y, int margin, String fontName, float fontSize, Color idleColor, Color activeColor) {
        this.x = x;
        this.y = y;
        this.fontName = fontName;
        this.fontSize = fontSize;
        this.idleColor = idleColor;
        this.activeColor = activeColor;
    }

    public void apply(LinkLabel link) {
        link.setBounds(x, y, w, h);
        link.idleColor = idleColor;
        link.activeColor = activeColor;
        link.setFont(BaseUtils.getFont(fontName, fontSize));
        Font font = link.getFont();
        Map attributes = link.getFont().getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        link.setFont(font.deriveFont(attributes));
        if (null != align) {
            link.setHorizontalAlignment(align == Align.LEFT ? SwingConstants.LEFT : align == Align.CENTER ? SwingConstants.CENTER : SwingConstants.RIGHT);
        }
    }
}