package net.lucent.easygui.interfaces.events;

public interface Clickable extends Hoverable {

    void onClick(double mouseX,double mouseY,int button,boolean clicked);
}