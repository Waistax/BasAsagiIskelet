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
	private static final String TERS_RENK_DEĞERİ_ADI = "tersRenk";
	
	/** Materyali yüklemek için kullanılacak değerleri gölgelendiricide
	 * bulur. */
	public static void değerlerininKonumlarınıBul(
		final Gölgelendirici gölgelendirici) {
		gölgelendirici.değerinKonumunuBul(RENK_DEĞERİ_ADI);
		gölgelendirici.değerinKonumunuBul(TERS_RENK_DEĞERİ_ADI);
	}
	
	/** Dokunun ekran kartındaki işaretçisi. */
	public int dokusu;
	/** Kullanılacak renk, RGBA olarak. */
	public final Yöney3 rengi;
	/** Kullanılacak ters renk, RGBA olarak. */
	public final Yöney3 tersRengi;
	
	/** Verilenler ile tanımlar. */
	public Materyal(
		final int dokusu,
		final Yöney3 rengi,
		final Yöney3 tersRengi) {
		this.dokusu = dokusu;
		this.rengi = rengi;
		this.tersRengi = tersRengi;
	}
	
	/** Materyali gölgelendiriciye yükler. */
	public void yükle(final Gölgelendirici gölgelendirici) {
		glBindTexture(GL_TEXTURE_2D, dokusu);
		gölgelendirici.değeriDeğiştir(RENK_DEĞERİ_ADI, rengi);
		gölgelendirici.değeriDeğiştir(TERS_RENK_DEĞERİ_ADI, tersRengi);
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
		tersRengi
			.aradeğerleriniBul(
				baştakiMateryal.tersRengi,
				sondakiMateryal.tersRengi,
				uzunluk);
		return this;
	}
	
	@Override
	public Yumuşatılabilir değiştir(final Yumuşatılabilir öbürü) {
		final Materyal öbürMateryal = (Materyal)öbürü;
		dokusu = öbürMateryal.dokusu;
		rengi.değiştir(öbürMateryal.rengi);
		tersRengi.değiştir(öbürMateryal.tersRengi);
		return this;
	}
}
