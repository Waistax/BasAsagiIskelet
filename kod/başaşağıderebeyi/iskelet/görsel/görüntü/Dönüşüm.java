/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.0.5 / 10 Tem 2021 / 13:14:16
 */
package başaşağıderebeyi.iskelet.görsel.görüntü;

import başaşağıderebeyi.iskelet.görsel.köşedizisi.*;
import başaşağıderebeyi.kütüphane.matematik.*;
import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;

import java.nio.*;

/** Sahnedeki nesnelerin ekran uzayına dönüşümünü yapan */
public class Dönüşüm {
	/** Dönüşümün oluşumlu köşe dizisinde kapladığı boyut. */
	public static final int BOYUTU = 3 + 2 + 1;
	
	/** Verilen oluşumlu köşe dizisinde dönüşüm için yer ekler. */
	public static void oluşumluKöşeDizisineEkle(OluşumluKöşeDizisi köşeDizisi) {
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(3);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(2);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(1);
	}
	
	/** Sahnede bulunduğu konum. */
	public final Yöney3 konumu;
	/** Sahnedeki boyutu. */
	public final Yöney2 boyutu;
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
	
	/** Dönüşümü oluşumlu köşe dizisine yükler. */
	public void yükle(OluşumluKöşeDizisi köşeDizisi) {
		yükle(köşeDizisi.yazılacakVerisi);
	}
	
	/** Dönüşümü tampona yükler. */
	public void yükle(final FloatBuffer tampon) {
		tampon
			.put((float)konumu.birinciBileşeniniEdin())
			.put((float)konumu.ikinciBileşeniniEdin())
			.put((float)konumu.üçüncüBileşeniniEdin())
			.put((float)boyutu.birinciBileşeniniEdin())
			.put((float)boyutu.ikinciBileşeniniEdin())
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
