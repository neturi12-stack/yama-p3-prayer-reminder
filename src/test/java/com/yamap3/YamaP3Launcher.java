package com.yamap3;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class YamaP3Launcher
{
    public static void main(String[] args) throws Exception
    {
        ExternalPluginManager.loadBuiltin(YamaP3PrayerReminderPlugin.class);
        RuneLite.main(args);
    }
}
