/**
 * başaşağıderebeyi.iskelet.girdi.GirdiOlayı.java
 * 0.8 / 15 Mar 2021 / 14:12:07
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.girdi;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.kütüphane.olay.*;

/** İskelete girdi verilmesinin olayı. Bu olaylar tekrar tekrar kullanıldığı
 * için her an tek bir kere dağıtıldığından emin olmak gerekir. */
public class GirdiOlayı extends Olay {
	private final İskelet çalıştıranİskelet;
	private long enSonDağıtıldığıAn;
	
	GirdiOlayı(final İskelet çalıştıranİskelet) {
		this.çalıştıranİskelet = çalıştıranİskelet;
		enSonDağıtıldığıAn = -1L;
	}
	
	void dağıtmayıDene() {
		if (enSonDağıtıldığıAn < çalıştıranİskelet.anınıEdin()) {
			susturulması = false;
			çalıştıranİskelet.olayDağıtıcısınıEdin().dağıt(this);
			enSonDağıtıldığıAn = çalıştıranİskelet.anınıEdin();
		}
	}
}
