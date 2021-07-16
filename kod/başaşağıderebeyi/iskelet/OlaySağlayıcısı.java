/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.10.2 / 20 Mar 2021 / 10:55:59
 */
package başaşağıderebeyi.iskelet;

import static başaşağıderebeyi.iskelet.İskelet.*;

import başaşağıderebeyi.kütüphane.olay.*;

class OlaySağlayıcısı {
	final AnlıOlayDağıtıcısı güncellemeOlaylarınınDağıtıcısı;
	final OlayDağıtıcısı çizimOlaylarınınDağıtıcısı;
	
	OlaySağlayıcısı() {
		güncellemeOlaylarınınDağıtıcısı = new AnlıOlayDağıtıcısı();
		çizimOlaylarınınDağıtıcısı = new OlayDağıtıcısı();
	}
	
	void oluşturmaOlayınıDağıt() {
		güncellemeOlaylarınınDağıtıcısı.dağıt(new OluşturmaOlayı());
		güncellemeOlaylarınınDağıtıcısı.güncelle();
	}
	
	void yokEtmeOlayınıDağıt() {
		güncellemeOlaylarınınDağıtıcısı.dağıt(new YokEtmeOlayı());
		güncellemeOlaylarınınDağıtıcısı.güncelle();
	}
	
	void güncellemeOlayınıDağıt() {
		güncellemeOlaylarınınDağıtıcısı
			.dağıt(new GüncellemeOlayı(İSKELET.anınıEdin()));
		güncellemeOlaylarınınDağıtıcısı.güncelle();
	}
	
	void sayaçOlayınıDağıt() {
		güncellemeOlaylarınınDağıtıcısı.dağıt(new SayaçOlayı());
	}
	
	void çizimOlayınıDağıt() {
		çizimOlaylarınınDağıtıcısı.dağıt(new ÇizimOlayı());
	}
}
