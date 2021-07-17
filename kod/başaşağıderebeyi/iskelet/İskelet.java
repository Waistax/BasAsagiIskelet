/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.1 / 3 Mar 2021 / 08:35:27
 * 
 * BaşAşağıMotor'dan alındı.
 * 0.1 / 28 Ağu 2020 / 23:22:45
 */
package başaşağıderebeyi.iskelet;

import static başaşağıderebeyi.iskelet.görsel.yükleyici.Yükleyici.*;
import static başaşağıderebeyi.kütüphane.günlük.SistemGünlüğü.*;
import static başaşağıderebeyi.kütüphane.olay.Öncelik.*;
import static org.lwjgl.glfw.GLFW.*;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.olaylar.*;
import başaşağıderebeyi.kütüphane.girdi.*;
import başaşağıderebeyi.kütüphane.günlük.*;
import başaşağıderebeyi.kütüphane.matematik.ölçüm.*;
import başaşağıderebeyi.kütüphane.olay.*;

import java.nio.file.*;
import java.util.*;

/** Uygulamayı çalıştıran ve döngü içerisinde güncelleyen genel kodları sağlayan
 * iskelet. */
public class İskelet {
	/** Ana sürümü. */
	public static final int ANA_SÜRÜMÜ = 2;
	/** Ara sürümü. */
	public static final int ARA_SÜRÜMÜ = 10;
	/** Yaması. */
	public static final int YAMASI = 7;
	/** Bütün sürümü. */
	public static final String SÜRÜM =
		ANA_SÜRÜMÜ + "." + ARA_SÜRÜMÜ + "." + YAMASI;
	
	/** İskeletin kullanılacak nesnesi. Herhangi bir zamanda birden fazla
	 * İskelet olması akıl dışı. */
	public static final İskelet İSKELET = new İskelet();
	
	/** İskeletin uygulamaları yükleyeceği klasörün varsayılan konumu. İskeleti
	 * çalıştırırken verilmiş bir klasör yoksa bu kullanılır. */
	public static final String VARSAYILAN_UYGULAMA_KLASÖRÜ = "uygulamalar";
	
	private static final GünlükKaydedici GÜNLÜK_KAYDEDİCİSİ =
		new GünlükKaydedici();
	
	private final AnaDöngü anaDöngü;
	private final UygulamaYükleyici uygulamaYükleyicisi;
	
	private Ortalama tıkHızınınOrtalaması;
	private Ortalama kareHızınınOrtalaması;
	private Map<String, Süreç> süreçleri;
	
	private OlaySağlayıcısı olaySağlayıcısı;
	private Girdi girdisi;
	
	/** İskeletin dışarıdan tanımlanmasını engeller. */
	private İskelet() {
		anaDöngü = new AnaDöngü(
			this::oluştur,
			this::zamanıEdin,
			this::güncelle,
			this::saniyeSay,
			this::çiz,
			this::yokEt);
		uygulamaYükleyicisi = new UygulamaYükleyici();
	}
	
	/** Ana döngüyü başlatır. */
	public void başlat() {
		Thread.currentThread().setName("İskelet");
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
		return glfwGetTime();
	}
	
	/** Şu anki zamanı JDK saatinden saniye biriminde döndürür. Bu zamanın
	 * değeri tek başına anlamlı olmayabilir ama daha önceden edinilmiş başka
	 * bir zamanı bu zamandan çıkarak saniye biriminden geçen süre bulunur. */
	public double sistemZamanınıEdin() {
		return System.nanoTime() / 1000000000.0;
	}
	
	/** Ana döngünün ulaşmaya çalıştığı saniye başına tık hızını değiştirir. */
	public void istenenTıkHızınıDeğiştir(final double istenenTıkHızı) {
		anaDöngü.istenenTıkHızı = istenenTıkHızı;
	}
	
	/** Hertz biriminden tık hızını döndürür. Tık hızı, motorun birim zamandaki
	 * güncelleme sayısıdır. */
	public int tıkHızınıEdin() {
		return anaDöngü.tıkHızınıEdin();
	}
	
	/** Hertz biriminden kare hızını döndürür. Kare hızı, motorun birim
	 * zamandaki çizme sayısıdır. */
	public int kareHızınıEdin() {
		return anaDöngü.kareHızınıEdin();
	}
	
