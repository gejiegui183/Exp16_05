package com.company.Client;

import javax.swing.*;
import java.awt.*;

public interface Config {

    public static final int loginFrameWidth = 560;
    public static final int loginFrameHeight = 480;

    public static final int friendListWidth = 360;
    public static final int friendListHeight = 750;

    public static final int chatFrameWidht = 680;
    public static final int chatFrameHeight = 540;

    public static final int registerWidth = 560;
    public static final int registerHeight = 480;

    public static Image icon = new ImageIcon("./Image/QQ_64.png").getImage();
    public static Image trayIconImage = new ImageIcon("./Image/QQ_16.png").getImage();
}
