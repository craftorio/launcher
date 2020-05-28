package net.launcher.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.launcher.run.Settings;
import net.launcher.theme.LoginTheme;
import net.launcher.theme.Message;
import net.launcher.utils.BaseUtils;
import net.launcher.utils.ImageUtils;
import net.launcher.utils.ThemeUtils;
import net.launcher.utils.ThreadUtils;

import static net.launcher.theme.LoginTheme.loginCurrent;
import static net.launcher.utils.BaseUtils.*;

import com.sun.awt.AWTUtilities;

public class Frame extends JFrame implements ActionListener, FocusListener {
    boolean b1 = false;
    boolean b2 = true;
    private static final long serialVersionUID = 1L;
    private static final Component Frame = null;
    public static String token = "null";
    public static boolean savetoken = false;

    public static Frame main;
    public Panel panel = new Panel(0);
    public Dragger dragger = new Dragger();
    public Title title = new Title();
    public static Button toGame = new Button(Message.Game);
    public static Avatar avatar = new Avatar();
    public static Button toAuth = new Button(Message.Auth);
    public static LinkLabel toHelp = new LinkLabel(Message.Help, Settings.toHelpUrl);
    public static ButtonAccount toAccount = new ButtonAccount(Message.Account, Settings.toAccountUrl);

    public static Button toLogout = new Button(Message.Logout);
    public static Button serverSelectBack = new Button(Message.Back);
    public static Button toPersonal = new Button(Message.Personal);
    public Button toOptions = new Button(Message.Options);
    public static Button toRegister = new Button(Message.Register);
    public JTextPane browser = new JTextPane();
    public JTextPane personalBrowser = new JTextPane();
//    public JScrollPane bpane = new JScrollPane(browser);
//    public JScrollPane personalBpane = new JScrollPane(personalBrowser);
    public static Textfield login = new Textfield();
    public static Passfield password = new Passfield();
    public Combobox servers = new Combobox(getServerNames(), 0);
    public Serverbar serverbar = new Serverbar();
    public ServerSelect serverselect = new ServerSelect(LoginTheme.serverSelectStyle);

    public LinkLabel[] links = new LinkLabel[Settings.links.length];

    public Dragbutton hide = new Dragbutton();
    public Dragbutton close = new Dragbutton();

    public Button update_exe = new Button(Message.update_exe);
    public Button update_jar = new Button(Message.update_jar);
    public Button update_no = new Button(Message.update_no);

    public Checkbox loadnews = new Checkbox(Message.loadnews);
    public Checkbox Music = new Checkbox(Message.Music);
    public Checkbox updatepr = new Checkbox(Message.updatepr);
    public Checkbox cleanDir = new Checkbox(Message.cleanDir);
    public Checkbox fullscreen = new Checkbox(Message.fullscreen);
    public Textfield memory = new Textfield();


    public Textfield loginReg = new Textfield();
    public Passfield passwordReg = new Passfield();
    public Passfield password2Reg = new Passfield();
    public Textfield mailReg = new Textfield();
    public Button okreg = new Button(Message.register);
    public Button closereg = new Button(Message.closereg);

    public Button options_close = new Button(Message.options_close);

    public Button buyCloak = new Button(Message.buyCloak);
    public Button changeSkin = new Button(Message.changeSkin);
    public Textfield vaucher = new Textfield();
    public Button vaucherButton = new Button(Message.vaucherButton);
    public Button buyVaucher = new Button(Message.buyVaucher);
    public Textfield exchangeFrom = new Textfield();
    public Textfield exchangeTo = new Textfield();
    public Button exchangeButton = new Button(Message.exchangeButton);
    public Button buyVip = new Button(BaseUtils.empty);
    public Button buyPremium = new Button(BaseUtils.empty);
    public Button buyUnban = new Button(Message.buyUnban);
    public Button toGamePersonal = new Button(Message.textloginReg1);
    public LinkLabel projectLink = new LinkLabel(Settings.projectLinkLabel, Settings.projectLinkUrl);



    //public static Button loginButton = new Button(Message.LoginBtn);
    //public static Button helpButton = new Button(Message.HelpBtn);

