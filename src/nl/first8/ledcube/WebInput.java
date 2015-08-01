package nl.first8.ledcube;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

/**
 * A cube input that reads from a website new views.
 */
public class WebInput implements CubeInput {

    private List<CubeInputListener> listeners = new ArrayList<>();
    private final long fetchDelay;
    private final String baseUrl;
    
    private String current;
    
    public WebInput(String baseUrl, long fetchDelay) {
        this.baseUrl = baseUrl;
        this.fetchDelay = fetchDelay;
        start();
    }
    
    private void start() {
        new Thread(()->{
            for(;;) {

                try {
                    URL obj = new URL(baseUrl+"/bulk");
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");

                    String result = IOUtils.toString(con.getInputStream());
                    if (current==null || current.equals(result)) {
                        notifyListeners(result);
                        current = result;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                
                try {
                    Thread.sleep(fetchDelay);
                } catch(Exception e) {throw new RuntimeException(e);}
            }
            
            
        }).start();
    }

   private void notifyListeners(String image) {
        boolean[][][] cube = StringCubeUtil.stringToArray(image);
        
        listeners.forEach( l -> l.onCubeChange(cube));
    }
 
    @Override
    public String getName() {
        return "Web";
    }

    @Override
    public void addLedCubeListener(CubeInputListener listener) {
        listeners.add(listener);
    }

    @Override
    public boolean removeListener(CubeInputListener listener) {
        return listeners.remove(listener);
    }
}
