package actors.codecs.mongo;
import jsonvalues.JsLong;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.*;


class JsLongCodec implements Codec<JsLong> {

    private static final LongCodec longCodec = new LongCodec();
    @Override
    public void encode(final BsonWriter writer, final JsLong jsLong, final EncoderContext encoderContext) {
       longCodec.encode(writer, jsLong.value, encoderContext);
    }

    @Override
    public JsLong decode(final BsonReader reader, final DecoderContext decoderContext) {
        return JsLong.of(longCodec.decode(reader,
                                          decoderContext)
                       );
    }

    @Override
    public Class<JsLong> getEncoderClass() {
        return JsLong.class;
    }
}