	/** Anlık olarak güncellenmeyi bekleyen tıkların sayısını döndürür. Bu sayı
	 * çizim yaparken aradeğerleri hesaplamada kullanılmalıdır. Ayrıca bu sayı
	 * birden büyükse iskelet istenen hızda çalışamıyor demektir. */
	public double güncellenmemişTıkSayısınıEdin() {
		return anaDöngü.güncellenmemişTıkSayısınıEdin();
	}
	
	/** Şu anın sırasını döndürür. */
	public long anınıEdin() {
		return anaDöngü.anınıEdin();
	}
	
	/** Verilen uygulama nesnesinin bilgisini döndürür. Bu nesne iskelet
	 * tarafından uygulamalar yüklenirken tanımlanmış olan nesnese olmalıdır.
	 * Herhangi bir nesne işe yaramaz. */
	public UygulamaBilgisi uygulamaBilgisiniEdin(final Object nesne) {
		return uygulamaYükleyicisi.uygulamaBilgisiniEdin(nesne);
	}
	
	/** İskeletin çalışmaya başlamasından beri her saniyedeki tık hızlarının
	 * ortalamasını döndürür. */
	public double tıkHızınınOrtalamasınıEdin() {
		return tıkHızınınOrtalaması.ortalamasınıEdin();
	}
	
	/** İskeletin çalışmaya başlamasından beri her saniyedeki kare hızlarının
	 * ortalamasını döndürür. */
	public double kareHızınınOrtalamasınıEdin() {
		return kareHızınınOrtalaması.ortalamasınıEdin();
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
	public Girdi girdisiniEdin() {
		return girdisi;
	}
	
	private void sayaçOlayınıDinle(final SayaçOlayı olay) {
		tıkHızınınOrtalaması.örnekle(tıkHızınıEdin());
		kareHızınınOrtalaması.örnekle(kareHızınıEdin());
	}
	
	private void güncellemeOlayınıDinle(final GüncellemeOlayı olay) {
		if (Gösterici.edin().penceresininKapatılmasınıEdin())
			durdur();
		
		girdisi.güncelle();
	}
	
	private void oluştur() {
		KONSOL.yaz("Oluşturuluyor...");
		KONSOL.yaz("Başaşağı İskelet s." + SÜRÜM);
		final Süreç oluşturmaSüreci = new Süreç();
		oluşturmaSüreci.başla(sistemZamanınıEdin());
		
		tıkHızınınOrtalaması = new Ortalama();
		kareHızınınOrtalaması = new Ortalama();
		
		süreçleriniOluştur(oluşturmaSüreci);
		
		girdisi = new Girdi();
		olaySağlayıcısınıOluştur();
		
		uygulamaYükleyicisi.yükle(Path.of(VARSAYILAN_UYGULAMA_KLASÖRÜ));
		
		Gösterici.edin().penceresiniOluştur();
		
		olaySağlayıcısı.oluşturmaOlayınıDağıt();
		
		oluşturmaSüreci.dur(sistemZamanınıEdin());
		KONSOL
			.yaz(
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
					this::güncellemeOlayınıDinle).önceliğiniDeğiştir(ÖNCE));
	}
	
	private void yokEt() {
		KONSOL.yaz("Yok ediliyor...");
		
		olaySağlayıcısı.yokEtmeOlayınıDağıt();
		
		final Gösterici gösterici = Gösterici.edin();
		if (gösterici != null) {
			gösterici.yokEt();
			YÜKLEYİCİ.yokEt();
		}
		
		KONSOL.yaz("Ortalama Tık Oranı: " + tıkHızınınOrtalamasınıEdin());
		KONSOL.yaz("Ortalama Kare Oranı: " + kareHızınınOrtalamasınıEdin());
		
		KONSOL.yaz("Sistem günlüğü kaydediliyor...");
		GÜNLÜK_KAYDEDİCİSİ.kaydet(NESNESİ);
	}
	
	private void güncelle() {
		süreçleri.get("Tık").başla(zamanıEdin());
		olaySağlayıcısı.güncellemeOlayınıDağıt();
		süreçleri.get("Tık").dur(zamanıEdin());
	}
	
	private void saniyeSay() {
		olaySağlayıcısı.sayaçOlayınıDağıt();
	}
	
	private void çiz() {
		süreçleri.get("Kare").başla(zamanıEdin());
		olaySağlayıcısı.çizimOlayınıDağıt();
		Gösterici.edin().göster();
		süreçleri.get("Kare").dur(zamanıEdin());
	}
}
