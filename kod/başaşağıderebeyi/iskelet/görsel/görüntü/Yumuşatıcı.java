/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.1.1 / 11 Tem 2021 / 07:23:57
 */
package başaşağıderebeyi.iskelet.görsel.görüntü;

import başaşağıderebeyi.iskelet.*;

/** Bir nesnenin üç farklı halini saklayıp, gerektiğinde yumuşatan araç. */
public class Yumuşatıcı<T extends Yumuşatılabilir> {
	/** Şu an güncellenen nesne. */
	public final T anlığı;
	/** Önceki güncellemedeki sürümü. */
	public final T önceki;
	/** İki güncelleme arasında çizim için yumuşatılmış aradeğeri. */
	public final T aradeğeri;
	
	/** Verilen nesneyi çoğaltarak tanımlar. */
	@SuppressWarnings("unchecked")
	public Yumuşatıcı(final T anlığı) {
		this(anlığı, (T)anlığı.çoğalt(), (T)anlığı.çoğalt());
	}
	
	/** Verilen nesnelerden tanımlar. */
	public Yumuşatıcı(final T anlığı, final T önceki, final T aradeğeri) {
		this.anlığı = anlığı;
		this.önceki = önceki;
		this.aradeğeri = aradeğeri;
	}
	
	/** Nesnenin anlık sürümünü saklar. Bu, güncellemeden önce yapılmalıdır. */
	public void sakla() {
		önceki.değiştir(anlığı);
	}
	
	/** Çizimde kullanılacak yumuşatılmış aradeğeri bulur. Bu, çizimden önce
	 * yapılmalıdır. */
	public void bul() {
		aradeğeri
			.aradeğerleriniBul(
				önceki,
				anlığı,
				İskelet.NESNESİ.güncellenmemişTıkSayısınıEdin());
	}
}
