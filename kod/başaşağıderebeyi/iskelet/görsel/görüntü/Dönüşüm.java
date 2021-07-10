/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.0.5 / 10 Tem 2021 / 13:14:16
 */
package başaşağıderebeyi.iskelet.görsel.görüntü;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.kütüphane.matematik.*;
import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;

import java.nio.*;

/** Sahnedeki nesnelerin ekran uzayına dönüşümünü yapan */
public class Dönüşüm {
	private static final String DÖNÜŞÜM_KONUMU_DEĞERİ_ADI = "donusumKonumu";
	private static final String DÖNÜŞÜM_BOYUTU_DEĞERİ_ADI = "donusumBoyutu";
	private static final String DÖNÜŞÜM_AÇISI_DEĞERİ_ADI = "donusumAcisi";
	
	/** Dönüşümü yüklemek için kullanılacak değerleri gölgelendiricide bulur. */
	public static void değerlerininKonumlarınıBul(
		final Gölgelendirici gölgelendirici) {
		gölgelendirici.değerinKonumunuBul(DÖNÜŞÜM_KONUMU_DEĞERİ_ADI);
		gölgelendirici.değerinKonumunuBul(DÖNÜŞÜM_BOYUTU_DEĞERİ_ADI);
		gölgelendirici.değerinKonumunuBul(DÖNÜŞÜM_AÇISI_DEĞERİ_ADI);
	}
	
	/** Sahnede bulunduğu konum. */
	public final Yöney3 konumu;
	/** Sahnedeki boyutu. */
	public Yöney2 boyutu;
	/** Sahnede bulunduğu açı. */
	public double açısı;
	
	/** Orijinde birim boyutlarla tanımlar. */
	public Dönüşüm() {
		konumu = new Yöney3();
		boyutu = new Yöney2(Yöney2.BİR);
	}
	
	/** Verilenin aynısı tanımlar. */
	public Dönüşüm(final Dönüşüm dönüşüm) {
		this();
		değiştir(dönüşüm);
	}
	
	/** Dönüşümü gölgelendiriciye yükler. */
	public void yükle(final Gölgelendirici gölgelendirici) {
		gölgelendirici.değeriDeğiştir(DÖNÜŞÜM_KONUMU_DEĞERİ_ADI, konumu);
		gölgelendirici.değeriDeğiştir(DÖNÜŞÜM_BOYUTU_DEĞERİ_ADI, boyutu);
		gölgelendirici.değeriDeğiştir(DÖNÜŞÜM_AÇISI_DEĞERİ_ADI, açısı);
	}
	
	/** Dönüşümü tampona yükler. */
	public void yükle(final FloatBuffer tampon) {
		tampon
			.put((float)konumu.birinciBileşeniniEdin())
			.put((float)konumu.ikinciBileşeniniEdin())
			.put((float)konumu.üçüncüBileşeniniEdin())
			.put((float)boyutu.birinciBileşeniniEdin())
			.put((float)boyutu.ikinciBileşeniniEdin())
			.put((float)boyutu.üçüncüBileşeniniEdin())
			.put((float)açısı);
	}
	
	/** Bu dönüşümü baştaki ve sondaki dönüşümlerin verilen uzaklığa göre
	 * aradeğerlerine değiştirir. Verilen dönüşümlerin bu dönüşümden farklı
	 * olduğunu varsayar. Bu dönüşümü döndürür. */
	public Dönüşüm aradeğerleriniBul(
		final Dönüşüm baştaki,
		final Dönüşüm sondaki,
		final double uzaklık) {
		boyutu.aradeğerleriniBul(baştaki.boyutu, sondaki.boyutu, uzaklık);
		konumu.aradeğerleriniBul(baştaki.konumu, sondaki.konumu, uzaklık);
		açısı = MatematikAracı
			.aradeğerleriniBul(baştaki.açısı, sondaki.açısı, uzaklık);
		return this;
	}
	
	/** Bu dönüşümü verilen dönüşümle değiştirir. Bu dönüşümü döndürür. */
	public Dönüşüm değiştir(final Dönüşüm öbürü) {
		konumu.değiştir(öbürü.konumu);
		boyutu.değiştir(öbürü.boyutu);
		açısı = öbürü.açısı;
		return this;
	}
}
