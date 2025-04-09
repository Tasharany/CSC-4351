package FindEscape;

public abstract class Escape {
	public int depth;
	public boolean escape;

	public Escape(int d) {
		depth = d;
		escape = false;
	}

	public void setEscape() {
		escape = true;
	}
}




