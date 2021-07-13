/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.6 / 5 Mar 2021 / 20:07:03
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import başaşağıderebeyi.iskelet.görsel.köşedizisi.*;
import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;

import java.nio.*;

/** Belli bir yazı şeklindeki belli bir sesin şeklini saklayan nesne. */
public class SesŞekli {
	/** Ses şeklinin oluşumlu köşe dizisinde kapladığı boyut. */
	public static final int BOYUTU = 4;
	
	/** Verilen oluşumlu köşe dizisinde ses şekli için yer ekler. */
	public static void oluşumluKöşeDizisineEkle(
		final OluşumluKöşeDizisi köşeDizisi) {
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(4);
	}
	
	/** Sesin dokudaki kenarlarının konumları. Sırasıyla sol, sağ, üst ve alt
	 * kenarın konumları. */
	public final Yöney4 dokuKonumu;
	
	SesŞekli(
		final double solKonumu,
		final double sağKonumu,
		final double üstKonumu,
		final double altKonumu,
		final double dokusununBoyutu) {
		dokuKonumu =
			(Yöney4)new Yöney4(solKonumu, sağKonumu, üstKonumu, altKonumu)
				.böl(dokusununBoyutu);
	}
	
	/** Ses şeklini tampona yükler. */
	public void yükle(final FloatBuffer tampon) {
		tampon
			.put((float)dokuKonumu.birinciBileşeniniEdin())
			.put((float)dokuKonumu.ikinciBileşeniniEdin())
			.put((float)dokuKonumu.üçüncüBileşeniniEdin())
			.put((float)dokuKonumu.dördüncüBileşeniniEdin());
	}
}
