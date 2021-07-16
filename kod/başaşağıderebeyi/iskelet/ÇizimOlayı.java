/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.10.1 / 17 Mar 2021 / 21:35:59
 */
package başaşağıderebeyi.iskelet;

import static başaşağıderebeyi.iskelet.İskelet.*;

import başaşağıderebeyi.kütüphane.olay.*;

/** İskeletin bir sonraki kareyi oluşturmak için dağıttığı olay. */
public class ÇizimOlayı extends Olay {
	/** Önceki tığa olan uzaklık. Bu uzaklık görüntüleri yumuşatmak için
	 * kullanılabilir. */
	public final double uzaklığı;
	
	/** Yumuşatma için gerekli uzaklıkla tanımlar. */
	public ÇizimOlayı() {
		uzaklığı = İSKELET.güncellenmemişTıkSayısınıEdin();
	}
}
