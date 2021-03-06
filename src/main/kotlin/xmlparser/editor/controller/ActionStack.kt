package xmlparser.editor.controller

import xmlparser.editor.controller.action.IAction
import java.util.*

object ActionStack {
    val undoStack = Stack<IAction>()
    private val redoStack = Stack<IAction>()

    fun doAction(action: IAction)
    {
        action.execute()
        undoStack.push(action)
    }

    fun undoAction()
    {
        val action = undoStack.pop()
        action.undo()
        redoStack.push(action)
    }

    fun redoAction()
    {
        val action = redoStack.pop()
        doAction(action)
    }

    fun getPeekActionUndo(): IAction = undoStack.peek()

    fun getPeekActionRedo(): IAction = redoStack.peek()

    fun isUndoStackEmpty() = undoStack.isEmpty()

    fun isRedoStackEmpty() = redoStack.isEmpty()

}