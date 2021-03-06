/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.12.0 / 20 Mar 2021 / 16:14:38
 */
package başaşağıderebeyi.iskelet;

import static başaşağıderebeyi.iskelet.görsel.yükleyici.Yükleyici.*;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.görsel.yazı.*;
import başaşağıderebeyi.iskelet.görsel.yükleyici.*;

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
	
	/** Veri klasöründen verilen konumdaki resimi yükler. */
	public ResimBilgisi resimYükle(final String konumu) {
		return new ResimBilgisi(verisiniBul(konumu));
	}
	
	/** Arşivden verilen konumdaki resimi yükler. */
	public ResimBilgisi arşivdenResimYükle(final String konumu) {
		return new ResimBilgisi(kaynağınıBul(konumu));
	}
	
	/** Veri klasöründen verilen konumlardaki yazı şeklini yükler. En iyi
	 * küçültme ve büyütme yöntemlerini kullanır. */
	public YazıŞekli yazıŞekliYükle(
		final String dokusununKonumu,
		final String bilgisininKonumu) {
		return new YazıŞekli(
			verisiniBul(dokusununKonumu),
			verisiniBul(bilgisininKonumu));
	}
	
	/** Arşivden verilen konumlardaki yazı şeklini yükler. En iyi küçültme ve
	 * büyütme yöntemlerini kullanır. */
	public YazıŞekli arşivdenYazıŞekliYükle(
		final String dokusununKonumu,
		final String bilgisininKonumu) {
		return new YazıŞekli(
			kaynağınıBul(dokusununKonumu),
			kaynağınıBul(bilgisininKonumu));
	}
	
	/** Veri klasöründen verilen konumlardaki yazı şeklini yükler. Verilen
	 * küçültme ve büyütme yöntemlerini kullanır. */
	public YazıŞekli yazıŞekliYükle(
		final String dokusununKonumu,
		final int küçültmeYöntemi,
		final int büyütmeYöntemi,
		final String bilgisininKonumu) {
		return new YazıŞekli(
			verisiniBul(dokusununKonumu),
			küçültmeYöntemi,
			büyütmeYöntemi,
			verisiniBul(bilgisininKonumu));
	}
	
	/** Arşivden verilen konumlardaki yazı şeklini yükler. Verilen küçültme ve
	 * büyütme yöntemlerini kullanır. */
	public YazıŞekli arşivdenYazıŞekliYükle(
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
	
	/** Veri klasöründen verilen konumlardaki gölgelendiricileri yükler. */
	public Gölgelendirici gölgelendiriciYükle(
		final String köşesininKonumu,
		final String beneğininKonumu) {
		return new Gölgelendirici(
			verisiniBul(köşesininKonumu),
			verisiniBul(beneğininKonumu));
	}
	
	/** Arşivden verilen konumlardaki gölgelendiricileri yükler. */
	public Gölgelendirici arşivdenGölgelendiriciYükle(
		final String köşesininKonumu,
		final String beneğininKonumu) {
		return new Gölgelendirici(
			kaynağınıBul(köşesininKonumu),
			kaynağınıBul(beneğininKonumu));
	}
	
	/** Veri klasöründen verilen konumdaki resmi GLFW resmi olarak yükler ve
	 * döndürür. */
	public GLFWImage GLFWResmiYükle(final String konumu) {
		return YÜKLEYİCİ.GLFWResmiYükle(verisiniBul(konumu));
	}
	
	/** Arşivden verilen konumdaki resmi GLFW resmi olarak yükler ve
	 * döndürür. */
	public GLFWImage arşivdenGLFWResmiYükle(final String konumu) {
		return YÜKLEYİCİ.GLFWResmiYükle(kaynağınıBul(konumu));
	}
	
	/** Veri klasöründen verilen konumdaki resmi ekran kartına yükler ve
	 * işaretçisini döndürür. En iyi küçültme ve büyütme yöntemlerini
	 * kullanır. */
	public int dokuYükle(final String konumu) {
		return YÜKLEYİCİ.dokuYükle(verisiniBul(konumu));
	}
	
	/** Arşivden verilen konumdaki resmi ekran kartına yükler ve işaretçisini
	 * döndürür. En iyi küçültme ve büyütme yöntemlerini kullanır. */
	public int arşivdenDokuYükle(final String konumu) {
		return YÜKLEYİCİ.dokuYükle(kaynağınıBul(konumu));
	}
	
	/** Veri klasöründen verilen konumdaki resmi ekran kartına yükler ve
	 * işaretçisini döndürür. Verilen küçültme ve büyütme yöntemlerini
	 * kullanır. */
	public int dokuYükle(
		final String konumu,
		final int küçültmeYöntemi,
		final int büyütmeYöntemi) {
		return YÜKLEYİCİ
			.dokuYükle(verisiniBul(konumu), küçültmeYöntemi, büyütmeYöntemi);
	}
	
	/** Arşivden verilen konumdaki resmi ekran kartına yükler ve işaretçisini
	 * döndürür. Verilen küçültme ve büyütme yöntemlerini kullanır. */
	public int arşivdenDokuYükle(
		final String konumu,
		final int küçültmeYöntemi,
		final int büyütmeYöntemi) {
		return YÜKLEYİCİ
			.dokuYükle(kaynağınıBul(konumu), küçültmeYöntemi, büyütmeYöntemi);
	}
	
	/** Veri klasöründen verilen konumdaki metin belgesini yükler ve bütün
	 * satırları tek bir dize olarak döndürür. */
	public String yazıYükle(final String konumu) {
		return YÜKLEYİCİ.yazıYükle(verisiniBul(konumu));
	}
	
	/** Arşivden verilen konumdaki metin belgesini yükler ve bütün satırları tek
	 * bir dize olarak döndürür. */
	public String arşivdenYazıYükle(final String konumu) {
		return YÜKLEYİCİ.yazıYükle(kaynağınıBul(konumu));
	}
	
	/** Veri klasöründen verilen konumdaki metin belgesini yükler ve her
	 * satırını ayrı bir dize olarak döndürür. */
	public List<String> satırlarYükle(final String konumu) {
		return YÜKLEYİCİ.satırlarYükle(verisiniBul(konumu));
	}
	
	/** Arşivden verilen konumdaki metin belgesini yükler ve her satırını ayrı
	 * bir dize olarak döndürür. */
	public List<String> arşivdenSatırlarYükle(final String konumu) {
		return YÜKLEYİCİ.satırlarYükle(kaynağınıBul(konumu));
	}
	
	/** Verilen konumdaki verinin dosya yolunu döndürür. Veriler, uygulamaya
	 * ayrılmış veri klasöründeki dosyalardır. Dönderilen dosya yolunda bir
	 * dosya ya da klasör bulunmayabilir. */
	public Path verisiniBul(final String konumu) {
		return Path.of("veriler", uygulaması.adı(), konumu);
	}
	
	/** Verilen konumdaki kaynağın dosya yolunu döndürür. Kaynaklar, uygulamanın
	 * arşivinin içinde bulunan dosyalardır. Bu dosyalar arşivden çıkartılır ve
	 * geçici dosyalara kopyalanır. Bu kopyanın dosya yolunu döndürür. Eğer
	 * arşivin içerisinde verilen konumda bir şey bulunmuyorsa null döndürür. */
	public Path kaynağınıBul(final String konumu) {
		return arşivi.kaynakları.get(konumu);
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
