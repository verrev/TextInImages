import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.imageio.ImageIO;

public class Main {
	public static void main(String[] args) throws Exception {
		for (String s : args) {
			if (s.contains(".txt")) {
				stringToImage(s.replace(".txt", ".png"),readFile(s));
			} else if (s.contains(".png")) {
				System.out.println(getTextFromImage(s));
			}
		}
	}
	public static void stringToImage(String path, String data) {
		try {
			data = stringToBytes(data);
			int a = (int) (Math.sqrt(data.length()) + 1.0);
			BufferedImage img = new BufferedImage(a,a,BufferedImage.TYPE_INT_RGB);
			int k = 0;
			for (int i = 0; i < a; ++i) {
				for (int j = 0; j < a; ++j) {
					if (k >= data.length()) break;
					if (data.charAt(k) == '0')
						img.setRGB(j, i, (255 << 16) | (255 << 8) | 255);
					else
						img.setRGB(j, i, (0 << 16) | (0 << 8) | 0);
					++k;
				}
			}
			ImageIO.write(img,"png",new File(path));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static String getTextFromImage(String path) {
		String s = "";
		try {
			BufferedImage img = ImageIO.read(new File(path));
			for (int i = 0; i < img.getHeight(); ++i) {
				for (int j = 0; j < img.getWidth(); ++j) {
					if (img.getRGB(j, i) == -1)
						s += "0";
					else
						s += "1";			
				}
			}
			String []temp = s.split("(?<=\\G.{" + 8 + "})");
			s = "";
			for (String a : temp) {
				s += (char) Integer.parseInt(a, 2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	public static String stringToBytes(String s) {
		String bd = "";
		try {
			byte[] bytes = null;
			bytes = s.getBytes("UTF-8");
			for (byte b : bytes) {
				String bs = Integer.toBinaryString(b);
				while (bs.length() < 8) bs = "0" + bs;
				bd += bs;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bd;
	}
	public static String readFile(String path) {
		String s = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = "";
			while ((line = br.readLine()) != null) s += line + System.getProperty("line.separator");
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
}