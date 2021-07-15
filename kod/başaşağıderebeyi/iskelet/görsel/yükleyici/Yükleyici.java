/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.3 / 4 Mar 2021 / 09:07:10
 * 
 * Waistax Framework'den alındı.
 * ? / ? / ?
 */
package başaşağıderebeyi.iskelet.görsel.yükleyici;

import java.nio.*;
import java.nio.file.*;
import java.util.*;

import org.lwjgl.glfw.*;

/** Görselleri ve şekilleri ekran kartının belleğine yükler. */
public class Yükleyici {
	/** Yükleyicinin kullanılacak nesnesi. Ekran kartına bir şey yüklemek için
	 * yeni bir yükleyici oluşturmak gereksiz. */
	public static final Yükleyici NESNESİ = new Yükleyici();
	
	/** Dizeyleri ekran kartına yüklemek için kullanılacak tampon. */
	public final FloatBuffer dizeyTamponu;
	
	private final KöşeDizisiYükleyicisi köşeDizisiYükleyicisi;
	private final KöşeTamponuYükleyicisi köşeTamponuYükleyicisi;
	private final DokuYükleyicisi dokuYükleyicisi;
	private final YazılımYükleyicisi yazılımYükleyicisi;
	private final TamponYükleyicisi tamponYükleyicisi;
	
	/** Boş yükleyici tanımlar. */
	private Yükleyici() {
		köşeDizisiYükleyicisi = new KöşeDizisiYükleyicisi();
		köşeTamponuYükleyicisi = new KöşeTamponuYükleyicisi();
		dokuYükleyicisi = new DokuYükleyicisi();
		yazılımYükleyicisi = new YazılımYükleyicisi();
		tamponYükleyicisi = new TamponYükleyicisi();
		
		dizeyTamponu = tamponYükle(16);
	}
	
	/** Ekran kartının belleğine yüklediklerini yok eder. */
	public void yokEt() {
		köşeDizisiYükleyicisi.yokEt();
		köşeTamponuYükleyicisi.yokEt();
		dokuYükleyicisi.yokEt();
		yazılımYükleyicisi.yokEt();
		tamponYükleyicisi.yokEt();
	}
	
	/** Verilen float dizisini bir FloatBuffer nesnesine yerleştirip yeni bir
	 * köşe tamponu nesnesine yükler. */
	public void köşeTamponuNesnesiYükle(
		final int sırası,
		final int boyutu,
		final float[] veri) {
		köşeTamponuYükleyicisi.yükle(sırası, boyutu, veri);
	}
	
	/** Köşe başına verilen boyuttaki kadar float içeren bir float dizisini yeni
	 * bir köşe tamponu nesnesine yükleyip şu an aktif olan köşe dizisi
	 * nesnesinin verilen sırasına yerleştirir. Verilen tamponu kendiliğinden
	 * yok eder. */
	public void köşeTamponuNesnesiYükle(
		final int sırası,
		final int boyutu,
		final FloatBuffer veri) {
		köşeTamponuYükleyicisi.yükle(sırası, boyutu, veri);
	}
	
	/** Verilen int dizisini bir IntBuffer nesnesine yerleştirip yeni bir sıra
	 * tamponu nesnesine yükler. */
	public void sıraTamponuNesnesiYükle(final int[] sıra) {
		köşeTamponuYükleyicisi.sıraYükle(sıra);
	}
	
	/** Köşelerin nasıl sıralanacağını belirten sıra tamponu nesnesini şu an
	 * aktif olan köşe dizisi nesnesine yerleştirir. Verilen tamponu
	 * kendiliğinden yok eder. */
	public void sıraTamponuNesnesiYükle(final IntBuffer sıra) {
		köşeTamponuYükleyicisi.sıraYükle(sıra);
	}
	
	/** Ekran kartına yeni bir tampon yükler ve işaretçisini döndürür. */
	public int tamponYükle() {
		return köşeTamponuYükleyicisi.yükle();
	}
	
	/** Ekran kartına yeni bir köşe dizisi nesnesi yükler ve işaretçisini
	 * döndürür. Oluşturulan dizinin işaretçisini döndürür. */
	public int köşeDizisiNesnesiYükle() {
		return köşeDizisiYükleyicisi.yükle();
	}
	
	/** Ekran kartında boş bir köşe tamponu nesnesi oluşturur. Bu tampon her
	 * kare yeniden doldurulmak için oluşturulur. Oluşturulan tamponun
	 * işaretçisini döndürür. */
	public int boşKöşeTamponuNesnesiOluştur(final int boyutu) {
		return köşeTamponuYükleyicisi.boşYükle(boyutu);
	}
	
