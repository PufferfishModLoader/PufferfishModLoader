package me.dreamhopping.pml.api.client.gui;

import java.util.List;

public abstract class FontRenderer {
    private static FontRenderer impl;

    public static FontRenderer getInstance() {
        return impl;
    }

    public static void setInstance(FontRenderer instance) {
        if (impl != null) throw new IllegalStateException("Instance already set");
        impl = instance;
    }

    public abstract int drawString(String text, int x, int y, int color);

    public abstract int drawString(String text, float x, float y, int color, boolean drawShadow);

    public abstract int drawStringWithShadow(String text, float x, float y, int color);

    public abstract int getStringWidth(String string);

    public abstract int getCharWidth(char character);

    public abstract String trimStringToWidth(String string, int width);

    public abstract String trimStringToWidth(String string, int width, boolean var2);

    public abstract void drawSplitString(String var0, int var1, int var2, int var3, int var4);

    public abstract int splitStringWidth(String var0, int var1);

    public abstract void setUnicodeFlag(boolean var0);

    public abstract boolean getUnicodeFlag();

    public abstract List<String> listFormattedStringToWidth(String var0, int var1);
}
