/**
 * başaşağıderebeyi.iskelet.girdi.Girdi.java
 * 0.2 / 3 Mar 2021 / 20:49:04
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 
 * Waistax Engine'den biraz alındı.
 * 4.0.0 / 7 Ara 2018 / ?
 */
package başaşağıderebeyi.iskelet.girdi;

import static başaşağıderebeyi.kütüphane.matematik.MatematikAracı.*;
import static org.lwjgl.glfw.GLFW.*;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.kütüphane.olay.*;

/** Pencereye yapılan girdileri bildirir. */
public class Girdi {
	final İskelet bildireceğiİskelet;
	
	private final KlavyeGirdisiBildiricisi klavyeGirdisiBildiricisi;
	private final FareGirdisiBildiricisi fareGirdisiBildiricisi;
	private final İmleçGirdisiBildiricisi imleçGirdisiBildiricisi;
	private final TekerlekGirdisiBildiricisi tekerlekGirdisiBildiricisi;
	
	/** Verilenler ile tanımlar. */
	public Girdi(
		final İskelet bildireceğiİskelet,
		final long dinlediğiPencere) {
		this.bildireceğiİskelet = bildireceğiİskelet;
		
		bildireceğiİskelet.olayDağıtıcısınıEdin().dinleyicileriniEkle(this);
		klavyeGirdisiBildiricisi = new KlavyeGirdisiBildiricisi(this);
		fareGirdisiBildiricisi = new FareGirdisiBildiricisi(this);
		imleçGirdisiBildiricisi = new İmleçGirdisiBildiricisi(this);
		tekerlekGirdisiBildiricisi = new TekerlekGirdisiBildiricisi(this);
		
		bildiricileriPencereyeEkle(dinlediğiPencere);
	}
	
	/** Bütün bildiricileri yok eder. */
	public void bildiriceleriniSal() {
		klavyeGirdisiBildiricisi.free();
		fareGirdisiBildiricisi.free();
		imleçGirdisiBildiricisi.free();
		tekerlekGirdisiBildiricisi.free();
	}
	
	@Dinleyici
	public void klavyeGirdisiOlayınıDinle(final KlavyeGirdisiOlayı olay) {
		olay.tuşu.basılıOlmasınıBildir(olay.basılıOlması);
	}
	
	@Dinleyici
	public void fareGirdisiOlayınıDinle(final FareGirdisiOlayı olay) {
		olay.tuşu.basılıOlmasınıBildir(olay.basılıOlması);
	}
	
	@Dinleyici
	public void imleçGirdisiOlayınıDinle(final İmleçGirdisiOlayı olay) {
		bildireceğiİskelet
			.girdisiniEdin()
			.imlecininKonumunuBildir(
				(float)olay.konumununYatayBileşeni,
				(float)olay.konumununDikeyBileşeni);
	}
	
	@Dinleyici
	public void tekerlekGirdisiOlayınıDinle(final TekerlekGirdisiOlayı olay) {
		bildireceğiİskelet
			.girdisiniEdin()
			.tekerleğininDevriniBildir(yuvarla((float)olay.devri));
	}
	
	private void bildiricileriPencereyeEkle(final long dinlediğiPencere) {
		glfwSetKeyCallback(dinlediğiPencere, klavyeGirdisiBildiricisi);
		glfwSetMouseButtonCallback(dinlediğiPencere, fareGirdisiBildiricisi);
		glfwSetCursorPosCallback(dinlediğiPencere, imleçGirdisiBildiricisi);
		glfwSetScrollCallback(dinlediğiPencere, tekerlekGirdisiBildiricisi);
	}
}
