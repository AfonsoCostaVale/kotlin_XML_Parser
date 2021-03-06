package xmlparser.editor.controller.action

import xmlparser.core.XmlAttribute

class EditAttributeValueAction(private val xmlAttribute: XmlAttribute,
                               private val newValue: String) : IAction {

    private val oldValue: String = xmlAttribute.value
    override val name: String = "Attribute value from $oldValue to $newValue"

    override fun execute() {
        xmlAttribute.value = newValue
    }

    override fun undo() {
        xmlAttribute.value = oldValue
    }
}