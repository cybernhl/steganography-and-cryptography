import javax.swing.JFrame
import javax.swing.JOptionPane

public fun getCommand() = getString("Task (hide, show, exit):")
public fun runCommand(command: String) {
    when (command) {
        "hide" -> hide()
        "show" -> show()
        else -> "Wrong task: $command"
    }.let { println(it) }
}

public fun runCommandMessageDialog(command: String, frame: JFrame) {
    when (command) {
        "hide" -> JOptionPane.showMessageDialog(frame, hide())
        "show" -> JOptionPane.showMessageDialog(frame, show())
        else -> JOptionPane.showMessageDialog(frame, "Wrong task: $command")
    }
}