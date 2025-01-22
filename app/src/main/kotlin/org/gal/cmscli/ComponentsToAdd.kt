package org.gal.cmscli

import org.gal.cmscli.dsl.HYBRIS_LONG_STRING
import org.gal.cmscli.dsl.itemType

fun generateComponents() = listOf(
    itemType {
        code = "SampleCmsComponent"
        attributes {
            attribute {
                qualifier = "attribute"
                modifiers {
                    optional = true
                }
                persistence {
                    columnType {
                        HYBRIS_LONG_STRING
                    }
                }
            }
            attribute {
                qualifier = "attribute2"
            }
        }
    }
)