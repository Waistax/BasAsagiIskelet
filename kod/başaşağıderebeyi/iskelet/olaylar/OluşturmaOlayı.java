/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.10.1 / 17 Mar 2021 / 20:50:44
 */
package başaşağıderebeyi.iskelet.olaylar;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.kütüphane.olay.*;

/** İskelet ilk açıldığı zaman gerekli kaynakların yüklenmesi için dağıtılan
 * olay. */
public class OluşturmaOlayı extends GeriDönüşümlüOlay {
	@Override
	public void dağıtmayıDene(final OlayDağıtıcısı olayDağıtıcısı) {
		assert İskelet.NESNESİ.anınıEdin() == 0L;
		super.dağıtmayıDene(olayDağıtıcısı);
	}
}
