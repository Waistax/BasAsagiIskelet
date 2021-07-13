/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.12.0 / 20 Mar 2021 / 16:14:38
 */
package başaşağıderebeyi.iskelet;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.görsel.kaynak.*;
import başaşağıderebeyi.iskelet.görsel.yazı.*;

import java.lang.reflect.*;
import java.nio.file.*;
import java.util.*;

import org.lwjgl.glfw.*;

/** Uygulamanın arşivdeki bilgilerini içiren nesne. */
public class UygulamaBilgisi {
	final Map<String, Path> kaynakları;
	Class<?> sınıfı;
	private Object nesnesi;
	
	UygulamaBilgisi() {
		kaynakları = new HashMap<>();
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
	
	/** Verilen konumdaki kaynağın tanımlayıcısını döndürür. Eğer arşivin
	 * içerisinde verilen konumda bir şey bulunmuyorsa null döndürür. */
	public Path kaynağınıBul(final String konumu) {
		return kaynakları.get(konumu);
	}
	
	/** Uygulamanın sınıfını döndürür. */
	public Class<?> sınıfınıEdin() {
		return sınıfı;
	}
	
	/** Uygulamanın iskelet tarafından oluşturulmuş nesnesini döndürür. */
	public Object nesnesiniEdin() {
		return nesnesi;
	}
	
	void tanımla()
		throws InstantiationException,
			IllegalAccessException,
			IllegalArgumentException,
			InvocationTargetException,
			NoSuchMethodException,
			SecurityException {
		nesnesi =
			sınıfı.getConstructor(UygulamaBilgisi.class).newInstance(this);
	}
}
