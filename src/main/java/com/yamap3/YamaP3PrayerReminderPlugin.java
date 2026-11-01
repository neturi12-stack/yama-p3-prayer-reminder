package com.yamap3;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import javax.swing.SwingUtilities;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(name = "Yama P3 Prayer Reminder",
    description = "Select your P2 prayer to display the opposite prayer as a manual reminder",
    tags = {"yama", "prayer", "reminder"})
public class YamaP3PrayerReminderPlugin extends Plugin
{
    @Inject private ClientToolbar toolbar;
    @Inject private net.runelite.client.game.SpriteManager sprites;
    @Inject private OverlayManager overlayManager;
    @Inject private YamaP3Overlay overlay;
    @Inject private ReminderState state;
    private NavigationButton navigation;

    @Override
    protected void startUp() throws Exception
    {
        state.clear();
        onUiThread(() -> {
            navigation = NavigationButton.builder()
                .tooltip("Yama P3 Prayer Reminder")
                .icon(createIcon())
                .priority(6)
                .panel(new YamaP3Panel(state, sprites))
                .build();
            toolbar.addNavigation(navigation);
        });
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception
    {
        state.clear();
        overlayManager.remove(overlay);
        onUiThread(() -> {
            if (navigation != null)
            {
                toolbar.removeNavigation(navigation);
                navigation = null;
            }
        });
    }

    private static void onUiThread(Runnable action) throws Exception
    {
        if (SwingUtilities.isEventDispatchThread())
        {
            action.run();
        }
        else
        {
            SwingUtilities.invokeAndWait(action);
        }
    }
    private BufferedImage createIcon()
    {
        BufferedImage icon = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = icon.createGraphics();
        g.setColor(new Color(100, 200, 255));
        g.fillRoundRect(1, 1, 22, 22, 6, 6);
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        g.drawString("Y", 6, 19);
        g.dispose();
        return icon;
    }
}


