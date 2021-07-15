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
import java.util.stream.*;

/** Bir Java arşivini temsil eder. Arşivdeki sınıfları yükler ve kaynakları
 * geçici dosyalara kopyalar. */
public class Arşiv {
	/** Verilen klasördeki Java arşivlerini bir akış olarak döndürür. */
	public static Stream<Path> arşivDosyalarınıBul(final Path klasör)
		throws IOException {
		return Files
			.find(
				klasör,
				Integer.MAX_VALUE,
				(dosya, nitelikleri) -> dosya
					.getFileName()
					.toString()
					.toLowerCase()
					.endsWith(".jar"));
	}
	
	/** Arşivdeki sınıflar. */
	public final Set<Class<?>> sınıfları;
	/** Arşivin içerisindeki kod dışı kaynaklar. */
	public final Map<String, Path> kaynakları;
	
	/** Verilen dosyadaki arşivi yükler. */
	public Arşiv(final Path dosya) {
		sınıfları = new HashSet<>();
		kaynakları = new HashMap<>();
		dosyayıİşle(dosya);
	}
	
	private void dosyayıİşle(final Path dosya) {
		final String dosyanınYolu = dosya.toAbsolutePath().toString();
		try (JarFile arşiv = new JarFile(dosyanınYolu)) {
			arşiviİşle(
				arşiv,
				new URLClassLoader(
					new URL[] { new URL("jar:file:" + dosyanınYolu + "!/") }));
		} catch (final Exception hata) {
			throw new RuntimeException(
				"Arşiv " + dosya.toString() + " yüklenemedi!",
				hata);
		}
	}
	
	private void arşiviİşle(
		final JarFile arşivDosyası,
		final URLClassLoader kaynakYükleyicisi) {
		for (final Enumeration<JarEntry> arşivdekiDosyalar =
			arşivDosyası.entries(); arşivdekiDosyalar.hasMoreElements();)
			arşivGirdisiniİşle(
				arşivdekiDosyalar.nextElement(),
				kaynakYükleyicisi);
	}
	
	private void arşivGirdisiniİşle(
		final JarEntry girdi,
		final URLClassLoader kaynakYükleyicisi) {
		final String adı = girdi.getName();
		try {
			if (adı.endsWith(".class"))
				sınıfıYükle(adı);
			else
				kaynağıYükle(adı, kaynakYükleyicisi);
		} catch (final Throwable hata) {
			throw new RuntimeException(
				"Arşiv girdisi " + adı + " işlenemedi!",
				hata);
		}
	}
	
	private void sınıfıYükle(final String adı) throws ClassNotFoundException {
		if (adı.endsWith("module-info.class"))
			return;
		sınıfları
			.add(Arşiv.class.getClassLoader().loadClass(sınıfınAdınıBul(adı)));
	}
	
	private String sınıfınAdınıBul(final String adı) {
		return adı.substring(0, adı.length() - 6).replace('/', '.');
	}
	
	private void kaynağıYükle(
		final String adı,
		final URLClassLoader kaynakYükleyicisi)
		throws IOException {
		final URL bulucusu = kaynakYükleyicisi.findResource(adı);
		final File geçiciDosya = Files.createTempFile(null, null).toFile();
		geçiciDosya.deleteOnExit();
		
		try (
			FileOutputStream yazıcı = new FileOutputStream(geçiciDosya);
			InputStream okuyucu = bulucusu.openStream()) {
			yazıcı.write(okuyucu.readAllBytes());
		}
		kaynakları.put(adı, geçiciDosya.toPath());
	}
}
