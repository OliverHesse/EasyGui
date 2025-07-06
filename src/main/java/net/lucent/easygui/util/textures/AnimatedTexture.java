package net.lucent.easygui.util.textures;

import com.mojang.datafixers.util.Pair;
import net.lucent.easygui.interfaces.ITextureData;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.List;

/**
 * holds a list of ITexture texture data with Pair(ITextureData,TimeToNextFrame)
 * time is in ticks. may change this in the future
 */
public class AnimatedTexture {
    public List<Pair<ITextureData,Integer>> frameData = new ArrayList<>();
    private int currentFrame;

    private double totalTimeElapsed;
    private double timeElapsed;
    private double timeSinceLastTick;

    private boolean paused = false;

    private final boolean loop;



    private AnimatedTexture(List<Pair<ITextureData,Integer>> frameData,boolean loop){
        this.frameData = frameData;
        this.loop = loop;

    }

    public boolean isFinished(){
        if(loop) return false;
        if(currentFrame >= frameData.size()) return true;
        return false;
    }

    public boolean isPaused() {return paused;}
    public void setPaused(boolean paused){this.paused = paused;}

    public int getCurrentFrame(){return currentFrame;}
    public void setCurrentFrame(int frame){
        currentFrame = frame;
    }
    public ITextureData getFrame(int frame){
        return frameData.get(frame%frameData.size()).getFirst();
    }


    public void render(GuiGraphics guiGraphics,int x, int y,double partialTick){
        if(isFinished()) return;
        if(isPaused()) return;
        timeSinceLastTick += partialTick;
        //less computationally intensive than rounding + conversion
        if(timeSinceLastTick >= 1){
            timeSinceLastTick = 0;
            totalTimeElapsed += 1;
            timeElapsed += 1;
        }

        getFrame(getCurrentFrame()).renderTexture(guiGraphics,x,y);

        if(timeElapsed == frameData.get(currentFrame).getSecond()){
            timeElapsed = 0;
            setCurrentFrame(getCurrentFrame()+1);
        }
    }

    public static class Builder{

        private static final List<Pair<ITextureData,Integer>>  frameData = new ArrayList<>();
        private static boolean loop = false;


        public Builder addFrame(ITextureData frame,int frameTime){
            frameData.add(new Pair<>(frame,frameTime));
            return this;
        }
        public Builder setLooping(boolean looping){
            loop = looping;
            return this;
        }

        public AnimatedTexture build(){
            return  new AnimatedTexture(frameData,loop);

        }
    }
}
