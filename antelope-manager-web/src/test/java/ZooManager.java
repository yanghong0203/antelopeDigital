import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;


/**
 * zoo管理类
 * @author bla
 *
 */
public class ZooManager implements Watcher {

    public ZooKeeper zooKeeper;

    private static final int SESSION_TIME_OUT = 6000;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * 连接zookeeper
     *
     * @param host
     * @throws IOException
     * @throws InterruptedException
     */
    public void connectZookeeper(String host) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper(host, SESSION_TIME_OUT, this);
        countDownLatch.await();
        System.out.println("zookeeper connect ok");

    }

    /**
     * 实现watcher的接口方法，当连接zookeeper成功后，zookeeper会通过此方法通知watcher
     * 此处为如果接受到连接成功的event，则countDown，让当前线程继续其他事情。
     */
    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            System.out.println("watcher received event");
            countDownLatch.countDown();
        }
    }

    /**
     * 根据路径创建节点，并且设置节点数据
     *
     * @param path
     * @param data
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String createNode(String path, byte[] data) throws KeeperException, InterruptedException {
        return this.zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    /**
     * 根据路径获取所有孩子节点
     *
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public List<String> getChildren(String path) throws KeeperException, InterruptedException {
        return this.zooKeeper.getChildren(path, false);
    }

    public Stat setData(String path, byte[] data, int version) throws KeeperException, InterruptedException {
        return this.zooKeeper.setData(path, data, version);
    }

    /**
     * 根据路径获取节点数据
     *
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public byte[] getData(String path) throws KeeperException, InterruptedException {
        return this.zooKeeper.getData(path, false, null);
    }

    /**
     * 删除节点
     *
     * @param path
     * @param version
     * @throws InterruptedException
     * @throws KeeperException
     */
    public void deletNode(String path, int version) throws InterruptedException, KeeperException {
        this.zooKeeper.delete(path, version);
    }

    /**
     * 关闭zookeeper连接
     *
     * @throws InterruptedException
     */
    public void closeConnect() throws InterruptedException {
        if (null != zooKeeper) {
            zooKeeper.close();
        }
    }

    public static void main(String[] args) {
        try {
            ZooManager baseZookeeper = new ZooManager();

            String host = "115.159.68.198:2181";

            // 连接zookeeper
            baseZookeeper.connectZookeeper(host);
            System.out.println("--------connect zookeeper ok-----------");

            // 创建节点
             /*byte [] data = {1, 2, 3, 4, 5};
             String result = baseZookeeper.createNode("/test", data);
             System.out.println(result);
             System.out.println("--------create node ok-----------");*/


            // 获取某路径下所有节点
            List<String> children = baseZookeeper.getChildren("/");
            for (String child : children)
            {
                System.out.println(child);
            }
            System.out.println("--------get children ok-----------");

            // 获取节点数据
             byte [] nodeData1 = baseZookeeper.getData("/dubbo");
            System.out.println(nodeData1);
            System.out.println("--------get node data ok-----------");

            // 更新节点数据
           /* byte[] data = "测试".getBytes("UTF-8");
            baseZookeeper.createNode("/test", data);//版本号为当前的版本
            System.out.println("--------set node data ok-----------");*/

           /* byte[] nodeData = baseZookeeper.getData("/test");
            System.out.println(new String(nodeData,"UTF-8"));
            System.out.println("--------get node new data ok-----------");*/

            baseZookeeper.closeConnect();
            System.out.println("--------close zookeeper ok-----------");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("...zookeeper操作出错....");
        }
    }
}