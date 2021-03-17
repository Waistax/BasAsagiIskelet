/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.3 / 4 Mar 2021 / 09:07:10
 * 
 * Waistax Framework'den alındı.
 * ? / ? / ?
 */
package başaşağıderebeyi.iskelet.görsel;

import static java.lang.Math.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.awt.image.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import javax.imageio.*;

import org.hsluv.*;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

/** Görselleri ve şekilleri ekran kartının belleğine yükler. */
public class Yükleyici {
	/** Resimlerin saklandığı klasörün yolu. Bu yol programın çalıştığı yere
	 * göredir. */
	public static final String RESİMLERİN_KLASÖRÜ = "resimler";
	/** Resimlerin uzantısı. */
	public static final String RESİMLERİN_UZANTISI = ".png";
	
	/** Gölgelendiricilerin saklandığı klasörün yolu. Bu yol programın çalıştığı
	 * yere göredir. */
	public static final String GÖLGELENDİRİCİLERİN_KLASÖRÜ =
		"gölgelendiriciler";
	/** Köşe gölgelendiricilerinin uzantısı. */
	public static final String KÖŞE_GÖLGELENDİRİCİLERİNİN_UZANTISI = ".kgöl";
	/** Benek gölgelendiricilerinin uzantısı. */
	public static final String BENEK_GÖLGELENDİRİCİLERİNİN_UZANTISI = ".bgöl";
	
	/** Yazı şekillerinin saklandığı klasörün yolu. Bu yol programın çalıştığı
	 * yere göredir. */
	public static final String YAZI_ŞEKİLLERİNİN_KLASÖRÜ = "yazıŞekilleri";
	/** Yazı şekillerinin uzantısı. */
	public static final String YAZI_ŞEKİLLERİNİN_UZANTISI = ".yşek";
	
	/**  */
	public static final IntUnaryOperator BENEĞİ_RGB_UZAYINA_ÇEVİRİCİ =
		benekRengi -> benekRengi & 0xFF000000 |
			(benekRengi & 0x000000FF) << 16 |
			(benekRengi & 0x0000FF00) << 0 |
			(benekRengi & 0x00FF0000) >> 16;
	
	public static final IntUnaryOperator BENEĞİ_HSLUV_UZAYINA_ÇEVİRİCİ =
		benekRengi -> {
			final int solukluğu = (benekRengi & 0xFF000000) >> 24;
			
			final double[] HSLuv = HUSLColorConverter
				.rgbToHsluv(
					new double[] {
						((benekRengi & 0x00FF0000) >> 16) / 255.0,
						((benekRengi & 0x0000FF00) >> 8) / 255.0,
						((benekRengi & 0x000000FF) >> 0) / 255.0 });
			
			return solukluğu << 24 |
				(int)round(HSLuv[2] / 100.0 * 255.0) << 16 |
				(int)round(HSLuv[1] / 100.0 * 255.0) << 8 |
				(int)round(HSLuv[0] / 360.0 * 255.0);
		};
	
	/** Dizeyleri ekran kartına yüklemek için kullanılacak tampon. */
	public final FloatBuffer dizeyTamponu;
	
	private final List<Integer> köşeDizisiNesneleri;
	private final List<Integer> köşeTamponuNesneleri;
	private final List<Integer> dokuları;
	private final List<Integer> yazılımları;
	private final List<Buffer> tamponlar;
	
	/** Boş yükleyici tanımlar. */
	public Yükleyici() {
		köşeDizisiNesneleri = new ArrayList<>();
		köşeTamponuNesneleri = new ArrayList<>();
		dokuları = new ArrayList<>();
		yazılımları = new ArrayList<>();
		tamponlar = new ArrayList<>();
		
		dizeyTamponu = tamponYükle(16);
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
		tamponlar.forEach(MemoryUtil::memFree);
		
		köşeDizisiNesneleri.clear();
		köşeTamponuNesneleri.clear();
		dokuları.clear();
		yazılımları.clear();
		tamponlar.clear();
	}
	
