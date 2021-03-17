/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.2 / 3 Mar 2021 / 20:58:27
 */
package başaşağıderebeyi.iskelet.girdi;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.kütüphane.girdi.*;

/** Fareyle girdi verilmesinin olayı. Bu girdi olayı tekrar tekrar kullanılır.
 * Böylece her olayda yeni bir nesne oluşturulmamış olur. */
public class FareGirdisiOlayı extends GirdiOlayı {
	/** Bu olaya karışan tuş. */
	public final Tuş tuşu;
	/** Olayın tuşunun basılı olup olmaması. */
	public boolean basılıOlması;
	
	FareGirdisiOlayı(final İskelet çalıştıranİskelet, final Tuş tuşu) {
		super(çalıştıranİskelet);
		this.tuşu = tuşu;
	}
}
