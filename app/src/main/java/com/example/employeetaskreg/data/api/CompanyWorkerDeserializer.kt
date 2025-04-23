package com.example.employeetaskreg.data.api

import com.example.employeetaskreg.domain.model.CompanyWorker
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CompanyWorkerDeserializer: JsonDeserializer<CompanyWorker> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): CompanyWorker {
        val jsonObject = json.asJsonObject
        val role = jsonObject.get("role").asString
        return when(jsonObject.get("role").asString){
            "employee" ->{
                CompanyWorker.Employee(
                    id = jsonObject.get("id").asInt,
                    name = jsonObject.get("name").asString,
                    userId = jsonObject.get("userId").asInt,
                    directorId = jsonObject.get("directorId")?.asInt,
                    role = role
                )
            }
            "director" ->{
                CompanyWorker.Director(
                    id = jsonObject.get("id").asInt,
                    name = jsonObject.get("name").asString,
                    userId = jsonObject.get("userId").asInt,
                    role = role
                )
            }
            else -> throw IllegalArgumentException("Unknown CompanyWorker type: $role")
        }
    }

}