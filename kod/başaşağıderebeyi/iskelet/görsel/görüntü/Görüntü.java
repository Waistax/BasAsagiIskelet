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
	public Görüntü(Materyal materyali, Dönüşüm dönüşümü) {
		this.materyali = materyali;
		this.dönüşümü = dönüşümü;
	}
	
	/** Temiz bir dönüşümle tanımlar. */
	public Görüntü(Materyal materyal) {
		this(materyal, new Dönüşüm());
	}
	
	@Override
	public Yumuşatılabilir aradeğerleriniBul(
		Yumuşatılabilir baştaki,
		Yumuşatılabilir sondaki,
		double uzunluk) {
		Görüntü baştakiGörüntü = (Görüntü)baştaki;
		Görüntü sondakiGörüntü = (Görüntü)sondaki;
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
	public Yumuşatılabilir değiştir(Yumuşatılabilir öbürü) {
		Görüntü öbürGörüntü = (Görüntü)öbürü;
		materyali.değiştir(öbürGörüntü.materyali);
		dönüşümü.değiştir(öbürGörüntü.dönüşümü);
		return null;
	}
}
