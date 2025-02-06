package org.gal.cmscli.dsl

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

const val BEAN_NODE_NAME = "bean"
const val PROPERTY_NODE_NAME = "property"
const val CMS_STRUCTURE_TYPE_MODE_ATTRIBUTE_FILTER = "cmsStructureTypeModeAttributeFilter"
const val CMS_STRUCTURE_TYPE_MODE_ATTRIBUTE_FILTER_LIST = "cmsStructureTypeModeAttributeFilterList"
const val LIST_MERGE_DIRECTIVE = "listMergeDirective"

@Scoped
@JacksonXmlRootElement(localName = BEAN_NODE_NAME)
data class FilteringBean(
    @field:JacksonXmlProperty(isAttribute = true)
    var id: String = "",
    @field:JacksonXmlProperty(isAttribute = true)
    val parent: String = CMS_STRUCTURE_TYPE_MODE_ATTRIBUTE_FILTER,
    @field:JacksonXmlProperty(localName = PROPERTY_NODE_NAME)
    val constrainedByProperty: ConstrainedByProperty = ConstrainedByProperty(),
    private val property: MutableList<Property> = mutableListOf(),
) {
    fun constrainedByProperty(builder: ConstrainedByProperty.() -> Unit) {
        constrainedByProperty.apply(builder)
    }
    fun property(builder: @Scoped Property.() -> Unit) {
        property.add(Property().apply(builder))
    }
    fun property(propertyInstance: @Scoped Property) {
        property.add(propertyInstance)
    }
    fun toXml() = objectToXml(this)

    // Needed for jackson to parse elements
    @JacksonXmlElementWrapper(useWrapping = false)
    fun getProperty() = mutableListOf<Any>(constrainedByProperty).plus(property)
}

@Scoped
@JacksonXmlRootElement(localName = PROPERTY_NODE_NAME)
data class ConstrainedByProperty(
    @field:JacksonXmlProperty(isAttribute = true)
    var name: String = "constrainedBy",
    val bean: BeanRef = BeanRef(),
) {
    fun bean(builder: @Scoped BeanRef.() -> Unit) {
        bean.apply(builder)
    }
}

@JacksonXmlRootElement(localName = BEAN_NODE_NAME)
data class BeanRef(
    @field:JacksonXmlProperty(isAttribute = true)
    var parent: String = "cmsEqualsTypeAndModeBiPredicate",
    @field:JacksonXmlProperty(isAttribute = true, localName = "p:typeCode")
    var typeCode: String = "",
    @field:JacksonXmlProperty(isAttribute = true, localName = "p:mode")
    var mode: String = "DEFAULT",
)

@Scoped
@JacksonXmlRootElement(localName = PROPERTY_NODE_NAME)
data class Property(
    @field:JacksonXmlProperty(isAttribute = true)
    var name: String = "order",
    val list: List = List(),
){
    fun list(builder: @Scoped List.() -> Unit) {
        list.apply(builder)
    }
}

data class List(
    @field:JacksonXmlProperty(isAttribute = true)
    var merge: String? = null,
    private val value: MutableList<Value> = mutableListOf()
) {
    fun value(v: String) {
        value.add(v)
    }

    // Needed for jackson to parse elements
    @JacksonXmlElementWrapper(useWrapping = false)
    fun getValue() = value
}

typealias Value = String

@Scoped
@JacksonXmlRootElement(localName = BEAN_NODE_NAME)
data class DependsOnBean(
    @field:JacksonXmlProperty(isAttribute = true, localName = "depends-on")
    var dependsOn: String = CMS_STRUCTURE_TYPE_MODE_ATTRIBUTE_FILTER_LIST,
    @field:JacksonXmlProperty(isAttribute = true)
    var parent: String = LIST_MERGE_DIRECTIVE,
    val property: DependsOnProperty = DependsOnProperty(),
) {
    fun property(builder: DependsOnProperty.() -> Unit) {
        property.apply(builder)
    }
    fun toXml() = objectToXml(this)
}

data class DependsOnProperty(
    @field:JacksonXmlProperty(isAttribute = true)
    var name: String = "add",
    @field:JacksonXmlProperty(isAttribute = true)
    var ref: String = "",
)

fun filteringBean(builder: @Scoped FilteringBean.() -> Unit): FilteringBean = FilteringBean().apply(builder)
fun dependsOnBean(builder: @Scoped DependsOnBean.() -> Unit): DependsOnBean = DependsOnBean().apply(builder)
fun constrainedByProperty(builder: @Scoped ConstrainedByProperty.() -> Unit): ConstrainedByProperty = ConstrainedByProperty().apply(builder)
fun property(builder: @Scoped Property.() -> Unit): Property = Property().apply(builder)
