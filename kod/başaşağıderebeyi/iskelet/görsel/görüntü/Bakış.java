/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.0.5 / 10 Tem 2021 / 13:19:14
 */
package başaşağıderebeyi.iskelet.görsel.görüntü;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.kütüphane.matematik.*;
import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;

/** Sahnenin ekran kartı uzayına aktarılmasına yarayan araç. */
public class Bakış implements Yumuşatılabilir {
	private static final String BAKIŞ_KONUMU_DEĞERİ_ADI = "bakisKonumu";
	private static final String BAKIŞ_BOYUTU_DEĞERİ_ADI = "bakisBoyutu";
	private static final String BAKIŞ_AÇISI_DEĞERİ_ADI = "bakisAcisi";
	
	/** Bakışı yüklemek için kullanılacak değerleri gölgelendiricide bulur. */
	public static void değerlerininKonumlarınıBul(
		final Gölgelendirici gölgelendirici) {
		gölgelendirici.değerinKonumunuBul(BAKIŞ_KONUMU_DEĞERİ_ADI);
		gölgelendirici.değerinKonumunuBul(BAKIŞ_BOYUTU_DEĞERİ_ADI);
		gölgelendirici.değerinKonumunuBul(BAKIŞ_AÇISI_DEĞERİ_ADI);
	}
	
	/** Sahnede bulunduğu konum. Bu konumdaki pikseller ekran kartı uzayında
	 * orijine çizilir. */
	public final Yöney3 konumu;
	/** Sahnenin kamerada görünürken ölçeklendirileceği miktar. */
	public double boyutu;
	/** Sahnede bulunduğu açı. Bütün dünya bu açının tersi kadar dönecektir. */
	public double açısı;
	
	/** Orijinde birim boyutlarla tanımlar. */
	public Bakış() {
		konumu = new Yöney3();
		boyutu = 1.0;
	}
	
	/** Verilenin aynısı tanımlar. */
	public Bakış(final Bakış bakış) {
		this();
		değiştir(bakış);
	}
	
	/** Bakışı gölgelendiriciye yükler. */
	public void yükle(final Gölgelendirici gölgelendirici) {
		gölgelendirici.değeriDeğiştir(BAKIŞ_KONUMU_DEĞERİ_ADI, konumu);
		gölgelendirici.değeriDeğiştir(BAKIŞ_BOYUTU_DEĞERİ_ADI, boyutu);
		gölgelendirici.değeriDeğiştir(BAKIŞ_AÇISI_DEĞERİ_ADI, açısı);
	}
	
	@Override
	public Yumuşatılabilir aradeğerleriniBul(
		final Yumuşatılabilir baştaki,
		final Yumuşatılabilir sondaki,
		final double uzaklık) {
		final Bakış baştakiBakış = (Bakış)baştaki;
		final Bakış sondakiBakış = (Bakış)sondaki;
		konumu
			.aradeğerleriniBul(
				baştakiBakış.konumu,
				sondakiBakış.konumu,
				uzaklık);
		boyutu = MatematikAracı
			.aradeğerleriniBul(
				baştakiBakış.boyutu,
				sondakiBakış.boyutu,
				uzaklık);
		açısı = MatematikAracı
			.aradeğerleriniBul(baştakiBakış.açısı, sondakiBakış.açısı, uzaklık);
		return this;
	}
	
	@Override
	public Yumuşatılabilir değiştir(final Yumuşatılabilir öbürü) {
		final Bakış öbürBakış = (Bakış)öbürü;
		konumu.değiştir(öbürBakış.konumu);
		boyutu = öbürBakış.boyutu;
		açısı = öbürBakış.açısı;
		return this;
	}
}
