/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.2 / 3 Mar 2021 / 21:00:09
 */
package başaşağıderebeyi.iskelet.girdi;

import static org.lwjgl.glfw.GLFW.*;

import başaşağıderebeyi.iskelet.*;

import org.lwjgl.glfw.*;

class FareGirdisiBildiricisi extends GLFWMouseButtonCallback {
	private final FareGirdisiOlayı[] olayları =
		new FareGirdisiOlayı[GLFW_MOUSE_BUTTON_LAST + 1];
	
	FareGirdisiBildiricisi(final İskelet bildireceğiİskelet) {
		for (int i = 0; i < olayları.length; i++) {
			bildireceğiİskelet.girdisiniEdin().faresininTuşunuEkle(i);
			olayları[i] = new FareGirdisiOlayı(
				bildireceğiİskelet,
				bildireceğiİskelet.girdisiniEdin().faresininTuşunuEdin(i));
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
		olayı.dağıtmayıDene();
	}
}
