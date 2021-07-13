/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.0.7 / 10 Tem 2021 / 15:05:11
 */
package başaşağıderebeyi.iskelet.görsel.görüntü;

import static org.lwjgl.opengl.GL11.*;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.kütüphane.matematik.*;
import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;

/** Doku ve renk birleşimi. Herhangi bir nesneyi çizerken kullanılır. */
public class Materyal implements Yumuşatılabilir {
	private static final String RENK_DEĞERİ_ADI = "renk";
	private static final String TABAN_RENGİ_DEĞERİ_ADI = "tabanRengi";
	
	/** Materyali yüklemek için kullanılacak değerleri gölgelendiricide
	 * bulur. */
	public static void değerlerininKonumlarınıBul(
		final Gölgelendirici gölgelendirici) {
		gölgelendirici.değerinKonumunuBul(RENK_DEĞERİ_ADI);
		gölgelendirici.değerinKonumunuBul(TABAN_RENGİ_DEĞERİ_ADI);
	}
	
	/** Dokunun ekran kartındaki işaretçisi. */
	public int dokusu;
	/** Dokunun üstünü boyayacak renk, RGBA olarak. */
	public final Yöney4 rengi;
	/** Saydam kısımları dolduracak renk, RGBA olarak. */
	public final Yöney4 tabanınınRengi;
	
	/** Boş tanımlar. */
	public Materyal() {
		this(0, new Yöney4(Yöney4.BİR), new Yöney4());
	}
	
	/** Verilenler ile tanımlar. */
	public Materyal(
		final int dokusu,
		final Yöney4 rengi,
		final Yöney4 tabanınınRengi) {
		this.dokusu = dokusu;
		this.rengi = rengi;
		this.tabanınınRengi = tabanınınRengi;
	}
	
	/** Materyali gölgelendiriciye yükler. */
	public void yükle(final Gölgelendirici gölgelendirici) {
		glBindTexture(GL_TEXTURE_2D, dokusu);
		gölgelendirici.değeriDeğiştir(RENK_DEĞERİ_ADI, rengi);
		gölgelendirici.değeriDeğiştir(TABAN_RENGİ_DEĞERİ_ADI, tabanınınRengi);
	}
	
	@Override
	public Yumuşatılabilir aradeğerleriniBul(
		final Yumuşatılabilir baştaki,
		final Yumuşatılabilir sondaki,
		final double uzunluk) {
		final Materyal baştakiMateryal = (Materyal)baştaki;
		final Materyal sondakiMateryal = (Materyal)sondaki;
		dokusu = MatematikAracı
			.aradeğerleriniBul(
				(Integer)baştakiMateryal.dokusu,
				(Integer)sondakiMateryal.dokusu,
				uzunluk);
		rengi
			.aradeğerleriniBul(
				baştakiMateryal.rengi,
				sondakiMateryal.rengi,
				uzunluk);
		tabanınınRengi
			.aradeğerleriniBul(
				baştakiMateryal.tabanınınRengi,
				sondakiMateryal.tabanınınRengi,
				uzunluk);
		return this;
	}
	
	@Override
	public Yumuşatılabilir değiştir(final Yumuşatılabilir öbürü) {
		final Materyal öbürMateryal = (Materyal)öbürü;
		dokusu = öbürMateryal.dokusu;
		rengi.değiştir(öbürMateryal.rengi);
		tabanınınRengi.değiştir(öbürMateryal.tabanınınRengi);
		return this;
	}
	
	@Override
	public Yumuşatılabilir çoğalt() {
		return new Materyal().değiştir(this);
	}
}
