/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.4.4 / 13 Tem 2021 / 17:07:30
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import static org.lwjgl.opengl.GL11.*;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.görsel.görüntü.*;
import başaşağıderebeyi.iskelet.görsel.köşedizisi.*;
import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;

/** Durağan bir yazının köşelerini saklar. */
public class DurağanYazı implements Yumuşatılabilir {
	/** Yazının şekli. */
	public final YazıŞekli şekli;
	/** Yazının materyali. Bunun dokusu yazı şeklinin dokusuyla aynı
	 * olmalıdır. */
	public final Materyal materyali;
	/** Yazının sahnedeki dönüşümü. */
	public final Dönüşüm dönüşümü;
	/** Yazının köşeleri. */
	public final SıralıKöşeDizisi köşeDizisi;
	
	/** Verilen oluşturucudan temiz bir materyal ve dönüşümle tanımlar. */
	public DurağanYazı(final DurağanYazıOluşturucu oluşturucu) {
		şekli = oluşturucu.şekli;
		materyali =
			new Materyal(şekli.dokusu, new Yöney4(Yöney4.BİR), new Yöney4());
		dönüşümü = new Dönüşüm();
		köşeDizisi = new SıralıKöşeDizisi(GL_TRIANGLES);
		köşeDizisi
			.sıraTamponuNesnesiYükle(oluşturucu.sırası)
			.durağanKöşeTamponuNesnesiEkle(2, oluşturucu.konumları)
			.durağanKöşeTamponuNesnesiEkle(2, oluşturucu.dokuKonumları);
	}
	
	private DurağanYazı(final DurağanYazı öbürü) {
		şekli = öbürü.şekli;
		materyali = (Materyal)öbürü.materyali.çoğalt();
		dönüşümü = (Dönüşüm)öbürü.dönüşümü.çoğalt();
		köşeDizisi = öbürü.köşeDizisi;
	}
	
	/** Yazıyı çizer. */
	public void çiz(Gölgelendirici gölgelendirici) {
		materyali.yükle(gölgelendirici);
		dönüşümü.yükle(gölgelendirici);
		köşeDizisi.çiz();
	}
	
	@Override
	public Yumuşatılabilir aradeğerleriniBul(
		Yumuşatılabilir baştaki,
		Yumuşatılabilir sondaki,
		double uzunluk) {
		final DurağanYazı baştakiDurağanYazı = (DurağanYazı)baştaki;
		final DurağanYazı sondakiDurağanYazı = (DurağanYazı)sondaki;
		materyali
			.aradeğerleriniBul(
				baştakiDurağanYazı.materyali,
				sondakiDurağanYazı.materyali,
				uzunluk);
		dönüşümü
			.aradeğerleriniBul(
				baştakiDurağanYazı.dönüşümü,
				sondakiDurağanYazı.dönüşümü,
				uzunluk);
		return this;
	}
	
	@Override
	public Yumuşatılabilir değiştir(Yumuşatılabilir öbürü) {
		final DurağanYazı öbürDurağanYazı = (DurağanYazı)öbürü;
		materyali.değiştir(öbürDurağanYazı.materyali);
		dönüşümü.değiştir(öbürDurağanYazı.dönüşümü);
		return this;
	}
	
	@Override
	public Yumuşatılabilir çoğalt() {
		return new DurağanYazı(this);
	}
}
