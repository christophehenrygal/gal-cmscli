package org.gal.cmscli.dsl

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

const val DEFAULT_EXTENDS = "SimpleCMSComponent"
const val DEFAULT_ATTRIBUTE_TYPE = "localized:java.lang.String"
const val DEFAULT_PERSISTENCE_TYPE = "property"
const val HYBRIS_LONG_STRING = "HYBRIS.LONG_STRING"

@Scoped
data class ItemTypes(
    private val itemType: MutableList<ItemType> = mutableListOf(),
) {
    fun itemType(builder: ItemType.() -> Unit) {
        itemType.add(ItemType().apply(builder))
    }

    // Needed for jackson to parse elements
    @JacksonXmlElementWrapper(useWrapping = false)
    fun getItemType() = itemType
}

@Scoped
@JacksonXmlRootElement(localName = "itemtype")
data class ItemType(
    @field:JacksonXmlProperty(isAttribute = true)
    var code: String = "",
    @field:JacksonXmlProperty(isAttribute = true)
    var generate: Boolean = true,
    @field:JacksonXmlProperty(isAttribute = true)
    var autocreate: Boolean = true,
    @field:JacksonXmlProperty(isAttribute = true)
    var extends: String = DEFAULT_EXTENDS,
    @field:JacksonXmlProperty(isAttribute = true)
    var jaloclass: String = "",
    val attributes: Attributes = Attributes(),
    @field:JsonIgnore @get:JsonIgnore
    var isOrdered: Boolean = true,
) {
    fun attributes(builder: @Scoped Attributes.() -> Unit) {
        attributes.apply(builder)
    }

    fun toXml() = objectToXml(this)
}

data class Attributes(
    private val attribute: MutableList<Attribute> = mutableListOf()
) {
    fun attribute(builder: @Scoped Attribute.() -> Unit) {
        attribute.add(Attribute().apply(builder))
    }

    // Needed for jackson to parse elements
    @JacksonXmlElementWrapper(useWrapping = false)
    fun getAttribute() = attribute
}

data class Attribute(
    @field:JacksonXmlProperty(isAttribute = true)
    var qualifier: String = "",
    @field:JacksonXmlProperty(isAttribute = true)
    var type: String = DEFAULT_ATTRIBUTE_TYPE,
    var description: String = "",
    private var modifiers: Modifiers = Modifiers(),
    private val persistence: Persistence = Persistence(),
) {
    fun modifiers(builder: @Scoped Modifiers.() -> Unit) {
        modifiers = Modifiers().apply(builder)
    }

    fun persistence(builder: @Scoped Persistence.() -> Unit) {
        persistence.apply(builder)
    }

    // Needed for jackson to parse elements
    fun getModifiers() = modifiers
    fun getPersistence() = persistence
}

data class Modifiers(
    @field:JacksonXmlProperty(isAttribute = true)
    var optional: Boolean = false,
)

data class Persistence(
    @field:JacksonXmlProperty(isAttribute = true)
    var type: String = DEFAULT_PERSISTENCE_TYPE,
    private var columntype: ColumnType? = null,
) {
    fun columnType(builder: @Scoped ColumnType.() -> Unit) {
        columntype = ColumnType().apply(builder)
    }

    // Needed for jackson to parse elements
    fun getColumntype() = columntype
}

data class ColumnType(
    var value: String = HYBRIS_LONG_STRING
)

fun itemTypes(builder: @Scoped ItemTypes.() -> Unit): ItemTypes = ItemTypes().apply(builder)
fun itemType(builder: @Scoped ItemType.() -> Unit): ItemType = ItemType().apply(builder)