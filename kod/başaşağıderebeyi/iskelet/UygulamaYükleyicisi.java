/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.10.1 / 19 Mar 2021 / 09:45:27
 * 
 * Mending Engine'den biraz alındı.
 * ? / ? Kas 2016 / ?
 */
package başaşağıderebeyi.iskelet;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.jar.*;

/** Uygulamaları yükleyecek olan araç. */
public class UygulamaYükleyicisi {
	/** Uygulamaların saklandığı klasörün yolu. Bu yol programın çalıştığı yere
	 * göredir. */
	public static final String UYGULAMALARIN_KLASÖRÜ = "uygulamalar";
	/** Uygulamaların uzantısı. */
	public static final String UYGULAMALARIN_UZANTISI = ".jar";
	
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
	public void yükle(final String altKlasörü) {
		File yüklenecekKlasör = Path
			.of(
				UYGULAMALARIN_KLASÖRÜ +
					(altKlasörü == null ? "" : "/" + altKlasörü))
			.toFile();
		
		if (!yüklenecekKlasör.exists())
			throw new RuntimeException(
				"Uygulamaların yükleneceği klasör " +
					yüklenecekKlasör.getPath() +
					" bulunamadı!");
		
		final File[] dosyalar = yüklenecekKlasör
			.listFiles((konumu, adı) -> dosyaAdınınGeçerliliğiniBul(adı));
		
		for (final File dosya : dosyalar)
			dosyayıİşle(dosya);
	}
	
	private boolean dosyaAdınınGeçerliliğiniBul(final String adı) {
		return adı.length() > UYGULAMALARIN_UZANTISI.length() &&
			adı
				.substring(
					adı.length() - UYGULAMALARIN_UZANTISI.length(),
					adı.length())
				.equalsIgnoreCase(UYGULAMALARIN_UZANTISI);
	}
	
	private void dosyayıİşle(final File dosya) {
		final String dosyanınYolu = dosya.getAbsolutePath();
		try (JarFile arşiv = new JarFile(dosyanınYolu)) {
			System.out.println("Arşiv " + dosya.getPath() + " yükleniyor...");
			arşiviİşle(
				arşiv,
				new URLClassLoader(
					new URL[] { new URL("jar:file:" + dosyanınYolu + "!/") }));
			System.out.println("Arşiv " + dosya.getPath() + " yüklendi!");
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
		System.out.println("Sınıf " + sınıf + " yüklendi!");
		
		if (sınıf.isAnnotationPresent(Uygulama.class)) {
			if (bilgisi.sınıfı != null)
				throw new RuntimeException("Birden fazla uygulama sınıfı var!");
			bilgisi.sınıfı = sınıf;
			System.out.println("Uygulama sınıfı bulundu!");
		}
	}
	
	private void kaynağıYükle(
		final URLClassLoader sınıfYükleyicisi,
		final UygulamaBilgisi bilgisi,
		final String adı)
		throws Exception {
		URL bulucusu = sınıfYükleyicisi.findResource(adı);
		File geçiciDosya = File.createTempFile("" + bulucusu.hashCode(), null);
		geçiciDosya.deleteOnExit();
		
		try (
			FileOutputStream yazıcı = new FileOutputStream(geçiciDosya);
			InputStream okuyucu = bulucusu.openStream()) {
			yazıcı.write(okuyucu.readAllBytes());
		}
		
		bilgisi.kaynakları.put(adı, geçiciDosya.toURI());
		System.out.println("Kaynak " + adı + " yüklendi!");
		System.out
			.println("Geçici dosya konumu: " + geçiciDosya.getAbsolutePath());
	}
}
