/**
 * başaşağıderebeyi.iskelet.görsel.yazı.DurağanYazıGörselleştirici.java
 * 0.7 / 7 Mar 2021 / 15:29:21
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.görsel.köşedizisi.*;
import başaşağıderebeyi.kütüphane.matematik.*;

import java.nio.*;

public class DurağanYazıGörselleştirici {
	private final Gölgelendirici gölgelendiricisi;
	private final SıralıKöşeDizisi köşeDizisi;
	private final YazıŞekli şekli;
	private final Yöney4 rengi;
	private final Dönüşüm dönüşümü;
	
	/** Verilenler ile tanımlar. */
	public DurağanYazıGörselleştirici(
		final Yükleyici yükleyici,
		final Dizey4 izdüşümDizeyi,
		final float saydamlıkEşiği,
		final DurağanYazıOluşturucu oluşturucu) {
		gölgelendiricisi = new Gölgelendirici(yükleyici, "durağanYazı");
		gölgelendiricisiniOluştur(izdüşümDizeyi, saydamlıkEşiği);
		
		şekli = oluşturucu.şekli;
		rengi = new Yöney4();
		dönüşümü = new Dönüşüm();
		
		köşeDizisi = new SıralıKöşeDizisi(yükleyici, GL_TRIANGLES);
		
		final FloatBuffer konumları =
			memAllocFloat(oluşturucu.konumları.size());
		oluşturucu.konumları.stream().forEachOrdered(konumları::put);
		köşeDizisi.durağanKöşeTamponuNesnesiEkle(4, konumları);
		
		final FloatBuffer dokuKonumları =
			memAllocFloat(oluşturucu.dokuKonumları.size());
		oluşturucu.dokuKonumları.stream().forEachOrdered(dokuKonumları::put);
		köşeDizisi.durağanKöşeTamponuNesnesiEkle(2, dokuKonumları);
		
		final IntBuffer sırası = memAllocInt(oluşturucu.sırası.size());
		oluşturucu.sırası.stream().forEachOrdered(sırası::put);
		köşeDizisi.sıraTamponuNesnesiYükle(sırası);
	}
	
	/** Yerleşik yazıları çizer. */
	public void çiz() {
		gölgelendiricisi.bağla();
		şekli.bağla();
		köşeDizisi.çiz();
		şekli.kopar();
		gölgelendiricisi.kopar();
	}
	
	/** Rengini döndürür. */
	public Yöney4 renginiEdin() {
		return rengi;
	}
	
	/** Rengini gölgelendiriciye yükler. */
	public void renginiGüncelle() {
		gölgelendiricisi.bağla();
		gölgelendiricisi.değeriDeğiştir("rengi", rengi);
		gölgelendiricisi.kopar();
	}
	
	public void konumunuDeğiştir(final float konumu, final float çizgisi) {
		dönüşümü.konumu.bileşenleriniDeğiştir(konumu, çizgisi, 0.0F);
	}
	
	/** Boyutunu değiştirir. */
	public void boyutunuDeğiştir(final float boyut) {
		dönüşümü.biçimi.bileşenleriniDeğiştir(boyut, boyut, 0.0F);
	}
	
	/** Dönüşümünü gölgelendiriciye yükler. */
	public void dönüşümünüGüncelle() {
		dönüşümü.güncelle();
		gölgelendiricisi.bağla();
		gölgelendiricisi.değeriDeğiştir("donusumu", dönüşümü.dizeyi);
		gölgelendiricisi.kopar();
	}
	
	private void gölgelendiricisiniOluştur(
		final Dizey4 izdüşümDizeyi,
		final float saydamlıkEşiği) {
		gölgelendiricisi.değerinKonumunuBul("izdusumDizeyi");
		gölgelendiricisi.değerinKonumunuBul("saydamlikEsigi");
		gölgelendiricisi.değerinKonumunuBul("donusumu");
		gölgelendiricisi.değerinKonumunuBul("rengi");
		
		gölgelendiricisi.bağla();
		gölgelendiricisi.değeriDeğiştir("izdusumDizeyi", izdüşümDizeyi);
		gölgelendiricisi.değeriDeğiştir("saydamlikEsigi", saydamlıkEşiği);
		gölgelendiricisi.kopar();
	}
}
