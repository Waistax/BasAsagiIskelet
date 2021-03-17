/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.8 / 15 Mar 2021 / 14:12:07
 */
package başaşağıderebeyi.iskelet;

import başaşağıderebeyi.kütüphane.olay.*;

/** Geridönüştürülebilir bir olay. Bu olay tekrar tekrar kullanıldığı için her
 * an tek bir kere dağıtıldığından emin olmak gerekir. */
public class GeridönüşümlüOlay extends Olay {
	private final İskelet çalıştıranİskelet;
	private long enSonDağıtıldığıAn;
	
	/** Verilen iskelette tanımlar. */
	public GeridönüşümlüOlay(final İskelet çalıştıranİskelet) {
		this.çalıştıranİskelet = çalıştıranİskelet;
		enSonDağıtıldığıAn = -1L;
	}
	
	/** Bu olayı dağıtmayı dener. Eğer şu anda olay dağıtılmadıysa dağıtır.
	 * Ayrıca dağıtmadan önce olayın susturulmadığından emin olur. */
	public void dağıtmayıDene() {
		if (enSonDağıtıldığıAn != çalıştıranİskelet.anınıEdin()) {
			susturulması = false;
			çalıştıranİskelet.olayDağıtıcısınıEdin().dağıt(this);
			enSonDağıtıldığıAn = çalıştıranİskelet.anınıEdin();
		}
	}
}
