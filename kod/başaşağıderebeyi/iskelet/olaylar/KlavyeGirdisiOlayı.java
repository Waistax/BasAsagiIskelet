/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.2 / 3 Mar 2021 / 20:32:15
 */
package başaşağıderebeyi.iskelet.olaylar;

import başaşağıderebeyi.kütüphane.girdi.*;
import başaşağıderebeyi.kütüphane.olay.*;

/** Klavyeyle girdi verilmesinin olayı. */
public class KlavyeGirdisiOlayı extends Olay {
	/** Bu olaya karışan tuş. */
	public final Tuş tuşu;
	/** Olayın basılma olup olmadığı. */
	public final boolean basılması;
	
	/** Verilenlerle tanımlar. */
	public KlavyeGirdisiOlayı(final Tuş tuşu, final boolean basılması) {
		this.tuşu = tuşu;
		this.basılması = basılması;
	}
}
