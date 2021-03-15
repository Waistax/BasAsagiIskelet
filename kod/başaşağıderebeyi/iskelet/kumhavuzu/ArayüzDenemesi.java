/**
 * başaşağıderebeyi.iskelet.kumhavuzu.ArayüzDenemesi.java
 * 0.8 / 14 Mar 2021 / 20:30:18
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.kumhavuzu;

import static org.lwjgl.glfw.GLFW.*;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.iskelet.girdi.*;
import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.görsel.yazı.*;
import başaşağıderebeyi.kütüphane.arayüz.*;
import başaşağıderebeyi.kütüphane.girdi.*;
import başaşağıderebeyi.kütüphane.matematik.*;
import başaşağıderebeyi.kütüphane.olay.*;

import java.util.*;

/** Arayüz bölümünü dener. */
public class ArayüzDenemesi implements Uygulama {
	public static void main(final String[] args) {
		new ArayüzDenemesi();
	}
	
	private final İskelet çalıştıranİskelet;
	
	private DeğişkenYazıGörselleştirici yazar;
	private Görselleştirici görselleştirici;
	private Map<Öğe, Dönüşüm> dönüşümleri;
	private Map<Dönüşüm, Dönüşüm> eskiDönüşümleri;
	private Map<Dönüşüm, Dönüşüm> çizilecekDönüşümleri;
	private Ekran ekranı;
	
	private ArayüzDenemesi() {
		final Gösterici gösterici = new Gösterici(
			1280,
			720,
			"Arayüz Denemesi Sürüm: " + İskelet.SÜRÜM,
			true,
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
			new Dizey4().izdüşümDizeyineÇevir(1280.0F, 720.0F, 1000.0F);
		
		yazar = new DeğişkenYazıGörselleştirici(
			çalıştıranİskelet.yükleyicisi,
			1000,
			new YazıŞekli(
				çalıştıranİskelet.yükleyicisi,
				"sabitGenişlikliBüyük"),
			30.0F,
			izdüşümDizeyi,
			0.9F);
		
		yazar.renginiEdin().değiştir(Yöney4.BİR);
		yazar.boyutunuDeğiştir(20.0F);
		
		görselleştirici = new Görselleştirici(
			çalıştıranİskelet.yükleyicisi,
			izdüşümDizeyi,
			100,
			"arkaplan");
		
		dönüşümleri = new HashMap<>();
		eskiDönüşümleri = new HashMap<>();
		çizilecekDönüşümleri = new HashMap<>();
		
		ekranı = new Ekran(
			çalıştıranİskelet.girdisiniEdin(),
			GLFW_MOUSE_BUTTON_LEFT,
			0.0F,
			0.0F,
			1280.0F,
			720.0F);
		
		new Pencere(ekranı, "Birinci Pencere", 500.0F, 300.0F);
		
		öğeninDönüşümünüOluştur(ekranı);
		dönüşümleri.values().forEach(dönüşümü -> {
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
		
		dönüşümleri
			.values()
			.parallelStream()
			.forEach(
				dönüşümü -> eskiDönüşümleri.get(dönüşümü).değiştir(dönüşümü));
		ekranı.güncelle();
		öğeninDönüşümünüBul(ekranı, 0.0F);
	}
	
	@Override
	public void çiz() {
		dönüşümleri
			.values()
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
		
		yazar
			.yaz(
				4.0F - 1280.0F / 2.0F,
				720.0F / 2.0F - 20.0F,
				"İmleç: " + çalıştıranİskelet.girdisiniEdin().imlecininKonumu,
				"Sürüklenmesi: " +
					çalıştıranİskelet.girdisiniEdin().imlecininSürüklenmesi,
				"İlgilendiği: " +
					çalıştıranİskelet
						.girdisiniEdin()
						.faresininTuşunuEdin(
							GLFW_MOUSE_BUTTON_LEFT).ilgilendiğiNesne);
		yazar.çiz();
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
	
	@Dinleyici(önceliği = Öncelik.TEMEL)
	public void imleçGirdisiOlayınıDinle(final İmleçGirdisiOlayı olay) {
		olay.konumununYatayBileşeni =
			olay.konumununYatayBileşeni - 1280.0 / 2.0;
		olay.konumununDikeyBileşeni = 720.0 / 2.0 - olay.konumununDikeyBileşeni;
	}
	
	private void öğeninDönüşümünüOluştur(final Öğe öğe) {
		if (!(öğe instanceof Ekran))
			dönüşümleri.put(öğe, new Dönüşüm());
		if (öğe instanceof Levha)
			((Levha)öğe).içeriği.forEach(this::öğeninDönüşümünüOluştur);
	}
	
	private void öğeninDönüşümünüBul(final Öğe öğe, float derinliği) {
		if (!(öğe instanceof Ekran)) {
			final Dönüşüm dönüşümü = dönüşümleri.get(öğe);
			dönüşümü.konumu
				.bileşenleriniDeğiştir(
					öğe.alanı.ortaNoktası.birinciBileşeni,
					öğe.alanı.ortaNoktası.ikinciBileşeni,
					derinliği);
			dönüşümü.biçimi
				.bileşenleriniDeğiştir(
					öğe.alanı.uzunlukları.birinciBileşeni,
					öğe.alanı.uzunlukları.ikinciBileşeni,
					0.0F);
		}
		if (öğe instanceof Levha) {
			derinliği++;
			for (final Öğe içeriği : ((Levha)öğe).içeriği)
				öğeninDönüşümünüBul(içeriği, derinliği);
		}
	}
}
