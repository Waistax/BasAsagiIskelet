/**
 * başaşağıderebeyi.iskelet.girdi.İmleçGirdisiOlayı.java
 * 0.2 / 3 Mar 2021 / 21:18:07
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.girdi;

import başaşağıderebeyi.iskelet.*;

/** Fare imleciyle girdi verilmesinin olayı. Bu girdi olayı tekrar tekrar
 * kullanılır. Böylece her olayda yeni bir nesne oluşturulmamış olur. */
public class İmleçGirdisiOlayı extends GirdiOlayı {
	/** Fare imlecinin konumunun yatay bileşeni. */
	public double konumununYatayBileşeni;
	/** Fare imlecinin konumunun dikey bileşeni. */
	public double konumununDikeyBileşeni;
	
	İmleçGirdisiOlayı(final İskelet çalıştıranİskelet) {
		super(çalıştıranİskelet);
	}
}
