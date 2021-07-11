/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.1.1 / 11 Tem 2021 / 07:23:57
 */
package başaşağıderebeyi.iskelet.görsel.görüntü;

import başaşağıderebeyi.iskelet.*;

/** Bir nesnenin üç farklı halini saklayıp, gerektiğinde yumuşatan araç. */
public class Yumuşatıcı<T extends Yumuşatılabilir> {
	public final T anlığı;
	public final T önceki;
	public final T yumuşatılmışı;
	
	public Yumuşatıcı(T anlığı, T önceki, T yumuşatılmışı) {
		this.anlığı = anlığı;
		this.önceki = önceki;
		this.yumuşatılmışı = yumuşatılmışı;
	}
	
	public void sakla() {
		önceki.değiştir(anlığı);
	}
	
	public void bul() {
		yumuşatılmışı
			.aradeğerleriniBul(
				önceki,
				anlığı,
				İskelet.NESNESİ.güncellenmemişTıkSayısınıEdin());
	}
}
