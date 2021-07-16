/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.2 / 3 Mar 2021 / 21:59:37
 */
package başaşağıderebeyi.iskelet.girdi;

import static başaşağıderebeyi.iskelet.İskelet.*;

import org.lwjgl.glfw.*;

class TekerlekGirdisiBildiricisi extends GLFWScrollCallback {
	@Override
	public void invoke(
		final long pencereİşaretçisi,
		final double yataydakiDevri,
		final double dikeydekiDevri) {
		İSKELET
			.güncellemeOlaylarınınDağıtıcısınıEdin()
			.dağıt(new TekerlekGirdisiOlayı(dikeydekiDevri));
	}
}
