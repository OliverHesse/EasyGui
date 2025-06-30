package net.lucent.easygui.elements.containers.panels;

import net.lucent.easygui.elements.inventory.DisplaySlot;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.minecraft.client.Minecraft;

public class InventoryPanel extends Panel{

    public InventoryPanel(){}

    public InventoryPanel(IEasyGuiScreen screen, int x, int y, int width,int height,int slots){

    }
    public void generateSlots(int slots){

        int slotsPerRow = width/16;

        for(int i=0;i<slots;i++){
            int row = (height/16);
            int column = i%slotsPerRow;
            DisplaySlot slot = new DisplaySlot(getScreen(),column*16,row*16,16,16,i, Minecraft.getInstance().player.getInventory());
        }
    }
}
