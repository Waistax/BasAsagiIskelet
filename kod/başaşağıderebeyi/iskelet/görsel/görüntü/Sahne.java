/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.0.7 / 10 Tem 2021 / 14:28:02
 */
package başaşağıderebeyi.iskelet.görsel.görüntü;

/** Aynı bakış ile çizilecek bir gurup nesnenin bulunduğu bir uzaydır. */
public class Sahne {
	/** Sahnenin yumuşak bakışı. */
	public final YumuşakBakış yumuşakBakış;
	
	public Sahne() {
		yumuşakBakış = new YumuşakBakış();
	}
}
