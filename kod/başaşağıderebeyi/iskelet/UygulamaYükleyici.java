/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.10.1 / 19 Mar 2021 / 09:45:27
 * 
 * Mending Engine'den biraz alındı.
 * ? / ? Kas 2016 / ?
 */
package başaşağıderebeyi.iskelet;

import başaşağıderebeyi.kütüphane.günlük.*;

import java.nio.file.*;
import java.util.*;

class UygulamaYükleyici {
	private final Map<Object, UygulamaBilgisi> uygulamaları;
	
	UygulamaYükleyici() {
		uygulamaları = new HashMap<>();
	}
	
	void yükle(final Path klasör) {
		assert İskelet.NESNESİ.anınıEdin() == -1L;
		SistemGünlüğü.KONSOL
			.yaz(
				"Uygulamalar " +
					klasör.toAbsolutePath().toString() +
					" klasöründen yükleniyor...");
		try {
			Arşiv
				.arşivDosyalarınıBul(klasör)
				.parallel()
				.forEach(this::dosyayıİşle);
		} catch (final Throwable hata) {
			throw new RuntimeException(
				"Uygulamalar yüklenirken bir hata oluştu!",
				hata);
		}
	}
	
	UygulamaBilgisi uygulamaBilgisiniEdin(final Object nesne) {
		return uygulamaları.get(nesne);
	}
	
	private void dosyayıİşle(final Path dosya) {
		try {
			SistemGünlüğü.KONSOL
				.yaz("Arşiv " + dosya.toString() + " yükleniyor...");
			final UygulamaBilgisi bilgisi = new UygulamaBilgisi(dosya);
			bilgiyiEkle(bilgisi);
			SistemGünlüğü.KONSOL
				.yaz("Arşiv " + dosya.toString() + " yüklendi!");
		} catch (final Exception hata) {
			throw new RuntimeException(
				"Uygulama " + dosya.toString() + " yüklenemedi!",
				hata);
		}
	}
	
	private synchronized void bilgiyiEkle(final UygulamaBilgisi bilgisi) {
		uygulamaları.put(bilgisi.nesnesiniEdin(), bilgisi);
	}
}
