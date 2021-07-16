/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.2 / 3 Mar 2021 / 21:40:57
 */
package başaşağıderebeyi.iskelet.girdi;

import static başaşağıderebeyi.iskelet.İskelet.*;

import başaşağıderebeyi.iskelet.olaylar.*;

import org.lwjgl.glfw.*;

class İmleçGirdisiBildiricisi extends GLFWCursorPosCallback {
	@Override
	public void invoke(
		final long pencereİşaretçisi,
		final double konumununYatayBileşeni,
		final double konumununDikeyBileşeni) {
		İSKELET
			.güncellemeOlaylarınınDağıtıcısınıEdin()
			.dağıt(
				new İmleçGirdisiOlayı(
					konumununYatayBileşeni,
					konumununDikeyBileşeni));
	}
}
