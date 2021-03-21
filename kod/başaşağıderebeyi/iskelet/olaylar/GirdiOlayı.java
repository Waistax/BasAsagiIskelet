/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.10.2 / 20 Mar 2021 / 11:16:56
 */
package başaşağıderebeyi.iskelet.olaylar;

import başaşağıderebeyi.iskelet.*;

abstract class GirdiOlayı extends GeriDönüşümlüOlay {
	/** Doğrudan iskeletin güncelleme olay dağıtıcısında dağıtmayı dener. */
	public void dağıtmayıDene() {
		dağıtmayıDene(İskelet.NESNESİ.güncellemeOlaylarınınDağıtıcısınıEdin());
	}
}
