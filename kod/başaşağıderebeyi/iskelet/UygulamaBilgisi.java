/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.12.0 / 20 Mar 2021 / 16:14:38
 */
package başaşağıderebeyi.iskelet;

import java.net.*;
import java.util.*;

/** Uygulamanın arşivdeki bilgilerini içiren nesne. */
public class UygulamaBilgisi {
	final Map<String, URL> kaynakları;
	Object nesnesi;
	
	UygulamaBilgisi() {
		kaynakları = new HashMap<>();
	}
	
	/** Verilen konumun URL'sini döndürür. Eğer arşivden alınan kaynaklar
	 * içerisinde verilen konumda bir şey bulunmuyorsa null döndürür. */
	public URL kaynağınıBul(final String konumu) {
		return kaynakları.get(konumu);
	}
	
	/** Uygulamanın iskelet tarafından oluşturulmuş nesnesini döndürür. */
	public Object nesnesiniEdin() {
		return nesnesi;
	}
}
