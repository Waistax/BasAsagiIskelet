/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.12.2 / 20 Mar 2021 / 22:24:30
 */
package başaşağıderebeyi.iskelet.görsel.yükleyici;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.*;
import java.util.*;

import org.lwjgl.opengl.*;

class KöşeTamponuYükleyicisi {
	final List<Integer> köşeTamponları;
	
	KöşeTamponuYükleyicisi() {
		köşeTamponları = new ArrayList<>();
	}
	
	void yokEt() {
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		köşeTamponları.forEach(GL15::glDeleteBuffers);
		köşeTamponları.clear();
	}
	
	void yükle(final int sırası, final int boyutu, final float[] veri) {
		yükle(sırası, boyutu, memAllocFloat(veri.length).put(veri).flip());
	}
	
	void yükle(final int sırası, final int boyutu, final FloatBuffer veri) {
		final int köşeTamponu = yükle();
		glBindBuffer(GL_ARRAY_BUFFER, köşeTamponu);
		glBufferData(GL_ARRAY_BUFFER, veri, GL_STATIC_DRAW);
		glVertexAttribPointer(sırası, boyutu, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		memFree(veri);
	}
	
	void sıraYükle(final int[] sıra) {
		sıraYükle(memAllocInt(sıra.length).put(sıra).flip());
	}
	
	void sıraYükle(final IntBuffer sıra) {
		final int sıraTamponu = yükle();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, sıraTamponu);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, sıra, GL_STATIC_DRAW);
		memFree(sıra);
	}
	
	int boşYükle(final int boyutu) {
		final int köşeTamponu = yükle();
		glBindBuffer(GL_ARRAY_BUFFER, köşeTamponu);
		glBufferData(GL_ARRAY_BUFFER, boyutu * 4, GL_STREAM_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		return köşeTamponu;
	}
	
	void oluşumBaşınaDeğişenNitelikEkle(
		final int köşeDizisi,
		final int köşeTamponu,
		final int sırası,
		final int boyutu,
		final int oluşumunBoyutu,
		final int kaçıklığı) {
		glBindVertexArray(köşeDizisi);
		glBindBuffer(GL_ARRAY_BUFFER, köşeTamponu);
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
	
	void oluşumlarınNitelikleriniGüncelle(
		final int köşeTamponu,
		final FloatBuffer veri) {
		glBindBuffer(GL_ARRAY_BUFFER, köşeTamponu);
		glBufferSubData(GL_ARRAY_BUFFER, 0, veri);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	void birOluşumunNitelikleriniGüncelle(
		final int köşeTamponu,
		final FloatBuffer veri,
		final int sırası) {
		glBindBuffer(GL_ARRAY_BUFFER, köşeTamponu);
		glBufferSubData(GL_ARRAY_BUFFER, sırası * veri.capacity(), veri);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	int yükle() {
		final int köşeTamponu = glGenBuffers();
		köşeTamponları.add(köşeTamponu);
		return köşeTamponu;
	}
}
