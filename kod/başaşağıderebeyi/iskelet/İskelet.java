/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.1 / 3 Mar 2021 / 08:35:27
 * 
 * BaşAşağıMotor'dan alındı.
 * 0.1 / 28 Ağu 2020 / 23:22:45
 */
package başaşağıderebeyi.iskelet;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.olaylar.*;
import başaşağıderebeyi.kütüphane.girdi.*;
import başaşağıderebeyi.kütüphane.matematik.ortalama.*;
import başaşağıderebeyi.kütüphane.olay.*;

import java.util.*;

import org.lwjgl.glfw.*;

/** Uygulamayı çalıştıran ve döngü içerisinde güncelleyen genel kodları sağlayan
 * iskelet. */
public class İskelet {
	/** Ana sürümü. */
	public static final int ANA_SÜRÜMÜ = 0;
	/** Ara sürümü. */
	public static final int ARA_SÜRÜMÜ = 11;
	/** Yaması. */
	public static final int YAMASI = 1;
	/** Bütün sürümü. */
	public static final String SÜRÜM =
		ANA_SÜRÜMÜ + "." + ARA_SÜRÜMÜ + "." + YAMASI;
	
	/** İskeletin kullanılacak nesnesi. Herhangi bir zamanda birden fazla
	 * İskelet olması akıl dışı. */
	public static final İskelet NESNESİ = new İskelet();
	
	/** İskeleti çalıştırır. */
	public static void main(final String[] argümanlar) {
		NESNESİ.başlat();
	}
	
	private final AnaDöngü anaDöngü;
	
	private Ortalama tıklarınınOranınınOrtalaması;
	private Ortalama karelerininOranınınOrtalaması;
	private Map<String, Süreç> süreçleri;
	
	private OlaySağlayıcısı olaySağlayıcısı;
	private ÇiğGirdi çiğGirdisi;
	
	/** İskeletin dışarıdan tanımlanmasını engeller. */
	private İskelet() {
		anaDöngü = new AnaDöngü(
			this::oluştur,
			this::zamanıEdin,
			this::güncelle,
			this::saniyeSay,
			this::çiz,
			this::yokEt);
	}
	
	/** Ana döngüyü başlatır. */
	public void başlat() {
		anaDöngü.başla();
	}
	
	/** Ana döngüyü durdurur. */
	public void durdur() {
		anaDöngü.dur();
	}
	
	/** Şu anki zamanı GLFW saatinden saniye biriminde döndürür. Bu zamanın
	 * değeri tek başına anlamlı olmayabilir ama daha önceden edinilmiş başka
	 * bir zamanı bu zamandan çıkarak saniye biriminden geçen süre bulunur. */
	public double zamanıEdin() {
		return GLFW.glfwGetTime();
	}
	
	/** Şu anki zamanı JDK saatinden saniye biriminde döndürür. Bu zamanın
	 * değeri tek başına anlamlı olmayabilir ama daha önceden edinilmiş başka
	 * bir zamanı bu zamandan çıkarak saniye biriminden geçen süre bulunur. */
	public float sistemZamanınıEdin() {
		return System.nanoTime() / 1000000000.0F;
	}
	
	/** Ana döngünün ulaşmaya çalıştığı saniye başına tık oranını değiştirir. */
	public void istenenTıkOranınıDeğiştir(final double istenenTıkOranı) {
		anaDöngü.istenenTıkOranı = istenenTıkOranı;
	}
	
	/** Saniye başına tık oranını döndürür. Tık oranı güncelleme sayısıdır. */
	public double tıklarınınOranınıEdin() {
		return anaDöngü.tıklarınınOranınıEdin();
	}
	
	/** Saniye başına kare oranını döndürür. Kare oranı çizme sayısıdır. */
	public double karelerininOranınıEdin() {
		return anaDöngü.karelerininOranınıEdin();
	}
	
	/** Anlık olarak güncellenmeyi bekleyen tıkların sayısını döndürür. Bu sayı
	 * çizim yaparken aradeğerleri hesaplamada kullanılmalıdır. Ayrıca bu sayı
	 * birden büyükse iskelet istenen hızda çalışamıyor demektir. */
	public double güncellenmemişTıklarınıEdin() {
		return anaDöngü.güncellenmemişTıklarınıEdin();
	}
	
	/** Şu anın sırasını döndürür. */
	public long anınıEdin() {
		return anaDöngü.anınıEdin();
	}
	
	/** İskeletin çalıştığı her saniyedeki tık oranının ortalamasını
	 * döndürür. */
	public float tıklarınınOranınınOrtalamasınıEdin() {
		return tıklarınınOranınınOrtalaması.ortalamasınıEdin();
	}
	
	/** İskeletin çalıştığı her saniyedeki kare oranının ortalamasını
	 * döndürür. */
	public float karelerininOranınınOrtalamasınıEdin() {
		return karelerininOranınınOrtalaması.ortalamasınıEdin();
	}
	
