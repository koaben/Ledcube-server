package nl.first8.ledcube;

public class CubeUtil {
	public static final Object CUBE_LOCK = new Object();

	public static String cubeToString(Cube cube) {
		StringBuilder s = new StringBuilder();
		
		for (int z=7; z>=0;z--) {
			for (int y=0; y<8;y++) {
				for (int x=0; x<8;x++) {
					s.append( (cube.getPixel(x,y,z)?"1":"0"));
				}
				s.append("\n");
			}
			s.append("\n");
		}
		
		return s.toString();
	}

	public static void stringToCube(Cube cube, String image) {
		String filtered = image.replaceAll("\\s","");
		for (int z=0; z<8;z++) {
			for (int y=0; y<8;y++) {
				for (int x=0; x<8;x++) {
					int index = (7-z)*8*8 + y*8 + x;
					cube.setPixel(x, y, z, filtered.charAt(index)=='1');
				}
			}
		}
	}

}
