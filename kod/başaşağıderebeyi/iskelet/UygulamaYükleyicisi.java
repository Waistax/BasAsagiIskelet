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
import java.net.*;
import java.util.*;
import java.util.jar.*;

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
		final String dosyanınYolu = dosya.getAbsolutePath();
		try (JarFile arşiv = new JarFile(dosyanınYolu)) {
			SistemGünlüğü.KONSOL
				.yaz("Arşiv " + dosya.getPath() + " yükleniyor...");
			arşiviİşle(
				arşiv,
				new URLClassLoader(
					new URL[] { new URL("jar:file:" + dosyanınYolu + "!/") }));
			SistemGünlüğü.KONSOL.yaz("Arşiv " + dosya.getPath() + " yüklendi!");
		} catch (final Exception hata) {
			throw new RuntimeException(
				"Uygulama " + dosya.getPath() + " yüklenemedi!",
				hata);
		}
	}
	
	private void arşiviİşle(
		final JarFile arşiv,
		final URLClassLoader sınıfYükleyicisi)
		throws Exception {
		final UygulamaBilgisi bilgisi = new UygulamaBilgisi();
		for (final Enumeration<JarEntry> arşivdekiDosyalar =
			arşiv.entries(); arşivdekiDosyalar.hasMoreElements();)
			arşivGirdisiniİşle(
				arşivdekiDosyalar.nextElement(),
				sınıfYükleyicisi,
				bilgisi);
		bilgisi.tanımla();
		uygulamaları.put(bilgisi.nesnesiniEdin(), bilgisi);
	}
	
	private void arşivGirdisiniİşle(
		final JarEntry girdi,
		final URLClassLoader sınıfYükleyicisi,
		final UygulamaBilgisi bilgisi) {
		final String adı = girdi.getName();
		try {
			if (adı.endsWith(".class"))
				sınıfıYükle(sınıfYükleyicisi, bilgisi, adı);
			else
				kaynağıYükle(sınıfYükleyicisi, bilgisi, adı);
		} catch (final Throwable hata) {
			throw new RuntimeException(
				"Arşiv girdisi " + adı + " işlenemedi!",
				hata);
		}
	}
	
	private void sınıfıYükle(
		final URLClassLoader sınıfYükleyicisi,
		final UygulamaBilgisi bilgisi,
		final String adı)
		throws Exception {
		if (adı.equalsIgnoreCase("module-info.class"))
			return;
		
		final Class<?> sınıf = sınıfYükleyicisi
			.loadClass(adı.substring(0, adı.length() - 6).replace('/', '.'));
		SistemGünlüğü.KONSOL.yaz("Sınıf " + sınıf.getName() + " yüklendi!");
		
		if (sınıf.isAnnotationPresent(Uygulama.class)) {
			if (bilgisi.sınıfı != null)
				throw new RuntimeException("Birden fazla uygulama sınıfı var!");
			bilgisi.sınıfı = sınıf;
			SistemGünlüğü.KONSOL.yaz("Uygulama sınıfı bulundu!");
		}
	}
	
	private void kaynağıYükle(
		final URLClassLoader sınıfYükleyicisi,
		final UygulamaBilgisi bilgisi,
		final String adı)
		throws Exception {
		final URL bulucusu = sınıfYükleyicisi.findResource(adı);
		final File geçiciDosya =
			File.createTempFile("" + bulucusu.hashCode(), null);
		geçiciDosya.deleteOnExit();
		
		try (
			FileOutputStream yazıcı = new FileOutputStream(geçiciDosya);
			InputStream okuyucu = bulucusu.openStream()) {
			yazıcı.write(okuyucu.readAllBytes());
		}
		
		bilgisi.kaynakları.put(adı, geçiciDosya.toURI());
		SistemGünlüğü.KONSOL
			.yaz(
				"Kaynak " +
					adı +
					" yüklendi! Geçici dosya konumu: " +
					geçiciDosya.getAbsolutePath());
	}
}
