/**
 * başaşağıderebeyi.iskelet.girdi.FareGirdisiBildiricisi.java
 * 0.2 / 3 Mar 2021 / 21:00:09
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.girdi;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.*;

class FareGirdisiBildiricisi extends GLFWMouseButtonCallback {
	private final Girdi içerenGirdi;
	private final FareGirdisiOlayı[] olayları =
		new FareGirdisiOlayı[GLFW_MOUSE_BUTTON_LAST + 1];
	
	FareGirdisiBildiricisi(final Girdi içerenGirdi) {
		this.içerenGirdi = içerenGirdi;
		
		for (int i = 0; i < olayları.length; i++) {
			içerenGirdi.bildireceğiÇiğGirdi.faresininTuşunuEkle(i);
			olayları[i] = new FareGirdisiOlayı(
				içerenGirdi.bildireceğiÇiğGirdi.faresininTuşunuEdin(i));
		}
	}
	
	@Override
	public void invoke(
		final long pencereİşaretçisi,
		final int tuşKodu,
		final int hareket,
		final int kipleri) {
		final FareGirdisiOlayı olayı = olayları[tuşKodu];
		olayı.basılıOlması = hareket != GLFW_RELEASE;
		olayı.susturulması = false;
		içerenGirdi.bildireceğiOlayDağıtıcısı.dağıt(olayı);
	}
}
