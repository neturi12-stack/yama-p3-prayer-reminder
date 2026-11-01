package com.yamap3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import net.runelite.api.SpriteID;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.PluginPanel;

public class YamaP3Panel extends PluginPanel
{
    public YamaP3Panel(ReminderState state, SpriteManager sprites)
    {
        setLayout(new BorderLayout(0, 12));
        setBorder(BorderFactory.createEmptyBorder(12, 8, 12, 8));
        add(new JLabel("<html><center>Yama P3 Prayer Reminder<br><br>Choose your P2 prayer:</center></html>",
            SwingConstants.CENTER), BorderLayout.NORTH);
        JPanel controls = new JPanel();
        controls.setLayout(new javax.swing.BoxLayout(controls, javax.swing.BoxLayout.Y_AXIS));
        JPanel prayers = new JPanel(new GridLayout(0, 1, 0, 12));
        JLabel status = new JLabel("No reminder", SwingConstants.CENTER);
        PrayerButton range = new PrayerButton("RANGE", new Color(120, 225, 145));
        PrayerButton mage = new PrayerButton("MAGE", new Color(100, 190, 255));
        range.setToolTipText("Used Range in P2: show PRAY MAGE");
        mage.setToolTipText("Used Mage in P2: show PRAY RANGE");
        sprites.getSpriteAsync(SpriteID.PRAYER_PROTECT_FROM_MISSILES, 0,
            image -> SwingUtilities.invokeLater(() -> range.setPrayerImage(image)));
        sprites.getSpriteAsync(SpriteID.PRAYER_PROTECT_FROM_MAGIC, 0,
            image -> SwingUtilities.invokeLater(() -> mage.setPrayerImage(image)));
        JButton clear = new JButton("Clear Reminder");
        range.addActionListener(e -> {
            state.selectRange(); range.setSelected(true); mage.setSelected(false);
            status.setText(state.getReminder());
        });
        mage.addActionListener(e -> {
            state.selectMage(); mage.setSelected(true); range.setSelected(false);
            status.setText(state.getReminder());
        });
        clear.addActionListener(e -> {
            state.clear(); range.setSelected(false); mage.setSelected(false);
            status.setText("No reminder");
        });
        prayers.add(range);
        prayers.add(mage);
        prayers.setMaximumSize(new Dimension(Integer.MAX_VALUE, 276));
        controls.add(prayers);
        controls.add(javax.swing.Box.createVerticalStrut(12));
        clear.setAlignmentX(CENTER_ALIGNMENT);
        clear.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        controls.add(clear);
        controls.add(javax.swing.Box.createVerticalStrut(10));
        status.setAlignmentX(CENTER_ALIGNMENT);
        controls.add(status);
        JPanel body = new JPanel(new BorderLayout());
        body.add(controls, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
        add(new JLabel("<html>Hold Alt and drag the visible<br>in-game reminder to move it.</html>"), BorderLayout.SOUTH);
    }

    private static class PrayerButton extends JButton
    {
        private final Color accent;
        private BufferedImage image;

        PrayerButton(String text, Color accent)
        {
            super(text);
            this.accent = accent;
            setPreferredSize(new Dimension(200, 132));
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setRolloverEnabled(true);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            getAccessibleContext().setAccessibleName(text + " P2 prayer");
        }

        void setPrayerImage(BufferedImage image)
        {
            this.image = image;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics graphics)
        {
            Graphics2D g = (Graphics2D) graphics.create();
            try
            {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth();
                int h = getHeight();
                g.setColor(getModel().isPressed() ? new Color(22, 29, 35)
                    : getModel().isRollover() ? new Color(56, 66, 76) : new Color(38, 43, 50));
                g.fillRoundRect(1, 1, w - 2, h - 2, 16, 16);
                if (image != null)
                {
                    double scale = Math.min(68.0 / image.getWidth(), 68.0 / image.getHeight());
                    int iw = (int) (image.getWidth() * scale);
                    int ih = (int) (image.getHeight() * scale);
                    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                    g.drawImage(image, (w - iw) / 2, 12 + (68 - ih) / 2, iw, ih, null);
                }
                // The label is painted over a dark band within the image button.
                g.setColor(new Color(0, 0, 0, 115));
                g.fillRect(3, h - 43, w - 6, 38);
                g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 21));
                FontMetrics fm = g.getFontMetrics();
                g.setColor(accent);
                g.drawString(getText(), (w - fm.stringWidth(getText())) / 2, h - 15);
                g.setColor(isSelected() || getModel().isRollover() || isFocusOwner() ? accent : new Color(85, 91, 101));
                g.setStroke(new java.awt.BasicStroke(isSelected() || isFocusOwner() ? 3f : 1f));
                g.drawRoundRect(2, 2, w - 5, h - 5, 16, 16);
            }
            finally
            {
                g.dispose();
            }
        }
    }
}
