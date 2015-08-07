package nl.first8.ledcube;

public class StringCubeUtil implements Cube {

    public static boolean[][][] stringToArray(String image) {
        boolean[][][] cube = new boolean[WIDTH][HEIGHT][DEPTH];
        String filtered = image.replaceAll("\\s", "");
        for (int z = 0; z < 8; z++) {
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    int index = (7 - z) * 8 * 8 + y * 8 + x;
                    cube[x][y][z] = filtered.charAt(index) == '1';
                }
            }
        }
        return cube;
    }

    public static String cubeToString(CubeOutput cube) {
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

}
