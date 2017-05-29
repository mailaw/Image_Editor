package a9;

public interface Pixel {
	double getRed();
	double getGreen();
	double getBlue();
	void setRed(double r);
	void setGreen(double g);
	void setBlue(double b);
	double getIntensity();
	int toRGB();
}