	/** Oluşum başına değişen bir nitelik ekler. Bu nitelikler boş bir tampona
	 * eklenip her bir kare yazılabilir. Böylece tek bir görselin ve/veya
	 * şekilin birden fazla oluşumu çizilebilinir. Oluşumun boyutu, her oluşum
	 * başına değişen float sayısıdır. Niteliğin kaçıklığı ise oluşum başına
	 * düşen floatlardan hangisi olduğunu belli eden sırasıdır. */
	public void oluşumBaşınaDeğişenNitelikEkle(
		final int köşeDizisi,
		final int köşeTamponu,
		final int sırası,
		final int boyutu,
		final int oluşumunBoyutu,
		final int kaçıklığı) {
		köşeTamponuYükleyicisi
			.oluşumBaşınaDeğişenNitelikEkle(
				köşeDizisi,
				köşeTamponu,
				sırası,
				boyutu,
				oluşumunBoyutu,
				kaçıklığı);
	}
	
	/** Verilen nitelikleri yükler ve bütün oluşumların niteliklerini
	 * verilenlerle değiştirir. */
	public void oluşumlarınNitelikleriniGüncelle(
		final int köşeTamponu,
		final FloatBuffer veri) {
		köşeTamponuYükleyicisi
			.oluşumlarınNitelikleriniGüncelle(köşeTamponu, veri);
	}
	
	/** Verilen nitelikleri yükler ve verilen sıradaki oluşumun niteliklerini
	 * verilenlerle değiştirir. */
	public void birOluşumunNitelikleriniGüncelle(
		final int köşeTamponuNesnesi,
		final FloatBuffer yüklenecekVeri,
		final int sırası) {
		köşeTamponuYükleyicisi
			.birOluşumunNitelikleriniGüncelle(
				köşeTamponuNesnesi,
				yüklenecekVeri,
				sırası);
	}
	
	/** Verilen dosya yolundaki resmi GLFW resmi olarak yükler ve döndürür. */
	public GLFWImage GLFWResmiYükle(final Path dosyaYolu) {
		return dokuYükleyicisi.GLFWResmiYükle(dosyaYolu);
	}
	
	/** Verilen resmi GLFW resmi olarak yükler ve döndürür. */
	public GLFWImage GLFWResmiYükle(final ResimBilgisi resim) {
		return dokuYükleyicisi.GLFWResmiYükle(resim);
	}
	
	/** Verilen dosya yolundaki resmi ekran kartına yükler ve işaretçisini
	 * döndürür. En iyi küçültme ve büyütme yöntemlerini kullanır. */
	public int dokuYükle(final Path dosyaYolu) {
		return dokuYükleyicisi.yükle(dosyaYolu);
	}
	
	/** Verilen dosya yolundaki resmi ekran kartına yükler ve işaretçisini
	 * döndürür. Verilen küçültme ve büyütme yöntemlerini kullanır. */
	public int dokuYükle(
		final Path dosyaYolu,
		final int küçültmeYöntemi,
		final int büyütmeYöntemi) {
		return dokuYükleyicisi
			.yükle(dosyaYolu, küçültmeYöntemi, büyütmeYöntemi);
	}
	
	/** Verilen resmi ekran kartına yükler ve işaretçisini döndürür. En iyi
	 * küçültme ve büyütme yöntemlerini kullanır. */
	public int dokuYükle(final ResimBilgisi resim) {
		return dokuYükleyicisi.yükle(resim);
	}
	
	/** Verilen resmi ekran kartına yükler ve işaretçisini döndürür. Verilen
	 * küçültme ve büyütme yöntemlerini kullanır. */
	public int dokuYükle(
		final ResimBilgisi resim,
		final int küçültmeYöntemi,
		final int büyütmeYöntemi) {
		return dokuYükleyicisi.yükle(resim, küçültmeYöntemi, büyütmeYöntemi);
	}
	
	/** Ekran kartında bir yazılım oluşturur. Oluşturulan yazılımın işaretçisini
	 * döndürür. */
	public int yazılımYükle() {
		return yazılımYükleyicisi.yükle();
	}
	
	/** Verilen türden gölgelendiriciyi derler ve ekran kartına yükler.
	 * Oluşturulan gölgelendiricinin işaretçisini döndürür. */
	public int gölgelendiriciYükle(final Path dosyaYolu, final int türü) {
		return yazılımYükleyicisi.gölgelendiriciYükle(dosyaYolu, türü);
	}
	
	/** Verilen dosya yolundaki metin belgesini yükler ve bütün satırları tek
	 * bir dize olarak döndürür. */
	public String yazıYükle(final Path dosyaYolu) {
		return yazılımYükleyicisi.yazıYükle(dosyaYolu);
	}
	
	/** Verilen dosya yolundaki metin belgesini yükler ve her satırını ayrı bir
	 * dize olarak döndürür. */
	public List<String> satırlarYükle(final Path dosyaYolu) {
		return yazılımYükleyicisi.satırlarYükle(dosyaYolu);
	}
	
	/** İskelet durana kadar hayatta kalacak bir tampon oluşturur. İskelet
	 * durduğunda tamponu kendiliğinden yok eder. */
	public FloatBuffer tamponYükle(final int uzunluğu) {
		return tamponYükleyicisi.yükle(uzunluğu);
	}
}
