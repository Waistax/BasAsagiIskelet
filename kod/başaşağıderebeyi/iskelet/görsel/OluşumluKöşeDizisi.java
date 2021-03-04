/**
 * başaşağıderebeyi.iskelet.görsel.OluşumluKöşeDizisi.java
 * 0.4 / 4 Mar 2021 / 19:32:53
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.görsel;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;

import java.nio.*;

import org.lwjgl.*;

/** Birden fazla iki boyutlu nesnenin niteliklerini bir arada tutan köşe dizisi
 * nesnesi. Bu nesnenin köşeleri sol alt, sağ alt, sol üst ve sağ üst sırasında
 * tamponlara yüklenmelidir. */
public class OluşumluKöşeDizisi {
	/** Oluşumların verilerini içeren tampon. */
	public final FloatBuffer yazılacakVerisi;
	
	private final Yükleyici yükleyici;
	private final int oluşumBoyutu;
	private final int köşeDizisiNesnesi;
	private final int oluşumluKöşeTamponuNesnesi;
	
	private int niteliklerininSayısı;
	private int niteliklerininBoyutu;
	
	/** Verilen boyutta ve sığada tanımlar. */
	public OluşumluKöşeDizisi(
		final Yükleyici yükleyici,
		final int oluşumBoyutu,
		final int sığası) {
		yazılacakVerisi = BufferUtils.createFloatBuffer(oluşumBoyutu * sığası);
		this.yükleyici = yükleyici;
		this.oluşumBoyutu = oluşumBoyutu;
		köşeDizisiNesnesi = yükleyici.köşeDizisiNesnesiYükle();
		oluşumluKöşeTamponuNesnesi =
			yükleyici.boşKöşeTamponuNesnesiOluştur(yazılacakVerisi.capacity());
	}
	
	/** Köşe başına değişen ve bir kere yazıldıktan sonra değişmeyen verileri bu
	 * köşe dizisi nesnesine ekler. */
	public void durağanKöşeTamponuNesnesiEkle(
		final int boyutu,
		final FloatBuffer verisi) {
		glBindVertexArray(köşeDizisiNesnesi);
		yükleyici
			.köşeTamponuNesnesiYükle(niteliklerininSayısı++, boyutu, verisi);
		glBindVertexArray(0);
	}
	
	/** Oluşum başına değişen ve her kare yenilenebilecek verileri bu köşe
	 * dizisi nesnesine ekler. */
	public void oluşumBaşınaDeğişenNitelikEkle(final int boyutu) {
		glBindVertexArray(köşeDizisiNesnesi);
		yükleyici
			.oluşumBaşınaDeğişenNitelikEkle(
				köşeDizisiNesnesi,
				oluşumluKöşeTamponuNesnesi,
				niteliklerininSayısı++,
				boyutu,
				oluşumBoyutu,
				niteliklerininBoyutu);
		niteliklerininBoyutu += boyutu;
		glBindVertexArray(0);
	}
	
	/** Tampona yazılmış verileri oluşum başına değişen köşe tamponu nesnesine
	 * yükler. */
	public void tamponunuGüncelle() {
		yükleyici
			.oluşumlarınNitelikleriniGüncelle(
				oluşumluKöşeTamponuNesnesi,
				yazılacakVerisi.flip());
	}
	
	/** Verilen sayıda oluşumu çizer. */
	public void çiz(final int çizilecekOluşumSayısı) {
		glBindVertexArray(köşeDizisiNesnesi);
		for (int i = 0; i < niteliklerininSayısı; i++)
			glEnableVertexAttribArray(i);
		glDrawArraysInstanced(GL_TRIANGLE_STRIP, 0, 4, çizilecekOluşumSayısı);
		for (int i = 0; i < niteliklerininSayısı; i++)
			glDisableVertexAttribArray(i);
		glBindVertexArray(0);
	}
}
