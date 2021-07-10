/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.0.4 / 10 Tem 2021 / 11:46:57
 */
package başaşağıderebeyi.iskelet.görsel.görüntü;

import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;

/** Çizimlerin ekran kartı uzayına aktarılmasını sağlayacak araç. Orta noktası
 * her zaman origindir. */
public class İzdüşüm {
	public final Yöney3 boyutu;
	
	/** Birim uzay olarak tanımlar. */
	public İzdüşüm() {
		boyutu = new Yöney3(Yöney3.BİR);
	}
	
	/** İzdüşümün uzayının boyutunu değiştirir. */
	public İzdüşüm boyutunuDeğiştir(
		double genişlik,
		double yükseklik,
		double derinlik) {
		boyutu.bileşenleriniDeğiştir(genişlik, yükseklik, derinlik);
		return this;
	}
}
