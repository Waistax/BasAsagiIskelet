/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 3.0.10 / 16 Tem 2021 / 15:44:08
 */
package başaşağıderebeyi.iskelet.girdi;

import static başaşağıderebeyi.iskelet.İskelet.*;

import org.lwjgl.glfw.*;

class SesGirdisiBildiricisi extends GLFWCharCallback {
	@Override
	public void invoke(final long pencereİşaretçisi, final int sesKodu) {
		İSKELET
			.güncellemeOlaylarınınDağıtıcısınıEdin()
			.dağıt(new SesGirdisiOlayı((char)sesKodu));
	}
}
