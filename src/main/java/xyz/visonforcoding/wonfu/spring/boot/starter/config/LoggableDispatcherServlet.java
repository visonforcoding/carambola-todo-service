package xyz.visonforcoding.wonfu.spring.boot.starter.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 *
 * @author vison.cao <visonforcoding@gmail.com>
 */
public class LoggableDispatcherServlet extends DispatcherServlet {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        //创建一个 json 对象，用来存放 http 日志信息
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("uri", requestWrapper.getRequestURI());
        rootNode.put("clientIp", requestWrapper.getRemoteAddr());
//        rootNode.set("requestHeaders", mapper.valueToTree(getRequestHeaders(requestWrapper)));
        String method = requestWrapper.getMethod();
        String contentType = requestWrapper.getContentType();
        rootNode.put("method", method);
        try {
            super.doDispatch(requestWrapper, responseWrapper);
        } finally {
            if (method.equals("GET") || method.equals("DELETE")) {
                rootNode.set("request", mapper.valueToTree(requestWrapper.getParameterMap()));
            } else if (contentType.equals("application/x-www-form-urlencoded")) {
                rootNode.set("request", mapper.valueToTree(requestWrapper.getParameterMap()));
            } else {
                JsonNode newNode = mapper.readTree(requestWrapper.getContentAsByteArray());
                rootNode.set("request", newNode);
            }
            
            rootNode.put("status", responseWrapper.getStatus());
            JsonNode newNode = mapper.readTree(responseWrapper.getContentAsByteArray());
            rootNode.set("response", newNode);
            
            responseWrapper.copyBodyToResponse();

//            rootNode.set("responseHeaders", mapper.valueToTree(getResponsetHeaders(responseWrapper)));
            Log.info(rootNode.toString());
        }
    }
    
}
