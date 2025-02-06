package org.gal.cmscli.dsl

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.dataformat.xml.XmlMapper

fun objectToXml(instance: Any): String {
    val mapper = XmlMapper()
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    return mapper.writeValueAsString(instance)
}