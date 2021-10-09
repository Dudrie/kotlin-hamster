package de.github.dudrie.hamster.ui.application.windows

import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import java.util.concurrent.CountDownLatch

/**
 * Listener used for the main window.
 */
class AppWindowListener(private val initLatch: CountDownLatch) : WindowListener {
    override fun windowOpened(e: WindowEvent?) {
        initLatch.countDown()
    }

    override fun windowClosing(e: WindowEvent?) {}

    override fun windowClosed(e: WindowEvent?) {}

    override fun windowIconified(e: WindowEvent?) {}

    override fun windowDeiconified(e: WindowEvent?) {}

    override fun windowActivated(e: WindowEvent?) {}

    override fun windowDeactivated(e: WindowEvent?) {}
}
