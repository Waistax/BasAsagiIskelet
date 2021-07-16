/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.6 / 6 Mar 2021 / 10:46:40
 */
package başaşağıderebeyi.iskelet.görsel.köşedizisi;

import static başaşağıderebeyi.iskelet.görsel.yükleyici.Yükleyici.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.*;

/** Köşeleri bir sıra ile çizen nesne. */
public class SıralıKöşeDizisi extends KöşeDizisi {
	/** Verilenler ile tanımlar. */
	public SıralıKöşeDizisi(final int çizimKipi) {
		super(çizimKipi);
	}
	
	/** Köşelerin çizim sırasını yükler. Verilen tamponu kendiliğinden çevirir
	 * ve yok eder. */
	public SıralıKöşeDizisi sıraTamponuNesnesiYükle(
		final IntBuffer yüklenecekVeri) {
		glBindVertexArray(işaretçisi);
		YÜKLEYİCİ.sıraTamponuNesnesiYükle(yüklenecekVeri.flip());
		glBindVertexArray(0);
		çizilecekKöşeSayısı = yüklenecekVeri.limit();
		return this;
	}
	
	@Override
	protected void çizimÇağrısıYap() {
		glDrawElements(çizimKipi, çizilecekKöşeSayısı, GL_UNSIGNED_INT, 0);
	}
}
