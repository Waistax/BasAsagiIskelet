/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.2 / 3 Mar 2021 / 16:51:33
 * 
 * Waistax Framework'den alındı.
 * ? / ? / ?
 */
package başaşağıderebeyi.iskelet.görsel;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import başaşağıderebeyi.iskelet.girdi.*;
import başaşağıderebeyi.kütüphane.günlük.*;
import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

/** GLFW ile bir pencere oluşturan ve OpenGL içeriğini ayarlayan araç. */
public class Gösterici {
	private static Gösterici nesnesi;
	
	/** Verilen gösterici nesnesini kullanılacak olanla değiştirir. Bu yöntem
	 * yalnızca bir kez çağırılmalıdır. */
	public static void sağla(final Gösterici gösterici) {
		assert nesnesi == null && gösterici != null;
		nesnesi = gösterici;
	}
	
	/** Kullanılacak göstericiyi döndürür. */
	public static Gösterici edin() {
		return nesnesi;
	}
	
	private final int genişliği;
	private final int yüksekliği;
	private final String başlığı;
	
	private final boolean tamEkranOlması;
	
	private final int örneklemelerininSayısı;
	private final int değiştirilmeAralığı;
	private final Yöney temizlenmeRengi;
	
	private long penceresi;
	private GLFWGirdisi girdisi;
	
	/** Verilenler ile tanımlar. */
	public Gösterici(
		final int genişliği,
		final int yüksekliği,
		final String başlığı,
		final boolean tamEkranOlması,
		final int örneklemelerininSayısı,
		final int değiştirilmeAralığı,
		final Yöney temizlenmeRengi) {
		this.genişliği = genişliği;
		this.yüksekliği = yüksekliği;
		this.başlığı = başlığı;
		this.tamEkranOlması = tamEkranOlması;
		this.örneklemelerininSayısı = örneklemelerininSayısı;
		this.değiştirilmeAralığı = değiştirilmeAralığı;
		this.temizlenmeRengi = temizlenmeRengi;
	}
	
	/** Verilen iskelet için pencere oluşturur. */
	public void penceresiniOluştur() {
		GLFWErrorCallback.createPrint().set();
		
		if (!glfwInit())
			throw new IllegalStateException("GLFW başlatılamadı!");
		
		penceresiniAyarla();
		final long ekranı = glfwGetPrimaryMonitor();
		penceresi = glfwCreateWindow(
			genişliği,
			yüksekliği,
			başlığı,
			tamEkranOlması ? ekranı : NULL,
			NULL);
		
		if (penceresi == NULL)
			throw new RuntimeException("Pencere oluşturulamadı!");
		
		girdisi = new GLFWGirdisi(penceresi);
		
		penceresiniOturt(ekranı);
		içeriğiniAyarla();
		
		SistemGünlüğü.KONSOL.yaz(başlığı + " oluşturuldu!");
	}
	
	/** Pencereyi kapatır ve girdileri salar. */
	public void yokEt() {
		girdisi.bildiriceleriniSal();
		glfwDestroyWindow(penceresi);
		glfwTerminate();
	}
	
	/** Çizilenleri pencereye gösterir. */
	public void göster() {
		glfwSwapBuffers(penceresi);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		final int hataKodu = glGetError();
		if (hataKodu != GL_NO_ERROR)
			throw new RuntimeException(
				"OpenGL hata verdi! Hata Kodu: " + hataKodu);
	}
	
	/** Pencerenin kapatılıp kapatılmadığını döndürür. Ayrıca pencerenin
	 * girdilerini bildirir. */
	public boolean penceresininKapatılmasınıEdin() {
		glfwPollEvents();
		return glfwWindowShouldClose(penceresi);
	}
	
	/** Verilenlerden imleç oluşturur ve imlecin işaretçisini döndürür. */
	public long imleçOluştur(
		final GLFWImage resmi,
		final int yataydaDokunduğuKonumu,
		final int dikeydeDokunduğuKonumu) {
		return glfwCreateCursor(
			resmi,
			yataydaDokunduğuKonumu,
			dikeydeDokunduğuKonumu);
	}
	
	/** Penceredeki imleci verilen ile değiştirir. */
	public void imleciDeğiştir(final long imleç) {
		glfwSetCursor(penceresi, imleç);
	}
	
	private void penceresiniAyarla() {
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		glfwWindowHint(GLFW_DECORATED, GL_TRUE);
		glfwWindowHint(GLFW_SAMPLES, örneklemelerininSayısı);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GL_TRUE);
	}
	
	private void penceresiniOturt(final long ekranı) {
		final GLFWVidMode görüntüKipi = glfwGetVideoMode(ekranı);
		glfwSetWindowPos(
			penceresi,
			(görüntüKipi.width() - genişliği) / 2,
			(görüntüKipi.height() - yüksekliği) / 2);
		glfwShowWindow(penceresi);
		glfwMakeContextCurrent(penceresi);
		glfwSwapInterval(değiştirilmeAralığı);
		glfwShowWindow(penceresi);
		glfwSetCursorPos(penceresi, genişliği / 2, yüksekliği / 2);
	}
	
	private void içeriğiniAyarla() {
		GL.createCapabilities();
		
		SistemGünlüğü.KONSOL.yaz("OpenGL Sürümü: " + glGetString(GL_VERSION));
		
		glFrontFace(GL_CCW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glViewport(0, 0, genişliği, yüksekliği);
		glClearColor(
			(float)temizlenmeRengi.birinciBileşeniniEdin(),
			(float)temizlenmeRengi.ikinciBileşeniniEdin(),
			(float)temizlenmeRengi.üçüncüBileşeniniEdin(),
			(float)temizlenmeRengi.dördüncüBileşeniniEdin());
	}
}
