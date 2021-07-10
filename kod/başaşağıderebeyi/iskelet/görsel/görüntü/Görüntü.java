/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.0.8 / 10 Tem 2021 / 15:13:16
 */
package başaşağıderebeyi.iskelet.görsel.görüntü;

/** Sahnedeki görünen nesnelerden biri. */
public class Görüntü {
	/** Çizilecek materyali. */
	public final Materyal materyali;
	/** Yumuşatılmış dönüşümü. */
	public final YumuşakDönüşüm dönüşümü;
	
	/** Verilenlerle tanımlar. */
	public Görüntü(Materyal materyali, YumuşakDönüşüm dönüşümü) {
		this.materyali = materyali;
		this.dönüşümü = dönüşümü;
	}
	
	/** Temiz bir dönüşümle tanımlar. */
	public Görüntü(Materyal materyal) {
		this(materyal, new YumuşakDönüşüm());
	}
}
