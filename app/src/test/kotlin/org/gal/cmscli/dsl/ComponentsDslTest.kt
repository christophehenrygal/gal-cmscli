package org.gal.cmscli.dsl

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

const val SOME_CMS_COMPONENT = "SomeCmsComponent"
const val SOME_QUALIFIER = "SomeQualifier"
const val SOME_QUALIFIER_2 = "SomeQualifier2"
const val SOME_TYPE = "SomeOtherCmsComponent"
const val SOME_DESCRIPTION = "some qualifier description"
const val SOME_CMS_COMPONENT_TO_EXTEND = "SomeCmsComponentToExtend"
const val SOME_JALO_CLASS = "org.test.SomeClass"

class ComponentsDslTest {

    @Test
    fun `dsl should create valid object tree`() {
        val itemTypes = itemTypes {
            itemType {
                code = SOME_CMS_COMPONENT
                generate = false
                autocreate = false
                extends = SOME_CMS_COMPONENT_TO_EXTEND
                jaloclass = SOME_JALO_CLASS
                attributes {
                    attribute {
                        qualifier = SOME_QUALIFIER
                        type = SOME_TYPE
                        description = SOME_DESCRIPTION
                        modifiers {
                            optional = true
                        }
                        persistence {
                            columnType {
                                value = HYBRIS_LONG_STRING
                            }
                        }
                    }
                    attribute {
                        qualifier = SOME_QUALIFIER_2
                    }
                }
            }
        }

        assertThat(itemTypes).isEqualTo(
            ItemTypes(
                mutableListOf(
                    ItemType(
                        SOME_CMS_COMPONENT,
                        false,
                        false,
                        SOME_CMS_COMPONENT_TO_EXTEND,
                        SOME_JALO_CLASS,
                        Attributes(
                            mutableListOf<Attribute>(
                                Attribute(
                                    SOME_QUALIFIER,
                                    SOME_TYPE,
                                    SOME_DESCRIPTION,
                                    Modifiers(true),
                                    Persistence(DEFAULT_PERSISTENCE_TYPE, ColumnType(HYBRIS_LONG_STRING)),
                                ),
                                Attribute(
                                    SOME_QUALIFIER_2
                                )
                            )
                        )
                    )
                )
            )
        )
    }
}