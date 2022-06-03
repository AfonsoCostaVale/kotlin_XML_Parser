package xmlparser.plugins.calendar.component

import xmlparser.gui.view.ElementView
import xmlparser.gui.view.component.IComponent
import javax.swing.JPanel

class CalendarElementValueComponent : IComponent<ElementView> {

    override fun accept(view: ElementView): Boolean = view.xmlElement.name == "Calendar"

    override fun component(view: ElementView): JPanel? {
        return null
    }
}