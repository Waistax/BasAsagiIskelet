/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.2 / 3 Mar 2021 / 20:58:27
 */
package başaşağıderebeyi.iskelet.olaylar;

import başaşağıderebeyi.kütüphane.girdi.*;

/** Fareyle girdi verilmesinin olayı. Bu girdi olayı tekrar tekrar kullanılır.
 * Böylece her olayda yeni bir nesne oluşturulmamış olur. */
public class FareGirdisiOlayı extends GirdiOlayı {
	/** Bu olaya karışan tuş. */
	public final Tuş tuşu;
	/** Olayın tuşunun basılı olup olmaması. */
	public boolean basılıOlması;
	
	/** Verilen tuşla tanımlar. */
	public FareGirdisiOlayı(final Tuş tuşu) {
		this.tuşu = tuşu;
	}
}
