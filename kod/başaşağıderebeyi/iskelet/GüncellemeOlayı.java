/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.10.1 / 17 Mar 2021 / 21:41:42
 */
package başaşağıderebeyi.iskelet;

import başaşağıderebeyi.kütüphane.olay.*;

/** İskeletin bir sonraki anı güncellemek için dağıttığı olay. */
public class GüncellemeOlayı extends Olay {
	/** Güncellenen an. */
	public final long anı;
	
	GüncellemeOlayı(final long anı) {
		this.anı = anı;
	}
}