    public Frame() {
        try {
            ServerSocket socket = new ServerSocket(Integer.parseInt("65534"));
            Socket soc = new Socket(socket);
            soc.start();
        } catch (IOException var2) {
            JOptionPane.showMessageDialog(Frame, "Запуск второй копии лаунчера невозможен!", "Лаунчер уже запущен", javax.swing.JOptionPane.ERROR_MESSAGE);
            try {
                Class<?> af = Class.forName("java.lang.Shutdown");
                Method m = af.getDeclaredMethod("halt0", int.class);
                m.setAccessible(true);
                m.invoke(null, 1);
            } catch (Exception e) {
            }
        }

        //Подготовка окна
        setIconImage(BaseUtils.getLocalImage("favicon"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.DARK_GRAY);
        setForeground(Color.DARK_GRAY);

        // Disable border
        setLayout(new BorderLayout());
        setUndecorated(Settings.customframe && BaseUtils.getPlatform() != 0);

        if (isUndecorated()) {
            AWTUtilities.setWindowOpaque(this, false);
        }

        // Disable window resize
        setResizable(false);

        //for(int i = 0; i < links.length; i++)
        //{
        //String[] s = Settings.links[i].split("::");
        //links[i] = new LinkLabel(s[0], s[1]);
        //links[i].setEnabled(BaseUtils.checkLink(s[1]));
        //}

        try {
            ThemeUtils.updateStyle(this);
        } catch (Exception e) {
            e.printStackTrace();
        }


//        loginButton.addActionListener(this);
//        helpButton.addActionListener(this);

        // Add listeners
        toGame.addActionListener(this);
        toAuth.addActionListener(this);
        //toHelp.addActionListener(this);
        toLogout.addActionListener(this);
        serverSelectBack.addActionListener(this);
        toPersonal.addActionListener(this);
        toPersonal.setVisible(Settings.usePersonal);
        toOptions.addActionListener(this);
        toRegister.addActionListener(this);
        login.setPlaceholderText("Логин");
        login.addActionListener(this);
        //login.addFocusListener(this);
        password.setPlaceholderText("Введите пароль");

//        password.setEchoChar('*');
//        password.setEchoChar((char) 0);
//        password.setEchoChar('*');
//        passwordReg.setEchoChar('*');
//        password2Reg.setEchoChar('*');
        password.addActionListener(this);
        //password.addFocusListener(this);
        //Focus.setInitialFocus(this, password);
        String pass = getPropertyString("password");
        if (pass == null || pass.equals("-")) {
            b1 = true;
            b2 = false;
        }

        if (b2) {
            LoginTheme.loginCurrent.apply(net.launcher.components.Frame.login);
        } else {
            LoginTheme.login.apply(net.launcher.components.Frame.login);
        }
        login.setVisible(true);
        password.setVisible(b1);
        toGame.setVisible(b2);
        toAccount.setVisible(b2);
        toPersonal.setVisible(b2 && Settings.usePersonal);
        toAuth.setVisible(b1);
        toHelp.setVisible(true);
        toLogout.setVisible(b2);
        avatar.setVisible(b2);
        toRegister.setVisible(Settings.useRegister && b1);
        if (toGame.isVisible()) {
            token = "token";
        }

        login.setEditable(b1);
//        bpane.setOpaque(false);
//        bpane.getViewport().setOpaque(false);
//        if (Settings.drawTracers) {
//            bpane.setBorder(BorderFactory.createLineBorder(Color.black));
//        } else {
//            bpane.setBorder(null);
//        }
//
//        personalBpane.setOpaque(false);
//        personalBpane.getViewport().setOpaque(false);
//        personalBpane.setBorder(null);

//        personalBrowser.setOpaque(false);
//        personalBrowser.setBorder(null);
//        personalBrowser.setContentType("text/html");
//        personalBrowser.setEditable(false);
//        personalBrowser.setFocusable(false);
//        personalBrowser.addHyperlinkListener(new HyperlinkListener() {
//            public void hyperlinkUpdate(HyperlinkEvent e) {
//                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
//                    openURL(e.getURL().toString());
//                }
//            }
//        });

//        browser.setOpaque(false);
//        browser.setBorder(null);
//        browser.setContentType("text/html");
//        browser.setEditable(false);
//        browser.setFocusable(false);
//        browser.addHyperlinkListener(new HyperlinkListener() {
//            public void hyperlinkUpdate(HyperlinkEvent e) {
//                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
//                    if (Settings.useStandartWB)
//                        openURL(e.getURL().toString());
//                    else
//                        ThreadUtils.updateNewsPage(e.getURL().toString());
//                }
//            }
//        });
        hide.addActionListener(this);
        close.addActionListener(this);

//        update_exe.addActionListener(this);
        update_jar.addActionListener(this);
        update_no.addActionListener(this);
        servers.addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseClicked(MouseEvent e) {
                if (servers.getPressed() || e.getButton() != MouseEvent.BUTTON1)
                    return;

                ThreadUtils.pollSelectedServer();
                setProperty("server", servers.getSelectedIndex());
            }
        });

