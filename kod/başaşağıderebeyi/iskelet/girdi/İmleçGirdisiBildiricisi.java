/**
 * başaşağıderebeyi.iskelet.girdi.İmleçGirdisiBildiricisi.java
 * 0.2 / 3 Mar 2021 / 21:40:57
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.girdi;

import org.lwjgl.glfw.*;

class İmleçGirdisiBildiricisi extends GLFWCursorPosCallback {
	private final Girdi içerenGirdi;
	private final İmleçGirdisiOlayı olayı;
	
	İmleçGirdisiBildiricisi(final Girdi içerenGirdi) {
		this.içerenGirdi = içerenGirdi;
		olayı = new İmleçGirdisiOlayı();
	}
	
	@Override
	public void invoke(
		final long pencereİşaretçisi,
		final double konumununYatayBileşeni,
		final double konumununDikeyBileşeni) {
		olayı.konumununYatayBileşeni = konumununYatayBileşeni;
		olayı.konumununDikeyBileşeni = konumununDikeyBileşeni;
		olayı.susturulması = false;
		içerenGirdi.bildireceğiOlayDağıtıcısı.dağıt(olayı);
	}
}
