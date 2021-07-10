/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.0.7 / 10 Tem 2021 / 15:05:11
 */
package başaşağıderebeyi.iskelet.görsel.görüntü;

import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;

/** Doku ve renk birleşimi. Herhangi bir nesneyi çizerken kullanılır. */
public class Materyal {
	/** Dokunun ekran kartındaki işaretçisi. */
	public final int doku;
	/** Kullanılacak renk, RGBA olarak. */
	public final Yöney4 renk;
	
	/** Verilenler ile tanımlar. */
	public Materyal(final int doku, final Yöney4 renk) {
		this.doku = doku;
		this.renk = renk;
	}
}
