/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.7 / 7 Mar 2021 / 15:29:21
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.görsel.görüntü.*;

import java.util.*;

/** Değişmeyen yazıları tek bir gölgelendirici ile çizen araç. */
public class DurağanYazıGörselleştirici {
	private final List<DurağanYazı> yazdıkları;
	private final Gölgelendirici gölgelendiricisi;
	
	/** Verilenler ile tanımlar. */
	public DurağanYazıGörselleştirici(
		final Gölgelendirici gölgelendiricisi,
		final İzdüşüm izdüşümü) {
		yazdıkları = new ArrayList<>();
		this.gölgelendiricisi = gölgelendiricisi;
		gölgelendiricisiniKur(izdüşümü);
	}
	
	/** Verilen durağan yazıyı çizmek için ekler. */
	public void ekle(final DurağanYazı yazı) {
		yazdıkları.add(yazı);
	}
	
	/** Durağan yazıları verilen bakışa göre çizer. */
	public void çiz(final Bakış bakış) {
		gölgelendiricisi.bağla();
		bakış.yükle(gölgelendiricisi);
		for (DurağanYazı yazı : yazdıkları)
			yazı.çiz(gölgelendiricisi);
		gölgelendiricisi.kopar();
	}
	
	private void gölgelendiricisiniKur(final İzdüşüm izdüşümü) {
		gölgelendiricisi.bağla();
		İzdüşüm.değerlerininKonumlarınıBul(gölgelendiricisi);
		Bakış.değerlerininKonumlarınıBul(gölgelendiricisi);
		Materyal.değerlerininKonumlarınıBul(gölgelendiricisi);
		izdüşümü.yükle(gölgelendiricisi);
		gölgelendiricisi.kopar();
	}
}