	/** İskeletin olay dağıtıcısını döndürür. Bu anlık olarak çalışır;
	 * dağıtacağı olayları biriktirip hepsini güncellemeden önce dağıtır. */
	public OlayDağıtıcısı güncellemeOlaylarınınDağıtıcısınıEdin() {
		return olaySağlayıcısı.güncellemeOlaylarınınDağıtıcısı;
	}
	
	/** İskeletin çizim olaylarının dağıtıcısını döndürür. Bu dağıtıcı kendi
	 * haline bırakılmalıdır. */
	public OlayDağıtıcısı çizimOlaylarınınDağıtıcısınıEdin() {
		return olaySağlayıcısı.çizimOlaylarınınDağıtıcısı;
	}
	
	/** İskeletin çiğ girdisini döndürür. */
	public ÇiğGirdi girdisiniEdin() {
		return çiğGirdisi;
	}
	
	private void sayaçOlayınıDinle(final SayaçOlayı olay) {
		tıklarınınOranınınOrtalaması.ekle((float)tıklarınınOranınıEdin());
		karelerininOranınınOrtalaması.ekle((float)karelerininOranınıEdin());
		
		süreçleri.forEach((ad, süreç) -> {
			if (ad != "Oluşturma") {
				System.out.println(ad + " Süreci: " + süreç.ortalamasınıEdin());
				süreç.sıfırla();
			}
		});
	}
	
	private void güncellemeOlayınıDinle(final GüncellemeOlayı olay) {
		if (Gösterici.edin().penceresininKapatılmasınıEdin())
			durdur();
		
		çiğGirdisi.güncelle();
	}
	
	private void oluştur() {
		System.out.println("Oluşturuluyor...");
		final Süreç oluşturmaSüreci = new Süreç();
		oluşturmaSüreci.başla(sistemZamanınıEdin());
		System.out.println("Oluşturma A.");
		
		tıklarınınOranınınOrtalaması = new Ortalama();
		karelerininOranınınOrtalaması = new Ortalama();
		
		System.out.println("Oluşturma B.");
		süreçleriniOluştur(oluşturmaSüreci);
		
		çiğGirdisi = new ÇiğGirdi();
		olaySağlayıcısınıOluştur();
		System.out.println("Oluşturma C.");
		
		UygulamaYükleyicisi.NESNESİ.yükle();
		
		System.out.println("Oluşturma Ç.");
		Gösterici.edin().penceresiniOluştur();
		
		System.out.println("Oluşturma D.");
		olaySağlayıcısı.oluşturmaOlayınıDağıt();
		
		System.out.println("Oluşturma E.");
		oluşturmaSüreci.dur(sistemZamanınıEdin());
		System.out
			.println(
				"Oluşturma tamamlandı! Geçen süre: " +
					oluşturmaSüreci.toplamınıEdin());
	}
	
	private void süreçleriniOluştur(final Süreç oluşturmaSüreci) {
		süreçleri = new HashMap<>();
		süreçleri.put("Oluşturma", oluşturmaSüreci);
		süreçleri.put("Tık", new Süreç());
		süreçleri.put("Kare", new Süreç());
	}
	
	private void olaySağlayıcısınıOluştur() {
		olaySağlayıcısı = new OlaySağlayıcısı();
		
		olaySağlayıcısı.güncellemeOlaylarınınDağıtıcısı
			.dinleyiciyiEkle(
				new DinleyiciBilgisi<>(
					SayaçOlayı.class,
					this::sayaçOlayınıDinle));
		
		olaySağlayıcısı.güncellemeOlaylarınınDağıtıcısı
			.dinleyiciyiEkle(
				new DinleyiciBilgisi<>(
					GüncellemeOlayı.class,
					this::güncellemeOlayınıDinle)
						.önceliğiniDeğiştir(Öncelik.ÖNCE));
	}
	
	private void yokEt() {
		System.out.println("Yok ediliyor...");
		
		olaySağlayıcısı.yokEtmeOlayınıDağıt();
		
		Gösterici.edin().yokEt();
		Yükleyici.NESNESİ.yokEt();
		
		System.out
			.println(
				"Ortalama Tık Oranı: " + tıklarınınOranınınOrtalamasınıEdin());
		System.out
			.println(
				"Ortalama Kare Oranı: " +
					karelerininOranınınOrtalamasınıEdin());
	}
	
	private void güncelle() {
		süreçleri.get("Tık").başla((float)zamanıEdin());
		olaySağlayıcısı.güncellemeOlayınıDağıt();
		süreçleri.get("Tık").dur((float)zamanıEdin());
	}
	
	private void saniyeSay() {
		olaySağlayıcısı.sayaçOlayınıDağıt();
	}
	
	private void çiz() {
		süreçleri.get("Kare").başla((float)zamanıEdin());
		olaySağlayıcısı.çizimOlayınıDağıt();
		Gösterici.edin().göster();
		süreçleri.get("Kare").dur((float)zamanıEdin());
	}
}
