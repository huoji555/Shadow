package DesignPatterns.facade;

/**
 * @Auther: Ragty
 * @Date: 2019/2/1 14:22
 * @Description: 外观模式(提供一个高级接口，降低子系统与客户端的耦合度，且客户端调用非常方便)
 */
public class FacadePattern {

    public static void main(String[] args) {
        FacadeMarker marker = new FacadeMarker();

        marker.methodA();
        marker.methodB();
        marker.methodC();

    }

}
