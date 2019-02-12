package responsibility;

/**
 * @Auther: Ragty
 * @Description: 责任链模式 -->是一种对象的行为模式。在责任链模式里，很多对象由每一个对象对其下家的引用而连接起来形成一条链。请求在这个链上传递，
 *               直到链上的某一个对象决定处理此请求
 *               发出这个请求的客户端并不知道链上的哪一个对象最终处理这个请求，这使得系统可以在不影响客户端的情况下动态地重新组织和分配责任
 * @param:${param}
 * @Date: 2019/2/12 18:45
 */
public class ResponsibilityPattern {

    public static void main(String[] args) {
        RequestHandler hr = new HrRequestHandle();
        RequestHandler pm = new PmRequestHandle(hr);

        Request request = new DimissionRequest();   //升职，人事审批
        hr.handleRequest(request);

        request  = new AddMoneyRequest();           //加薪，项目经理审批
        pm.handleRequest(request);

        request = new DimissionRequest();           //项目经理审批不了，交付给人事审批
        pm.handleRequest(request);

    }

}
