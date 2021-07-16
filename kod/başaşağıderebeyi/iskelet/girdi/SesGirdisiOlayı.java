/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.10.0 / 16 Tem 2021 / 15:01:44
 */
package başaşağıderebeyi.iskelet.girdi;

import başaşağıderebeyi.kütüphane.olay.*;

/** Ses girdisi verilmesinin olayı. */
public class SesGirdisiOlayı extends Olay {
	/** Girilen ses. */
	public final char ses;
	
	/** Verilenle tanımlar. */
	public SesGirdisiOlayı(final char ses) {
		this.ses = ses;
	}
}
