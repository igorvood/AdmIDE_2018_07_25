package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.message

import org.springframework.context.ApplicationEvent

class AddImportEvent : ApplicationEvent {

    var fullNameClass: String

    constructor(source: Any, fullNameClass: String) : super(source) {
        this.fullNameClass = fullNameClass
    }
}