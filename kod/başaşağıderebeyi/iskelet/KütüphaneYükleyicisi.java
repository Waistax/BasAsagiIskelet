/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.6.2 / 14 Tem 2021 / 18:34:25
 */
package başaşağıderebeyi.iskelet;

import java.nio.file.*;

class KütüphaneYükleyicisi {
	KütüphaneYükleyicisi() {
	}
	
	void yükle(final Path klasör) {
		assert İskelet.NESNESİ.anınıEdin() == -1L;
		try {
			Arşiv
				.arşivDosyalarınıBul(klasör)
				.parallel()
				.forEach(this::dosyayıİşle);
		} catch (final Throwable hata) {
			throw new RuntimeException(
				"Kütüphaneler yüklenirken bir hata oluştu!",
				hata);
		}
	}
	
	private void dosyayıİşle(final Path dosya) {
		new Arşiv(dosya);
	}
}