	/** İskelet durana kadar hayatta kalacak bir tampon oluşturur. İskelet
	 * durduğunda tamponu kendiliğinden yok eder. */
	public FloatBuffer tamponYükle(final int uzunluğu) {
		final FloatBuffer tampon = memAllocFloat(uzunluğu);
		tamponlar.add(tampon);
		return tampon;
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
			memAllocFloat(yüklenecekVeri.length).put(yüklenecekVeri).flip());
	}
	
	/** Köşe başına verilen boyuttaki kadar float içeren bir float dizisini yeni
	 * bir köşe tamponu nesnesine yükleyip şu an aktif olan köşe dizisi
	 * nesnesinin verilen sırasına yerleştirir. Verilen tamponu kendiliğinden
	 * yok eder. */
	public void köşeTamponuNesnesiYükle(
		final int sırası,
		final int boyutu,
		final FloatBuffer yüklenecekVeri) {
		final int köşeTamponuNesnesi = tamponYükle();
		glBindBuffer(GL_ARRAY_BUFFER, köşeTamponuNesnesi);
		glBufferData(GL_ARRAY_BUFFER, yüklenecekVeri, GL_STATIC_DRAW);
		glVertexAttribPointer(sırası, boyutu, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		memFree(yüklenecekVeri);
	}
	
	/** Verilen int dizisini bir IntBuffer nesnesine yerleştirip yeni bir sıra
	 * tamponu nesnesine yükler. */
	public void sıraTamponuNesnesiYükle(final int[] yüklenecekVeri) {
		sıraTamponuNesnesiYükle(
			memAllocInt(yüklenecekVeri.length).put(yüklenecekVeri).flip());
	}
	
	/** Köşelerin nasıl sıralanacağını belirten sıra tamponu nesnesini şu an
	 * aktif olan köşe dizisi nesnesine yerleştirir. Verilen tamponu
	 * kendiliğinden yok eder. */
	public void sıraTamponuNesnesiYükle(final IntBuffer yüklenecekVeri) {
		final int sıraTamponuNesnesi = tamponYükle();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, sıraTamponuNesnesi);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, yüklenecekVeri, GL_STATIC_DRAW);
		memFree(yüklenecekVeri);
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
	public int gölgelendiriciYükle(final String adı, final int türü) {
		final int gölgelendirici = glCreateShader(türü);
		
		glShaderSource(
			gölgelendirici,
			yazıyıYükle(
				GÖLGELENDİRİCİLERİN_KLASÖRÜ,
				adı,
				türü == GL_VERTEX_SHADER ?
					KÖŞE_GÖLGELENDİRİCİLERİNİN_UZANTISI :
					BENEK_GÖLGELENDİRİCİLERİNİN_UZANTISI));
		glCompileShader(gölgelendirici);
		
		if (glGetShaderi(gölgelendirici, GL_COMPILE_STATUS) == 0)
			throw new RuntimeException(
				"Gölgelendirici derlenemedi! Hata: " +
					glGetShaderInfoLog(gölgelendirici, 1024));
		
		return gölgelendirici;
	}
	
	/** Verilen addaki yazı şeklinin bilgilerini döndürür. */
	public List<String> yazıŞekliBilgisiniYükle(final String adı) {
		return satırlarınıYükle(
			YAZI_ŞEKİLLERİNİN_KLASÖRÜ,
			adı,
			YAZI_ŞEKİLLERİNİN_UZANTISI);
	}
	
	/** Yolu verilen dosyayı yükler ve dize olarak döndürür. */
	public String yazıyıYükle(
		final String klasörü,
		final String adı,
		final String uzantısı) {
		try {
			return Files.readString(dosyayıBul(klasörü, adı, uzantısı));
		} catch (final IOException hata) {
			throw new RuntimeException(
				"Dosya " + klasörü + "/" + adı + uzantısı + " yüklenemedi!",
				hata);
		}
	}
	
	/** Yolu verilen dosyayı yükler ve satırlarını döndürür. */
	public List<String> satırlarınıYükle(
		final String klasörü,
		final String adı,
		final String uzantısı) {
		try {
			return Files.readAllLines(dosyayıBul(klasörü, adı, uzantısı));
		} catch (final IOException hata) {
			throw new RuntimeException(
				"Dosya " + klasörü + "/" + adı + uzantısı + " yüklenemedi!",
				hata);
		}
	}
	
	/** Klasörü, adı ve uzantısı verilen dosyanın yolunu döndürür. */
	public Path dosyayıBul(
		final String klasörü,
		final String adı,
		final String uzantısı) {
		return Paths.get(klasörü, adı + uzantısı);
	}
	
	/** Resimler klasöründeki verilen addaki resmi GLFW resmi olarak yükler ve
	 * döndürür. */
	public GLFWImage glfwResmiYükle(final String resminAdı) {
		return glfwResmiYükle(resimYükle(resminAdı));
	}
	
	/** Verilen resmi GLFW resmi olarak yükler ve döndürür. */
	public GLFWImage glfwResmiYükle(final BufferedImage resim) {
		final int[] resminVerisi = resim
			.getRGB(
				0,
				0,
				resim.getWidth(),
				resim.getHeight(),
				new int[resim.getWidth() * resim.getHeight()],
				0,
				resim.getWidth());
		
		final byte[] renkBaytları = new byte[resminVerisi.length * 4];
		
		IntStream.range(0, resminVerisi.length).parallel().forEach(benek -> {
			final int benekRengi = resminVerisi[benek];
			renkBaytları[benek * 4 + 0] = (byte)(benekRengi >> 16);
			renkBaytları[benek * 4 + 1] = (byte)(benekRengi >> 8);
			renkBaytları[benek * 4 + 2] = (byte)benekRengi;
			renkBaytları[benek * 4 + 3] = (byte)(benekRengi >> 24);
		});
		
		return GLFWImage
			.malloc()
			.set(
				resim.getWidth(),
				resim.getHeight(),
				BufferUtils
					.createByteBuffer(renkBaytları.length)
					.put(renkBaytları)
					.flip());
	}
	
	/** Resimler klasöründeki verilen addaki resmi ekran kartına RGB renk
	 * uzayında yükler ve işaretçisini döndürür. */
	public int rgbDokuYükle(final String resminAdı) {
		return dokuYükle(resminAdı, BENEĞİ_RGB_UZAYINA_ÇEVİRİCİ);
	}
	
	/** Resimler klasöründeki verilen addaki resmi ekran kartına HSLuv renk
	 * uzayında yükler ve işaretçisini döndürür. */
	public int hsluvDokuYükle(final String resminAdı) {
		return dokuYükle(resminAdı, BENEĞİ_HSLUV_UZAYINA_ÇEVİRİCİ);
	}
	
	/** Resimler klasöründeki verilen addaki resmi ekran kartına yükler ve
	 * işaretçisini döndürür. */
	public int dokuYükle(
		final String resiminAdı,
		final IntUnaryOperator çevirici) {
		return dokuYükle(resimYükle(resiminAdı), çevirici);
	}
	
	/** Verilen resmi ekran kartına yükler ve işaretçisini döndürür. */
	public int dokuYükle(
		final BufferedImage resim,
		final IntUnaryOperator çevirici) {
		final int doku = glGenTextures();
		dokuları.add(doku);
		glBindTexture(GL_TEXTURE_2D, doku);
		resminVerisiniYükle(resim, çevirici);
		glTexParameteri(
			GL_TEXTURE_2D,
			GL_TEXTURE_MIN_FILTER,
			GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glGenerateMipmap(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, 0);
		return doku;
	}
	
	/** Verilen resmin verisini gerekli şekle sokup ekran kartına yükler. */
	private void resminVerisiniYükle(
		final BufferedImage resim,
		final IntUnaryOperator çevirici) {
		final int[] resminVerisi = resim
			.getRGB(
				0,
				0,
				resim.getWidth(),
				resim.getHeight(),
				new int[resim.getWidth() * resim.getHeight()],
				0,
				resim.getWidth());
		
		IntStream
			.range(0, resminVerisi.length)
			.parallel()
			.forEach(
				benek -> resminVerisi[benek] =
					çevirici.applyAsInt(resminVerisi[benek]));
		
		final IntBuffer tampon =
			memAllocInt(resminVerisi.length).put(resminVerisi).flip();
		
		glTexImage2D(
			GL_TEXTURE_2D,
			0,
			GL_RGBA,
			resim.getWidth(),
			resim.getHeight(),
			0,
			GL_RGBA,
			GL_UNSIGNED_BYTE,
			tampon);
		
		memFree(tampon);
	}
	
	/** Resimler klasöründeki verilen addaki resmi yükler ve döndürür. */
	public BufferedImage resimYükle(final String resminAdı) {
		try {
			return ImageIO
				.read(
					Paths
						.get(
							RESİMLERİN_KLASÖRÜ,
							resminAdı + RESİMLERİN_UZANTISI)
						.toFile());
		} catch (final IOException hata) {
			throw new RuntimeException(
				"Resim " +
					RESİMLERİN_KLASÖRÜ +
					"/" +
					resminAdı +
					RESİMLERİN_UZANTISI +
					" yüklenemedi!",
				hata);
		}
	}
}
