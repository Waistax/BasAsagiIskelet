/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.4 / 4 Mar 2021 / 20:43:28
 */
package başaşağıderebeyi.iskelet.kumhavuzu;

import static başaşağıderebeyi.kütüphane.matematik.MatematikAracı.*;
import static org.lwjgl.glfw.GLFW.*;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.kütüphane.girdi.*;
import başaşağıderebeyi.kütüphane.matematik.sayısal.*;
import başaşağıderebeyi.kütüphane.olay.*;

import java.util.*;

/** Görselleştiriciyi dener. */
public class GörselleştiriciDenemesi implements Uygulama {
	public static void main(final String[] args) {
		new GörselleştiriciDenemesi();
	}
	
	private final İskelet çalıştıranİskelet;
	
	private Görselleştirici görselleştirici;
	private Set<Dönüşüm> dönüşümleri;
	private Map<Dönüşüm, Dönüşüm> eskiDönüşümleri;
	private Map<Dönüşüm, Dönüşüm> çizilecekDönüşümleri;
	
	private GörselleştiriciDenemesi() {
		final Gösterici gösterici = new Gösterici(
			640,
			360,
			"Görselleştirici Denemesi Sürüm: " + İskelet.SÜRÜM,
			false,
			16,
			0,
			new Yöney4());
		çalıştıranİskelet =
			new İskelet(10.0F, this, gösterici, new Yükleyici());
		çalıştıranİskelet.başla();
	}
	
	@Override
	public void oluştur() {
		çalıştıranİskelet.olayDağıtıcısınıEdin().dinleyicileriniEkle(this);
		
		final Dizey4 izdüşümDizeyi =
			new Dizey4().izdüşümDizeyineÇevir(1280.0F, 720.0F, 20.0F);
		
		final int nesneSayısı = 72;
		görselleştirici = new Görselleştirici(
			çalıştıranİskelet.yükleyicisi,
			izdüşümDizeyi,
			nesneSayısı,
			"denemeResmi");
		
		int sıraBüyüklüğü = (int)kökünüBul(nesneSayısı);
		if (sıraBüyüklüğü % 2 == 0)
			sıraBüyüklüğü++;
		
		dönüşümleri = new HashSet<>();
		eskiDönüşümleri = new HashMap<>();
		çizilecekDönüşümleri = new HashMap<>();
		
		for (int i = 0; i < nesneSayısı; i++) {
			final Dönüşüm dönüşüm = new Dönüşüm();
			dönüşüm.konumu.birinciBileşeni =
				(i % sıraBüyüklüğü - sıraBüyüklüğü / 2) * 105.0F;
			dönüşüm.konumu.ikinciBileşeni =
				(i / sıraBüyüklüğü - sıraBüyüklüğü / 2) * 105.0F;
			dönüşümleri.add(dönüşüm);
			görselleştirici.dönüşümüEkle(dönüşüm);
		}
		
		dönüşümleri.forEach(dönüşümü -> {
			eskiDönüşümleri.put(dönüşümü, new Dönüşüm());
			çizilecekDönüşümleri.put(dönüşümü, new Dönüşüm());
		});
		
		çalıştıranİskelet.göstericisi
			.imleciDeğiştir(
				çalıştıranİskelet.göstericisi
					.imleçOluştur(
						çalıştıranİskelet.yükleyicisi.glfwResmiYükle("imleç"),
						0,
						0));
	}
	
	@Override
	public void yokEt() {
		
	}
	
	@Override
	public void güncelle() {
		final ÇiğGirdi girdi = çalıştıranİskelet.girdisiniEdin();
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_ESCAPE).salınmasınıEdin())
			çalıştıranİskelet.dur();
		
		final float yatayBoyut =
			girdi.imlecininKonumu.birinciBileşeni / 1280.0F * 100.0F;
		final float dikeyBoyut =
			girdi.imlecininKonumu.ikinciBileşeni / 720.0F * 360.0F;
		
		dönüşümleri.parallelStream().forEach(dönüşümü -> {
			eskiDönüşümleri.get(dönüşümü).değiştir(dönüşümü);
			dönüşümü.biçimi
				.bileşenleriniDeğiştir(
					yatayBoyut,
					yatayBoyut,
					radyanaÇevir(dikeyBoyut));
		});
	}
	
	@Override
	public void çiz() {
		dönüşümleri
			.parallelStream()
			.forEach(
				dönüşümü -> çizilecekDönüşümleri
					.get(dönüşümü)
					.aradeğerleriniBul(
						eskiDönüşümleri.get(dönüşümü),
						dönüşümü,
						(float)çalıştıranİskelet.güncellenmemişTıklarınıEdin())
					.güncelle());
		çizilecekDönüşümleri.values().forEach(görselleştirici::dönüşümüEkle);
		görselleştirici
			.çiz((float)çalıştıranİskelet.güncellenmemişTıklarınıEdin());
	}
	
	@Dinleyici
	public void sayaçOlayınıDinle(final SayaçOlayı olay) {
		System.out
			.println(
				"Tık Oranı: " +
					çalıştıranİskelet.tıklarınınOranınıEdin() +
					" Kare Oranı: " +
					çalıştıranİskelet.karelerininOranınıEdin());
	}
}
