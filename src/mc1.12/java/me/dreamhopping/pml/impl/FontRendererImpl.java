package me.dreamhopping.pml.impl;

import me.dreamhopping.pml.api.client.gui.FontRenderer;
import net.minecraft.client.Minecraft;

import java.util.List;

public class FontRendererImpl extends FontRenderer {
    public int drawString(String text, int x, int y, int color) {
        return Minecraft.getMinecraft().fontRenderer.drawString(text, x, y, color);
    }

    public int drawString(String text, float x, float y, int color, boolean drawShadow) {
        return Minecraft.getMinecraft().fontRenderer.drawString(text, x, y, color, drawShadow);
    }

    public int drawStringWithShadow(String text, float x, float y, int color) {
        return Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(text, x, y, color);
    }

    public void drawSplitString(String text, int x, int y, int width, int color) {
        Minecraft.getMinecraft().fontRenderer.drawSplitString(text, x, y, color, width);
    }

    public boolean getUnicodeFlag() {
        return Minecraft.getMinecraft().fontRenderer.getUnicodeFlag();
    }

    public void setUnicodeFlag(boolean unicodeFlag) {
        Minecraft.getMinecraft().fontRenderer.setUnicodeFlag(unicodeFlag);
    }

    public int getStringWidth(String string) {
        return Minecraft.getMinecraft().fontRenderer.getStringWidth(string);
    }

    public int getCharWidth(char character) {
        return Minecraft.getMinecraft().fontRenderer.getCharWidth(character);
    }

    public int splitStringWidth(String string, int width) {
        return Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT * this.listFormattedStringToWidth(string, width).size();
    }

    public List<String> listFormattedStringToWidth(String string, int width) {
        return Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(string, width);
    }

    public String trimStringToWidth(String string, int width) {
        return Minecraft.getMinecraft().fontRenderer.trimStringToWidth(string, width);
    }

    public String trimStringToWidth(String string, int width, boolean var2) {
        return Minecraft.getMinecraft().fontRenderer.trimStringToWidth(string, width, var2);
    }
}
