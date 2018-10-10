import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import java.io.IOException;

public class FastDFSTest {


    public void testUpload() throws IOException, MyException {
            ClientGlobal.init("G:/antelopeDigital/antelope-manager-web/src/main/resources/conf/client.conf");
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageClient storageClient = new StorageClient(trackerServer,null);
            String[] strings = storageClient.upload_file("G:/test.jpg", "jpg", null);
            for(String s: strings){
                System.out.println(s);
            }
    }
}
