/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.0.5 / 10 Tem 2021 / 13:19:14
 */
package başaşağıderebeyi.iskelet.görsel.görüntü;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.kütüphane.matematik.*;
import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;

/** Sahnenin ekran kartı uzayına aktarılmasına yarayan araç. */
public class Bakış {
	private static final String İZDÜŞÜM_BOYUTU_DEĞERİ_ADI = "izdusumBoyutu";
	private static final String BAKIŞ_KONUMU_DEĞERİ_ADI = "bakisKonumu";
	private static final String BAKIŞ_BOYUTU_DEĞERİ_ADI = "bakisBoyutu";
	private static final String BAKIŞ_AÇISI_DEĞERİ_ADI = "bakisAcisi";
	
	/** Bakışı yüklemek için kullanılacak değerleri gölgelendiricide bulur. */
	public static void değerlerininKonumlarınıBul(
		final Gölgelendirici gölgelendirici) {
		gölgelendirici.değerinKonumunuBul(İZDÜŞÜM_BOYUTU_DEĞERİ_ADI);
		gölgelendirici.değerinKonumunuBul(BAKIŞ_KONUMU_DEĞERİ_ADI);
		gölgelendirici.değerinKonumunuBul(BAKIŞ_BOYUTU_DEĞERİ_ADI);
		gölgelendirici.değerinKonumunuBul(BAKIŞ_AÇISI_DEĞERİ_ADI);
	}
	
	/** Ekran kartı uzayına izdüşürülecek alanın boyutu. */
	public final Yöney3 izdüşümBoyutu;
	/** Sahnede bulunduğu konum. Bu konumdaki pikseller ekran kartı uzayında
	 * orijine çizilir. */
	public final Yöney3 konumu;
	/** Sahnenin kamerada görünürken ölçeklendirileceği miktar. */
	public double boyutu;
	/** Sahnede bulunduğu açı. Bütün dünya bu açının tersi kadar dönecektir. */
	public double açısı;
	
	/** Orijinde birim boyutlarla tanımlar. */
	public Bakış() {
		izdüşümBoyutu = new Yöney3(Yöney3.BİR);
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
		gölgelendirici.değeriDeğiştir(İZDÜŞÜM_BOYUTU_DEĞERİ_ADI, izdüşümBoyutu);
		gölgelendirici.değeriDeğiştir(BAKIŞ_KONUMU_DEĞERİ_ADI, konumu);
		gölgelendirici.değeriDeğiştir(BAKIŞ_BOYUTU_DEĞERİ_ADI, boyutu);
		gölgelendirici.değeriDeğiştir(BAKIŞ_AÇISI_DEĞERİ_ADI, açısı);
	}
	
	/** Bu bakışı baştaki ve sondaki bakışların verilen uzaklığa göre
	 * aradeğerlerine değiştirir. Verilen bakışların bu bakıştan farklı olduğunu
	 * varsayar. Bu bakışı döndürür. */
	public Bakış aradeğerleriniBul(
		final Bakış baştaki,
		final Bakış sondaki,
		final double uzaklık) {
		izdüşümBoyutu
			.aradeğerleriniBul(
				baştaki.izdüşümBoyutu,
				sondaki.izdüşümBoyutu,
				uzaklık);
		konumu.aradeğerleriniBul(baştaki.konumu, sondaki.konumu, uzaklık);
		boyutu = MatematikAracı
			.aradeğerleriniBul(baştaki.boyutu, sondaki.boyutu, uzaklık);
		açısı = MatematikAracı
			.aradeğerleriniBul(baştaki.açısı, sondaki.açısı, uzaklık);
		return this;
	}
	
	/** Bu bakışı verilen bakışla değiştirir. Bu bakışı döndürür. */
	public Bakış değiştir(final Bakış öbürü) {
		izdüşümBoyutu.değiştir(öbürü.izdüşümBoyutu);
		konumu.değiştir(öbürü.konumu);
		boyutu = öbürü.boyutu;
		açısı = öbürü.açısı;
		return this;
	}
}
