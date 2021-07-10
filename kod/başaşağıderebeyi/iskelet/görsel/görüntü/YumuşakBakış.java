/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.0.7 / 10 Tem 2021 / 14:31:47
 */
package başaşağıderebeyi.iskelet.görsel.görüntü;

import başaşağıderebeyi.iskelet.*;

/** Çizimi yumuşatmak için eski bakışları saklayıp çizimde kullanılacak bakışı
 * eski ile yeni bakışın aradeğerinden bulmaya yarayan araç. */
public class YumuşakBakış {
	public final Bakış bakış;
	public final Bakış eskiBakış;
	public final Bakış çizilecekBakış;
	
	/** Verilen bakışla tanımlar. */
	public YumuşakBakış(final Bakış bakış) {
		this.bakış = bakış;
		eskiBakış = new Bakış();
		çizilecekBakış = new Bakış();
	}
	
	/** Yeni bir bakışla tanımlar. */
	public YumuşakBakış() {
		this(new Bakış());
	}
	
	public void sakla() {
		eskiBakış.değiştir(bakış);
	}
	
	public void bul() {
		çizilecekBakış
			.aradeğerleriniBul(
				eskiBakış,
				bakış,
				İskelet.NESNESİ.güncellenmemişTıkSayısınıEdin());
		
	}
}
