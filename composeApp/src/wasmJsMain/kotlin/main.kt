import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.ariastro.portfolio.App
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.configureWebResources
import org.w3c.dom.HTMLElement

@OptIn(ExperimentalComposeUiApi::class, ExperimentalResourceApi::class)
fun main() {
    val basePath = resolveBasePath()
    configureWebResources {
        resourcePathMapping { path ->
            val clean = path.trimStart('/')
            if (basePath == "/") "./$clean" else "${basePath.trimEnd('/')}/$clean"
        }
    }
    ComposeViewport(document.body!!) {
        LaunchedEffect(Unit) {
            // Wait fonts + first layout so user never sees tofu squares
            delay(400)
            markAppReady()
        }
        App()
    }
}

private fun resolveBasePath(): String {
    val path = window.location.pathname
    return when {
        path.endsWith(".html") -> {
            val dir = path.substringBeforeLast('/')
            if (dir.isEmpty()) "/" else "$dir/"
        }
        path.endsWith("/") -> path
        else -> "$path/"
    }
}

private fun markAppReady() {
    val body = document.body ?: return
    body.classList.remove("booting")
    body.classList.add("ready")
    val loading = document.getElementById("loading") as? HTMLElement
    loading?.classList?.add("hidden")
}
