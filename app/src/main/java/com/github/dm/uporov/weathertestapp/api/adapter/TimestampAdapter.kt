package com.github.dm.uporov.weathertestapp.api.adapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.sql.Timestamp

class TimestampAdapter : TypeAdapter<Timestamp>() {

    override fun write(writer: JsonWriter, value: Timestamp) {
        writer.value(value.time / 1000)
    }

    override fun read(reader: JsonReader): Timestamp {
        return Timestamp(reader.nextLong() * 1000)
    }
}