/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.2 / 3 Mar 2021 / 21:46:14
 */
package başaşağıderebeyi.iskelet.girdi;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.iskelet.olaylar.*;

/** Fare tekerleğiyle girdi verilmesinin olayı. Bu girdi olayı tekrar tekrar
 * kullanılır. Böylece her olayda yeni bir nesne oluşturulmamış olur. */
public class TekerlekGirdisiOlayı extends GeriDönüşümlüOlay {
	/** Fare tekerleğinin döndüğü devir. */
	public double devri;
	
	TekerlekGirdisiOlayı(final İskelet çalıştıranİskelet) {
		super(çalıştıranİskelet);
	}
}
