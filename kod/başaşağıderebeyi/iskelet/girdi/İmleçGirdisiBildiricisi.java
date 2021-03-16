/**
 * başaşağıderebeyi.iskelet.girdi.İmleçGirdisiBildiricisi.java
 * 0.2 / 3 Mar 2021 / 21:40:57
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.girdi;

import başaşağıderebeyi.iskelet.*;

import org.lwjgl.glfw.*;

class İmleçGirdisiBildiricisi extends GLFWCursorPosCallback {
	private final İmleçGirdisiOlayı olayı;
	
	İmleçGirdisiBildiricisi(final İskelet bildireceğiİskelet) {
		olayı = new İmleçGirdisiOlayı(bildireceğiİskelet);
	}
	
	@Override
	public void invoke(
		final long pencereİşaretçisi,
		final double konumununYatayBileşeni,
		final double konumununDikeyBileşeni) {
		olayı.konumununYatayBileşeni = konumununYatayBileşeni;
		olayı.konumununDikeyBileşeni = konumununDikeyBileşeni;
		olayı.dağıtmayıDene();
	}
}
