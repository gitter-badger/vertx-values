package actors.httpclient;



import java.util.function.Function;


public class HttpExceptions  {

    public static final byte HTTP_METHOD_NOT_SUPPORTED_CODE = 101;
    public static final byte EXCEPTION_READING_BODY_RESPONSE_CODE = 102;
    public static final byte EXCEPTION_RESPONSE_CODE = 103;

    public static Function<Integer,HttpException> HTTP_METHOD_NOT_SUPPORTED =
           method -> new HttpException(HTTP_METHOD_NOT_SUPPORTED_CODE,"The method "+ method +"is not supported. Supported types are in enum HttpReqBuilder.TYPE. Use a provided builder to make requests.");


    public static Function<Throwable,HttpException> EXCEPTION_READING_BODY_RESPONSE =
            exc -> new HttpException(EXCEPTION_READING_BODY_RESPONSE_CODE,exc);


    public static Function<Throwable,HttpException> EXCEPTION_RESPONSE =
            exc -> new HttpException(EXCEPTION_RESPONSE_CODE,exc);
}
