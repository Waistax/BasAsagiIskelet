/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.2 / 3 Mar 2021 / 21:46:14
 */
package başaşağıderebeyi.iskelet.olaylar;

import başaşağıderebeyi.kütüphane.olay.*;

/** Fare tekerleğiyle girdi verilmesinin olayı. */
public class TekerlekGirdisiOlayı extends Olay {
	/** Fare tekerleğinin döndüğü devir. */
	public final double devri;
	
	/** Verilenle tanımlar. */
	public TekerlekGirdisiOlayı(final double devri) {
		this.devri = devri;
	}
}
