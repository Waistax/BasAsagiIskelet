/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.10.1 / 17 Mar 2021 / 21:35:59
 */
package başaşağıderebeyi.iskelet.olaylar;

import başaşağıderebeyi.iskelet.*;

/** İskeletin bir sonraki kareyi oluşturmak için dağıttığı olay. */
public class ÇizimOlayı extends GeriDönüşümlüOlay {
	/** Önceki tığa olan uzaklık. */
	public float uzaklığı;
	
	/** Verilen iskelette tanımlar. */
	public ÇizimOlayı(final İskelet çalıştıranİskelet) {
		super(çalıştıranİskelet);
	}
	
	@Override
	public void dağıtmayıDene() {
		uzaklığı = (float)çalıştıranİskelet.güncellenmemişTıklarınıEdin();
		susturulması = false;
		çalıştıranİskelet.çizimOlaylarınınDağıtıcısınıEdin().dağıt(this);
	}
}
