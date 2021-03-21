/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.2 / 3 Mar 2021 / 21:18:07
 */
package başaşağıderebeyi.iskelet.olaylar;

/** Fare imleciyle girdi verilmesinin olayı. Bu girdi olayı tekrar tekrar
 * kullanılır. Böylece her olayda yeni bir nesne oluşturulmamış olur. */
public class İmleçGirdisiOlayı extends GirdiOlayı {
	/** Fare imlecinin konumunun yatay bileşeni. */
	public double konumununYatayBileşeni;
	/** Fare imlecinin konumunun dikey bileşeni. */
	public double konumununDikeyBileşeni;
}
