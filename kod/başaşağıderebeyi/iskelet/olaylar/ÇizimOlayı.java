/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.10.1 / 17 Mar 2021 / 21:35:59
 */
package başaşağıderebeyi.iskelet.olaylar;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.kütüphane.olay.*;

/** İskeletin bir sonraki kareyi oluşturmak için dağıttığı olay. */
public class ÇizimOlayı extends GeriDönüşümlüOlay {
	/** Önceki tığa olan uzaklık. */
	public float uzaklığı;
	
	@Override
	public void dağıtmayıDene(final OlayDağıtıcısı olayDağıtıcısı) {
		uzaklığı = (float)İskelet.NESNESİ.güncellenmemişTıkSayısınıEdin();
		susturulması = false;
		İskelet.NESNESİ.çizimOlaylarınınDağıtıcısınıEdin().dağıt(this);
	}
}
