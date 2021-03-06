package xmlparser.examples.plugins.calendar.component

import xmlparser.editor.controller.ActionStack
import xmlparser.editor.controller.action.EditAttributeValueAction
import xmlparser.editor.view.AttributeView
import xmlparser.editor.view.component.IComponent
import java.awt.GridLayout
import javax.swing.JCheckBox
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingConstants

class MandatoryAttributeComponent: IComponent<AttributeView, JPanel>
{

    override fun accept(view: AttributeView): Boolean {
        return view.xmlAttribute.name == "Mandatory" &&
                view.xmlElement.name == "Event"
    }

    override fun component(view: AttributeView): JPanel {
        val panel = JPanel()
        panel.layout = GridLayout(0,2)
        val label = JLabel(view.xmlAttribute.name)
        label.horizontalAlignment = SwingConstants.RIGHT
        panel.add(label)

        val jCheckBox = JCheckBox()
        jCheckBox.isSelected = convertToBool(view.xmlAttribute.value)
        jCheckBox.addActionListener {
            val value = jCheckBox.isSelected.toString()
            ActionStack.doAction(
                EditAttributeValueAction(
                    view.xmlAttribute,
                    value
                )
            )
        }
        panel.add(jCheckBox)
        return panel
    }

    private fun convertToBool(boolString: String): Boolean = boolString == "true"

}