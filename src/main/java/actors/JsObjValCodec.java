package actors;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.DecodeException;
import jsonvalues.JsObj;
import jsonvalues.MalformedJson;

/**
 Codec that allows to send {@link JsObj} as messages
 */
public class JsObjValCodec implements MessageCodec<JsObj,JsObj>{

  public static JsObjValCodec INSTANCE = new JsObjValCodec();

  private JsObjValCodec(){};
  @Override
  public JsObj decodeFromWire(int pos,
                              final Buffer buffer
                             )
  {
    try
    {
      int length = buffer.getInt(pos);
      pos += 4;
      return JsObj.parse(buffer.getString(pos,
                                          pos + length
                                         ));
    }
    catch (MalformedJson malformedJson)
    {
      throw new DecodeException(malformedJson.getMessage());
    }
  }

  @Override
  public void encodeToWire(final Buffer buffer,
                           final JsObj obj
                          )
  {
    byte[] encoded = obj.serialize();
    buffer.appendInt(encoded.length);
    buffer.appendBytes(encoded);
  }

  @Override
  public String name()
  {
    return "json-obj-val";
  }

  @Override
  public byte systemCodecID()
  {
    return -1;
  }

  @Override
  public JsObj transform(final JsObj obj)
  {
    return obj;
  }
}
