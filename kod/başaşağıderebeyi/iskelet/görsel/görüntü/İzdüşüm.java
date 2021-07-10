/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.0.4 / 10 Tem 2021 / 11:46:57
 */
package başaşağıderebeyi.iskelet.görsel.görüntü;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;

/** Çizimlerin ekran kartı uzayına aktarılmasını sağlayacak araç. Orta noktası
 * her zaman orijindir. */
public class İzdüşüm {
	private static final String İZDÜŞÜM_DEĞERİ_ADI = "izdusum";
	
	/** Bakışı yüklemek için kullanılacak değerleri gölgelendiricide bulur. */
	public static void değerlerininKonumlarınıBul(
		final Gölgelendirici gölgelendirici) {
		gölgelendirici.değerinKonumunuBul(İZDÜŞÜM_DEĞERİ_ADI);
	}
	
	/** Ekran kartı uzayına izdüşürülecek alanın boyutu. */
	public final Yöney3 boyutu;
	
	/** Verilen boyutlarla tanımlar. */
	public İzdüşüm(Yöney3 boyutu) {
		this.boyutu = boyutu;
	}
	
	/** Birim boyutlarla tanımlar. */
	public İzdüşüm() {
		this(new Yöney3(Yöney3.BİR));
	}
	
	/** İzdüşümü gölgelendiriciye yükler. */
	public void yükle(final Gölgelendirici gölgelendirici) {
		gölgelendirici.değeriDeğiştir(İZDÜŞÜM_DEĞERİ_ADI, boyutu);
	}
	
	/** Bu izdüşümü baştaki ve sondaki izdüşümlerin verilen uzaklığa göre
	 * aradeğerlerine değiştirir. Verilen izdüşümlerin bu izdüşümden farklı
	 * olduğunu varsayar. Bu izdüşümü döndürür. */
	@Deprecated
	public İzdüşüm aradeğerleriniBul(
		final İzdüşüm baştaki,
		final İzdüşüm sondaki,
		final double uzaklık) {
		boyutu.aradeğerleriniBul(baştaki.boyutu, sondaki.boyutu, uzaklık);
		return this;
	}
	
	/** Bu izdüşümü verilen izdüşümle değiştirir. Bu izdüşümü döndürür. */
	public İzdüşüm değiştir(final İzdüşüm öbürü) {
		boyutu.değiştir(öbürü.boyutu);
		return this;
	}
}
