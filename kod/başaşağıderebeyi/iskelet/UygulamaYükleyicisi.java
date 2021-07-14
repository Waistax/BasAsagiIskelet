/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.10.1 / 19 Mar 2021 / 09:45:27
 * 
 * Mending Engine'den biraz alındı.
 * ? / ? Kas 2016 / ?
 */
package başaşağıderebeyi.iskelet;

import başaşağıderebeyi.kütüphane.günlük.*;

import java.io.*;
import java.util.*;

/** Uygulamaları yükleyecek olan araç. */
public class UygulamaYükleyicisi {
	/** Uygulama yükleyicisinin kullanılacak nesnesi. Uygulamaları yüklemek için
	 * yeni bir uygulama yükleyicisi oluşturmak gereksiz. */
	public static final UygulamaYükleyicisi NESNESİ = new UygulamaYükleyicisi();
	
	/** Yüklenmiş olan uygulamalar ve nesnelerinin haritası. */
	public final Map<Object, UygulamaBilgisi> uygulamaları;
	
	/** Boş tanımlar. */
	private UygulamaYükleyicisi() {
		uygulamaları = new HashMap<>();
	}
	
	/** Klasördeki bütün uygulamaları yükler. */
	void yükle(final File klasör) {
		assert İskelet.NESNESİ.anınıEdin() == -1L;
		SistemGünlüğü.KONSOL
			.yaz(
				"Uygulamalar " +
					klasör.getAbsolutePath() +
					" klasöründen yükleniyor...");
		for (final File dosya : dosyalarınıBul(klasör))
			dosyayıİşle(dosya);
	}
	
	private File[] dosyalarınıBul(final File klasör) {
		if (!klasör.exists())
			throw new RuntimeException(
				"Uygulamaların yükleneceği klasör " +
					klasör.getPath() +
					" bulunamadı!");
		
		if (!klasör.isDirectory())
			throw new RuntimeException(
				"Uygulamaların yükleneceği klasör " +
					klasör.getPath() +
					" klasör değil!");
		
		return klasör
			.listFiles((konumu, adı) -> dosyaAdınınGeçerliliğiniBul(adı));
	}
	
	private boolean dosyaAdınınGeçerliliğiniBul(final String adı) {
		return adı.length() > 4 &&
			adı
				.substring(adı.length() - 4, adı.length())
				.equalsIgnoreCase(".jar");
	}
	
	private void dosyayıİşle(final File dosya) {
		try {
			SistemGünlüğü.KONSOL
				.yaz("Arşiv " + dosya.getPath() + " yükleniyor...");
			final UygulamaBilgisi bilgisi = new UygulamaBilgisi(dosya);
			uygulamaları.put(bilgisi.nesnesiniEdin(), bilgisi);
			SistemGünlüğü.KONSOL.yaz("Arşiv " + dosya.getPath() + " yüklendi!");
		} catch (final Exception hata) {
			throw new RuntimeException(
				"Uygulama " + dosya.getPath() + " yüklenemedi!",
				hata);
		}
	}
}
