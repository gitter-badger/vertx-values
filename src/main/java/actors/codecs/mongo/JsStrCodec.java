package actors.codecs.mongo;
import jsonvalues.JsStr;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.*;


class JsStrCodec implements Codec<JsStr> {

    private static final StringCodec strCodec = new StringCodec();
    @Override
    public void encode(final BsonWriter writer, final JsStr jsStr, final EncoderContext encoderContext) {
       strCodec.encode(writer,jsStr.value,encoderContext);
    }

    @Override
    public JsStr decode(final BsonReader reader, final DecoderContext decoderContext) {
        return JsStr.of(strCodec.decode(reader,
                                            decoderContext)
                       );
    }

    @Override
    public Class<JsStr> getEncoderClass() {
        return JsStr.class;
    }
}
