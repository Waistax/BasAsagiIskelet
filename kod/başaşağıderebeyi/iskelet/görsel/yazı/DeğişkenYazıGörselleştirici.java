/**
 * başaşağıderebeyi.iskelet.görsel.yazı.DeğişkenYazıGörselleştirici.java
 * 0.6 / 5 Mar 2021 / 21:59:54
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import static org.lwjgl.opengl.GL11.*;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.görsel.köşedizisi.*;
import başaşağıderebeyi.kütüphane.matematik.*;

public class DeğişkenYazıGörselleştirici {
	private final Gölgelendirici gölgelendiricisi;
	private final Dizey4 izdüşümDizeyi;
	private final OluşumluKöşeDizisi köşeDizisi;
	private final int sığası;
	private final YazıŞekli şekli;
	
	private int çizilmişSesSayısı;
	
	public DeğişkenYazıGörselleştirici(
		final Yükleyici yükleyici,
		final float uzayınGenişliği,
		final float uzayınYüksekliği,
		final float uzayınDerinliği,
		final int sığası,
		final YazıŞekli şekli) {
		gölgelendiricisi = new Gölgelendirici(yükleyici, "değişkenYazı");
		gölgelendiricisi.değerinKonumunuBul("izdusumDizeyi");
		
		izdüşümDizeyi = new Dizey4()
			.izdüşümDizeyineÇevir(
				uzayınGenişliği,
				uzayınYüksekliği,
				uzayınDerinliği);
		
		gölgelendiricisi.bağla();
		gölgelendiricisi.değeriDeğiştir("izdusumDizeyi", izdüşümDizeyi);
		gölgelendiricisi.kopar();
		
		köşeDizisi =
			new OluşumluKöşeDizisi(yükleyici, GL_TRIANGLES, 4, sığası, 16);
	}
}
