package io.github.jaksatomovic.fx.commons.utility

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class LocalTimeSerializer : JsonSerializer<LocalTime>() {
    override fun serialize(value: LocalTime?, gen: JsonGenerator, serializers: SerializerProvider) {
        return gen.writeString(value?.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }
}

class LocalTimeDeserializer : JsonDeserializer<LocalTime>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): LocalTime {
        return LocalTime.parse(p.readValueAs(String::class.java))
    }
}