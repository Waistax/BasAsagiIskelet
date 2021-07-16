/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.2 / 3 Mar 2021 / 21:18:07
 */
package başaşağıderebeyi.iskelet.olaylar;

import başaşağıderebeyi.kütüphane.olay.*;

/** Fare imleciyle girdi verilmesinin olayı. */
public class İmleçGirdisiOlayı extends Olay {
	/** Fare imlecinin konumunun yatay bileşeni. */
	public final double konumununYatayBileşeni;
	/** Fare imlecinin konumunun dikey bileşeni. */
	public final double konumununDikeyBileşeni;
	
	/** Verilenlerle tanımlar. */
	public İmleçGirdisiOlayı(
		final double konumununYatayBileşeni,
		final double konumununDikeyBileşeni) {
		this.konumununYatayBileşeni = konumununYatayBileşeni;
		this.konumununDikeyBileşeni = konumununDikeyBileşeni;
	}
}
