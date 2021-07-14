/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.6.3 / 14 Tem 2021 / 19:43:32
 */
package başaşağıderebeyi.iskelet;

import java.nio.file.*;

/** İskeleti çalıştıran ve kütüphaneleri yükleyen sınıf. */
public class Başlatıcı {
	/** İskeletin kütüphaneleri yükleyeceği klasörün konumu. */
	public static final String KÜTÜPHANE_KLASÖRÜ = "kütüphaneler";
	/** İskeletin uygulamaları yükleyeceği klasörün varsayılan konumu. İskeleti
	 * çalıştırırken verilmiş bir klasör yoksa bu kullanılır. */
	public static final String VARSAYILAN_UYGULAMA_KLASÖRÜ = "uygulamalar";
	
	private static Path kütüphanelerinKlasörü;
	private static Path uygulamalarınKlasörü;
	
	/** İskeleti başlatır. */
	public static void main(final String... argümanlar) {
		Thread.currentThread().setName("İskelet");
		kütüphanelerinKlasörü = Path.of(KÜTÜPHANE_KLASÖRÜ);
		new KütüphaneYükleyicisi().yükle(kütüphanelerinKlasörü);
		
		uygulamalarınKlasörü = argümanlar.length != 0 ?
			Path.of("", argümanlar) :
			Path.of(VARSAYILAN_UYGULAMA_KLASÖRÜ);
		
		İskelet.NESNESİ.başlat();
	}
	
	/** Kütüphanelerin yüklendiği klasörü döndürür. */
	public static Path kütüphanelerinKlasörünüEdin() {
		return kütüphanelerinKlasörü;
	}
	
	/** Uygulamaların yüklendiği klasörü döndürür. */
	public static Path uygulamalarınKlasörünüEdin() {
		return uygulamalarınKlasörü;
	}
}
