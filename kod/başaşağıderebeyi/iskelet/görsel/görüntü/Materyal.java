/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.0.7 / 10 Tem 2021 / 15:05:11
 */
package başaşağıderebeyi.iskelet.görsel.görüntü;

import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;

/** Doku ve renk birleşimi. Herhangi bir nesneyi çizerken kullanılır. */
public class Materyal {
	/** Dokunun ekran kartındaki işaretçisi. */
	public final int dokusu;
	/** Kullanılacak renk, RGBA olarak. */
	public final Yöney4 rengi;
	
	/** Verilenler ile tanımlar. */
	public Materyal(final int dokusu, final Yöney4 rengi) {
		this.dokusu = dokusu;
		this.rengi = rengi;
	}
}
