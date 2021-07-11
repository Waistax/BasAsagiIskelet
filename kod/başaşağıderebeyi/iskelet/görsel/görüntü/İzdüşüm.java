/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.0.4 / 10 Tem 2021 / 11:46:57
 */
package başaşağıderebeyi.iskelet.görsel.görüntü;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;

/** Çizimlerin ekran kartı uzayına aktarılmasını sağlayacak araç. Orta noktası
 * her zaman orijindir. */
public class İzdüşüm implements Yumuşatılabilir {
	private static final String İZDÜŞÜM_DEĞERİ_ADI = "izdusum";
	
	/** İzdüşümü yüklemek için kullanılacak değerleri gölgelendiricide bulur. */
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
	
	@Override
	public Yumuşatılabilir aradeğerleriniBul(
		final Yumuşatılabilir baştaki,
		final Yumuşatılabilir sondaki,
		final double uzaklık) {
		İzdüşüm baştakiİzdüşüm = (İzdüşüm)baştaki;
		İzdüşüm sondakiİzdüşüm = (İzdüşüm)sondaki;
		boyutu
			.aradeğerleriniBul(
				baştakiİzdüşüm.boyutu,
				sondakiİzdüşüm.boyutu,
				uzaklık);
		return this;
	}
	
	@Override
	public Yumuşatılabilir değiştir(final Yumuşatılabilir öbürü) {
		İzdüşüm öbürİzdüşüm = (İzdüşüm)öbürü;
		boyutu.değiştir(öbürİzdüşüm.boyutu);
		return this;
	}
}
