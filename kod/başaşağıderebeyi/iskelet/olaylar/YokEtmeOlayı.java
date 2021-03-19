/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.10.1 / 17 Mar 2021 / 21:56:54
 */
package başaşağıderebeyi.iskelet.olaylar;

import başaşağıderebeyi.iskelet.*;

/** İskelet kapanırken yüklenen kaynakların yok edilmesi için oluşturulmuş
 * olay. */
public class YokEtmeOlayı extends GeriDönüşümlüOlay {
	/** Verilen iskelet ile tanımlar ve kendisini dağıtır. */
	public YokEtmeOlayı(final İskelet çalıştıranİskelet) {
		super(çalıştıranİskelet);
		çalıştıranİskelet.olaylarınınDağıtıcısınıEdin().dağıt(this);
	}
	
	@Override
	public void dağıtmayıDene() {
		// Yok etme olayı yalnızca bir kere en sonda dağıtılmalıdır.
	}
}