        options_close.addActionListener(this);
        closereg.addActionListener(this);
        okreg.addActionListener(this);
        loadnews.addActionListener(this);
        Music.addActionListener(this);
        fullscreen.addActionListener(this);

        buyCloak.addActionListener(this);
        changeSkin.addActionListener(this);
        vaucherButton.addActionListener(this);
        buyVaucher.addActionListener(this);
        exchangeButton.addActionListener(this);
        buyVip.addActionListener(this);
        buyPremium.addActionListener(this);
        buyUnban.addActionListener(this);
        toGamePersonal.addActionListener(this);

        login.setText(getPropertyString("login"));
        servers.setSelectedIndex(getPropertyInt("server"));

        exchangeFrom.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }

            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                try {
                    int i = Integer.parseInt(exchangeFrom.getText());
                    exchangeTo.setText(String.valueOf((long) i * (long) panel.pc.exchangeRate) + Message.exchange);
                } catch (Exception e) {
                    exchangeTo.setText(Message.exchangeTo);
                }
            }
        });

        addAuthComp();
        addFrameComp();
        add(panel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        validate();
        repaint();
        setVisible(true);
    }

    public void addFrameComp() {
        if (Settings.customframe) {
            panel.add(hide);
            panel.add(close);
            panel.add(dragger);
            panel.add(title);
        }
    }

    public void setAuthComp() {
        panel.type = 0;
        panel.timer.stop();
        panel.removeAll();
        addAuthComp();
        addFrameComp();
        repaint();
    }

    /**
     * Добавление элементов авторизации
     */
    public void addAuthComp() {
        panel.add(projectLink);
        //panel.add(servers);
        //panel.add(serverbar);
//        for (LinkLabel link : links)
//            panel.add(link);
        panel.add(toGame);
        panel.add(toAccount);
        panel.add(toAuth);
        panel.add(toHelp);
        panel.add(toLogout);
//        panel.add(toPersonal);
        panel.add(toOptions);
        panel.add(toRegister);
        panel.add(avatar);
        panel.add(login);
        panel.add(password);

//        panel.add(bpane);
    }

    //Старт программы
    public static void start() {
        try {
            send("****launcher****");
            try {
                send("Setting new LaF...");
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                send("Fail setting LaF");
            }
            send("Running debug methods...");

            new Runnable() {
                public void run() {
                    Settings.onStart();
                }
            }.run();

            main = new Frame();

            //ThreadUtils.updateNewsPage(buildUrl("news.php"));
            ThreadUtils.pollSelectedServer();
            ThreadUtils.pollServersStatus();
            try {
                main.memory.setText(String.valueOf(getPropertyIntMax("memory", Settings.defaultmemory)));
                main.fullscreen.setSelected(getPropertyBoolean("fullscreen"));
                main.loadnews.setSelected(getPropertyBoolean("loadnews", true));
                main.Music.setSelected(getPropertyBoolean("Music", true));
            } catch (Exception e) {
            }
        } catch (Exception e) {
            throwException(e, main);
        }
    }

    public static String jar;

    @SuppressWarnings("deprecation")
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == hide)
            setExtendedState(ICONIFIED);
        if (e.getSource() == close || e.getSource() == update_no)
            System.exit(0);

