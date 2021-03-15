/**
 * başaşağıderebeyi.iskelet.girdi.TekerlekGirdisiOlayı.java
 * 0.2 / 3 Mar 2021 / 21:46:14
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.girdi;

import başaşağıderebeyi.iskelet.*;

/** Fare tekerleğiyle girdi verilmesinin olayı. Bu girdi olayı tekrar tekrar
 * kullanılır. Böylece her olayda yeni bir nesne oluşturulmamış olur. */
public class TekerlekGirdisiOlayı extends GirdiOlayı {
	/** Fare tekerleğinin döndüğü devir. */
	public double devri;
	
	TekerlekGirdisiOlayı(final İskelet çalıştıranİskelet) {
		super(çalıştıranİskelet);
	}
}
