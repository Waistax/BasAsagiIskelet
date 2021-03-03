/**
 * başaşağıderebeyi.iskelet.Gösterici.java
 * 0.2 / 3 Mar 2021 / 16:51:33
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 
 * Waistax Engine'den alındı.
 * 4.0.0 / 2 Kas 2018 / ?
 */
package başaşağıderebeyi.iskelet;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import başaşağıderebeyi.iskelet.girdi.*;
import başaşağıderebeyi.kütüphane.matematik.*;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

/** GLFW ile bir pencere oluşturan ve OpenGL içeriğini ayarlayan araç. */
public class Gösterici {
	private final int genişliği;
	private final int yüksekliği;
	private final String başlığı;
	
	private final boolean tamEkranOlması;
	
	private final int örneklemelerininSayısı;
	private final int değiştirilmeAralığı;
	private final Yöney4 temizlenmeRengi;
	
	private long penceresininİşaretçisi;
	private Girdi girdisi;
	
	/** Verilenler ile tanımlar. */
	public Gösterici(
		int genişliği,
		int yüksekliği,
		String başlığı,
		boolean tamEkranOlması,
		int örneklemelerininSayısı,
		int değiştirilmeAralığı,
		Yöney4 temizlenmeRengi) {
		this.genişliği = genişliği;
		this.yüksekliği = yüksekliği;
		this.başlığı = başlığı;
		this.tamEkranOlması = tamEkranOlması;
		this.örneklemelerininSayısı = örneklemelerininSayısı;
		this.değiştirilmeAralığı = değiştirilmeAralığı;
		this.temizlenmeRengi = temizlenmeRengi;
	}
	
	void penceresiniOluştur(İskelet çalıştıranİskelet) {
		GLFWErrorCallback.createPrint().set();
		
		if (!glfwInit())
			throw new IllegalStateException("GLFW başlatılamadı!");
		
		penceresiniAyarla();
		final long ekranınınİşaretçisi = glfwGetPrimaryMonitor();
		penceresininİşaretçisi = glfwCreateWindow(
			genişliği,
			yüksekliği,
			başlığı,
			tamEkranOlması ? ekranınınİşaretçisi : NULL,
			NULL);
		
		if (penceresininİşaretçisi == NULL)
			throw new RuntimeException("Pencere oluşturulamadı!");
		
		girdisi = new Girdi(
			çalıştıranİskelet.olayDağıtıcısınıEdin(),
			çalıştıranİskelet.girdisiniEdin(),
			penceresininİşaretçisi);
		
		penceresiniOturt(ekranınınİşaretçisi);
		içeriğiniAyarla();
	}
	
	void yokEt() {
		girdisi.bildiriceleriniSal();
		glfwDestroyWindow(penceresininİşaretçisi);
		glfwTerminate();
	}
	
	void göster() {
		glfwSwapBuffers(penceresininİşaretçisi);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		final int hataKodu = glGetError();
		if (hataKodu != GL_NO_ERROR)
			throw new RuntimeException(
				"OpenGL hata verdi! Hata Kodu: " + hataKodu);
	}
	
	boolean penceresininKapatılmasınıEdin() {
		glfwPollEvents();
		return glfwWindowShouldClose(penceresininİşaretçisi);
	}
	
	private void penceresiniAyarla() {
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		glfwWindowHint(GLFW_DECORATED, GL_TRUE);
		glfwWindowHint(GLFW_SAMPLES, örneklemelerininSayısı);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GL_TRUE);
	}
	
	private void penceresiniOturt(final long ekranınınİşaretçisi) {
		final GLFWVidMode görüntüKipi = glfwGetVideoMode(ekranınınİşaretçisi);
		glfwSetWindowPos(
			penceresininİşaretçisi,
			(görüntüKipi.width() - genişliği) / 2,
			(görüntüKipi.height() - yüksekliği) / 2);
		glfwSetCursorPos(
			penceresininİşaretçisi,
			genişliği / 2.0D,
			yüksekliği / 2.0D);
		glfwShowWindow(penceresininİşaretçisi);
		glfwMakeContextCurrent(penceresininİşaretçisi);
		glfwSwapInterval(değiştirilmeAralığı);
		glfwShowWindow(penceresininİşaretçisi);
	}
	
	private void içeriğiniAyarla() {
		GL.createCapabilities();
		
		glFrontFace(GL_CCW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glViewport(0, 0, genişliği, yüksekliği);
		glClearColor(
			temizlenmeRengi.birinciBileşeni,
			temizlenmeRengi.ikinciBileşeni,
			temizlenmeRengi.üçüncüBileşeni,
			temizlenmeRengi.dördüncüBileşeni);
	}
}
