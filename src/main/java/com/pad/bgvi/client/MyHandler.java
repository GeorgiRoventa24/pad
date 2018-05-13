package com.pad.bgvi.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import com.pad.bgvi.common.model.Article;

class MyHandler implements ActionListener {
	public static int height = 0;
	public static int total_price = 0;
	private final JLabel p4, p3, p2, p;
	MyClient m;
	Article a;
	int code;

	public MyHandler(MyClient m, Article a, JLabel p1, JLabel p2, JLabel p3, JLabel p4, int code) {
		this.m = m;
		this.a = a;
		this.code = code;
		this.p = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
	}

	public void actionPerformed(ActionEvent evt) {
		if (this.code == 4) {
			a.setQuantity(a.getQuantity() + 1);
			m.remove(this.p, this.p2, this.p3, this.p4, a.getPrice());
			height = height - 250;

		} else {
			a.setQuantity(a.getQuantity() - 1);
			m.addArticle(m.cos, a, height);
			height = height + 250;
		}
	}
}