/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.4 / 4 Mar 2021 / 17:47:03
 */
package başaşağıderebeyi.iskelet.görsel.görüntü;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.görsel.köşedizisi.*;

/** Belli bir gölgelendirici ve bakış ile görüntüleri çizen araç. */
public class Görselleştirici {
	private static final float[] KÖŞE_KONUMLARI =
		{ -0.5F, -0.5F, 0.5F, -0.5F, -0.5F, 0.5F, 0.5F, 0.5F };
	private static final float[] DOKU_KONUMLARI =
		{ 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F };
	private static final int[] KÖŞE_SIRASI = { 0, 1, 2, 2, 1, 3 };
	
	/** Kullanılacak gölgelendirici. */
	public final Gölgelendirici gölgelendiricisi;
	/** Yumuşatılmış bakışı. */
	public final YumuşakBakış bakışı;
	
	private final İzdüşüm izdüşümü;
	private final SıralıOluşumluKöşeDizisi köşeDizisi;
	private final int sığası;
	
	private int çiziceklerininSayısı;
	
	/** Boş görselleştirici tanımlar. */
	public Görselleştirici(
		final Gölgelendirici gölgelendiricisi,
		İzdüşüm izdüşümü,
		final int sığası) {
		this.gölgelendiricisi = gölgelendiricisi;
		this.izdüşümü = izdüşümü;
		bakışı = new YumuşakBakış();
		this.sığası = sığası;
		
		gölgelendiricisi.bağla();
		Bakış.değerlerininKonumlarınıBul(gölgelendiricisi);
		İzdüşüm.değerlerininKonumlarınıBul(gölgelendiricisi);
		izdüşümü.yükle(gölgelendiricisi);
		gölgelendiricisi.kopar();
		
		köşeDizisi =
			new SıralıOluşumluKöşeDizisi(GL_TRIANGLES, sığası, Dönüşüm.BOYUTU);
		oluşumluKöşeDizisiniOluştur();
	}
	
	/** Eğer yer varsa verilen görüntüyü çizmek için ekler. */
	public void ekle(final Görüntü görüntü) {
		if (sığası < ++çiziceklerininSayısı)
			return;
		görüntü.dönüşümü.çizilecekDönüşümü.yükle(köşeDizisi);
	}
	
	/** Dönüşümlerin verilen uzaklığa göre aradeğerlerini çizer. */
	public void çiz(final float uzaklık) {
		köşeDizisi.tamponunuGüncelle();
		gölgelendiricisi.bağla();
		bakışı.bul();
		bakışı.anlıkBakışı.yükle(gölgelendiricisi);
		köşeDizisi.çiz();
		gölgelendiricisi.kopar();
		çiziceklerininSayısı = 0;
	}
	
	private void oluşumluKöşeDizisiniOluştur() {
		köşeDizisi
			.durağanKöşeTamponuNesnesiEkle(
				KÖŞE_KONUMLARI.length / 4,
				memAllocFloat(KÖŞE_KONUMLARI.length).put(KÖŞE_KONUMLARI));
		köşeDizisi
			.durağanKöşeTamponuNesnesiEkle(
				DOKU_KONUMLARI.length / 4,
				memAllocFloat(DOKU_KONUMLARI.length).put(DOKU_KONUMLARI));
		köşeDizisi
			.sıraTamponuNesnesiYükle(
				memAllocInt(KÖŞE_SIRASI.length).put(KÖŞE_SIRASI));
		Dönüşüm.oluşumluKöşeDizisineEkle(köşeDizisi);
	}
}
