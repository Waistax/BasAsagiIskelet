/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.8 / 15 Mar 2021 / 14:12:07
 */
package başaşağıderebeyi.iskelet.olaylar;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.kütüphane.olay.*;

/** Geri dönüştürülebilir bir olay. Bu olay tekrar tekrar kullanıldığı için her
 * an tek bir kere dağıtıldığından emin olmak gerekir. */
public abstract class GeriDönüşümlüOlay extends Olay {
	/** Çalıştıran iskelet. */
	public final İskelet çalıştıranİskelet;
	/** Bu olayın en son dağıtıldığı an. Her dağıtımda bu an güncellenmelidir.
	 * Yoksa tek bir anda aynı olay birden fazla kere dağıtılır. */
	public long enSonDağıtıldığıAn;
	
	/** Verilen iskelette tanımlar. */
	public GeriDönüşümlüOlay(final İskelet çalıştıranİskelet) {
		this.çalıştıranİskelet = çalıştıranİskelet;
		enSonDağıtıldığıAn = -1L;
	}
	
	/** Bu olayı dağıtmayı dener. Eğer şu anda olay dağıtılmadıysa dağıtır.
	 * Ayrıca dağıtmadan önce olayın susturulmadığından emin olur. */
	public void dağıtmayıDene() {
		if (enSonDağıtıldığıAn != çalıştıranİskelet.anınıEdin()) {
			susturulması = false;
			çalıştıranİskelet.olaylarınınDağıtıcısınıEdin().dağıt(this);
			enSonDağıtıldığıAn = çalıştıranİskelet.anınıEdin();
		}
	}
}
