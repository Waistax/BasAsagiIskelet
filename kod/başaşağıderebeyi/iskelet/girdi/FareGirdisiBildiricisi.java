/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.2 / 3 Mar 2021 / 21:00:09
 */
package başaşağıderebeyi.iskelet.girdi;

import static başaşağıderebeyi.iskelet.İskelet.*;
import static org.lwjgl.glfw.GLFW.*;

import başaşağıderebeyi.iskelet.olaylar.*;

import org.lwjgl.glfw.*;

class FareGirdisiBildiricisi extends GLFWMouseButtonCallback {
	FareGirdisiBildiricisi() {
		for (int i = 0; i <= GLFW_MOUSE_BUTTON_LAST; i++)
			İSKELET.girdisiniEdin().faresininTuşunuEkle(i);
	}
	
	@Override
	public void invoke(
		final long pencereİşaretçisi,
		final int tuşKodu,
		final int hareket,
		final int kipleri) {
		İSKELET
			.güncellemeOlaylarınınDağıtıcısınıEdin()
			.dağıt(
				new FareGirdisiOlayı(
					İSKELET.girdisiniEdin().faresininTuşunuEdin(tuşKodu),
					hareket != GLFW_RELEASE));
	}
}
