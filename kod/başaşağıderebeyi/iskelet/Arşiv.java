/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.6.1 / 14 Tem 2021 / 14:17:41
 */
package başaşağıderebeyi.iskelet;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.jar.*;

/** Bir Java arşivini temsil eder. Arşivdeki sınıfları yükler ve kaynakları
 * geçici dosyalara kopyalar. */
public class Arşiv {
	/** Arşivdeki sınıflar. */
	public final Set<Class<?>> sınıfları;
	/** Arşivin içerisindeki kod dışı kaynaklar. */
	public final Map<String, Path> kaynakları;
	
	/** Verilen dosyadaki arşivi yükler. */
	public Arşiv(final File dosya) {
		sınıfları = new HashSet<>();
		kaynakları = new HashMap<>();
		dosyayıİşle(dosya);
	}
	
	private void dosyayıİşle(final File dosya) {
		final String dosyanınYolu = dosya.getAbsolutePath();
		try (JarFile arşiv = new JarFile(dosyanınYolu)) {
			arşiviİşle(
				arşiv,
				new URLClassLoader(
					new URL[] { new URL("jar:file:" + dosyanınYolu + "!/") }));
		} catch (final Exception hata) {
			throw new RuntimeException(
				"Arşiv " + dosya.getPath() + " yüklenemedi!",
				hata);
		}
	}
	
	private void arşiviİşle(
		final JarFile arşivDosyası,
		final URLClassLoader sınıfYükleyicisi) {
		for (final Enumeration<JarEntry> arşivdekiDosyalar =
			arşivDosyası.entries(); arşivdekiDosyalar.hasMoreElements();)
			arşivGirdisiniİşle(
				arşivdekiDosyalar.nextElement(),
				sınıfYükleyicisi);
	}
	
	private void arşivGirdisiniİşle(
		final JarEntry girdi,
		final URLClassLoader sınıfYükleyicisi) {
		final String adı = girdi.getName();
		try {
			if (adı.endsWith(".class"))
				sınıfıYükle(sınıfYükleyicisi, adı);
			else
				kaynağıYükle(sınıfYükleyicisi, adı);
		} catch (final Throwable hata) {
			throw new RuntimeException(
				"Arşiv girdisi " + adı + " işlenemedi!",
				hata);
		}
	}
	
	private void sınıfıYükle(
		final URLClassLoader sınıfYükleyicisi,
		final String adı)
		throws ClassNotFoundException {
		if ("module-info.class".equalsIgnoreCase(adı))
			return;
		sınıfları
			.add(
				sınıfYükleyicisi
					.loadClass(
						adı.substring(0, adı.length() - 6).replace('/', '.')));
	}
	
	private void kaynağıYükle(
		final URLClassLoader sınıfYükleyicisi,
		final String adı)
		throws IOException {
		final URL bulucusu = sınıfYükleyicisi.findResource(adı);
		final File geçiciDosya =
			File.createTempFile("" + bulucusu.hashCode(), null);
		geçiciDosya.deleteOnExit();
		
		try (
			FileOutputStream yazıcı = new FileOutputStream(geçiciDosya);
			InputStream okuyucu = bulucusu.openStream()) {
			yazıcı.write(okuyucu.readAllBytes());
		}
		kaynakları.put(adı, geçiciDosya.toPath());
	}
}
