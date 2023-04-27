package com.newVer_V5.cam;

import com.github.sarxos.webcam.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CamStart extends JFrame implements Runnable, WebcamListener, WindowListener, Thread.UncaughtExceptionHandler, ItemListener, WebcamDiscoveryListener {
    JFrame videoFrame;
    WebcamPanel northPanel;
    JPanel southPanel;
    static Webcam webcam = null;
    WebcamPanel panel = null;
    WebcamPicker picker = null;
    String windowTitle;

    public CamStart(String windowTitle){
        this.windowTitle = windowTitle;
    }

    @Override
    public void run() {
//        getCam();
//        setWindow();
    }

    public void setWindow(){
        videoFrame = new JFrame(windowTitle);
        videoFrame.setSize(640 , 640);
        videoFrame.setResizable(false);
        videoFrame.addWindowListener(this);
        videoFrame.add(northPanel , BorderLayout.NORTH);

        southPanel = new JPanel();
        southPanel.setPreferredSize(new Dimension(640 , 120));
        JButton jb1 = new JButton("接通");
        JButton jb2 = new JButton("挂断");
        jb1.setBounds(30 , 100 , 70 , 30);
        jb2.setBounds(120 , 100 , 70 , 30);
        southPanel.add(jb1);
        southPanel.add(jb2);

        videoFrame.add(southPanel , BorderLayout.SOUTH);
        videoFrame.setLocationRelativeTo(null);
        videoFrame.setVisible(true);
        videoFrame.setDefaultCloseOperation(1);
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shutDownTip();
            }
        });
    }

    public void getCam(){
        Webcam.addDiscoveryListener(this);
        picker = new WebcamPicker();
        webcam = picker.getSelectedWebcam();
        if (webcam == null) {
            JFrame tip = new JFrame("提示");
            tip.setSize(300 , 200);
            tip.setLocationRelativeTo(null);
            JPanel panel1 = new JPanel();
            panel1.setPreferredSize(new Dimension(220 , 100));
            JLabel label = new JLabel("未在您的设备上找到摄像头，请检查连接情况");
            label.setSize(200 , 90);
            panel1.add(label);

            JPanel panel2 = new JPanel();
            panel2.setPreferredSize(new Dimension(220 , 35));
            JButton jb1 = new JButton("确定");
            jb1.setBounds(70 , 40 , 70 , 30);
            panel2.add(jb1);
            tip.add(panel1 , BorderLayout.NORTH);
            tip.add(panel2 , BorderLayout.SOUTH);
            tip.setDefaultCloseOperation(1);
            tip.setVisible(true);
            tip.setResizable(false);
            jb1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tip.dispose();
                    tip.setDefaultCloseOperation(1);
                }
            });
        }
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.addWebcamListener(CamStart.this);
        northPanel = new WebcamPanel(webcam , false);
        northPanel.setFPSDisplayed(true);
        Thread t = new Thread(){
            @Override
            public void run() {
                northPanel.start();
            }
        };
        t.setDaemon(true);
        t.setUncaughtExceptionHandler(this);
        t.start();
    }

    public void shutDownTip(){
        JFrame tipWindow = new JFrame();
        tipWindow.setTitle("系统提示");
        tipWindow.setSize(300 , 200);
        tipWindow.setLocationRelativeTo(null);
        JPanel panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(220 , 100));
        JLabel label = new JLabel("是否结束当前视频通信？");
        label.setSize(200 , 90);
        panel1.add(label);

        JPanel panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(220 , 35));
        JButton jb1 = new JButton("是");
        jb1.setBounds(70 , 40 , 70 , 30);
        JButton jb2 = new JButton("否");
        jb1.setBounds(160 , 40 , 20 , 30);
        panel2.add(jb1);
        panel2.add(jb2);
        tipWindow.add(panel1 , BorderLayout.NORTH);
        tipWindow.add(panel2 , BorderLayout.SOUTH);
        tipWindow.setDefaultCloseOperation(1);
        tipWindow.setVisible(true);
        tipWindow.setResizable(false);
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                webcam.close();
                videoFrame.dispose();
                tipWindow.dispose();
                videoFrame.setDefaultCloseOperation(1);
                tipWindow.setDefaultCloseOperation(1);
            }
        });
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tipWindow.dispose();
                tipWindow.setDefaultCloseOperation(1);
            }
        });
    }


    @Override
    public void webcamFound(WebcamDiscoveryEvent webcamDiscoveryEvent) {

    }

    @Override
    public void webcamGone(WebcamDiscoveryEvent webcamDiscoveryEvent) {

    }

    @Override
    public void webcamOpen(WebcamEvent webcamEvent) {

    }

    @Override
    public void webcamClosed(WebcamEvent webcamEvent) {

    }

    @Override
    public void webcamDisposed(WebcamEvent webcamEvent) {

    }

    @Override
    public void webcamImageObtained(WebcamEvent webcamEvent) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        webcam.close();
        this.setDefaultCloseOperation(1);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }
}