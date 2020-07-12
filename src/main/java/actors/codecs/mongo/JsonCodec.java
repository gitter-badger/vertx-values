package actors.codecs.mongo;

import jsonvalues.*;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.codecs.BsonTypeClassMap;
import org.bson.codecs.BsonTypeCodecMap;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;


abstract class JsonCodec {


  CodecRegistry registry;
  BsonTypeClassMap bsonTypeClassMap;
  private BsonTypeCodecMap bsonTypeCodecMap;


  public JsonCodec(final CodecRegistry registry,
                   final BsonTypeClassMap bsonTypeClassMap) {
    this.registry = registry;
    this.bsonTypeClassMap = bsonTypeClassMap;
    this.bsonTypeCodecMap = new BsonTypeCodecMap(bsonTypeClassMap,
                                                 registry
    );
  }

  protected JsValue readValue(final BsonReader reader,
                              final DecoderContext context) {

    BsonType bsonType = reader.getCurrentBsonType();
    if (bsonType == BsonType.NULL) {
      reader.readNull();
      return JsNull.NULL;
    }
    if (bsonType == BsonType.OBJECT_ID) {
      ObjectId objectId = reader.readObjectId();
      return JsStr.of(objectId.toHexString());
    }
    if (bsonType == BsonType.DECIMAL128) {
      try {
        Decimal128 decimal128 = reader.readDecimal128();
        return JsBigDec.of(decimal128.bigDecimalValue());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    //todo mirar si añadir date en json-values
    if(bsonType == BsonType.TIMESTAMP){
      long value = reader.readTimestamp()
                         .getValue();
      return JsLong.of(value);
    }
    if(bsonType == BsonType.DATE_TIME){
      long dateTime = reader.readDateTime();
      return JsLong.of(dateTime);
    }
    Codec<JsValue> codec = (Codec<JsValue>) bsonTypeCodecMap.get(bsonType);
    return codec.decode(reader,
                        context
                       );
  }
}
