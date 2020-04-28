package com.example.hw_fragment.db

import androidx.room.TypeConverter
import com.example.hw_fragment.internal.HabitType
import java.util.*


class Converter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToLong(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun typeToString(type: HabitType?): String? {
        return type?.toString()
    }

    @TypeConverter
    fun stringToType(s: String?): HabitType? {
        return when (s) {
            "GOOD" -> HabitType.GOOD
            else -> HabitType.BAD
        }
    }
}