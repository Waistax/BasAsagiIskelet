/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.1 / 3 Mar 2021 / 09:18:32
 */
package başaşağıderebeyi.iskelet.kumhavuzu;

import static başaşağıderebeyi.kütüphane.matematik.MatematikAracı.*;
import static java.lang.Math.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.görsel.köşedizisi.*;
import başaşağıderebeyi.iskelet.olaylar.*;
import başaşağıderebeyi.kütüphane.girdi.*;
import başaşağıderebeyi.kütüphane.matematik.sayısal.*;
import başaşağıderebeyi.kütüphane.olay.*;

import java.lang.annotation.*;

/** İskeleti dener. */
public class Deneme implements Uygulama {
	public static void main(final String[] args) {
		new Deneme();
	}
	
	private final İskelet çalıştıranİskelet;
	
	private KöşeDizisi köşeDizisi;
	private int tekerleğinToplamDevri;
	private float boyutu;
	private float öncekiBoyutu;
	private float çizilecekBoyutu;
	private Gölgelendirici gölgelendiricisi;
	
	Deneme() {
		final Gösterici gösterici = new Gösterici(
			1280,
			720,
			"Deneme Sürümü: " + İskelet.SÜRÜM,
			false,
			0,
			0,
			new Yöney4());
		çalıştıranİskelet =
			new İskelet(10.0F, this, gösterici, new Yükleyici());
		çalıştıranİskelet.başla();
	}
	
	@Override
	public void oluştur() {
		çalıştıranİskelet
			.olaylarınınDağıtıcısınıEdin()
			.dinleyicileriniEkle(this);
		
		köşeDizisi =
			new KöşeDizisi(çalıştıranİskelet.yükleyicisi, GL_TRIANGLE_STRIP);
		
		köşeDizisi
			.durağanKöşeTamponuNesnesiEkle(
				2,
				memAllocFloat(8)
					.put(-0.5F)
					.put(-0.5F)
					.put(+0.5F)
					.put(-0.5F)
					.put(-0.5F)
					.put(+0.5F)
					.put(+0.5F)
					.put(+0.5F));
		
		final Yöney4 solAltKöşeninRengi = new Yöney4(1.0F, 0.0F, 0.0F, 1.0F);
		final Yöney4 sağAltKöşeninRengi = new Yöney4(0.0F, 0.0F, 1.0F, 1.0F);
		final Yöney4 solÜstKöşeninRengi = new Yöney4(1.0F, 0.0F, 0.0F, 1.0F);
		final Yöney4 sağÜstKöşeninRengi = new Yöney4(0.0F, 0.0F, 1.0F, 1.0F);
		
		if (true) {
			solAltKöşeninRengi.rgbdenHsluva();
			sağAltKöşeninRengi.rgbdenHsluva();
			solÜstKöşeninRengi.rgbdenHsluva();
			sağÜstKöşeninRengi.rgbdenHsluva();
		}
		
		köşeDizisi
			.durağanKöşeTamponuNesnesiEkle(
				4,
				memAllocFloat(16)
					.put(solAltKöşeninRengi.birinciBileşeni)
					.put(solAltKöşeninRengi.ikinciBileşeni)
					.put(solAltKöşeninRengi.üçüncüBileşeni)
					.put(solAltKöşeninRengi.dördüncüBileşeni)
					.put(sağAltKöşeninRengi.birinciBileşeni)
					.put(sağAltKöşeninRengi.ikinciBileşeni)
					.put(sağAltKöşeninRengi.üçüncüBileşeni)
					.put(sağAltKöşeninRengi.dördüncüBileşeni)
					.put(solÜstKöşeninRengi.birinciBileşeni)
					.put(solÜstKöşeninRengi.ikinciBileşeni)
					.put(solÜstKöşeninRengi.üçüncüBileşeni)
					.put(solÜstKöşeninRengi.dördüncüBileşeni)
					.put(sağÜstKöşeninRengi.birinciBileşeni)
					.put(sağÜstKöşeninRengi.ikinciBileşeni)
					.put(sağÜstKöşeninRengi.üçüncüBileşeni)
					.put(sağÜstKöşeninRengi.dördüncüBileşeni));
		
		gölgelendiricisi = new Gölgelendirici(
			çalıştıranİskelet.yükleyicisi,
			"renkliDikdörtgen");
		gölgelendiricisi.bağla();
		gölgelendiricisi.değerinKonumunuBul("boyutu");
	}
	
	@Override
	public void yokEt() {
	}
	
	@Override
	public void güncelle() {
		final ÇiğGirdi girdi = çalıştıranİskelet.girdisiniEdin();
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_ESCAPE).salınmasınıEdin())
			çalıştıranİskelet.dur();
		
		tekerleğinToplamDevri += girdi.tekerleğininDevri;
		öncekiBoyutu = boyutu;
		boyutu = (float)pow(1.05, tekerleğinToplamDevri);
	}
	
	@Override
	public void çiz() {
		çizilecekBoyutu = aradeğerleriniBul(
			öncekiBoyutu,
			boyutu,
			(float)çalıştıranİskelet.güncellenmemişTıklarınıEdin());
		
		gölgelendiricisi.değeriDeğiştir("boyutu", çizilecekBoyutu);
		köşeDizisi.çiz();
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
	
	@Override
	public Class<? extends Annotation> annotationType() {
		// TODO Auto-generated method stub
		return null;
	}
}
