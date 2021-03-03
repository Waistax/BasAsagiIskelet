/**
 * başaşağıderebeyi.iskelet.Gösterici.java
 * 0.2 / 3 Mar 2021 / 16:51:33
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import başaşağıderebeyi.kütüphane.matematik.*;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

public class Gösterici {
	private final İskelet çalıştıranİskelet;
	
	private final long ekranınınİşaretçisi;
	private final int ekranınınGenişliği;
	private final int ekranınınYüksekliği;
	
	private final long penceresininİşaretçisi;
	
	private final int genişliği;
	private final int yüksekliği;
	private final String başlığı;
	
	private final boolean yenidenBoyutlandırılabilirOlması;
	private final boolean çerçeveliOlması;
	private final boolean tamEkranOlması;
	
	private final int örneklemelerininSayısı;
	private final int değiştirilmeAralığı;
	private final Yöney4 temizlenmeRengi;
	
	public Gösterici(
		İskelet çalıştıranİskelet,
		int genişliği,
		int yüksekliği,
		String başlığı,
		boolean yenidenBoyutlandırılabilirOlması,
		boolean çerçeveliOlması,
		boolean tamEkranOlması,
		int örneklemelerininSayısı,
		int değiştirilmeAralığı,
		Yöney4 temizlemeRengi) {
		this.çalıştıranİskelet = çalıştıranİskelet;
		this.genişliği = genişliği;
		this.yüksekliği = yüksekliği;
		this.başlığı = başlığı;
		this.yenidenBoyutlandırılabilirOlması =
			yenidenBoyutlandırılabilirOlması;
		this.çerçeveliOlması = çerçeveliOlması;
		this.tamEkranOlması = tamEkranOlması;
		this.örneklemelerininSayısı = örneklemelerininSayısı;
		this.değiştirilmeAralığı = değiştirilmeAralığı;
		this.temizlenmeRengi = temizlemeRengi;
		
		GLFWErrorCallback.createPrint().set();
		
		if (!glfwInit())
			throw new IllegalStateException("GLFW başlatılamadı!");
		
		ekranınınİşaretçisi = glfwGetPrimaryMonitor();
		GLFWVidMode görüntüKipi = glfwGetVideoMode(ekranınınİşaretçisi);
		ekranınınGenişliği = görüntüKipi.width();
		ekranınınYüksekliği = görüntüKipi.height();
		
		glfwDefaultWindowHints();
		glfwWindowHint(
			GLFW_RESIZABLE,
			yenidenBoyutlandırılabilirOlması ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_DECORATED, çerçeveliOlması ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_SAMPLES, örneklemelerininSayısı);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GL_TRUE);
		
		penceresininİşaretçisi = glfwCreateWindow(
			genişliği,
			yüksekliği,
			başlığı,
			tamEkranOlması ? ekranınınİşaretçisi : NULL,
			NULL);
		
		if (penceresininİşaretçisi == NULL)
			throw new RuntimeException("Pencere oluşturulamadı!");
		
		// TODO girdiler
		
		glfwSetWindowPos(
			penceresininİşaretçisi,
			(ekranınınGenişliği - genişliği) / 2,
			(ekranınınYüksekliği - yüksekliği) / 2);
		glfwSetCursorPos(
			penceresininİşaretçisi,
			genişliği / 2.0D,
			yüksekliği / 2.0D);
		glfwShowWindow(penceresininİşaretçisi);
		glfwMakeContextCurrent(penceresininİşaretçisi);
		glfwSwapInterval(değiştirilmeAralığı);
		glfwShowWindow(penceresininİşaretçisi);
		
		GL.createCapabilities();
		
		glFrontFace(GL_CCW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glViewport(0, 0, genişliği, yüksekliği);
		glClearColor(
			temizlemeRengi.birinciBileşeni,
			temizlemeRengi.ikinciBileşeni,
			temizlemeRengi.üçüncüBileşeni,
			temizlemeRengi.dördüncüBileşeni);
	}
	
	public void yokEt() {
		// TODO girdiler
		glfwDestroyWindow(penceresininİşaretçisi);
		glfwTerminate();
	}
	
	public void göster() {
		glfwSwapBuffers(penceresininİşaretçisi);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		int hataKodu = glGetError();
		if (hataKodu != GL_NO_ERROR)
			throw new RuntimeException(
				"OpenGL hata verdi! Hata Kodu: " + hataKodu);
	}
	
	public boolean penceresininKapatılmasınıEdin() {
		glfwPollEvents();
		return glfwWindowShouldClose(penceresininİşaretçisi);
	}
}
