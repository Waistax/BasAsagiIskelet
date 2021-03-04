/**
 * başaşağıderebeyi.iskelet.görsel.Yükleyici.java
 * 0.3 / 4 Mar 2021 / 09:07:10
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 
 * Waistax Framework'den alındı.
 * ? / ? / ?
 */
package başaşağıderebeyi.iskelet.görsel;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL33.*;

import java.nio.*;
import java.util.*;

import org.lwjgl.*;
import org.lwjgl.opengl.*;

/** Görselleri ve şekilleri ekran kartının belleğine yükler. */
public class Yükleyici {
	private final List<Integer> köşeDizisiNesneleri;
	private final List<Integer> köşeTamponuNesneleri;
	private final List<Integer> dokuları;
	private final List<Integer> yazılımları;
	
	/** Boş yükleyici tanımlar. */
	public Yükleyici() {
		köşeDizisiNesneleri = new ArrayList<>();
		köşeTamponuNesneleri = new ArrayList<>();
		dokuları = new ArrayList<>();
		yazılımları = new ArrayList<>();
	}
	
	/** Ekran kartının belleğine yüklediklerini yok eder. */
	public void yokEt() {
		glBindVertexArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindTexture(GL_TEXTURE_2D, 0);
		glUseProgram(0);
		
		köşeDizisiNesneleri.forEach(GL30::glDeleteVertexArrays);
		köşeTamponuNesneleri.forEach(GL15::glDeleteBuffers);
		dokuları.forEach(GL11::glDeleteTextures);
		yazılımları.forEach(GL20::glDeleteProgram);
		
		köşeDizisiNesneleri.clear();
		köşeTamponuNesneleri.clear();
		dokuları.clear();
		yazılımları.clear();
	}
	
	/** Verilen float dizisini bir FloatBuffer nesnesine yerleştirip yeni bir
	 * köşe tamponu nesnesine yükler. */
	public void köşeTamponuNesnesiYükle(
		final int sırası,
		final int boyutu,
		final float[] yüklenecekVeri) {
		köşeTamponuNesnesiYükle(
			sırası,
			boyutu,
			BufferUtils
				.createFloatBuffer(yüklenecekVeri.length)
				.put(yüklenecekVeri)
				.flip());
	}
	
	/** Köşe başına verilen boyuttaki kadar float içeren bir float dizisini yeni
	 * bir köşe tamponu nesnesine yükleyip şu an aktif olan köşe dizisi
	 * nesnesinin verilen sırasına yerleştirir. */
	public void köşeTamponuNesnesiYükle(
		final int sırası,
		final int boyutu,
		final FloatBuffer yüklenecekVeri) {
		final int köşeTamponuNesnesi = tamponYükle();
		glBindBuffer(GL_ARRAY_BUFFER, köşeTamponuNesnesi);
		glBufferData(GL_ARRAY_BUFFER, yüklenecekVeri, GL_STATIC_DRAW);
		glVertexAttribPointer(sırası, boyutu, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	/** Verilen int dizisini bir IntBuffer nesnesine yerleştirip yeni bir sıra
	 * tamponu nesnesine yükler. */
	public void sıraTamponuNesnesiYükle(final int[] yüklenecekVeri) {
		sıraTamponuNesnesiYükle(
			BufferUtils
				.createIntBuffer(yüklenecekVeri.length)
				.put(yüklenecekVeri)
				.flip());
	}
	
	/** Köşelerin nasıl sıralanacağını belirten sıra tamponu nesnesini şu an
	 * aktif olan köşe dizisi nesnesine yerleştirir. */
	public void sıraTamponuNesnesiYükle(final IntBuffer yüklenecekVeri) {
		final int sıraTamponuNesnesi = tamponYükle();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, sıraTamponuNesnesi);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, yüklenecekVeri, GL_STATIC_DRAW);
	}
	
	/** Ekran kartına yeni bir tampon yükler ve işaretçisini döndürür. */
	public int tamponYükle() {
		final int köşeTamponuNesnesi = glGenBuffers();
		köşeTamponuNesneleri.add(köşeTamponuNesnesi);
		return köşeTamponuNesnesi;
	}
	
