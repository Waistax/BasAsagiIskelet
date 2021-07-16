/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.6 / 6 Mar 2021 / 10:57:37
 */
package başaşağıderebeyi.iskelet.görsel.köşedizisi;

import static başaşağıderebeyi.iskelet.görsel.yükleyici.Yükleyici.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;

import java.nio.*;

/** Birden fazla nesnenin niteliklerini bir arada tutan ve köşe dizisini sırayla
 * çizen nesne. */
public class SıralıOluşumluKöşeDizisi extends OluşumluKöşeDizisi {
	/** Verilen boyutta ve sığada tanımlar. */
	public SıralıOluşumluKöşeDizisi(
		final int çizimKipi,
		final int sığası,
		final int oluşumBoyutu) {
		super(çizimKipi, sığası, oluşumBoyutu);
	}
	
	/** Köşelerin çizim sırasını yükler. Verilen tamponu kendiliğinden çevirir
	 * ve yok eder. */
	public SıralıOluşumluKöşeDizisi sıraTamponuNesnesiYükle(
		final IntBuffer yüklenecekVeri) {
		glBindVertexArray(işaretçisi);
		YÜKLEYİCİ.sıraTamponuNesnesiYükle(yüklenecekVeri.flip());
		glBindVertexArray(0);
		çizilecekKöşeSayısı = yüklenecekVeri.limit();
		return this;
	}
	
	@Override
	protected void çizimÇağrısıYap() {
		glDrawElementsInstanced(
			çizimKipi,
			çizilecekKöşeSayısı,
			GL_UNSIGNED_INT,
			0,
			oluşumSayısı);
	}
}
