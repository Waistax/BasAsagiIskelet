/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.12.0 / 20 Mar 2021 / 16:14:38
 */
package başaşağıderebeyi.iskelet;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.görsel.kaynak.*;

import java.net.*;
import java.util.*;

import org.lwjgl.glfw.*;

/** Uygulamanın arşivdeki bilgilerini içiren nesne. */
public class UygulamaBilgisi {
	final Map<String, URI> kaynakları;
	Object nesnesi;
	
	UygulamaBilgisi() {
		kaynakları = new HashMap<>();
	}
	
	/** Verilen kaynaktaki resimi yükler. */
	public ResimBilgisi resimYükle(final String konumu) {
		return new ResimBilgisi(kaynağınıBul(konumu));
	}
	
	/** Verilen kaynaklardaki gölgelendiricileri yükler. */
	public Gölgelendirici gölgelendiriciYükle(
		final String köşeninKonumu,
		final String beneğinKonumu) {
		return new Gölgelendirici(
			kaynağınıBul(köşeninKonumu),
			kaynağınıBul(beneğinKonumu));
	}
	
	/** Kaynağı verilen resmi GLFW resmi olarak yükler ve döndürür. */
	public GLFWImage glfwResmiYükle(final String konumu) {
		return Yükleyici.NESNESİ.glfwResmiYükle(kaynağınıBul(konumu));
	}
	
	/** Kaynağı verilen resmi ekran kartına yükler ve işaretçisini döndürür. */
	public int dokuYükle(final String konumu) {
		return Yükleyici.NESNESİ.dokuYükle(kaynağınıBul(konumu));
	}
	
	/** Kaynağı verilen metin belgesini yükler ve dize olarak döndürür. */
	public String yazıyıYükle(final String konumu) {
		return Yükleyici.NESNESİ.yazıyıYükle(kaynağınıBul(konumu));
	}
	
	/** Kaynağı verilen metin belgesini yükler ve satırlarını döndürür. */
	public List<String> satırlarınıYükle(final String konumu) {
		return Yükleyici.NESNESİ.satırlarınıYükle(kaynağınıBul(konumu));
	}
	
	/** Verilen konumdaki kaynağın tanımlayıcısını döndürür. Eğer arşivin
	 * içerisinde verilen konumda bir şey bulunmuyorsa null döndürür. */
	public URI kaynağınıBul(final String konumu) {
		return kaynakları.get(konumu);
	}
	
	/** Uygulamanın iskelet tarafından oluşturulmuş nesnesini döndürür. */
	public Object nesnesiniEdin() {
		return nesnesi;
	}
}
