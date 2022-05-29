package xmlparser.gui.action

import xmlparser.core.element.XmlElement
import xmlparser.gui.IAction

class RenameElementAction(private val xmlElement: XmlElement, private val newName: String) : IAction {

    override val name: String = "Rename Element ${xmlElement.name} to $newName"

    private val oldName: String = xmlElement.name
    override val displayName: String = ""

    override fun execute() {
        xmlElement.name = newName
    }

    override fun undo() {
        xmlElement.name = oldName
    }
}