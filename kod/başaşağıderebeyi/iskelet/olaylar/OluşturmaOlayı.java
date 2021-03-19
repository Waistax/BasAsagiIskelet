/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.10.1 / 17 Mar 2021 / 20:50:44
 */
package başaşağıderebeyi.iskelet.olaylar;

import başaşağıderebeyi.iskelet.*;

/** İskelet ilk açıldığı zaman gerekli kaynakların yüklenmesi için oluşturulmuş
 * olay. */
public class OluşturmaOlayı extends GeriDönüşümlüOlay {
	/** Verilen iskelet ile tanımlar ve kendisini dağıtır. */
	public OluşturmaOlayı(final İskelet çalıştıranİskelet) {
		super(çalıştıranİskelet);
		assert çalıştıranİskelet.anınıEdin() == 0L;
		çalıştıranİskelet.olaylarınınDağıtıcısınıEdin().dağıt(this);
	}
	
	@Override
	public void dağıtmayıDene() {
		// Oluşturma olayı yalnızca bir kere en başta dağıtılmalıdır.
	}
}
