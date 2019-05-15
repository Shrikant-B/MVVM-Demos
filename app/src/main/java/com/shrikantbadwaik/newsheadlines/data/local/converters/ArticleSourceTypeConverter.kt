package com.shrikantbadwaik.newsheadlines.data.local.converters

import android.arch.persistence.room.TypeConverter
import com.fasterxml.jackson.databind.ObjectMapper
import com.shrikantbadwaik.newsheadlines.domain.model.Source

class ArticleSourceTypeConverter {
    private val mapper: ObjectMapper = ObjectMapper()

    @TypeConverter
    fun fromSourceToString(source: Source): String {
        return mapper.writeValueAsString(source)
    }

    @TypeConverter
    fun fromStringToSource(value: String): Source {
        return mapper.readValue(value, Source::class.java)
    }
}