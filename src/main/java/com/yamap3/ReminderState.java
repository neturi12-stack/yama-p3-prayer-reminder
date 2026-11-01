package com.yamap3;

import javax.inject.Singleton;

@Singleton
public class ReminderState
{
    // Swing writes on the EDT; RuneLite renders on its client thread.
    private volatile String reminder;

    public void selectRange() { reminder = "PRAY MAGE"; }
    public void selectMage() { reminder = "PRAY RANGE"; }
    public void clear() { reminder = null; }
    public String getReminder() { return reminder; }
}
