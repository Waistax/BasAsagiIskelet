/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.1 / 3 Mar 2021 / 09:18:32
 */
package başaşağıderebeyi.iskelet.kumhavuzu;

import static başaşağıderebeyi.kütüphane.matematik.MatematikAracı.*;
import static java.lang.Math.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.kütüphane.girdi.*;
import başaşağıderebeyi.kütüphane.matematik.sayısal.*;
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
	private Gölgelendirici gölgelendiricisi;
	
	Deneme() {
		temizlenmeRengi = new Yöney4();
		öncekiTemizlenmeRengi = new Yöney4();
		kullanılacakTemizlenmeRengi = new Yöney4();
		final Gösterici gösterici = new Gösterici(
			1280,
			720,
			"Deneme Sürümü: " + İskelet.SÜRÜM,
			false,
			0,
			0,
			temizlenmeRengi);
		çalıştıranİskelet =
			new İskelet(10.0F, this, gösterici, new Yükleyici());
		çalıştıranİskelet.başla();
	}
	
	@Override
	public void oluştur() {
		çalıştıranİskelet.olayDağıtıcısınıEdin().dinleyicileriniEkle(this);
		final int köşeDizisiNesnesiİşaretçisi =
			çalıştıranİskelet.yükleyicisi.köşeDizisiNesnesiYükle();
		glBindVertexArray(köşeDizisiNesnesiİşaretçisi);
		çalıştıranİskelet.yükleyicisi
			.köşeTamponuNesnesiYükle(
				0,
				2,
				new float[] { -0.5F, -0.5F, +0.5F, -0.5F, -0.5F, +0.5F,
		
//					-0.5F,
//					+0.5F,
//					+0.5F,
//					-0.5F,
					+0.5F,
					+0.5F });
		
		gölgelendiricisi =
			new Gölgelendirici(çalıştıranİskelet.yükleyicisi, "deneme");
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
		
		öncekiTemizlenmeRengi.değiştir(temizlenmeRengi);
		
		temizlenmeRengi.birinciBileşeni =
			(float)pow(1.05, tekerleğinToplamDevri);
		temizlenmeRengi.ikinciBileşeni = sıkıştır(
			girdi.imlecininKonumu.birinciBileşeni / 1280.0F,
			0.0F,
			1.0F);
		temizlenmeRengi.üçüncüBileşeni =
			sıkıştır(girdi.imlecininKonumu.ikinciBileşeni / 720.0F, 0.0F, 1.0F);
	}
	
	@Override
	public void çiz() {
		kullanılacakTemizlenmeRengi
			.aradeğerleriniBul(
				öncekiTemizlenmeRengi,
				temizlenmeRengi,
				(float)çalıştıranİskelet.güncellenmemişTıklarınıEdin());
		glClearColor(
			0.5F,
			kullanılacakTemizlenmeRengi.ikinciBileşeni,
			kullanılacakTemizlenmeRengi.üçüncüBileşeni,
			kullanılacakTemizlenmeRengi.dördüncüBileşeni);
		
		gölgelendiricisi
			.değeriDeğiştir(
				"boyutu",
				kullanılacakTemizlenmeRengi.birinciBileşeni);
		glEnableVertexAttribArray(0);
		glDrawArrays(GL_TRIANGLE_STRIP, 0, 6);
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
