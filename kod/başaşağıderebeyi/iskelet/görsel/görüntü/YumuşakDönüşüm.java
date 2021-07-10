/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.0.7 / 10 Tem 2021 / 14:39:39
 */
package başaşağıderebeyi.iskelet.görsel.görüntü;

import başaşağıderebeyi.iskelet.*;

/** Çizimi yumuşatmak için eski dönüşümleri saklayıp çizimde kullanılacak
 * dönüşümü eski ile yeni dönüşümün aradeğerinden bulmaya yarayan araç. */
public class YumuşakDönüşüm {
	/** Şu andaki dönüşümü. */
	public final Dönüşüm anlıkDönüşümü;
	/** Önceki andaki dönüşümü. */
	public final Dönüşüm öncekiDönüşümü;
	/** Karedeki dönüşümü. */
	public final Dönüşüm çizilecekDönüşümü;
	
	/** Verilen dönüşümle tanımlar. */
	public YumuşakDönüşüm(final Dönüşüm anlıkDönüşümü) {
		this.anlıkDönüşümü = anlıkDönüşümü;
		öncekiDönüşümü = new Dönüşüm();
		çizilecekDönüşümü = new Dönüşüm();
	}
	
	/** Yeni bir dönüşümle tanımlar. */
	public YumuşakDönüşüm() {
		this(new Dönüşüm());
	}
	
	/** Önceki dönüşümü anlık dönüşüme değiştirir. Güncellemenin başında
	 * çağrılmalıdır. */
	public void güncelle() {
		öncekiDönüşümü.değiştir(anlıkDönüşümü);
	}
	
	/** Çizilecek dönüşümü bulur. Çizimin başında çağrılmalıdır. */
	public void bul() {
		çizilecekDönüşümü
			.aradeğerleriniBul(
				öncekiDönüşümü,
				anlıkDönüşümü,
				İskelet.NESNESİ.güncellenmemişTıkSayısınıEdin());
		
	}
}
