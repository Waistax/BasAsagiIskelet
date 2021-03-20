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
	
	public URL kaynağınıBul(String arşivGirdisi) {
		return kaynakları.get(arşivGirdisi);
	}
	
	public Object nesnesiniEdin() {
		return nesnesi;
	}
}
