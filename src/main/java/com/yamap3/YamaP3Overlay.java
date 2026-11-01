package com.yamap3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class YamaP3Overlay extends Overlay
{
    private static final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 26);
    private final ReminderState state;
    private final Client client;

    @Inject
    public YamaP3Overlay(ReminderState state, Client client)
    {
        this.state = state;
        this.client = client;
        setPosition(OverlayPosition.TOP_LEFT);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        setMovable(true);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        String text = state.getReminder();
        if (text == null || client.getGameState() != GameState.LOGGED_IN)
        {
            return null;
        }
        Graphics2D g = (Graphics2D) graphics.create();
        try
        {
            g.setFont(FONT);
            FontMetrics metrics = g.getFontMetrics();
            int width = metrics.stringWidth(text) + 28;
            int height = metrics.getHeight() + 20;
            g.setColor(new Color(15, 15, 15, 220));
            g.fillRoundRect(0, 0, width, height, 12, 12);
            g.setColor(text.equals("PRAY MAGE") ? new Color(100, 200, 255) : new Color(120, 255, 140));
            g.drawRoundRect(0, 0, width - 1, height - 1, 12, 12);
            g.drawString(text, 14, 10 + metrics.getAscent());
            return new Dimension(width, height);
        }
        finally
        {
            g.dispose();
        }
    }
}
