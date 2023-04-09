package com.newVer_V2.Client;

import javax.swing.*;
import java.awt.*;

public class DisplayPanel extends JPanel {
    JPanel chatFramePanel;
    String message;
    int align;

    public DisplayPanel(){}

    public DisplayPanel(String message , int align , JPanel chatFramePanel){
        this.message = message;
        this.align = align;
        this.chatFramePanel = chatFramePanel;
        setDisplayPanel();
    }

    public void setDisplayPanel(){
        JLabel msgLable = new JLabel(message , align);
        msgLable.setForeground(Color.BLACK);
        msgLable.setBackground(Color.cyan);
        msgLable.setSize(50 , 25);
        msgLable.setOpaque(true);

        JPanel itemPanel = new JPanel();
        itemPanel.setPreferredSize(new Dimension(660 , 25));
        itemPanel.add(msgLable);

        FlowLayout layout = (FlowLayout) itemPanel.getLayout();
        layout.setAlignment(align);
        chatFramePanel.add(itemPanel);
        chatFramePanel.updateUI();
    }
}
