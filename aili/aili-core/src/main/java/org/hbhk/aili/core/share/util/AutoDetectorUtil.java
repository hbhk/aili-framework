package org.hbhk.aili.core.share.util;


/**
 * 资源变化自动检测
 */
public class AutoDetectorUtil {
//    private static final Log LOGGER = LogFactory.getLog(AutoDetectorUtil.class);
//    //已经被监控的文件
//    private static final Set<String> fileWatchers = new HashSet<>();    
//    private static final Set<String> httpWatchers = new HashSet<>();
//    private static final Map<DirectoryWatcher, String> resources = new HashMap<>();
//    private static final Map<DirectoryWatcher, ResourceLoader> resourceLoaders = new HashMap<>();
//    private static final Map<DirectoryWatcher.WatcherCallback, DirectoryWatcher> watcherCallbacks = new HashMap<>();
//    
//    /**
//     * 加载资源并自动检测资源变化
//     * 当资源发生变化的时候重新自动加载
//     * @param resourceLoader 资源加载逻辑
//     * @param resourcePaths 多个资源路径，用逗号分隔
//     */
//    public static void loadAndWatch(ResourceLoader resourceLoader, String resourcePaths) {
//        LOGGER.info("开始加载资源");
//        LOGGER.info(resourcePaths);
//        long start = System.currentTimeMillis();
//        List<String> result = new ArrayList<>();
//        for(String resource : resourcePaths.split("[,，]")){
//            try{
//                resource = resource.trim();
//                if(resource.startsWith("classpath:")){
//                    //处理类路径资源
//                    result.addAll(loadClasspathResource(resource.replace("classpath:", ""), resourceLoader, resourcePaths));
//                }else if(resource.startsWith("http:")){
//                    //处理HTTP资源
//                    result.addAll(loadHttpResource(resource, resourceLoader));
//                }else{
//                    //处理非类路径资源
//                    result.addAll(loadNoneClasspathResource(resource, resourceLoader, resourcePaths));
//                }
//            }catch(Exception e){
//                LOGGER.error("加载资源失败："+resource, e);
//            }
//        }
//        LOGGER.info("加载资源 "+result.size()+" 行");
//        //调用自定义加载逻辑
//        resourceLoader.clear();
//        resourceLoader.load(result);        
//        long cost = System.currentTimeMillis() - start;
//        LOGGER.info("完成加载资源，耗时"+cost+" 毫秒");
//    }
//    /**
//     * 加载类路径资源
//     * @param resource 资源名称
//     * @param resourceLoader 资源自定义加载逻辑
//     * @param resourcePaths 资源的所有路径，用于资源监控
//     * @return 资源内容
//     * @throws IOException 
//     */
//    private static List<String> loadClasspathResource(String resource, ResourceLoader resourceLoader, String resourcePaths) throws IOException{
//        List<String> result = new ArrayList<>();
//        LOGGER.info("类路径资源："+resource);
//        Enumeration<URL> ps = AutoDetectorUtil.class.getClassLoader().getResources(resource);
//        while(ps.hasMoreElements()) {
//            URL url=ps.nextElement();
//            LOGGER.info("类路径资源URL："+url);
//            if(url.getFile().contains(".jar!")){
//                //加载jar资源
//                result.addAll(load("classpath:"+resource));
//                continue;
//            }
//            File file=new File(url.getFile());
//            boolean dir = file.isDirectory();
//            if(dir){
//                //处理目录
//                result.addAll(loadAndWatchDir(file.toPath(), resourceLoader, resourcePaths));
//            }else{
//                //处理文件
//                result.addAll(load(file.getAbsolutePath()));
//                //监控文件
//                watchFile(file, resourceLoader, resourcePaths);
//            }            
//        }
//        return result;
//    }
//    /**
//     * 加载HTTP资源
//     * @param resource 资源URL
//     * @param resourceLoader 资源自定义加载逻辑
//     * @return 资源内容
//     */
//    private static List<String> loadHttpResource(String resource, ResourceLoader resourceLoader) throws MalformedURLException, IOException {
//        List<String> result = new ArrayList<>();
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(resource).openConnection().getInputStream(), "utf-8"))) {
//            String line = null;
//            while((line = reader.readLine()) != null){
//                line = line.trim();
//                if("".equals(line) || line.startsWith("#")){
//                    continue;
//                }
//                result.add(line);
//            }
//        }
//        watchHttp(resource, resourceLoader);
//        return result;
//    }
//    private static void watchHttp(String resource, final ResourceLoader resourceLoader){
//        String[] attrs = resource.split("/");
//        final String channel = attrs[attrs.length-1];
//        if(httpWatchers.contains(channel)){
//            return;
//        }
//        httpWatchers.add(channel);
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String host = WordConfTools.get("redis.host", "localhost");
//                int port = WordConfTools.getInt("redis.port", 6379);
//                String channel_add = channel+".add";
//                String channel_remove = channel+".remove";
//                LOGGER.info("redis服务器配置信息 host:" + host + ",port:" + port + ",channels:[" + channel_add + "," + channel_remove+"]");
//                while(true){
//                    try{
//                        JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), host, port);
//                        final Jedis jedis = jedisPool.getResource();
//                        LOGGER.info("redis守护线程启动");                
//                        jedis.subscribe(new HttpResourceChangeRedisListener(resourceLoader), new String[]{channel_add, channel_remove});
//                        jedisPool.returnResource(jedis);
//                        LOGGER.info("redis守护线程结束");
//                        break;
//                    }catch(Exception e){
//                        LOGGER.info("redis未启动，暂停一分钟后重新连接");
//                        try {
//                            Thread.sleep(60000);
//                        } catch (InterruptedException ex) {
//                            LOGGER.error(ex.getMessage(), ex);
//                        }
//                    }
//                }
//            }
//        });
//        thread.setDaemon(true);
//        thread.setName("redis守护线程，用于动态监控资源："+channel);
//        thread.start();
//    }
//    private static final class HttpResourceChangeRedisListener extends JedisPubSub {
//        private ResourceLoader resourceLoader;
//        public HttpResourceChangeRedisListener(ResourceLoader resourceLoader){
//            this.resourceLoader = resourceLoader;
//        }
//        @Override
//        public void onMessage(String channel, String message) {
//            LOGGER.debug("onMessage channel:" + channel + " and message:" + message);
//            if(channel.endsWith(".add")){
//                this.resourceLoader.add(message);
//            }else if(channel.endsWith(".remove")){
//                this.resourceLoader.remove(message);
//            }
//        }
//
//        @Override
//        public void onPMessage(String pattern, String channel, String message) {
//            LOGGER.debug("pattern:" + pattern + " and channel:" + channel + " and message:" + message);
//            onMessage(channel, message);
//        }
//
//        @Override
//        public void onPSubscribe(String pattern, int subscribedChannels) {
//            LOGGER.debug("psubscribe pattern:" + pattern + " and subscribedChannels:" + subscribedChannels);
//        }
//
//        @Override
//        public void onPUnsubscribe(String pattern, int subscribedChannels) {
//            LOGGER.debug("punsubscribe pattern:" + pattern + " and subscribedChannels:" + subscribedChannels);
//        }
//
//        @Override
//        public void onSubscribe(String channel, int subscribedChannels) {
//            LOGGER.debug("subscribe channel:" + channel + " and subscribedChannels:" + subscribedChannels);
//        }
//
//        @Override
//        public void onUnsubscribe(String channel, int subscribedChannels) {
//            LOGGER.debug("unsubscribe channel:" + channel + " and subscribedChannels:" + subscribedChannels);
//        }
//    }
//    /**
//     * 加载非类路径资源
//     * @param resource 资源路径
//     * @param resourceLoader 资源自定义加载逻辑
//     * @param resourcePaths 资源的所有路径，用于资源监控
//     * @return 资源内容
//     * @throws IOException 
//     */
//    private static List<String> loadNoneClasspathResource(String resource, ResourceLoader resourceLoader, String resourcePaths) throws IOException {
//        List<String> result = new ArrayList<>();
//        Path path = Paths.get(resource);
//        boolean exist = Files.exists(path);
//        if(!exist){
//            LOGGER.error("资源不存在："+resource);
//            return result;
//        }
//        boolean isDir = Files.isDirectory(path);
//        if(isDir){
//            //处理目录
//            result.addAll(loadAndWatchDir(path, resourceLoader, resourcePaths));
//        }else{
//            //处理文件
//            result.addAll(load(resource));
//            //监控文件
//            watchFile(path.toFile(), resourceLoader, resourcePaths);
//        }
//        return result;
//    }
//    /**
//     * 递归加载目录下面的所有资源
//     * 并监控目录变化
//     * @param path 目录路径
//     * @param resourceLoader 资源自定义加载逻辑
//     * @param resourcePaths 资源的所有路径，用于资源监控
//     * @return 目录所有资源内容
//     */
//    private static List<String> loadAndWatchDir(Path path, ResourceLoader resourceLoader, String resourcePaths) {
//        final List<String> result = new ArrayList<>();
//        try {
//            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
//                @Override
//                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
//                        throws IOException {
//                    result.addAll(load(file.toAbsolutePath().toString()));
//                    return FileVisitResult.CONTINUE;
//                }
//            });
//        } catch (IOException ex) {
//            LOGGER.error("加载资源失败："+path, ex);
//        }
//        
//        if(fileWatchers.contains(path.toString())){
//            //之前已经注册过监控服务，此次忽略
//            return result;
//        }
//        fileWatchers.add(path.toString());
//        DirectoryWatcher.WatcherCallback watcherCallback = new DirectoryWatcher.WatcherCallback(){
//
//            private long lastExecute = System.currentTimeMillis();
//            @Override
//            public void execute(WatchEvent.Kind<?> kind, String path) {
//                //一秒内发生的多个相同事件认定为一次，防止短时间内多次加载资源
//                if(System.currentTimeMillis() - lastExecute > 1000){
//                    lastExecute = System.currentTimeMillis();
//                    LOGGER.info("事件："+kind.name()+" ,路径："+path);
//                    synchronized(AutoDetectorUtil.class){
//                        DirectoryWatcher dw = watcherCallbacks.get(this);
//                        String paths = resources.get(dw);
//                        ResourceLoader loader = resourceLoaders.get(dw);
//                        LOGGER.info("重新加载数据");
//                        loadAndWatch(loader, paths);
//                    }
//                }
//            }
//
//        };
//        DirectoryWatcher directoryWatcher = DirectoryWatcher.getDirectoryWatcher(watcherCallback,
//                StandardWatchEventKinds.ENTRY_CREATE,
//                    StandardWatchEventKinds.ENTRY_MODIFY,
//                    StandardWatchEventKinds.ENTRY_DELETE);
//        directoryWatcher.watchDirectoryTree(path);
//        
//        watcherCallbacks.put(watcherCallback, directoryWatcher);
//        resources.put(directoryWatcher, resourcePaths);
//        resourceLoaders.put(directoryWatcher, resourceLoader);
//        
//        return result;
//    }
//    /**
//     * 加载文件资源
//     * @param path 文件路径
//     * @return 文件内容
//     */
//    private static List<String> load(String path) {
//        List<String> result = new ArrayList<>();
//        try{
//            InputStream in = null;
//            LOGGER.info("加载资源："+path);
//            if(path.startsWith("classpath:")){
//                in = AutoDetectorUtil.class.getClassLoader().getResourceAsStream(path.replace("classpath:", ""));
//            }else{
//                in = new FileInputStream(path);
//            }        
//            try(BufferedReader reader = new BufferedReader(new InputStreamReader(in,"utf-8"))){
//                String line;
//                while((line = reader.readLine()) != null){
//                    line = line.trim();
//                    if("".equals(line) || line.startsWith("#")){
//                        continue;
//                    }
//                    result.add(line);
//                }
//            }
//        }catch(Exception e){
//            LOGGER.error("加载资源失败："+path, e);
//        }
//        return result;
//    }
//    /**
//     * 监控文件变化
//     * @param file 文件
//     */
//    private static void watchFile(final File file, ResourceLoader resourceLoader, String resourcePaths) {
//        if(fileWatchers.contains(file.toString())){
//            //之前已经注册过监控服务，此次忽略
//            return;
//        }
//        fileWatchers.add(file.toString());
//        LOGGER.info("监控文件："+file.toString());
//        DirectoryWatcher.WatcherCallback watcherCallback = new DirectoryWatcher.WatcherCallback(){
//            private long lastExecute = System.currentTimeMillis();
//            @Override
//            public void execute(WatchEvent.Kind<?> kind, String path) {
//                if(System.currentTimeMillis() - lastExecute > 1000){
//                    lastExecute = System.currentTimeMillis();
//                    if(!path.equals(file.toString())){
//                        return;
//                    }
//                    LOGGER.info("事件："+kind.name()+" ,路径："+path);
//                    synchronized(AutoDetectorUtil.class){
//                        DirectoryWatcher dw = watcherCallbacks.get(this);
//                        String paths = resources.get(dw);
//                        ResourceLoader loader = resourceLoaders.get(dw);
//                        LOGGER.info("重新加载数据");
//                        loadAndWatch(loader, paths);
//                    }
//                }
//            }
//
//        };
//        DirectoryWatcher fileWatcher = DirectoryWatcher.getDirectoryWatcher(watcherCallback, 
//                StandardWatchEventKinds.ENTRY_MODIFY,
//                    StandardWatchEventKinds.ENTRY_DELETE);
//        fileWatcher.watchDirectory(file.getParent());        
//        watcherCallbacks.put(watcherCallback, fileWatcher);
//        resources.put(fileWatcher, resourcePaths);
//        resourceLoaders.put(fileWatcher, resourceLoader);
//    }
//    public static void main(String[] args){
//        AutoDetectorUtil.loadAndWatch(new ResourceLoader(){
//
//            @Override
//            public void clear() {
//                System.out.println("清空资源");
//            }
//
//            @Override
//            public void load(List<String> lines) {
//                for(String line : lines){
//                    System.out.println(line);
//                }
//            }
//
//            @Override
//            public void add(String line) {
//                System.out.println("add："+line);
//            }
//
//            @Override
//            public void remove(String line) {
//                System.out.println("remove："+line);
//            }
//        }, "d:/DIC, d:/DIC2, d:/dic.txt, classpath:dic2.txt,classpath:dic");
//    }
}
