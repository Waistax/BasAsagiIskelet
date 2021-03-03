/**
 * başaşağıderebeyi.iskelet.girdi.KlavyeGirdisiOlayı.java
 * 0.2 / 3 Mar 2021 / 20:32:15
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.girdi;

import başaşağıderebeyi.kütüphane.girdi.*;
import başaşağıderebeyi.kütüphane.olay.*;

/** Klavyeyle girdi verilmesinin olayı. Bu girdi olayı tekrar tekrar kullanılır.
 * Böylece her olayda yeni bir nesne oluşturulmamış olur. */
public class KlavyeGirdisiOlayı extends Olay {
	/** Bu olaya karışan tuş. */
	public final Tuş tuşu;
	/** Olayın tuşunun basılı olup olmaması. */
	public boolean basılıOlması;
	
	/** Verilen tuş koduyla tanımlar. */
	KlavyeGirdisiOlayı(final Tuş tuşu) {
		this.tuşu = tuşu;
	}
}
