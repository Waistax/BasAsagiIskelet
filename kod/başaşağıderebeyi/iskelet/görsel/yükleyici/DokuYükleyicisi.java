/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.12.2 / 20 Mar 2021 / 22:24:38
 */
package başaşağıderebeyi.iskelet.görsel.yükleyici;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.*;
import java.nio.file.*;
import java.util.*;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

class DokuYükleyicisi {
	private final List<Integer> dokuları;
	
	DokuYükleyicisi() {
		dokuları = new ArrayList<>();
	}
	
	void yokEt() {
		glBindTexture(GL_TEXTURE_2D, 0);
		dokuları.forEach(GL11::glDeleteTextures);
		dokuları.clear();
	}
	
	GLFWImage glfwResmiYükle(final Path dosyaYolu) {
		return glfwResmiYükle(new ResimBilgisi(dosyaYolu));
	}
	
	GLFWImage glfwResmiYükle(final ResimBilgisi resim) {
		final byte[] renkBaytları = resim.renkBaytlarınıBul();
		
		final GLFWImage glfwResmi = GLFWImage
			.malloc()
			.set(
				resim.genişliği,
				resim.yüksekliği,
				BufferUtils
					.createByteBuffer(renkBaytları.length)
					.put(renkBaytları)
					.flip());
		return glfwResmi;
	}
	
	int yükle(final Path dosyaYolu) {
		return yükle(new ResimBilgisi(dosyaYolu));
	}
	
	int yükle(
		final Path dosyaYolu,
		final int küçültmeYöntemi,
		final int büyütmeYöntemi) {
		return yükle(
			new ResimBilgisi(dosyaYolu),
			küçültmeYöntemi,
			büyütmeYöntemi);
	}
	
	int yükle(final ResimBilgisi resim) {
		return yükle(resim, GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR);
	}
	
	int yükle(
		final ResimBilgisi resim,
		final int küçültmeYöntemi,
		final int büyütmeYöntemi) {
		final int doku = glGenTextures();
		dokuları.add(doku);
		glBindTexture(GL_TEXTURE_2D, doku);
		resminVerisiniYükle(resim);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, küçültmeYöntemi);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, büyütmeYöntemi);
		glGenerateMipmap(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, 0);
		return doku;
	}
	
	private void resminVerisiniYükle(final ResimBilgisi resim) {
		final IntBuffer tampon =
			memAllocInt(resim.verisi.length).put(resim.verisi).flip();
		
		glTexImage2D(
			GL_TEXTURE_2D,
			0,
			GL_RGBA,
			resim.genişliği,
			resim.yüksekliği,
			0,
			GL_RGBA,
			GL_UNSIGNED_BYTE,
			tampon);
		
		memFree(tampon);
	}
}
