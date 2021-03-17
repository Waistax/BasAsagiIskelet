/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.4 / 4 Mar 2021 / 17:47:03
 */
package başaşağıderebeyi.iskelet.görsel;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import başaşağıderebeyi.iskelet.görsel.köşedizisi.*;
import başaşağıderebeyi.kütüphane.matematik.sayısal.*;

/** Verilen dönüşümlerin belirlediği iki boyutlu nesneleri ekrana çizer. */
public class Görselleştirici {
	private final Gölgelendirici gölgelendiricisi;
	private final SıralıOluşumluKöşeDizisi köşeDizisi;
	private final int sığası;
	private final int dokusu;
	
	private int çiziceklerininSayısı;
	
	/** Boş görselleştirici tanımlar. */
	public Görselleştirici(
		final Yükleyici yükleyici,
		final Dizey4 izdüşümDizeyi,
		final int sığası,
		final int dokusu) {
		gölgelendiricisi = new Gölgelendirici(yükleyici, "dokuluDikdörtgen");
		gölgelendiricisi.değerinKonumunuBul("izdusumDizeyi");
		
		gölgelendiricisi.bağla();
		gölgelendiricisi.değeriDeğiştir("izdusumDizeyi", izdüşümDizeyi);
		gölgelendiricisi.kopar();
		
		köşeDizisi =
			new SıralıOluşumluKöşeDizisi(yükleyici, GL_TRIANGLES, sığası, 16);
		oluşumluKöşeDizisiniOluştur();
		
		this.sığası = sığası;
		this.dokusu = dokusu;
	}
	
	/** Eğer yer varsa verilen dönüşümü çizilecekler kümesine ekler. */
	public void dönüşümüEkle(final Dönüşüm dönüşüm) {
		if (sığası < ++çiziceklerininSayısı)
			return;
		köşeDizisi.yazılacakVerisi.put(dönüşüm.dizeyi.girdileri);
	}
	
	/** Dönüşümlerin verilen uzaklığa göre aradeğerlerini çizer. */
	public void çiz(final float uzaklık) {
		köşeDizisi.tamponunuGüncelle();
		gölgelendiricisi.bağla();
		glBindTexture(GL_TEXTURE_2D, dokusu);
		köşeDizisi.çiz();
		glBindTexture(GL_TEXTURE_2D, 0);
		gölgelendiricisi.kopar();
		çiziceklerininSayısı = 0;
	}
	
	private void oluşumluKöşeDizisiniOluştur() {
		köşeDizisi
			.durağanKöşeTamponuNesnesiEkle(
				4,
				memAllocFloat(16)
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
					.put(1.0F));
		
		köşeDizisi
			.durağanKöşeTamponuNesnesiEkle(
				2,
				memAllocFloat(8)
					.put(0.0F)
					.put(1.0F)
					.put(1.0F)
					.put(1.0F)
					.put(0.0F)
					.put(0.0F)
					.put(1.0F)
					.put(0.0F));
		
		köşeDizisi
			.sıraTamponuNesnesiYükle(
				memAllocInt(6).put(0).put(1).put(2).put(2).put(1).put(3));
		
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(4);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(4);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(4);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(4);
	}
}
