/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.0.7 / 10 Tem 2021 / 15:05:11
 */
package başaşağıderebeyi.iskelet.görsel.görüntü;

import static org.lwjgl.opengl.GL11.*;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;

/** Doku ve renk birleşimi. Herhangi bir nesneyi çizerken kullanılır. */
public class Materyal {
	private static final String RENK_DEĞERİ_ADI = "renk";
	
	/** Bakışı yüklemek için kullanılacak değerleri gölgelendiricide bulur. */
	public static void değerlerininKonumlarınıBul(
		final Gölgelendirici gölgelendirici) {
		gölgelendirici.değerinKonumunuBul(RENK_DEĞERİ_ADI);
	}
	
	/** Dokunun ekran kartındaki işaretçisi. */
	public final int dokusu;
	/** Kullanılacak renk, RGBA olarak. */
	public final Yöney4 rengi;
	
	/** Verilenler ile tanımlar. */
	public Materyal(final int dokusu, final Yöney4 rengi) {
		this.dokusu = dokusu;
		this.rengi = rengi;
	}
	
	/** İzdüşümü gölgelendiriciye yükler. */
	public void yükle(final Gölgelendirici gölgelendirici) {
		glBindTexture(GL_TEXTURE_2D, dokusu);
		gölgelendirici.değeriDeğiştir(RENK_DEĞERİ_ADI, rengi);
	}
}
