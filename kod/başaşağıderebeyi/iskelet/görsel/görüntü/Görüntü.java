/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.0.8 / 10 Tem 2021 / 15:13:16
 */
package başaşağıderebeyi.iskelet.görsel.görüntü;

/** Sahnedeki görünen nesnelerden biri. */
public class Görüntü implements Yumuşatılabilir {
	/** Çizilecek materyali. */
	public final Materyal materyali;
	/** Dönüşümü. */
	public final Dönüşüm dönüşümü;
	
	/** Verilenlerle tanımlar. */
	public Görüntü(final Materyal materyali, final Dönüşüm dönüşümü) {
		this.materyali = materyali;
		this.dönüşümü = dönüşümü;
	}
	
	/** Temiz bir dönüşümle tanımlar. */
	public Görüntü(final Materyal materyal) {
		this(materyal, new Dönüşüm());
	}
	
	@Override
	public Yumuşatılabilir aradeğerleriniBul(
		final Yumuşatılabilir baştaki,
		final Yumuşatılabilir sondaki,
		final double uzunluk) {
		final Görüntü baştakiGörüntü = (Görüntü)baştaki;
		final Görüntü sondakiGörüntü = (Görüntü)sondaki;
		materyali
			.aradeğerleriniBul(
				baştakiGörüntü.materyali,
				sondakiGörüntü.materyali,
				uzunluk);
		dönüşümü
			.aradeğerleriniBul(
				baştakiGörüntü.dönüşümü,
				sondakiGörüntü.dönüşümü,
				uzunluk);
		return null;
	}
	
	@Override
	public Yumuşatılabilir değiştir(final Yumuşatılabilir öbürü) {
		final Görüntü öbürGörüntü = (Görüntü)öbürü;
		materyali.değiştir(öbürGörüntü.materyali);
		dönüşümü.değiştir(öbürGörüntü.dönüşümü);
		return null;
	}
}
