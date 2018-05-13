package com.pad.bgvi.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import com.pad.bgvi.common.RMIServerUtil;
import com.pad.bgvi.common.model.Article;
import com.pad.bgvi.common.rmi.Shop;

public class MyClient extends JFrame{

	private static final long serialVersionUID = 8681385533353229200L;
	JFrame frame;
    Article add;
    double total_price = 0;
    private static Shop shop;
    List<Article> articles = new ArrayList<Article>();
    ArrayList<Article_cos> articles_cos = new ArrayList<Article_cos>();
    JLabel price_total;
    HashMap<JPanel,Integer> panel_code = new HashMap<JPanel,Integer>();
    JPanel imbracaminte, incaltaminte, accesorii, cos;
    public MyClient(List<Article> art){
        this.articles = art;
        frame = new JFrame("MagazinSportiv");//creating instance of JFrame
        //Image icon  = Toolkit.getDefaultToolkit().getImage("D:\\minge.jpg");
        //frame.setIconImage(icon);
        //menu
        JTabbedPane jtp = new JTabbedPane();

        imbracaminte = new JPanel();
        imbracaminte.setLayout(null);
        incaltaminte = new JPanel();
        incaltaminte.setLayout(null);
        accesorii = new JPanel();
        accesorii.setLayout(null);
        cos = new JPanel();
        cos.setLayout(null);

        panel_code.put(imbracaminte,1);
        panel_code.put(incaltaminte,2);
        panel_code.put(accesorii,3);
        panel_code.put(cos,4);

        jtp.add("Imbracaminte", imbracaminte);
        jtp.add("Incaltaminte", incaltaminte);
        jtp.add("Accesorii", accesorii);
        jtp.add("Cos", cos);
        frame.add(jtp, BorderLayout.CENTER);

        addTab(imbracaminte);
        addTab(incaltaminte);
        addTab(accesorii);
        addTab(cos);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setSize(1000,800);//400 width and 500 height
        // frame.setLayout(null);//using no layout managers
        frame.setVisible(true);//making the frame visible
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public void addTab (JPanel panel){
        //title
        JLabel label = new JLabel("Magazin de articole sportive");
        label.setBounds(150,0,1000,50);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font(label.getName(), Font.PLAIN, 30));
        panel.add(label);
        int height = 0;
        for(Article a: this.articles) {
            if (a.getType() == panel_code.get(panel)) {
                addArticle(panel, a, height);
            }
        }

        if (panel_code.get(panel)==4){
            price_total = new JLabel("Total: " + total_price + "$");
            price_total.setBounds(1100, 120, 200, 30);
            price_total.setFont(new Font("Serif", Font.PLAIN, 30));
            panel.add(price_total);
        }

    }

    public void addArticle (JPanel panel, Article a, int height) {
        if(panel_code.get(panel) == 4){
            total_price = total_price + a.getPrice();
            price_total.setText("Total: " + total_price + "$");

        }

        //first box
        JLabel label1 = new JLabel();
        label1.setBounds(370, 150 + height, 1000, 200);
        Border border1 = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 5);
        label1.setBorder(border1);
        label1.setOpaque(true);
        label1.setBackground(Color.WHITE);

        JLabel label_title1 = new JLabel(a.getName());
        label_title1.setBounds(500, 170 + height, 400, 30);
        label_title1.setHorizontalAlignment(SwingConstants.CENTER);
        label_title1.setFont(new Font(label_title1.getName(), Font.PLAIN, 20));
        panel.add(label_title1);

        JLabel label_des1 = new JLabel(a.getDescription());
        label_des1.setBounds(460, 200 + height, 1000, 50);

        label_des1.setFont(new Font("Courier New", Font.PLAIN, 14));
        label_des1.setForeground(Color.GRAY);

        label_des1.setHorizontalAlignment(SwingConstants.CENTER);
        label_des1.setFont(new Font(label_des1.getName(), Font.PLAIN, 20));
        panel.add(label_des1);

        JLabel price_l = new JLabel(a.getPrice() + "$");
        price_l.setBounds(600, 280 + height, 200, 30);
        price_l.setFont(new Font("Serif", Font.PLAIN, 30));
        panel.add(price_l);

        JButton b, b_comanda;
        if (panel_code.get(panel) == 4){ //daca suntem in cos
            b = new JButton("Elimina");
            b_comanda = new JButton("Trimite comanda");

            //actiune la apasarea butonului
            b_comanda.addActionListener(new ActionListener() {
                
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "Comanda a fost inregistrata");
                    price_total.setText("Total: 0$");

                    List<Article> articles = new ArrayList<Article>();
                    for(Article_cos art: articles_cos){
                        //System.out.print(art);
                    	art.a.setQuantity(art.a.getQuantity()-1);
                    	articles.add(art.a);
                        remove(art.l1, art.l2, art.l3, art.l4, art.a.getPrice());
                    }
                    try {
						shop.buyArticles(articles);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
                    articles_cos = null;
                    //height = 0;
                }

            });

        } else {
            b = new JButton("Cumpara");
            b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "Adaugat in cos");
                }

            });

            b_comanda = new JButton("Vezi cosul");

        }
        b.setBounds(450,120,100,50);
        b.setBackground(Color.pink);
        b.setForeground(Color.white);
        label1.add(b);
        b.addActionListener(new MyHandler(this,a,label1, label_title1, label_des1, price_l, panel_code.get(panel)));

        b_comanda.setBounds(1100,50,200,50);
        b_comanda.setBackground(Color.lightGray);
        b_comanda.setForeground(Color.white);
        panel.add(b_comanda);


        label1.setVerticalTextPosition(SwingConstants.TOP);
        ImageIcon imageIcon = new ImageIcon(a.getImage());
        Image image = imageIcon.getImage();
        Image newimage = image.getScaledInstance(200,150, Image.SCALE_SMOOTH);
        ImageIcon myimage = new ImageIcon(newimage);
        label1.setIcon(myimage);
        panel.add(label1);

        if (panel_code.get(panel)==4) {
            Article_cos art = new Article_cos(a, label1, label_title1, label_des1, price_l);
            articles_cos.add(art);

        }
    }

    public void remove(JLabel p, JLabel p2, JLabel p3, JLabel p4, double price) {
        p.getParent().remove(p);
        p2.getParent().remove(p2);
        p3.getParent().remove(p3);
        p4.getParent().remove(p4);
        total_price = total_price - price;
        price_total.setText("Total: " + total_price + "$");
    }

    public static void main(String args[]) throws Exception {

    	Registry reg = LocateRegistry.getRegistry("192.168.43.94");
        shop = (Shop) reg.lookup(RMIServerUtil.URI);

        List<Article> articleList = shop.getArticles();
        MyClient myClient = new MyClient(articleList);
    }
    
}

class Article_cos{
    Article a;
    JLabel l1, l2, l3, l4;
    public Article_cos(Article a, JLabel l1, JLabel l2, JLabel l3, JLabel l4){
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
        this.l4 = l4;
        this.a = a;
    }

}

