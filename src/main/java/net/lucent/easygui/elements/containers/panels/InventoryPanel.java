package net.lucent.easygui.elements.containers.panels;

import net.lucent.easygui.EasyGui;
import net.lucent.easygui.elements.inventory.DisplaySlot;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.lucent.easygui.util.math.BoundChecker;
import net.lucent.easygui.util.textures.TextureData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;

public class InventoryPanel extends Panel{

    public ITextureData panelTexture = new TextureData(ResourceLocation.fromNamespaceAndPath(
            EasyGui.MOD_ID,
            "built_in_textures/inventory_panel.png"
    ),
            176,91);

    public BoundChecker.Vec2 hotBarStartPos = new BoundChecker.Vec2(7,66);
    public BoundChecker.Vec2 inventoryStartPos = new BoundChecker.Vec2(7,8);
    public InventoryPanel(IEasyGuiScreen screen, int x, int y,Container container){
        super(screen,x,y,0,0);

        generateSlots(container);


    }

    @Override
    public int getHeight() {
        return panelTexture.getTextureHeight();
    }

    @Override
    public int getWidth() {
        return panelTexture.getTextureWidth();
    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        panelTexture.renderTexture(guiGraphics);
    }

    public void generateSlots(Container container){

        int slotsPerRow = 9;
        //generate hotBar
        for(int i = 0; i<9;i++){
            DisplaySlot slot = new DisplaySlot(getScreen(),hotBarStartPos.x+i*18+1,hotBarStartPos.y+1,16,16,i, container,false);
            addChild(slot);
        }
        //generate inventory
        for(int i=0;i<27;i++){
            int row = i/9;
            int column = i%9;
            DisplaySlot slot = new DisplaySlot(getScreen(),inventoryStartPos.x+column*18+1,inventoryStartPos.y+row*18+1,16,16,i+9, container,false);
            addChild(slot);
        }

    }
}
