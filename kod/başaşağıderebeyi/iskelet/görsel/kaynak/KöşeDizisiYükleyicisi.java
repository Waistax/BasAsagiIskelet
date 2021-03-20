/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.12.2 / 20 Mar 2021 / 22:23:51
 */
package başaşağıderebeyi.iskelet.görsel.kaynak;

import static org.lwjgl.opengl.GL30.*;

import java.util.*;

import org.lwjgl.opengl.*;

class KöşeDizisiYükleyicisi {
	private final List<Integer> köşeDizileri;
	
	KöşeDizisiYükleyicisi() {
		köşeDizileri = new ArrayList<>();
	}
	
	void yokEt() {
		glBindVertexArray(0);
		köşeDizileri.forEach(GL30::glDeleteVertexArrays);
		köşeDizileri.clear();
	}
	
	int yükle() {
		final int köşeDizisi = glGenVertexArrays();
		köşeDizileri.add(köşeDizisi);
		return köşeDizisi;
	}
}
