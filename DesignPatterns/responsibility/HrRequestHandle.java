package responsibility;

//HR处理请求
public class HrRequestHandle implements RequestHandler{

    @Override
    public void handleRequest(Request request) {

        if (request instanceof DimissionRequest) {
            System.out.println("离职 人事审批 ...");
        }

        System.out.println("审批完成");

    }
}
