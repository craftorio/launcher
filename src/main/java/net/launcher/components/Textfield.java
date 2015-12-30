package net.launcher.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;

import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import net.launcher.run.Settings;
import net.launcher.utils.BaseUtils;
import net.launcher.utils.ImageUtils;

public class Textfield extends JTextField
        implements FocusListener
{
    private static final long serialVersionUID = 1L;
    private String placeholderText = "";
    private Color placeholderTextColor = Color.decode("0x777777");
    private Boolean isFirstPaint = true;

    public BufferedImage texture;

    public Textfield() {
        setOpaque(false);
        addFocusListener(this);
    }

    public void test(){}

    public void setPlaceholderText(String text) {
        placeholderText = text;
    }

    public void setPlaceholderTextColot(Color color) {
        placeholderTextColor = color;
    }

    protected void paintInitialize()
    {
        if (getText().isEmpty() && !placeholderText.isEmpty()) {
            setText(placeholderText);
            setForeground(placeholderTextColor);
        }
    }

    protected void paintComponent(Graphics maing) {
        Graphics2D g = (Graphics2D) maing.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //g.drawImage(ImageUtils.genButton(getWidth(), getHeight(), texture), 0, 0, getWidth(), getHeight(), null);
        g.drawImage(texture, 0, 0, getWidth(), getHeight(), null);
        if (Settings.drawTracers) {
            g.setColor(Color.PINK);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
        g.dispose();

        if (isFirstPaint) {
            isFirstPaint = false;
            paintInitialize();
        }

        super.paintComponent(maing);
    }

    public void focusGained(FocusEvent e) {
        if (getText().equals(placeholderText)) {
            setText("");
            setForeground(Color.decode("0xFFFFFF"));
        }
    }

    public void focusLost(FocusEvent e) {
        if (getText().isEmpty()) {
            setText(placeholderText);
            setForeground(placeholderTextColor);
        }
    }
}