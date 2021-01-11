package me.dreamhopping.pml.impl;

import me.dreamhopping.pml.api.client.gui.FontRenderer;
import net.minecraft.client.Minecraft;

import java.util.List;

public class FontRendererImpl extends FontRenderer {
    public int drawString(String text, int x, int y, int color) {
        return Minecraft.getMinecraft().fontRendererObj.drawString(text, x, y, color);
    }

    public int drawString(String text, float x, float y, int color, boolean drawShadow) {
        return Minecraft.getMinecraft().fontRendererObj.drawString(text, x, y, color, drawShadow);
    }

    public int drawStringWithShadow(String text, float x, float y, int color) {
        return Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(text, x, y, color);
    }

    public void drawSplitString(String text, int x, int y, int width, int color) {
        Minecraft.getMinecraft().fontRendererObj.drawSplitString(text, x, y, color, width);
    }

    public boolean getUnicodeFlag() {
        return Minecraft.getMinecraft().fontRendererObj.getUnicodeFlag();
    }

    public void setUnicodeFlag(boolean unicodeFlag) {
        Minecraft.getMinecraft().fontRendererObj.setUnicodeFlag(unicodeFlag);
    }

    public int getStringWidth(String string) {
        return Minecraft.getMinecraft().fontRendererObj.getStringWidth(string);
    }

    public int getCharWidth(char character) {
        return Minecraft.getMinecraft().fontRendererObj.getCharWidth(character);
    }

    public int splitStringWidth(String string, int width) {
        return Minecraft.getMinecraft().fontRendererObj.splitStringWidth(string, width);
    }

    public List<String> listFormattedStringToWidth(String string, int width) {
        return Minecraft.getMinecraft().fontRendererObj.listFormattedStringToWidth(string, width);
    }

    public String trimStringToWidth(String string, int width) {
        return Minecraft.getMinecraft().fontRendererObj.trimStringToWidth(string, width);
    }

    public String trimStringToWidth(String string, int width, boolean var2) {
        return Minecraft.getMinecraft().fontRendererObj.trimStringToWidth(string, width, var2);
    }
}
