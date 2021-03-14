/**
 * başaşağıderebeyi.iskelet.kumhavuzu.GörselleştiriciDenemesi.java
 * 0.4 / 4 Mar 2021 / 20:43:28
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.kumhavuzu;

import static başaşağıderebeyi.kütüphane.matematik.MatematikAracı.*;
import static org.lwjgl.glfw.GLFW.*;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.kütüphane.girdi.*;
import başaşağıderebeyi.kütüphane.matematik.*;
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
		
		for (int i = 0; i < nesneSayısı; i++) {
			final Dönüşüm dönüşüm = new Dönüşüm();
			dönüşüm.konumu.birinciBileşeni =
				(i % sıraBüyüklüğü - sıraBüyüklüğü / 2) * 105.0F;
			dönüşüm.konumu.ikinciBileşeni =
				(i / sıraBüyüklüğü - sıraBüyüklüğü / 2) * 105.0F;
			dönüşümleri.add(dönüşüm);
			görselleştirici.dönüşümüEkle(dönüşüm);
		}
		
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
		
		dönüşümleri
			.stream()
			.parallel()
			.forEach(
				dönüşümü -> dönüşümü.biçimi
					.bileşenleriniDeğiştir(
						yatayBoyut,
						yatayBoyut,
						radyanaÇevir(dikeyBoyut)));
		
		görselleştirici.güncelle();
	}
	
	@Override
	public void çiz() {
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
