/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.2 / 3 Mar 2021 / 20:58:27
 */
package başaşağıderebeyi.iskelet.olaylar;

import başaşağıderebeyi.kütüphane.girdi.*;
import başaşağıderebeyi.kütüphane.olay.*;

/** Fareyle girdi verilmesinin olayı. */
public class FareGirdisiOlayı extends Olay {
	/** Bu olaya karışan tuş. */
	public final Tuş tuşu;
	/** Olayın basılma olup olmadığı. */
	public final boolean basılması;
	
	/** Verilenlerle tanımlar. */
	public FareGirdisiOlayı(final Tuş tuşu, final boolean basılması) {
		this.tuşu = tuşu;
		this.basılması = basılması;
	}
}
