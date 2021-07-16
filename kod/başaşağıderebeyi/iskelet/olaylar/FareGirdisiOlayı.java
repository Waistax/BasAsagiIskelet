/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.2 / 3 Mar 2021 / 20:58:27
 */
package başaşağıderebeyi.iskelet.olaylar;

import başaşağıderebeyi.kütüphane.olay.*;

/** Fareyle girdi verilmesinin olayı. */
public class FareGirdisiOlayı extends Olay {
	/** Bu olaya karışan tuşun kodu. */
	public final int tuşKodu;
	/** Olayın basılma olup olmadığı. */
	public final boolean basılması;
	
	/** Verilenlerle tanımlar. */
	public FareGirdisiOlayı(final int tuşKodu, final boolean basılması) {
		this.tuşKodu = tuşKodu;
		this.basılması = basılması;
	}
}
