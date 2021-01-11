package me.dreamhopping.pml.impl;

import me.dreamhopping.pml.api.client.gui.FontRenderer;
import net.minecraft.client.MinecraftClient;

import java.util.List;

public class FontRendererImpl extends FontRenderer {
    public int drawString(String text, float x, float y, int color, boolean drawShadow) {
        if (!drawShadow) {
            return MinecraftClient.getInstance().textRenderer.draw(text, x, y, color);
        } else {
            return MinecraftClient.getInstance().textRenderer.drawWithShadow(text, x, y, color);
        }
    }

    public int drawString(String text, int x, int y, int color) {
        return MinecraftClient.getInstance().textRenderer.draw(text, x, y, color);
    }

    public int drawStringWithShadow(String text, float x, float y, int color) {
        return MinecraftClient.getInstance().textRenderer.drawWithShadow(text, x, y, color);
    }

    public void drawSplitString(String text, int x, int y, int width, int color) {
        MinecraftClient.getInstance().textRenderer.drawTrimmed(text, x, y, color, width);
    }

    public boolean getUnicodeFlag() {
        return true;
    }

    public void setUnicodeFlag(boolean unicodeFlag) {
        // Do nothing
    }

    public int getStringWidth(String string) {
        return MinecraftClient.getInstance().textRenderer.getStringWidth(string);
    }

    public int getCharWidth(char character) {
        if (character == 167) {
            return -1;
        } else if (character == ' ') {
            return 4;
        } else {
            int var1 = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000".indexOf(character);
            if (character > 0 && var1 != -1) {
                return (new int[256])[var1];
            } else if ((new byte[65536])[var1] != 0) {
                int var2 = (new byte[65536])[var1] & 255;
                int var3 = var2 >>> 4;
                int var4 = var2 & 15;
                ++var4;
                return (var4 - var3) / 2 + 1;
            } else {
                return 0;
            }
        }
    }

    public int splitStringWidth(String string, int width) {
        return MinecraftClient.getInstance().textRenderer.fontHeight * this.listFormattedStringToWidth(string, width).size();
    }

    public List<String> listFormattedStringToWidth(String string, int width) {
        return MinecraftClient.getInstance().textRenderer.wrapStringToWidthAsList(string, width);
    }

    public String trimStringToWidth(String string, int width) {
        return MinecraftClient.getInstance().textRenderer.trimToWidth(string, width);
    }

    public String trimStringToWidth(String string, int width, boolean var2) {
        return MinecraftClient.getInstance().textRenderer.trimToWidth(string, width, var2);
    }
}