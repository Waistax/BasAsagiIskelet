/**
 * başaşağıderebeyi.iskelet.görsel.OluşumluKöşeDizisi.java
 * 0.4 / 4 Mar 2021 / 19:32:53
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.görsel;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;

import java.nio.*;

import org.lwjgl.*;

/** Birden fazla nesnenin niteliklerini bir arada tutan köşe dizisi nesnesi. */
public class OluşumluKöşeDizisi extends KöşeDizisi {
	/** Oluşumların verilerini içeren tampon. */
	public final FloatBuffer yazılacakVerisi;
	/** Çizebileceği en fazla oluşum sayısı. */
	public final int sığası;
	
	private final int oluşumBoyutu;
	private final int oluşumluKöşeTamponuNesnesi;
	
	private int niteliklerininBoyutu;
	
	/** Verilen boyutta ve sığada tanımlar. */
	public OluşumluKöşeDizisi(
		final Yükleyici yükleyici,
		final int çizimKipi,
		final int köşeSayısı,
		final int sığası,
		final int oluşumBoyutu) {
		super(yükleyici, çizimKipi, köşeSayısı);
		yazılacakVerisi = BufferUtils.createFloatBuffer(oluşumBoyutu * sığası);
		this.sığası = sığası;
		this.oluşumBoyutu = oluşumBoyutu;
		oluşumluKöşeTamponuNesnesi =
			yükleyici.boşKöşeTamponuNesnesiOluştur(yazılacakVerisi.capacity());
	}
	
	/** Oluşum başına değişen ve her kare yenilenebilecek verileri bu köşe
	 * dizisi nesnesine ekler. */
	public void oluşumBaşınaDeğişenNitelikEkle(final int boyutu) {
		glBindVertexArray(işaretçisi);
		yükleyici
			.oluşumBaşınaDeğişenNitelikEkle(
				işaretçisi,
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
	
	@Override
	protected void çizimÇağrısıYap() {
		glDrawArraysInstanced(
			çizimKipi,
			0,
			köşeSayısı,
			yazılacakVerisi.limit() / oluşumBoyutu);
	}
}