//        if (e.getSource() == update_exe) {
//            jar = ".exe";
//            new Thread() {
//                public void run() {
//                    try {
//                        panel.type = 8;
//                        update_exe.setEnabled(false);
//                        update_no.setText(Message.update_no2);
//                        panel.repaint();
//                        BaseUtils.updateLauncher();
//                    } catch (Exception e1) {
//                        e1.printStackTrace();
//                        send("Error updating launcher!");
//                        update_no.setText(Message.update_no);
//                        update_exe.setEnabled(true);
//                        panel.type = 9;
//                        panel.repaint();
//                    }
//                }
//            }.start();
//        }

        if (e.getSource() == update_jar) {
            jar = ".jar";
            new Thread() {
                public void run() {
                    try {
                        panel.type = 8;
                        update_jar.setEnabled(false);
                        update_no.setText(Message.update_no2);
                        panel.repaint();
                        BaseUtils.updateLauncher();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        send("Error updating launcher!");
                        update_no.setText(Message.update_no);
                        update_jar.setEnabled(true);
                        panel.type = 9;
                        panel.repaint();
                    }
                }
            }.start();
        }

        if (e.getSource() == serverSelectBack) {
            setAuthComp();
        }

        if (e.getSource() == toLogout) {
            setProperty("password", "-");
            setProperty("login", "");
            password.setVisible(true);
            toGame.setVisible(false);
            toAccount.setVisible(false);
            toPersonal.setVisible(false);
            toAuth.setVisible(true);
            toHelp.setVisible(true);
            toLogout.setVisible(false);
            avatar.setVisible(false);
            LoginTheme.login.apply(net.launcher.components.Frame.login);

            toRegister.setVisible(Settings.useRegister && true);
            token = "null";
            login.setText("");
            login.setEditable(true);
            login.paintInitialize();
            //login.setText("Логин...");
            password.setText("");
            password.paintInitialize();
            password.repaint();
            repaint();
        }

        if (e.getSource() == login || e.getSource() == password || e.getSource() == toAuth || e.getSource() == toPersonal || e.getSource() == toGamePersonal) {
//            if (e.getSource() == toAuth || e.getSource() == options_close) {
//                loginCurrent.apply(login);
//            }
            //loginCurrent.apply(login);
            toGame(e, servers.getSelectedIndex());
        } else {
            if (e.getSource() == toLogout) {
                LoginTheme.login.apply(login);
            }
        }

        if (e.getSource() == toOptions) {
            setOptions();
        }

        if (e.getSource() == toRegister) {
            setRegister();
        }

        if (e.getSource() == toGame) {
            setSelectServer();
            repaint();
        }

        if (e.getSource() == options_close) {
            if (!memory.getText().equals(getPropertyString("memory"))) {
                try {
                    int i = Integer.parseInt(memory.getText());
                    setProperty("memory", i);
                } catch (Exception e1) {
                }
                restart();
            }
            setAuthComp();
        }

        if (e.getSource() == fullscreen || e.getSource() == loadnews || e.getSource() == Music) {
            setProperty("fullscreen", fullscreen.isSelected());
            setProperty("loadnews", loadnews.isSelected());
            setProperty("Music", Music.isSelected());
        }

        if (e.getSource() == buyCloak) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new SkinFilter(1));
            chooser.setAcceptAllFileFilterUsed(false);
            int i = chooser.showDialog(main, Message.buyCloak);

            if (i == JFileChooser.APPROVE_OPTION) {
                setLoading();
                ThreadUtils.upload(chooser.getSelectedFile(), 1);
            }
        }

        if (e.getSource() == changeSkin) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new SkinFilter(0));
            chooser.setAcceptAllFileFilterUsed(false);
            int i = chooser.showDialog(main, Message.changeSkin);

            if (i == JFileChooser.APPROVE_OPTION) {
                setLoading();
                ThreadUtils.upload(chooser.getSelectedFile(), 0);
            }
        }

        if (e.getSource() == vaucherButton) {
            setLoading();
            ThreadUtils.vaucher(vaucher.getText());
        }

        if (e.getSource() == okreg) {
            setLoading();
            ThreadUtils.register(loginReg.getText(), passwordReg.getText(), password2Reg.getText(), mailReg.getText());
        }
        if (e.getSource() == closereg) {
            setAuthComp();
        }
        if (e.getSource() == buyVaucher) {
            openURL(Settings.buyVauncherLink);
        }

        if (e.getSource() == exchangeButton) {
            setLoading();
            ThreadUtils.exchange(exchangeFrom.getText());
        }

        if (e.getSource() == buyVip) {
            setLoading();
            ThreadUtils.buyVip(0);
        }

        if (e.getSource() == buyPremium) {
            setLoading();
            ThreadUtils.buyVip(1);
        }

        if (e.getSource() == buyUnban) {
            setLoading();
            ThreadUtils.unban();
        }
    }

    public void toGame(AWTEvent e, int serverIndex) {
        BaseUtils.setCurrentServer(serverIndex);
        boolean personal = false;
        if (e.getSource() == toPersonal) {
            personal = true;
        }
        setProperty("login", login.getText());
        setProperty("server", serverIndex);
        panel.remove(hide);
        panel.remove(close);
        BufferedImage screen = ImageUtils.sceenComponent(panel);
        panel.removeAll();
        panel.setAuthState(screen);
        ThreadUtils.auth(personal);
        addFrameComp();
    }

    public void focusGained(FocusEvent e) {
        if (e.getSource() == login && login.getText().equals(Message.Login))
            login.setText(empty);
    }

    public void focusLost(FocusEvent e) {
        if (e.getSource() == login && login.getText().equals(empty))
            login.setText(Message.Login);
    }

    public void setUpdateComp(String version) {
        panel.removeAll();
        panel.setUpdateState(version);
        //panel.add(update_exe);
        panel.add(update_jar);
        panel.add(update_no);
        addFrameComp();
        repaint();
    }

    public void setUpdateState() {
        panel.removeAll();
        panel.setUpdateStateMC();
        addFrameComp();
        repaint();
    }

    public void setRegister() {
        panel.remove(hide);
        panel.remove(close);
        BufferedImage screen = ImageUtils.sceenComponent(panel);
        panel.removeAll();
        panel.setRegister(screen);


        loginReg.setPlaceholderText("Логин (имя в игре)");
        passwordReg.setPlaceholderText("Пароль");
        password2Reg.setPlaceholderText("Подтвердить пароль");
        mailReg.setPlaceholderText("E-Mail");

        panel.add(avatar);
        panel.add(loginReg);
        panel.add(passwordReg);
        panel.add(password2Reg);
        panel.add(mailReg);
        panel.add(okreg);
        panel.add(closereg);
        addFrameComp();
        repaint();
    }

    public void setSelectServer() {
        panel.remove(hide);
        panel.remove(close);
        BufferedImage screen = ImageUtils.sceenComponent(panel);
        panel.removeAll();
        panel.setSelectServer(screen);
        panel.add(serverSelectBack);
        panel.add(serverselect);
        addFrameComp();
        repaint();
    }

    public void setOptions() {
        panel.remove(hide);
        panel.remove(close);
        BufferedImage screen = ImageUtils.sceenComponent(panel);
        panel.removeAll();
        panel.setOptions(screen);
        //panel.add(loadnews);
        //panel.add(Music);
        //panel.add(updatepr);
        //panel.add(cleanDir);
        panel.add(fullscreen);
        panel.add(memory);
        panel.add(options_close);
        addFrameComp();
        repaint();
    }

    public void setPersonal(PersonalContainer pc) {
        panel.removeAll();

        if (pc.canUploadCloak)
            panel.add(buyCloak);
        if (pc.canUploadSkin)
            panel.add(changeSkin);
        if (pc.canActivateVaucher) {
            panel.add(vaucher);
            panel.add(vaucherButton);
            panel.add(buyVaucher);
        }

        if (pc.canExchangeMoney) {
            panel.add(exchangeFrom);
            panel.add(exchangeTo);
            panel.add(exchangeButton);
        }

        if (pc.canBuyVip)
            panel.add(buyVip);
        if (pc.canBuyPremium)
            panel.add(buyPremium);

        if (pc.canBuyUnban)
            panel.add(buyUnban);

        buyVip.setText(Message.buyVip);
        buyVip.setEnabled(true);

        buyPremium.setText(Message.buyPremium);
        buyPremium.setEnabled(true);

        if (pc.ugroup.equals("Banned")) {
            buyPremium.setEnabled(false);
            buyVip.setEnabled(false);
        } else if (pc.ugroup.equals("VIP")) {
            buyVip.setText(Message.buyVipN);
            buyPremium.setEnabled(false);
            buyUnban.setEnabled(false);
        } else if (pc.ugroup.equals("Premium")) {
            buyPremium.setText(Message.buyPremiumN);
            buyVip.setEnabled(false);
            buyUnban.setEnabled(false);
        } else if (pc.ugroup.equals("User")) {
            buyUnban.setEnabled(false);
        }

        panel.add(toGamePersonal);

        panel.setPersonalState(pc);
        addFrameComp();
        repaint();
    }

    public void setLoading() {
        panel.remove(hide);
        panel.remove(close);
        BufferedImage screen = ImageUtils.sceenComponent(panel);
        panel.removeAll();
        panel.setLoadingState(screen, Message.Loading);
        addFrameComp();
    }

    public void setError(String s) {
        panel.removeAll();
        panel.setErrorState(s);
        addFrameComp();
    }
}