package actors.httpclient;



import java.util.function.Function;


public class HttpExceptions  {


    public static final int ERROR_HTTP_METHOD_CODE_NOT_IMPLEMENTED = 10000;
    public static final int EXCEPTION_READING_BODY_RESPONSE_CODE = 10001;
    public static final int EXCEPTION_RESPONSE_UNKNOWN = 10002;
    public static final int EXCEPTION_UNKNOWN_HOST = 10003;
    public static final int EXCEPTION_CONNECT_TIMEOUT = 10004;
    public static final int EXCEPTION_REQUEST_TIMEOUT = 10005;

    public static Function<Integer,HttpException> HTTP_METHOD_NOT_SUPPORTED =
           method -> new HttpException(ERROR_HTTP_METHOD_CODE_NOT_IMPLEMENTED, "The method "+ method +"is not supported. Supported types are in enum HttpReqBuilder.TYPE. Use a provided builder to make requests.");


    public static Function<Throwable,HttpException> EXCEPTION_READING_BODY_RESPONSE =
            exc -> new HttpException(EXCEPTION_READING_BODY_RESPONSE_CODE,exc);



    public static Function<Throwable,HttpException> EXCEPTION_RESPONSE =
            exc -> {
                switch (exc.getClass()
                           .getSimpleName()) {
                    case "ConnectTimeoutException": {
                        return new HttpException(EXCEPTION_CONNECT_TIMEOUT,
                                                 exc);
                    }
                    case "UnknownHostException": {
                        return new HttpException(EXCEPTION_UNKNOWN_HOST,
                                                 exc);
                    }
                    case "NoStackTraceTimeoutException": {
                        return new HttpException(EXCEPTION_REQUEST_TIMEOUT,
                                                 exc);

                    }
                    default:
                        return new HttpException(EXCEPTION_RESPONSE_UNKNOWN,
                                                 exc
                        );
                }
            };

}
