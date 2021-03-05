/**
 * başaşağıderebeyi.iskelet.görsel.Görselleştirici.java
 * 0.4 / 4 Mar 2021 / 17:47:03
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.görsel;

import static org.lwjgl.opengl.GL11.*;

import başaşağıderebeyi.kütüphane.matematik.*;

import java.util.*;

import org.lwjgl.*;

/** Verilen dönüşümlerin belirlediği iki boyutlu nesneleri ekrana çizer. */
public class Görselleştirici {
	private final Gölgelendirici gölgelendiricisi;
	private final Dizey4 izdüşümDizeyi;
	private final OluşumluKöşeDizisi köşeDizisi;
	private final int sığası;
	private final int dokusu;
	
	private final Set<Dönüşüm> çizilecekDönüşümleri;
	private final HashMap<Dönüşüm, Dönüşüm> eskiDönüşümleri;
	private final HashMap<Dönüşüm, Dönüşüm> kullanılacakDönüşümleri;
	
	/** Boş görselleştirici tanımlar. */
	public Görselleştirici(
		final Yükleyici yükleyici,
		final float uzayınGenişliği,
		final float uzayınYüksekliği,
		final float uzayınDerinliği,
		final int sığası,
		final String dokusununAdı) {
		gölgelendiricisi = new Gölgelendirici(yükleyici, "dokuluDikdörtgen");
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
			new OluşumluKöşeDizisi(yükleyici, GL_TRIANGLE_STRIP, 4, sığası, 16);
		oluşumluKöşeDizisiniOluştur();
		
		this.sığası = sığası;
		dokusu = yükleyici.dokuYükle(dokusununAdı);
		çizilecekDönüşümleri = new HashSet<>();
		eskiDönüşümleri = new HashMap<>();
		kullanılacakDönüşümleri = new HashMap<>();
	}
	
	/** Eğer yer varsa verilen dönüşümü çizilecekler kümesine ekler. */
	public void dönüşümüEkle(final Dönüşüm dönüşüm) {
		if (çizilecekDönüşümleri.size() >= sığası)
			return;
		çizilecekDönüşümleri.add(dönüşüm);
		eskiDönüşümleri.put(dönüşüm, new Dönüşüm());
		kullanılacakDönüşümleri.put(dönüşüm, new Dönüşüm());
	}
	
	/** Eğer verilen dönüşüm çizilecekler kümesindeyse çıkarır. */
	public void dönüşümünüÇıkar(final Dönüşüm dönüşümü) {
		çizilecekDönüşümleri.remove(dönüşümü);
		eskiDönüşümleri.remove(dönüşümü);
		kullanılacakDönüşümleri.remove(dönüşümü);
	}
	
	/** Dönüşümlerin anlık verilerini saklar. Böylece çizim yaparken
	 * aradeğerlerini bulup onu çizer. */
	public void güncelle() {
		çizilecekDönüşümleri
			.stream()
			.parallel()
			.forEach(
				dönüşümü -> eskiDönüşümleri.get(dönüşümü).değiştir(dönüşümü));
	}
	
	/** Dönüşümlerin verilen uzaklığa göre aradeğerlerini çizer. */
	public void çiz(final float uzaklık) {
		oluşumluKöşeDizisiniGüncelle(uzaklık);
		gölgelendiricisi.bağla();
		glBindTexture(GL_TEXTURE_2D, dokusu);
		köşeDizisi.çiz();
		glBindTexture(GL_TEXTURE_2D, 0);
		gölgelendiricisi.kopar();
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
			.durağanKöşeTamponuNesnesiEkle(
				2,
				BufferUtils
					.createFloatBuffer(4 * 4)
					.put(0.0F)
					.put(1.0F)
					.put(1.0F)
					.put(1.0F)
					.put(0.0F)
					.put(0.0F)
					.put(1.0F)
					.put(0.0F)
					.flip());
		
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(4);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(4);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(4);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(4);
	}
	
	private void oluşumluKöşeDizisiniGüncelle(final float uzaklık) {
		çizilecekDönüşümleri
			.stream()
			.parallel()
			.forEach(
				dönüşümü -> kullanılacakDönüşümleri
					.get(dönüşümü)
					.aradeğerleriniBul(
						dönüşümü,
						eskiDönüşümleri.get(dönüşümü),
						uzaklık));
		kullanılacakDönüşümleri
			.values()
			.stream()
			.parallel()
			.forEach(Dönüşüm::güncelle);
		kullanılacakDönüşümleri
			.values()
			.forEach(
				yeniDönüşümü -> köşeDizisi.yazılacakVerisi
					.put(yeniDönüşümü.dizeyi.girdileri));
		köşeDizisi.tamponunuGüncelle();
	}
}
