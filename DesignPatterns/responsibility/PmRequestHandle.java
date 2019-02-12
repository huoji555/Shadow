package responsibility;

/**
 * @Auther: HASEE
 * @Description:
 * @param:${param}
 * @Date: 2019/2/12 18:41
 */
public class PmRequestHandle implements RequestHandler {

    RequestHandler requestHandler;

    public PmRequestHandle(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }


    @Override
    public void handleRequest(Request request) {
        if (request instanceof AddMoneyRequest) {
            System.out.println("加薪  项目经理审批 ...");
        } else {
            requestHandler.handleRequest(request);
        }
    }
}
