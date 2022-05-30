package xmlparser.plugins

import xmlparser.core.element.XmlElement
import xmlparser.gui.action.IAction
import xmlparser.gui.action.popupmenu.IActionPopupMenu
import xmlparser.gui.action.AddChildAction
import xmlparser.gui.view.ElementView
import javax.swing.*


class Test {
    val name = "ce"
}

class AddPointPopupMenuAction : IActionPopupMenu<ElementView>
{
    override val displayName: String = "Add Point"

    override fun accept(view: ElementView): Boolean = view.xmlElement.name != "Point"

    override fun action(view: ElementView): IAction? {
        val xField = JTextField(5)
        val yField = JTextField(5)

        val jPanel = JPanel()

        jPanel.add(JLabel("x:"))
        jPanel.add(xField)
        jPanel.add(Box.createHorizontalStrut(15))

        jPanel.add(JLabel("y:"))
        jPanel.add(yField)
        val result = JOptionPane.showConfirmDialog(
            null, jPanel,
            "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION
        )
        return if (result == JOptionPane.OK_OPTION) {
            if(xField.text == "")
            {
                JOptionPane.showMessageDialog(view, "Invalid x value")
                return null
            }
            if(yField.text == "")
            {
                JOptionPane.showMessageDialog(view, "Invalid y value")
                return null
            }

            try
            {
                Integer.parseInt(xField.text)
            }
            catch (exception: Exception)
            {
                JOptionPane.showMessageDialog(view, "Invalid x value")
                return null
            }
            try
            {
                Integer.parseInt(yField.text)
            }
            catch (exception: Exception)
            {
                JOptionPane.showMessageDialog(view, "Invalid y value")
                return null
            }
            val xmlElement = XmlElement("Point")
            xmlElement.addChild("x", xField.text)
            xmlElement.addChild("y", yField.text)
            AddChildAction(view.xmlElement, xmlElement)
        }else
            null
    }

}

class ActionPopupMenu2 : IActionPopupMenu<ElementView>
{
    override val displayName: String = "PluginActionTest2"
    override fun action(view: ElementView): IAction? {
        return ActionTest2()
    }

    class ActionTest2() : IAction
    {
        override val name: String = "ActionTest2"
        override fun execute() {
            TODO("Not yet implemented")
        }

        override fun undo() {
            TODO("Not yet implemented")
        }
    }
}

