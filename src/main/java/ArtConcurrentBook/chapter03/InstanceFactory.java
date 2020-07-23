package ArtConcurrentBook.chapter03;

public class InstanceFactory {
    private static class InstanceHolder {
        public static Instance instance = new Instance();
    }

    public static Instance getInstance() {
        // 这里将导致InstanceHolder类被初始化
        return InstanceHolder.instance;
    }

    static class Instance {
    }
}
