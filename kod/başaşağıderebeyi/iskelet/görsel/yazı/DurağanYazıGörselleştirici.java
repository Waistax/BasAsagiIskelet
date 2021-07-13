/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.7 / 7 Mar 2021 / 15:29:21
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import static org.lwjgl.opengl.GL11.*;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.görsel.görüntü.*;
import başaşağıderebeyi.iskelet.görsel.köşedizisi.*;

public class DurağanYazıGörselleştirici {
	/** Yazdıklarının şekli. */
	public final YazıŞekli şekli;
	/** Yazdıklarının materyali. Bu materyalin dokusu yazı şeklinin dokusu ile
	 * aynı olmalıdır. Bu nesne ilk tanımlandığında materyalin dokusunu yazı
	 * şeklinin dokusu ile değiştirir. */
	public final Materyal materyali;
	/** Yazdıklarının dönüşümü. Bu dönüşüm ile durağan yazının yeri, konumu ve
	 * açısı değiştirilebilir. */
	public final Dönüşüm dönüşümü;
	
	private final Gölgelendirici gölgelendiricisi;
	private final SıralıKöşeDizisi köşeDizisi;
	
	/** Verilenler ile tanımlar. */
	public DurağanYazıGörselleştirici(
		final DurağanYazıOluşturucu oluşturucu,
		final Materyal materyali,
		final Dönüşüm dönüşümü,
		final Gölgelendirici gölgelendiricisi,
		final İzdüşüm izdüşümü) {
		
		şekli = oluşturucu.şekli;
		this.materyali = materyali;
		materyali.dokusu = şekli.dokusu;
		this.gölgelendiricisi = gölgelendiricisi;
		gölgelendiricisiniKur(izdüşümü);
		köşeDizisi = new SıralıKöşeDizisi(GL_TRIANGLES);
		köşeDizisi
			.sıraTamponuNesnesiYükle(oluşturucu.sırası)
			.durağanKöşeTamponuNesnesiEkle(2, oluşturucu.konumları)
			.durağanKöşeTamponuNesnesiEkle(2, oluşturucu.dokuKonumları);
		this.dönüşümü = dönüşümü;
	}
	
	/** Yerleşik yazıları çizer. */
	public void çiz(final Bakış bakış) {
		gölgelendiricisi.bağla();
		bakış.yükle(gölgelendiricisi);
		materyali.yükle(gölgelendiricisi);
		dönüşümü.yükle(gölgelendiricisi);
		köşeDizisi.çiz();
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
