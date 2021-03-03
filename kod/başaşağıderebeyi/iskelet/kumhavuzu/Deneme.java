/**
 * başaşağıderebeyi.iskelet.kumhavuzu.Deneme.java
 * 0.1 / 3 Mar 2021 / 09:18:32
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.kumhavuzu;

import static başaşağıderebeyi.kütüphane.matematik.MatematikAracı.*;
import static java.lang.Math.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.kütüphane.girdi.*;
import başaşağıderebeyi.kütüphane.matematik.*;
import başaşağıderebeyi.kütüphane.olay.*;

/** İskeleti dener. */
public class Deneme implements Uygulama {
	public static void main(final String[] args) {
		new Deneme();
	}
	
	private final İskelet çalıştıranİskelet;
	private final Yöney4 temizlenmeRengi;
	private final Yöney4 öncekiTemizlenmeRengi;
	private final Yöney4 kullanılacakTemizlenmeRengi;
	
	private int tekerleğinToplamDevri;
	
	Deneme() {
		temizlenmeRengi = new Yöney4();
		öncekiTemizlenmeRengi = new Yöney4();
		kullanılacakTemizlenmeRengi = new Yöney4();
		Gösterici gösterici = new Gösterici(
			1920,
			1080,
			"Baş Aşağı Derebeyi " + İskelet.SÜRÜM,
			true,
			0,
			1,
			temizlenmeRengi);
		çalıştıranİskelet = new İskelet(10.0F, this, gösterici);
		çalıştıranİskelet.başla();
	}
	
	@Override
	public void oluştur() {
		çalıştıranİskelet.olayDağıtıcısınıEdin().dinleyicileriniEkle(this);
	}
	
	@Override
	public void yokEt() {
	}
	
	@Override
	public void güncelle() {
		ÇiğGirdi girdi = çalıştıranİskelet.girdisiniEdin();
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_ESCAPE).salınmasınıEdin())
			çalıştıranİskelet.dur();
		
		tekerleğinToplamDevri += girdi.tekerleğininDevriniEdin();
		
		öncekiTemizlenmeRengi.değiştir(temizlenmeRengi);
		
		temizlenmeRengi.birinciBileşeni =
			sıkıştır((float)pow(1.2, tekerleğinToplamDevri), 0.0F, 1.0F);
		temizlenmeRengi.ikinciBileşeni = sıkıştır(
			girdi.imlecininKonumu.birinciBileşeni / 1920.0F,
			0.0F,
			1.0F);
		temizlenmeRengi.üçüncüBileşeni = sıkıştır(
			girdi.imlecininKonumu.ikinciBileşeni / 1080.0F,
			0.0F,
			1.0F);
	}
	
	@Override
	public void çiz() {
		kullanılacakTemizlenmeRengi
			.aradeğerleriniBul(
				öncekiTemizlenmeRengi,
				temizlenmeRengi,
				(float)çalıştıranİskelet.güncellenmemişTıklarınıEdin());
		glClearColor(
			kullanılacakTemizlenmeRengi.birinciBileşeni,
			kullanılacakTemizlenmeRengi.ikinciBileşeni,
			kullanılacakTemizlenmeRengi.üçüncüBileşeni,
			kullanılacakTemizlenmeRengi.dördüncüBileşeni);
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
