/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.10.2 / 20 Mar 2021 / 10:55:59
 */
package başaşağıderebeyi.iskelet;

import başaşağıderebeyi.iskelet.olaylar.*;
import başaşağıderebeyi.kütüphane.olay.*;

class OlaySağlayıcısı {
	final AnlıOlayDağıtıcısı güncellemeOlaylarınınDağıtıcısı;
	final OlayDağıtıcısı çizimOlaylarınınDağıtıcısı;
	
	private final OluşturmaOlayı oluşturmaOlayı;
	private final YokEtmeOlayı yokEtmeOlayı;
	private final GüncellemeOlayı güncellemeOlayı;
	private final SayaçOlayı sayaçOlayı;
	private final ÇizimOlayı çizimOlayı;
	
	OlaySağlayıcısı() {
		güncellemeOlaylarınınDağıtıcısı = new AnlıOlayDağıtıcısı();
		çizimOlaylarınınDağıtıcısı = new OlayDağıtıcısı();
		
		oluşturmaOlayı = new OluşturmaOlayı();
		yokEtmeOlayı = new YokEtmeOlayı();
		güncellemeOlayı = new GüncellemeOlayı();
		sayaçOlayı = new SayaçOlayı();
		çizimOlayı = new ÇizimOlayı();
	}
	
	void oluşturmaOlayınıDağıt() {
		oluşturmaOlayı.dağıtmayıDene(güncellemeOlaylarınınDağıtıcısı);
		güncellemeOlaylarınınDağıtıcısı.güncelle();
	}
	
	void yokEtmeOlayınıDağıt() {
		yokEtmeOlayı.dağıtmayıDene(güncellemeOlaylarınınDağıtıcısı);
		güncellemeOlaylarınınDağıtıcısı.güncelle();
	}
	
	void güncellemeOlayınıDağıt() {
		güncellemeOlayı.dağıtmayıDene(güncellemeOlaylarınınDağıtıcısı);
		güncellemeOlaylarınınDağıtıcısı.güncelle();
	}
	
	void sayaçOlayınıDağıt() {
		sayaçOlayı.dağıtmayıDene(güncellemeOlaylarınınDağıtıcısı);
	}
	
	void çizimOlayınıDağıt() {
		çizimOlayı.dağıtmayıDene(çizimOlaylarınınDağıtıcısı);
	}
}
