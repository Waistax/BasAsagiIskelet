/**
 * başaşağıderebeyi.iskelet.görsel.köşedizisi.SıralıOluşumluKöşeDizisi.java
 * 0.6 / 6 Mar 2021 / 10:57:37
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.görsel.köşedizisi;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;

import başaşağıderebeyi.iskelet.görsel.*;

import java.nio.*;

/** Birden fazla nesnenin niteliklerini bir arada tutan ve köşe dizisini sırayla
 * çizen nesne. */
public class SıralıOluşumluKöşeDizisi extends OluşumluKöşeDizisi {
	/** Verilen boyutta ve sığada tanımlar. */
	public SıralıOluşumluKöşeDizisi(
		final Yükleyici yükleyici,
		final int çizimKipi,
		final int sığası,
		final int oluşumBoyutu) {
		super(yükleyici, çizimKipi, sığası, oluşumBoyutu);
	}
	
	/** Köşelerin çizim sırasını yükler. Verilen tamponu kendiliğinden yok
	 * eder. */
	public void sıraTamponuNesnesiYükle(final IntBuffer yüklenecekVeri) {
		glBindVertexArray(işaretçisi);
		yükleyici.sıraTamponuNesnesiYükle(yüklenecekVeri);
		glBindVertexArray(0);
		çizilecekKöşeSayısı = yüklenecekVeri.limit();
	}
	
	@Override
	protected void çizimÇağrısıYap() {
		glDrawElementsInstanced(
			çizimKipi,
			çizilecekKöşeSayısı,
			GL_UNSIGNED_INT,
			0,
			yazılacakVerisi.limit() / oluşumBoyutu);
	}
}
