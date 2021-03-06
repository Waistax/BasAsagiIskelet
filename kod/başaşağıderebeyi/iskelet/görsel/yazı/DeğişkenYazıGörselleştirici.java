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

import org.lwjgl.*;

public class DeğişkenYazıGörselleştirici {
	private final Gölgelendirici gölgelendiricisi;
	private final Dizey4 izdüşümDizeyi;
	private final SıralıOluşumluKöşeDizisi köşeDizisi;
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
			new SıralıOluşumluKöşeDizisi(yükleyici, GL_TRIANGLES, sığası, 28);
		oluşumluKöşeDizisiniOluştur();
		this.sığası = sığası;
		this.şekli = şekli;
	}
	
	public void sesEkle(char ses, Yöney4 rengi, Dönüşüm dönüşümü) {
		if (sığası == çizilmişSesSayısı)
			return;
		çizilmişSesSayısı++;
		
		SesŞekli sesŞekli = şekli.sesininŞekliniEdin(ses);
		köşeDizisi.yazılacakVerisi
			.put(sesŞekli.solAltDokuKonumu.birinciBileşeni)
			.put(sesŞekli.solAltDokuKonumu.ikinciBileşeni)
			.put(sesŞekli.sağAltDokuKonumu.birinciBileşeni)
			.put(sesŞekli.sağAltDokuKonumu.ikinciBileşeni)
			.put(sesŞekli.solÜstDokuKonumu.birinciBileşeni)
			.put(sesŞekli.solÜstDokuKonumu.ikinciBileşeni)
			.put(sesŞekli.sağÜstDokuKonumu.birinciBileşeni)
			.put(sesŞekli.sağÜstDokuKonumu.ikinciBileşeni)
			.put(rengi.birinciBileşeni)
			.put(rengi.ikinciBileşeni)
			.put(rengi.üçüncüBileşeni)
			.put(rengi.dördüncüBileşeni)
			.put(dönüşümü.dizeyi.girdileri);
	}
	
	public void çiz() {
		köşeDizisi.tamponunuGüncelle();
		gölgelendiricisi.bağla();
		şekli.bağla();
		köşeDizisi.çiz();
		şekli.kopar();
		gölgelendiricisi.kopar();
		çizilmişSesSayısı = 0;
	}
	
	private void oluşumluKöşeDizisiniOluştur() {
		köşeDizisi
			.durağanKöşeTamponuNesnesiEkle(
				4,
				BufferUtils
					.createFloatBuffer(4 * 4)
					.put(-0.5F)
					.put(-0.5F)
					.put(0.0F)
					.put(1.0F)
					.put(+0.5F)
					.put(-0.5F)
					.put(0.0F)
					.put(1.0F)
					.put(-0.5F)
					.put(+0.5F)
					.put(0.0F)
					.put(1.0F)
					.put(+0.5F)
					.put(+0.5F)
					.put(0.0F)
					.put(1.0F)
					.flip());
		
		köşeDizisi
			.sıraTamponuNesnesiYükle(
				BufferUtils
					.createIntBuffer(6)
					.put(0)
					.put(1)
					.put(2)
					.put(2)
					.put(1)
					.put(3)
					.flip());
		
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(2);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(2);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(2);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(2);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(4);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(4);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(4);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(4);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(4);
	}
}