	/** Ekran kartına yeni bir köşe dizisi nesnesi yükler ve işaretçisini
	 * döndürür. Oluşturulan dizinin işaretçisini döndürür. */
	public int köşeDizisiNesnesiYükle() {
		final int köşeDizisiNesnesi = glGenVertexArrays();
		köşeDizisiNesneleri.add(köşeDizisiNesnesi);
		return köşeDizisiNesnesi;
	}
	
	/** Ekran kartında boş bir köşe tamponu nesnesi oluşturur. Bu tampon her
	 * kare yeniden doldurulmak için oluşturulur. Oluşturulan tamponun
	 * işaretçisini döndürür. */
	public int boşKöşeTamponuNesnesiOluştur(final int boyutu) {
		final int köşeTamponuNesnesi = tamponYükle();
		glBindBuffer(GL_ARRAY_BUFFER, köşeTamponuNesnesi);
		glBufferData(GL_ARRAY_BUFFER, boyutu * 4, GL_STREAM_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		return köşeTamponuNesnesi;
	}
	
	/** Oluşum başına değişen bir nitelik ekler. Bu nitelikler boş bir tampona
	 * eklenip her bir kare yazılabilir. Böylece tek bir görselin ve/veya
	 * şekilin birden fazla oluşumu çizilebilinir. Oluşumun boyutu, her oluşum
	 * başına değişen float sayısıdır. Niteliğin kaçıklığı ise oluşum başına
	 * düşen floatlardan hangisi olduğunu belli eden sırasıdır. */
	public void oluşumBaşınaDeğişenNitelikEkle(
		final int köşeDizisiNesnesi,
		final int köşeTamponuNesnesi,
		final int sırası,
		final int boyutu,
		final int oluşumunBoyutu,
		final int kaçıklığı) {
		glBindVertexArray(köşeDizisiNesnesi);
		glBindBuffer(GL_ARRAY_BUFFER, köşeTamponuNesnesi);
		glVertexAttribPointer(
			sırası,
			boyutu,
			GL_FLOAT,
			false,
			oluşumunBoyutu * 4,
			kaçıklığı * 4);
		glVertexAttribDivisor(sırası, 1);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	
	/** Verilen nitelikleri yükler ve bütün oluşumların niteliklerini
	 * verilenlerle değiştirir. */
	public void oluşumlarınNitelikleriniGüncelle(
		final int köşeTamponuNesnesi,
		final FloatBuffer yüklenecekVeri) {
		glBindBuffer(GL_ARRAY_BUFFER, köşeTamponuNesnesi);
		glBufferSubData(GL_ARRAY_BUFFER, 0, yüklenecekVeri);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	/** Verilen nitelikleri yükler ve verilen sıradaki oluşumun niteliklerini
	 * verilenlerle değiştirir. */
	public void birOluşumunNitelikleriniGüncelle(
		final int köşeTamponuNesnesi,
		final FloatBuffer yüklenecekVeri,
		final int sırası) {
		glBindBuffer(GL_ARRAY_BUFFER, köşeTamponuNesnesi);
		glBufferSubData(
			GL_ARRAY_BUFFER,
			sırası * yüklenecekVeri.capacity(),
			yüklenecekVeri);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	/** Ekran kartında bir yazılım oluşturur. Oluşturulan yazılımın işaretçisini
	 * döndürür. */
	public int yazılımYükle() {
		final int yazılım = glCreateProgram();
		yazılımları.add(yazılım);
		return yazılım;
	}
	
	/** Verilen türden gölgelendiriciyi derler ve ekran kartına yükler.
	 * Oluşturulan gölgelendiricinin işaretçisini döndürür. */
	public int gölgelendiriciYükle(final String kaynağı, final int türü) {
		final int gölgelendirici = glCreateShader(türü);
		
		glShaderSource(gölgelendirici, kaynağı);
		glCompileShader(gölgelendirici);
		
		if (glGetShaderi(gölgelendirici, GL_COMPILE_STATUS) == 0)
			throw new RuntimeException(
				"Gölgelendirici derlenemedi! Hata: " +
					glGetShaderInfoLog(gölgelendirici, 1024));
		
		return gölgelendirici;
	}
}
