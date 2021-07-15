/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.12.0 / 20 Mar 2021 / 16:14:38
 */
package başaşağıderebeyi.iskelet;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.görsel.kaynak.*;
import başaşağıderebeyi.iskelet.görsel.yazı.*;
import başaşağıderebeyi.kütüphane.günlük.*;

import java.nio.file.*;
import java.util.*;

import org.lwjgl.glfw.*;

/** Uygulamanın arşivdeki bilgilerini içiren nesne. */
public class UygulamaBilgisi {
	/** Uygulamanın arşivi. */
	public final Arşiv arşivi;
	
	Class<?> sınıfı;
	private Object nesnesi;
	private Uygulama uygulaması;
	
	UygulamaBilgisi(final Path dosyası) {
		arşivi = new Arşiv(dosyası);
		sınıflarıİşle();
		tanımla();
		veriKlasörünüOluştur();
	}
	
	/** Verilen konumdaki resimi yükler. */
	public ResimBilgisi resimYükle(final String konumu) {
		return new ResimBilgisi(kaynağınıBul(konumu));
	}
	
	/** Verilen konumlardaki yazı şeklini yükler. En iyi küçültme ve büyütme
	 * yöntemlerini kullanır. */
	public YazıŞekli yazıŞekliYükle(
		final String dokusununKonumu,
		final String bilgisininKonumu) {
		return new YazıŞekli(
			kaynağınıBul(dokusununKonumu),
			kaynağınıBul(bilgisininKonumu));
	}
	
	/** Verilen konumlardaki yazı şeklini yükler. Verilen küçültme ve büyütme
	 * yöntemlerini kullanır. */
	public YazıŞekli yazıŞekliYükle(
		final String dokusununKonumu,
		final int küçültmeYöntemi,
		final int büyütmeYöntemi,
		final String bilgisininKonumu) {
		return new YazıŞekli(
			kaynağınıBul(dokusununKonumu),
			küçültmeYöntemi,
			büyütmeYöntemi,
			kaynağınıBul(bilgisininKonumu));
	}
	
	/** Verilen konumlardaki gölgelendiricileri yükler. */
	public Gölgelendirici gölgelendiriciYükle(
		final String köşesininKonumu,
		final String beneğininKonumu) {
		return new Gölgelendirici(
			kaynağınıBul(köşesininKonumu),
			kaynağınıBul(beneğininKonumu));
	}
	
	/** Konumu verilen resmi GLFW resmi olarak yükler ve döndürür. */
	public GLFWImage glfwResmiYükle(final String konumu) {
		return Yükleyici.NESNESİ.glfwResmiYükle(kaynağınıBul(konumu));
	}
	
	/** Konumu verilen resmi ekran kartına yükler ve işaretçisini döndürür. En
	 * iyi küçültme ve büyütme yöntemlerini kullanır. */
	public int dokuYükle(final String konumu) {
		return Yükleyici.NESNESİ.dokuYükle(kaynağınıBul(konumu));
	}
	
	/** Konumu verilen resmi ekran kartına yükler ve işaretçisini döndürür.
	 * Verilen küçültme ve büyütme yöntemlerini kullanır. */
	public int dokuYükle(
		final String konumu,
		final int küçültmeYöntemi,
		final int büyütmeYöntemi) {
		return Yükleyici.NESNESİ
			.dokuYükle(kaynağınıBul(konumu), küçültmeYöntemi, büyütmeYöntemi);
	}
	
	/** Konumu verilen metin belgesini yükler ve dize olarak döndürür. */
	public String yazıyıYükle(final String konumu) {
		return Yükleyici.NESNESİ.yazıyıYükle(kaynağınıBul(konumu));
	}
	
	/** Konumu verilen metin belgesini yükler ve satırlarını döndürür. */
	public List<String> satırlarınıYükle(final String konumu) {
		return Yükleyici.NESNESİ.satırlarınıYükle(kaynağınıBul(konumu));
	}
	
	/** Verilen konumdaki kaynağın dosya yolunu döndürür. Kaynaklar, uygulamanın
	 * arşivinin içinde bulunan dosyalardır. Bu dosyalar arşivden çıkartılır ve
	 * geçici dosyalara kopyalanır. Bu kopyanın dosya yolunu döndürür. Eğer
	 * arşivin içerisinde verilen konumda bir şey bulunmuyorsa null döndürür. */
	public Path kaynağınıBul(final String konumu) {
		return arşivi.kaynakları.get(konumu);
	}
	
	/** Verilen konumdaki verinin dosya yolunu döndürür. Veriler, uygulamaya
	 * ayrılmış veri klasöründeki dosyalardır. Dönderilen dosya yolunda bir
	 * dosya ya da klasör bulunmayabilir. */
	public Path verisiniBul(final String konumu) {
		return Path.of("veriler", uygulaması.adı(), konumu);
	}
	
	/** Uygulamanın sınıfını döndürür. */
	public Class<?> sınıfınıEdin() {
		return sınıfı;
	}
	
	/** Uygulamanın iskelet tarafından oluşturulmuş nesnesini döndürür. */
	public Object nesnesiniEdin() {
		return nesnesi;
	}
	
	/** Uygulamanın bilgilerini içeren dipnotunu döndürür. */
	public Uygulama uygulamasınıEdin() {
		return uygulaması;
	}
	
	private void sınıflarıİşle() {
		for (final Class<?> sınıf : arşivi.sınıfları) {
			SistemGünlüğü.KONSOL.yaz(sınıf);
			final Uygulama uygulama = sınıf.getAnnotation(Uygulama.class);
			if (uygulama != null) {
				if (sınıfı != null)
					throw new RuntimeException(
						"Birden fazla uygulama sınıfı var!");
				sınıfı = sınıf;
				uygulaması = uygulama;
			}
		}
		
		if (sınıfı == null)
			throw new RuntimeException("Uygulama sınıfı bulunamadı!");
	}
	
	private void tanımla() {
		try {
			nesnesi =
				sınıfı.getConstructor(UygulamaBilgisi.class).newInstance(this);
		} catch (final Throwable hata) {
			throw new RuntimeException(
				"Uygulamanın nesnesi tanımlanamadı!",
				hata);
		}
	}
	
	private void veriKlasörünüOluştur() {
		Path.of("veriler", uygulaması.adı()).toFile().mkdirs();
	}
}
