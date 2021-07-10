/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.0.7 / 10 Tem 2021 / 14:31:47
 */
package başaşağıderebeyi.iskelet.görsel.görüntü;

import başaşağıderebeyi.iskelet.*;

/** Çizimi yumuşatmak için eski bakışları saklayıp çizimde kullanılacak bakışı
 * eski ile yeni bakışın aradeğerinden bulmaya yarayan araç. */
public class YumuşakBakış {
	/** Şu andaki bakışı. */
	public final Bakış anlıkBakışı;
	/** Önceki andaki bakışı. */
	public final Bakış öncekiBakışı;
	/** Karedeki bakışı. */
	public final Bakış çizilecekBakışı;
	
	/** Verilen bakışla tanımlar. */
	public YumuşakBakış(final Bakış anlıkBakışı) {
		this.anlıkBakışı = anlıkBakışı;
		öncekiBakışı = new Bakış();
		çizilecekBakışı = new Bakış();
	}
	
	/** Yeni bir bakışla tanımlar. */
	public YumuşakBakış() {
		this(new Bakış());
	}
	
	/** Önceki bakışı anlık bakışa değiştirir. Güncellemenin başında
	 * çağrılmalıdır. */
	public void sakla() {
		öncekiBakışı.değiştir(anlıkBakışı);
	}
	
	/** Çizilecek bakışı önceki ve anlık bakışların aradeğerinden bulur. Çizimin
	 * başında çağrılmalıdır. */
	public void bul() {
		çizilecekBakışı
			.aradeğerleriniBul(
				öncekiBakışı,
				anlıkBakışı,
				İskelet.NESNESİ.güncellenmemişTıkSayısınıEdin());
		
	}
}
