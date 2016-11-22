package utilities;

import generalstuff.FerryConfigDetail;
import java.util.HashMap;
import java.util.Map;

public class FerryConfigListManagement {

    private static Map<Long, FerryConfigDetail> ferryConfigsDetailMap;
    private static long nextIdferryConfig;

    public FerryConfigListManagement() {
        ferryConfigsDetailMap = new HashMap<>();
        nextIdferryConfig = 0;;
    }

    public void addFerryConfigsDetail( FerryConfigDetail ferryConfigDetail ) {
        ferryConfigsDetailMap.put( nextIdferryConfig, ferryConfigDetail );
    }

    public Map<Long, FerryConfigDetail> getFerryConfigs() {
        return ferryConfigsDetailMap;
    }

    public static long getNextIdFerryConfigsDetail() {
        return nextIdferryConfig++;
    }
}
