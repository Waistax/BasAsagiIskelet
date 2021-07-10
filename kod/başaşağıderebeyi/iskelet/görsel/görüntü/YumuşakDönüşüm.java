/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.0.7 / 10 Tem 2021 / 14:39:39
 */
package başaşağıderebeyi.iskelet.görsel.görüntü;

import başaşağıderebeyi.iskelet.*;

/** Çizimi yumuşatmak için eski dönüşümleri saklayıp çizimde kullanılacak
 * dönüşümü eski ile yeni dönüşümün aradeğerinden bulmaya yarayan araç. */
public class YumuşakDönüşüm {
	public final Dönüşüm dönüşüm;
	public final Dönüşüm eskiDönüşüm;
	public final Dönüşüm çizilecekDönüşüm;
	
	/** Verilen dönüşümle tanımlar. */
	public YumuşakDönüşüm(final Dönüşüm dönüşüm) {
		this.dönüşüm = dönüşüm;
		eskiDönüşüm = new Dönüşüm();
		çizilecekDönüşüm = new Dönüşüm();
	}
	
	/** Yeni bir dönüşümle tanımlar. */
	public YumuşakDönüşüm() {
		this(new Dönüşüm());
	}
	
	public void sakla() {
		eskiDönüşüm.değiştir(dönüşüm);
	}
	
	public void bul() {
		çizilecekDönüşüm
			.aradeğerleriniBul(
				eskiDönüşüm,
				dönüşüm,
				İskelet.NESNESİ.güncellenmemişTıkSayısınıEdin());
		
	}
}
